# service.Elevator

[![forthebadge](http://forthebadge.com/images/badges/60-percent-of-the-time-works-every-time.svg)](http://forthebadge.com)
[![forthebadge](http://forthebadge.com/images/badges/uses-badges.svg)](http://forthebadge.com)

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

* Elevator is not splay tree, save power
* Write unit tests
* Migrate to Kotlin coroutines
* Maintenance mode
* Write better todo

