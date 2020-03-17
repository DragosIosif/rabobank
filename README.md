# Rabobank assignment
The purpose for this application is to read csv and xml documents containing transactions, validate them and output the failed transactions.

## Configurations
Before running the application, check the `application.properties` file(_src/main/resources/application.properties_).
The first property, `storage.location` points to the folder where all the files will be uploaded to. The default is `storage.location=D:/tmp`.

## Running the application
In order to run the application, run the following command in the root folder: `./mvnw spring-boot:run`.
This will start a server running on port 8080. Then open a browser and go to `localhost:8080`.
Buttons will enable you to upload files, run a validation or delete the existent validations or remove the uploaded files.
Validation results will not be persisted after the server is stopped.

## Technologies used
This is a Spring Boot application, using Spring Web MVC and Spring Batch.
I chose these frameworks because of their popularity, stability, documentation and the structure they impose. Using them leads to more standardized applications.
While Spring Bath might be too much for single file validation, it enforces a standard approach in handling files. What is more, the Reader-Processor-Writer pattern fits the requirements of the project perfectly.
No explicit datasource is provided. An in-memory hsqldb database is used.

## Extensibility
The application is extensible and can be optimized depending on further needs. I decided against implementing these changes for now in order to avoid adding complexity which is not needed for now.
* The handling of CSV and XML files can be done in separate threads.
* If the order of transactions within a file is not important, all the processing within a step can be done in parallel as well.
* For a non-blocking approach, the job can be run using an asynchronous executor. For updating the UI, either an AJAX polling mechanism or Websockets can be used.
* The in-memory hsqldb can be replaced with another database of choice
* Multiple file upload can be enabled
* Validation results can be written to a file