// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

/**
 * A super simple IO monad for react. Its not meant to be large or complex, but
 * merely available without dependencies on other libraries. It does not manage
 * the stack so its possible to blow it if you are not careful. It uses
 * javacript available functions to managed delayed and async effects so its
 * super-lean. It's a bifunctor with a right bias.
 * 
 * The approach as based on ZIO using tags to tag the different types of IO but
 * it does not include everythig that ZIO has, just enough to be helpful and
 * allow other effects to map into RIO so you can use your own effects library.
 * 
 * Let's make sure this is worth it first...
 */
// sealed abstract class RIO[+E,+A] private[react] () extends Serializable {
// sealed class RIO[+E,+A] private[react] (val run: () => A) extends Serializable {
//   self =>

//   /** Tag the type as int comparisons are fast. Forget free! */
//   def tag: Int
    
//   /** Run now, synchronous. */
//   @inline def runSync(): A = run()
//   def widen[B >: A] = new RIO[E,B](run)
//   def map[B](f: A => B) = new RIO[E,B](() => f(run()))
//   def |>[B](f: A => B) = map[B](f)
//   def flatMap[B](f: A => RIO[E,B]) = new RIO[E,B](() => f(run()).run())
//   def >>=[B](f: A => RIO[E,B]) = flatMap[B](f)

//   def flatTap[B](f: A => RIO[E,B]): RIO[E,A] = for {
//     a <- this
//     _ <- f(a)
//   } yield a
//   def tap(f: A => scala.Any) = flatTap(a => RIO[E,Any](f(a)))
  
  
//   def productR[B](right: RIO[E,B]) = new RIO[E,B](() => flatMap[B](_ => right))
//   def *>[B](right: RIO[E,B]) = productR[B](right)
//   def productL[B](right: RIO[E,B]) = flatTap[B](_ => right)
//   def <*[B](right: RIO[E,B]) = productL[B](right)
//   def void = this *> RIO.empty[E]
// }

// object RIO {

//   def apply[E,A](f: => A) = new RIO[E,A](() => f)
//   def lift[E,A](f: () => A) = apply[E,A](f)
//   def pure[E,A](a: A) = new RIO[E,A](() => a)
//   def byName[E,A](thunk: => RIO[E,A]) = new RIO[E,A](thunk.runSync())
//   def memo[E,A](a: => RIO[E,A]) = {
//     lazy val memory = a
//     byName(a)
//   }
//   def empty[E] = RIO[E,Unit](() => ())

//   object Tags {
//     final val FlatMap = 0
//     final val Point = 1
//     final val Strict = 2
//     final val SyncEffect = 3
//     final val Fail = 4
//     //final val AsyncEffect = 5
//   }

//   final case class FlatMap[E, A0, A] private[RIO] (val io: RIO[E, A0], val mapper: A0 => RIO[E,A])
//       extends IO[E,A] { override def tag = Tags.FlatMap }

//   final class Point[A] private[RIO] (val value: () => A) extends RIO[Nothing, A] {
//     override def tag = Tags.Point
//   }

//   final class Strict[A] private[RIO] (val value: A) extends RIO[Nothing, A] {
//     override def tag = Tags.Strict
//   }

//   final class SyncEffect[A] private[RIO] (val effect: Env => A) extends RIO[Nothing, A] {
//     override def tag = Tags.SyncEffect
//   }

//   // final class AsyncEffect[E, A] private[RIO] (val register: (IO[E, A] => Unit) => Async[E, A]) extends IO[E, A] {
//   //   override def tag = Tags.AsyncEffect
//   // }

//    final class Fail[E] private[RIO] (val cause: Cause[E]) extends RIO[E, Nothing] {
//     override def tag = Tags.Fail
//   }

// }

// case class Clause[E]()
// case class Env()
