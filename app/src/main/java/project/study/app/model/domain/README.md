# Domain Package

## Overview

The domain package contains the core business logic and domain models of the application. These models represent the key entities and their behaviors in the system. This package is crucial for defining how the data is structured and manipulated within the application.

## Classes

### Answer.java

**Description:**  
Defines the abstract class `Answer` that serves as a base for different types of answers in the application.

**Key Responsibilities:**
- Provides a contract for the `getCorrectAnswer` method.
- Serves as a parent class for specific answer types like `FreeTextAnswer` and `MultipleChoiceTextAnswer`.

**Authors:**  
Davide Capone, Sandro Junior Della Rovere

---

### AnswerFactory.java

**Description:**  
Implements the factory design pattern to create instances of different `Answer` types based on provided criteria.

**Key Responsibilities:**
- Provides a method to instantiate `FreeTextAnswer` or `MultipleChoiceTextAnswer` based on input parameters.
- Encapsulates the object creation logic for `Answer` objects.

**Authors:**  
Davide Capone, Sandro Junior Della Rovere

---

### FreeTextAnswer.java

**Description:**  
Represents an answer where the correct answer is a free text string.

**Key Responsibilities:**
- Stores the correct answer as a string.
- Implements the `getCorrectAnswer` method to return the stored string.

**Authors:**  
Davide Capone, Sandro Junior Della Rovere

---

### MultipleChoiceTextAnswer.java

**Description:**  
Represents an answer where the correct answer is one of several multiple-choice options.

**Key Responsibilities:**
- Stores multiple choice options and the correct answer.
- Implements the `getCorrectAnswer` method to return the correct choice.

**Authors:**  
Davide Capone, Sandro Junior Della Rovere

---

### Question.java

**Description:**  
Represents a question in the application, containing the question text and an associated answer.

**Key Responsibilities:**
- Stores the question text and an `Answer` object.
- Provides methods to get the question text and the associated answer.

**Authors:**  
Davide Capone, Sandro Junior Della Rovere

---

### QuestionSet.java

**Description:**  
Represents a set of questions, typically grouped together for a quiz or examination.

**Key Responsibilities:**
- Stores a list of `Question` objects.
- Provides methods to add questions to the set and retrieve the list of questions.

**Authors:**  
Davide Capone, Sandro Junior Della Rovere