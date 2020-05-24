(window.webpackJsonp=window.webpackJsonp||[]).push([[14],{114:function(e,t,n){"use strict";n.r(t),n.d(t,"frontMatter",(function(){return o})),n.d(t,"metadata",(function(){return s})),n.d(t,"rightToc",(function(){return l})),n.d(t,"default",(function(){return p}));var a=n(1),r=n(6),i=(n(0),n(143)),o={id:"vdom",title:"Virtual DOM"},s={id:"core/vdom",title:"Virtual DOM",description:'scalajs-reaction has a "virtual DOM."',source:"@site/docs/core/vdom.md",permalink:"/scalajs-reaction/docs/core/vdom",editUrl:"https://github.com/aappddeevv/scalar2020online/edit/master/presentation/docs/core/vdom.md",sidebar:"docs",previous:{title:"Defining Components",permalink:"/scalajs-reaction/docs/core/creating"},next:{title:"Exporting From Scala to JS",permalink:"/scalajs-reaction/docs/core/exporting"}},l=[{value:"Attribute List Based VDOM",id:"attribute-list-based-vdom",children:[]},{value:"Styling",id:"styling",children:[]}],c={rightToc:l};function p(e){var t=e.components,n=Object(r.a)(e,["components"]);return Object(i.b)("wrapper",Object(a.a)({},c,n,{components:t,mdxType:"MDXLayout"}),Object(i.b)("p",null,'scalajs-reaction has a "virtual DOM."\nThe vdom is there to help you create elements and their properties\nmore easily. ',Object(i.b)("inlineCode",{parentName:"p"},"vdom")," is a separate package "),Object(i.b)("p",null,"To you use the vdom, do"),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"import react.vdom._\nimport tags._ // to import standard HTML vdom tags\n")),Object(i.b)("p",null,"This import also provides you the ability to create attributes as a list (discussed\nbelow), compared\nto using non-native JS traits, and create elements using their name."),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),"// create a div, ReactDomElement < ReactNode\nval aDiv: ReactDomElement = div(...attributes...)(..children...)\n")),Object(i.b)("p",null,"The 2 parameter list (which allows you to easily curry as well) helps break up\nthe lists between attributes and children which improves type\ninference. Children need to be ",Object(i.b)("inlineCode",{parentName:"p"},"ReactNode")," or a subclass of this such as\n",Object(i.b)("inlineCode",{parentName:"p"},"ReactElement"),". An ",Object(i.b)("inlineCode",{parentName:"p"},"import react.implicits._")," will bring in an implicit\nconversion to a ",Object(i.b)("inlineCode",{parentName:"p"},"ReactNode")," for you. "),Object(i.b)("p",null,"The first parameter list takes a statically defined attribute trait whose name\nis the capitalized version of the element:"),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),'val aDiv = div(new DivProps { className = "myDivClassName" })("child1", "child2")\n')),Object(i.b)("p",null,"If you only have children and no attribute, skip the first set of parenthesis,"),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),'val aDiv = div("child1", "child2")\n')),Object(i.b)("p",null,"In the case of div, you can use a pre-defined helper:"),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),'divWithClassname("my-class-name",\n  child1,\n  child2\n)\n')),Object(i.b)("h3",{id:"attribute-list-based-vdom"},"Attribute List Based VDOM"),Object(i.b)("p",null,"Another attribute list based vdom is available by importing\n",Object(i.b)("inlineCode",{parentName:"p"},"vdom.prefix_<^._"),". Elements should be prefixed with ",Object(i.b)("inlineCode",{parentName:"p"},"<.")," as in ",Object(i.b)("inlineCode",{parentName:"p"},"<.div")," and\nattributes are specified using ",Object(i.b)("inlineCode",{parentName:"p"},"^.")," as in ",Object(i.b)("inlineCode",{parentName:"p"},"^.className"),". This is alot\nlike the API that scalajs-reaction (not scalajs-reaction) uses and is\ncommon in other scala.js facade libraries. I recommend not using this\nstyle though."),Object(i.b)("p",null,"The attribute list VDOM has less type safety and allows you to add attributes\noutside the prescribed list based on a statically defined trait, but it can be\nmore convenient at times to use this syntax."),Object(i.b)("p",null,"The funny symbol ",Object(i.b)("inlineCode",{parentName:"p"},"<.")," is meant to look like an element since scalajs-react does\nnot have a JSX macro/processor. Attributes start with ",Object(i.b)("inlineCode",{parentName:"p"},"^.")," as in\n",Object(i.b)("inlineCode",{parentName:"p"},"^.className"),". If you import both ",Object(i.b)("inlineCode",{parentName:"p"},"import ^._")," and ",Object(i.b)("inlineCode",{parentName:"p"},"import ^._")," you can do away\nwith those symbols completely at the risk of having a name clash. If define your\nown symbols such as ",Object(i.b)("inlineCode",{parentName:"p"},"E")," or ",Object(i.b)("inlineCode",{parentName:"p"},"A"),", you can use those. Just repeat the definition of\nthose symbols as they are defined in react.redux package object and use\nyours."),Object(i.b)("p",null,"The attributes in a list are automatically converted to a js.Object behind the\nscenes. Attributes that result in a js.undefined value are removed from the\njavascript object automatically."),Object(i.b)("p",null,'In its current form, the attributes are a list converted to a props, which is\nnot really typesafe from the perspective the attributes or the props "set of\nattributes." To improve this we need to use non-native JS traits which I have\nnot provided, then provide the functions to use the props as well as perhaps the\nlist of attributes. The non-native JS trait versions of these vdom elements will\nbe added shortly.'),Object(i.b)("p",null,"The list based vdom allows you to specify style attributes in a list using\n",Object(i.b)("inlineCode",{parentName:"p"},"style = Style(...)"),". See the next section for more safe version of creating\nstyles."),Object(i.b)("h2",{id:"styling"},"Styling"),Object(i.b)("p",null,"A simple, slightly more safe inline style capability has also been provided."),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),'import vdom._\nval style1 = new StyleAttr { display: "flex" }\nval style2 = new StyleAttr { flexDirection: "column" }\n\nimport react.vdom.style._\n// style2 takes precedence\nval flexVertical = style1.combine(style2)\n')),Object(i.b)("p",null,"you can add style attribute anything missing via:"),Object(i.b)("pre",null,Object(i.b)("code",Object(a.a)({parentName:"pre"},{className:"language-scala"}),'val unsafeStyle = unsafeAddProp(style1, "foo", "bar")\n')),Object(i.b)("p",null,'You would use these as the "value" in the list of attributes for the vdom elements.'))}p.isMDXComponent=!0},143:function(e,t,n){"use strict";n.d(t,"a",(function(){return b})),n.d(t,"b",(function(){return m}));var a=n(0),r=n.n(a);function i(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function o(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,a)}return n}function s(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?o(Object(n),!0).forEach((function(t){i(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):o(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function l(e,t){if(null==e)return{};var n,a,r=function(e,t){if(null==e)return{};var n,a,r={},i=Object.keys(e);for(a=0;a<i.length;a++)n=i[a],t.indexOf(n)>=0||(r[n]=e[n]);return r}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(a=0;a<i.length;a++)n=i[a],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(r[n]=e[n])}return r}var c=r.a.createContext({}),p=function(e){var t=r.a.useContext(c),n=t;return e&&(n="function"==typeof e?e(t):s({},t,{},e)),n},b=function(e){var t=p(e.components);return r.a.createElement(c.Provider,{value:t},e.children)},d={inlineCode:"code",wrapper:function(e){var t=e.children;return r.a.createElement(r.a.Fragment,{},t)}},u=Object(a.forwardRef)((function(e,t){var n=e.components,a=e.mdxType,i=e.originalType,o=e.parentName,c=l(e,["components","mdxType","originalType","parentName"]),b=p(n),u=a,m=b["".concat(o,".").concat(u)]||b[u]||d[u]||i;return n?r.a.createElement(m,s({ref:t},c,{components:n})):r.a.createElement(m,s({ref:t},c))}));function m(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var i=n.length,o=new Array(i);o[0]=u;var s={};for(var l in t)hasOwnProperty.call(t,l)&&(s[l]=t[l]);s.originalType=e,s.mdxType="string"==typeof e?e:a,o[1]=s;for(var c=2;c<i;c++)o[c]=n[c];return r.a.createElement.apply(null,o)}return r.a.createElement.apply(null,n)}u.displayName="MDXCreateElement"}}]);