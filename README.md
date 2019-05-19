# sk.dlo.result
The `result` library provides a robust framework for error handling with the
aim of eliminating exceptions and the `null` value.

## Introduction
This library intends to change how a program represents and processes
computation results - both success and failure. The cardinal aim is to ensure
that all potential failures are readily captured during compile-time and that
the programmer is assisted in addressing all potential results of a
combination of computations.

### Why not exceptions?
Exceptions are a non-local form of program control, which strongly tie
together two places in the code which might be completely unrelated - the
place where the exception was thrown and where it was caught. This makes
reasoning about the code correctness harder. Moreover, exceptions are designed
to be used in cases where a situation isn't _exceptional_ at all, such as user
input errors or network transmission issues. Runtime exceptions can occur at
any time without the programmer being able to verify that these were handled -
something that the `result` library finds unacceptable for production-grade
code.

The `result` library enables local access to the result of the computation and
inspires clear representation of both success and failure cases by requiring
both types in the generic type arguments.

### Why avoid `null`?
The `null` value is used to represent many facts or circumstances. This leads
to issues for readers of the code as to what particular purpose the value
is being used. An example might be a database search, where the result `null`
might represent both unavailability of the requested key, or the inability to
connect to the remote database system. Moreover, a production-grade code shall
not fail due to unexpected `NullPointerException`. This can be partly achieved
by not returning `null`, but also needs to be accompanied by ever-present
checking if each operation in a chain has not returned `null`. This renders
the code to be deeply nested and leads to programmer errors due to unhandled
edge-cases.

The `result` library offers a simple way how to define various types of
failures for a computation, whilst ensuring that a chain of operations is
handled correctly. All this is done in a succinct manner. 

## Example
The following code attempts to connect to either the primary or secondary
instance of a service, request data if either of the connections was
successful, format and print the results to the user. An error is logged in
case any of these steps failed. 

```java
connectToPrimary()
  .orElse(connectToSecondary)
  .andThen(selectData)
  .andThen(formatDataTable)
  .conclude(printDataTable, logError);
```

It is crucial to recognise that this approach combines multiple design
patterns: alternative data sources with `orElse`, lazy execution of chained 
operations with `andThen`, and compulsory confrontation of both success and
failure of the computation with `conclude` .

The assumption made is that methods `connectToPrimary`,
`connectToSecondary`, `selectData`, and `formatDataTable` return
type-compatible `Result` instances. Furthermore, functions `printDataTable`
and `logError` are expected to work directly on non-null values, whilst
obeying the requirement that any failure is non-reportable or non-actionable,
and thus can be disregarded.

## API
### Types
The central type of the `result` library is the `Result<S,F>` interface, which
is implemented by two package-scoped classes. All users of the library are
expected to create instances of the `Result` interface, initialized by the
provided static methods.

### Static Methods
The `Result` interface presents two public static methods - `success` and
`failure` - intended to create `Result` objects directly from plain Java
objects.

The `Result` interface also offers the static method `wrap`, which provides
the means to execute a computation and convert the return value (or any
potential exceptions) into a `Result` object. The method is overloaded to
support execution of the following computations: `BiFunction`, `BiPredicate`,
`Function`, `Predicate`, and `Supplier`; along with a `Throwing` equivalent
for each one of the listed.

### Methods
The `Result` interface defines a rich set of methods that can be used to
combine, compose, inspect, and process its state.

#### Combine
TODO

#### Compose
TODO

#### Inspect
TODO

#### Process
TODO

## Inspiration
The `Result` type is heaving inspired by the `Either` monad from the standard
library of the Haskell language. Whilst the ideas in the `Either` class are of
great value, the naming of `Left` and `Right` cases did not seem to bring
forth the correct intuition and were thus renamed to `failure` and `success`,
respectively.

## License
The `result` project is licensed under the terms of the [2-cause BSD
license](LICENSE).

## Author
Daniel Lovasko <daniel.lovasko@gmail.com>
