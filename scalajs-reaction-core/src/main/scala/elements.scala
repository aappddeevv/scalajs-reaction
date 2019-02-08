// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

// based on commit: reason-react master branch

import collection.mutable
import scala.scalajs.js
import js.|
import js.annotation._
import js.JSConverters._
import js.Dynamic.{literal => lit}
import org.scalajs.dom
import dom.html

object elements {

  /** Mostly untyped React.createElement or replacements e.g. glamor's createElement. */
  //type NativeCreateElement = js.Function3[js.Any|String, js.UndefOr[Nothing], Seq[ReactNode], Unit]

  /** Create a DOM element, which is always a string, lowercase. */
  def createDOMElement(
      n: String,
      props: js.Object | js.Dynamic,
      children: ReactElement*): ReactDOMElement = {
    React.createDOMElement(n, props.asInstanceOf[js.Object])(children: _*)
  }

  /**
    * Create a scala side "element" from a scala side Component definition using
    * React.createElement. If component is a scala-side wrapper around a js
    * component, create the js component. No children are allowed in this
    * function as they come should through the props. This is called "element"
    * instead of "createElement" to make it shorter to type if you are not using
    * JSX. You do *not* use this to create standard html elements like "div" use
    * `createDomElement` and you do not use this to create an element that is a
    * function. This function does not take a wide variety of "components"
    * similar to reactjs's version which can take a ReactClass, function, string,
    * etc.
    */
  def element(
      component: Component,
      key: Option[String] = None,
      ref: Option[Ref[js.Any]] = None): ReactElement = {
    component.jsElementWrapped.toOption match {
      // this func is only put here from interop code
      case Some(func) => func(key, ref)
      case _ =>
        val props = js.Dictionary.empty[Any] // not js.Any! why?
        key.foreach(k => props("key") = k)
        ref.foreach(refcb => props("ref") = refcb)
        props("scalaProps") = component.asInstanceOf[js.Any]
        ReactJS.createElement(component.reactClassInternal, props)
    }
  }

  /** The long-named version of `element` that matches ReasonReact. */
  def createElement(component: Component, key: Option[String] = None, ref: Option[RefCb[js.Any]] = None) =
    element(component, key, ref)

  /** Convert *anything* to what you assert is a js.Any value. Very dangerous. */
  private[this] def toAny(o: scala.Any): js.Any = o.asInstanceOf[js.Any]

  /** Same comment as `toAny`. Very dangerous */
  private[this] def toDynamic(o: scala.Any): js.Dynamic =
    o.asInstanceOf[js.Dynamic]

  /** Stateless component. It only has a render function. */
  def statelessComponent(debugNameArg: String) = {
    new StatelessComponentCake {
      type ProxyType = ProxyLike
      type ComponentType = ComponentLike

      class ProxyLike extends super.ProxyLike {
        val displayName: String = debugNameArg
      }
      val proxy = new ProxyType()
      trait ComponentLike extends super.ComponentLike
      val component = new ComponentType {
        var debugName          = debugNameArg
        var reactClassInternal = reactCreateClass(proxy)
      }
    }
  }

  /** Stateless, with retained props. */
  def statelessComponentWithRetainedProps[RetainedProps](debugNameArg: String) =
    new StatelessComponentWithRetainedPropsCake {
      type RP = RetainedProps
      type ProxyType = ProxyLike
      type ComponentType = ComponentLike

      class ProxyLike extends super.ProxyLike {
        val displayName: String = debugNameArg
      }
      val proxy = new ProxyType()
      trait ComponentLike extends super.ComponentLike
      val component = new ComponentType {
        var debugName          = debugNameArg
        var reactClassInternal = reactCreateClass(proxy)
      }
    }

  /** Stateful. */
  def reducerComponent[TheState, Action](debugNameArg: String) =
    new ReducerComponentCake {
      type S = TheState
      type A = Action
      type ProxyType = ProxyLike
      type ComponentType = ComponentLike

      class ProxyLike extends super.ProxyLike {
        val displayName: String = debugNameArg
      }
      val proxy = new ProxyType()
      trait ComponentLike extends super.ComponentLike
      val component = new ComponentType {
        var debugName          = debugNameArg
        var reactClassInternal = reactCreateClass(proxy)
      }
    }

  /**
    * Scala side version of React.createClass. Since this function call creates
    * the underlying react js class, call this once per component then define a
    * "make" function to derive a new element/component instance with your input
    * props and functions to define behavior. The default render method renders
    * "Not Implemented" and the default reducer performs no state update.
    *
    * The function creates the shim/proxy scala "component" which is just a
    * "record" of the client-level API and calls React.createClass with that
    * proxy. The shim/proxy react lifecycle/mangement functions call the
    * scala-side client visible API. Since you customize what "self" is for the
    * scala client side API, this function also defines how to create the API
    * "self" value from the underlying javascript "this" pointer. The only
    * reason that each component needs its own "react class" proxy is because
    * debugName is unique to a component. Having said that, the number of
    * "classes" is not large in an application.
    */
  def reducerComponentWithRetainedProps[TheState, RetainedProps, Action](debugNameArg: String) =
    new KitchenSinkComponentCake {
      type S         = TheState
      type RP        = RetainedProps
      type A         = Action
      type ProxyType = ProxyLike
      type ComponentType = ComponentLike

      class ProxyLike extends super.ProxyLike {
        val displayName: String = debugNameArg
      }
      val proxy = new ProxyType()
      trait ComponentLike extends super.ComponentLike
      val component = new ComponentType {
        var debugName          = debugNameArg
        var reactClassInternal = reactCreateClass(proxy)
      }
    }

