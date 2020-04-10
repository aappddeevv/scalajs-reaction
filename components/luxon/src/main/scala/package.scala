package object luxon {
  import Ordering.Double.TotalOrdering
  import luxon._

  /** Use this when you need an ordering or set as an implicit
   *  so it is automatically picked up.
   */
  val dateOrdering = Ordering.by((item: DateTime) => item.valueOf().asInstanceOf[Double])
}
