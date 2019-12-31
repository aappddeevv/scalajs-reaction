// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package react

import collection.mutable
import scala.scalajs.js

object createClass {

  val statics = Map(
    "getDerivedStateFromProps"->true,
    "displayName" -> "true"
  )

  val lifecycle = Map(
    "componentDidMount" -> true,
    "componentDidUpdate" -> true,
    "componentWillUpdate" -> true,
    "shouldComponentUpdate" -> true,
    "componentDidCatch" -> true,
    "componentWillUnmount" -> true,
    "render" -> true
  )

  /**
   * Create a function that creates a class using tricks on Component. This
   * avoids react-create-class and will always be more up to date.
   */
  def apply(spec: js.Object): ReactClass = {
    val prototype = js.Object.create(ReactJS.Component.prototype.asInstanceOf[js.Object])
    val specKeys = js.Object.keys(spec)
    lazy val CreateClassComponent: js.ThisFunction3[js.Dynamic,js.Object,js.Object,js.Object,Unit] = (t,a,b,c) => {
      // init this object using the "superclass" function call but but swap out the this parameter
      ReactJS.Component.call(t, a, b, c)
      // simulate setting state as if this was the constructor method
      t.state = if(js.isUndefined(t.getInitialState)) null else t.getInitialState()
      // auto-bind
      val cp = CreateClassComponent.asInstanceOf[js.Dynamic].prototype
      // autobind, need Array.forEach directly, not in scala.js API
      js.Object.keys(cp.asInstanceOf[js.Object]).asInstanceOf[js.Dynamic].forEach(((th,key) => {
        if(js.typeOf(th.key) == "function" && !lifecycle.contains(key))
          th.key = th.key.bind(t)
      }):js.ThisFunction1[js.Dynamic, String, Unit], t)
    }
    // add spec methods
    for(key <- specKeys) {
      val specValue = spec.asInstanceOf[js.Dynamic].selectDynamic(key)
      if(statics.contains(key))
        CreateClassComponent.asInstanceOf[js.Dynamic].updateDynamic(key)(specValue)
      else 
        prototype.asInstanceOf[js.Dynamic].updateDynamic(key)(specValue)
    }
    // reswizzle to be a react Component
    val ComponentDyn = CreateClassComponent.asInstanceOf[js.Dynamic]
    ComponentDyn.prototype = prototype
    ComponentDyn.prototype.constructor = CreateClassComponent
    ComponentDyn.__defined = false
    CreateClassComponent.asInstanceOf[ReactClass]
  }

}
