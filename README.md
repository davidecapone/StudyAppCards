# SDMProject

## Overview
This repository contains a mobile application project (written in Java) designed to enhance students' study experiences by providing tools to manage and review study materials effectively. 
The app helps students prepare for exams and quizzes by allowing them to create, organize, and practice questions in various formats.

One of the core features of this app is the concept of "Question Sets" (also referred to as "cards"). Question Sets allow students to group related questions together, making it easier to focus on specific subjects or topics. Each Question Set can contain multiple questions, which can be either multiple-choice or open-ended. This structured approach helps students systematically cover all the necessary material and track their progress over time.

## Features
- **Question Management**: Create, modify, and delete questions.
- **Question Sets**: Organize questions into sets for different subjects or topics.
- **Practice Mode**: Test your knowledge by answering questions from different sets.
- **Pomodoro Mode**: Balance study sessions with breaks using the Pomodoro technique.
---
## Project Architecture
This project follows the Model-View-Presenter (MVP) architecture pattern, which helps to separate concerns and enhance testability. The MVP pattern divides the application into three main components: Model, View, and Presenter.

### Model
The Model component is responsible for handling the data of the application. It includes the business logic, data retrieval, and storage mechanisms. In our project, the Model is divided into several packages:
- **converters**: Handles data conversion for database storage.
- **dao**: Data Access Object interfaces for database operations.
- **database**: Database configuration and initialization.
- **domain**: Core domain models like `Question`, `Answer`, and `QuestionSet`.
- **entity**: Entities representing database tables.

### View
The View component is responsible for displaying data and handling user interactions. It includes the Activity and View classes that form the user interface. Each View interacts with a corresponding Presenter to update the UI and process user inputs.

### Presenter
The Presenter acts as a middleman between the Model and the View. It retrieves data from the Model, applies business logic, and updates the View accordingly. The Presenter also handles user input, processes it, and updates the Model.

---
## Installation
1. Clone the repository:
```bash
    git clone https://github.com/davidecapone/SDMProject.git
```
3. Open the project in Android Studio.
4. Build and run the application on an emulator or physical device.


## Contributing
We welcome contributions from the community! Please fork the repository and submit pull requests for any features, bug fixes, or enhancements.
