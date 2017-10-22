# service.Elevator

Implement simple threaded elevator controll system (don't use this in realworld please).

(default run on port 4567)

# Endpoints

##### GET /i/:eid/:floor

User make request to :floor in the :eid elevator


##### GET /o/:dir/:floor

User make request with direction :dir (either u for UP or d for DOWN) from :floor

##### GET /status

See current overall status

