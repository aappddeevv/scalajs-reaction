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

/**
 * Take note of 
 * https://github.com/reasonml/reason-react/blob/1f9372bbd36494c14ed7ceeaaf0fe8352b62a729/src/ReasonReact.re
 * https://github.com/reasonml/reason-react/pull/277/files/c994aa03fbb0337f0c0aaa3709949ecc35e9e0c0..1f9372bbd36494c14ed7ceeaaf0fe8352b62a729?utf8=%E2%9C%93&diff=split
 */

/** Component ADT using scala.js "record" type. */
sealed trait ComponentX extends js.Object

trait ReducerComponentX extends ComponentX {
  type State
  type Action
  type ReducerSelf <: ReducerSelfLike
  trait ReducerSelfLike {
    def handle(s: ReducerSelf): Unit
    def onUnmount(cb: OnUnmount): Unit
    def send(action: Action): Unit
    def state: State
  }
  val reactClassInternal: ReactClass
  val debugName: String
  val reducer: (Action, State) => Unit
  val deriveStateFromProps: State => State
  val didMount: ReducerSelf => Unit
  val didUpdate: OldNewSelf[ReducerSelf] => Unit
  val didCatch: (ReducerSelf, js.Any, js.Any) => Unit
  val willUnmount: ReducerSelf => Unit
  val willUpdate: OldNewSelf[ReducerSelf] => Unit
  val shouldUpdate: OldNewSelf[ReducerSelf] => Unit
  val render: ReducerSelf => ReactNode
}

trait StatelessSelf {
  def handle[P <: scala.Any](cb: (P, StatelessSelf) => Unit): P => Unit
  def onUnmount(cb: OnUnmount): Unit
}

/** Programmer customizations. Note that funcs are scala funcs, not js
 * funcs. js.Undef is more friendly than Option when defining an optional
 * method. It is this object that is stored in an element instance's props. See
 * JSComponentProps.
 */
trait StatelessComponent extends ComponentX {
  val reactClassInternal: ReactClass
  val debugName: String
  val didMount: js.UndefOr[StatelessSelf => Unit] = js.undefined
  val didUpdate: js.UndefOr[StatelessSelf => Unit] = js.undefined
  val willUnmount: js.UndefOr[StatelessSelf => Unit] = js.undefined
  val willUpdate: js.UndefOr[StatelessSelf => Unit] = js.undefined
  val shouldUpdate: js.UndefOr[StatelessSelf => Boolean] = js.undefined
  val render: StatelessSelf => ReactNode
}

// explicitly for creating the component
trait StatelessComponentMethods extends ComponentX {
  var didMount: js.UndefOr[StatelessSelf => Unit] = js.undefined
  var didUpdate: js.UndefOr[StatelessSelf => Unit] = js.undefined
  var willUnmount: js.UndefOr[StatelessSelf => Unit] = js.undefined
  var willUpdate: js.UndefOr[StatelessSelf => Unit] = js.undefined
  var shouldUpdate: js.UndefOr[StatelessSelf => Boolean] = js.undefined
  val render: StatelessSelf => ReactNode
}

/** State in js component. Holds our scala.js side state in a redcer component.
 * In a stateful component, it doese not hold anything.
 */
@js.native
trait JSState extends js.Object {
  val scalaState: scala.Any // scala.js side state is often a scala object
}

@js.native
trait JSComponentProps[C <: ComponentX] extends js.Object {
  /** reactjs props holds at least a single member, the scala side ComponentX
    * subclass.
   */
  val scalaProps: js.UndefOr[C] = js.undefined
}

/** When calling into a lifecycle method, the js side "this". Use this trait
 * when all you need to do is get access to the props and constructor as the
 * lifecycle methods are not revealed. This should really be called `JSElement`.
 */
@js.native
trait JSComponent[C <: ComponentX] extends js.Object {
  val __defined: Boolean // if reactClass has ben called with programmer's specializations
  val props: JSComponentProps[C]
  val constructor: JSPropsConverter[C]
  def forceUpdate(): Unit = js.native
}

// /** A "this" that has more than just JSComponent. They both alias the this when
//  * a react method is called.
//  */
// @js.native
// trait JSThis[C <: ComponentX] extends js.Object {
//   var __defined: Boolean // if reactClass has ben called with programmer's specializations
//   val props: JSComponentProps[C]
//   val state: JSState
//   // setState((state, props) => state /* can be null for no update */, () => ())
//   val setState: js.Function2[js.Function2[JSState, js.Object, JSState|Null], js.UndefOr[Unit => Unit], Unit]
//   @JSName("setState")
//   val setStateNoProps: js.Function2[js.Function1[JSState, JSState|Null], js.UndefOr[Unit => Unit], Unit]
//   val constructor: JSPropsConverter[C]
// }

