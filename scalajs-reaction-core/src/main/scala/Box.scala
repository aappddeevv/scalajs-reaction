// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

/**
 * Fake ref type. Need to understand why this is really needed and how it is
 * used in reason ml (ocaml). But use this for instance variables in your state
 * and hopefully it won't change to much in the future. I think the intent is to
 * "box" a value so when copy the box, you can still access the same value.
 * However, jvm/js semantics are that when copying the parent structure, you
 * only copy a reference to any child data items anyway making Box potentially
 * not useful.
 */
case class Box[T] private[react] (private var _value: T) {
  def value: T = _value
  def value_=(t: T): Unit = { _value = t }
}

object Box {
  def apply[T](v: T) = new Box[T](v)
}
