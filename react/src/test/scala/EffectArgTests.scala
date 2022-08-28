package react  

import scala.scalajs.js
import utest._

import jshelpers.syntax.*


object EffectArgTests extends TestSuite:
  import scala.language.unsafeNulls
  val tests = Tests {
    test("EffectArg magnet pattern") {
      val ef1: EffectArg = () => 10
      val ef2: EffectArg = () => () => ()
      val cb1: js.Function0[Unit] = () => ()
      val ef3: EffectArg = () => cb1
      val cb2: js.Function0[Int] = () => 10
      val ef4: EffectArg = () => cb2
    }
    
  }
end EffectArgTests
