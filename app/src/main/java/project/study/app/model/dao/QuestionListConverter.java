package project.study.app.model.dao;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import project.study.app.model.domain.Question;

public class QuestionListConverter {

    private static final Gson gson = new Gson();
    

    @TypeConverter
    public static String fromList(List<Question<?>> list) {
        return gson.toJson(list);
    }
}

