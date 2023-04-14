# Charter assignment

This code is a solution for the recruitment assignment from Charter.

## How to run

### Prerequisites

Java 17
Java Development IDE (tested with Intellij IDEA)
SQL database (tested with MariaDB on localhost)
Maven, maven-wrapper

### Database

A mariadb instance is required for the application to run. 
Import the file `db/charter.sql` into your database server to create the required database with some sample data.

Fill the `config/db.config` file for the applciation to be able to connect to the desired database.

### WWW in IDE

Import the project into your IDE of choice. The simplest way is to import it directly from GitHub using your IDE's
"get from VCS" functionality. Build the project once it has been imported.

The command to run the project is `mvnw spring-boot:run`. Make sure to add this command in your "Run configuration".
This command will run the embedded WWW server and expose the application on `http://localhost:8080` (by default; port
may vary if there is already another daemon listening on 8080).

## REST

### Adding a transaction

To add a transaction, the following information must be passed by GET to the `addTransaction` endpoint:

* firstname (string, alphanumeric)
* surname (string, alphanumeric)
* email (string, matching email schema),
* amount spent (float, in dollars).

E.g.
http://localhost:8080/addTransaction?firstname=Bartosz&surname=Zaleski&email=bartosz.zaleski@gmail.com&amount=200

### Checking points earned

Supply the customer's email address to the `getPoints` endpoint.

E.g.
http://localhost:8080/getPoints?email=bartosz.zaleski@gmail.com
