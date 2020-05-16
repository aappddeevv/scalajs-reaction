package recoil

import scala.scalajs.js
import js.JSConverters._
import js.|, js.annotation._
import react._

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

  def apply(props: Props)(children: ReactNode*) = createElement(JS, props, children: _*)
  def apply(children: ReactNode*) = createElement(JS, null, children: _*)
}

trait AtomOptions[T] extends js.Object {
  val key: String
  val default: T | RecoilValue[T] | js.Promise[T]
  // persistence
  var dangerouslyAllowMutability: js.UndefOr[Boolean] = js.undefined

}

trait GetArgs[T] {
    // val get: GetRecoilValuen[T]
  def get(value: RecoilValue[T]): Return[T]
}

@js.native
trait GetSetResetArgs extends js.Object {
  def set[A](atom: RecoilState[A], newValue: A | DefaultValue | js.Function1[A, A | DefaultValue]): Unit = js.native
  def get[A](atom: RecoilValue[A]): A = js.native
  def reset(atom: RecoilState[_]): Unit = js.native
}

trait ReadOnlySelectorOptions[T] extends js.Object {
  val key: NodeKey
  def get(getter: GetArgs[T]): Return[T]

  /** If undefined, creates a readonly selector. */
  //def set[A]: js.UndefOr[js.Function2[SetArgs, A, Unit]] = js.undefined
  var dangerouslyAllowMutability: js.UndefOr[Boolean] = js.undefined
}

trait WriteableSelectorOptions[T] extends ReadOnlySelectorOptions[T] {
  def set[A](getSetReset: GetSetResetArgs, value: A | DefaultValue): Unit
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
  def errorOrMaybe(): js.Error = js.native
  def promiseMaybe(): js.UndefOr[js.Promise[T]] = js.native
  def promiseOrThrow(): js.Promise[T] = js.native
  //map ????
  def map[S](thunk: js.Function1[Any, S | js.Promise[S]]): Loadable[S] = js.native
}

@js.native
abstract trait LoadableValue[+T] extends js.Object

@js.native
trait LoadableStrict[+T] extends LoadableValue[T] {
  val state: "hasValue" = js.native
  val contents: T = js.native
}
@js.native
trait LoadableError[+T] extends LoadableValue[T] {
  val state: "hasError" = js.native
  val contents: js.Error = js.native
}
@js.native
trait LoadableEffect[+T] extends LoadableValue[T] with Accessors[T] {
  val state: "loading" = js.native
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
  def getPromise[T](value: RecoilValue[T]): js.Promise[T] = js.native
  def getLoadable[T](value: RecoilValue[T]): Loadable[T] = js.native
  def set[T](value: RecoilState[T], valOrUpdater: SetValOrUpdater[T]): Unit = js.native
  def reset(value: RecoilState[_]): Unit = js.native
}

@js.native
trait recoil_module extends js.Object {
  def atom[T](options: AtomOptions[T]): RecoilState[T] = js.native
  //def selector[T](options: AllSelectorOptions[T]): RecoilValue[T] = js.native
  @JSName("selector")
  def readonlySelector[T](options: ReadOnlySelectorOptions[T]): RecoilValueReadOnly[T] = js.native
  @JSName("selector")
  def writableSelector[T](options: WriteableSelectorOptions[T]): RecoilValue[T] = js.native
  def useRecoilState[T](atom: RecoilValue[T]): js.Tuple2[T, SetterOrUpdater[T]] = js.native
  def useRecoilStateLoadable[T](state: RecoilValue[T]): js.Tuple2[Loadable[T], SetterOrUpdater[T]] = js.native

