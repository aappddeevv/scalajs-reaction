// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
import scala.scalajs.js

package object fabric {

  // export interface IRenderFunction<P> {
  // (props?: P, defaultRender?: (props?: P) => JSX.Element | null): JSX.Element | null;
  //}
  /** Return a node including potentially a nullElement. */
  type IRenderFunction[P <: js.Object] =
    js.Function2[P, js.UndefOr[js.Function1[P, ReactNode]], ReactNode]
  
}
