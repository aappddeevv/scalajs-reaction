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
  * A component in scala world is a simple immutable data structure. Currently,
  * many of these are wrapped in Option since everything but render is really
  * optional. A scala object is used so that you can use more advanced
  * techniques to create it. If we used a non-native JS class, we would have
  * reduced scala-side flexibility.
  *
  * You can use `with*` functions syntax to build up the spec incrementally.
  *
  * @todo Maybe drop Option allocations to reduce memory allocation. Perhaps use null.
  *
  * @tparam S State
  * @tparam RP Retained props.
  * @tparam A Action type for in-component reducer.
  * @tparam SLF The "self" parameter passed into the lifecycle/management functions.
  */
case class ComponentSpec[S, RP, A, SLF](
    /** Component name. Also pushed down into the js side. */
    debugName: String,
    /** Value returned by scalareact.createClass. Internal use only. */
    reactClassInternal: ReactClass,
    /** The only required function in a component. */
    render: SLF => ReactNode,
    /** Retained props. This can change??? Is this mutable? */
    retainedProps: Option[RP] = None,
    initialState: Option[() => Option[S]] = None,
    didMount: Option[(SLF, ReducerResult[S, SLF]) => ReducerResult[S, SLF]#UpdateType] = None,
    willUnmount: Option[SLF => Unit] = None,
    willReceiveProps: Option[SLF => Option[S]] = None,
    /** State management via reducer concept. */
    reducer: (A, Option[S], ReducerResult[S, SLF]) => ReducerResult[S, SLF]#UpdateType = (_: A, _: Option[S], _: ReducerResult[S, SLF]) => NoUpdate[S, SLF](),
    /** Only used when we wrap a js-side element inside of scala. */
    jsElementWrapped: Option[JsElementWrapped] = None,
    shouldUpdate: Option[OldNewSelf[SLF] => Boolean] = None,
    willUpdate: Option[OldNewSelf[SLF] => Unit] = None,
    didUpdate: Option[OldNewSelf[SLF] => Unit] = None,
    subscriptions: SLF => Seq[Subscription] = (_: SLF) => Seq(),
    contextTypes: Option[js.Object] = None,
    didCatch: Option[(SLF, js.Error, ErrorInfo) => Unit] = None
) { self =>
  /** 
   * Use this type for your component e.g. YourComponent.Self for your functions
   * external to the callback methods to help your organize your code.
   */
  type Self = SLF

  def withInitialState(is: () => Option[S]) =
    this.copy(initialState = Some(is))
  def withDidMount(f: (SLF, ReducerResult[S, SLF]) => ReducerResult[S, SLF]#UpdateType) =
    this.copy(didMount = Some(f))
  def withRender(f: SLF => ReactNode) = this.copy(render = f)
  def withWillReceiveProps(f: SLF => Option[S]) =
    this.copy(willReceiveProps = Some(f))
  def withReducer(f: (A, Option[S], ReducerResult[S, SLF]) => ReducerResult[S, SLF]#UpdateType) =
    this.copy(reducer = f)
  def withShouldUpdate(f: OldNewSelf[SLF] => Boolean) =
    this.copy(shouldUpdate = Some(f))
  def withWillUpdate(f: OldNewSelf[SLF] => Unit) =
    this.copy(willUpdate = Some(f))
  def withDidUpdate(f: OldNewSelf[SLF] => Unit) =
    this.copy(didUpdate = Some(f))
  def withRetainedProps(rp: RP) = this.copy(retainedProps = Some(rp))
  def withSubscriptions(subs: SLF => Seq[Subscription]) =
    this.copy(subscriptions = subs)
  def withDidCatch(f: (SLF, js.Error, ErrorInfo) => Unit) = this.copy(didCatch = Some(f))

  /**
    * Escape hatch. We need to remove this, it represents props *not* coming in
    * not through "make" parameters.
    */
  def withContextTypes(ct: js.Object) = this.copy(contextTypes = Some(ct))

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
  def wrapScalaForJs[P <: js.Object](jsPropsToScala: P => ComponentSpec[S, RP, A, _]): ReactClass = {
    val dyn = this.reactClassInternal.asInstanceOf[js.Dynamic]
    dyn.prototype.jsPropsToScala = jsPropsToScala.asInstanceOf[js.Any]
    this.reactClassInternal
  }
}

/**
  * Scala-side "this/self" used to call the scala react methods provided by a
  * scala Component. Lifecycle functions receives an instance as the first
  * parameter. You can extend it to add additional content if you create
  * your own "component creation" API.
  */
trait Self[S, RP, A] {
  type HandleArg <: Self[S, RP, A]

  /** Component state. */
  def state: Option[S]

  /** Retained props. */
  def retainedProps: Option[RP]

  /** Execute an action in the component's reducer. Should this be wrapped in an effect? */
  def send(a: A): Unit

  /** Callback handling. Curry your function down to accept just a single "self" arg. */
  def handle(cb: HandleArg => Unit): Unit
}

/** Allows prev/next comparisons. */
class OldNewSelf[SLF](val oldSelf: SLF, val newSelf: SLF) extends js.Object

/**
  * In the js-side "class" component, the props holds a special member called scalaProps.
  * We cast the js object to this structure to extract out scalaProps on the scala-side,
  * which could be null or undefined. P is a Component typically but could be any
  * type that you want `scalaProps` to be. P is not the same as retained props.
  */
@js.native
trait JsComponentThisProps[P] extends js.Object {

  /** We hang our interop component on this property. */
  val scalaProps: js.UndefOr[P] = js.native
}

/**
  * A js-side component as seen by scala. scalareact.createClass creates a react
  * "class" component. In the shim/proxy methods scala side, the js "this" value
  * can be exposed as the following type which should represent that "class"
  * component. This API only needs to access to a few values/methods.  This can
  * also be overlaid wih a ComponentSpec.reactInternalClass.
  *
  */
@js.native
trait JsComponentThis[JsProps, JsState, NewComponent] extends js.Object {

  /** this.state */
  val state: JsState

  /** this.props */
  val props: JsProps

  /** this.setState */
  def setState(set: js.Function1[JsState, JsState], onComplete: () => Unit = () => ()): Unit

  /**
    * Convert raw js this.props to a scala side object. This is only used when
    * wrapping a non-scala (js side defined) component.
    */
  val jsPropsToScala: js.UndefOr[js.Object => NewComponent]
}

/**
  * js-side this.state proxy. We keep it as js-object so you can still
  * manipulate it from javascript if you need to.
30  */
trait TotalState[S, RP, A, SLF] extends js.Object {

  /** Actual client provided state. */
  val scalaState: Option[S]

  /** Version number of state i.e. optimistic concurrency. */
  val scalaStateVersion: Int
  var scalaStateVersionUsedToComputeSubelements: Int

  /** Side effects to execute, if any. */
  val sideEffects: Seq[SLF => Unit]
}

// State update ADT
sealed trait StateUpdate[S, SLF]
case class NoUpdate[S, SLF]() extends StateUpdate[S, SLF]
case class Update[S, SLF](s: Option[S]) extends StateUpdate[S, SLF]
case class SilentUpdate[S, SLF](s: Option[S]) extends StateUpdate[S, SLF]
case class SideEffects[S, SLF](effect: SLF => Unit) extends StateUpdate[S, SLF]
case class UpdateWithSideEffects[S, SLF](s: Option[S], effect: SLF => Unit) extends StateUpdate[S, SLF]
case class SilentUpdateWithSideEffects[S, SLF](s: Option[S], effect: SLF => Unit) extends StateUpdate[S, SLF]

/**
  * Instead of exposing the ADT to the client, we use a smart constructor at the
  * cost of an extra object allocation. Now we can change the ADT over time. Use
  * `ReducerResult[S, SLF]#UpdateType` for the type when you need to refer to
  * the ADT type.
  */
case class ReducerResult[S, SLF]() {

  type UpdateType = StateUpdate[S, SLF]
  type UpdateEffect = SLF => Unit

  lazy val skip: UpdateType = NoUpdate()
  def update(s: Option[S]): UpdateType = Update(s)
  def silent(s: Option[S]): UpdateType = SilentUpdate(s)
  def effect(effect: UpdateEffect): UpdateType = SideEffects(effect)
  def updateAndEffect(s: Option[S], effect: UpdateEffect = _ => Unit): UpdateType =
    UpdateWithSideEffects(s, effect)
  def silentAndEffect(s: Option[S], effect: UpdateEffect = _ => Unit): UpdateType =
    SilentUpdateWithSideEffects(s, effect)
}

object elements {

  /**
    * If the props arg contains a `scalaProps` member return it
    * directly. Otherwise, convert the entire js props object using
    * jsPropsToScala. The scala-side proxy uses this to function extract out
    * "scala props" stashed in js-side props. js-side props are always a
    * a js object.
    *
    * For example, P is typically a Component.
    */
  def convertPropsIfTheyAreFromJs[P, S, RP, A](props: JsComponentThisProps[P], jsPropsToScala: Option[js.Object => P], debugName: String): P = {
    val scalaProps: Option[P] = props.scalaProps.toOption
    (scalaProps, jsPropsToScala) match {
      // order is important, check for scalaProps first
      case (Some(jsprops), _) => jsprops
      case (None, Some(toScalaProps)) => toScalaProps(props)
      case (None, None) =>
        throw new IllegalStateException(
          s"A JS component called scala component $debugName " +
            "which did not implement the JS->Scala React props conversion.")
    }
  }

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
  def element(component: Component[_, _, _, _], key: Option[String] = None, ref: Option[RefCb] = None): ReactElement = {
    component.jsElementWrapped match {
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
  def createElement(component: Component[_, _, _, _], key: Option[String] = None, ref: Option[RefCb] = None) = element(component, key, ref)

  /** Convert *anything* to what you assert is a js.Any value. Very dangerous. */
  private[this] def toAny(o: scala.Any): js.Any = o.asInstanceOf[js.Any]

  /** Same comment as `toAny`. Very dangerous */
  private[this] def toDynamic(o: scala.Any): js.Dynamic =
    o.asInstanceOf[js.Dynamic]

  /**
    * Create an object to pass to reactCreateClass. Since the actual definition
    * of SLF is client defined you still need to define a `mkSelf` function (and
    * displayName). This is an interop class that when created, creates a
    * js class, but we only care about it as a js.Object. Hence methods that will
    * get picked up by reactCreateClass are vals and ThisFunctions. Other methods
    * on the trait are fine and turn into class methods. This approch allows
    * easier extensibility e.g. override some of the abstract types in subclasses.
    */
  abstract class StandardProxy[S, RP, A] extends Proxy[S, RP, A] {
    type SLF <: Self[S, RP, A]
    type TheComponent = ComponentSpec[S, RP, A, SLF]
    type PropsType = TheComponent
    type ThisSelfProps = JsComponentThisProps[PropsType]
    type State = TotalState[S, RP, A, SLF]
    type ThisSelf = JsComponentThis[ThisSelfProps, State, TheComponent]

    // convenience function
    def convertProps(props: ThisSelfProps, convert: js.UndefOr[js.Object => PropsType], debugName: String) =
      convertPropsIfTheyAreFromJs[PropsType, S, RP, A](props, convert.toOption, displayName)

    // convenience function
    def mkState(s: Option[S], v: Int, sv: Int, e: Seq[SLF => Unit]): State =
      new TotalState[S, RP, A, SLF] {
        val scalaState = s
        val scalaStateVersion = v
        var scalaStateVersionUsedToComputeSubelements = sv
        val sideEffects = e
      }

    // use null to avoid allocation
    var subscriptions: Seq[() => Unit] = null

    // adding sideeffects to start of list is significant in the processing algorithm
    def transitionNextTotalState(curTotalState: State, scalaStateUpdate: StateUpdate[S, SLF]): State = {
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
    }

    //val componentWillUnmount: js.ThisFunction0[ThisSelf, State] = (thisJs: ThisSelf) => {
    val componentWillUnmount = thisJs => {
      val newConvertedScalaProps =
        convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
      val component = newConvertedScalaProps
      val curState = thisJs.state
      val curScalaState = curState.scalaState

      component.willUnmount match {
        case Some(wu) =>
          val self =
            mkSelf(thisJs, curScalaState, component.retainedProps)
          wu(self)
        case _ => // do nothing
      }
      // call unmount subscription callbacks
      subscriptions.foreach(_())
    }

    val componentWillReceiveProps = (thisJs, nextProps) => {
      //println(s"$displayName:CreateClassOpts.componentWillReceiveProps: nextProps ${PrettyJson.render(nextProps)}")
      val newConvertedScalaProps =
        convertProps(nextProps, thisJs.jsPropsToScala, displayName)
      val newComponent = newConvertedScalaProps
      newComponent.willReceiveProps match {
        case Some(wrp) =>
          val oldJsProps = thisJs.props
          val oldConvertedScalaProps =
            if (nextProps == oldJsProps) newConvertedScalaProps
            else convertProps(oldJsProps, thisJs.jsPropsToScala, displayName)
          val oldComponent = oldConvertedScalaProps
          thisJs.setState(curTotalState => {
            val curScalaState = curTotalState.scalaState
            val curScalaStateVersion = curTotalState.scalaStateVersion
            val os = mkSelf(thisJs, curScalaState, oldComponent.retainedProps)
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

    val componentDidUpdate = (thisJs, prevProps, prevState) => {
      //println(s"$debugName:CreateClassOpts.componentDidUpdate:\nprevProps ${PrettyJson.render(prevProps)}\nprevState: ${PrettyJson.render(prevState)}")
      val newJsProps = thisJs.props
      val curState = thisJs.state
      val curScalaState = curState.scalaState
      val newConvertedScalaProps =
        convertProps(newJsProps, thisJs.jsPropsToScala, displayName)
      val newComponent = newConvertedScalaProps

      newComponent.didUpdate match {
        case Some(du) =>
          val oldComponent =
            if (prevProps == newJsProps) newConvertedScalaProps
            else convertProps(prevProps, thisJs.jsPropsToScala, displayName)
          val prevScalaState = prevState.scalaState
          val ns =
            mkSelf(thisJs, curScalaState, newComponent.retainedProps)
          val os =
            mkSelf(thisJs, prevScalaState, oldComponent.retainedProps)
          du(new OldNewSelf(os, ns))
        case _ => // do nothing!
      }
    }

    val componentDidMount = thisJs => {
      //println(s"$debugName:CreateClassOpts.componentDidMount")
      val component =
        convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
      val curTotalState = thisJs.state
      val curScalaState = curTotalState.scalaState
      val self = mkSelf(thisJs, curScalaState, component.retainedProps)

      // call subscriptions
      //println(s"calling subscriptions ${component.subscriptions}, ${component.subscriptions}")
      subscriptions = component.subscriptions(self).map(_())

      component.didMount match {
        case Some(dm) =>
          val scalaStateUpdate = dm(self, ReducerResult())
          val nextTotalState =
            transitionNextTotalState(curTotalState, scalaStateUpdate)
          //println(s"$debugName:CreateClassOpts.componentDidMount:\nnext: ${PrettyJson.render(nextTotalState)},\ncurrent: ${PrettyJson.render(curTotalState)}")
          if (nextTotalState.scalaStateVersion != curTotalState.scalaStateVersion)
            thisJs.setState(_ => nextTotalState)
        case _ => // do nothing!
      }
    }

    val getInitialState = thisJs => {
      val component =
        convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
      val x = mkState(component.initialState.flatMap(_()), 1, 1, Seq())

      //println(s"$debugName:getInitialState ${PrettyJson.render(x)}, ${component.initialState.flatMap(_())}")
      x
    }

    val componentDidCatch = (thisJs, error, errorInfo) => {
      val component = convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
      component.didCatch match {
        case Some(dc) =>
          val state = thisJs.state.scalaState
          val self = mkSelf(thisJs, state, component.retainedProps)
          dc(self, error, errorInfo)
        case _ => // do nothing
      }
    }

    val componentWillUpdate = (thisJs, nextProps, nextState) => {
      //println(s"$debugName:CreateClassOpts.componentWillUpdate")
      val newConvertedScalaProps =
        convertProps(nextProps, thisJs.jsPropsToScala, displayName)
      val newComponent = newConvertedScalaProps
      newComponent.willUpdate match {
        case Some(wu) =>
          val oldJsProps = thisJs.props
          /* Avoid converting again the props that are just the same as curProps. */
          val oldConvertedScalaProps =
            if (nextProps == oldJsProps) newConvertedScalaProps
            else convertProps(oldJsProps, thisJs.jsPropsToScala, displayName)
          val oldComponent = oldConvertedScalaProps
          val curState = thisJs.state
          val curScalaState = curState.scalaState
          val nextScalaState = nextState.scalaState
          val newSelf =
            mkSelf(thisJs, nextScalaState, newComponent.retainedProps)
          val oldSelf =
            mkSelf(thisJs, curScalaState, oldComponent.retainedProps)
          wu(new OldNewSelf(oldSelf, newSelf))
        case _ => // do nothing
      }
    }

    val shouldComponentUpdate = (thisJs, nextJsProps, nextState) => {
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

      val nextScalaState = nextState.scalaState
      val newSelf =
        mkSelf(thisJs, nextScalaState, newComponent.retainedProps)
      val ret =
        newComponent.shouldUpdate match {
          case Some(su) if (warrentsUpdate) =>
            val curState = thisJs.state
            val curScalaState = curState.scalaState
            /* bypass this##self call for small perf boost */
            val oldSelf =
              mkSelf(thisJs, curScalaState, oldComponent.retainedProps)
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

    def sendMethod(thisJs: ThisSelf, action: A): Unit = {
      //println(s"$displayName:CreateClasOpts.sendMethod: $action")
      val convertedScalaProps =
        convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
      val component = convertedScalaProps
      // Allow side-effects to be executed here. Return inside of setState
      // means reactjs.setStates will not update the state.
      thisJs.setState(curTotalState => {
        val curScalaState = curTotalState.scalaState
        val scalaStateUpdate =
          component.reducer(action, curScalaState, ReducerResult())
        if (scalaStateUpdate == NoUpdate()) null
        else {
          val nextTotalState =
            transitionNextTotalState(curTotalState, scalaStateUpdate)
          if (nextTotalState.scalaStateVersion != curTotalState.scalaStateVersion)
            nextTotalState
          else null
        }
      })
    }

    def handleMethod(thisJs: ThisSelf, cb: SLF => Unit): Unit = {
      //println(s"$displayName:CreateClassOpts.handleMethod: $cb")
      val component =
        convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
      val curState = thisJs.state // this is the actual react js state, which is TotalState
      val curScalaState = curState.scalaState
      val self = mkSelf(thisJs, curScalaState, component.retainedProps)
      cb(self)
    }

    val render = thisJs => {
      // call the real render method!
      val component =
        convertProps(thisJs.props, thisJs.jsPropsToScala, displayName)
      // create the fake "self" structure for render
      val curState = thisJs.state // this is the actual react js state, which is TotalState
      val curScalaState = curState.scalaState
      val self = mkSelf(thisJs, curScalaState, component.retainedProps)
      //println(s"$debugName:render: cur total state ${PrettyJson.render(curState)}")
      //println(s"$debugName:CreateClassOpts.render: cur scala state ${self.state}")
      component.render(self)
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
  def basicComponent[S, RP, A](debugNameArg: String) = {
    class BasicProxy[S, RP, A] extends StandardProxy[S, RP, A] {
      type SLF = Self[S, RP, A]
      val displayName: String = debugNameArg
      def mkSelf(thisJs: ThisSelf, sopt: Option[S], popt: Option[RP]): SLF = new Self[S, RP, A] {
        type HandleArg = SLF
        val state = sopt
        val retainedProps = popt
        def send(a: A) = sendMethod(thisJs, a)
        def handle(cb: HandleArg => Unit) = handleMethod(thisJs, cb)
      }
      val contextTypes = js.undefined
    }
    val proxy = new BasicProxy[S, RP, A]()
    ComponentSpec[S, RP, A, proxy.SLF](
      debugName = debugNameArg,
      reactClassInternal = reactCreateClass(proxy),
      render = _ => stringToElement("Not Implemented"),
    )
  }

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
        }
      // the cast on this should not be needed
      val contextTypes = contextTypes.fold(js.undefined.asInstanceOf[js.UndefOr[js.Object]])(x => x)
    }
    val proxy = new BasicProxy[S, RP, A, C]()
    ComponentSpec[S, RP, A, proxy.SLF](
      debugName = debugNameArg,
      reactClassInternal = reactCreateClass(proxy),
      render = _ => stringToElement("Not Implemented"),
    )
  }

  /** Stateless component, no props. */
  def statelessComponent(debugName: String) =
    basicComponent[Stateless, NoRetainedProps, Actionless](debugName)

  /** Stateless, with retained props. */
  def statelessComponentWithRetainedProps[RP](debugName: String) =
    basicComponent[Stateless, RP, Actionless](debugName)

  /** Stateful. */
  def reducerComponent[S, A](debugName: String) =
    basicComponent[S, NoRetainedProps, A](debugName)

  /** Stateful, with retained props. */
  def reducerComponentWithRetainedProps[S, RP, A](debugName: String) =
    basicComponent[S, RP, A](debugName)

  /** Like nullElement but a Component. Useful for optional children. */
  val nullComponent = statelessComponent("null").withRender(self => nullElement)

  /** Clone a ReactElement and add new props. You should not use this if you can avoid it. */
  def cloneElement(element: ReactElement, props: js.Object): ReactElement =
    JSReact.cloneElement(element, props.asInstanceOf[js.Dynamic])

  /**
    * Wrap a js side component for scala side usage. You also need to import the
    * react class using standard scala.js import mechanisms and write a "make"
    * function to create your props from "make" parameters.
    */
  def wrapJsForScala[P <: js.Object](reactClass: ReactJSComponent, props: P, children: ReactNode*): Component[Stateless, NoRetainedProps, Actionless, _] = {
    WrapProps.wrapJsForScala(reactClass, props, children: _*)
  }

  /** Turn a ReactRef to a js.Dynamic so you can call class methods directly. */
  def refToJs[T <: ReactRef](ref: T): js.Dynamic = ref.asInstanceOf[js.Dynamic]

  /** Create a fragment element. Per the API, you are only allowed an optional key. */
  def fragmentElement(key: Option[String] = None)(children: ReactNode*) =
    React.createFragment(key, children: _*)
}
