import React from "react"
import cx from "classnames"

// yeah, same as SuspenseParent, repeated here
export interface Props {
  className?: string | null
  label?: string | null
  doit?: boolean | null
  delay?: number | null
  ckey?: string | null
}

// total fake cache
const cache: Record<string, any> = {}

export const SuspenseChild: React.SFC<Props> =
  ({ ckey, className, label, children, doit, delay }) => {

    const cacheKey = ckey || "fetched"
    // if the cache entrty does not exist, throw a Promise...simulate React.lazy().
    if (!cache[cacheKey])
      throw new Promise((resolve, reject) => {
        setTimeout(() => {
          cache[cacheKey] = true
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
