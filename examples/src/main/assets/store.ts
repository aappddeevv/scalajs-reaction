import { createStore, applyMiddleware, compose, Store } from "redux"
import thunk from "redux-thunk"
import { reducers } from "./reducers"

let composeEnhancers = compose
if (process.env.NODE_ENV !== "production") {
    if ((parent.window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__) {
        // we are storing functions in our state, no remote dev!
        composeEnhancers =
            (parent.window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({ serialize: true, name: "scalajs" })
    }
}

// not sure we use this anywhere, redux-saga should be used instead
const middlewares = [thunk]

// this needs to be added to <Provider>
export const store = createStore(reducers,
                                 composeEnhancers(applyMiddleware(...middlewares)))
