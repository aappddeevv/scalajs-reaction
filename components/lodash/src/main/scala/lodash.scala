// Copyright (c) 2019 The Trapelo Group LLC - All Rights Reserved
// Unauthorized copying of this file, via any medium is strictly prohibited.
// Proprietary and confidential.

import scala.scalajs.js
import js.|
import js.annotation._

package object lodash {

  type JSAnyArray = js.Array[js.Any]

  /** current item, key (string or string/int), parent object, stack?? */
  type CloneCustomizer =
    js.Function4[js.Any, js.UndefOr[String | Int], js.UndefOr[js.Object], js.Any, js.Any]

  /** Safe get something by matching on the type after using a ClassTag. */
  def safeGet[T: scala.reflect.ClassTag](
      o: js.Any,
      path: js.UndefOr[String] | js.Array[js.UndefOr[String]],
      defaultValue: js.UndefOr[T] = js.undefined
    ) =
    lodash.get[T](o, path, defaultValue).flatMap {
      case t: T => js.defined(t)
      case _    => js.undefined
    }

  @js.native
  @JSImport("lodash", "groupBy")
  def groupBy[T](arr: js.Array[T], fields: js.Array[String] | String): js.Dictionary[js.Array[T]] = js.native

  @js.native
  @JSImport("lodash", "assign")
  def assign[T <: js.Object](args: T | js.Object | js.Dynamic | Null | Unit | js.UndefOr[_]*): T = js.native

  @js.native
  @JSImport("lodash", "difference")
  def difference[T](lhs: js.Array[T], rhs: js.Array[T]): js.Array[T] = js.native

  @js.native
  @JSImport("lodash", "intersection")
  def intersection[T](lhs: js.Array[T]*): js.Array[T] = js.native

  // brutal to rewrite the args
  def intersectionBy[T](thunk: js.Function1[T,T]|String, arr: js.Array[T]*) =
    intersectionBy_UNSAFE(
      Seq(arr.asInstanceOf[js.Any], thunk.asInstanceOf[js.Any]):_*
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
    defaultValue: js.UndefOr[T] = js.undefined
  ): js.UndefOr[T] = js.native

  @js.native
  @JSImport("lodash", "has")
  def has(o: js.Any, path: String | js.Array[String]): Boolean = js.native

  @js.native
  @JSImport("lodash", "omit")
  def omit[T <: js.Object](o: js.Object, paths: js.Array[String | js.Array[String]]): T = js.native

  @js.native
  @JSImport("lodash", "merge")
  def merge[T <: js.Object](args: T | js.Object | js.Dynamic | Null | Unit | js.UndefOr[_]*): T =
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

}
