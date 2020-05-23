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
package utils

import scala.scalajs.js
import js.|
import js.annotation._

// should inherit from AtomOptions
trait AtomFamilyOptions[T, P <: SerializableParameter] extends js.Object {
  val key: NodeKey
  val default: T | RecoilValue[T] | js.Thenable[T] | js.Function1[Primitive, Return[T]] | js.Function1[P, Return[T]] | js.Function1[
    js.Array[P],
    Return[T]] | js.Function1[js.Dictionary[P], Return[T]]
}

trait ReadOnlySelectorFamilyOptions[T, P <: SerializableParameter] extends js.Object {
  val key: String
  def get(args: P): js.Function1[GetRecoilValue[T], Return[T]]
  var dangerouslyAllowMutability: js.UndefOr[Boolean] = js.undefined
}

trait ReadWriteSelectorFamilyOptions[T, P <: SerializableParameter] extends ReadOnlySelectorFamilyOptions[T, P] {
  def set(args: P): js.Function2[ReadWriteAccessors, T | DefaultValue, Unit]
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
