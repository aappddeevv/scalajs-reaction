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
import js.|
import js.annotation._

/** Keys must be serializable. */
trait CacheImplementation[T] extends js.Object {
    def get(key: Any): js.UndefOr[T]
    def put(key: Any, a: T): CacheImplementation[T]
}

/** P should be < SerializableParameter. */
trait AtomFamilyOptions[P, T] extends js.Object {
  val key: NodeKey
  var dangerouslyAllowMutability: js.UndefOr[Boolean] = js.undefined
  // break some of these out even though with real union types we would not need as much
  val default: T | RecoilValue[T] | js.Thenable[T] | js.Function1[Primitive, Return[T]] | js.Function1[P, Return[T]] | js.Function1[
    js.Array[P] | js.Function1[P, RecoilValue[T]],
    Return[T]] | js.Function1[js.Dictionary[P], Return[T]]
}

trait ReadOnlySelectorFamilyOptions[P,T] extends js.Object {
  val key: String
  def get(args: P): js.Function1[GetRecoilValue[T], Return[T]]
  
  var cacheImplementation_UNSTABLE: js.UndefOr[js.Function0[CacheImplementation[Loadable[T]]]] = js.undefined
  var cacheImplementationForParams_UNSTABLE: js.UndefOr[js.Function0[CacheImplementation[RecoilValue[T]]]] = js.undefined
  
  var dangerouslyAllowMutability: js.UndefOr[Boolean] = js.undefined
}

trait ReadWriteSelectorFamilyOptions[P,T] extends ReadOnlySelectorFamilyOptions[P,T] {
  def set(args: P): js.Function2[ReadWriteAccessors, T | DefaultValue, Unit]
}

@js.native
trait utils_module extends js.Object {
  def atomFamily[P, T](options: AtomFamilyOptions[P,T]): js.Function1[P, RecoilState[T]] =
    js.native

  @JSName("selectorFamily")
  def selectorFamilyRO[P,T](
    options: ReadOnlySelectorFamilyOptions[P,T]): js.Function1[SerializableParameter, RecoilValueReadOnly[T]] = js.native
    
  @JSName("selectorFamily")
  def selectorFamilyRW[P,T](
    options: ReadWriteSelectorFamilyOptions[P,T]): js.Function1[SerializableParameter, RecoilState[T]] = js.native
    
  def selectorFamily[P,T](
    options: ReadOnlySelectorFamilyOptions[P,T] | ReadWriteSelectorFamilyOptions[P,T])
    : js.Function1[P, RecoilValue[T]] = js.native

  def constSelector[T](constant: T): RecoilValueReadOnly[T] = js.native
  
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
@JSImport("recoil", JSImport.Namespace)
object utils_module extends utils_module

/** Move type parameter to type member. */
trait JSF[F[_]] {
  type JS[T] = F[T]
}

trait utils_exports { 

  def atomFamily[P,T](options: AtomFamilyOptions[P,T]) =
    utils_module.atomFamily[P,T](options)

  def selectorFamilyRO[P,T](options: ReadOnlySelectorFamilyOptions[P,T]) =
    utils_module.selectorFamilyRO[P,T](options)

  def selectorFamilyRW[P,T](options: ReadWriteSelectorFamilyOptions[P,T]) =
    utils_module.selectorFamilyRW[P,T](options)

    /**
    * @tparam T
    * @tparam P Args to resulting function.
    */
  def selectorFamily[P,T](
    options: ReadOnlySelectorFamilyOptions[P,T] | ReadWriteSelectorFamilyOptions[P,T]) =
    utils_module.selectorFamily[P,T](options)

  def constSelector[T <: SerializableParameter](constant: T) = utils_module.constSelector[T](constant)
  def errorSelector[T](message: String) = utils_module.errorSelector[T](message)

  def waitForNone[F[_]](args: F[RecoilValueReadOnly[_]])(implicit ev: JSF[F]) =
    utils_module.waitForNoneF[ev.JS](args)
  def waitForAny[F[_]](args: F[RecoilValueReadOnly[_]])(implicit ev: JSF[F]) =
    utils_module.waitForNoneF[ev.JS](args)
  def waitForAll[F[_]](args: F[RecoilValueReadOnly[_]])(implicit ev: JSF[F]) =
    utils_module.waitForNoneF[ev.JS](args)

  def nowait(value: RecoilValue[_]) = utils_module.nowait(value)
}