@js.native
trait JSPropsConverter[C <: ComponentX] extends js.Object {
  /** Convert reactjs javascript side props to a scala.js side component */
  val jsPropsToScala: js.UndefOr[js.Function1[js.Object, C]] = js.undefined
}

/** Attach a converter thunk to the reactjs class on the component (under
 * reactClassInternal key).
 */
object wrapScalaForJS {
  // converter is Props => scala.js component (spec)
  def apply(component: js.Dynamic, converter: js.Function1[js.Object, js.Dynamic]): ReactClass = {
    component.reactClassInternal.jsPropsToScala = converter
    component.reactClassInternal.asInstanceOf[ReactClass]
  }
}

// do not use *any* implicit conversions.
// https://github.com/reasonml/reason-react/blob/1f9372bbd36494c14ed7ceeaaf0fe8352b62a729/src/ReasonReact.re
// Why all of this? Improve efficiency/dcrease memory needs by only defining
// methods on the "class" component that the user specifies and enable lazy
// class creation. All of the below is pure javascript in scala.js
object StatelessComponent {

  object JSSpec {

    type JSC = JSComponent[StatelessComponent]

    /** Some of these methods must be callable from the JS side as they are directly
     * attached to the reactjs component class. Some of the methods are called
     * only in scala code. This data structure is used in createClass to create
     * the reactjs class component. Hence, all of the methods/value are
     * available on an element (= component class instancee) when a lifecycle
     * method is called. It does not reveal, however, other methods/value such
     * as props or __defined as does the JSComponent alias. So this data
     * structure acts as both the argument for createClass and an overlay for an
     * instance. (last sentence true, don't think so?)
     */
    trait JSStatelessSpec extends js.Object {
      val displayName: String
      // the OnUnmount callbacks are stored on the scala.js side component
      var subscriptions: js.UndefOr[js.Array[OnUnmount]] = js.undefined
      val self: JSStatelessSpec =>  StatelessSelf // thunk to create self for method callbacks
      val handleMethod: (JSStatelessSpec, (scala.Any, StatelessSelf) => Unit) => (scala.Any => Unit)
      val onUnmountMethod: (JSStatelessSpec, OnUnmount) => Unit
      val getInitialState: js.ThisFunction0[JSC,Unit]
      // optional user specified, undefined by defaulut to avoid overhead
      var componentDidMount: js.UndefOr[js.ThisFunction0[JSC,Unit]] = js.undefined
      var componentDidUpdate: js.UndefOr[js.ThisFunction0[JSC, Unit]] = js.undefined
      var componentWillUnmount: js.UndefOr[js.ThisFunction0[JSC, Unit]] = js.undefined
      var componentWillUpdate: js.UndefOr[js.ThisFunction0[JSC, Unit]] = js.undefined
      var shouldComponentUpdate: js.UndefOr[js.ThisFunction0[JSC, Boolean]] = js.undefined
      // required user specified
      var render: js.UndefOr[js.ThisFunction0[JSC, ReactNode]] = js.undefined
    }

    /**
     * @param props Something that has "scalaProps".
     */
    def convertPropsIfTheyreFromJs(
      props: JSComponentProps[StatelessComponent],
      converter: JSPropsConverter[StatelessComponent],
      debugName: String
    ): StatelessComponent =
      (props.scalaProps.toOption, converter.jsPropsToScala.toOption) match {
        case (Some(c), _) => c.asInstanceOf[StatelessComponent]
        case (None, Some(toScalaProps)) =>
          // no props to convert, what's the argument?
          toScalaProps(lit()).asInstanceOf[StatelessComponent]
        case _ =>
          // should not happen, indicates flaw in program construction
          throw new IllegalStateException("A JS component called the Scala component "
            + debugName
            + " which did not implement the JS=>Scala React props conversion."
          )
      }

    val handleMethod: js.Function2[Unit,Unit,Unit] = { (_,_) => () }
    def setDefined(c: JSC): Unit = c.asInstanceOf[js.Dynamic].__defined = true
    type MaybeList = js.UndefOr[js.Array[OnUnmount]]

