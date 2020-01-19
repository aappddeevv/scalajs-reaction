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

package ttg
package examples
// not in movie package!

//                           js.Any.fromFunction1(renderPropsChild).asInstanceOf[ReactNode])//           //reactdom.deferredUpdates { () =>//       }//   import concurrent.duration._//   /** Delay (ms resolution) providing a value via a js.Promise. *///   case class SetValue(value: V) extends Action//       val initialState  = _ => State(defaultValue)
// object Loading {
//   def make(key: Option[String] = None)(isLoading: Boolean => ReactNode) = {
//     val props = js.Dictionary.empty[js.Any]
//     key.foreach(props("key") = _)
//     // JScreateElement(JSReact.Loading,
//     //   props,
//     //   js.Any.fromFunction1(isLoading).asInstanceOf[ReactNode])
//     null
//   }
// }

// object Timeout {
//   def make(ms: Long, key: Option[String] = None)(renderPropsChild: Boolean => ReactNode) = {
//     val props = js.Dictionary.empty[js.Any]
//     key.foreach(props("key") = _)
//     props("ms") = ms.toDouble
//     JScreateElement(JSReact.Timeout,
//                           props,

//   }
// }

// object TimeoutWithFallback {
//   def make(ms: Long, key: Option[String] = None)(fallback: ReactNode, children: ReactNode*) =
//     Timeout.make(ms, key) { didTimeout =>
//       elements.fragmentElement()(
//         createElement("span", lit("hidden" -> didTimeout))(children: _*),
//         if (didTimeout) fallback else null
//       )
//     }
// }

// object Delay {



//   def delayPromise[A](delay: FiniteDuration): A => js.Promise[A] =
//     data => new js.Promise[A]((res, rej) => js.timers.setTimeout(delay) { res(data) })

//   /** Well, this is a bit knot of nasty casting, but we are "throwing a promise" that "never" resolves. */
//   private def Never(whatsNever: FiniteDuration = 10 seconds): ReactNode =
//     ({ () =>
//       throw js.JavaScriptException(delayPromise[Unit](whatsNever)(()))
//     }: js.Function0[Unit]).asInstanceOf[ReactNode]

//   def make(ms: Long, key: Option[String]): ReactNode = {
//     Timeout.make(ms, key)(didTimeout => {
//       if (didTimeout) null
//       else Never()
//     })
//   }
// }

// /** Instantiate for each V type. */
// trait AsyncValueTemplate[V] {
//   sealed trait Action


//   case class State(asyncValue: V)
//   val av = reducerComponentWithRetainedProps[State, V, Action]("AsyncValue")
//   import av.ops._

//   def make(value: V, defaultValue: V)(children: V => ReactNode) =
//     av.copy(new methods {

//       val retainedProps = value
//       val reducer = (action, state, gen) => {
//         action match {
//           case SetValue(v) => gen.update(State(v))
//           case _           => gen.skip
//         }
//       }
//       didMount = js.defined { (self, gen) =>
//         //reactdom.deferredUpdates { () =>
//           self.send(SetValue(self.retainedProps))
//         //}
//       }
//       didUpdate = js.defined { on =>
//         if (on.newSelf.retainedProps != on.oldSelf.retainedProps)

//             on.newSelf.send(SetValue(value))
//           //}

//       val render = self => children(self.state.asyncValue)
//     })
// }
