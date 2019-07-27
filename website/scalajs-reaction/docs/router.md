---
id: routing
title: Routing
---

Routing is a complex topic and many react projects have sprung up around
routing. scalajs react facades also provide routing libraries. You can use any
javascript based routing librar you wish, such as
[react-router](https://reacttraining.com/react-router). To make it easier though
we have provided two levels of routing support.


## Lowest Level 

ReasonReact provides a small, ~100 line router, in the base distribution and
that small router code is also included in the scalajs-react implementation.

The router establishes a simple callback mechanism when the URL changes and
allows you to obtain the current URL as well as push a new URL state into the
history object running in the browser.

You can route to some hashes by doing something like the following:

```scala
  sealed trait Action
  case class Navigate(page: Page) extends Action
  case object SomeOtherAction extends Action
  
  case class State(page: Page)

  sealed trait Page
  case object Home extends Page
  case class AnotherPage(id: String) extends Page

  val c = reducerComponent[State, Action]("MyComponent")
  import c.ops._
  
  def apply() =
   c.copy(new methods { 
     val initialState = () => State(Home)
     
     val reducer = (action, state, gen) => {
       action match { 
         case Navigate(p) => gen.update(state.copy(page = p))
         case SomeOtherAction = gen.skip // for demo purposes only
       }
     }

     didMount = js.defined(self => {
       val token = router.watchUrl{ url => 
         url match {
           case Some("home") => self.send(Navigate(Home))
           case Some("person") => self.send(Navigate(AnotherPage("person")))
           case _ => self.send(Navigate(Home))
         }
       }
       // prime the pump!
       // ...don't forget this...
       self.onUnmount(() => router.unwatchUrl(token)
     })
     
     val render = self => {
       self.state.page match { 
          case Home => "home"
          case AnotherPage(id) => id
       }
     }
       
  })
```

## Routing Component

We have a provided a DOM agnostic router component and a specialization of that
routing component for the ReasonReact style router described in the previous
section. You can have as many RoutingComponents as you like in your application
as there is no shared state between them. Think of these components as rendering
based on the URL and if you don't like the URL, return a "null" node which
effectively turns off rendering for any children of the routing component.

You are free to use another routing strategy and plug that into
`RoutingComponent`.

```scala
// ReactionRouting
import react.router.browser._

// App
object Routing {
    import ReactionRouting._
    // if using sparsetech.trail route matching library
    val coolRoute = Root / Arg[String] / Arg[String]
    val login = Root / "login"
    
    val config = ReactionConfig(rules(parts => {
       parts.pathname match {
          case coolRoute(entityName, entityId) => Render(() => SomeView(entityName, entityId))
          case login(_) => Redirect("/login", RedirectMethod.Push)
          case _ => Render(() => UnknownPage(parts))
       }
       // or do something custom
       parts.pathname match {
          case parts if(parts.map(_.hash == "blah").getOrElse(false)) => Render(() => Blah)
          case _ => Render(() => null)
       }
    }))
}

def main() {
    reactdom.createAndRenderWithId(
      Fabric(new Fabric.Props {})(
        ReactionRouting(Routing.config)
      ),
      "container"
     )
    // kick things off
    react.router.push("")
}
```

You will want to use a real "matching" library like
[trail](https://github.com/sparsetech/trail) to match on the URL and provide
"route-to-page" routing processing in your config.
