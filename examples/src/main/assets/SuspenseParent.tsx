import React, { lazy, Suspense } from "react"
import cx from "classnames"

export const SuspenseChild = React.lazy(() => import("./SuspenseChild"))
export const X = () => () => import("./SuspenseChild")

console.log("SuspenseChild via ts React.lazy", SuspenseChild)
console.log("X", X)
//console.log("X called", X())

export interface Props {
    className?: string | null
    label?: string | null
    doit?: boolean | null
    delay?: number | null
    key?: string | null
}

export const SuspenseParent: React.SFC<Props> =
    ({ className, label, children, doit, delay }) => {
        return (
            <Suspense fallback={<div>Loading...</div>}>
                <div className={cx("ttg-SuspenseParent", className)}>
                    <div>{label || "SuspenseParent: no label arg provided"}</div>
                    {children}
                </div>
            </Suspense>
        )
    }
