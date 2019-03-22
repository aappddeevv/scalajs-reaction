// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react
package router
package browser

object ReactionRouter extends ReactionRouterDOMComponent {

  type Config = ReactionConfig

  // case class for convenience, easy to copy and change
  case class ReactionConfig(
    rules: Rules,
    prefixPath: Option[String] = None,
    postRender: Option[PathParts => Unit] = None,
    render: ((Control, Control => ReactNode) => ReactNode) = (c,f) => f(c)
  ) extends super.ConfigLike {

    /** Add a post renderer to this config, runs before existing postRender thunk. */
    def withPostRender(f: PathParts => Unit) = copy(
      postRender = postRender.map{previous => (info: PathParts) => { f(info); previous(info)}} orElse Some(f)
    )
  }
}
