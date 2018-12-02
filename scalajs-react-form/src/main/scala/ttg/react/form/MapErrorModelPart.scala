// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
package form

/** Use a simple string map monad. */
trait MapErrorModelPart {
  self: FormControllerBase =>

  object MapErrorModel extends ErrorModelLike {

    type Errors = collection.immutable.Map[String, String]

    val EmptyErrors = collection.immutable.Map[String, String]()

    def count(errors: Errors): Int = errors.size

    def combine(errors: Errors*): Errors = {
      val result = collection.mutable.Map[String, String]()
      for (e <- errors) result ++= e
      result.toMap
    }

    def pure(field: String, message: String): Errors =
      collection.immutable.Map[String, String](field -> message)
  }

  type ErrorModel = MapErrorModel.type
  val errors = MapErrorModel
}
