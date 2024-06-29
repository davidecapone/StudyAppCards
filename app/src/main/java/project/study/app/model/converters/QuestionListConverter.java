package project.study.app.model.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import project.study.app.model.domain.Question;

import java.util.List;

/**
 * This class provides methods to convert a list of Question objects to a JSON string and vice versa,
 * using the Gson library. It integrates with the Room persistence library through the use of @TypeConverter annotations.
 */
public class QuestionListConverter {

    // Gson object to handle JSON serialization and deserialization
    private static final Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(Question.class, new QuestionTypeAdapter())
            .create();

    /**
     * Converts a list of Question objects to a JSON string.
     *
     * @param questions: The list of Question objects to be converted to JSON. This list can contain objects of any class that implements Question.
     * @return String: A JSON representation of the list of Question objects.
     */
    @TypeConverter
    public static String fromList(List<Question> questions) {
        return gson.toJson(questions);
    }

    /**
     * Converts a JSON string to a list of Question objects.
     *
     * @param json: The JSON string to be converted to a list of Question objects.
     * @return List<Question<?>>: A list of Question objects represented by the JSON string.
     */
    @TypeConverter
    public static List<Question> toList(String json) {
        return gson.fromJson(json, new com.google.gson.reflect.TypeToken<List<Question>>() {}.getType());
    }
}
