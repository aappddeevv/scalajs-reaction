(window.webpackJsonp=window.webpackJsonp||[]).push([[15],{115:function(e,n,t){"use strict";t.r(n),t.d(n,"frontMatter",(function(){return i})),t.d(n,"metadata",(function(){return c})),t.d(n,"rightToc",(function(){return s})),t.d(n,"default",(function(){return p}));var r=t(1),a=t(6),o=(t(0),t(143)),i={id:"routing",title:"Routing"},c={id:"topics/routing",title:"Routing",description:"Routing is a complex topic and many react projects have sprung up around",source:"@site/docs/topics/router.md",permalink:"/scalajs-reaction/docs/topics/routing",editUrl:"https://github.com/aappddeevv/scalar2020online/edit/master/presentation/docs/topics/router.md",sidebar:"docs",previous:{title:"Forms",permalink:"/scalajs-reaction/docs/topics/forms"},next:{title:"Redux",permalink:"/scalajs-reaction/docs/topics/redux"}},s=[{value:"Lowest Level (DEPRECATED)",id:"lowest-level-deprecated",children:[]},{value:"Routing Component (DEPRECATED)",id:"routing-component-deprecated",children:[]}],l={rightToc:s};function p(e){var n=e.components,t=Object(a.a)(e,["components"]);return Object(o.b)("wrapper",Object(r.a)({},l,t,{components:n,mdxType:"MDXLayout"}),Object(o.b)("p",null,"Routing is a complex topic and many react projects have sprung up around\nrouting. scalajs react facades also provide routing libraries. You can use any\njavascript based routing librar you wish, such as\n",Object(o.b)("a",Object(r.a)({parentName:"p"},{href:"https://reacttraining.com/react-router"}),"react-router"),". "),Object(o.b)("p",null,"NOTE: The approach below is deprecated. Just use react-router. A\nscalajs-reaction version of react-router-dom is provided. Consult the example\nprogram's source code for an example of using react-router in scalajs-reaction."),Object(o.b)("p",null,"DON'T LOOK BELOW!"),Object(o.b)("h2",{id:"lowest-level-deprecated"},"Lowest Level (DEPRECATED)"),Object(o.b)("p",null,"ReasonReact provides a small, ~100 line router, in the base distribution and\nthat small router code is also included in the scalajs-react implementation."),Object(o.b)("p",null,"The router establishes a simple callback mechanism when the URL changes and\nallows you to obtain the current URL as well as push a new URL state into the\nhistory object running in the browser."),Object(o.b)("p",null,"You can route to some hashes by doing something like the following:"),Object(o.b)("pre",null,Object(o.b)("code",Object(r.a)({parentName:"pre"},{className:"language-scala"}),'  sealed trait Action\n  case class Navigate(page: Page) extends Action\n  case object SomeOtherAction extends Action\n  \n  case class State(page: Page)\n\n  sealed trait Page\n  case object Home extends Page\n  case class AnotherPage(id: String) extends Page\n\n  val c = reducerComponent[State, Action]("MyComponent")\n  import c.ops._\n  \n  def apply() =\n   c.copy(new methods { \n     val initialState = () => State(Home)\n     \n     val reducer = (action, state, gen) => {\n       action match { \n         case Navigate(p) => gen.update(state.copy(page = p))\n         case SomeOtherAction = gen.skip // for demo purposes only\n       }\n     }\n\n     didMount = js.defined(self => {\n       val token = router.watchUrl{ url => \n         url match {\n           case Some("home") => self.send(Navigate(Home))\n           case Some("person") => self.send(Navigate(AnotherPage("person")))\n           case _ => self.send(Navigate(Home))\n         }\n       }\n       // prime the pump!\n       // ...don\'t forget this...\n       self.onUnmount(() => router.unwatchUrl(token)\n     })\n     \n     val render = self => {\n       self.state.page match { \n          case Home => "home"\n          case AnotherPage(id) => id\n       }\n     }\n       \n  })\n')),Object(o.b)("h2",{id:"routing-component-deprecated"},"Routing Component (DEPRECATED)"),Object(o.b)("p",null,'We have a provided a DOM agnostic router component and a specialization of that\nrouting component for the ReasonReact style router described in the previous\nsection. You can have as many RoutingComponents as you like in your application\nas there is no shared state between them. Think of these components as rendering\nbased on the URL and if you don\'t like the URL, return a "null" node which\neffectively turns off rendering for any children of the routing component.'),Object(o.b)("p",null,"You are free to use another routing strategy and plug that into\n",Object(o.b)("inlineCode",{parentName:"p"},"RoutingComponent"),"."),Object(o.b)("pre",null,Object(o.b)("code",Object(r.a)({parentName:"pre"},{className:"language-scala"}),'// ReactionRouting\nimport react.router.browser._\n\n// App\nobject Routing {\n    import ReactionRouting._\n    // if using sparsetech.trail route matching library\n    val coolRoute = Root / Arg[String] / Arg[String]\n    val login = Root / "login"\n    \n    val config = ReactionConfig(rules(parts => {\n       parts.pathname match {\n          case coolRoute(entityName, entityId) => Render(() => SomeView(entityName, entityId))\n          case login(_) => Redirect("/login", RedirectMethod.Push)\n          case _ => Render(() => UnknownPage(parts))\n       }\n       // or do something custom\n       parts.pathname match {\n          case parts if(parts.map(_.hash == "blah").getOrElse(false)) => Render(() => Blah)\n          case _ => Render(() => null)\n       }\n    }))\n}\n\ndef main() {\n    reactdom.createAndRenderWithId(\n      Fabric(new Fabric.Props {})(\n        ReactionRouting(Routing.config)\n      ),\n      "container"\n     )\n    // kick things off\n    react.router.push("")\n}\n')),Object(o.b)("p",null,'You will want to use a real "matching" library like\n',Object(o.b)("a",Object(r.a)({parentName:"p"},{href:"https://github.com/sparsetech/trail"}),"trail"),' to match on the URL and provide\n"route-to-page" routing processing in your config.'))}p.isMDXComponent=!0},143:function(e,n,t){"use strict";t.d(n,"a",(function(){return u})),t.d(n,"b",(function(){return g}));var r=t(0),a=t.n(r);function o(e,n,t){return n in e?Object.defineProperty(e,n,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[n]=t,e}function i(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);n&&(r=r.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,r)}return t}function c(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?i(Object(t),!0).forEach((function(n){o(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):i(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}function s(e,n){if(null==e)return{};var t,r,a=function(e,n){if(null==e)return{};var t,r,a={},o=Object.keys(e);for(r=0;r<o.length;r++)t=o[r],n.indexOf(t)>=0||(a[t]=e[t]);return a}(e,n);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(r=0;r<o.length;r++)t=o[r],n.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(a[t]=e[t])}return a}var l=a.a.createContext({}),p=function(e){var n=a.a.useContext(l),t=n;return e&&(t="function"==typeof e?e(n):c({},n,{},e)),t},u=function(e){var n=p(e.components);return a.a.createElement(l.Provider,{value:n},e.children)},d={inlineCode:"code",wrapper:function(e){var n=e.children;return a.a.createElement(a.a.Fragment,{},n)}},m=Object(r.forwardRef)((function(e,n){var t=e.components,r=e.mdxType,o=e.originalType,i=e.parentName,l=s(e,["components","mdxType","originalType","parentName"]),u=p(t),m=r,g=u["".concat(i,".").concat(m)]||u[m]||d[m]||o;return t?a.a.createElement(g,c({ref:n},l,{components:t})):a.a.createElement(g,c({ref:n},l))}));function g(e,n){var t=arguments,r=n&&n.mdxType;if("string"==typeof e||r){var o=t.length,i=new Array(o);i[0]=m;var c={};for(var s in n)hasOwnProperty.call(n,s)&&(c[s]=n[s]);c.originalType=e,c.mdxType="string"==typeof e?e:r,i[1]=c;for(var l=2;l<o;l++)i[l]=t[l];return a.a.createElement.apply(null,i)}return a.a.createElement.apply(null,t)}m.displayName="MDXCreateElement"}}]);