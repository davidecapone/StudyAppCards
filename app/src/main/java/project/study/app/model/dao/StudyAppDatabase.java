package project.study.app.model.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import project.study.app.model.domain.QuestionSet;

@Database(entities = {QuestionSet.class}, version = 1, exportSchema = false)
public abstract class StudyAppDatabase extends RoomDatabase {

    private static volatile StudyAppDatabase INSTANCE;

    /**
     * Here we define all the data access objects (DAO) for the database.
     */
    public abstract QuestionSetDao questionSetDao();


    public static StudyAppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StudyAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    StudyAppDatabase.class, "study_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}