// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

import scala.scalajs.js

import js._
import react._

package object fabric {

  /** Use this to help define render functions in scala code easier. */
  type RenderForProps[P <: js.Object] = js.Function1[js.UndefOr[P], ReactNode]

  /** Used frequently in fabric so you can customize rendering. */
  type IRenderFunction[P <: js.Object] =
      js.Function2[js.UndefOr[P], js.UndefOr[RenderForProps[P]], ReactNode]
}
