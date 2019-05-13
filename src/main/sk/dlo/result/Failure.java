// Copyright (c) 2019 Daniel Lovasko
// All Rights Reserved
//
// Distributed under the terms of the 2-clause BSD License. The full
// license is in the file LICENSE, distributed as part of this software.

package sk.dlo.result;

import java.util.function.Consumer;
import java.util.function.Function;


/**
 * Result representing a failure of a computation.
 *
 * @param <S> success type
 * @param <F> failure type
 *
 * @author Daniel Lovasko
 */
public final class Failure<S, F> implements Result<S, F> {
    /**
     * Storage of the failed result of the computation.
     */
    private final F failure;

    /**
     * Initialize the failed result.
     *
     * This constructor should remain package-scoped, as it is not within the design of the library
     * for users to be able to create these objects directly.
     *
     * @param failure failure
     */
    Failure(final F failure) {
        this.failure = failure;
    }

    /**
     * Always false, as this class does not represent the result of a successful computation.
     *
     * @return false
     */
    @Override
    public boolean isSuccess() {
        return false;
    }

    /**
     * Always true, as this class does represent the result of a failed computation.
     *
     * @return true
     */
    @Override
    public boolean isFailure() {
        return true;
    }

    /**
     * This function has no effect, as this class does represent a failed result.
     *
     * @param function function to apply to the success (unused)
     * @param <Z> success type
     *
     * @return new identical result
     */
    @Override
    public <Z> Result<Z, F> transformSuccess(final Function<S, Z> function) {
        return Result.failure(this.failure);
    }

    /**
     * Apply a function to the failure.
     *
     * @param function function to apply to the failure 
     * @param <G> failure type
     *
     * @return new result
     */
    @Override
    public <G> Result<S, G> transformFailure(final Function<F, G> function) {
        return Result.failure(function.apply(this.failure));
    }

    /**
     * Propagate the current failed result onwards, without executing the
     * provided function.
     *
     * The following table details the behaviour:
     *   failure(X) and success(Y) = failure(X)
     *   failure(X) and failure(Y) = failure(X)
     *
     * @param function function to apply to the success (unused)
     * @param <Z> success type
     *
     * @return identical failed result
     */
    @Override
    public <Z> Result<Z, F> andThen(final Function<S, Result<Z, F>> function) {
        return Result.failure(this.failure);
    }

    /**
     * Always return an identical copy of itself, as a failure should not be propagated further in
     * case of a stream of conjunctions.
     *
     * The following table details the behaviour:
     *   failure(X) and success(Y) = failure(X)
     *   failure(X) and failure(Y) = failure(X)
     *
     * @param result subsequent result
     * @param <Z> success type
     *
     * @return
     */
    @Override
    public <Z> Result<Z, F> and(final Result<Z, F> result) {
        return Result.failure(this.failure);
    }

    /**
     * Apply a function to the function, producing a new result.
     *
     * The following table details the behaviour:
     *   failure(X) or success(Y) = success(Y)
     *   failure(X) or failure(Y) = failure(Y)
     *
     * @param function function to apply to the failure 
     * @param <G> failure type
     *
     * @return new result
     */
    @Override
    public <G> Result<S, G> orElse(final Function<F, Result<S, G>> function) {
        return function.apply(this.failure);
    }

    /**
     * Always select the supplied argument, as the current failed computation should not be
     * propagated further in a stream of disjunctions.
     *
     * The following table details the behaviour:
     *   failure(X) or success(Y) = success(Y)
     *   failure(X) or failure(Y) = failure(Y)
     *
     * @param result subsequent result
     * @param <G> failure type
     *
     * @return subsequent result
     */
    @Override
    public <G> Result<S, G> or(final Result<S, G> result) {
        return result;
    }

    /**
     * Transform the failure into a new object.
     *
     * @param successFunction function to apply to the success (unused)
     * @param failureFunction function to apply to the failure 
     * @param <R> reduced result type
     *
     * @return reduced result
     */
    @Override
    public <R> R
    reduce(final Function<S, R> successFunction, final Function<F, R> failureFunction) {
        return failureFunction.apply(this.failure);
    }

    /**
     * Consume the failure by executing a unary function.
     *
     * @param successConsumer
     * @param failureConsumer
     */
    @Override
    public void conclude(final Consumer<S> successConsumer, final Consumer<F> failureConsumer) {
        failureConsumer.accept(this.failure);
    }

    /**
     * Create a textual representation of the failed result.
     *
     * @return textual representation
     */
    @Override
    public String toString() {
        return "Failure[" + this.failure.toString() + "]";
    }
}
