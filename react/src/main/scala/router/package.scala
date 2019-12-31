// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react

package object router {

  object Redirect {
    sealed trait Method
    /** Can't go back. */
    case object Replace extends Method
    /** Can go back. */
    case object Push extends Method
    /** Full reload if that is meaninful to a routing source. */
    case object Reload extends Method
  }

}
