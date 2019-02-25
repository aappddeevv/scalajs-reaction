import React, { Suspense } from "react"
import cx from "classnames"

// We do lazy in js code since scala.js cannot handle it yet.
export const SuspenseChild = React.lazy(() => import("./SuspenseChild"))
// dynamic import is really just a Promise, use scala.js React.lazy on the scala.js side
export const X = () => import("./SuspenseChild")

console.log("SuspenseChild via ts React.lazy", SuspenseChild)
console.log("X", X)
//console.log("X called", X())

export interface Props {
    className?: string | null
    label?: string | null
    doit?: boolean | null
    delay?: number | null
    ckey?: string | null // cache key
}

export const SuspenseParent: React.SFC<Props> =
    ({ className, label, children, doit, delay }) => {
        return (
            <Suspense fallback={<div>Loading (typescript)...</div>}>
                <div className={cx("ttg-SuspenseParent", className)}>
                    <div>{label || "SuspenseParent: no label arg provided"}</div>
                    {children}
                </div>
            </Suspense>
        )
    }
