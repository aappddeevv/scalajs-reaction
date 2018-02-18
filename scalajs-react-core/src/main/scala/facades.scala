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
  * Basic component.  Only includes the parts of a component not dependent on
  * specific types. A component is just a javascript object with some callback
  * fields on it.
  */
trait ComponentSpec extends js.Object {
  var debugName: String
  var reactClassInternal: ReactClass
  var contextTypes: js.UndefOr[js.Object] = js.undefined
  var jsElementWrapped: js.UndefOr[JsElementWrapped] = js.undefined
}

@js.native
protected trait JsComponentThisProps extends js.Object {

  /** We hang our interop component on this property. */
  val scalaProps: js.UndefOr[ComponentSpec] = js.native
}

@js.native
protected trait JsComponentThis[State] extends js.Object {

  /** this.state */
  def state: State

  /** this.setState */
  def setState(set: js.Function1[State, State | Null], onComplete: () => Unit = () => ()): Unit

  /** this.props */
  def props: JsComponentThisProps

  /**
    * Convert raw js this.props to a scala side object. This is only used when
    * wrapping a non-scala (js side defined) component. It is attached to the
    * objects prototype so its present on all instances.
    */
  def jsPropsToScala: js.UndefOr[js.Object => ComponentSpec]
}

trait CakeBase { cake =>

  /** Component self for most clent API methods which may or may not contain state. */
  type Self <: SelfLike

  /** Self used for component.willUnmount. andle/send do not work there */
  type SelfForUnmount <: SelfForUnmountLike

  trait SelfForUnmountLike {
    private[ttg] var ptr: js.Any
  }

  /**
    * Self for methods. At its simplest, you can execute callbacks. Subtraits
    * may create additional "Self"s for use in client API methods.
    */
  trait SelfLike extends SelfForUnmountLike {
    def handle(cb: Self => Unit): Unit
  }

  /** reactjs this: Only the parts that we need or the interop. */
  type ThisSelf = JsComponentThis[State]

  /** The basic scala component that requires methods to cusomize its behavior. */
  val component: ComponentType
  type ComponentType <: ComponentLike

  /**
    * A component is just a javascript object. All of these methods should
    * be defined in the ProxyLike class in this cake layer.
    */
  protected trait ComponentLike extends ComponentSpec {
    var render: js.UndefOr[Self => ReactNode] = js.undefined
    var subscriptions: js.UndefOr[Self => Seq[Subscription]] = js.undefined
    var didCatch: js.UndefOr[(Self, js.Error, ErrorInfo) => Unit] = js.undefined

    var willUpdate: js.UndefOr[OldNewSelf[Self] => Unit] = js.undefined
    var didUpdate: js.UndefOr[OldNewSelf[Self] => Unit] = js.undefined
    var willUnmount: js.UndefOr[SelfForUnmount => Unit] = js.undefined
    var shouldUpdate: js.UndefOr[OldNewSelf[Self] => Boolean] = js.undefined
  }

  /** reactjs this.state: The object stored in reactjs this.state. */
  protected type State <: StateLike

  /**
    * Keep it as js-object so you can still manipulate it from javascript.
    */
  protected trait StateLike extends js.Object

  /**
    * Methods exposed for the public component API. Clients add their own methods to
    * this js object and then it is merged into copy of the component to
    * create a new component. Copying is a javascript "Object.assign".
    */
  type WithMethods <: WithMethodsLike

  trait WithMethodsLike extends js.Object {
    var render: js.UndefOr[Self => ReactNode] = js.undefined
    var subscriptions: js.UndefOr[Self => Seq[Subscription]] = js.undefined
    var didCatch: js.UndefOr[(Self, js.Error, ErrorInfo) => Unit] = js.undefined

    //var shouldUpdate: js.UndefOr[OldNewSelf[Self] => Boolean] = js.undefined
    var willUpdate: js.UndefOr[OldNewSelf[Self] => Unit] = js.undefined
    var didUpdate: js.UndefOr[OldNewSelf[Self] => Unit] = js.undefined
    var willUnmount: js.UndefOr[SelfForUnmount => Unit] = js.undefined
    var shouldUpdate: js.UndefOr[OldNewSelf[Self] => Boolean] = js.undefined
  }

  /**
    * Helpers exposed when the client imports them via `import myCmponent.ops._`.
    */
  val ops: Ops

  type Ops <: OpsLike
  trait OpsLike {
    type Self = cake.Self

    /** Use this as `myComponent.copy(new methods { ... })`. */
    type methods = cake.WithMethods
  }

  /**
    * Client API to copy this component and add methods. The cost of the copy/merge
    * is the same as that for raw javascript.
    */
  def copy(methods: WithMethods): ComponentType = {
    mergeComponents[ComponentType](lit(), component, methods.asInstanceOf[js.Dynamic])
  }

  /** Wrap the component for use in javascript. */
  def wrapScalaForJs[P <: js.Object](jsPropsToScala: P => ComponentSpec): ReactJsComponent =
    elements.wrapScalaForJs(component, jsPropsToScala)

  protected type ProxyType <: ProxyLike

