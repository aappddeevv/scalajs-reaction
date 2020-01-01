// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package router
package browser

sealed trait Redirect
object Redirect {
    /** Can't go back. */
    case object Replace extends Redirect 
    /** Can go back. */
    case object Push extends Redirect 
    /** Full reload if that is meaninful to a routing source. */
    case object Reload extends Redirect 
  }

