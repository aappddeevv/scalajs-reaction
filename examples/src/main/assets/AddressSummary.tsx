import * as React from "react"
import { Address } from "./datamodel"

/** Summarize address. Hardcoded css className. */
export const AddressSummary: React.SFC<Address> = (address: Address) => {
    return (
        <div className="addressSummary">
            Address: {address.name || "noname"}
        </div>
    )
}