  /** Like nullElement but a Component. */
  val nullComponent = statelessComponent("null").component

  /** Clone a ReactElement and add new props. You should not use this if you can avoid it. */
  def cloneElement(element: ReactElement, props: js.Object): ReactElement =
    ReactJS.cloneElement(element, props.asInstanceOf[js.Dynamic])

  /**
    * Wrap a js side component for scala side usage. You also need to import the
    * react class using standard scala.js import mechanisms and write a "make"
    * function to create your props from "make" parameters. props can be null
    * saving an allocation of an empty object.
    */
  def wrapJsForScala[P <: js.Object](
      reactClass: ReactJsComponent,
      props: P,
      children: ReactNode*): Component = {
    WrapProps.wrapJsForScala(reactClass, props, children: _*)
  }

  /** Create a React.fragment element. */
  object Fragment {

    def make(key: Option[String] = None)(children: ReactNode*) =
      React.createFragment(key, children: _*)

    def make(children: ReactNode*) =
      React.createFragment(None, children: _*)

    /** Preferred creation function. */
    def apply(children: ReactNode*) =
      React.createFragment(None, children: _*)
  }

  /** Strict element. Wraps your root component typically. */
  object StrictMode {
    /** Wrap some children with a Strict component. */
    def apply(children: ReactNode*): ReactNode =
      ReactJS.createElement(ReactJS.StrictMode, null, children:_*)
  }

  /** Catch a thrown js.Promise from the child. Show fallback until the promise is
   * resolved.
   */
  object Suspense {
    def apply(fallback: ReactNode | Null = null, child: ReactElement) =
      ReactJS.createElement(ReactJS.Suspense, lit("fallback" -> fallback.asInstanceOf[js.Any]), child)
  }

  /**
    * Wrap a scala component to be used in js with reactjs. The js props
    * converter is attached to the js side component.  When the converter is
    * called, it should take a js.Object (untyped) and convert it to a
    * component, typically via a call to "make."  jsPropsToScala can use a
    * js.native trait inheriting from js.Object to make picking out the values
    * from the js-side easier, or not, it's up to you. The returned value should
    * be exported from scala-world so that js-world can see it. If you call this
    * multiple times for the same component, the last one wins.
    *
    * Note: jsPropsToScala will appear in reactsj's 'this' because its attached
    * to the prototype of reactClassInternal.
   * 
   * @param c The base component.
   * @param jsPropsToScala Function that returns a component that is typically
   * created by calling "make" or "apply."
    */
  def wrapScalaForJs[P <: js.Object](
      c: Component,
      jsPropsToScala: P => ComponentSpec): ReactJsComponent = {
    require(c != null, "wrapScalaForJs: Component must be non-null.")
    val dyn = c.reactClassInternal.asInstanceOf[js.Dynamic]
    dyn.prototype.jsPropsToScala = jsPropsToScala.asInstanceOf[js.Any]
    c.reactClassInternal.asInstanceOf[ReactJsComponent]
  }

  /**
    * Given a js.Object (or subclass), find a "children" property and assume its
    * an array of ReactNodes. If not found, return an empty js array. This
    * function is used for interop where your scala "make" method takes children
    * as a separate parameter but your props (event JS friendly props) does not
    * contain the children property--it's there if there are children on the js
    * side. This is not needed in scalajs-react because the children are passed
    * and managed explicitly.
    *
    * @todo Seems like this is an expensive call. Can we do better?
    */
  @inline def extractChildren(item: js.UndefOr[js.Object]): js.Array[ReactNode] =
    if (item == null) js.Array() // need this since could be a "defined" null
    else
      item.toOption
        .flatMap(_.asInstanceOf[js.Dictionary[js.Array[ReactNode]]].get("children"))
        .getOrElse[js.Array[ReactNode]](js.Array())

  /** Convert a scala function, T => ReactNode, to a Stateless Functional
   * Component (SFC) that returns a Component. A SFC is considered a react
   * component, defined as a function.  While you could just use a regular scala
   * function but then it would not be a react component underneath the hood and
   * the scala function would be eagerly evaluated. Using a component can save
   * UI update time if the output is expensive to create. Most importantly,
   * using an SFC also enables hooks inside.
   */
  object SFC {
    def apply[T](f: T => ReactNode): T => Component = {
      val c = js.Any.fromFunction1[T,ReactNode](f).asInstanceOf[ReactJsComponent]
      t => wrapJsForScala[js.Object](c, t.asInstanceOf[js.Object])
    }
  }
}
