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
import js.annotation._

package object formik {
  import formik._

  type TouchedInit = js.UndefOr[js.Object | js.Dynamic | js.Dictionary[Boolean]]
  type ErrorsInit = js.UndefOr[js.Object | js.Dynamic | js.Dictionary[String]]
  type Touched = js.UndefOr[js.Object | js.Dictionary[Boolean]]
  type Errors = js.UndefOr[js.Object | js.Dictionary[String]]
  type SetOrUpdateArg[P] = P | js.Function1[P, P]
  type FieldValidator = js.Function1[Any, String | Unit | js.Promise[String | Unit]]
  type UseFieldResult[P] = js.Tuple3[FieldInputProps[P], FieldMetaProps[P], FieldHelperProps[P]]

  @js.native
  @JSImport("formik", "useField")
  def useField[A](props: String | UseFieldProps[A]): UseFieldResult[A] = js.native

  @js.native
  @JSImport("formik", "useField")
  def useFieldByName[A](props: String): UseFieldResult[A] = js.native

  @js.native
  @JSImport("formik", "useFormikContext")
  def useFormikContext[P](): ContextType[P] = js.native
}
