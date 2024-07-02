# Package: presenter

## Description
The `presenter` package contains classes responsible for the presentation logic in the application. These classes act as intermediaries between the view and the model, handling user interactions, updating the view, and retrieving/manipulating data from the model.

## Classes/Interfaces

### ExaminationSessionPresenter
**Description:** Manages the examination session logic, including loading questions, validating answers, and tracking statistics.  
**Responsibilities:**
- Start and manage the examination session.
- Validate user answers.
- Track and display statistics.
  **Key Methods:**
- `startExamination(String questionSetName)` - Initiates the examination session by loading the questions from the specified question set.
- `validateAnswer(String answer)` - Validates the provided answer, updates the statistics, and provides feedback to the view.
- `advanceToNextQuestion()` - Advances to the next question in the session. If no more questions, it displays the statistics and navigates back to manual mode.

### ManualModePresenter
**Description:** Manages the manual mode, allowing users to create, delete, and manage question sets.  
**Responsibilities:**
- Load all question sets.
- Add new question sets.
- Delete existing question sets.
- Navigate to question set details.
- Start an examination session.
  **Key Methods:**
- `loadAllQuestionSets()` - Loads all question sets from the service and updates the view.
- `addNewQuestionSet(String name)` - Adds a new question set with the given name.
- `deleteQuestionSet(QuestionSet questionSet)` - Deletes the specified question set.
- `onQuestionSetSelected(QuestionSet questionSet)` - Navigates to the details of the selected question set.
- `onStartExaminationSessionButtonClicked(QuestionSet questionSet)` - Starts an examination session with the selected question set.

### PomodoroModePresenter
**Description:** Manages the pomodoro mode, including study, break, and question insertion sessions.  
**Responsibilities:**
- Start and manage the pomodoro mode.
- Handle transitions between different pomodoro sessions.
- Update the view with the remaining time and current session.
  **Key Methods:**
- `startPomodoroMode()` - Starts the pomodoro mode by initiating the study session.
- `startSession(Session session)` - Starts a specific session within the pomodoro mode.
- `moveToNextSession()` - Moves to the next session in the pomodoro mode cycle.
- `onTick(long millisUntilFinished)` - Updates the view with the remaining time.
- `onFinish()` - Moves to the next session when the timer finishes.

### QuestionSetDetailsPresenter
**Description:** Manages the details of a specific question set, allowing users to load questions, add new questions, and modify the question set.  
**Responsibilities:**
- Load questions for a specific question set.
- Add a new question to the current question set.
- Save changes to the question set.
  **Key Methods:**
- `loadQuestions(String questionSetName)` - Loads the questions for the specified question set and updates the view.
- `addQuestion(Question question)` - Adds a new question to the current question set and updates the view.
- `saveQuestionSet(String newQuestionSetName)` - Saves changes to the current question set.

## Dependencies
- `model` package for domain classes like `Question`, `QuestionSet`, and `Statistics`.
- `service` package for interacting with the data layer.
- `view` package for updating the UI.

## Author
Davide Capone, Sandro Junior Della Rovere