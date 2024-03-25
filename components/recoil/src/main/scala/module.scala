/*
 * Copyright (c) 2018 The Trapelo Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package recoil

import scala.scalajs.js
import js.JSConverters._
import js.|, js.annotation._
import react._

@js.native
trait SnapshotID extends js.Any

// Should be creatable so can set snapshot on root when needed rework this.
@js.native
trait Snapshot extends js.Object {
  def getID(): SnapshotID = js.native
  def getLoadable[T](recoilValue: RecoilValue[T]): Loadable[T] = js.native
  def getPromise[T](recoilValue: RecoilValue[T]): js.Promise[T] = js.native
  def map(cb: js.Function1[MutableSnapshot, Unit]): Snapshot = js.native
  def asyncMap(cb: js.Function1[MutableSnapshot, js.Thenable[Unit]]): js.Promise[Snapshot] = js.native
  
  // unstable API
  def getNodes_UNSTABLE(opts: js.Dynamic): js.Iterable[RecoilValue[Any]]
}

// keep in sync with SetRecoilState, ResetRecoilState
@js.native
trait MutableSnapshot extends Snapshot {
  def set[T](state: RecoilState[T], value: T | DefaultValue | js.Function1[T, T | DefaultValue]): Unit = js.native
  def reset[T](state: RecoilState[T]): Unit = js.native
}

trait PersistenceSettings[T] {
  val `type`: String // "none" or "url"
  var backButton: js.UndefOr[Boolean] = js.undefined
  def validator(arg: Any, defaultValue: DefaultValue): T | DefaultValue
}

object RecoilRoot {
  @js.native
  @JSImport("recoil", "RecoilRoot")
  object JS extends ReactJSComponent

  trait SetOptions extends js.Object {
    def set[T](recoilVal: RecoilState[T], newVal: T): Unit
    def setUnvalidatedAtomValues(atomMap: js.Dictionary[js.Any]): Unit
  }

  trait Props extends js.Object {
    var initializeState: js.UndefOr[js.Function1[SetOptions, Unit]] = js.undefined
  }

  def apply(props: Props)(children: ReactNode*) = createElement(JS, props, children*)
  def apply(children: ReactNode*) = createElement(JS, null, children*)
}

trait AtomOptions[T] extends js.Object {
  val key: String
  val default: FlexiValue[T]
  var persistence_UNSTABLE: js.UndefOr[PersistenceSettings[T]] = js.undefined
  var dangerouslyAllowMutability: js.UndefOr[Boolean] = js.undefined
}

object AtomOptions {
  def apply[T](k: String, d: FlexiValue[T]) =
    new AtomOptions[T] {
      val key = k
      val default = d
    }
}

@js.native
trait ReadOnlyAccessors extends js.Object {
  def get[A](value: RecoilValue[A]): A = js.native
  @JSName("get")
  def value[A](rvalue: RecoilValue[A]): A = js.native
}

/** Accessors but with a type definition that can be convenient if
 * the output type is long.
 */
@js.native
trait ReadOnlyAccessorsWithOutput[T] extends js.Object {
  type Output = T
  def get[A](value: RecoilValue[A]): A = js.native
  @JSName("get")
  def value[A](rvalue: RecoilValue[A]): A = js.native
}

@js.native
trait ReadWriteAccessors extends ReadOnlyAccessors {
  def set[A](atom: RecoilState[A], newValue: A | DefaultValue | js.Function1[A, A | DefaultValue]): Unit = js.native
  @JSName("set")
  def update[A](atom: RecoilState[A], newValue: A | DefaultValue | js.Function1[A, A | DefaultValue]): Unit = js.native
  def reset(atom: RecoilState[?]): Unit = js.native
}

trait ReadOnlySelectorOptions[T] extends js.Object {
  val key: NodeKey
  def get(accessors: ReadOnlyAccessors): Return[T]
  var dangerouslyAllowMutability: js.UndefOr[Boolean] = js.undefined
  var cacheImplementation_UNSTABLE: js.UndefOr[js.Function0[CacheImplementation[Loadable[T]]]] = js.undefined
}

trait ReadOnlySelectorOptions2[T] extends js.Object {
  val key: NodeKey
  val get: js.Function1[ReadOnlyAccessors, Return[T]]
  var dangerouslyAllowMutability: js.UndefOr[Boolean] = js.undefined
}

