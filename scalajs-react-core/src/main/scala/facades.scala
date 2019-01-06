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

trait ErrorInfo extends js.Object {
  val componentStack: js.UndefOr[String] = js.undefined
}

/**
  * Scala-side react component.  This trait only includes the parts of a
  * component not dependent on specific types (see the cake layers for the
  * rest). An instance of ComponentSpec is produced as a basic template that is
  * paired with an instance of a Proxy. For each cake instance (1 to 1 with each
  * scalajs-react component) there is a proxy and an instance of ComponentSpec.
  * This single ComponentSpec is then "copied" with the specific desired
  * behavior a specific rendering method.
  */
trait ComponentSpec extends js.Object {
  /**
    * The result of calling React.createClass(proxy) is attached here.
    */
  var reactClassInternal: ReactClass

  /** Needed potentially for some context integration. Remove at some point in react 17. */
  var contextTypes: js.UndefOr[js.Object] = js.undefined

  /** If ComponentSpec is just wrapping a reactjs component, the wrapped component
    * is attached here.
    */
  var jsElementWrapped: js.UndefOr[JsElementWrapped] = js.undefined

  /** The debug name of the component. Mandatory in scalajs-react. This is also
   * kept in the scala side component.
   */
  var debugName: String
}

/**
  * The scala-side view of reactjs "this.props". Used internally to call public
  * API methods. If you inspected a component in javascript, you would see this
  * structure for the props.
  */
@js.native
protected trait JsComponentThisProps extends js.Object {
  /**
    * The scala side interop ComponentSpec is attached here in the props.
    */
  val scalaProps: js.UndefOr[ComponentSpec] = js.native
}

/**
  * The scala-side view of reactjs "this". Used internally to access reactjs
  * state and props.
  */
@js.native
protected trait JsComponentThis[State] extends js.Object {
  /** this.state */
  def state: State

  /** this.setState */
  def setState(
      set: js.Function1[State, State | Null],
      onComplete: js.Function0[Unit] = () => ()): Unit

  /** this.props */
  def props: JsComponentThisProps

  /**
    * Convert raw js this.props to a scala side object. This is only used when
    * wrapping a non-scala (js side defined) component. It is attached to the
    * objects prototype so its present on all instances of a component.
    */
  def jsPropsToScala: js.UndefOr[js.Object => ComponentSpec]

  ///** Our array of unmounts, if there are any. */
  //var onUnmounts: js.UndefOr[js.Array[OnUnmount]] = js.native
}

/**
  * The lowest level of cake base that brings together the reactjs proxy and the
  * scala.js ComponentSpec. Each layer of the cake adds functionality specific
  * to that layer e.g. a stateful layer will provide a public API to set the
  * initial state. There is one instance of the cake for each component
  * "type/class" but there is one instance of ComponentSpec for each component
  * instance. The key part at runtime is the (Proxy, ComponentSpec) pair inside
  * the cake layer. The pair acts as a "template." Proxy handles the reactjs
  * world, calls methods defined in the cake and the cake calls methods on a
  * copy of the Component. An instance of the cake holds the types and an
  * instance of the proxy and component "emplates" together and becomes a
  * factory for creating "components" customized e.g. a specific render
  * method. The cake (and the single instances of the Proxy and Component
  * "templates") is the equivalent of a react class in ES5 terminology.
  *
  * There are several types with the base layer. There are also multiple
  * variations of a specific type such as "Self", which represents the
  * javascript "self" concept but in the scala.js world. The non-native js trait
  * representing the react component on the scala.js side is based on
  * `ComponentSpec`. `WithMethods` provides the public API for adding specific
  * functions for rendering, changing state, etc., and only used in
  * "building/specifying" creating the ComponentSpec. `Proxy` is the non-native
  * js trait object provided by scala.js to the reactjs system that represents
  * the javascript side of the component. Methods in the proxy are forwarded to
  * the cake, the cake calls methods in a copy of the `Component` stored in the
  * reactjs props. `Ops` is the object that can be imported by clients to bring
  * the cake types and values (the plain Component) into scope for use in
  * building the final Component. For example, importing Ops brings
  * `WithMethods` into scope.
  *
  * The cake method of creating objects is less common today in scala as it
  * creates a rigid structure of types all interdependent on each other. However,
  * this is exactly what's needed in scalajs-react.
  *
  */
trait CakeBase { cake =>

  /** Component self for most clent API methods which may or may not contain state. */
  type Self <: SelfLike

  /**
    * Self for methods. At its simplest, you can execute callbacks. Subtraits
    * may create additional "Self"s for use in client API methods.
    */
  trait SelfLike extends SelfForUnmountLike {

    /**
      * You should need not need to use this in scala.js vs reason-react, given
      * that we have a separate Self type...or do we? I'm not sure when to use
      * this in scalajs. It is different than ReasonReact in that you should use
      * `_ => handle(func)` since `handle` returns Unit and cannot be used
      * directly as the argument expecting a function. You will need to curry
      * `func` down to a function taking a single `Self` argument. Self comes
      * from importing `ops` from your component e.g. `import c.ops._`. Perhaps
      * you use it when you want a callback to always have a fresh "self" object
      * vs binding to the one that was mostly current when the callback was
      * configured in a render method.
      */
    def handle(cb: Self => Unit): Unit

