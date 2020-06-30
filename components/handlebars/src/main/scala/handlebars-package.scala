// Copyright (c) 2019 The Trapelo Group LLC - All Rights Reserved
// Unauthorized copying of this file, via any medium is strictly prohibited.
// Proprietary and confidential.

import scala.scalajs.js
import js.|
import js.annotation._
import js._

package object handlebars {
  @js.native
  @JSImport("handlebars", "compile")
  def compile(source: String): Template = js.native

  @js.native
  @JSImport("handlebars", "precompile")
  def precompile(source: String): TemplateSpec = js.native

  @js.native
  @JSImport("handlebars", "registerHelper")
  def registerHelper(name: String, cb: js.ThisFunction2[js.Dynamic, js.Any, HelperOptions, String]): Unit = js.native

  @js.native
  @JSImport("handlebars", "unregisterHelper")
  def unregisterHelper(name: String): Unit                                                                = js.native


  @js.native
  @JSImport("handlebars", "registerPartial")
  def registerHelper(name: String, partial: String): Unit = js.native

  @js.native
  @JSImport("handlebars", "unregisterPartial")
  def registerHelper(name: String): Unit = js.native

  @js.native
  @JSImport("handlebars", "Utils")
  val Utils: HandlebarsUtils = js.native

  @js.native
  @JSImport("handlebars", "log")
  def log(level: Int, message: String): Unit = js.native

  @js.native
  @JSImport("handlebars", "template")
  def template(spec: TemplateSpec): Template = js.native

  @js.native
  @JSImport("handlebars", "SafeString")
  def SafeString(spec: String): String = js.native
  
}
