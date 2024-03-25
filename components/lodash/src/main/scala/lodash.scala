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

// Copyright (c) 2019 The Trapelo Group LLC - All Rights Reserved
// Unauthorized copying of this file, via any medium is strictly prohibited.
// Proprietary and confidential.

package lodash

import scala.scalajs.js
import js.annotation.*

type JSAnyArray = js.Array[js.Any]

/** current item, key (string or string/int), parent object, stack?? */
type CloneCustomizer =
  js.Function4[js.Any, js.UndefOr[String | Int], js.UndefOr[js.Object], js.Any, js.Any]

/** Safe get something by matching on the type after using a ClassTag. */
def safeGet[T: scala.reflect.ClassTag](
  o: js.Any,
  path: js.UndefOr[String] | js.Array[js.UndefOr[String]],
  //defaultValue: js.UndefOr[T] = js.undefined
) =
  lodash.get[T](o, path).flatMap {
    case t: T => js.defined(t)
    case null    => js.undefined
  }

@js.native
@JSImport("lodash", "groupBy")
def groupBy[T](arr: js.Array[T], fields: js.Array[String] | String): js.Dictionary[js.Array[T]] = js.native

@js.native
@JSImport("lodash", "assign")
def assign[T <: js.Object](args: T | js.Object | js.Dynamic | Null | Unit | js.UndefOr[scala.Any]*): T = js.native

@js.native
@JSImport("lodash", "difference")
def difference[T](lhs: js.Array[T], rhs: js.Array[T]): js.Array[T] = js.native

@js.native
@JSImport("lodash", "intersection")
def intersection[T](lhs: js.Array[T]*): js.Array[T] = js.native

// brutal to rewrite the args
def intersectionBy[T](thunk: js.Function1[T, T] | String, arr: js.Array[T]*) =
  intersectionBy_UNSAFE(
    Seq(arr.asInstanceOf[js.Any], thunk.asInstanceOf[js.Any])*
  )

@js.native
@JSImport("lodash", "intersectionBy")
def intersectionBy_UNSAFE[T](args: js.Any*): js.Array[T] = js.native

@js.native
@JSImport("lodash", "at")
def at(o: js.Any, paths: js.Array[String | js.Array[String]]): js.Array[js.Any] = js.native

@js.native
@JSImport("lodash", "getArray")
def getArray[T](o: js.Any, path: js.UndefOr[String] | js.Array[String]): js.UndefOr[T] = js.native

@js.native
@JSImport("lodash", "get")
def get[T](
  o: js.Any,
  path: js.UndefOr[String] | js.Array[js.UndefOr[String]],
  //defaultValue: js.UndefOr[T] = js.undefined
): js.UndefOr[T] = js.native

@js.native
@JSImport("lodash", "get")
def getOrElse[T](
  o: js.Any,
  path: js.UndefOr[String] | js.Array[js.UndefOr[String]],
  defaultValue: T = js.undefined
): T = js.native

@js.native
@JSImport("lodash", "has")
def has(o: js.Any, path: String | js.Array[String]): Boolean = js.native

@js.native
@JSImport("lodash", "omit")
def omit[T <: js.Object](o: js.Object, paths: js.Array[String]|js.Array[String | js.Array[String]]): T = js.native

@js.native
@JSImport("lodash", "omitBy")
def omitBy[T <: js.Object](o: js.Object, predicate: js.Any): T = js.native

@js.native
@JSImport("lodash", "isNil")
def isNil(value: Any): Boolean = js.native

@js.native
@JSImport("lodash", "isNull")
def isNull(value: Any): Boolean = js.native

@js.native
@JSImport("lodash", "isUndefined")
def isUndefined(value: Any): Boolean = js.native

@js.native
@JSImport("loadash", "isDate")
def isDate(value: Any): Boolean = js.native

@js.native
@JSImport("lodash", "identity")
val identity: js.Any = js.native

@js.native
@JSImport("lodash", "pick")
def pick[T <: js.Object](o: js.Object, paths: js.Array[String]|js.Array[String | js.Array[String]]): T = js.native

@js.native
@JSImport("lodash", "merge")
def merge[T <: js.Object](args: T | js.Object | js.Dynamic | Null | Unit | js.UndefOr[scala.Any]*): T =
  js.native

@js.native
@JSImport("lodash", "merge")
def mergeAny[T <: js.Object](args: js.Any | js.Dynamic*): T = js.native

@js.native
@JSImport("lodash", "cloneDeep")
def cloneDeep[T <: js.Object](value: T | js.Dynamic): T = js.native

@js.native
@JSImport("lodash", "clone")
def clone[T <: js.Object](value: T | js.Dynamic): T = js.native

@js.native
@JSImport("lodash", "castArray")
def castArray[T <: js.Any](value: T): js.Array[T] = js.native

@js.native
@JSImport("lodash", "orderBy")
def orderBy[T <: js.Any](
  values: js.Array[T],
  fields: js.Array[String],
  directions: js.UndefOr[js.Array[String]] = js.undefined
): js.Array[T] = js.native

@js.native
@JSImport("lodash", "round")
def roundDouble(value: Double, precision: js.UndefOr[Int] = js.undefined): Double = js.native

@js.native
@JSImport("lodash", "round")
def roundFloat(value: Float, precision: js.UndefOr[Int] = js.undefined): Float = js.native

@js.native
@JSImport("lodash", "round")
def roundLong(value: Long, precision: js.UndefOr[Int] = js.undefined): Long = js.native

@js.native
@JSImport("lodash", "round")
def roundInt(value: Int, precision: js.UndefOr[Int] = js.undefined): Int = js.native

@js.native
@JSImport("lodash", "isEqual")
def isEqual(lhs: js.Any, rhs: js.Any): Boolean = js.native

@js.native
@JSImport("lodash", "template")
def template(source: String, options: js.UndefOr[js.Object|js.Dynamic]=js.undefined): js.Function1[js.Object|js.Dynamic, String] = js.native

@js.native
@JSImport("lodash", "templateSettings")
val templateSettings: js.Dynamic = js.native