    /**
      * Add a callback when unmount occurs. This method is obviously *not*
      * present during the unmount method.
      */
    def onUnmount(cb: OnUnmount): Unit
  }

  /**
    * Self used for component.willUnmount, for example "handle/send" does not
    * work during willUnmount.
    */
  type SelfForUnmount <: SelfForUnmountLike

  trait SelfForUnmountLike {
    private[ttg] var ptr: js.Any
  }

  /** reactjs this: Only the parts that we need or the interop. */
  type ThisSelf = JsComponentThis[State]

  /**
    * The basic scala component that requires methods to customize its behavior.
    * This base component is used as a "template" to create each component
    * instance (= element). The Proxy calls methods on this object. The
    * scala-side component is placed in the reactjs component's props. Component
    * will call methods provided by the user, specific to the component, as well
    * as lifecycle methods defined in the cake layers which are specific to that
    * the cake layer.
    */
  val component: ComponentType
  type ComponentType <: ComponentLike

  /** The signature of DidMount varies by cake layer. */
  type DidMount

  /**
    * The scala.js component is a javascript object (non-native trait) with all
    * values optional. A client does not create a ComponentSpec directly,
    * instead the WithMethods trait is used and the WithMethods are merged into
    * a final ComponentSpec. When the scaal-side instance e.g. a stateless cake
    * instance, is called by the proxy (see the Proxy type), the cake layer in
    * turn locates the Component instance and calls the Component's methods like
    * those below.
    */
  protected trait ComponentLike extends ComponentSpec {
    var render: js.UndefOr[Self => ReactNode] = js.undefined

    var subscriptions: js.UndefOr[Self => js.Array[Subscription]] = js.undefined
    /** This may be set to undefined if there are no unmounts registered. */
    var onUnmounts: js.UndefOr[js.Array[OnUnmount]]               = js.undefined
    var didCatch: js.UndefOr[(Self, js.Error, ErrorInfo) => Unit] = js.undefined

    var willUpdate: js.UndefOr[OldNewSelf[Self] => Unit]      = js.undefined
    var didUpdate: js.UndefOr[OldNewSelf[Self] => Unit]       = js.undefined
    var willUnmount: js.UndefOr[SelfForUnmount => Unit]       = js.undefined
    var shouldUpdate: js.UndefOr[OldNewSelf[Self] => Boolean] = js.undefined
    var didMount: js.UndefOr[DidMount]                        = js.undefined
  }

  /**
    * reactjs this.state: The object stored in reactjs this.state. For stateless
    * components, there is obviously no scala state to store. (reason-react
    * TotalState).
    */
  protected type State <: StateLike

  /**
    * Keep it as js-object so you can still manipulate it from javascript. In
    * the base case we are not storing any real state of course because the base
    * case is stateless.
    */
  protected trait StateLike extends js.Object

  /**
    * Methods exposed for the public component API. Clients add their own
    * methods to this object and then it is merged into the the
    * ComponentSpec. Copying is via a javascript "Object.assign" like
    * method. WithMethods should be read "create a component with these
    * methods...". Methods on WithMethods are made optional or not depending on
    * the type of component being created. WithMethods is the typesafe way to
    * create a Component. WithMethods support building components with
    * customized behaviour, e.g. define a render method, but are not used
    * otherwise.
    */
  type WithMethods <: WithMethodsLike

  /**
    * The methods on the base layer WithMethods are available for all components
    * regardless of the type of component being created.
    */
  trait WithMethodsLike extends js.Object {
    val render: Self => ReactNode
    var subscriptions: js.UndefOr[Self => js.Array[Subscription]] = js.undefined
    var didCatch: js.UndefOr[(Self, js.Error, ErrorInfo) => Unit] = js.undefined

    //var shouldUpdate: js.UndefOr[OldNewSelf[Self] => Boolean] = js.undefined
    var willUpdate: js.UndefOr[OldNewSelf[Self] => Unit]      = js.undefined
    var didUpdate: js.UndefOr[OldNewSelf[Self] => Unit]       = js.undefined
    var willUnmount: js.UndefOr[SelfForUnmount => Unit]       = js.undefined
    var shouldUpdate: js.UndefOr[OldNewSelf[Self] => Boolean] = js.undefined

    var didMount: js.UndefOr[DidMount] = js.undefined
  }

  /**
    * Helpers exposed when the client imports them via `import
    * myComponent.ops._`.
    */
  val ops: Ops

  type Ops <: OpsLike
  trait OpsLike {
    type Self           = cake.Self
    type SelfForUnmount = cake.SelfForUnmount
    type ComponentType  = cake.ComponentType

    /** Use this as `myComponent.copy(new methods { ... })`. */
    type methods = cake.WithMethods

