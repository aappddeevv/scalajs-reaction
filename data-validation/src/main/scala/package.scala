/*
 * Copyright (c) 2018 The Trapelo Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package data

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
    def empty                          = Nil
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
