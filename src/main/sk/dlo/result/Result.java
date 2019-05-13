// Copyright (c) 2019 Daniel Lovasko
// All Rights Reserved
//
// Distributed under the terms of the 2-clause BSD License. The full
// license is in the file LICENSE, distributed as part of this software.

package sk.dlo.result;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import sk.dlo.result.throwing.ThrowingBiFunction;
import sk.dlo.result.throwing.ThrowingBiPredicate;
import sk.dlo.result.throwing.ThrowingFunction;
import sk.dlo.result.throwing.ThrowingPredicate;
import sk.dlo.result.throwing.ThrowingSupplier;


/**
 * Result of a computation that is able to represent both success and failure.
 *
 * This class is intended to serve as a betterment to the standard java.util.Optional class, as it
 * enriches the result of the computation with failure information.
 *
 * @param <S> success type
 * @param <F> failure type
 *
 * @author Daniel Lovasko
 * @version 1.0.0
 */
public interface Result<S, F> {
    /**
     * Create a successful result.
     *
     * @param success result of the computation
     * @param <S> success type
     * @param <F> failure type
     *
     * @return new successful result
     */
    static <S, F> Result<S, F> success(final S success) {
        return new Success<>(success);
    }

    /**
     * Create a failed result.
     *
     * @param failure description of the computation failure
     * @param <S> success type
     * @param <F> failure type
     *
     * @return new failed result
     */
    static <S, F> Result<S, F> failure(final F failure) {
        return new Failure<>(failure);
    }

    /**
     * Execute a computation and wrap the result.
     *
     * The computation is considered to be failed if an exception occurs. Otherwise, the returning
     * value is presumed to be a successful result. This method is able to catch *unchecked*
     * exceptions, but will not compile if used for a function that throws a checked exception.
     *
     * @param argument argument to the function
     * @param function function to execute
     * @param <R> function return type
     * @param <A> function argument type
     *
     * @return wrapped result
     */
    static <R, A>
    Result<R, ? extends Throwable> wrap(final A argument, final Function<A, R> function) {
        try {
            R returnValue = function.apply(argument);
            return Result.success(returnValue);
        } catch (Throwable throwable) {
            return Result.failure(throwable);
        }
    }

    /**
     * Execute a computation and wrap the result.
     *
     * The computation is considered to be failed if an exception occurs. Otherwise, the returning
     * value is presumed to be a successful result. This method is able to catch *checked* and
     * *unchecked* exceptions.
     *
     * @param argument argument to the function
     * @param function function to execute
     * @param <R> function return type
     * @param <A> function argument type
     *
     * @return wrapped result
     */
    static <R, A>
    Result<R, ? extends Throwable> wrap(final A argument, final ThrowingFunction<A, R> function) {
        try {
            R returnValue = function.apply(argument);
            return Result.success(returnValue);
        } catch (Throwable throwable) {
            return Result.failure(throwable);
        }
    }

    /**
     * Execute a computation and wrap the result.
     *
     * @param supplier supplier to execute
     * @param <R> supplier return type
     *
     * @return wrapped result
     */
    static <R> Result<R, ? extends Throwable> wrap(final Supplier<R> supplier) {
        try {
            R returnValue = supplier.get();
            return Result.success(returnValue);
        } catch (Throwable throwable) {
            return Result.failure(throwable);
        }
    }

    /**
     * Execute a computation and wrap the result.
     *
     * @param supplier supplier to execute
     * @param <R> supplier return type
     *
     * @return wrapped result
     */
    static <R> Result<R, ? extends Throwable> wrap(final ThrowingSupplier<R> supplier) {
        try {
            R returnValue = supplier.get();
            return Result.success(returnValue);
        } catch (Throwable throwable) {
            return Result.failure(throwable);
        }
    }

    /**
     * Test a object with a predicate and wrap the result.
     * 
     * @param argument 
     * @param predicate
     * @param <A> predicate argument type
     *
     * @return wrapped result
     */
    static <A> Result<Boolean, ? extends Throwable>
    wrap(final A argument, final Predicate<A> predicate) {
        try {
            Boolean value = predicate.test(argument);
            return Result.success(value);
        } catch (Throwable throwable) {
            return Result.failure(throwable);
        }
    }

