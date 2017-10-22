import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async

/**
 * Created by quangio.
 */

class Elevator(val id: Int = 0) {
    val destinations: MutableSet<Int> = mutableSetOf()
    var elevatorState: ElevatorState = ElevatorState()

    private fun move(direction: Direction) {
        Thread.sleep(1000)
        when (direction) {
            Direction.UP -> elevatorState.floor += 1
            Direction.DOWN -> elevatorState.floor -= 1
            Direction.NONE -> return
        }
        destinations.remove(elevatorState.floor)
        Store.requests.remove(OutsideRequests(elevatorState.floor, elevatorState.direction))


        if (destinations.isEmpty()) {
            elevatorState.direction = Direction.NONE
        }
        println("$id " + elevatorState)
    }

    private fun findDirection(f1: Int, f2: Int): Direction {
        if (f1 < f2) return Direction.UP
        if (f1 > f2) return Direction.DOWN
        return Direction.NONE
    }

    private fun operate() {
        if (destinations.isEmpty()) return
        when (elevatorState.direction) {
            Direction.UP -> {
                if (destinations.max()!! < elevatorState.floor) {
                    elevatorState.direction = Direction.DOWN
                }
            }
            Direction.DOWN -> {
                if (destinations.min()!! > elevatorState.floor) {
                    elevatorState.direction = Direction.UP
                }
            }
            Direction.NONE -> {
                elevatorState.direction = findDirection(elevatorState.floor, destinations.first())
            }
        }
        move(elevatorState.direction)
        if (elevatorState.direction != Direction.NONE) operate()
    }

    @Synchronized
    fun addRequestFromInside(vararg floors: Int) {
        floors.forEach { destinations.add(it) }
        async(CommonPool) {
            operate()
        }
    }
}