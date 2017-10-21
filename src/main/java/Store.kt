/**
 * Created by quangio.
 */
object Store { // Singleton + event queue (not so good design actually)
    @Volatile var requests = mutableSetOf<OutsideRequests>()
}