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

import scala.concurrent.duration.*
import scala.scalajs.js
import js.JSConverters.*
import js.annotation.*

object fakedata {

  /* Fake DAO. Can fail every 10 times and adds a random delay for the fetch. */
  object addressDAO extends AddressDAO {
    private var counter      = -1
    private var fetchCounter = -1
    var delayMaxMillis: Int  = 1000
    var failMod: Int         = 10
    private val addresses    = defaultAddresses.jsSlice(0)
    override val fetch = (id: Id) => {
      fetchCounter = fetchCounter + 1
      if (fetchCounter % failMod == 0) js.Promise.reject("Fetch failed (as specified in failMod)")
      delayPromise(scala.util.Random.nextInt(delayMaxMillis).millis)(addresses)
    }
    override val add = address => {
      addresses.concat(js.Array(address))
      counter = counter + 1
      js.Promise.resolve[String](s"$counter")
    }
    override val remove = address => js.Promise.resolve[Unit](())
    override val update = address => js.Promise.resolve[Unit](())
  }

  object addressesVM extends AddressesViewModel {
    private var _activeId: Id |Null   = null
    private var _active: Address |Null= null
    def setActive(id: Id, a: Address): Unit = {
      _activeId = id
      _active = a
    }
    def activeId: Id|Null    = _activeId
    def active: Address|Null = _active
  }

  val defaultAddresses = generateAddresses(500)

  def generateAddresses(n: Int) = (1 to n).map(i => generateRandom(i.toString())).toJSArray

  private def generateRandom(id: String): Address =
    new Address {
      override val customeraddressid = id
      override val name              = s"${faker.name.lastName()}, ${faker.name.firstName()}"
      override val city              = faker.address.city()
      override val stateorprovince   = faker.address.state()
      override val postalcode        = faker.address.zipCode()
      override val country           = faker.address.country()
      override val createdon         = "3/1/2016"
    }

  /** Delay (ms) resolution. */
  def delayPromise[A](delay: FiniteDuration): A => js.Promise[A] =
    data => new js.Promise[A]((res, rej) => js.timers.setTimeout(delay) { res(data) })
}
