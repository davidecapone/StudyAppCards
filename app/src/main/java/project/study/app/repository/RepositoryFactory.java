package project.study.app.repository;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import project.study.app.model.dao.QuestionSetDao;
import project.study.app.model.database.StudyAppDatabase;

public class RepositoryFactory {

    public static QuestionSetRepository create(Context context) {

        // Get an instance of the StudyAppDatabase using the provided context
        StudyAppDatabase db = StudyAppDatabase.getDatabase(context);

        // Obtain the QuestionSetDao from the database instance
        QuestionSetDao questionSetDao = db.questionSetDao();

        // Create a cached thread pool ExecutorService to handle asynchronous tasks
        ExecutorService executorService = Executors.newCachedThreadPool();

        return new QuestionSetRepositoryImplementation(questionSetDao, executorService);
    }
}
