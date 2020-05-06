package react

import scala.scalajs.js

/** 
 *  Conditional rendering support. Uses by-name parameters to delay creating 
 *  a react node if the condition idicates not to render. 
 */
trait When { 
  /** Render something or return a null element. Render is by name. Could just use fold. */
  def when[T <: Boolean](cond: js.UndefOr[T])(render: => ReactNode)(implicit ev: T =:= Boolean): ReactNode =
    if (cond.getOrElse(false)) render else nullNode

  /** Render something or return a null element. Render is by name. Could just use fold. */
  def when[T <: Boolean](cond: Option[T])(render: => ReactNode)(implicit ev: T =:= Boolean): ReactNode =
    if (cond.getOrElse(false)) render else nullNode

  /** Render something or return a null element. Render is by name. Could just use fold. */
  def when(cond: Boolean)(render: => ReactNode): ReactNode =
    if (cond) render else nullElement

  /** Render something if notcond or return a null element. Render is by name. Could also use fold. */
  def whenNot(cond: Boolean)(render: => ReactNode): ReactNode =
    if (!cond) render else nullElement
  
  /** Render something if not cond or return a null element. Render is by name. Could also use fold. */
  def whenNot[T <: Boolean](cond: js.UndefOr[T])(render: => ReactNode)(implicit ev: T =:= Boolean): ReactNode =
    if (!cond.getOrElse(false)) render else nullElement

  /** Render something if not cond or return a null element. Render is by name. Could also use fold. */
  def whenNot[T <: Boolean](cond: Option[T])(render: => ReactNode)(implicit ev: T =:= Boolean): ReactNode =
    if (!cond.getOrElse(false)) render else nullElement

  def when[T](cond: js.UndefOr[T])(render: => ReactNode): ReactNode =
    if (cond.isDefined) render else nullElement

  def whenNot[T](cond: js.UndefOr[T])(render: => ReactNode): ReactNode =
    if (cond.isEmpty) render else nullElement

}
