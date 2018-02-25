// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.JSConverters._
import js.Dynamic.{literal => lit}

object WrapProps {

  /** Curry given basic react js creation args. Allow scala objects as keys on props. */
  private[this] def wrapProps[P <: js.Object](
      reactComponent: ReactJsComponent,
      props: P,
      children: ReactNode*): JsElementWrapped =
    (key: Option[String], ref: Option[RefCb]) => {
      val newProps = js.Dictionary.empty[scala.Any]
      ref.foreach(r => newProps("ref") = r)
      key.foreach(k => newProps("key") = k)
      // avoid a merge, if possible
      val all: js.UndefOr[js.Any] =
        if (props == null && newProps.size > 0) newProps
        else if (props == null && newProps.size == 0) js.undefined
        else if (props != null && newProps.size == 0) props
        else merge[js.Object](props, newProps.asInstanceOf[js.Object])
      //js.Dynamic.global.console.log("wrapped props", all)
      JSReact.createElement(reactComponent, all, children: _*)
    }

  /** Only need 1 interop component, then just copy and change jsElementWrapped. */
  private[this] val dummyInteropComponent =
    elements.statelessComponent("interop")

  import dummyInteropComponent.ops._

  /**
    * Wrap a js authored component for use in scala world.  Props must be a js
    * object hence the type constraint and since this is used for interop into
    * an existing js component, only allow valid js values i.e. no scala object
    * leakage. `reactComponent` must be imported into scala using `@JSImport`.
    */
  def wrapJsForScala[P <: js.Object](
      reactComponent: ReactJsComponent,
      props: P,
      children: ReactNode*): Component = {
    // scala func
    val jsElementWrapped = wrapProps(reactComponent, props, children: _*)
    mergeComponents(lit(),
                    dummyInteropComponent.component,
                    lit("jsElementWrapped" -> jsElementWrapped.asInstanceOf[js.Any]))
  }
}