  /**
    * The basic proxy object for this component. We only need one. It calls into
    * the public API methods found on ComponentType.
    */
  protected val proxy: ProxyType

  /**
    * If the props arg contains a `scalaProps` member return it
    * directly. Otherwise, convert the entire js props object using
    * jsPropsToScala. The scala-side proxy uses this to function extract out
    * "scala props" stashed in js-side props. js-side props are always a js
    * object.
    */
  def convertPropsIfTheyAreFromJs(props: JsComponentThisProps, jsPropsToScala: Option[js.Object => Component], debugName: String): Component = {
    val scalaProps: Option[Component] = props.scalaProps.toOption
    (scalaProps, jsPropsToScala) match {
      // order is important, check for scalaProps first
      case (Some(jsprops), _) => jsprops
      case (None, Some(toScalaProps)) => toScalaProps(props)
      case (None, None) =>
        //js.Dynamic.global.console.log("object in error", props.asInstanceOf[js.Any])
        throw new IllegalStateException(
          s"""A JS component called scala component ${Option(debugName).getOrElse("<no name>")} """ +
            "which does not implement the JS->Scala React props conversion.")
    }
  }

  /**
    * Helper to extract component from reactjs this.props. This is one of the very few
    * places we perform a cast.
    */
  protected def convertProps(props: JsComponentThisProps, convert: js.UndefOr[js.Object => ComponentSpec], debugName: String = null): ComponentType =
    convertPropsIfTheyAreFromJs(props, convert.toOption, debugName).asInstanceOf[ComponentType]

  /** Make a self or most of the public APi calls. */
  protected def mkSelf(thisJs: ThisSelf, jsState: State, component: ComponentType, displayName: String = null): Self

  /** Make a self for unmounting which may different than most public API needs. */
  protected def mkSelfForUnmount(thisJs: ThisSelf, jsState: State, component: ComponentType, displayName: String = null): SelfForUnmount

  /** Common didMount functionality. Runs subscriptions. */
  protected def _componentDidMountCommon(displayName: String)(thisJs: ThisSelf): (ComponentType, Self, Seq[() => Unit]) = {
    val component =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    val self = mkSelf(thisJs, thisJs.state, component, displayName)
    // call subscriptions
    val subscriptions = component.subscriptions.map(_(self).map(_())).getOrElse(Seq())
    (component, self, subscriptions)
  }

  /** Call a callback immediately with the most "recent" reactjs this. */
  protected def handleMethod(thisJs: ThisSelf, cb: Self => Unit, displayName: String = null): Unit = {
    //println(s"$displayName:CreateClassOpts.handleMethod: $cb")
    val component =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    //val curState = thisJs.state // this is the actual react js state, which is TotalState
    //val curScalaState = curState.scalaState
    // was component.retainedProps
    val self = mkSelf(thisJs, thisJs.state, component)
    cb(self)
  }

  /** The basic unmount runs the unsubscribes. */
  protected def _componentWillUnmount(subscriptions: Seq[() => Unit], displayName: String)(thisJs: ThisSelf): Unit = {
    val component =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    component.willUnmount.toOption match {
      case Some(wu) =>
        val self = mkSelfForUnmount(thisJs, thisJs.state, component, displayName)
        wu(self)
      case _ => // do nothing
    }
    subscriptions.foreach(_())
  }

  protected def _componentDidCatch(displayName: String)(thisJs: ThisSelf, error: js.Error, errorInfo: ErrorInfo): Unit = {
    val component = convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    component.didCatch.toOption match {
      case Some(dc) =>
        val self = mkSelf(thisJs, thisJs.state, component)
        dc(self, error, errorInfo)
      case _ => // do nothing
    }
  }

  protected def _render(displayName: String)(thisJs: ThisSelf): ReactNode = {
    // call the real render method!
    val component =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    val self = mkSelf(thisJs, thisJs.state, component)
    component.render.map(_(self)).getOrElse(null)
  }

  protected def _componentWillUpdate(displayName: String)(thisJs: ThisSelf, nextProps: JsComponentThisProps, nextState: State): Unit = {
    //println(s"$debugName:CreateClassOpts.componentWillUpdate")
    val newComponent =
      convertProps(nextProps, thisJs.jsPropsToScala, displayName)
    newComponent.willUpdate.toOption match {
      case Some(wu) =>
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
      case _ => // do nothing
    }
  }

  protected def _componentDidUpdate(displayName: String)(thisJs: ThisSelf, prevProps: JsComponentThisProps, prevState: State): Unit = {
    //println(s"$debugName:CreateClassOpts.componentDidUpdate:\nprevProps ${PrettyJson.render(prevProps)}\nprevState: ${PrettyJson.render(prevState)}")
    val newJsProps = thisJs.props
    val curState = thisJs.state
    val newConvertedScalaProps =
      convertProps(newJsProps, thisJs.jsPropsToScala, displayName)
    val newComponent = newConvertedScalaProps
    newComponent.didUpdate.toOption match {
      case Some(du) =>
        val oldComponent =
          if (prevProps == newJsProps) newConvertedScalaProps
          else convertProps(prevProps, thisJs.jsPropsToScala, displayName)
        val ns =
          mkSelf(thisJs, curState, newComponent, displayName)
        val os =
          mkSelf(thisJs, prevState, oldComponent, displayName)
        du(new OldNewSelf(os, ns))
      case _ => // do nothing!
    }
  }

