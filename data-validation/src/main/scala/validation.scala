// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg.data
package validation

import util.{Try, Success, Failure}
import scala.util.matching.Regex
import java.util.regex.Pattern

import cats._
import cats.data._
import cats.implicits._

/** Useful instance for the formulation below. You can define your own. */
case class Error(val message: String, val path: Path = Path.empty)

/** A validation concept involves a function that evaluates a value and
  * the ability to create a success or failure based on an input type wrapped in `F`.
  * `F` must be able to express the concept of success or failure. Note that at
  * this level, `F` could be a product type or sum type (coproduct). Usually they
  * are sum types.
  *
  * Should we consider message gathering to be a "logging" activity and use a
  * writer monad?
  */
trait Algebra[F[_]] {
  def apply[I, O](implicit validator: Validator[F, I, O]): Validator[F, I, O] =
    validator
  def pure[I, O](f: I => F[O]): Validator[F, I, O] = Kleisli(f)
  def succeed[I]: Validator[F, I, I]
  def fail[I]: Validator[F, I, I]

  /** Since we can fail and succeed, add logic support. This is really a Semigroup "combine"
    * for a specific logic operation. Explicitly use or create an import for them as
    * needed. We could defined an `isValid` type method
    * in this trait but that's a bit of a code smell that we are using procedural logic
    * and we can use these semigroups in other places and algorithms.
    */
  trait Logic {
    val AndSemigroupK: SemigroupK[F]
    val OrSemigroupK: SemigroupK[F]
  }
  val logic: Logic
}

/** Encode valid/invalid via an `ApplicativeError` and failure via  an `E`. */
trait ApplicativeAlgebra[F[_], E] extends Algebra[F] {
  implicit val A: ApplicativeError[F, E]
  def succeed[I]: Validator[F, I, I] = pure(A.pure)
  def fail[I]: Validator[F, I, I] = failure(_ => "failed")

  // is this needed? potentially remove...requires knowing details of E
  // which you may know only in a specific implementation of the interpreter
  //def fail[I,O](error: E): Validator[F,I,O] = pure(_ => A.raiseError[O](error))
  /** Create an `E` potentially with the value and a message if `E` supports
    * string messages, which it may not.
    */
  def failure[I](message: I => String): Validator[F, I, I]
}

/** Test is wrapped in an effect. Error generation is still strict. Fail processing can
  * use test value & test result to create error output.
  */
// trait EffectAlgebra[F[_], E, G[_], TR] extends Algebra[G] {
//   //implicit protected def gfunctor: Functor[G] = Functor[G]
//   //def isFailed(v: TR): Boolean
//   def test[T](id: String, isValid: T => G[TR],
//     failed: (T, TR) => Validator[G, T, F[T]]): Validator[G, T, F[T]]
// }

// trait FutureEffects[F[_], E] extends EffectAlgebra[F, E, Future, E] {
//   def test[T](id: String, isValid: T => G[TR],
//     failed: (T, TR) => Validator[G, T, F[T]]): Validator[G, T, F[T]] =
//     pure{ t =>
//       Future { isValid(t) }

//     }
// }

/** A boolean test for validity is a strict test */
trait StrictApplicativeAlgebra[F[_], E] extends ApplicativeAlgebra[F, E] {
  def test[T](id: String, failed: Validator[F, T, T])(
      isValid: T => Boolean): Validator[F, T, T] =
    pure(t => if (isValid(t)) succeed(t) else failed(t))
  //pure(t => if(isValid(t)) A.pure(t) else A.raiseError(failed(t)))
}

// interpreters
object StrictApplicativeAlgebra {

  def apply[F[_], E](implicit alg: StrictApplicativeAlgebra[F, E]) = alg

