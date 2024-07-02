# Model Package

## Overview

The `model` package contains all the classes and interfaces that represent the application's data and its interactions with the database. This package is organized into several sub-packages to maintain a clean structure and separate concerns.

## Sub-Packages

### Converters Package

**Description:**  
The `converters` package contains classes responsible for converting data between different types. 

### DAO Package

**Description:**  
The `dao` (Data Access Object) package contains interfaces that provide methods for interacting with the database. These interfaces abstract the database operations and allow for easy testing and maintenance.

### Database Package

**Description:**  
The `database` package contains the database configuration and initialization code. It includes the main database class that sets up the database and provides access to DAO interfaces.

### Domain Package

**Description:**  
The `domain` package contains the core business logic and domain models of the application. These classes represent the main entities and their behaviors within the application's domain.

### Entity Package

**Description:**  
The `entity` package contains the data entities that correspond to the tables in the database. These classes are annotated with Room annotations to define the database schema and relationships.

## Statistics.java

**Description:**  
The `Statistics` class is responsible for tracking and calculating statistics related to the application's usage. This includes metrics such as the number of correct and incorrect answers, as well as the proportion of correct answers.

**Author:**  
Davide Capone, Sandro Junior Della Rovere