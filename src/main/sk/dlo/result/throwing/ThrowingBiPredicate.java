// Copyright (c) 2019 Daniel Lovasko
// All Rights Reserved
//
// Distributed under the terms of the 2-clause BSD License. The full
// license is in the file LICENSE, distributed as part of this software.


package sk.dlo.result.throwing;

/**
 * @param <T>
 * @param <U>
 */
@FunctionalInterface
public interface ThrowingBiPredicate<T, U> {
    /**
     * @param t
     * @param u
     *
     * @return
     *
     * @throws Throwable
     */
    boolean test(T t, U u) throws Throwable;
}
