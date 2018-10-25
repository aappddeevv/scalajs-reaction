// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react
import scala.scalajs.js

package object fabric {

  // export interface IRenderFunction<P> {
  // (props?: P, defaultRender?: (props?: P) => JSX.Element | null): JSX.Element | null;
  //}
  /** Given some props, return a node including potentially a nullElement. This is
   * a javascript oriented render func matching found in fabric. For your own
   * pure scala side components you can use standard scala function syntax.
   */
  type IRenderFunction[P <: js.Object] =
    //js.|[
      js.Function2[P, js.Function1[P, ReactElement], ReactElement]
      //,js.Function1[P, ReactNode]
    //]
}
