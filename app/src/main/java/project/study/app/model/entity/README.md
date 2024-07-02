# Package: entity

## Description
The `entity` package contains the data entities used in the application. These entities represent the data structure that will be persisted in the database. Each entity is typically annotated with Room annotations to define the database schema.

## Classes

### QuestionSetEntity
**Description:** Represents a question set entity in the database. This entity holds information about a set of questions.
**Attributes:**
- `id` (int): The unique identifier for the question set.
- `name` (String): The name of the question set.
- `questions` (String): A JSON string representing the list of questions in the set.

**Key Methods:**
- Getters and setters for each attribute.
- Overrides for `equals()` and `hashCode()` based on the `id` and `name` attributes.

**Annotations:**
- `@Entity(tableName = "question_set")`: Marks this class as a Room entity with the table name `question_set`.
- `@PrimaryKey(autoGenerate = true)`: Marks the `id` field as the primary key that is auto-generated.
- `@ColumnInfo(name = "name")`: Specifies the column name for the `name` field.
- `@ColumnInfo(name = "questions")`: Specifies the column name for the `questions` field.
- `@TypeConverters({QuestionListConverter.class})`: Indicates the use of a type converter to handle the `questions` field.

## Dependencies
- Room library for database annotations and persistence.
- `QuestionListConverter` for converting the list of questions to and from JSON.

## Author
Davide Capone, Sandro Junior Della Rovere