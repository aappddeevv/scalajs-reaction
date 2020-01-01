// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg 
package router
package browser

import scala.scalajs.js
import js.JSConverters._
import org.scalajs.dom

/**
 * URL ops. F-bounded poly.
 */
// trait URLOps[Self <: URLOps[Self]] {
//   self: Self =>

//   def create(source: String): Self
//   def string: String
//   def isEmpty: Boolean
//   def isAbsolute: Boolean

//   def map(mod: String => String): Self = create(mod(self.string))
//   def +(source: String): Self = map(_ + source)
//   def +(p: Self): Self = this + p.string
//   def /(source: String): Self = this
//   def /(p: Self): Self = self + self.string
// }

// /** Simple replacement for java URI/URL. Tags a string with the type of URL it
//  * represents and allows you to add some segments, etc. Helps ensure you provide
//  * a valid URL where that's needed.  A URL can be in just a few states: absolute
//  * or just some path segments.  It would be nice to use a refined type here, but
//  * this needs to stay lean.
//  * 
//  * Inspired by
//  * https://github.com/lemonlabsuk/scala-uri/tree/master/shared/src/main/scala/io/lemonlabs/uri
//  * and scalajs-react Path.
//  */
// abstract sealed trait URL[P <: URLOps[P]] extends URLOps[P]

// /** Path with authority. You need one for routing e.g. https://server/api/v2. */
// final case class AbsoluteURL private[react](value: String) extends URL[AbsoluteURL] {
//   def isEmpty = false
//   def isAbsolute = true
//   def string = value

//   /** Create an absolute URL by appending. */
//   def apply(p: Path) = AbsoluteURL(value + p.value)
// }

// object AbsoluteURL {
//   def locationOrigin = dom.document.location.origin.toOption.map(AbsoluteURL(_))
//   def locationHref = AbsoluteURL(dom.document.location.href)
// }

// /**
//  * Segments.
//  */
// final case class Path(value: String) extends URL[Path] {
//   def isEmpty = false
//   def isAbsolute = true
//   def string = value

//   def resolve(url: AbsoluteURL): AbsoluteURL = url apply this
// }