  /** If pending, throws Promise to suspend, or throws an error. */
  def useRecoilValue[T](selector: RecoilValue[T]): T = js.native
  def useRecoilValueLoadable[T](value: RecoilValue[T]): Loadable[T] = js.native
  def useSetRecoilState[T](state: RecoilState[T]): SetterOrUpdater[T] = js.native
  def useResetRecoilState[T](state: RecoilState[T]): Resetter = js.native
  @JSName("useRecoilCallback")
  def useRecoilCallback_UNTYPED[T](f: js.Function, deps: js.UndefOr[js.Array[AllType]] = js.undefined): js.Any =
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
  def useRecoilCallbackMounting[T](deps: AllType*)(f: js.Function1[CallbackInterface, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f, emptyDependencies).asInstanceOf[js.Function0[T]]

  def useRecoilCallback[T](f: js.Function1[CallbackInterface, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function0[T]]

  def useRecoilCallback[T](deps: AllType*)(f: js.Function1[CallbackInterface, Return[T]]) =
    if (deps.length == 0) module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function0[T]]
    else module.useRecoilCallback_UNTYPED[T](f, deps.toJSArray).asInstanceOf[js.Function0[T]]

  def useRecoilCallbackA[T](deps: Dependencies)(f: js.Function1[CallbackInterface, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f, deps).asInstanceOf[js.Function0[T]]

  def useRecoilCallback1[A1, T](f: js.Function2[CallbackInterface, A1, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function1[A1, T]]

  def useRecoilCallback1[A1, T](deps: AllType*)(f: js.Function2[CallbackInterface, A1, Return[T]]) =
    if (deps.length == 0) module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function1[A1, T]]
    else module.useRecoilCallback_UNTYPED[T](f, deps.toJSArray).asInstanceOf[js.Function1[A1, T]]

  def useRecoilCallback1A[A1, T](deps: Dependencies)(f: js.Function2[CallbackInterface, A1, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f, deps).asInstanceOf[js.Function1[A1, T]]

  def useRecoilCallback2[A1, A2, T](f: js.Function3[CallbackInterface, A1, A2, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function2[A1, A2, T]]

  def useRecoilCallback2[A1, A2, T](deps: AllType*)(f: js.Function3[CallbackInterface, A1, A2, Return[T]]) =
    if (deps.length == 0) module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function2[A1, A2, T]]
    else module.useRecoilCallback_UNTYPED[T](f, deps.toJSArray).asInstanceOf[js.Function2[A1, A2, T]]

  def useRecoilCallback2A[A1, A2, T](deps: Dependencies)(f: js.Function3[CallbackInterface, A1, A2, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f, deps).asInstanceOf[js.Function2[A1, A2, T]]

  def useRecoilCallback3[A1, A2, A3, T](f: js.Function4[CallbackInterface, A1, A2, A3, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function3[A1, A2, A3, T]]

  def useRecoilCallback3[A1, A2, A3, T](deps: AllType*)(f: js.Function4[CallbackInterface, A1, A2, A3, Return[T]]) =
    if (deps.length == 0) module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function3[A1, A2, A3, T]]
    else module.useRecoilCallback_UNTYPED[T](f, deps.toJSArray).asInstanceOf[js.Function3[A1, A2, A3, T]]

  def useRecoilCallback3A[A1, A2, A3, T](deps: Dependencies)(
    f: js.Function4[CallbackInterface, A1, A2, A3, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f, deps).asInstanceOf[js.Function3[A1, A2, A3, T]]

  def useRecoilCallback4[A1, A2, A3, A4, T](f: js.Function5[CallbackInterface, A1, A2, A3, A4, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function4[A1, A2, A3, A4, T]]

  def useRecoilCallback4[A1, A2, A3, A4, T](deps: AllType*)(
    f: js.Function5[CallbackInterface, A1, A2, A3, A4, Return[T]]) =
    if (deps.length == 0) module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function4[A1, A2, A3, A4, T]]
    else module.useRecoilCallback_UNTYPED[T](f, deps.toJSArray).asInstanceOf[js.Function4[A1, A2, A3, A4, T]]

  def useRecoilCallback4A[A1, A2, A3, A4, T](deps: Dependencies)(
    f: js.Function5[CallbackInterface, A1, A2, A3, A4, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f, deps).asInstanceOf[js.Function4[A1, A2, A3, A4, T]]

  def useRecoilCallback5[A1, A2, A3, A4, A5, T](f: js.Function6[CallbackInterface, A1, A2, A3, A4, A5, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function5[A1, A2, A3, A4, A5, T]]

  def useRecoilCallback5[A1, A2, A3, A4, A5, T](deps: AllType*)(
    f: js.Function6[CallbackInterface, A1, A2, A3, A4, A5, Return[T]]) =
    if (deps.length == 0) module.useRecoilCallback_UNTYPED[T](f).asInstanceOf[js.Function5[A1, A2, A3, A4, A5, T]]
    else module.useRecoilCallback_UNTYPED[T](f, deps.toJSArray).asInstanceOf[js.Function5[A1, A2, A3, A4, A5, T]]

  def useRecoilCallback5A[A1, A2, A3, A4, A5, T](deps: Dependencies)(
    f: js.Function6[CallbackInterface, A1, A2, A3, A4, A5, Return[T]]) =
    module.useRecoilCallback_UNTYPED[T](f, deps).asInstanceOf[js.Function5[A1, A2, A3, A4, A5, T]]

  def atom[T](options: AtomOptions[T]) = module.atom[T](options)

  @JSName("selector")
  def readonlySelector[T](options: ReadOnlySelectorOptions[T]) = module.readonlySelector[T](options)
  @JSName("selector")
  def writableSelector[T](options: WriteableSelectorOptions[T]) = module.writableSelector[T](options)
  def useRecoilState[T](atom: RecoilValue[T]): (T, SetterOrUpdater[T]) =
    module.useRecoilState[T](atom)
  def useRecoilStateLoadable[T](state: RecoilValue[T]): (Loadable[T], SetterOrUpdater[T]) =
    module.useRecoilStateLoadable[T](state)

  /** If pending, throws Promise to suspend, or throws an error. */
  def useRecoilValue[T](selector: RecoilValue[T]) = module.useRecoilValue[T](selector)
  def useRecoilValueLoadable[T](value: RecoilValue[T]) = module.useRecoilValueLoadable[T](value)
  def useSetRecoilState[T](state: RecoilState[T]) = module.useSetRecoilState[T](state)
  def useResetRecoilState[T](state: RecoilState[T]) = module.useResetRecoilState[T](state)
  def isRecoilValue(value: Any) = module.isRecoilValue(value)

}
