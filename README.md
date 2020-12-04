# favorite-movies quarkus project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/

## Tech Stack

* JDK 11.0.3
* PostgreSQL 13.1
* Quarkus 1.2
* Maven
* AngularJS
* Bootstrap
* Liquibase

## Prerequisites

* Install and setup Java

    https://www.oracle.com/java/technologies/javase-downloads.html
	
* Install Java IDE

    https://www.jetbrains.com/idea/
	
* Install and setup PostgreSQL

    https://www.postgresql.org/download/
	
* Clone project

    https://github.com/evgeniy-sibagatullin/favorite-movies.git
		
* Register on themoviedb site and retrieve API Key (v3 auth)

    https://www.themoviedb.org/settings/api
		
* Connect IDEA and PostgreSQL

    https://www.jetbrains.com/help/idea/connecting-to-a-database.html#connect-to-postgresql-database

* Define your movie-api.api-key and quarkus.datasource.password
    ```
    \favorite-movies\src\main\resources\application.properties
    ```


## Compiling the application  and running in dev mode

Issue the command from folder where pom.xml is present:
    ```
    ./mvnw compile quarkus:dev
    ```

## Running the application in dev mode

Run your application in dev mode that enables live coding using:
    ```
    ./mvnw quarkus:dev
    ```

## Launching the application

Launch the following URL in the browser once the server is up:

http://localhost:9000/index.html

License
=======

    Copyright 2020 Yevhen Sibahatullin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.