  // https://stackoverflow.com/questions/42126125/merging-multiple-validated-into-single-one
  implicit object validatedAlg
      extends StrictApplicativeAlgebra[ValidatedNec[String, ?],
                                       NonEmptyChain[String]] {
    type FF[T] = ValidatedNec[String, T]
    type EE = NonEmptyChain[String]
    val A = ApplicativeError[ValidatedNec[String, ?], EE]
    val logic = new Logic {
      import Validated.{Invalid, Valid, invalid}
      val AndSemigroupK = new SemigroupK[FF] {
        def combineK[T](x: FF[T], y: FF[T]): FF[T] = (x, y) match {
          case (r @ Valid(_), Valid(_))         => r
          case (Invalid(lerrs), Invalid(rerrs)) => invalid(lerrs ++ rerrs)
          case (_, r @ Invalid(_))              => r
          case (r @ Invalid(_), _)              => r
        }
      }
      val OrSemigroupK = new SemigroupK[FF] {
        def combineK[T](x: FF[T], y: FF[T]): FF[T] = (x, y) match {
          case (r @ Valid(_), _)                => r
          case (_, r @ Valid(_))                => r
          case (Invalid(lerrs), Invalid(rerrs)) => invalid(lerrs ++ rerrs)
          case (r @ Invalid(rerrs), _)          => r
          case (_, r @ Invalid(rerrs))          => r
        }
      }
    }
    def failure[I](message: I => String) =
      pure(t => A.raiseError(NonEmptyChain.one(message(t))))
  }

  implicit object optionAlg extends StrictApplicativeAlgebra[Option, Unit] {
    type FF[T] = Option[T]
    val A = ApplicativeError[Option, Unit]
    def failure[I](message: I => String) = pure(t => A.raiseError(()))
    val logic = new Logic {
      val AndSemigroupK = new SemigroupK[FF] {
        def combineK[T](x: FF[T], y: FF[T]): FF[T] = (x, y) match {
          case (r @ Some(_), Some(_)) => r
          case _                      => None
        }
      }
      val OrSemigroupK = new SemigroupK[FF] {
        def combineK[T](x: FF[T], y: FF[T]): FF[T] = (x, y) match {
          case (r @ Some(_), _) => r
          case (_, r @ Some(_)) => r
          case _                => None
        }
      }
    }
  }

  implicit object eitherAlg
      extends StrictApplicativeAlgebra[Either[String, ?], String] {
    type FF[T] = Either[String, T]
    val A = ApplicativeError[Either[String, ?], String]
    def failure[I](message: I => String) = pure(t => A.raiseError(message(t)))
    val logic = new Logic {
      val AndSemigroupK = new SemigroupK[FF] {
        def combineK[T](x: FF[T], y: FF[T]): FF[T] = (x, y) match {
          case (r @ Right(_), Right(_)) => r
          case (r @ Left(_), _)         => r
          case (_, r @ Left(_))         => r
        }
      }
      val OrSemigroupK = new SemigroupK[FF] {
        def combineK[T](x: FF[T], y: FF[T]): FF[T] = (x, y) match {
          case (r @ Right(_), _) => r
          case (_, r @ Right(_)) => r
          case (r @ Left(_), _)  => r
          case (_, r @ Left(_))  => r
        }
      }
    }
  }

  implicit object eitherNecAlg
      extends StrictApplicativeAlgebra[Either[NonEmptyChain[String], ?],
                                       NonEmptyChain[String]] {
    type EE = NonEmptyChain[String]
    type FF[T] = Either[EE, T]
    val A = ApplicativeError[FF, EE]
    def failure[I](message: I => String) =
      pure(t => A.raiseError(NonEmptyChain.one(message(t))))
    val logic = new Logic {
      val AndSemigroupK = new SemigroupK[FF] {
        def combineK[T](x: FF[T], y: FF[T]): FF[T] = (x, y) match {
          case (r @ Right(_), Right(_))   => r
          case (Left(lerrs), Left(rerrs)) => Left(lerrs ++ rerrs)
          case (r @ Left(_), _)           => r
          case (_, r @ Left(_))           => r
        }
      }
      val OrSemigroupK = new SemigroupK[FF] {
        def combineK[T](x: FF[T], y: FF[T]): FF[T] = (x, y) match {
          case (r @ Right(_), _)          => r
          case (_, r @ Right(_))          => r
          case (Left(lerrs), Left(rerrs)) => Left(lerrs ++ rerrs)
          case (r @ Left(_), _)           => r
          case (_, r @ Left(_))           => r
        }
      }
    }
  }
}

trait LogicOps[F[_], E] {
  implicit val alg: StrictApplicativeAlgebra[F, E]
  import alg._

