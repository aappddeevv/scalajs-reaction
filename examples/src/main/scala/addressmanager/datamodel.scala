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

package ttg
package examples
package addressmanager

import scala.scalajs.js
import js.annotation.*

/** See datamodel.ts */
trait Address extends js.Object:
  val customeraddressid: js.UndefOr[String] = js.undefined
  val name: js.UndefOr[String]              = js.undefined
  val city: js.UndefOr[String]              = js.undefined
  val stateorprovince: js.UndefOr[String]   = js.undefined
  val createdon: js.UndefOr[String]         = js.undefined
  val postalcode: js.UndefOr[String]        = js.undefined
  val country: js.UndefOr[String]           = js.undefined

trait AddressDAO extends js.Object:
  val fetch: js.Function1[Id, js.Promise[AddressList]]
  val add: js.Function1[Address, js.Promise[Id]]
  val remove: js.Function1[String, js.Promise[Unit]]
  val update: js.Function1[Address, js.Promise[Unit]]

/** Manage data access and selection state as a pair. */
trait AddressesViewModel extends js.Object:
  // address can be null value explicitly per plain old scala
  def setActive(id: Id, address: Address): Unit
  def activeId: Id
  // per the typescript documentation, Address|null in the redux state
  def active: Address

