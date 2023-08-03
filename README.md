#### How to build and run the application

Execute the following commands in the project's root directory using a terminal

- To build the application and run tests ./mvnw clean install (./mvnw or mvnw because the maven plugin wrapper is being used)
- To run the application ./mvnw spring-boot:run
- To login and have access to the apis:
- username = user
- password = $Password123
- The swagger documentation can be found at http://localhost:8081/swagger-ui.html
- An H2 File embedded database is used and will be preloaded with data on app startup at /build/h2db/db/drone_dispatch if it doesn't exist
- The database can be logged into at localhost:8081/h2-console using the following parameters:
- url = jdbc:h2:file:./build/h2db/db/drone_dispatch
- username = sa
- password =

---

:scroll: **END** 