  /** *and* the results, if one fails, return invalid. Collects *all* messages if failed. */
  def andAll[T](left: Validator[F, T, T],
                right: Validator[F, T, T]*): Validator[F, T, T] = pure { t =>
    val combine = logic.AndSemigroupK.algebra[T].combine _
    val list = NonEmptyChain(left, right: _*).map(_(t))
    list.tail.foldLeft(list.head)(combine)
  }

  /** *or* the results, if one succeeds, return valid. Collects *all* messages if failed. */
  def orAll[T](left: Validator[F, T, T],
               right: Validator[F, T, T]*): Validator[F, T, T] = pure { t =>
    val combine = logic.OrSemigroupK.algebra[T].combine _
    val list = NonEmptyChain(left, right: _*).map(_(t))
    list.tail.foldLeft(list.head)(combine)
  }
}

trait ObjectOps[F[_], E] {
  implicit val alg: StrictApplicativeAlgebra[F, E]
  import alg._

  def isNull[T <: scala.AnyRef] =
    test[T]("ttg.null", failure(_ => "is not a null"))(
      _ == null
    )

  def notNull[T <: scala.AnyRef] =
    test[T]("ttg.notNull", failure(_ => "is null"))(
      _ != null
    )
}

trait OptionOps[F[_], E] {
  implicit val alg: StrictApplicativeAlgebra[F, E]
  import alg._

  def required[I](validator: Validator[F, I, I]): Validator[F, Option[I], I] =
    required(validator,
             failure[Option[I]](_ => "value is required")
               .map(_.get /* never called */ ))

  def required[I](
      validator: Validator[F, I, I],
      orElse: Validator[F, Option[I], I]): Validator[F, Option[I], I] =
    pure(
      _.fold(orElse(None))(
        validator(_)
      ))

  def optional[I](
      validator: Validator[F, I, I]): Validator[F, Option[I], Option[I]] =
    pure(
      _.fold(succeed[Option[I]](None))(
        validator(_).map(Option(_))
      ))
}

trait StringOps[F[_], E] {
  implicit val alg: StrictApplicativeAlgebra[F, E]
  import alg._

  def startsWith(prefix: String) =
    test[String]("ttg.startsWith", failure(_ => s"must start with '$prefix'"))(
      t => if (t startsWith prefix) true else false
    )

  def endsWith(prefix: String) =
    test[String]("ttg.endsWith", failure(_ => s"must end with '$prefix'"))(
      _ endsWith prefix
    )

  def maxLength(len: Int) =
    test[String]("ttg.maxLength", failure(_ => s"length must be > $len"))(
      _.length <= len
    )

  def notBlank =
    test[String]("ttg.notBlank", failure(_ => "must not be blank"))(
      !_.trim.isEmpty
    )

  def blank = test[String]("ttg.blank", failure(_ => "must be blank"))(
    _.trim.isEmpty
  )

  def matchRegexPattern(pattern: Pattern, partialMatchAllowed: Boolean = true) =
    test[String](
      "ttg.matchRegexPattern",
      failure(
        _ =>
          if (partialMatchAllowed) s"must match regular expression '$pattern'"
          else s"must full match regular expression '$pattern'")
    )(
      t =>
        if (partialMatchAllowed) pattern.matcher(t).find()
        else pattern.matcher(t).matches()
    )

  def matchRegexString(regex: String, partial: Boolean = true) =
    matchRegex(regex.r, partial)
  def matchRegex(regex: Regex, partial: Boolean = true) =
    matchRegexPattern(regex.pattern, partial)

  def isTrue = test[Boolean]("ttg.isTrue", failure(_ => "must be true"))(
    identity
  )

  def isFalse = test[Boolean]("ttg.isFalse", failure(_ => "must be false"))(
    identity
  )

  def isIn[T](set: Set[T], prefix: String) =
    test[T](
      "ttg.isIn",
      failure(t => set.mkString(s"$prefix $t, expected one of: [", ", ", "]")))(
      set.contains
    )
}

trait ConversionOps[F[_], E] {
  implicit val alg: StrictApplicativeAlgebra[F, E]
  import alg._

  def parseInt(orElse: Validator[F, String, Int]): Validator[F, String, Int] =
    pure { value =>
      util.Try(value.toInt) match {
        case util.Success(i) => succeed(i)
        case util.Failure(_) => orElse(value)
      }
    }

