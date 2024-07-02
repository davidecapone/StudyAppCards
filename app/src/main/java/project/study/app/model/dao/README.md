# DAO Package

## Overview

The DAO (Data Access Object) package contains interfaces and classes that provide an abstract interface to the database. The purpose of the DAO is to encapsulate the logic for accessing data and ensure that the rest of the application can interact with the data layer without worrying about the underlying implementation details.

## Files

### QuestionSetDao.java

**Description:**  
The `QuestionSetDao` interface provides methods for performing CRUD (Create, Read, Update, Delete) operations on `QuestionSetEntity` objects in the database. It serves as an intermediary between the database and the application, allowing for data manipulation and retrieval.

**Key Responsibilities:**
- Insert a `QuestionSetEntity` into the database.
- Retrieve a `QuestionSetEntity` by its name.
- Retrieve all `QuestionSetEntity` objects.
- Delete a `QuestionSetEntity` from the database.
- Update a `QuestionSetEntity` in the database.

**Methods:**
- `void insert(QuestionSetEntity questionSetEntity)`: Inserts a new `QuestionSetEntity` into the database.
- `LiveData<QuestionSetEntity> getQuestionSetByName(String name)`: Retrieves a `QuestionSetEntity` by its name.
- `LiveData<List<QuestionSetEntity>> getAllQuestionSets()`: Retrieves all `QuestionSetEntity` objects.
- `void delete(QuestionSetEntity questionSetEntity)`: Deletes a `QuestionSetEntity` from the database.
- `void update(QuestionSetEntity questionSetEntity)`: Updates a `QuestionSetEntity` in the database.

**Authors:**  
Davide Capone, Sandro Junior Della Rovere