trait WritableSelectorOptions[T] extends ReadOnlySelectorOptions[T] {
  def set[A](accessors: ReadWriteAccessors, value: A | DefaultValue): Unit
}

@js.native
trait ResolvedLoadablePromiseInfo[T] extends js.Object {
  val value: T = js.native
}

@js.native
abstract trait LoadableState extends js.Any
object LoadableState {
  val hasValue = "hasValue".asInstanceOf[LoadableState]
  val hasError = "hasError".asInstanceOf[LoadableState]
  val loading = "loading".asInstanceOf[LoadableState]
}

@js.native
trait Accessors[+T] extends js.Object {
  /** Throw promise or error, or return the value. */
  def getValue(): T = js.native
  def toPromise[U >: T](): LoadablePromise[U] = js.native
  def valueMaybe(): js.UndefOr[T] = js.native
  def valueOrThrow(): T = js.native
  def errorMaybe(): js.UndefOr[js.Error] = js.native
  def errorOrThrow(): js.Error = js.native
  def promiseMaybe(): js.UndefOr[js.Promise[T]] = js.native
  def promiseOrThrow(): js.Promise[T] = js.native

  /** Suppose to be a map function but its just a mess. */
  @JSName("map")
  def _map[S](thunk: js.Function1[Any, S | js.Thenable[S]]): Loadable[S] = js.native
  @JSName("map")
  def map[S](f: js.Function1[T, S]): Loadable[S] = js.native
  @JSName("map")
  def flatMap[S](f: js.Function1[T, LoadablePromise[S]]): Loadable[S] = js.native
}

@js.native
abstract trait LoadableValue[+T] extends js.Object

@js.native
trait LoadableStrict[+T] extends LoadableValue[T] {
  val state: "hasValue"// = js.native
  val contents: T = js.native
}
@js.native
trait LoadableError[+T] extends LoadableValue[T] {
  val state: "hasError"// = js.native
  val contents: js.Error = js.native
}
@js.native
trait LoadableEffect[+T] extends LoadableValue[T] with Accessors[T] {
  val state: "loading"// = js.native
  def contents[U >: T]: LoadablePromise[U] = js.native
}

@js.native
trait Loadable[T] extends Accessors[T] {
  // you can exhaust switch on this...painful...need a GADT.
  val state: LoadableState = js.native
  val contents: T | js.Error | LoadablePromise[T] = js.native
  @JSName("contents")
  val contentsValue: T = js.native
  @JSName("contents")
  val contentsError: js.Error = js.native
  @JSName("contents")
  val contentsPromise: LoadablePromise[T] = js.native
}

@js.native
trait SetterOrUpdater[T] extends js.Object {
  def apply(t: T): Unit = js.native
  def apply(f: js.Function1[T, T]): Unit = js.native
}

/** Generic selector, state handle. */
@js.native
abstract trait AbstractRecoilValue[+T] extends js.Object {
  val key: String
}

@js.native
trait AbstractRecoilValueReadOnly[+T] extends js.Object {
  val key: String
}

/** Writable */
@js.native
abstract trait RecoilState[+T] extends AbstractRecoilValue[T]

/** Read only. */
@js.native
abstract trait RecoilValueReadOnly[+T] extends AbstractRecoilValueReadOnly[T]

@js.native
trait CallbackInterface extends js.Object {
  val snapshot: Snapshot = js.native
  def gotoSnapshot(snapshot: Snapshot): Unit = js.native
  def set[A](value: RecoilState[A], valOrUpdater: SetValOrUpdater[A]): Unit = js.native
  def reset(value: RecoilState[?]): Unit = js.native
}

@js.native
trait recoil_module extends js.Object {
  def atom[T](options: AtomOptions[T]): RecoilState[T] = js.native
  //def selector[T](options: AllSelectorOptions[T]): RecoilValue[T] = js.native
  @JSName("selector")
  def readonlySelector[T](options: ReadOnlySelectorOptions[T]): RecoilValueReadOnly[T] = js.native
  @JSName("selector")
  def writableSelector[T](options: WritableSelectorOptions[T]): RecoilState[T] = js.native

  def useRecoilState[T](atom: RecoilState[T]): js.Tuple2[T, SetterOrUpdater[T]] = js.native

