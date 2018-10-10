import * as React from "react"
import { Address } from "./datamodel"
import cx = require("classnames")

export interface Props {
    className?: string | null
    address?: Address | null
}

/** Summarize address. */
export const AddressSummary: React.SFC<Props> = (props?: Props) => {
    props = props || {}
    const name = (props && props.address && props.address.name) || "<unnamed address>"
    return (
        <div className={cx("addressSummary", props.className)}>
            Typescript component: Address Summary: {name}
        </div>
    )
}
