---
layout: docs
title: Virtual DOM
---
I simple "virtual DOM" has been provide. For facade libraries, this usually means some way to create HTML elements with attributes and children. It does *not* mean a virtual dom implementation since react already provides that.

`scalajs-react-vdom` is a separate vdom that you can use if you want to. You can another project's vdom as well as long as the final "element" is typed as a `ReactNode`.