  // simple versioin for stateless objects, the statefull one is more complicated
  protected def _statelessShouldComponentUpdate(displayName: String)(thisJs: ThisSelf, nextJsProps: JsComponentThisProps, nextState: State): Boolean = {
    val curJsProps = thisJs.props
    var propsWarrantRerender = nextJsProps != curJsProps
    val oldComponent = convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    val newComponent =
      if (nextJsProps == curJsProps) oldComponent
      else convertProps(nextJsProps, thisJs.jsPropsToScala, displayName)

    newComponent.shouldUpdate.toOption match {
      case Some(su) if (propsWarrantRerender) =>
        val curState = thisJs.state
        val newSelf = mkSelf(thisJs, nextState, newComponent, displayName)
        val oldSelf = mkSelf(thisJs, curState, oldComponent, displayName)
        su(new OldNewSelf(oldSelf, newSelf))
      case _ => propsWarrantRerender
    }
  }

  /**
    * Factored out methods that assume no state or retained props.  The methods
    * defined in any layer should only be based on the data model for that layer,
    * obviously. One proxy serves all components created from the same cake.
    */
  protected abstract class ProxyLike extends Proxy[Self, State, JsComponentThisProps, ThisSelf] {
    // Use null to avoid allocation. Thes are the "unsubscribe" part of the subscriptions.
    override val componentWillUnmount = js.defined(_componentWillUnmount(subscriptions.getOrElse(Seq()), displayName))
    override val componentDidCatch = js.defined(_componentDidCatch(displayName))
    override val render = _render(displayName)
    override val componentWillUpdate = js.defined(_componentWillUpdate(displayName))
    override val componentDidUpdate = js.defined(_componentDidUpdate(displayName))
    // just run subscriptions...subclasses have more to do...
    override val componentDidMount = js.defined { thisJs =>
      //println(s"$debugName:CreateClassOpts.componentDidMount")
      val (_, _, subs) = _componentDidMountCommon(displayName)(thisJs)
      subscriptions = subs
    }
    override val shouldComponentUpdate = js.defined(_statelessShouldComponentUpdate(displayName))
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

  trait SelfLike extends super.SelfLike with HasRP
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
    type RP = cake.RP
  }
}

trait CakeWithState extends CakeBase { cake =>

  /** Component state. Not quite the same as reactjs state. */
  type S

  /** Component action type for built in reducer. */
  type A
  protected type State <: StateLike
  protected trait StateLike extends super.StateLike {

    /** Actual Component provided state. Can be null */
    val scalaState: S

    /** Version number of state i.e. optimistic concurrency. */
    val scalaStateVersion: Int

    /** Versions tracing to sub-elements. */
    var scalaStateVersionUsedToComputeSubelements: Int

    /** Side effects to execute, if any. */
    val sideEffects: Seq[Self => Unit]
  }

