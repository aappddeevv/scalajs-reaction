// Copyright (c) 2019 The Trapelo Group LLC
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

import scala.scalajs.js
import js.|
import js.annotation._

package object apollo_observable {
  import apollo_observable._

  type Subscriber[T] = SubscriptionObserver[T] | js.Function0[Unit] | Subscription
}
