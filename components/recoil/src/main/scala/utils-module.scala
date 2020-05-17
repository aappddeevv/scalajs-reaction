package recoil
package utils

import scala.scalajs.js
import js.|
import js.annotation._

// should inherit from AtomOptions
trait AtomFamilyOptions[T, P <: SerializableParameter] extends js.Object {
  val key: NodeKey
  val default: T | RecoilValue[T] | js.Promise[T] | js.Function1[Primitive, Return[T]] | js.Function1[P, Return[T]] | js.Function1[
    js.Array[P],
    Return[T]] | js.Function1[js.Dictionary[P], Return[T]]
}

trait ReadOnlySelectorFamilyOptions[T, P <: SerializableParameter] extends js.Object {
  val key: String
  def get(args: P): js.Function1[GetRecoilValue[T], Return[T]]
  var dangerouslyAllowMutability: js.UndefOr[Boolean] = js.undefined
}

trait ReadWriteSelectorFamilyOptions[T, P <: SerializableParameter] extends ReadOnlySelectorFamilyOptions[T, P] {
  def set(args: P): js.Function2[GetSetResetArgs, T | DefaultValue, Unit]
}

@js.native
trait utils_module extends js.Object {
  def atomFamily[T, P <: SerializableParameter](options: AtomFamilyOptions[T, P]): js.Function1[P, RecoilState[T]] =
    js.native

  @JSName("selectorFamily")
  def selectorFamilyRO[T, P <: SerializableParameter](
    options: ReadOnlySelectorFamilyOptions[T, P]): js.Function1[P, RecoilValueReadOnly[T]] = js.native
  @JSName("selectorFamily")
  def selectorFamilyRW[T, P <: SerializableParameter](
    options: ReadWriteSelectorFamilyOptions[T, P]): js.Function1[P, RecoilState[T]] = js.native
  def selectorFamily[T, P <: SerializableParameter](
    options: ReadOnlySelectorFamilyOptions[T, P] | ReadWriteSelectorFamilyOptions[T, P])
    : js.Function1[P, RecoilValue[T]] = js.native

  def constSelector[T <: SerializableParameter](constant: T): RecoilValueReadOnly[T] = js.native
  def errorSelector[T](message: String): RecoilValueReadOnly[T] = js.native

    // there is no way in js to restrict F with a context bounds since that adds an argument.

  @JSName("waitForNone")
  def waitForNoneF[F[_]](args: F[RecoilValueReadOnly[_]]): F[Loadable[_]] = js.native
  @JSName("waitForAny")
  def waitForAnyF[F[_]](args: F[RecoilValueReadOnly[_]]): F[Loadable[_]] = js.native
  @JSName("waitForAll")
  def waitForAllF[F[_]](args: F[RecoilValueReadOnly[_]]): F[Loadable[_]] = js.native

  // // wait array version
  // @JSName("waitForNone")
  // def waitForNone(args: js.Array[RecoilValueReadOnly[_]]): js.Array[Loadable[_]] = js.native
  // @JSName("waitForAny")
  // def waitForAny(args: js.Array[RecoilValueReadOnly[_]]): js.Array[Loadable[_]] = js.native
  // @JSName("waitForAll")
  // def waitForAll(args: js.Array[RecoilValueReadOnly[_]]): js.Array[Loadable[_]] = js.native

  // // wait dictionary version
  // @JSName("waitForNone")
  // def waitForNoneD(args: js.Dictionary[RecoilValueReadOnly[_]]): js.Dictionary[Loadable[_]] = js.native
  // @JSName("waitForAny")
  // def waitForAnyD(args: js.Dictionary[RecoilValueReadOnly[_]]): js.Dictionary[Loadable[_]] = js.native
  // @JSName("waitForAll")
  // def waitForAllD(args: js.Dictionary[RecoilValueReadOnly[_]]): js.Dictionary[Loadable[_]] = js.native

  def nowait(value: RecoilValue[_]): Loadable[Any] = js.native
}

@js.native
@JSImport("recoil", "RecoilUtils")
object module extends utils_module

/** Move type parameter to type member. */
trait JSF[F[_]] { 
    type JS[T] = F[T]
}