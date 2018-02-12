// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.JSConverters._

object WrapProps {

  /** Curry given basic react js creation args. */
  private[this] def wrapProps[P <: js.Object](reactComponent: ReactJsComponent, props: P,
    children: ReactNode*): JsElementWrapped =
    (key: Option[String], ref: Option[RefCb]) => {
      val newProps = js.Dictionary.empty[scala.Any]
      ref.foreach(r => newProps("ref") = r)
      key.foreach(k => newProps("key") = k)
      val pprops = props.asInstanceOf[js.Dictionary[scala.Any]]
      val totalProps: js.Dictionary[scala.Any] = (pprops ++ newProps).toJSDictionary
      JSReact.createElement(reactComponent, totalProps, children: _*)
    }

  /** Only need 1 interop component, then just copy and change jsElementWrapped. */
  private[this] val dummyInteropComponent =
    elements.basicComponent[Stateless, NoRetainedProps, Actionless]("interop")

  /**
    * Wrap a js authored component for use in scala world.  Props must be a js
    * object hence the type constraint and since this is used for interop into
    * an existing js component, only allow valid js values i.e. no scala object
    * leakage. `reactComponent` must be imported into scala using `@JSImport`.
    */
  def wrapJsForScala[P <: js.Object](reactComponent: ReactJsComponent, props: P,
    children: ReactNode*): Component[Stateless, NoRetainedProps, Actionless, _] = {
    val jsElementWrapped = wrapProps(reactComponent, props, children: _*)
    dummyInteropComponent
      .copy(jsElementWrapped = Some(jsElementWrapped))
      .asInstanceOf[Component[Stateless, NoRetainedProps, Actionless, _]]
  }
}