  type ComponentType <: ComponentLike
  trait ComponentLike extends super.ComponentLike {
    // we use UndefOr here so we can define it, but its required in WithMethods.
    var initialState: js.UndefOr[SelfForInitialState => S] = js.undefined
    var willReceiveProps: js.UndefOr[Self => S] = js.undefined
    var didMount: js.UndefOr[(Self, ReducerResult[S, Self]) => ReducerResult[S, Self]#UpdateType] = js.undefined
    // add didMount?
    var reducer: js.UndefOr[(A, S, ReducerResult[S, Self]) => ReducerResult[S, Self]#UpdateType] = js.undefined
    ///override val shouldUpdate: js.UndefOr[OldNewSelf[Self] => Boolean] = js.undefined
  }

  type WithMethods <: WithMethodsLike
  trait WithMethodsLike extends super.WithMethodsLike {
    // must be defined when trait is created
    val initialState: SelfForInitialState => S
    var willReceiveProps: js.UndefOr[Self => S] = js.undefined
    var didMount: js.UndefOr[(Self, ReducerResult[S, Self]) => ReducerResult[S, Self]#UpdateType] = js.undefined
    var reducer: js.UndefOr[(A, S, ReducerResult[S, Self]) => ReducerResult[S, Self]#UpdateType] = js.undefined
  }

  type Ops <: OpsLike
  trait OpsLike extends super.OpsLike {
    type S = cake.S
    type A = cake.A
  }

  /** The "self" used for the initial state used for the initialState API. */
  type SelfForInitialState <: SelfForInitialStateLike
  trait SelfForInitialStateLike {
    def send(a: A): Unit
    def handle(cb: Self => Unit): Unit
    private[ttg] var ptr: js.Any
  }

  def mkSelfForInitialState(thisJs: ThisSelf, reactjsState: State, component: ComponentType, displayName: String): SelfForInitialState

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

  // allocate just once...
  object reducerResult extends ReducerResult[S, Self]

  protected def mkState(s: S, v: Int, sv: Int, e: Seq[Self => Unit]): State

  protected def sendMethod(thisJs: ThisSelf, action: A, displayName: String): Unit = {
    //println(s"$displayName:CreateClasOpts.sendMethod: $action")
    val convertedScalaProps =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    val component = convertedScalaProps
    // Allow side-effects to be executed here. Return inside of setState
    // means reactjs.setStates will not update the state.
    thisJs.setState(curTotalState => {
      val curScalaState = curTotalState.scalaState
      val scalaStateUpdate = component.reducer.map(_(action, curScalaState, reducerResult)).getOrElse(NoUpdate())

      if (scalaStateUpdate == NoUpdate()) null // reactjs sees setState(null) so no update
      else {
        val nextTotalState =
          transitionNextTotalState(curTotalState, scalaStateUpdate)
        if (nextTotalState.scalaStateVersion != curTotalState.scalaStateVersion)
          nextTotalState
        else null
      }
    })
  }

  protected type ProxyType <: ProxyLike

  // adding sideeffects to start of list is significant in the processing algorithm
  protected def transitionNextTotalState(curTotalState: State, scalaStateUpdate: StateUpdate[S, Self]): State =
    scalaStateUpdate match {
      case NoUpdate() => curTotalState
      case Update(nextScalaState) =>
        mkState(nextScalaState, curTotalState.scalaStateVersion + 1, curTotalState.scalaStateVersionUsedToComputeSubelements, curTotalState.sideEffects)
      case SilentUpdate(nextScalaState) =>
        mkState(
          nextScalaState,
          curTotalState.scalaStateVersion + 1,
          curTotalState.scalaStateVersionUsedToComputeSubelements + 1,
          curTotalState.sideEffects
        )
      case SideEffects(effect) =>
        mkState(
          curTotalState.scalaState,
          curTotalState.scalaStateVersion + 1,
          curTotalState.scalaStateVersionUsedToComputeSubelements + 1,
          Seq(effect) ++ curTotalState.sideEffects
        )
      case UpdateWithSideEffects(nextScalaState, effect) =>
        mkState(
          nextScalaState,
          curTotalState.scalaStateVersion + 1,
          curTotalState.scalaStateVersionUsedToComputeSubelements,
          Seq(effect) ++ curTotalState.sideEffects
        )
      case SilentUpdateWithSideEffects(nextScalaState, effect) =>
        mkState(
          nextScalaState,
          curTotalState.scalaStateVersion + 1,
          curTotalState.scalaStateVersionUsedToComputeSubelements + 1,
          Seq(effect) ++ curTotalState.sideEffects
        )
      case _ => curTotalState
    }

  /** Unlike the base case with no state, allow state to be returned and acted upon. */
  protected def _componentDidMountWithState(displayName: String)(thisJs: ThisSelf): Seq[() => Unit] = {
    //println(s"$debugName:CreateClassOpts.componentDidMount")
    val (component, self, subs) = _componentDidMountCommon(displayName)(thisJs)
    component.didMount.toOption match {
      case Some(dm) =>
        val scalaStateUpdate = dm(self, reducerResult)
        // can we check for NoUpdate() as well and skip the transition!
        val curTotalState = thisJs.state
        val nextTotalState =
          transitionNextTotalState(curTotalState, scalaStateUpdate)
        if (nextTotalState.scalaStateVersion != curTotalState.scalaStateVersion)
          thisJs.setState(_ => nextTotalState)
      case _ => // do nothing!
    }
    subs
  }

  protected def _getInitialState(displayName: String)(thisJs: ThisSelf): State = {
    val component =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    val self = mkSelfForInitialState(thisJs, thisJs.state, component, displayName)
    // passing in self is not in reasonreact API.
    val istate: S =
      component.initialState.fold(throw new Exception(s"""No initial state was defined for ${displayName} even though it is a stateful component."""))(_(self))
    mkState(istate, 1, 1, Seq())
  }

  protected def _componentWillReceiveProps(displayName: String)(thisJs: ThisSelf, nextProps: JsComponentThisProps): Unit = {
    //println(s"$displayName:CreateClassOpts.componentWillReceiveProps: nextProps ${PrettyJson.render(nextProps)}")
    val newConvertedScalaProps =
      convertProps(nextProps, thisJs.jsPropsToScala, displayName)
    val newComponent = newConvertedScalaProps
    newComponent.willReceiveProps.toOption match {
      case Some(wrp) =>
        val oldJsProps = thisJs.props
        val oldConvertedScalaProps =
          if (nextProps == oldJsProps) newConvertedScalaProps
          else convertProps(oldJsProps, thisJs.jsPropsToScala, displayName)
        val oldComponent = oldConvertedScalaProps
        thisJs.setState(curTotalState => {
          val curScalaState = curTotalState.scalaState
          val curScalaStateVersion = curTotalState.scalaStateVersion
          val os = mkSelf(thisJs, curTotalState, oldComponent)
          val nextScalaState = wrp(os)
          val nextScalaStateVersion =
            if (nextScalaState != curScalaState) curScalaStateVersion + 1
            else curScalaStateVersion
          // figure out which TotalState to return
          if (nextScalaStateVersion != curScalaStateVersion) {
            mkState(
              nextScalaState,
              nextScalaStateVersion,
              curTotalState.scalaStateVersionUsedToComputeSubelements,
              //val sideEffects = nextScalaState.sideEffects // this is what .re had but sideEffects is on TotalState
              curTotalState.sideEffects
            )
          } else {
            curTotalState
          }
        })
      case _ => // do nothing
    }
  }

  protected def _shouldComponentUpdate(displayName: String)(thisJs: ThisSelf, nextJsProps: JsComponentThisProps, nextState: State): Boolean = {
    val curJsProps = thisJs.props
    var propsWarrantRerender = nextJsProps != curJsProps
    val oldConvertedScalaProps =
      convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
    val newConvertedScalaProps =
      if (nextJsProps == curJsProps) oldConvertedScalaProps
      else convertProps(nextJsProps, thisJs.jsPropsToScala, displayName)

    val oldComponent = oldConvertedScalaProps
    val newComponent = newConvertedScalaProps
    val nextScalaStateVersion = nextState.scalaStateVersion
    val nextScalaStateVersionUsedToComputeSubelements =
      nextState.scalaStateVersionUsedToComputeSubelements
    val stateChangeWarrantsComputingSubelements = nextScalaStateVersionUsedToComputeSubelements != nextScalaStateVersion
    val warrentsUpdate = propsWarrantRerender || stateChangeWarrantsComputingSubelements

    //val nextScalaState = nextState.scalaState
    val newSelf =
      mkSelf(thisJs, nextState, newComponent)
    val ret =
      newComponent.shouldUpdate.toOption match {
        case Some(su) if (warrentsUpdate) =>
          val curState = thisJs.state
          //val curScalaState = curState.scalaState
          /* bypass this##self call for small perf boost */
          val oldSelf =
            mkSelf(thisJs, curState, oldComponent, displayName)
          su(new OldNewSelf(oldSelf, newSelf))
        case _ => warrentsUpdate
      }

    // Mark ourselves as all caught up!, this is mutating, why???
    nextState.scalaStateVersionUsedToComputeSubelements = nextScalaStateVersion
    // run side effects and update list, run in reverse order
    val nextSideEffects = nextState.sideEffects.reverse
    if (nextSideEffects.length > 0) {
      // running side effects can create new ones
      nextSideEffects.foreach(_(newSelf))
      thisJs.setState(futureTotalState => {
        // this seems to be an obtuse way of .take
        //   let rec initialSegment = (acc, n, l) =>
        //     switch l {
        //     | [x, ...nextL] when n > 0 => initialSegment([x, ...acc], n - 1, nextL)
        //     | _ => List.rev(acc)
        //     }
        //   // Additional side effects are the initial segment.
        //   val newSideEffects = {
        //     val acc = []
        //     val n = futureTotalState.sideEffects.size - nextState.sideEffects.size
        //     initialSegment(acc, n, futureTotalState.sideEffects)
        //   }
        val n = futureTotalState.sideEffects.size - nextState.sideEffects.size
        val newSideEffects = futureTotalState.sideEffects.take(n)

        // nextStateOnlyNewSideEffects
        mkState(futureTotalState.scalaState, futureTotalState.scalaStateVersion, futureTotalState.scalaStateVersionUsedToComputeSubelements, newSideEffects)
      })
    }
    ret
  }

  abstract protected class ProxyLike extends super.ProxyLike {
    override val componentDidMount = js.defined { thisJs =>
      val subs = _componentDidMountWithState(displayName)(thisJs)
      subscriptions = subs
    }
    override val getInitialState = js.defined(_getInitialState(displayName) _)
    override val componentWillReceiveProps = js.defined(_componentWillReceiveProps(displayName) _)
    override val shouldComponentUpdate = js.defined(_shouldComponentUpdate(displayName))
  }
}

/** A ComponentCake that only requires a render method. */
trait StatelessComponentCake extends CakeBase { cake =>

  /** No RP, no S, no A. You do get a handle(). */
  type Self = super.SelfLike
  type SelfForUnmount = Self
  protected type State = super.StateLike
  type ProxyType = super.ProxyLike
  type ComponentType = super.ComponentLike
  type WithMethods = super.WithMethodsLike
  type Ops = super.OpsLike
  val ops = new super.OpsLike {}

  def mkSelf(thisJs: ThisSelf, reactjsState: State, component: ComponentType, displayName: String): Self =
    new Self {
      def handle(cb: Self => Unit) = handleMethod(thisJs, cb, displayName)
      var ptr = thisJs.asInstanceOf[js.Any] // future hack!
    }
  def mkSelfForUnmount(thisJs: ThisSelf, reactjsState: State, component: ComponentType, displayName: String): SelfForUnmount =
    mkSelf(thisJs, reactjsState, component, displayName)

}

trait StatelessComponentWithRetainedPropsCake extends CakeWithRP { cake =>

  /** No S, A. You do get a handle. */
  type Self = super.SelfLike
  type SelfForUnmount = super.SelfForUnmountLike
  protected type State = super.StateLike
  type ComponentType = ComponentLike
  type ProxyType = super.ProxyLike
  type WithMethods = super.WithMethodsLike
  type Ops = super.OpsLike
  val ops = new Ops {}

  def mkSelf(thisJs: ThisSelf, reactjsState: State, component: ComponentType, displayName: String) = new super.SelfLike {
    val retainedProps =
      component.retainedProps.getOrElse(throw new Exception(s"""Internal error: Retained props was not defined for component ${displayName}."""))
    def handle(cb: Self => Unit) = handleMethod(thisJs, cb, displayName)
    var ptr = thisJs.asInstanceOf[js.Any] // future hack!
  }
  def mkSelfForUnmount(thisJs: ThisSelf, reactjsState: State, component: ComponentType, displayName: String): SelfForUnmount =
    new super.SelfForUnmount {
      val retainedProps =
        component.retainedProps.getOrElse(throw new Exception(s"""Internal error: Retained props was not defined for component ${displayName}."""))
      var ptr = thisJs.asInstanceOf[js.Any] // future hack!
    }

}

trait ReducerComponentCake extends CakeWithState { cake =>
  type Self = super.SelfLike
  type SelfForUnmount = super.SelfForUnmountLike
  type SelfForInitialState = super.SelfForInitialStateLike
  type State = super.StateLike
  type ComponentType = super.ComponentLike
  type ProxyType = super.ProxyLike
  type WithMethods = super.WithMethodsLike
  type Ops = OpsLike
  trait OpsLike extends super.OpsLike
  val ops = new OpsLike {}

  /**
    * Helper to make reactjs this.state if this is all that there is. Subtraits
    *  will need to override if you add more to it.
    */
  protected def mkState(s: S, v: Int, sv: Int, e: Seq[Self => Unit]): State =
    new StateLike {
      val scalaState = s
      val scalaStateVersion = v
      var scalaStateVersionUsedToComputeSubelements = sv
      val sideEffects = e
    }

  override def mkSelf(thisJs: ThisSelf, jsState: State, component: ComponentType, displayName: String): Self =
    new SelfLike {
      val state = jsState.scalaState
      def send(a: A) = sendMethod(thisJs, a, displayName)
      def handle(cb: Self => Unit) = handleMethod(thisJs, cb, displayName)
      var ptr = thisJs.asInstanceOf[js.Any] // future hack!
    }

  override def mkSelfForUnmount(thisJs: ThisSelf, jsState: State, component: ComponentType, displayName: String): SelfForUnmount =
    new SelfForUnmount {
      val state = jsState.scalaState
      var ptr = thisJs.asInstanceOf[js.Any]
    }

  override def mkSelfForInitialState(thisJs: ThisSelf, reactjsState: State, component: ComponentType, displayName: String) =
    new SelfForInitialState {
      def handle(cb: Self => Unit): Unit = handleMethod(thisJs, cb, displayName)
      def send(a: A) = sendMethod(thisJs, a, displayName)
      var ptr = thisJs.asInstanceOf[js.Any] // future hack!
    }
}

trait KitchenSinkComponentCake extends CakeWithRP with CakeWithState { cake =>
  type Self = SelfLike
  type SelfForUnmount = SelfForUnmountLike
  type SelfForInitialState = SelfForInitialStateLike
  type State = super.StateLike
  type Ops <: OpsLike
  trait OpsLike extends super[CakeWithRP].OpsLike with super[CakeWithState].OpsLike

  trait SelfLike extends super[CakeWithRP].SelfLike with super[CakeWithState].SelfLike
  trait SelfForUnmountLike extends super[CakeWithRP].SelfForUnmountLike with super[CakeWithState].SelfForUnmountLike
  trait SelfForInitialStateLike extends super[CakeWithState].SelfForInitialStateLike

  protected type ProxyType <: super[CakeWithRP].ProxyLike with super[CakeWithState].ProxyLike

  type ComponentType = ComponentLike

  /** A component is just a javascript object. */
  protected trait ComponentLike extends super[CakeWithRP].ComponentLike with super[CakeWithState].ComponentLike

  type WithMethods = WithMethodsLike
  trait WithMethodsLike extends super[CakeWithRP].WithMethodsLike with super[CakeWithState].WithMethodsLike

  /**
    * Helper to make reactjs this.state if this is all that there is. Subtraits
    *  will need to override if you add more to it.
    */
  protected def mkState(s: S, v: Int, sv: Int, e: Seq[Self => Unit]): State =
    new State {
      val scalaState = s
      val scalaStateVersion = v
      var scalaStateVersionUsedToComputeSubelements = sv
      val sideEffects = e
    }

  // s, p could be null
  def mkSelf(thisJs: ThisSelf, reactjsState: State, component: ComponentType, displayName: String): Self = new Self {
    val state = reactjsState.scalaState
    val retainedProps =
      component.retainedProps.getOrElse(throw new Exception(s"""Internal error: Retained props were not defined for component ${displayName}"""))
    def send(a: A) = sendMethod(thisJs, a, displayName)
    def handle(cb: Self => Unit) = handleMethod(thisJs, cb, displayName)
    var ptr = thisJs.asInstanceOf[js.Any] // future hack!
  }

  override def mkSelfForUnmount(thisJs: ThisSelf, reactjsState: State, component: ComponentType, displayName: String): SelfForUnmount =
    new SelfForUnmount {
      val state = reactjsState.scalaState
      val retainedProps =
        component.retainedProps.getOrElse(throw new Exception(s"""Internal error: Retained props were not defined for component ${displayName}"""))
      var ptr = thisJs.asInstanceOf[js.Any] // future hack!
    }

  override def mkSelfForInitialState(thisJs: ThisSelf, reactjsState: State, component: ComponentType, displayName: String) =
    new SelfForInitialState {
      def send(a: A) = sendMethod(thisJs, a, displayName)
      def handle(cb: Self => Unit): Unit = handleMethod(thisJs, cb, displayName)
      var ptr = thisJs.asInstanceOf[js.Any] // future hack!
    }
}

/** Allows prev/next comparisons. */
class OldNewSelf[SLF](val oldSelf: SLF, val newSelf: SLF) extends js.Object

// State update ADT
sealed trait StateUpdate[S, SLF]
case class NoUpdate[S, SLF]() extends StateUpdate[S, SLF]

// things with state
case class Update[S, SLF](s: S) extends StateUpdate[S, SLF]
case class SilentUpdate[S, SLF](s: S) extends StateUpdate[S, SLF]
case class UpdateWithSideEffects[S, SLF](s: S, effect: SLF => Unit) extends StateUpdate[S, SLF]

// things without state
case class SideEffects[S, SLF](effect: SLF => Unit) extends StateUpdate[S, SLF]
case class SilentUpdateWithSideEffects[S, SLF](s: S, effect: SLF => Unit) extends StateUpdate[S, SLF]

/**
  * Instead of exposing the ADT to the client, we use a smart constructor at the
  * cost of an extra object allocation. Now we can change the ADT over time. Use
  * `ReducerResult[S, SLF]#UpdateType` for the type when you need to refer to
  * the ADT type. Side effects are run after the state update so use the
  * self parameter to the side effect to obtain the most current state and not the
  * self parameter to the function that you call the methods from.
  */
trait ReducerResult[S, SLF] {

  /** Use this type in client code to create an effect. */
  type UpdateEffect = SLF => Unit
  type UpdateType = StateUpdate[S, SLF]

  lazy val skip: UpdateType = NoUpdate()
  def update(s: S): UpdateType = Update(s)
  def silent(s: S): UpdateType = SilentUpdate(s)
  def effect(effect: UpdateEffect): UpdateType = SideEffects(effect)
  def updateAndEffect(s: S, effect: UpdateEffect = _ => Unit): UpdateType =
    UpdateWithSideEffects(s, effect)
  def silentAndEffect(s: S, effect: UpdateEffect = _ => Unit): UpdateType =
    SilentUpdateWithSideEffects(s, effect)
}

object elements {

  /** Create a DOM element. */
  def createDomElement(n: String, props: js.Object | js.Dynamic, children: ReactElement*): ReactDOMElement = {
    JSReact.createElement(n, props.asInstanceOf[js.Object], children: _*)
  }

  /**
    * Scala side version of React.createElement given a scala-side ComponentSpec.
    * It calls React.createElement. If component is a scala-side wrapper around a
    * js component, create the js component. No children are allowed in this
    * function as they come should through the props. This is called "element"
    * instead of "createElement" to make it shorter to type if you are not using
    * JSX. Do not use this if you not have a scala side component. You do *not*
    * use this to create standad html elements like "div."
    *
    * Since we return an untyped value, the types of the component are not important.
    */
  def element(component: Component, key: Option[String] = None, ref: Option[RefCb] = None): ReactElement = {
    component.jsElementWrapped.toOption match {
      case Some(func) => func(key, ref)
      case _ =>
        val props = js.Dictionary.empty[Any] // not js.Any! why?
        key.foreach(k => props("key") = k)
        ref.foreach(refcb => props("ref") = refcb)
        props("scalaProps") = component.asInstanceOf[js.Any]
        JSReact.createElement(component.reactClassInternal, props)
    }
  }

  /** The long-named version of `element`. */
  def createElement(component: Component, key: Option[String] = None, ref: Option[RefCb] = None) = element(component, key, ref)

  /** Convert *anything* to what you assert is a js.Any value. Very dangerous. */
  private[this] def toAny(o: scala.Any): js.Any = o.asInstanceOf[js.Any]

  /** Same comment as `toAny`. Very dangerous */
  private[this] def toDynamic(o: scala.Any): js.Dynamic =
    o.asInstanceOf[js.Dynamic]

  /** Stateless component essentially it just has a render. */
  def statelessComponent(debugNameArg: String) = {
    new StatelessComponentCake {
      class ProxyLike extends super.ProxyLike {
        val displayName: String = debugNameArg
      }
      val proxy = new ProxyLike()
      trait ComponentLike extends super.ComponentLike
      val component = new ComponentLike {
        var debugName = debugNameArg
        var reactClassInternal = reactCreateClass(proxy)
      }
    }
  }

  /*
   /** Self trait used when we want to expose the this.context. */
   trait SelfWithContext[S, RP, A, C] extends Self[S, RP, A] {
   val context: Option[C]
   }

   /** Create a component that also includes the context in "self." */
   def basicComponentWithContext[S, RP, A, C](debugNameArg: String, contextTypes: Option[js.Object] = None) = {
   class BasicProxy[S, RP, A, C] extends StandardProxy[S, RP, A] {
   type SLF = SelfWithContext[S, RP, A, C]
   val displayName: String = debugNameArg
   def mkSelf(thisJs: ThisSelf, sopt: Option[S], popt: Option[RP]): SLF =
   new SelfWithContext[S, RP, A, C] {
   type HandleArg = SLF
   val state = sopt
   val retainedProps = popt
   def send(a: A) = sendMethod(thisJs, a)
   def handle(cb: HandleArg => Unit) = handleMethod(thisJs, cb)
   val context = thisJs.asInstanceOf[js.Dynamic].context.asInstanceOf[js.UndefOr[C]].toOption
   ptr = thisJs.asInstanceOf[js.Any] // future hack!
   }
   // the cast on this should not be needed
   val contextTypes = contextTypes.fold(js.undefined.asInstanceOf[js.UndefOr[js.Object]])(x => x)
   }
   val proxy = new BasicProxy[S, RP, A, C]()
   ComponentSpec[S, RP, A, proxy.SLF, proxy.SLF, proxy.SLF](
   debugName = debugNameArg,
   reactClassInternal = reactCreateClass(proxy),
   render = _ => stringToElement("Not Implemented"),
   )
   }
   */

  /** Stateless, with retained props. */
  def statelessComponentWithRetainedProps[RetainedProps](debugNameArg: String) =
    new StatelessComponentWithRetainedPropsCake {
      type RP = RetainedProps
      class ProxyLike extends super.ProxyLike {
        val displayName: String = debugNameArg
      }
      val proxy = new ProxyLike()
      trait ComponentLike extends super.ComponentLike
      val component = new ComponentLike {
        var debugName = debugNameArg
        var reactClassInternal = reactCreateClass(proxy)
      }
    }

  /** Stateful. */
  def reducerComponent[TheState, Action](debugNameArg: String) =
    new ReducerComponentCake {
      type S = TheState
      type A = Action
      class ProxyLike extends super.ProxyLike {
        val displayName: String = debugNameArg
      }
      val proxy = new ProxyLike()
      trait ComponentLike extends super.ComponentLike
      val component = new ComponentLike {
        var debugName = debugNameArg
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
      type S = TheState
      type RP = RetainedProps
      type A = Action
      type Ops = OpsLike
      val ops = new OpsLike {}
      type ProxyType = ProxyLike

      class ProxyLike extends super.ProxyLike {
        val displayName: String = debugNameArg
      }
      val proxy = new ProxyType()
      trait ComponentLike extends super.ComponentLike
      val component = new ComponentLike {
        var debugName = debugNameArg
        var reactClassInternal = reactCreateClass(proxy)
      }
    }

  /** Like nullElement but a Component. Useful for optional children. */
  val nullComponent = statelessComponent("null").component

  /** Clone a ReactElement and add new props. You should not use this if you can avoid it. */
  def cloneElement(element: ReactElement, props: js.Object): ReactElement =
    JSReact.cloneElement(element, props.asInstanceOf[js.Dynamic])

  /**
    * Wrap a js side component for scala side usage. You also need to import the
    * react class using standard scala.js import mechanisms and write a "make"
    * function to create your props from "make" parameters.
    */
  def wrapJsForScala[P <: js.Object](reactClass: ReactJsComponent, props: P, children: ReactNode*): Component = {
    WrapProps.wrapJsForScala(reactClass, props, children: _*)
  }

  /** Create a fragment element. Per the API, you are only allowed an optional key. */
  def fragmentElement(key: Option[String] = None)(children: ReactNode*) =
    React.createFragment(key, children: _*)

  /**
    * Wrap a scala component to be used in js with reactjs. The js props
    * converter is attached to the js side component.  When the converter is
    * called, it should take a js.Object (untyped) and convert it to a
    * component, typically via a call to "make."  jsPropsToScala can use a
    * js.native trait inheriting from js.Object to make picking out the values
    * from the js-side easier, or not, it's up to you. The returned value should
    * be exported from scala-world so that js-world can see it.
    *
    * Note: jsPropsToScala will appear in reactsj's 'this' because its attached
    * to the prototype of reactClassInternal.
    */
  def wrapScalaForJs[P <: js.Object](c: Component, jsPropsToScala: P => ComponentSpec): ReactJsComponent = {
    val dyn = c.reactClassInternal.asInstanceOf[js.Dynamic]
    dyn.prototype.jsPropsToScala = jsPropsToScala.asInstanceOf[js.Any]
    c.reactClassInternal.asInstanceOf[ReactJsComponent]
  }
}
