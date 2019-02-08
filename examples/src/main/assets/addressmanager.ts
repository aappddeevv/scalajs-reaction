import { Action, ActionCreator } from "redux"
import update from "immutability-helper"
import { Address } from "./datamodel"

type Id = string

export interface State {
    activeId: Id | null
    /** This is really a cache for the active item, since it could be derived again. */
    active: Address | null // should not be undefined, must be explicit
    lastActiveAddressId: Id | null
}

const initialState = {
    activeId: null,
    active: null,
    lastActiveAddressId: null,
}

export enum TypeKeys {
    SET_ACTIVE = "am.active"
}

export interface AddressManagerAction extends Action {
    type: TypeKeys
}

interface SetActiveAction extends AddressManagerAction {
    type: TypeKeys.SET_ACTIVE
    activeId: Id | null
    active: Address | null
}

type AllActions = SetActiveAction

export const setActive = (activeId: Id | null,
    active: Address | null): SetActiveAction => {
    return {
        type: TypeKeys.SET_ACTIVE,
        activeId,
        active
    }
}

export const Actions = {
    setActive
}

export function reducer(state: State = initialState,
    action: AllActions): State {
    switch (action.type) {
        case TypeKeys.SET_ACTIVE: {
            console.log("redux router:SET_ACTIVE: id", action.activeId, "action: ", action)
            const last = state.activeId ? { lastActiveAddressId: state.activeId } : {}
            return {
                ...state,
                activeId: action.activeId ? action.activeId : null,
                active: action.active ? action.active : null,
                // no update if the new active id is null
                ...last,
            }
        }
        default:
            return state
    }
}
