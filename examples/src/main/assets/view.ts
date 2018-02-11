import { Action, ActionCreator } from "redux"
import update = require("immutability-helper")

export interface State {
    label?: string | null
    selectedTabKey?: string | null
}

const initialState  = {
    label: "redux label",
    selectedTabKey: null,
}

export enum TypeKeys {
    INIT = "view.init",
    SET_LABEL = "view.setLabel",
    SET_TAB = "vview.setTab"
}

export interface ViewAction extends Action {
    type: TypeKeys
}

interface SetTabAction extends ViewAction {
    type: TypeKeys.SET_TAB,  selectedTabKey: string|null }
interface SetLabelAction  extends ViewAction {
    type: TypeKeys.SET_LABEL, label: string|null }
interface InitAction extends ViewAction { type: TypeKeys.INIT }

type AllActions = SetTabAction | SetLabelAction | InitAction

export const init = (): InitAction => ({
    type: TypeKeys.INIT
})

export const setTab = (selectedTabKey: string|null): SetTabAction => ({
    type: TypeKeys.SET_TAB,
    selectedTabKey
})

export const setLabel = (label: string|null): SetLabelAction => ({
    type: TypeKeys.SET_LABEL,
    label
})

export const Actions = { init, setTab, setLabel }

export function reducer(state: State = initialState, action: AllActions): State {
    switch(action.type) {
        case TypeKeys.SET_LABEL: {
            return { ...state, label: action.label }
        }
        case TypeKeys.SET_TAB: {
            return { ...state, selectedTabKey: action.selectedTabKey }
        }
        case TypeKeys.INIT: {
            // do some init things...
            console.log("view.reducer: init")
        }
        default:
            return state
    }
}