  // making this a val crashs at startup even if you are not using it.
  def parseInt: Validator[F, String, Int] =
    parseInt(failure[String](_ => "Must be a whole number").map(_ => 0))
    //testInt(failure[String](_ => "Must be a whole number").map(_ => 0))

  def testInt(orElse: Validator[F, String,Int]): Validator[F,String,Int] =
    pure{ value =>
      failure[String](_ => "blah").map(_ => 10) apply (value)
      // util.Try(value.toInt) match {
      //   case util.Success(i) => succeed(i)
      //   case util.Failure(_) => failure[Int](_ => "Expected whole number")(0)
      // }
    }

  def parseDouble: Validator[F, String, Double] =
    parseDouble(failure[String](_ => "Must be a number").map(_ => 0.0))

  def parseDouble(
      orElse: Validator[F, String, Double]): Validator[F, String, Double] =
    pure { value =>
      util.Try(value.toDouble) match {
        case util.Success(d) => succeed(d)
        case util.Failure(_) => orElse(value)
      }
    }

  val trimString: Validator[F, String, String] =
    pure(value => succeed(value).map(_.trim))

}

trait NumberOps[F[_], E] {
  implicit val alg: StrictApplicativeAlgebra[F, E]
  import alg._
  import scala.collection.immutable.NumericRange

  def gt[T: Ordering](bound: T, prefix: String = "Got") =
    test[T]("ttg.gt", failure(t => s"$prefix $t, expected more than $bound"))(
      implicitly[Ordering[T]].gt(_, bound)
    )

  def gte[T: Ordering](bound: T, prefix: String = "Got") =
    test[T]("ttg.gte",
            failure(t => s"$prefix $t, exected equal or more than $bound"))(
      Ordering[T].gteq(_, bound)
    )

  def lt[T: Ordering](bound: T, prefix: String = "Got") =
    test[T]("ttg.lt", failure(t => s"$prefix $t, expected less than $bound"))(
      Ordering[T].lt(_, bound)
    )

  def lte[T: Ordering](bound: T, prefix: String = "Got") =
    test[T]("ttg.lte",
            failure(t => s"$prefix $t, expected equal or less than $bound"))(
      Ordering[T].lteq(_, bound)
    )

  def equiv[T: Ordering](bound: T, prefix: String = "Got") =
    test[T]("ttg.equiv", failure(t => s"$prefix $t, expected $bound"))(
      Ordering[T].equiv(_, bound)
    )

  /** Inclusive */
  def between[T: Ordering](lower: T, upper: T, prefix: String = "Got") =
    test[T]("ttg.between",
            failure(t => s"$prefix $t, should be between [$lower, $upper]"))(
      t => Ordering[T].gteq(t, lower) && Ordering[T].lteq(t, upper)
    )

  /** Exclsive. */
  def within[T: Ordering](lower: T, upper: T, prefix: String = "Got") =
    test[T]("ttg.within",
            failure(t => s"$prefix $t, shoud be between ($lower, $upper)"))(
      t => Ordering[T].gt(t, lower) && Ordering[T].lt(t, upper)
    )
}

trait ValidationAlgebra[F[_], E]
    extends StringOps[F, E]
    with ObjectOps[F, E]
    with LogicOps[F, E]
    with NumberOps[F, E]
    with OptionOps[F, E]
    with ConversionOps[F, E]

object ValidationAlgebra extends ValidationAlgebraInstances {
  /** Summoner of instance. */
  def apply[F[_], E](implicit alg: ValidationAlgebra[F, E]) = alg
}

trait ValidationAlgebraInstances {
  import StrictApplicativeAlgebra._

  implicit lazy val validatedAlgebra =
    new ValidationAlgebra[ValidatedNec[String, ?], NonEmptyChain[String]] {
      val alg =
        StrictApplicativeAlgebra[ValidatedNec[String, ?], NonEmptyChain[String]]
    }

  implicit lazy val optionAlegbra = new ValidationAlgebra[Option, Unit] {
    val alg = StrictApplicativeAlgebra[Option, Unit]
  }

  implicit lazy val eitherStringAlgebra =
    new ValidationAlgebra[Either[NonEmptyChain[String], ?],
                          NonEmptyChain[String]] {
      val alg = StrictApplicativeAlgebra[Either[NonEmptyChain[String], ?],
                                         NonEmptyChain[String]]
    }  
}
