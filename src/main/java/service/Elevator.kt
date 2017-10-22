package service

import domain.Direction
import domain.ElevatorState
import domain.OutsideRequests
import singleton.Store
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by quangio.
 */

class Elevator(val id: Int = 0) : Runnable {
    override fun run() {
        operate()
    }

    val destinations: MutableSet<Int> = ConcurrentHashMap.newKeySet<Int>()
    @Volatile var elevatorState: ElevatorState = ElevatorState()

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
            Store.requests.remove(OutsideRequests(elevatorState.floor, Direction.UP))
            Store.requests.remove(OutsideRequests(elevatorState.floor, Direction.DOWN))
        }
        println("$id " + elevatorState + "    " + Store.requests.toString())
    }

    private fun findDirection(f1: Int, f2: Int): Direction {
        if (f1 < f2) return Direction.UP
        if (f1 > f2) return Direction.DOWN
        return Direction.NONE
    }

    @Synchronized
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

    fun addRequestFromInside(vararg floors: Int) {
        floors.forEach { destinations.add(it) }
        if (elevatorState.direction == Direction.NONE){
            Thread(this).start()
        }
    }
}