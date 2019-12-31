// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT
package ttg
package examples
package addressmanager

import scala.scalajs.js
import js.annotation._
import js.JSConverters._
import scala.concurrent.duration._

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
    private var _activeId: Id    = null
    private var _active: Address = null
    def setActive(id: Id, a: Address): Unit = {
      _activeId = id
      _active = a
    }
    def activeId: Id    = _activeId
    def active: Address = _active
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
