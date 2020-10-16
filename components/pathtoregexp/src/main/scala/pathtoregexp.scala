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

package pathtoregexp

import scala.scalajs.js

import js.annotation._
import js.|

// /** Wrap the regexp returned from PathToRegex. This can only match path
//  * segments, not query parameters or hash paths. Each segment is wrapped in
//  * Option because some segments in path-to-regexp could be optional.
//  */
// case class regexp(r: js.RegExp) {
//   def unapplySeq(p: String): Option[Seq[Option[String]]] =
//     // drop "full match string"
//     Option(r.exec(p)).map(_.drop(1).map(_.toOption))
// }

// object int {
//   import scala.util._
//   def unapply(s: String): Option[Int] =
//     Try(s.toInt) match {
//       case Success(i) => Option(i)
//       case Failure(t) => None
//     }
// }

trait PathToRegexOptions extends js.Object {
  var sensitive: js.UndefOr[Boolean] = js.undefined
  var strict: js.UndefOr[Boolean] = js.undefined
  var end: js.UndefOr[Boolean] = js.undefined
  var start: js.UndefOr[Boolean] = js.undefined
  var delimiter: js.UndefOr[String] = js.undefined
  var endsWith: js.UndefOr[String | js.Array[String]] = js.undefined
  var whiteList: js.UndefOr[String | js.Array[Character]] = js.undefined
}

trait Key extends js.Object {
  var name: js.UndefOr[String | Int] = js.undefined
  var prefix: js.UndefOr[String] = js.undefined
  var delimiter: js.UndefOr[String] = js.undefined
  var optional: js.UndefOr[Boolean] = js.undefined
  var repeat: js.UndefOr[Boolean] = js.undefined
  var pattern: js.UndefOr[String] = js.undefined
}

trait ParseOptions extends js.Object {
  var delimiter: js.UndefOr[String] = js.undefined
}

@js.native
@JSImport("path-to-regexp", JSImport.Namespace)
object PathToRegexp extends js.Object {
  def apply(path: String | js.RegExp | js.Array[String | js.RegExp]): js.RegExp = js.native
  def apply(
    path: String | js.RegExp | js.Array[String | js.RegExp],
    keys: js.UndefOr[js.Array[Key]],
    options: js.UndefOr[PathToRegexOptions]
  ): js.RegExp = js.native
  def apply(path: String, options: js.UndefOr[PathToRegexOptions]): js.RegExp = js.native
  //def compile()
  //def parse()
}
