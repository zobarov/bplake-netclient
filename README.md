# Remote Arithmetic Operations

Technical Assessment project for Bloomberg Polarlake (2020).
The purposes of the project is to establish remote invocation to perform calculation remotely 
from client REST friendly service to background computator.
Due to simplicity only few basic algebra calculation supported with wide range to enhancement.

## Build and Run

Build with Gradle 6

To build application (you will need Internet access to Maven Central):

> gradle build

The service is available to run in two modes 'local computation' and 'remote computation'.
This implemented via profiles expected to be passed in runtime.

- Computator or Local computation does not perform remote calls but just evaluate the result.
This mode assumed to be 'background server' in the task.

> gradle bootRun --args='--spring.profiles.active=computator'

Runs on port 9000

- NetClient or Remote computation deploy REST endpoint to provide interface for a user.
In this mode application redirects requests to computator instance.

> gradle bootRun --args='--spring.profiles.active=netclient'

Runs on port 8080


## Testing

I'm assuming to run two instances of the application in two separate prompts via:

> gradle bootRun --args='--spring.profiles.active=computator'

and

> gradle bootRun --args='--spring.profiles.active=netclient'

Then it will be available via standard HTTP requests by port 8080.

You can find Postman collections for testing in src/main/resources

Alternatively for manual testing HTTP requests like:

````
http://localhost:8080/math/algebra/mult?operandA=10&operandB=30
````


### Used Libraries and frameworks

- Java 11 
- Spring Boot, REST, Test
- Gradle
- JUnit 5
- Swagger

### Reference Documentation

Endpoint API will be available by http://localhost:8080/swagger-ui.html
