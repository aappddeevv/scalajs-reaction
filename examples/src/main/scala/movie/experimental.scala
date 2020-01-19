
package ttg
package examples
// not in movie package!

import scala.scalajs.js
import js.Dynamic.{literal => lit}
import _root_.react.vdom.tags._


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
//                           js.Any.fromFunction1(renderPropsChild).asInstanceOf[ReactNode])
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
//   import concurrent.duration._

//   /** Delay (ms resolution) providing a value via a js.Promise. */
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
//   case class SetValue(value: V) extends Action

//   case class State(asyncValue: V)
//   val av = reducerComponentWithRetainedProps[State, V, Action]("AsyncValue")
//   import av.ops._

//   def make(value: V, defaultValue: V)(children: V => ReactNode) =
//     av.copy(new methods {
//       val initialState  = _ => State(defaultValue)
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
//           //reactdom.deferredUpdates { () =>
//             on.newSelf.send(SetValue(value))
//           //}
//       }
//       val render = self => children(self.state.asyncValue)
//     })
// }
