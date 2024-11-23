
# User API

A simple API for register users Using Java 17 and Spring



## Features

- Use UUID for Primay keys
- Token with JWT
- Use Java record classes for DTOs
- Use MapStruct for easly class mappings
- Use loombok to avoid boilarplate code
- Use docker and docker compose to easily run the project
- Use gradle as build system


## Run Locally
- No databases scripts needed since DB will create automatically
- Import the collection Json included in the repo
- Access to Swagger doc : http://localhost:8080/swagger-ui/index.html

Clone the project

```bash
  git clone https://github.com/johnalvn/smartjob-BCI.git
```

Go to the project directory

```bash
  cd smartjob-BCI
```

Start the server (if you have Java 17 installed)

```bash
  ./gradlew bootRun
```

Or if you prefer to use docker

```bash
  docker compose up
```