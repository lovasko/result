// Copyright (c) 2019 Daniel Lovasko
// All Rights Reserved
//
// Distributed under the terms of the 2-clause BSD License. The full
// license is in the file LICENSE, distributed as part of this software.

package sk.dlo.result.throwing;

/**
 * Nullary function that has no arguments that throws a Throwable object.
 *
 * @param <R> return type
 */
@FunctionalInterface
public interface ThrowingSupplier<R> {
    /**
     * Execute the function.
     *
     * @return any object
     * @throws Throwable any Throwable implementation
     */
    R get() throws Throwable;
}
