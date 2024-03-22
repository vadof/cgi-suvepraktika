## Technologies
- Spring Boot 3.2.3
- Java 17
- Angular 17.1.0
- PostgreSQL 16rc1

## How to Start the Project

Ensure that ports 4200, 8080, and 5432 are available.
Run `mvn clean package` in the `api` directory to generate the backend JAR file.
Run `docker compose up -d` in the root directory (`cgi-suvepraktika$`), which launches the client, server, and database.

## Description
## Server side

When the application is started, movies are added to the database, which are obtained from [themoviedb.org](https://www.themoviedb.org/) using the API. (If this does not happen, please contact me, there may be an issue with the API key). After obtaining all the movies, a schedule of movie sessions for the next 7 days is created. The schedule is updated daily.
## API
If the server part is running, the API documentation can be viewed at this address [OpenAPI](http://localhost:8080/swagger-ui/index.html#/).

## Client Side
On the client side, users need to register an account.
## Recommendations
Upon landing on the homepage, users are shown recommendations based on their genre preferences. If the user has no viewing history, movies are recommended based on their ratings.

Users can view sessions for available dates (if the session has already ended, the movie will not be displayed). If a movie is for adults only, there will be an "18+" mark on it. Users can also view all upcoming sessions for a specific movie.

## Movie tickets
When purchasing tickets, the user selects the number of tickets, after which they are shown a hall with seats. The application recommends seats as close to the center as possible. If there are multiple people and they cannot be seated in the same row, no recommendations will be provided. The algorithm's runtime is O(n), where n is the number of seats.

## Time Spent

Approximately 20 hours.

## Most Challenging Parts

- Finding a good API with a movie database.
- Algorithm for seat recommendations.
- Setting up a new Angular version.