  def useRecoilStateLoadable[T](state: RecoilState[T]): js.Tuple2[Loadable[T], SetterOrUpdater[T]] = js.native

  /** If pending, throws Promise to suspend, or throws an error. */
  def useRecoilValue[T](selector: RecoilValue[T]): T = js.native
  def useRecoilValueLoadable[T](value: RecoilValue[T]): Loadable[T] = js.native
  def useSetRecoilState[T](state: RecoilState[T]): SetterOrUpdater[T] = js.native
  def useResetRecoilState[T](state: RecoilState[T]): Resetter = js.native
  @JSName("useRecoilCallback")
  def useRecoilCallback_UNTYPED[T](f: js.Function, deps: js.UndefOr[Dependencies] = js.undefined): js.Any =
    js.native
  def isRecoilValue(value: Any): Boolean = js.native
}

@js.native
@JSImport("recoil", "DefaultValue")
class DefaultValue() extends js.Object

@js.native
@JSImport("recoil", JSImport.Namespace)
object module extends recoil_module

trait hooks {

  def useRecoilCallbackMounting[T](f: js.Function1[CallbackInterface, Return[T]]) =
    module
      .useRecoilCallback_UNTYPED[T](f, emptyDependencies)
      .asInstanceOf[js.Function1[CallbackInterface, js.Function0[T]]]
  def useRecoilCallback[T](deps: AllType*)(f: js.Function1[CallbackInterface, Return[T]]) =
    if deps.length == 0 then
      module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function1[CallbackInterface, js.Function0[T]]]
    else
      module
        .useRecoilCallback_UNTYPED[T](f, deps.toJSArray)
        .asInstanceOf[js.Function1[CallbackInterface, js.Function0[T]]]
  def useRecoilCallbackA[T](deps: Dependencies)(f: js.Function1[CallbackInterface, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f, deps).asInstanceOf[js.Function1[CallbackInterface, js.Function0[T]]]

  def useRecoilCallbackMounting1[A1, T](f: js.Function2[CallbackInterface, A1, Return[T]]) =
    module
      .useRecoilCallback_UNTYPED[T](f, emptyDependencies)
      .asInstanceOf[js.Function1[CallbackInterface, js.Function1[A1, T]]]
  def useRecoilCallback1[A1, T](deps: AllType*)(f: js.Function2[CallbackInterface, A1, Return[T]]) =
    if deps.length == 0 then
      module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function1[CallbackInterface, js.Function1[A1, T]]]
    else
      module
        .useRecoilCallback_UNTYPED[T](f, deps.toJSArray)
        .asInstanceOf[js.Function1[CallbackInterface, js.Function1[A1, T]]]
  def useRecoilCallback1A[A1, T](deps: Dependencies)(f: js.Function2[CallbackInterface, A1, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f, deps).asInstanceOf[js.Function1[CallbackInterface, js.Function1[A1, T]]]

//   def useRecoilCallbackMounting2[A1, A2, T](f: js.Function3[CallbackInterface, A1, A2, Return[T]]) =
//     module.useRecoilCallback_UNTYPED[T](f, emptyDependencies).asInstanceOf[js.Function2[A1, A2, T]]
//   def useRecoilCallback2[A1, A2, T](deps: AllType*)(f: js.Function3[CallbackInterface, A1, A2, Return[T]]) =
//     if (deps.length == 0) module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function2[A1, A2, T]]
//     else module.useRecoilCallback_UNTYPED[T](f, deps.toJSArray).asInstanceOf[js.Function2[A1, A2, T]]
//   def useRecoilCallback2A[A1, A2, T](deps: Dependencies)(f: js.Function3[CallbackInterface, A1, A2, Return[T]]) =
//     module.useRecoilCallback_UNTYPED[T](f, deps).asInstanceOf[js.Function2[A1, A2, T]]
//
//
//   def useRecoilCallbackMounting3[A1, A2, A3, T](f: js.Function4[CallbackInterface, A1, A2, A3, Return[T]]) =
//     module.useRecoilCallback_UNTYPED[T](f, emptyDependencies).asInstanceOf[js.Function3[A1, A2, A3, T]]
//   def useRecoilCallback3[A1, A2, A3, T](deps: AllType*)(f: js.Function4[CallbackInterface, A1, A2, A3, Return[T]]) =
//     if (deps.length == 0) module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function3[A1, A2, A3, T]]
//     else module.useRecoilCallback_UNTYPED[T](f, deps.toJSArray).asInstanceOf[js.Function3[A1, A2, A3, T]]
//   def useRecoilCallback3A[A1, A2, A3, T](deps: Dependencies)(
//     f: js.Function4[CallbackInterface, A1, A2, A3, Return[T]]) =
//     module.useRecoilCallback_UNTYPED[T](f, deps).asInstanceOf[js.Function3[A1, A2, A3, T]]
//
//
//   def useRecoilCallbackMounting4[A1, A2, A3, A4, T](f: js.Function5[CallbackInterface, A1, A2, A3, A4, Return[T]]) =
//     module.useRecoilCallback_UNTYPED[T](f, emptyDependencies).asInstanceOf[js.Function4[A1, A2, A3, A4, T]]
//   def useRecoilCallback4[A1, A2, A3, A4, T](deps: AllType*)(
//     f: js.Function5[CallbackInterface, A1, A2, A3, A4, Return[T]]) =
//     if (deps.length == 0) module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function4[A1, A2, A3, A4, T]]
//     else module.useRecoilCallback_UNTYPED[T](f, deps.toJSArray).asInstanceOf[js.Function4[A1, A2, A3, A4, T]]
//   def useRecoilCallback4A[A1, A2, A3, A4, T](deps: Dependencies)(
//     f: js.Function5[CallbackInterface, A1, A2, A3, A4, Return[T]]) =
//     module.useRecoilCallback_UNTYPED[T](f, deps).asInstanceOf[js.Function4[A1, A2, A3, A4, T]]
//
//   def useRecoilCallbackMounting5[A1, A2, A3, A4, A5, T](f: js.Function6[CallbackInterface, A1, A2, A3, A4, A5, Return[T]]) =
//     module.useRecoilCallback_UNTYPED[T](f, emptyDependencies).asInstanceOf[js.Function5[A1, A2, A3, A4, A5, T]]
//   def useRecoilCallback5[A1, A2, A3, A4, A5, T](deps: AllType*)(
//     f: js.Function6[CallbackInterface, A1, A2, A3, A4, A5, Return[T]]) =
//     if (deps.length == 0) module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function5[A1, A2, A3, A4, A5, T]]
//     else module.useRecoilCallback_UNTYPED[T](f, deps.toJSArray).asInstanceOf[js.Function5[A1, A2, A3, A4, A5, T]]
//   def useRecoilCallback5A[A1, A2, A3, A4, A5, T](deps: Dependencies)(
//     f: js.Function6[CallbackInterface, A1, A2, A3, A4, A5, Return[T]]) =
//     module.useRecoilCallback_UNTYPED[T](f, deps).asInstanceOf[js.Function5[A1, A2, A3, A4, A5, T]]

  def atom[T](options: AtomOptions[T]) = module.atom[T](options)

  def unsafeReadonlySelector[T](options: js.Dynamic) =
    module.readonlySelector[T](options.asInstanceOf[ReadOnlySelectorOptions[T]])
  def readonlySelector[T](options: ReadOnlySelectorOptions[T]) = module.readonlySelector[T](options)
  def writableSelector[T](options: WritableSelectorOptions[T]) = module.writableSelector[T](options)

  def useRecoilState[T](atom: RecoilState[T]): (T, SetterOrUpdater[T]) =
    module.useRecoilState[T](atom)

  def useRecoilStateLoadable[T](state: RecoilState[T]): (Loadable[T], SetterOrUpdater[T]) =
    module.useRecoilStateLoadable[T](state)

  /** If pending, throws Promise to suspend, or throws an error. */
  def useRecoilValue[T](selector: RecoilValue[T]) = module.useRecoilValue[T](selector)
  def useRecoilValueLoadable[T](value: RecoilValue[T]) = module.useRecoilValueLoadable[T](value)
  def useSetRecoilState[T](state: RecoilState[T]) = module.useSetRecoilState[T](state)
  def useResetRecoilState[T](state: RecoilState[T]) = module.useResetRecoilState[T](state)
  def isRecoilValue(value: Any) = module.isRecoilValue(value)

}