    /**
      * Alias for this component cake's `copy()` method but without the need for the val.
      * @example {{
      * val c = statelessComponent("MyComponent")
      * import c.ops._
      * copyWith(new methods { ... }) // instead of c.copy(new methods{...})
      *  }}
      */
    def copyWith(newMethods: methods) = cake.copy(newMethods)
  }

  /**
    * Client API to copy this component and add methods that customize render
    * behavioro. The cost of the copy/merge is the same as that for raw
    * javascript.
    * @example {{{
    * val c = statelessComponent("MyComponent")
    * import c.ops._
    * def make(...) = c.copy(with methods { ... })
    * }}}
    */
  def copy(methods: WithMethods): ComponentType = {
    mergeComponents[ComponentType](lit(), component, methods.asInstanceOf[js.Dynamic])
  }

  /** Wrap this component for use in javascript. The jsPropsToScala should return
   * a component based on calling "make" or "apply".
   */
  def wrapScalaForJs[P <: js.Object](jsPropsToScala: P => ComponentType): ReactJsComponent =
    elements.wrapScalaForJs(component, jsPropsToScala)

  /**
    * The type of object used in React.createClass that the javascript side
    * consumes.
    */
  protected type ProxyType <: ProxyLike

  /**
    * The proxy object for this component. Each cake instance defines a single
    * proxy that proxies all Components created from that cake. The methods on
    * Proxy call the cake layer methods which then call the specific customized
    * Component methods.
    */
  protected val proxy: ProxyType

  /**
    * If the props arg contains a `scalaProps` member return it
    * directly. Otherwise, convert the entire js props object using
    * jsPropsToScala. The scala-side proxy uses this to function extract out
    * "scala props" stashed in js-side props. js-side props are always a js
    * object.
    */
  def convertPropsIfTheyAreFromJs(
      props: JsComponentThisProps,
      jsPropsToScala: Option[js.Object => Component],
      debugName: String): Component = {
    val scalaProps: Option[Component] = props.scalaProps.toOption
    (scalaProps, jsPropsToScala) match {
      // order is important, check for scalaProps first
      case (Some(jsprops), _)         => jsprops
      case (None, Some(toScalaProps)) => toScalaProps(props)
      case (None, None)               =>
        //js.Dynamic.global.console.log("object in error", props.asInstanceOf[js.Any])
        throw new IllegalStateException(
          s"""A JS component called scala component ${Option(debugName)
            .getOrElse("<no name>")} """ +
            "which does not implement the JS->Scala React props conversion.")
    }
  }

  /**
    * Helper to extract component from reactjs this.props. This is one of the very few
    * places we perform a cast.
    */
  protected def convertProps(
      props: JsComponentThisProps,
      convert: js.UndefOr[js.Object => ComponentSpec],
      debugName: String = null): ComponentType =
    convertPropsIfTheyAreFromJs(props, convert.toOption, debugName).asInstanceOf[ComponentType]

  /** Make a self or most of the API calls, except, for example, unmounting. */
  protected def mkSelf(
      thisJs: ThisSelf,
      jsState: State,
      component: ComponentType,
      displayName: String = null): Self

  /** Make a self for unmounting which is different than most general "self". */
  protected def mkSelfForUnmount(
      thisJs: ThisSelf,
      jsState: State,
      component: ComponentType,
      displayName: String = null): SelfForUnmount

  /** Helper function to mutate thisJs to add Onmount callbacks to ThisSelf. */
  //protected def addOnUnmounts(thisJs: ThisSelf, onUnmounts: js.Array[OnUnmount]): Unit = {
  protected def addOnUnmounts(c: ComponentType, onUnmounts: js.Array[OnUnmount]): Unit = {
    val newUnmounts =
      c.onUnmounts
      //.map(_.concat(onUnmounts))
        .map(_ ++ onUnmounts)
        .getOrElse(onUnmounts)
    c.onUnmounts = if (newUnmounts.length > 0) newUnmounts else js.undefined
  }

  /**
    * Common didMount functionality. Runs subscriptions and return the OnMount
    * effects to be run during component unmount if the caller wants those.
    * `OnUnmount` callbacks are stored in the javascript component directly.
    * `mkSelf` is called so it should be the correct one created for the
    * layer of the cake you are in.
    */
  protected def _componentDidMountCommon(displayName: String)(
      thisJs: ThisSelf): (ComponentType, Self, js.Array[OnUnmount]) = {
    val component =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    val self = mkSelf(thisJs, thisJs.state, component, displayName)
    // call subscriptions
    val onUnmounts: js.Array[OnUnmount] =
      component.subscriptions.map(_(self).map(_())).getOrElse(js.Array())
    // remove subscriptions from component, they were just run!
    component.subscriptions = js.undefined // it this right, what if we remount?
    //addOnUnmounts(thisJs, onUnmounts)
    addOnUnmounts(component, onUnmounts)
    (component, self, onUnmounts)
  }

