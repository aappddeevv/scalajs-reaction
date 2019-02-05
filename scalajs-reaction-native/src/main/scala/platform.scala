// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.react.native

import scala.scalajs.js
import js.|
import js.annotation._

@js.native
sealed trait PlatformOSType extends js.Any
object PlatformOSType {
  val ios = "ios".asInstanceOf[PlatformOSType]
  val android = "android".asInstanceOf[PlatformOSType]
  val macos = "macos".asInstanceOf[PlatformOSType]
  val windows = "windows".asInstanceOf[PlatformOSType]
  val web = "web".asInstanceOf[PlatformOSType]
}

@js.native
trait PlatformValues extends js.Object {
  val OS: PlatformOSType = js.native
  val Version: Double | String = js.native
  def select[T](selects: js.Dictionary[T]): T = js.native
}


@js.native
@JSImport("react-native", "Platform")
object Platform extends PlatformValues

@js.native
@JSImport("react-native", "PlatformIOS")
object PlatformIOS extends PlatformValues {
  val isPad: Boolean = js.native
  val isTVOS: Boolean = js.native
}
