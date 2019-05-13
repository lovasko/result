// Copyright (c) 2019 Daniel Lovasko
// All Rights Reserved
//
// Distributed under the terms of the 2-clause BSD License. The full
// license is in the file LICENSE, distributed as part of this software.


package sk.dlo.result.throwing;

/**
 * Binary function
 * @param <A> first argument type
 * @param <B> second argument type
 * @param <R> return type
 */
@FunctionalInterface
public interface ThrowingBiFunction<A, B, R> {
    /**
     * Execute the function.
     *
     * @param firstArgument any object
     * @param secondArgument any object
     *
     * @return any object
     * @throws Throwable any Throwable implementation
     */
    R apply(A firstArgument, B secondArgument) throws Throwable;
}
