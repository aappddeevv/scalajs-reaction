import scala.scalajs.js
import js.|
import js.JSConverters._
import react._

package object recoil extends recoil.hooks {
  import recoil._

  type NodeKey = String
  type LoadablePromise[T] = js.Promise[ResolvedLoadablePromiseInfo[T]]

  type RecoilValue[T] = RecoilValueReadOnly[T] | RecoilState[T]

  def atom[T](k: String, d: FlexiValue[T]) =
    module.atom[T](new AtomOptions[T] {
      val key = k
      val default = d
    })

  type SetVal[T] = js.Function1[T, Unit]
  type Updater[T] = js.Function1[js.Function1[T, T], Unit]
  type SetValOrUpdater[T] = SetVal[T] | Updater[T]
  type Resetter = js.Function0[Unit]
  type AtomValues = js.Dictionary[Loadable[js.Any]]

  type FlexiValue[T] = T | RecoilValue[T] | js.Promise[T]
  type Return[T] = FlexiValue[T]
  type GetRecoilValue[T] = js.Function1[RecoilValue[T], T]
  type SetRecoilState[T] = js.Function2[RecoilState[T], T | DefaultValue | js.Function1[T, T | DefaultValue], Unit]
  type ResetRecoilState[T] = js.Function1[RecoilState[T], Unit]

}
