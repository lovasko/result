// Copyright (c) 2019 Daniel Lovasko
// All Rights Reserved
//
// Distributed under the terms of the 2-clause BSD License. The full
// license is in the file LICENSE, distributed as part of this software.

package sk.dlo.result.throwing;

/**
 * Unary function that returns an object, whilst possibly throwing a Throwable object.
 *
 * @param <A> argument type
 * @param <R> return type
 */
@FunctionalInterface
public interface ThrowingFunction<A, R> {
    /**
     * Execute the function.
     *
     * @param argument any object
     * @return any object
     * @throws Throwable any Throwable implementation
     */
    R apply(A argument) throws Throwable;
}