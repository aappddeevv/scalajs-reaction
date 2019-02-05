
// import scala.scalajs.js
// import scala.meta

// @simplecreate
// trait Foo extends js.Object {
//   val f1: String
//   var f2: js.UndefOr[Int] = js.undefined
// }

// //@simplecreate
// trait Blah[T] extends js.Object {
//   val b1: String
//   var b2: js.UndefOr[T] = js.undefined
//   type X = js.Function1[T, Unit]
//   var b3: js.UndefOr[X] = js.undefined
// }

// object Blah {

//   def apply[T](b1: String, b2: js.UndefOr[T] = js.undefined, b3: js.UndefOr[Blah[T]#X] = js.undefined)= {
//     val _b1 = b1
//     val _b2 = b2
//     val _b3 = b3
//     new Blah[T] {
//       val b1 = _b1
//       b2 = _b2
//       b3 = _b3
//     }}
// }


// object Test {
//   val x = Foo(f1 = "foo")
//   val y = Blah[Int](b1 = "blah", b2 = 10)

//   println("x: Foo")
//   js.Dynamic.global.console.log(x)
//   println(s"${x.f1}")
//   println("y: Blah[Int]")
//   js.Dynamic.global.console.log(y)
//   println(s"${y.b2}")
//   js.Dynamic.global.console.log("y direct", new Blah[Int]{val b1="cho"})
// }

//object Test2 {
  //   def doit: Unit = {
//   println("FOO new")
//   println(ru.showRaw(q"""new Foo { f1 = "blah" }"""))
//   println("BLAH new")
//     println(ru.showRaw(q"""new Blah[Int] { b1 = "blah"; b2 = 10 }"""))
//   }
//}