    // lazy component initialization based on the customizations.
    def apply(spec: StatelessComponent): JSStatelessSpec = {
      //dom.console.log("stateless enhancing spec", spec)
      // this value is closed over, we reaelly want a "ref" here
      val reactClass: JSStatelessSpec = lit().asInstanceOf[JSStatelessSpec]

      /** Add OnUnmount to the subscriptions list. "this" will be the instance. */
      val theOnUnmountMethod: (JSStatelessSpec, OnUnmount) => Unit = (statelessSpec, cb) => {
        val maybeList: MaybeList = statelessSpec.subscriptions.asInstanceOf[MaybeList]
        maybeList.fold(
          statelessSpec.subscriptions = js.Array(cb)
        )(
          _.push(cb)
        )
      }

      val theHandleMethod: (JSStatelessSpec, (scala.Any, StatelessSelf) => Unit) => (scala.Any => Unit) =
        (spec, cb) => {
          val self = spec.self(spec)
          p => cb(p, self)
      }

      // Inject the scala.js side methods. this is run for the side effect of
      // initing the react class methods.
      val theGetInitialState: js.ThisFunction0[JSC, Unit] = { initThisJs =>
        //dom.console.log("theGetInitialState", initThisJs, initThisJs.asInstanceOf[js.Dynamic].prototype)
        // get scala component when "getInitialState" runs, which is what the user defined
        val component: StatelessComponent =
          convertPropsIfTheyreFromJs(initThisJs.props, initThisJs.constructor, spec.debugName)
        
        if(!js.DynamicImplicits.truthValue(initThisJs.asInstanceOf[js.Dynamic].__defined)) {
          //dom.console.log("theGetInitialState: defining component")
          setDefined(initThisJs)

          val theComponentDidMount: js.UndefOr[js.ThisFunction0[JSC, Unit]] =
            component.didMount.map{ _ => thisJs =>
              val ccomponent: StatelessComponent =
                convertPropsIfTheyreFromJs(thisJs.props, thisJs.constructor, spec.debugName)
              ccomponent.didMount.map{ thunk =>
                val statelessSpec = thisJs.asInstanceOf[JSStatelessSpec]
                val self = statelessSpec.self(statelessSpec)
                thunk(self)
              }
            }

          val theComponentDidUpdate: js.UndefOr[js.ThisFunction0[JSC,Unit]] =
            component.didUpdate.map{ _ => thisJs =>
              val ccomponent: StatelessComponent =
                convertPropsIfTheyreFromJs(thisJs.props, thisJs.constructor, spec.debugName)
              ccomponent.didUpdate.map { thunk =>
                val statelessSpec = thisJs.asInstanceOf[JSStatelessSpec]
                val self = statelessSpec.self(statelessSpec)
                thunk(self)
              }
            }

          val theComponentWillUpdate: js.UndefOr[js.ThisFunction0[JSC,Unit]] =
            component.willUpdate.map{ _ => thisJs =>
              val ccomponent: StatelessComponent =
                convertPropsIfTheyreFromJs(thisJs.props, thisJs.constructor, spec.debugName)
              ccomponent.willUpdate.map { thunk =>
                val statelessSpec = thisJs.asInstanceOf[JSStatelessSpec]
                val self = statelessSpec.self(statelessSpec)
                thunk(self)
              }
            }

          val theComponentShouldUpdate: js.UndefOr[js.ThisFunction0[JSC,Boolean]] =
            component.shouldUpdate.map{ _ => (thisJs: JSC) =>
              val ccomponent: StatelessComponent =
                convertPropsIfTheyreFromJs(thisJs.props, thisJs.constructor, spec.debugName)
              ccomponent.shouldUpdate.map { thunk =>
                val statelessSpec = thisJs.asInstanceOf[JSStatelessSpec]
                val self = statelessSpec.self(statelessSpec)
                thunk(self)
              }.getOrElse(true)
            }

          val theComponentWillUnmount: js.UndefOr[js.ThisFunction0[JSC, Unit]] =
            component.willUnmount.map { _ => thisJs =>
              val ccomponent: StatelessComponent =
                convertPropsIfTheyreFromJs(thisJs.props, thisJs.constructor, spec.debugName)
              ccomponent.willUnmount.map{ thunk =>
                val statelessSpec = thisJs.asInstanceOf[JSStatelessSpec]
                val self = statelessSpec.self(statelessSpec)
                thunk(self)
                // call subscriptions in order, first tin, first called
                val maybeList: MaybeList = statelessSpec.subscriptions.asInstanceOf[MaybeList]
                maybeList.foreach(_.foreach(_()))
              }
            }

          val theRender: js.ThisFunction0[JSC, ReactNode] =  thisJs => {
            val mcomponent: StatelessComponent =
              convertPropsIfTheyreFromJs(thisJs.props, thisJs.constructor, spec.debugName)
            val statelessSpec = thisJs.asInstanceOf[JSStatelessSpec]
            val self = statelessSpec.self(statelessSpec)
            mcomponent.render(self)
          }

          // enhance the instance, in react this was enhancing the spec, why?
          if(reactClass != null) {
            val d = initThisJs.asInstanceOf[js.Dynamic]
            //dom.console.log("theGetInitialState: pre changing prototype", reactClass, d)
            //val rc = reactClass.asInstanceOf[js.Dynamic]
            // why isn't prototype already present?
            //rc.prototype = lit()
            //rc.prototype.render = theRender
            d.componentDidMount = theComponentDidMount
            d.componentWillUnmount = theComponentWillUnmount
            d.compopnentWillUpdate = theComponentWillUpdate
            d.componentShouldUpdate = theComponentShouldUpdate
            d.componentDidUpdate = theComponentDidUpdate
            d.render = theRender
            //dom.console.log("theGetInitialState: post changing prototype", reactClass, d)
          }
        }
      }
      // stop warning message
      theGetInitialState.asInstanceOf[js.Dynamic].isReactClassApproved = true

      val makeSelf: JSStatelessSpec => StatelessSelf = thisJs =>
      new StatelessSelf {
        def handle[P <: scala.Any](cb: (P, StatelessSelf) => Unit): P => Unit =
          thisJs.handleMethod(thisJs, cb.asInstanceOf[(scala.Any, StatelessSelf)=>Unit])
        def onUnmount(cb: OnUnmount): Unit = thisJs.onUnmountMethod(thisJs, cb)
      }

      // assign not in 0.6.26, without ocaml ref, add content to the val
      js.Object.asInstanceOf[js.Dynamic].assign(
        reactClass,
        new JSStatelessSpec {
          val displayName = spec.debugName
          val self = makeSelf
          val getInitialState = theGetInitialState
          // will be autobound to react component instance
          val onUnmountMethod = theOnUnmountMethod
          val handleMethod = theHandleMethod
        }
      ).asInstanceOf[JSStatelessSpec]
      reactClass
    }
  }

