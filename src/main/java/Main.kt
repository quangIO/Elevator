import domain.Direction
import domain.OutsideRequests
import service.Elevator
import service.ElevatorsController
import singleton.Store
import spark.Spark.get

/**
 * Created by quangio.
 */

fun sample() {
    val e = Elevator(1)
    val f = Elevator(2)
    val g = Elevator(3)
    g.addRequestFromInside(8)
    println("gg")
    val manager = ElevatorsController(listOf(e, f, g).toTypedArray())
    manager.addRequest(OutsideRequests(5, Direction.DOWN))
    manager.addRequest(OutsideRequests(2, Direction.UP))
    Thread.sleep(3000)
    manager.addRequest(OutsideRequests(1, Direction.UP))

    Thread.sleep(5000)
    println("gg " + Store.requests.isEmpty())
    e.addRequestFromInside(4)
    manager.addRequest(OutsideRequests(7, Direction.DOWN))
    manager.addRequest(OutsideRequests(3, Direction.DOWN))
}

fun main(args: Array<String>) {
    val n = 5
    val elevators = Array(n, { Elevator() })
    for (i in 0 until n) {
        elevators[i] = Elevator(i)
    }
    val manager = ElevatorsController(elevators)
    get("Hi") { _, _ ->
        "Hi"
    }
    get("/i/:eid/:floor") { req, _ ->
        val id = req.params("eid").toInt()
        val floor = req.params("floor").toInt()
        elevators[id].addRequestFromInside(floor)
        elevators[id].destinations
    }

    get("/o/:dir/:floor") { req, _ ->
        val floor: Int = req.params("floor").toInt()
        val dir = when (req.params("dir")) {
            "u" -> Direction.UP
            "d" -> Direction.DOWN
            else -> Direction.NONE
        }
        manager.addRequest(OutsideRequests(floor, dir))
        "ok"
    }
    get("/status") { req, response ->
        val r = HashMap<String, Any>()
        r["OutsideRequest"] = Store.requests
        elevators.forEach {
            r[it.id.toString()] = it.elevatorState
        }
        r
    }
}