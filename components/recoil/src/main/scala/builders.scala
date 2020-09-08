package recoil

import scala.scalajs.js
import js.|

object AtomBuilder {

  class AtomWithKey[T](_key: String) {
    def default(value: FlexiValue[T]) = new AtomWithKeyAndDefault[T](_key, value)
  }

  class AtomWithKeyAndDefault[T](_key: String, _default: FlexiValue[T]) {
    def build =
      module.atom[T](new AtomOptions[T] {
        val key = _key
        val default = _default
      })
  }

  def apply[T](key: String) = new AtomWithKey[T](key)
  def apply[T](key: String, default: T) = new AtomWithKeyAndDefault[T](key, default)
}

class ReadOnlySelectorWithKeyAndThunk[T](_key: String, _thunk: ReadOnlyAccessorsWithOutput[T] => FlexiValue[T]) {
  def build =
    recoil.unsafeReadonlySelector[T](
      js.Dynamic.literal(
        "key" -> _key,
        "get" -> (_thunk: js.Function1[ReadOnlyAccessorsWithOutput[T], FlexiValue[T]])
      ))
}

/** Create a read only selector. Accessors has `Output` type that matches the apply
 * type parameter for convenience if the output type is wordy and you are too lazy
 * to define your own type alias. There is a small processing
 * cost for using the builder pattern.
 */
object ReadOnlySelectorBuilder {
  def apply[T](key: String)(thunk: ReadOnlyAccessorsWithOutput[T] => FlexiValue[T]) =
    new ReadOnlySelectorWithKeyAndThunk[T](key, thunk)
}

object SelectorFamilyROBuilder {

  class SelectorFamilyROBuilderWithKey[P, T](_key: String, _thunk: P => GetRecoilValue[T] => Return[T]) {
    def build =
      utils_module.selectorFamilyRO[P, T](new ReadOnlySelectorFamilyOptions[P, T] {
        val key = _key
        def get(arg: P) = _thunk(arg): js.Function1[GetRecoilValue[T], Return[T]]
      })
  }

  class SelectorFamilyROBuildWithKeyAndMore[P, T](
    required: SelectorFamilyROBuilderWithKey[P, T],
    c: Int,
    cp: Int,
    d: js.UndefOr[Boolean] = js.undefined) {
//   var cacheImplementation_UNSTABLE: js.UndefOr[js.Function0[CacheImplementation[Loadable[T]]]] = js.undefined
//   var cacheImplementationForParams_UNSTABLE: js.UndefOr[js.Function0[CacheImplementation[RecoilValue[T]]]] = js.undefined
//   var dangerouslyAllowMutability: js.UndefOr[Boolean] = js.undefined
  }

  def apply[P, T](key: String, thunk: P => GetRecoilValue[T] => Return[T]) =
    new SelectorFamilyROBuilderWithKey[P, T](key, thunk)
}
