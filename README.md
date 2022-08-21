# Project Description

This is bank document management system to support Credit Journey Digitalizaiton. Bank will use pdf documents
to upload in system. System must be flexible enough to view, upload, delete the PDF files and also
allow to associate Posts against particular uploaded pdf document.

The code consists of REST API calls to support the DMS. 

## Requirements

The project is built using Java 1.8. It uses Maven as the build system.

1. Java - 1.8.x
2. Maven - 3.x.x
3. Spring-boot - 2.7.X

## Building the application using maven

You can build and package the application in the form of a jar file using maven -

```sh
mvn clean package
```

The above command will produce a standalone jar file named `app-X.X.X-SNAPSHOT.jar` in
the `/target` directory.

## Running tests

The `mvn package` command runs all the unit tests and also packages the
application in the form of a jar file. Unit tests and Integration test can be executed with following command.

```sh
mvn test
```

## Running the application

You can run the jar file created by the `mvn package` command like so -

```sh
java -jar target/app.jar
```