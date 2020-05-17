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
    module.atomFamily[T,P](options)

  def selectorFamilyRO[T, P <: SerializableParameter](
    options: ReadOnlySelectorFamilyOptions[T, P]) = module.selectorFamilyRO[T,P](options)

  def selectorFamilyRW[T, P <: SerializableParameter](
    options: ReadWriteSelectorFamilyOptions[T, P]) = module.selectorFamilyRW[T,P](options)

  def selectorFamily[T, P <: SerializableParameter](
    options: ReadOnlySelectorFamilyOptions[T, P] | ReadWriteSelectorFamilyOptions[T, P]) =
        module.selectorFamily[T,P](options)

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
