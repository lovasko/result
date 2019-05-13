// Copyright (c) 2019 Daniel Lovasko
// All Rights Reserved
//
// Distributed under the terms of the 2-clause BSD License. The full
// license is in the file LICENSE, distributed as part of this software.

package sk.dlo.result.throwing;


/**
 * @param <T>
 */
@FunctionalInterface
public interface ThrowingPredicate<T> {
    /**
     * Produce a decision about an object.
     *
     * @param t any object
     *
     * @return decision about the object
     * @throws Throwable any Throwable implementation
     */
    boolean test(T t) throws Throwable;
}
