(window.webpackJsonp=window.webpackJsonp||[]).push([[18],{118:function(e,t,n){"use strict";n.r(t),n.d(t,"frontMatter",(function(){return c})),n.d(t,"metadata",(function(){return i})),n.d(t,"rightToc",(function(){return s})),n.d(t,"default",(function(){return l}));var r=n(1),a=n(6),o=(n(0),n(143)),c={id:"may-changes",title:"Updates",author:"aappddeevv",authorTitle:"scalajs-reaction core team",authorURL:"https://github.com/aappddevv",authorImageURL:"https://avatars1.githubusercontent.com/u/1183337?v=4",tags:["scalajs-reaction"]},i={permalink:"/scalajs-reaction/blog/may-changes",editUrl:"https://github.com/aappddeevv/scalar2020online/edit/master/presentation/blog/blog/2020-05-10-may-changes.md",source:"@site/blog/2020-05-10-may-changes.md",description:"A large number of API tweaks have been made and there has ",date:"2020-05-10T00:00:00.000Z",tags:[{label:"scalajs-reaction",permalink:"/scalajs-reaction/blog/tags/scalajs-reaction"}],title:"Updates",readingTime:.39,truncated:!1,nextItem:{title:"Package and namespace changes",permalink:"/scalajs-reaction/blog/changes"}},s=[],p={rightToc:s};function l(e){var t=e.components,n=Object(a.a)(e,["components"]);return Object(o.b)("wrapper",Object(r.a)({},p,n,{components:t,mdxType:"MDXLayout"}),Object(o.b)("p",null,"A large number of API tweaks have been made and there has\nbeen an expansion of hand-crafted facades available including\nmany needed for production web applications."),Object(o.b)("p",null,"Also, many of the scala.js specific helpers have been\nsplit out to a separate project that is ",Object(o.b)("em",{parentName:"p"},"not")," dependent\non the DOM and can be used both server and client side.\nSee the ",Object(o.b)("inlineCode",{parentName:"p"},"jshelpers")," sub-project for details. The helpers\ninclude extensive ",Object(o.b)("inlineCode",{parentName:"p"},"js.Promise")," extensions that make them\nmuch more friendly to ",Object(o.b)("inlineCode",{parentName:"p"},"scala")," developers."))}l.isMDXComponent=!0},143:function(e,t,n){"use strict";n.d(t,"a",(function(){return u})),n.d(t,"b",(function(){return m}));var r=n(0),a=n.n(r);function o(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function c(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function i(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?c(Object(n),!0).forEach((function(t){o(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):c(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function s(e,t){if(null==e)return{};var n,r,a=function(e,t){if(null==e)return{};var n,r,a={},o=Object.keys(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var p=a.a.createContext({}),l=function(e){var t=a.a.useContext(p),n=t;return e&&(n="function"==typeof e?e(t):i({},t,{},e)),n},u=function(e){var t=l(e.components);return a.a.createElement(p.Provider,{value:t},e.children)},d={inlineCode:"code",wrapper:function(e){var t=e.children;return a.a.createElement(a.a.Fragment,{},t)}},b=Object(r.forwardRef)((function(e,t){var n=e.components,r=e.mdxType,o=e.originalType,c=e.parentName,p=s(e,["components","mdxType","originalType","parentName"]),u=l(n),b=r,m=u["".concat(c,".").concat(b)]||u[b]||d[b]||o;return n?a.a.createElement(m,i({ref:t},p,{components:n})):a.a.createElement(m,i({ref:t},p))}));function m(e,t){var n=arguments,r=t&&t.mdxType;if("string"==typeof e||r){var o=n.length,c=new Array(o);c[0]=b;var i={};for(var s in t)hasOwnProperty.call(t,s)&&(i[s]=t[s]);i.originalType=e,i.mdxType="string"==typeof e?e:r,c[1]=i;for(var p=2;p<o;p++)c[p]=n[p];return a.a.createElement.apply(null,c)}return a.a.createElement.apply(null,n)}b.displayName="MDXCreateElement"}}]);