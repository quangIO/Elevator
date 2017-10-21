/**
 * Created by quangio.
 */
fun main(args: Array<String>) {
    val e = Elevator(1)
    val f = Elevator(2)
    val g = Elevator(3)
    val manager = ElevatorsController(listOf(e, f, g))
    manager.addRequest(OutsideRequests(5, Direction.DOWN))
    manager.addRequest(OutsideRequests(2, Direction.UP))
    manager.addRequest(OutsideRequests(1, Direction.UP))
    Thread.sleep(10000)
    println("Hmmm")
    e.addRequestFromInside(3)
    //manager.addRequest(OutsideRequests(2, Direction.UP))
}