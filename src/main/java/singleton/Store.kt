package singleton

import domain.OutsideRequests
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by quangio.
 */
object Store { // Singleton + event queue (not so good design actually)
    @Volatile var requests = ConcurrentHashMap.newKeySet<OutsideRequests>()!!
}