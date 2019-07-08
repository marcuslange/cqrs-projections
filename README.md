cqrs-projections
==================================
Exploring patterns for creating and storing projections.

Local Development Environment
-----------------------------
####Start local environment with docker-compose

This will bring up:
* PostgreSQL

Windows & Linux
```
docker-compose up --build --remove-orphans
```
MacOS
```
TMPDIR=/private$TMPDIR docker-compose up --build --remove-orphans
```

####Start Application Locally

Windows
```
gradlew clean bootRun
```
Linux & MacOS
```
./gradlew clean bootRun
```

####Stop Local Environment

```
docker-compose down --rmi local -v
```
