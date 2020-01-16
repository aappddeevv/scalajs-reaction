// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package fabric
package react_hooks

import scala.scalajs.js
import js.annotation._
import js.|

@js.native
@JSImport("@uifabric/react-hooks", JSImport.Namespace)
private object module extends js.Object {
  def useConst(initialValue: scala.Any): js.Any = js.native
  def useConstCallback(cb: js.Any): js.Any = js.native
  def useId(prefx: js.UndefOr[String]= js.undefined): String = js.native
}

trait api {
  def useConstStrict[T](initialValue: T) = module.useConst(initialValue)

  def useConst[T](initialValue: js.Function0[T]) = module.useConst(initialValue)

  def useConstCallback[T](cb: js.Function0[T]) =
      module.useConstCallback(cb).asInstanceOf[js.Function0[T]]

  def useConstCallback[A1,T](cb: js.Function1[A1,T])=
      module.useConstCallback(cb).asInstanceOf[js.Function1[A1,T]]

  def useConstCallback[A1,A2,T](cb: js.Function2[A1,A2,T])=
      module.useConstCallback(cb).asInstanceOf[js.Function2[A1,A2,T]]

  def useConstCallback[A1,A2,A3,T](cb: js.Function3[A1,A2,A3,T])=
      module.useConstCallback(cb).asInstanceOf[js.Function3[A1,A2,A3,T]]

  def useConstCallback[A1,A2,A3,A4,T](cb: js.Function4[A1,A2,A3,A4,T])=
      module.useConstCallback(cb).asInstanceOf[js.Function4[A1,A2,A3,A4,T]]

  def useId(prefix: js.UndefOr[String]= js.undefined): String = module.useId(prefix)
}
