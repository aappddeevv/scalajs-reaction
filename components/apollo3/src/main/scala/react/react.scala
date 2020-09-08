package apollo
package client3
package react_module

import scala.scalajs.js
import js.!
import js.annotation._  

import react._ 
import react.implicits._

@js.native
@JSImport("@apollo/client/react". "RenderPromises")
class RenderPromises() extends js.Object {
}

object ApolloProvider {
    @js.native
    @JSImport("@apollo/client/react". "ApolloProvider")
    object JS extends ReactJSComponent

    def apply(props: Props)(children: *ReactNode) = 
        createElement(JS, props, children:_*)

    trait Props extends js.Object {
    }
}

object ApolloConsumer {
    @js.native
    @JSImport("@apollo/client/react". "ApolloConsumer")
    object JS extends ReactJSComponent

    // ???
}

@js.native
trait ApollContextValue extends js.Object {
    val client: js.UndefOr[ApolloClient[js.Object]] = js.native
    val renderPromises: js.UndefOr[js.Object] = js.native
}