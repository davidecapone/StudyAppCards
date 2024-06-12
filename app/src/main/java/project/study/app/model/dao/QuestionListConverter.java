package project.study.app.model.dao;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import project.study.app.model.domain.Question;

import java.util.List;

public class QuestionListConverter {

    private static final Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(Question.class, new QuestionTypeAdapter())
            .create();

    @TypeConverter
    public static String fromList(List<Question<?>> questions) {
        return gson.toJson(questions);
    }

    @TypeConverter
    public static List<Question<?>> toList(String json) {
        return gson.fromJson(json, new com.google.gson.reflect.TypeToken<List<Question<?>>>() {}.getType());
    }
}
