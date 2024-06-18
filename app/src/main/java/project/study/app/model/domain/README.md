# Study App Domain Package

The domain package includes core classes and interfaces that define the primary data models and business logic for the application. These models include various types of answers, questions, and question sets.

## Contents

### Classes

#### `Answer<T>`
An abstract class that represents an answer to a question. It includes:
- A generic type `T` for the correct answer.
- Getter and setter methods for the correct answer.
- An abstract method `isCorrect(T answer)` to be implemented by subclasses.

#### `FreeTextAnswer`
A subclass of `Answer<String>` that represents a free-text answer. It includes:
- A constructor to initialize the correct answer.
- An implementation of the `isCorrect` method to check if the given answer matches the correct answer.

#### `MultipleChoiceTextAnswer`
A subclass of `Answer<String>` that represents a multiple-choice text answer. It includes:
- A list of possible answers.
- A constructor to initialize the possible answers and the correct answer.
- Getter and setter methods for the possible answers.
- An implementation of the `isCorrect` method to check if the given answer matches the correct answer.

#### `Question`
A class that represents a question. It includes:
- The text of the question.
- An answer of type `Answer<?>`.
- Getter and setter methods for the question text and answer.
- A method `validateAnswer(Object answer)` to check if the given answer is correct.

#### `QuestionSet`
A class that represents a set of questions. It includes:
- The name of the question set.
- A list of `Question` objects.
- Methods to add and remove questions from the set.
- Getter and setter methods for the question set name and list of questions.

#### `AnswerFactory`
A factory class for creating instances of `Answer`. It includes:
- A static method `createAnswer` that takes the type of answer, the correct answer, and a list of possible answers (if applicable), and returns an instance of the appropriate `Answer` subclass.