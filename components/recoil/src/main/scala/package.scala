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

import scala.scalajs.js
import js.|
import js.JSConverters._
import react._

package object recoil extends recoil.hooks {
  import recoil._

  type NodeKey = String
  type LoadablePromise[T] = js.Promise[ResolvedLoadablePromiseInfo[T]]

  /** Value. Note this has poor type inference. */
  //type RecoilValue[T] = Lambda[X => RecoilValueReadOnly[X] | RecoilState[X]]
  type RecoilValue[T] = RecoilValueReadOnly[T] | RecoilState[T]

  def makeAtom[T](k: String, d: FlexiValue[T]) =
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
