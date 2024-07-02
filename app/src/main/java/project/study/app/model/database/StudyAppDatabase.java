package project.study.app.model.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import project.study.app.model.converters.QuestionListConverter;
import project.study.app.model.dao.QuestionSetDao;
import project.study.app.model.entity.QuestionSetEntity;

/**
 * Room database class for the StudyApp application.
 * This class defines the database and provides a method to get an instance of the database.
 */
@Database(entities = {QuestionSetEntity.class}, version = 1, exportSchema = false)
@TypeConverters({QuestionListConverter.class})
public abstract class StudyAppDatabase extends RoomDatabase {
    private static volatile StudyAppDatabase INSTANCE;
    private static final String STUDY_DATABASE = "study_database";

    /**
     * Here we define all the data access objects (DAO) for the database.
     */
    public abstract QuestionSetDao questionSetDao();
    /**
     * Method to get an instance of the database.
     *
     * @param context the application context
     * @return the database instance
     */
    public static StudyAppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StudyAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    StudyAppDatabase.class, STUDY_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}