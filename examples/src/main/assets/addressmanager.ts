import { Action, ActionCreator } from "redux"
import update = require("immutability-helper")

type Id = string

export interface State {
    selectedIds: Array<Id>
}

const initialState  = {
    selectedIds: []
}

export enum TypeKeys {
    SET_IDS = "am.setIds",
}

export interface AddressManagerAction extends Action {
    type: TypeKeys
}

interface SetSelectedIdsAction extends AddressManagerAction {
    type: TypeKeys.SET_IDS
    selectedIds: Array<Id>
}

type AllActions = SetSelectedIdsAction

export function setSelectedIds(selectedIds: Array<Id>): SetSelectedIdsAction {
    return {
        type: TypeKeys.SET_IDS,
        selectedIds
    }
}

export const Actions = { setSelectedIds }

export function reducer(state: State = initialState,
                        action: AllActions): State {
    switch(action.type) {
        case TypeKeys.SET_IDS: {
            return { ...state, selectedIds: action.selectedIds }
        }
        default:
            return state
    }
}
