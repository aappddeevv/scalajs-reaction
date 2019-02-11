// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

package object router extends router.Router {

  sealed trait RedirectMethod

  object RedirectMethod {
    /** Can't go back. */
    case object Replace extends RedirectMethod
    /** Can go back. */
    case object Push extends RedirectMethod
    /** Document reload. */
    case object Reload extends RedirectMethod
  }

}