  /** Call a callback immediately with the most "recent" reactjs this. */
  protected def handleMethod(
      thisJs: ThisSelf,
      cb: Self => Unit,
      displayName: String = null): Unit = {
    //println(s"$displayName:CreateClassOpts.handleMethod: $cb")
    val component =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    //val curState = thisJs.state // this is the actual react js state, which is TotalState
    //val curScalaState = curState.scalaState
    // was component.retainedProps
    val self = mkSelf(thisJs, thisJs.state, component)
    cb(self)
  }

  /**
    * The basic unmount runs the unsubscribes.
    */
  @inline protected def _componentWillUnmount(displayName: String)(thisJs: ThisSelf): Unit = {
    val component =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    component.willUnmount.foreach { wu =>
      val self = mkSelfForUnmount(thisJs, thisJs.state, component, displayName)
      wu(self)
    }
    // run unmount callbacks if they exist
    component.onUnmounts.foreach(_.foreach(_()))
  }

  @inline protected def _componentDidCatch(
      displayName: String)(thisJs: ThisSelf, error: js.Error, errorInfo: ErrorInfo): Unit = {
    val component = convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    component.didCatch.fold(
      // rewrap and throw
      throw new js.JavaScriptException(error)
    ) { dc =>
      val self = mkSelf(thisJs, thisJs.state, component)
      dc(self, error, errorInfo)
    }
  }

  @inline protected def _render(displayName: String)(thisJs: ThisSelf): ReactNode = {
    val component =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    val self = mkSelf(thisJs, thisJs.state, component)
    component.render.map(_(self)).getOrElse(null)
  }

  @inline protected def _componentWillUpdate(displayName: String)(
      thisJs: ThisSelf,
      nextProps: JsComponentThisProps,
      nextState: State): Unit = {
    //println(s"$debugName:CreateClassOpts.componentWillUpdate")
    val newComponent =
      convertProps(nextProps, thisJs.jsPropsToScala, displayName)
    newComponent.willUpdate.foreach { wu =>
      val oldJsProps = thisJs.props
      /* Avoid converting again the props that are just the same as curProps. */
      val oldComponent =
        if (nextProps == oldJsProps) newComponent
        else convertProps(oldJsProps, thisJs.jsPropsToScala, displayName)
      val curState = thisJs.state
      val newSelf =
        mkSelf(thisJs, nextState, newComponent, displayName)
      val oldSelf =
        mkSelf(thisJs, curState, oldComponent, displayName)
      wu(new OldNewSelf(oldSelf, newSelf))
    }
  }

  @inline protected def _componentDidUpdate(displayName: String)(
      thisJs: ThisSelf,
      prevProps: JsComponentThisProps,
      prevState: State): Unit = {
    //println(s"$debugName:CreateClassOpts.componentDidUpdate:\nprevProps ${PrettyJson.render(prevProps)}\nprevState: ${PrettyJson.render(prevState)}")
    val newJsProps = thisJs.props
    val curState   = thisJs.state
    val newConvertedScalaProps =
      convertProps(newJsProps, thisJs.jsPropsToScala, displayName)
    val newComponent = newConvertedScalaProps
    newComponent.didUpdate.foreach { du =>
      val oldComponent =
        if (prevProps == newJsProps) newConvertedScalaProps
        else convertProps(prevProps, thisJs.jsPropsToScala, displayName)
      val ns =
        mkSelf(thisJs, curState, newComponent, displayName)
      val os =
        mkSelf(thisJs, prevState, oldComponent, displayName)
      du(new OldNewSelf(os, ns))
    }
  }

  // simple version for stateless objects, the stateful one is more complicated
  @inline protected def _statelessShouldComponentUpdate(displayName: String)(
      thisJs: ThisSelf,
      nextJsProps: JsComponentThisProps,
      nextState: State): Boolean = {
    val curJsProps   = thisJs.props
    val oldComponent = convertProps(curJsProps, thisJs.jsPropsToScala, displayName)
    val newComponent =
      if (nextJsProps == curJsProps) oldComponent
      else convertProps(nextJsProps, thisJs.jsPropsToScala, displayName)
    newComponent.shouldUpdate
      .map { su =>
        val curState = thisJs.state
        val newSelf  = mkSelf(thisJs, nextState, newComponent, displayName)
        val oldSelf  = mkSelf(thisJs, curState, oldComponent, displayName)
        su(new OldNewSelf(oldSelf, newSelf))
      }
      .getOrElse(true)
  }

