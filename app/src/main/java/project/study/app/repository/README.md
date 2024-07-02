# `repository` Package

## Description
The `repository` package is responsible for managing data access and storage within the application. It acts as an intermediary between the data sources (such as databases or web services) and the rest of the application, providing a clean and consistent API for data operations. This package encapsulates the logic for data retrieval, insertion, deletion, and any other data-related operations, ensuring that the rest of the application remains decoupled from the specifics of data handling.

## Contents

- **RepositoryFactory.java**:
    - **Description**: This class provides a factory method to create instances of the `Repository`. It abstracts the creation logic and handles the dependencies required for the repository, such as database instances and executors for asynchronous operations.
    - **Usage**: Used to obtain a configured instance of `Repository` for use in services and other parts of the application.

- **RepositoryImplementation.java**:
    - **Description**: This class implements the `Repository` interface, defining the actual data operations. It interacts with the `QuestionSetDao` to perform database operations and uses an `ExecutorService` to handle these operations asynchronously.
    - **Usage**: Provides concrete implementations for data operations defined in the `Repository` interface, ensuring data is managed and accessed efficiently.

- **Repository.java**:
    - **Description**: An interface that defines the contract for data operations. It includes methods for inserting, deleting, and fetching `QuestionSet` entities, outlining the essential operations that any repository implementation must provide.
    - **Usage**: Acts as a contract for implementing classes, ensuring they provide the necessary data operations for `QuestionSet` entities.

## Authors
Davide Capone, Sandro Junior Della Rovere