// Copyright (c) 2019 Daniel Lovasko
// All Rights Reserved
//
// Distributed under the terms of the 2-clause BSD License. The full
// license is in the file LICENSE, distributed as part of this software.

package sk.dlo.result;

import java.util.function.Consumer;
import java.util.function.Function;


/**
 * Successful result of a computation.
 *
 * @param <S> success type
 * @param <F> failure type
 *
 * @author Daniel Lovasko
 */
public final class Success<S, F> implements Result<S, F> {
    /**
     * Storage of the result of a successful computation.
     */
    private final S success;

    /**
     * Initialize the successful result.
     *
     * This function is package-scoped by design, as instances of this class
     * shall not be created by users directly.
     *
     * @param success
     */
    Success(final S success) {
        this.success = success;
    }

    /**
     * Always true, as this class signifies the success of a computation.
     *
     * @return true
     */
    @Override
    public boolean isSuccess() {
        return true;
    }

    /**
     * Always false, as this class signifies the success of a computation.
     *
     * @return false
     */
    @Override
    public boolean isFailure() {
        return false;
    }

    /**
     * Apply a function to the successful result.
     *
     * @param function function to apply to the success 
     * @param <Z> success type
     *
     * @return new transformed result
     */
    @Override
    public <Z> Result<Z, F> transformSuccess(final Function<S, Z> function) {
        return Result.success(function.apply(this.success));
    }

    /**
     * This method has no effect, as this class does not store a failure.
     *
     * @param function function to apply to the failure (unused)
     * @param <G> failure type
     *
     * @return new identical result
     */
    @Override
    public <G> Result<S, G> transformFailure(final Function<F, G> function) {
        return Result.success(this.success);
    }

    /**
     * Sequentially compose a function on top of the current successful result.
     *
     * @param function function to apply to the success 
     * @param <Z> success type
     *
     * @return new result
     */
    @Override
    public <Z> Result<Z, F> andThen(final Function<S, Result<Z, F>> function) {
        return function.apply(this.success);
    }

    /**
     * Combine two sequential results.
     *
     * The following table details the behaviour:
     *   success(X) and success(Y) = success(Y)
     *   success(X) and failure(Y) = failure(Y)
     *
     * @param result subsequent result
     * @param <Z> success type
     *
     * @return subsequent result
     */
    @Override
    public <Z> Result<Z, F> and(final Result<Z, F> result) {
        return result;
    }

    /**
     * Propagate the current success onwards, without executing the provided function.
     *
     * @param function function to apply to the failure (unused)
     * @param <G> failure type
     *
     * @return new identical result
     */
    @Override
    public <G> Result<S, G> orElse(final Function<F, Result<S, G>> function) {
        return Result.success(this.success);
    }

    /**
     * Propagate the current success onwards.
     *
     * This method adheres to the following table:
     *   success(X) or success(Y) = success(X)
     *   success(X) or failure(Y) = success(X)
     *
     * @param result another result
     * @param <G> failure type
     *
     * @return new identical result
     */
    @Override
    public <G> Result<S, G> or(final Result<S, G> result) {
        return Result.success(this.success);
    }

    /**
     * Reduce the result by applying a function to the store successful result.
     *
     * @param successFunction function to apply to the success 
     * @param failureFunction function to apply to the failure (unused)
     * @param <R> reduced type
     *
     * @return transformed successful result
     */
    @Override
    public <R> R
    reduce(final Function<S, R> successFunction, final Function<F, R> failureFunction) {
        return successFunction.apply(this.success);
    }

    /**
     * Consume the successful result by executing a unary function.
     *
     * @param successConsumer consumer of the success
     * @param failureConsumer consumer of the failure (unused)
     */
    @Override
    public void
    conclude(final Consumer<S> successConsumer, final Consumer<F> failureConsumer) {
        successConsumer.accept(this.success);
    }

    /**
     * Create the textual representation of the successful result.
     *
     * @return textual representation
     */
    @Override
    public String toString() {
        return "Success[" + this.success.toString() + "]";
    }
}
