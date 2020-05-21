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

package object utils {
  import utils._

  type Primitive = Boolean | Int | Float | Double | String | Null | Unit
  type SerializableParameter = Boolean | Int | Float | Double | String | Null | Unit | js.Array[Primitive]

  implicit object jsfDictionary extends JSF[js.Dictionary]
  implicit object jsfArray extends JSF[js.Array]

  def atomFamily[T, P <: SerializableParameter](options: AtomFamilyOptions[T, P]) =
    module.atomFamily[T, P](options)

  def selectorFamilyRO[T, P <: SerializableParameter](options: ReadOnlySelectorFamilyOptions[T, P]) =
    module.selectorFamilyRO[T, P](options)

  def selectorFamilyRW[T, P <: SerializableParameter](options: ReadWriteSelectorFamilyOptions[T, P]) =
    module.selectorFamilyRW[T, P](options)

  def selectorFamily[T, P <: SerializableParameter](
    options: ReadOnlySelectorFamilyOptions[T, P] | ReadWriteSelectorFamilyOptions[T, P]) =
    module.selectorFamily[T, P](options)

  def constSelector[T <: SerializableParameter](constant: T) = module.constSelector[T](constant)
  def errorSelector[T](message: String) = module.errorSelector[T](message)

  def waitForNone[F[_]](args: F[RecoilValueReadOnly[_]])(implicit ev: JSF[F]) =
    module.waitForNoneF[ev.JS](args)
  def waitForAny[F[_]](args: F[RecoilValueReadOnly[_]])(implicit ev: JSF[F]) =
    module.waitForNoneF[ev.JS](args)
  def waitForAll[F[_]](args: F[RecoilValueReadOnly[_]])(implicit ev: JSF[F]) =
    module.waitForNoneF[ev.JS](args)

  def nowait(value: RecoilValue[_]) = module.nowait(value)
}
