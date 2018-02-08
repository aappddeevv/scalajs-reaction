// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

/** 
 * Mutable container in case you want to add something to your state in a box
 * like in the reasonreact examples. You really do not normally need this
 * though. Not thread safe, but that does not matter in scala.js. Note this does
 * not handle equality correctly yet, so don't use it.
 */
final class Ref[T] private[react](private var value: T){
  def apply() = value
  def get = value
  def set(newValue: T){
    value = newValue
   }
}

object Ref {
  def apply[T](v: T) = new Ref[T](v)
}