  /**
    * Factored out methods that assume no state or retained props.  The methods
    * defined in any layer should only be based on the data model for that
    * layer, obviously. The "methods" on this object are called by the reactjs
    * machinery directly and then the cake layer's methods, such as
    * _componentWillUnmount, then call methods on the ComponentSpec, which are
    * scala react component's methods defined by the programmer. Hence, there is
    * always a pair (proxy to be used in reactjs, componentspec to be used in
    * scala.js) and the glue are the methods in the cake. Note that this
    * instances of the Proxy type are only used in "React.createClass".
    *
    * We could have defined all of the methods the proxy calls inline with the
    * class but we pulled them into the cake and just call them direcly to help
    * improve readability.
    */
  abstract protected class ProxyLike extends Proxy[Self, State, JsComponentThisProps, ThisSelf] {
    // componentWillMount is not used in scalajs-react...
    override val componentWillUnmount = js.defined(_componentWillUnmount(displayName))
    // REMOVE TO ALLOW PROMISE PASS THROUGH UNHINDERED FOR THE MOMENT?!?!?!
    override val componentDidCatch     = js.defined(_componentDidCatch(displayName))
    override val render                = _render(displayName)
    override val componentWillUpdate   = js.defined(_componentWillUpdate(displayName))
    override val componentDidUpdate    = js.defined(_componentDidUpdate(displayName))
    override val componentDidMount     = js.defined(_componentDidMountCommon(displayName))
    override val shouldComponentUpdate = js.defined(_statelessShouldComponentUpdate(displayName))
  }
}

trait StatelessCakeBase extends CakeBase { cake =>
  protected type ProxyType <: ProxyLike
  type DidMount = Self => Unit

  protected def _componentDidMountStateless(displayName: String)(thisJs: ThisSelf): Unit = {
    val (c, s, cbs) = _componentDidMountCommon(displayName)(thisJs)
    c.didMount.foreach(_(s))
  }

  abstract protected class ProxyLike extends super.ProxyLike {
    override val componentDidMount = js.defined(_componentDidMountStateless(displayName))
  }
}

trait CakeWithRP extends CakeBase { cake =>

  /** Component retained props, for comparisons. */
  type RP
  type Self <: SelfLike
  type SelfForUnmount <: SelfForUnmountLike

  protected trait HasRP {
    def retainedProps: RP
  }

  trait SelfLike           extends super.SelfLike with HasRP
  trait SelfForUnmountLike extends super.SelfForUnmountLike with HasRP

  type ComponentType <: ComponentLike
  trait ComponentLike extends super.ComponentLike {
    var retainedProps: js.UndefOr[RP] = js.undefined
  }
  type WithMethods <: WithMethodsLike
  trait WithMethodsLike extends super.WithMethodsLike {
    // must be defined when trait is created
    val retainedProps: RP
  }
  type Ops <: OpsLike
  trait OpsLike extends super.OpsLike {
    type RetainedProps = cake.RP
  }
}

trait CakeWithState extends CakeBase { cake =>

  /** Component state. Not quite the same as reactjs state. */
  type S

  /** Component action type for built in reducer. */
  type A

  protected type State <: StateLike

  /**
    * In the reactjs State for a stateful cake layer, store the scala state.
    * and potentially some side effects.
    */
  protected trait StateLike extends super.StateLike {

    /** Actual Component provided state. Can be null */
    val scalaState: S
  }

  type DidMount = Self => Unit

  type ComponentType <: ComponentLike
  trait ComponentLike extends super.ComponentLike {
    // we use UndefOr here so we can define it, but its required in WithMethods.
    var initialState: js.UndefOr[SelfForInitialState => S] = js.undefined
    var willReceiveProps: js.UndefOr[Self => S]            = js.undefined
    //var didMount: js.UndefOr[] = js.undefined
    var reducer: js.UndefOr[(A, S, ReducerResult[S, Self]) => ReducerResult[S, Self]#UpdateType] =
      js.undefined
    ///override val shouldUpdate: js.UndefOr[OldNewSelf[Self] => Boolean] = js.undefined
  }

  type WithMethods <: WithMethodsLike
  trait WithMethodsLike extends super.WithMethodsLike {
    // must be defined when trait is created
    val initialState: SelfForInitialState => S
    var willReceiveProps: js.UndefOr[Self => S] = js.undefined
    val reducer: (A, S, ReducerResult[S, Self]) => ReducerResult[S, Self]#UpdateType
  }

  type Ops <: OpsLike
  trait OpsLike extends super.OpsLike {

    /** State type S. Kept as S to avoid clash with common name "State" */
    type S = cake.S

    /** Action type A. Kept as A to avoid clash with common name "Action" */
    type A = cake.A

    type SelfForInitialState = cake.SelfForInitialState
  }

  /** 
   * The "self" used for the initial state used for the initialState API.  We
   * need to think if send and handle are appropriate here. Clearly they need to
   * run after the state has been set internally. They area included in case the
   * information you put into state (which are really instance variables) need
   * to send actions to the reduce and that must be present in the initial
   * state.
   */
  type SelfForInitialState <: SelfForInitialStateLike
  trait SelfForInitialStateLike {
    def send(a: A): Unit
    def handle(cb: Self => Unit): Unit
    def onUnmount(cb: OnUnmount): Unit
    private[ttg] var ptr: js.Any
  }

  def mkSelfForInitialState(
      thisJs: ThisSelf,
      reactjsState: State,
      component: ComponentType,
      displayName: String): SelfForInitialState

  type Self <: SelfLike
  trait SelfLike extends super.SelfLike with SendParts

  trait Sender {
    def send(a: A): Unit
  }

