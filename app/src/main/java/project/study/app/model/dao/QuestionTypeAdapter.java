package project.study.app.model.dao;

import com.google.gson.*;

import org.junit.Test;

import project.study.app.model.domain.FreeTextQuestion;
import project.study.app.model.domain.MultipleChoiceQuestion;
import project.study.app.model.domain.Question;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionTypeAdapter implements JsonSerializer<Question<?>>, JsonDeserializer<Question<?>> {

    @Override
    public JsonElement serialize(Question<?> src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject jsonObject = new JsonObject();

        if (src instanceof FreeTextQuestion) {

            jsonObject.addProperty("type", "FreeText");
            jsonObject.addProperty("questionText", src.getQuestionText());
            jsonObject.addProperty("correctAnswer", ((FreeTextQuestion) src).getCorrectAnswer());

        } else if (src instanceof MultipleChoiceQuestion) {
            jsonObject.addProperty("type", "MultipleChoice");
            jsonObject.addProperty("questionText", src.getQuestionText());

            JsonArray answersArray = new JsonArray();
            for (MultipleChoiceQuestion.Answer answer : ((MultipleChoiceQuestion) src).getAnswers()) {

                JsonObject answerObject = new JsonObject();
                answerObject.addProperty("answerText", answer.getAnswerText());
                answerObject.addProperty("correctness", answer.getCorrectness());
                answersArray.add(answerObject);
            }
            jsonObject.add("answers", answersArray);

        }
        return jsonObject;
    }

    @Override
    public Question<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        // extract the type of the Question (MultipleChoice / FreeText):
        String type = jsonObject.get("type").getAsString();
        // extract the questionText:
        String questionText = jsonObject.get("questionText").getAsString();

        if ("FreeText".equals(type)) {
            // extract the correctAnswer:
            String correctAnswer = jsonObject.get("correctAnswer").getAsString();
            // create and return new FreeText object:
            return new FreeTextQuestion(questionText, correctAnswer);

        } else if ("MultipleChoice".equals(type)) {
            // the answer will be represented as another json object:
            JsonArray answersArray = jsonObject.getAsJsonArray("answers");
            MultipleChoiceQuestion.Answer[] answers = new MultipleChoiceQuestion.Answer[answersArray.size()];

            for (int i = 0; i < answersArray.size(); i++) {

                JsonObject answerObject = answersArray.get(i).getAsJsonObject();
                String answerText = answerObject.get("answerText").getAsString();
                boolean correct = answerObject.get("correctness").getAsBoolean();
                answers[i] = new MultipleChoiceQuestion.Answer(answerText, correct);
            }

            return new MultipleChoiceQuestion(questionText, answers);
        }

        throw new JsonParseException("Unknown question type: " + type);
    }
}
