// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

// Copyright (c) 2018 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT
package ttg.react.examples
package addressmanager

import scala.scalajs.js
import scala.concurrent.duration._

object fakedata {

  object addressDAO extends AddressDAO {
    private var counter = 0
    private val addresses = defaultAddresses.jsSlice(0)
    override val fetch = (id: Id) => {
      delayPromise(scala.util.Random.nextInt(1000).millis)(addresses)
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
    private var selectedIds: IdList = js.Array()
    def setSelectedIds(ids: IdList): Unit = { selectedIds = ids }
    def getSelectedIds(): IdList = selectedIds
  }

  val defaultAddresses = js.Array[Address](
    new Address {
      override val customeraddressid = "1"
      override val name = "address1"
      override val city = "NYC"
      override val createdon = "2/1/2018"
    },
    new Address {
      override val customeraddressid = "2"
      override val name = "address2"
      override val stateorprovince = "MA"
      override val city = "Boston"
      override val createdon = "1/1/2017"
    }
  )

  /** Delay (ms) resolution. */
  def delayPromise[A](delay: FiniteDuration): A => js.Promise[A] = 
    data => new js.Promise[A]((res, rej) => js.timers.setTimeout(delay){ res(data)})
}