  def apply(theDebugName: String) = {
    val spec = new StatelessComponent {
      val reactClassInternal = null
      val debugName = theDebugName
      val render = _ => "RenderNotImplemented".asInstanceOf[ReactNode]
    }
    val madeSpec = JSSpec(spec)
    //dom.console.log("make: madeSpec", madeSpec)
    val reactClassInternal = createClass(madeSpec)
    val x = merge[StatelessComponent](
      spec,
      lit(
        "reactClassInternal" -> reactClassInternal
      ))
    //dom.console.log("make: stateless component", x)
    x
  }

  def element(c: StatelessComponent, key: Option[String]=None, ref: Option[Ref[_]] = None) = {
    jsElementWrappedWeakMap.get(c).toOption match {
      case Some(thunk) => thunk.asInstanceOf[JSElementWrapped](key, ref)
      case _ =>
        //dom.console.log("creating element", c)
        React.createElement0(
          c.reactClassInternal,
          lit(
            "key" -> key.orUndefined,
            "ref" -> ref.orUndefined.asInstanceOf[js.Any],
            "scalaProps" -> c
          ))//()
    }
  }


  // def wrapProps(
  //   component: ImportetdJsComponent,
  //   props: js.Object,
  //   key: js.UndefOr[String] = js.undefined,
  //   ref: js.UndefOr[Ref[_]] = js.undefined,
  //   children: ReactNode*
  // ): ReactElement = {
  //     val props = merge[js.Object](
  //     lit(),
  //     props,
  //     lit("key" -> key, "ref" -> ref)
  //     )
  //   ReactJS.createElement(component, props, children:_*)
  // }

  // val dummy = StatelessComponent("interop")

  // def wrapJsForScala(
  //   reactComponent: ImportedJsComponent,
  //   props: js.Object,
  //   children: ReactNode*
  // ): StatelessComponent = {
  //   val ewrapped = wrapProps(reactComponent, props, children=children)
  //   //jsElementWrappedWeakMap.set(dummy,
  //   dummy
  // }

  /** Used for exporting to JS. */
  type JSElementWrapped = (Option[String], Option[Ref[_]]) => ReactNode
  val jsElementWrappedWeakMap = new WeakMap()//Map[StatelessComponent, JSElementWrapped]()

}
