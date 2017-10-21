import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlin.concurrent.fixedRateTimer

/**
 * Created by quangio.
 */

class ElevatorsController(private val elevators: List<Elevator>) {

    private fun findAvailableElevator(req: OutsideRequests): Elevator? =
        elevators.filter { it.destinations.isEmpty() || it.elevatorState.direction == req.direction  }
                .minBy { Math.abs(it.elevatorState.floor - req.fromFloor)  }

    fun addRequest(vararg req: OutsideRequests) {
        req.forEach {
            Store.requests.add(it)
        }
    }

    private fun loopCheck() { // can be done reactive-ly with Rx but likely overkill
        fixedRateTimer(name = "loop", initialDelay = 1000, period = 1000) {
            Store.requests.forEach { r->
                val nearestElevator = findAvailableElevator(r)
                nearestElevator?.let { e->
                    e.addRequestFromInside(r.fromFloor)
                    async(CommonPool) {
                        e.operate()
                    }
                }
            }
        }
    }

    init {
        loopCheck()
    }
}