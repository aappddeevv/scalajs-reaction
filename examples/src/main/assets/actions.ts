import { Actions as ViewActions} from "./view"
import { Actions as AMActions } from "./addressmanager"

export const Actions = {
    View: ViewActions,
    AddressManager: AMActions
}

export {
    ViewActions as ViewActions,
    AMActions as AddressManagerActions
}
