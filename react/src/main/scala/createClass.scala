/*
 * Copyright (c) 2018 The Trapelo Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package react

object createClass {

  val statics: Map[String, Any] = Map(
    "getDerivedStateFromProps" -> true,
    "displayName"              -> "true"
  )

  val lifecycle = Map(
    "componentDidMount"     -> true,
    "componentDidUpdate"    -> true,
    "componentWillUpdate"   -> true,
    "shouldComponentUpdate" -> true,
    "componentDidCatch"     -> true,
    "componentWillUnmount"  -> true,
    "render"                -> true
  )

  /**
   * Create a function that creates a class using tricks on Component. This
   * avoids react-create-class and will always be more up to date.
   */
  /*
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
      js.Object.keys(cp.asInstanceOf[js.Object]).asInstanceOf[js.Dynamic].forEach(js.defined{(th,key) =>
        if(js.typeOf(th.key) == "function" && !lifecycle.contains(key))
          th.key = th.key.bind(t)
      }, t)
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
 */

}
