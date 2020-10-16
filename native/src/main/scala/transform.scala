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

package react
package native

import scala.scalajs.js

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
