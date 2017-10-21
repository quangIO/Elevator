/**
 * Created by quangio.
 */
fun main(args: Array<String>) {
    val e = Elevator(1)
    val f = Elevator(2)
    val manager = ElevatorsController(listOf(e, f))
    manager.addRequest(OutsideRequests(5, Direction.UP))
    manager.addRequest(OutsideRequests(2, Direction.UP))
}