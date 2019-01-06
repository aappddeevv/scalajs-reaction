// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js

/**
  * Data structure used to create js side react class. These are the lifecycle
  * react APIs that weq use. Most of the methods below recover the scala side
  * "element" (the component spec) and then forwards the respective call to the
  * scala side "element." The same proxy is used for creating each component
  * instances for all component instances of the same component type.
  */
trait Proxy[SLF, State, ThisSelfProps, ThisSelf] extends js.Object {
  val displayName: String

  /** react js method. */
  val getInitialState: js.UndefOr[js.ThisFunction0[ThisSelf, State]] = js.undefined

  /** react js method. */
  val render: js.ThisFunction0[ThisSelf, ReactNode]

  /** react js method. */
  val componentWillReceiveProps: js.UndefOr[js.ThisFunction1[ThisSelf, ThisSelfProps, Unit]] =
    js.undefined

  /** react js method. */
  val componentWillUnmount: js.UndefOr[js.ThisFunction0[ThisSelf, Unit]] = js.undefined

  /** react js method. */
  val componentDidMount: js.UndefOr[js.ThisFunction0[ThisSelf, Unit]] = js.undefined

  /** react js method. */
  val shouldComponentUpdate: js.UndefOr[js.ThisFunction2[ThisSelf, ThisSelfProps, State, Boolean]] =
    js.undefined

  /** React js method. @deprecated */
  val componentWillUpdate: js.UndefOr[js.ThisFunction2[ThisSelf, ThisSelfProps, State, Unit]] =
    js.undefined

  /** react js method. */
  val componentDidUpdate: js.UndefOr[js.ThisFunction2[ThisSelf, ThisSelfProps, State, Unit]] =
    js.undefined

  /** This should be removed. */
  val contextTypes: js.UndefOr[js.UndefOr[js.Object]] = js.undefined

  /** react js method. v16+ */
  val componentDidCatch: js.UndefOr[js.ThisFunction2[ThisSelf, js.Error, ErrorInfo, Unit]] =
    js.undefined
}
