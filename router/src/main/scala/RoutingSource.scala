// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package router
package browser

/**
 * A specific abstraction of a "routers" source of routing events. You can use
 * reaction's router but you can also use something like
 * [history](https://github.com/ReactTraining/history) by defining your own
 * RoutingSource. The underlying routing subsystem should support the notion of
 * a stack of requested routes--yes, very DOM'ish. This API is a bit crude and
 * should be type of observable reactive object that bakes in failure semantics
 * more clearly. The burden of remembering the last routing info is put on the
 * RoutingComponent instead of the RoutingSource where it belongs :-(.  Note
 * that this is not very functional.
 * 
 * @tparam Info Routing event from an external thing such as an agent
 * e.g. someone types a URL into a class.
 * @tparam To Data needed to change the current route.
 * 
 * @todo Make this reactive and get rid of this awful API.
 */
trait RoutingSource[Info, To] {
  /** Subscribe to receiving routing notifications. Return unsubscribe thunk. */
  val subscribe: (Info => Unit) => (() => Unit)
  /** Push a route onto the conceptual set of routes. */
  val push: To => Unit
  /** Replace the top of the routing stack. */
  val replace: To => Unit
  /** Example, set window.href to force a reload. Means different things to
   * different sources. And it may interpret `To` differently e.g. expect a full
   * To specification.>
   */
  val reload: To => Unit
  /** Run a callaback with the current Info. */
  val run: (Info => Unit) => Unit
}
