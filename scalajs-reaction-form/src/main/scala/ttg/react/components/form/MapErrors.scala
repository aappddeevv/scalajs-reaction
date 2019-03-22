// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package components
package form

trait MapErrors extends HasErrors.Service {
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

object MapErrors extends MapErrors