    /**
     * @param object
     * @param predicate
     * @param <V>
     *
     * @return wrapped result
     */
    static <V> Result<Boolean, ? extends Throwable>
    wrap(final V object, final ThrowingPredicate<V> predicate) {
        try {
            Boolean value = predicate.test(object);
            return Result.success(value);
        } catch (Throwable throwable) {
            return Result.failure(throwable);
        }
    }

    /**
     * Execute a computation and wrap the result.
     *
     * @param firstArgument first argument of the function
     * @param secondArgument second argument of the function
     * @param biFunction function to execute
     * @param <A> first argument type
     * @param <B> second argument type
     * @param <R> function return type
     *
     * @return wrapped result
     */
    static <A, B, R> Result<R, ? extends Throwable>
    wrap(final A firstArgument, final B secondArgument, final BiFunction<A, B, R> biFunction) {
        try {
            R returnValue = biFunction.apply(firstArgument, secondArgument);
            return Result.success(returnValue);
        } catch (Throwable throwable) {
            return Result.failure(throwable);
        }
    }

    /**
     * @param firstArgument first argument of the function
     * @param secondArgument second argument of the function
     * @param biFunction function to execute
     * @param <A> first argument type
     * @param <B> second argument type
     * @param <R> return type
     *
     * @return wrapped result
     */
    static <A, B, R> Result<R, ? extends Throwable>
    wrap(final A firstArgument, final B secondArgument, final ThrowingBiFunction<A, B, R> biFunction) {
        try {
            R returnValue = biFunction.apply(firstArgument, secondArgument);
            return Result.success(returnValue);
        } catch (Throwable throwable) {
            return Result.failure(throwable);
        }
    }

    /**
     * @param firstArgument first predicate argument
     * @param secondArgument second predicate argument
     * @param biPredicate predicate about two arguments
     * @param <A> first argument type
     * @param <B> second argument type
     *
     * @return wrapped result
     */
    static <A, B> Result<Boolean, ? extends Throwable>
    wrap(final A firstArgument, final B secondArgument, final BiPredicate<A, B> biPredicate) {
        try {
            Boolean returnValue = biPredicate.test(firstArgument, secondArgument);
            return Result.success(returnValue);
        } catch (Throwable throwable) {
            return Result.failure(throwable);
        }
    }

    /**
     * 
     * @param firstArgument first predicate argument
     * @param secondArgument second predicate argument
     * @param biPredicate predicate about two arguments
     * @param <A> first argument type
     * @param <B> second argument type
     *
     * @return wrapped result
     */
    static <A, B> Result<Boolean, ? extends Throwable>
    wrap(final A firstArgument, final B secondArgument, final ThrowingBiPredicate<A, B> biPredicate) {
        try {
            Boolean returnValue = biPredicate.test(firstArgument, secondArgument);
            return Result.success(returnValue);
        } catch (Throwable throwable) {
            return Result.failure(throwable);
        }
    }

    /**
     * Test whether the result was a success.
     *
     * @return presence of a success 
     */
    boolean isSuccess();

    /**
     * Test whether the result was a failure.
     *
     * @return presence of a failure
     */
    boolean isFailure();

    /**
     * Apply a function to the successful result.
     *
     * In case that the result represents failure, this method does not affect the result.
     *
     * @param function function to apply to the success 
     * @param <Z> success type
     *
     * @return result with possibly altered success 
     */
    <Z> Result<Z, F> transformSuccess(final Function<S, Z> successFunction);

    /**
     * Apply a function to the failed result.
     *
     * In case that the result represents success, this method does not affect the result.
     *
     * @param function function to apply to the failure
     * @param <G> failure type
     *
     * @return result with possibly altered failure 
     */
    <G> Result<S, G> transformFailure(final Function<F, G> function);

    /**
     * Sequentially apply a function to the result, if it was successful.
     *
     * This method can be used to chain a set of functions, whilst propagating the first failure
     * that occurs to the end of the chain. This method can be used to drive control flow of a
     * program.
     *
     * Examples:
     *   Function<Integer, Result<Integer, Integer>> timesTwo = (x -> Result.success(x * x));
     *   Function<Integer, Result<Integer, Integer>> timesOne = (x -> Result.failure(x));
     *   Result.success(2).andThen(timesTwo).andThen(timesTwo); // Result.success(8)
     *   Result.success(2).andThen(timesTwo).andThen(timesOne); // Result.failure(4)
     *   Result.success(2).andThen(timesOne).andThen(timesOne); // Result.failure(2)
     *   Result.success(2).andThen(timesOne).andThen(timesTwo); // Result.failure(2)
     *   Result.failure(3).andThen(timesTwo).andThen(timesTwo); // Result.failure(3)
     *   Result.failure(3).andThen(timesTwo).andThen(timesOne); // Result.failure(3)
     *   Result.failure(3).andThen(timesOne).andThen(timesOne); // Result.failure(3)
     *   Result.failure(3).andThen(timesOne).andThen(timesTwo); // Result.failure(3)
     *
     * @param function function to apply to the success 
     * @param <Z> success type
     *
     * @return result after the possible transformation
     */
    <Z> Result<Z, F> andThen(final Function<S, Result<Z, F>> function);

