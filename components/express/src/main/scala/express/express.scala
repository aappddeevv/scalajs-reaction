// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package express

import scala.scalajs.js
import js.|
import js.annotation._

/** Immutable Response builder. */
case class ResponseBuilder(run: Response => Response) { subject =>
  def underlying(next: Response => Response) = ResponseBuilder(run andThen next)
  def status(s: Int) = subject.underlying{r => r.status(s); r}
  def json(v: js.Any|String) = subject.underlying{r => r.json(v.asInstanceOf[js.Any]); r }
  def header(k: String, v: String) = subject.underlying{r => r.set(k,v); r}
}


object ResponseBuilder {
  def apply(s: Int) = new ResponseBuilder(_.status(s))
}
