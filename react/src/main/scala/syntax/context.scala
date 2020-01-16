// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react

import scala.scalajs.js
import js.annotation._

trait ContextSyntax {

  implicit class ReactContextOps2[T](ctx: ReactContext[T]) {

    /** Create provider that provides an optional value. */
    def Provider(value: T)(children: ReactNode*) =
      context.provider[T](ctx)(value)(children: _*)

    /** Create a consumer that takes an Option[T] */
    def Consumer(f: T => ReactNode, key: Option[String] = None) =
      context.consumer[T](ctx)(js.Any.toFunction1(f))
  }

}
