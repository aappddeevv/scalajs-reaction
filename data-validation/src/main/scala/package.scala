// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.data

import cats.data._

/** A set of cats related data types for validation specific to our needs that
  * included data driven validation while still supporting multple styles such as
  * DSL or non-DSL.
  *
  * ==Overview==
  * By using cats you can compose on the Result or the Validator depending on
  * your needs. Some additonal methods for defining a Validator are provided using
  * the Tester classes--smart constructors for Validator.
  */
package object validation {

  /** Need to expand to include indexed positions, etc. */
  type Path = Seq[String]

  object Path {
    def apply(segments: String*): Path = segments.toVector
    def empty = Nil
  }

  /** Allow multiple errors if invalid, Validated has rich combinators including
    * sequencing.
    */
  type Result[T] = ValidatedNec[Error, T]

  /** Enable cats combinators including sequencing */
  //type Validator[T] = Kleisli[Result, T, T]
  type Validator[F[_], A, B] = Kleisli[F, A, B]

  /** Convenience type, as if Validated[Errors, T] */
  type Errors = NonEmptyChain[Error]

  // val nullError: Errors = NonEmptyChain.one(Error("is a null"))

  // /** Make an error with a message, no path. */
  // def error(message: String) = NonEmptyChain.one(Error(message))

  // /** Make an error with a message and a path. */
  // def error(message: String, path: String) =
  //   NonEmptyChain.one(Error(message, Path(path)))

  // def error(message: String, path: Path) =
  //   NonEmptyChain.one(Error(message, path))
}
