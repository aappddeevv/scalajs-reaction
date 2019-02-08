import React from "react"
import cx from "classnames"

export interface Props {
  className?: string | null
  label?: string | null
  doit?: boolean | null
  delay?: number | null
  key?: string | null
}

const cache: Record<string, any> = {}

export const SuspenseChild: React.SFC<Props> =
  ({ key, className, label, children, doit, delay }) => {

    if (!cache[key || "fetched"])
      throw new Promise((resolve, reject) => {
        setTimeout(() => {
          cache[key || "fetched"] = true
          resolve(true)
        }, delay || 7000)
      })

    return (
      <div className={cx("ttg-SuspensChild", className)}>
        <div>{label || "SuspenseChild (throws a js Promise explicitly): no label arg provided"}</div>
        {children}
      </div>
    )
  }

export default SuspenseChild