    /**
     * Combine two results similarly to the logical AND operation.
     *
     * This operation is not commutative, as it takes ordering of the results into account. The
     * first failure has greater priority, while the order is opposite for successful results. The
     * following table lists all scenarios:
     *   success(X) and success(Y) = success(Y)
     *   success(X) and failure(Y) = failure(Y)
     *   failure(X) and success(Y) = failure(X)
     *   failure(X) and failure(Y) = failure(X)
     *
     * @param result another result
     * @param <Z> success type
     *
     * @return combination of results
     */
    <Z> Result<Z, F> and(final Result<Z, F> result);

    /**
     * Sequentially apply a function to the result, if it represented a failure.
     *
     * This method can be used to chain a set of functions, whilst being able to recover from a
     * failure. This method can be used to drive control flow of a program.
     *
     * Examples:
     *   Function<Integer, Result<Integer, Integer>> timesTwo = (x -> Result.success(x * x));
     *   Function<Integer, Result<Integer, Integer>> timesOne = (x -> Result.failure(x));
     *   Result.success(2).orElse(timesTwo).orElse(timesTwo); // Result.success(8)
     *   Result.success(2).orElse(timesTwo).orElse(timesOne); // Result.failure(4)
     *   Result.success(2).orElse(timesOne).orElse(timesOne); // Result.failure(2)
     *   Result.success(2).orElse(timesOne).orElse(timesTwo); // Result.failure(2)
     *   Result.failure(3).orElse(timesTwo).orElse(timesTwo); // Result.success(8)
     *   Result.failure(3).orElse(timesTwo).orElse(timesOne); // Result.failure(4)
     *   Result.failure(3).orElse(timesOne).orElse(timesOne); // Result.failure(2)
     *   Result.failure(3).orElse(timesOne).orElse(timesTwo); // Result.failure(2)
     *
     * @param function function to apply to the failure 
     * @param <G> another failure type
     *
     * @return new result
     */
    <G> Result<S, G> orElse(final Function<F, Result<S, G>> function);

    /**
     * Combine two results similarly to the logical OR operation.
     *
     * This operation is not commutative, as it takes ordering of the results into account. The
     * latter failure has greater priority, while the order is opposite for values. The following
     * table lists all scenarios:
     *   success(X) or success(Y) = success(X)
     *   success(X) or failure(Y) = success(X)
     *   failure(X) or success(Y) = success(Y)
     *   failure(X) or failure(Y) = failure(Y)
     *
     * @param result another result
     * @param <G> failure type
     *
     * @return combination of results
     */
    <G> Result<S, G> or(final Result<S, G> result);

    /**
     * Reduce success or failure into a single unified representation of the result.
     *
     * This method can be used as a safe way to perform control flow, as the appropriate function
     * is chosen based on the type of the result. This in effect replaces an if statement, with the
     * added benefit of forcing both branches to be handled. It is strongly suggested to use this
     * method to unwrap the actual objects, as it prevents the null value to appear in the code.
     *
     * @param successFunction function to apply to the success
     * @param failureFunction function to apply to the failure
     * @param <R> reduced result type
     */
    <R> R reduce(final Function<S, R> successFunction, final Function<F, R> failureFunction);

    /**
     * Finalize a result chain by consuming either success or failure. 
     *
     * This method can be used as a safe way to perform control flow, as the appropriate consumer 
     * is chosen based on the type of the result. This in effect replaces an if statement, with the
     * added benefit of forcing both branches to be handled. It is strongly suggested to use this
     * method to unwrap the actual objects, as it prevents the null value to appear in the code.
     *
     * @param successConsumer consumer that accepts the success
     * @param failureConsumer consumer that accepts the failure
     */
    void conclude(final Consumer<S> successConsumer, final Consumer<F> failureConsumer);
}
