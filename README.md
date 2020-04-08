## Spring Boot Example

This is a basic example of how to use Kotlin in a Spring Boot application. See the [accompanying tutorial](http://kotlinlang.org/docs/tutorials/spring-boot-restful.html)
for more information.

To run:

install mongoDB
```
$ docker pull mongo
```

start mongoDB
```
$ docker run -d -p 27017:27107 --name auzen-db -d mongo
```

resolve mongoUri if you change db port, at
`\src\main\kotlin\com\auzen\config\MongoConfig.kt`

```
$ ./gradlew bootRun
```
