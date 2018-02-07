// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package ttg
package react

import scala.scalajs.js
import js.JSConverters._
import js.Dynamic.{literal => lit}

final case class ComponentOps[S, RP, A](c: Component[_, _, _, _]) {
  @inline def toEl: ReactElement = elements.element(c)
  @inline def toEl(key: Option[String] = None, ref: Option[RefCb] = None) =
    elements.element(c, key, ref)
}

trait ComponentSyntax {
  implicit def componentOpsSyntax[S, RP, A](c: Component[_, _, _, _]) =
    ComponentOps[S, RP, A](c)
}

trait ElementConversionSyntax {
  @inline implicit def stringToElementImpl(v: String): ReactNode = stringToElement(v)
  // this is actually a direct conversion, move to a separate syntax to be explicit?
  @inline implicit def arrToElementImpl(v: js.Array[ReactNode]): ReactNode = arrayToElement(v)
}

trait AllSyntax extends ComponentSyntax with ElementConversionSyntax

object syntax {
  object all extends AllSyntax
  object component extends ComponentSyntax
  object element extends ElementConversionSyntax
}

trait C2E {
  @inline implicit def c2e(c: Component[_, _, _, _]): ReactNode = elements.element(c)
}

trait AllInstances extends C2E

object instances {
  object all extends AllInstances
  object component extends C2E
}

object implicits extends AllSyntax with AllInstances