  trait SendParts extends Sender {
    def state: S
  }

  type SelfForUnmount <: SelfForUnmountLike
  trait SelfForUnmountLike extends super.SelfForUnmountLike {
    def state: S
  }

  // allocate just once per component
  object reducerResult extends ReducerResult[S, Self]

  protected def mkState(s: S): State

  protected def sendMethod(thisJs: ThisSelf, action: A, displayName: String): Unit = {
    //println(s"$displayName:CreateClasOpts.sendMethod: $action")
    val convertedScalaProps =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    val component                        = convertedScalaProps
    var sideEffect: Option[Self => Unit] = None
    component.reducer.foreach { reducer =>
      thisJs.setState(
        curTotalState => {
          val curScalaState = curTotalState.scalaState
          val scalaStateUpdate =
            component.reducer.map(_(action, curScalaState, reducerResult)).getOrElse(NoUpdate())
          if (scalaStateUpdate == NoUpdate()) null // reactjs sees setState(null) so no update
          else {
            val (sideEffectOpt, nextTotalState) =
              transitionNextTotalState(curTotalState, scalaStateUpdate)
            sideEffect = sideEffectOpt
            if (nextTotalState != curTotalState) nextTotalState
            else null
          }
        },
        () => sideEffect.foreach(cb => handleMethod(thisJs, cb, displayName))
      )
    }
  }

  protected type ProxyType <: ProxyLike

  // adding sideeffects to start of list is significant in the processing algorithm
  protected def transitionNextTotalState(
      curTotalState: State,
      scalaStateUpdate: StateUpdate[S, Self]): (Option[Self => Unit], State) =
    scalaStateUpdate match {
      case NoUpdate()             => (None, curTotalState)
      case Update(nextScalaState) => (None, mkState(nextScalaState))
      case SideEffects(effect)    => (Some(effect), curTotalState)
      case UpdateWithSideEffects(nextScalaState, effect) =>
        (Some(effect), mkState(nextScalaState))
    }

  /** Unlike the base case with no state, allow state to be returned and acted upon. */
  protected def _componentDidMountWithState(displayName: String)(thisJs: ThisSelf): Unit = {
    val (c, s, o) = _componentDidMountCommon(displayName)(thisJs)
    c.didMount.foreach(_(s))
  }

  protected def _getInitialState(displayName: String)(thisJs: ThisSelf): State = {
    val component =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    val self = mkSelfForInitialState(thisJs, thisJs.state, component, displayName)
    // passing in self is not in reasonreact API.
    val istate: S =
      component.initialState.fold(throw new Exception(
        s"""No initial state was defined for ${displayName} even though it is a stateful component."""))(
        _(self))
    mkState(istate)
  }

  protected def _componentWillReceiveProps(
      displayName: String)(thisJs: ThisSelf, nextProps: JsComponentThisProps): Unit = {
    //println(s"$displayName:CreateClassOpts.componentWillReceiveProps: nextProps ${PrettyJson.render(nextProps)}")
    val newConvertedScalaProps =
      convertProps(nextProps, thisJs.jsPropsToScala, displayName)
    val newComponent = newConvertedScalaProps
    newComponent.willReceiveProps.foreach { wrp =>
      val oldJsProps = thisJs.props
      val oldConvertedScalaProps =
        if (nextProps == oldJsProps) newConvertedScalaProps
        else convertProps(oldJsProps, thisJs.jsPropsToScala, displayName)
      val oldComponent = oldConvertedScalaProps
      thisJs.setState(curTotalState => {
        val curScalaState  = curTotalState.scalaState
        val os             = mkSelf(thisJs, curTotalState, oldComponent)
        val nextScalaState = wrp(os)
        // figure out which TotalState to return
        if (nextScalaState != curScalaState)
          mkState(nextScalaState)
        else
          curTotalState
      })
    }
  }

  protected def _shouldComponentUpdate(displayName: String)(
      thisJs: ThisSelf,
      nextJsProps: JsComponentThisProps,
      nextState: State): Boolean = {
    val curJsProps = thisJs.props
    val oldConvertedScalaProps =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    val newConvertedScalaProps =
      if (nextJsProps == curJsProps) oldConvertedScalaProps
      else convertProps(nextJsProps, thisJs.jsPropsToScala, displayName)

    val oldComponent   = oldConvertedScalaProps
    val newComponent   = newConvertedScalaProps
    val nextScalaState = nextState.scalaState
    val newSelf =
      mkSelf(thisJs, nextState, newComponent)
    newComponent.shouldUpdate
      .map { su =>
        val curState = thisJs.state
        val oldSelf =
          mkSelf(thisJs, curState, oldComponent, displayName)
        su(new OldNewSelf(oldSelf, newSelf))
      }
      .getOrElse(true)
  }

