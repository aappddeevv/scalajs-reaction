import React from "react"
import cx from "classnames"

export interface Props {
    className?: string | null
    label?: string | null
}

/**
 * Simple component that shows some text then the children
 * passed to it.
 */
export class LabelAndChild extends React.Component<Props, {}> {

    constructor(props: Props) {
        super(props)
    }

    public render() {
        return (
            <div className={cx("typescriptComponent", this.props.className)}>
                <div>{this.props.label || "<no label arg provided>"}</div>
                {this.props.children}
            </div>
        )
    }
}
