import { combineReducers } from "redux"
import { reducer as ViewReducer, State as ViewState } from "./view"
import { reducer as AMReducer, State as AMState } from "./view"

export const reducers = combineReducers({
    view: ViewReducer,
    addressManager: AMReducer,
})

export default reducers

export interface State {
    view: ViewState,
    addresseManager: AMState,
}