  abstract protected class ProxyLike extends super.ProxyLike {
    override val componentDidMount         = js.defined(_componentDidMountWithState(displayName))
    override val getInitialState           = js.defined(_getInitialState(displayName) _)
    override val componentWillReceiveProps = js.defined(_componentWillReceiveProps(displayName) _)
    override val shouldComponentUpdate     = js.defined(_shouldComponentUpdate(displayName))
  }
}

/**
  * A ComponentCake that only requires a render method. To reduce boilerplate
  * after importing ops, just use `[ops.]render(self => { ... })` in your make
  * function *if* you only have a render method.
  */
trait StatelessComponentCake extends StatelessCakeBase { cake =>

  /** No RP, no S, no A. You do get a handle(). */
  type Self            = SelfLike
  type SelfForUnmount  = SelfForUnmountLike
  protected type State = StateLike
  type WithMethods     = WithMethodsLike
  type Ops             = MyOpsLike

  trait MyOpsLike extends super.OpsLike {
    /**
      * Without the need for a val anchor, add the render method. If you need to
      * add other methods, more than just render, you need to use the long-form
      * syntax `yourcomponent.copy(new methods { })`.
      */
    def render(f: Self => ReactNode) =
      cake.copy(new WithMethods {
        val render = f
      })
  }

  val ops = new MyOpsLike {}

  def mkSelf(
      thisJs: ThisSelf,
      reactjsState: State,
      component: ComponentType,
      displayName: String): Self =
    new Self {
      def handle(cb: Self => Unit) = handleMethod(thisJs, cb, displayName)
      def onUnmount(cb: OnUnmount) = addOnUnmounts(component, js.Array(cb))
      var ptr                      = thisJs.asInstanceOf[js.Any] // future hack!
    }

  def mkSelfForUnmount(
      thisJs: ThisSelf,
      reactjsState: State,
      component: ComponentType,
      displayName: String): SelfForUnmount =
    new SelfForUnmount {
      def handle(cb: Self => Unit) = handleMethod(thisJs, cb, displayName)
      var ptr                      = thisJs.asInstanceOf[js.Any] // future hack!
    }
}

trait StatelessComponentWithRetainedPropsCake extends StatelessCakeBase with CakeWithRP { cake =>

  /** No S, A. You do get a handle. */
  type Self            = SelfLike
  type SelfForUnmount  = SelfForUnmountLike
  protected type State = StateLike
  type WithMethods     = WithMethodsLike
  type Ops             = OpsLike

  val ops = new Ops {}

  def mkSelf(thisJs: ThisSelf, reactjsState: State, component: ComponentType, displayName: String) =
    new Self {
      val retainedProps =
        component.retainedProps.getOrElse(
          throw new Exception(
            s"""Internal error: Retained props was not defined for component ${displayName}."""))
      def handle(cb: Self => Unit)       = handleMethod(thisJs, cb, displayName)
      def onUnmount(cb: OnUnmount): Unit = addOnUnmounts(component, js.Array(cb))
      var ptr                            = thisJs.asInstanceOf[js.Any] // future hack!
    }
  def mkSelfForUnmount(
      thisJs: ThisSelf,
      reactjsState: State,
      component: ComponentType,
      displayName: String): SelfForUnmount =
    new SelfForUnmount {
      val retainedProps =
        component.retainedProps.getOrElse(
          throw new Exception(
            s"""Internal error: Retained props was not defined for component ${displayName}."""))
      var ptr = thisJs.asInstanceOf[js.Any] // future hack!
    }

}

trait ReducerComponentCake extends CakeWithState { cake =>
  type Self                = SelfLike
  type SelfForUnmount      = SelfForUnmountLike
  type SelfForInitialState = SelfForInitialStateLike
  protected type State     = StateLike
  type WithMethods         = WithMethodsLike
  type Ops                 = OpsLike

  trait OpsLike extends super.OpsLike
  val ops = new Ops {}

  /**
    * Helper to make reactjs this.state if this is all that there is. Subtraits
    *  will need to override if you add more to it.
    */
  protected def mkState(s: S): State =
    new StateLike {
      val scalaState = s
    }

  override def mkSelf(
      thisJs: ThisSelf,
      jsState: State,
      component: ComponentType,
      displayName: String): Self =
    new SelfLike {
      val state                    = jsState.scalaState
      def send(a: A)               = sendMethod(thisJs, a, displayName)
      def handle(cb: Self => Unit) = handleMethod(thisJs, cb, displayName)
      def onUnmount(cb: OnUnmount) = addOnUnmounts(component, js.Array(cb))
      var ptr                      = thisJs.asInstanceOf[js.Any] // future hack!
    }

  override def mkSelfForUnmount(
      thisJs: ThisSelf,
      jsState: State,
      component: ComponentType,
      displayName: String): SelfForUnmount =
    new SelfForUnmount {
      val state = jsState.scalaState
      var ptr   = thisJs.asInstanceOf[js.Any]
    }

