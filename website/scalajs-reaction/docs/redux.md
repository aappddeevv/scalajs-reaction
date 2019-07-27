---
id: redux
title: Redux
---

The scalajs-reaction integration is not designed to create or write middleware
in scalajs-reaction. There are some other scala.js libraries that can provide
full access to the redux API. Two projects in particular are:

* [https://github.com/eldis/scalajs-redux](https://github.com/eldis/scalajs-redux)
* [https://github.com/shogowada/scalajs-reactjs](https://github.com/shogowada/scalajs-reactjs)

Both of these contain redux and full scala.js react integration layers. For
example, you can define a redux store in scala using those libraries.

Since this facade is based on hooks, you can use the relatively simply hooks
facade to use redux inside your scala.js defined components.

At its core, `react-redux`, the react integration layer with redux, creates
an intermediate component that:
1. Obtains the store from the context (or props if
they are passed down with the name "store" or it finds the storeKey).
1. Calls mapStateToProps (with the redux state and your component's props)  calls
mapDispatchToProps (dispatch func and your component's props) .
1. Sets the the state of the wrapper component (called Connect) that
   `react-redux` creates. It uses a dummy state. The setState call in Connect
   forces it and its children to render.

The magic is that react-redux tries to memoize the results so that it reduces
the number of renders in your component if the state changes but those changes
do not change the merged set of props.

Memoizing a function means that a function remembers (stores) its previous input
and on the next function call if the input has not changed, it returns the same
result. Of course, this means the function stores state and potentially holds
onto large amounts of "input" data as well as previousu results--all of which
inflates memory usage.

`react-redux` also uses a Subscription object to subscribe to events hold a link
to their parent subscription and managed the unsubscribe process in the correct
order (child to parent).

On the scala side, we just need to define our component and use the react-redux
hooks API provided.

## Scala Side Example

To preserve integration on the scala side, you have to do the same type of
mapping from redux state to props.

```
import ttg.react.redux

// mimic the definition in js
@js.native
trait GlobalAppState extends js.Object {
   val label: js.UndefOr[String] = js.undefined
}

object MyComponent {

    trait Props extends js.Object { 
       // ...
    }

    def apply(props: Props) = sfc(props)
    
    val sfc = SFC1[Props] { props =>
      val label = 
          ReactRedux.useSelector[GlobalAppState, js.UndefOr[String]](_.label)
      // other react-redux calls such is ReactRedux.useDispatch[GlobalAction]()
      div(label)
    }
    
    // if we wish to use this in js
    @JSExportTopLevel
    val exported = sfc.run
    
}
```

That's all you need to do. Just like with importing, if you choose your props
carefully, you can reduce your API data swizzling work significantly if your
props are js friendly, but its up to you.

Also, note that the `apply` function in the example above takes no children. If
it had, we would have added a second parameter list (or to the first parameter
list) or added children explicitly to the `Props` trait. It's up to you on how
you want to structure your internal API.
