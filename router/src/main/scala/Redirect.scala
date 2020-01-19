
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

