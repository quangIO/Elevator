import kotlin.concurrent.fixedRateTimer

/**
 * Created by quangio.
 */

class ElevatorsController(private val elevators: List<Elevator>) {

    private fun findAvailableElevator(req: OutsideRequests): Elevator? =
        elevators.filter { it.elevatorState.direction == Direction.NONE }
                .minBy { Math.abs(it.elevatorState.floor - req.fromFloor)  }

    fun addRequest(vararg req: OutsideRequests) {
        req.forEach {
            Store.requests.add(it)
        }
    }

    private fun loopCheck() { // can be done reactive-ly with Rx but likely overkill
        fixedRateTimer(name = "loop", initialDelay = 500, period = 500) {
            Store.requests.forEach { r->
                val nearestElevator = findAvailableElevator(r)
                nearestElevator?.addRequestFromInside(r.fromFloor)
            }
        }
    }

    init {
        loopCheck()
    }
}