  override def mkSelfForInitialState(
      thisJs: ThisSelf,
      reactjsState: State,
      component: ComponentType,
      displayName: String) =
    new SelfForInitialState {
      def handle(cb: Self => Unit): Unit = handleMethod(thisJs, cb, displayName)
      def send(a: A)                     = sendMethod(thisJs, a, displayName)
      def onUnmount(cb: OnUnmount)       = addOnUnmounts(component, js.Array(cb))
      var ptr                            = thisJs.asInstanceOf[js.Any] // future hack!
    }
}

trait KitchenSinkComponentCake extends CakeWithRP with CakeWithState { cake =>
  type Self                = SelfLike
  type SelfForUnmount      = SelfForUnmountLike
  type SelfForInitialState = SelfForInitialStateLike
  protected type State     = StateLike
  type ComponentType <: ComponentLike
  type Ops                 = OpsLike
  type WithMethods = WithMethodsLike  

  trait OpsLike extends super[CakeWithRP].OpsLike with super[CakeWithState].OpsLike

  val ops = new Ops {}

  trait SelfLike extends super[CakeWithRP].SelfLike with super[CakeWithState].SelfLike
  trait SelfForUnmountLike
      extends super[CakeWithRP].SelfForUnmountLike
      with super[CakeWithState].SelfForUnmountLike
  trait SelfForInitialStateLike extends super[CakeWithState].SelfForInitialStateLike

  protected type ProxyType <: super[CakeWithRP].ProxyLike with super[CakeWithState].ProxyLike

  /** A component is just a javascript object. */
  protected trait ComponentLike
      extends super[CakeWithRP].ComponentLike
      with super[CakeWithState].ComponentLike

  trait WithMethodsLike
      extends super[CakeWithRP].WithMethodsLike
      with super[CakeWithState].WithMethodsLike

  /**
    * Helper to make reactjs this.state if this is all that there is. Subtraits
    *  will need to override if you add more to it.
    */
  protected def mkState(s: S): State =
    new StateLike {
      val scalaState = s
    }

  // s, p could be null
  def mkSelf(
      thisJs: ThisSelf,
      reactjsState: State,
      component: ComponentType,
      displayName: String): Self = new Self {
    val state = reactjsState.scalaState
    val retainedProps =
      component.retainedProps.getOrElse(
        throw new Exception(
          s"""Internal error: Retained props were not defined for component ${displayName}"""))
    def send(a: A)               = sendMethod(thisJs, a, displayName)
    def handle(cb: Self => Unit) = handleMethod(thisJs, cb, displayName)
    def onUnmount(cb: OnUnmount) = addOnUnmounts(component, js.Array(cb))
    var ptr                      = thisJs.asInstanceOf[js.Any] // future hack!
  }

  override def mkSelfForUnmount(
      thisJs: ThisSelf,
      reactjsState: State,
      component: ComponentType,
      displayName: String): SelfForUnmount =
    new SelfForUnmount {
      val state = reactjsState.scalaState
      val retainedProps =
        component.retainedProps.getOrElse(
          throw new Exception(
            s"""Internal error: Retained props were not defined for component ${displayName}"""))
      var ptr = thisJs.asInstanceOf[js.Any] // future hack!
    }

  override def mkSelfForInitialState(
      thisJs: ThisSelf,
      reactjsState: State,
      component: ComponentType,
      displayName: String) =
    new SelfForInitialState {
      def send(a: A)               = sendMethod(thisJs, a, displayName)
      def handle(cb: Self => Unit) = handleMethod(thisJs, cb, displayName)
      def onUnmount(cb: OnUnmount) = addOnUnmounts(component, js.Array(cb))
      var ptr                      = thisJs.asInstanceOf[js.Any] // future hack!
    }
}

/** Allows prev/next comparisons. */
class OldNewSelf[SLF](val oldSelf: SLF, val newSelf: SLF) extends js.Object

// State update ADT
sealed trait StateUpdate[S, SLF]
case class NoUpdate[S, SLF]() extends StateUpdate[S, SLF]

// things with state
case class Update[S, SLF](s: S)                                     extends StateUpdate[S, SLF]
case class UpdateWithSideEffects[S, SLF](s: S, effect: SLF => Unit) extends StateUpdate[S, SLF]

// things without state
case class SideEffects[S, SLF](effect: SLF => Unit) extends StateUpdate[S, SLF]

/**
  * Instead of exposing the ADT to the client, we use a smart constructor at the
  * cost of an extra object allocation. Now we can change the ADT over time. Use
  * `ReducerResult[S, SLF]#UpdateType` for the type when you need to refer to
  * the ADT type. Side effects are run after the state update so use the
  * self parameter to the side effect to obtain the most current state and not the
  * self parameter to the function that you call the methods from.
  *
  * This trait is only used for stateful components.
  */
trait ReducerResult[S, SLF] {

  /** Use this type in client code to create an effect. */
  type UpdateEffect = SLF => Unit
  type UpdateType   = StateUpdate[S, SLF]

  lazy val skip: UpdateType                    = NoUpdate()
  def update(s: S): UpdateType                 = Update(s)
  def effect(effect: UpdateEffect): UpdateType = SideEffects(effect)
  def updateAndEffect(s: S)(effect: UpdateEffect = _ => Unit): UpdateType =
    UpdateWithSideEffects(s, effect)
}
