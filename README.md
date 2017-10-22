# service.Elevator

Implement simple threaded elevator control system (don't use this in production please).

(default run on port 4567)

# Endpoints

##### GET /i/:eid/:floor

User makes request to :floor in the :eid elevator


##### GET /o/:dir/:floor

User makes request with direction :dir (either u for UP or d for DOWN) from :floor

##### GET /status

See current overall status

# Todo

* Elevator is not slay tree, save power
* Write unit tests
* Migrate to Kotlin coroutines
* Maintenance mode
* Write better todo

