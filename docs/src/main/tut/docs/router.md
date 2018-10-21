---
layout: docs
title: routing
---

# Routing

Routing is a complex topic and many react projects have sprung up around
routing. scalajs react facades also provide routing libraries.

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
         println(s"Routing to: $url")
         url match {
           case Some("home") => self.send(Navigate(Home))
           case Some("person") => self.send(Navigate(AnotherPage("person")))
           case _ => self.send(Navigate(Home))
         }
       }
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

You will want to use a real "matching" library like
[trail](https://github.com/sparsetech/trail) to match on the URL and provide
parameterized routing.
