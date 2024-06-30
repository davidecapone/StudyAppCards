package project.study.app.model.converters;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import project.study.app.model.domain.Answer;
import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.MultipleChoiceTextAnswer;
import project.study.app.model.domain.Question;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides custom serialization and deserialization logic for Question objects to and from JSON format.
 * It implements the JsonSerializer and JsonDeserializer interfaces from the Gson library.
 */
public class QuestionTypeAdapter implements JsonSerializer<Question>, JsonDeserializer<Question> {

    /**
     * Serializes a Question object into a JSON element.
     *
     * @param src the object that needs to be converted to Json.
     * @param typeOfSrc (Type) The type of the source object.
     * @param context (JsonSerializationContext): Context for serialization.
     * @return JsonElement: The JSON representation of the Question object.
     */
    @Override
    public JsonElement serialize(Question src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("questionText", src.getText());

        Answer<?> answer = src.getAnswer();

        if (answer instanceof FreeTextAnswer) {
            jsonObject.addProperty("answerType", "FreeTextAnswer");
            jsonObject.addProperty("correctAnswer", ((FreeTextAnswer) answer).getCorrectAnswer());
        }
        else if (answer instanceof MultipleChoiceTextAnswer) {
            jsonObject.addProperty("answerType", "MultipleChoiceAnswer");
            JsonArray answersArray = new JsonArray();

            int i = 0;
            for (String answerText : ((MultipleChoiceTextAnswer) answer).getPossibleAnswers()) {
                JsonObject answerObject = new JsonObject();
                answerObject.addProperty("answer_"+i, answerText);
                answersArray.add(answerObject);
                i++;
            }

            jsonObject.add("possibleAnswers", answersArray);
            jsonObject.addProperty("correctAnswer", ((MultipleChoiceTextAnswer) answer).getCorrectAnswer());
        }

        return jsonObject;
    }

    /**
     * Deserializes a JSON element into a Question object.
     *
     * @param json The Json data being deserialized
     * @param typeOfT (Type): The type of the Object to deserialize to
     * @param context (JsonDeserializationContext): Context for deserialization.
     * @return Question<?>: The deserialized Question object.
     * @throws JsonParseException: Thrown if the JSON element cannot be deserialized into a Question object.
     */
    @Override
    public Question deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();

        String questionText = jsonObject.get("questionText").getAsString();
        String correctAnswer = jsonObject.get("correctAnswer").getAsString();
        String answerType = jsonObject.get("answerType").getAsString();



        if (answerType.equals("FreeTextAnswer")) {

            return new Question(questionText, new FreeTextAnswer(correctAnswer));

        } else if (answerType.equals("MultipleChoiceAnswer")){


            JsonArray answersArray = jsonObject.get("possibleAnswers").getAsJsonArray();

            List<String> possibleAnswers = new ArrayList<>();
            // Iterate through the JsonArray
            for (int i = 0; i < answersArray.size(); i++) {
                JsonElement answerElement = answersArray.get(i);
                String answerText = answerElement.getAsJsonObject().get("answer_"+i).getAsString();
                possibleAnswers.add(answerText);
            }

            return new Question(questionText, new MultipleChoiceTextAnswer(possibleAnswers, correctAnswer));

        }

        throw new JsonParseException("Unknown answer type: " + answerType);
    }
}
