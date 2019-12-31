// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react
package native

import scala.scalajs.js
import js.annotation._
import js.|

trait PerspectiveTransform { var perspective: Double }
trait RotateTransform { var rotate: String }
trait RotateXTransform { var rotateX: String }
trait RotateYTransform { var rotateY: String }
trait RotateZTransform { var rotateZ: String }
trait ScaleTransform { var scale: String }
trait ScaleXTransform { var scaleX: String }
trait ScaleYTransform { var scaleY: String }
trait TranslateXTransform { var translateX: String }
trait TranslateYTransform { var translateY: String }
trait SkewXTransform { var skewX: String }
trait SkewYTransform { var skewY: String }

trait TransformsStyle extends js.Object {
  var tranform: js.UndefOr[js.Any] = js.undefined
  var transformMatrix: js.UndefOr[js.Array[Double]] = js.undefined
  // everything below is deprecated
  var rotation: js.UndefOr[Double] = js.undefined
  var scaleX: js.UndefOr[Double] = js.undefined
  var scaleY: js.UndefOr[Double] = js.undefined
  var translateX: js.UndefOr[Double] = js.undefined
  var translateY: js.UndefOr[Double] = js.undefined
}
