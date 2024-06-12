package project.study.app.model.dao;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import project.study.app.model.domain.FreeTextQuestion;
import project.study.app.model.domain.MultipleChoiceQuestion;
import project.study.app.model.domain.Question;
import java.lang.reflect.Type;

/**
 * This class provides custom serialization and deserialization logic for Question objects to and from JSON format.
 * It implements the JsonSerializer and JsonDeserializer interfaces from the Gson library.
 */
public class QuestionTypeAdapter implements JsonSerializer<Question<?>>, JsonDeserializer<Question<?>> {

    /**
     * Serializes a Question object into a JSON element.
     * @param src the object that needs to be converted to Json.
     * @param typeOfSrc (Type) The type of the source object.
     * @param context (JsonSerializationContext): Context for serialization.
     * @return JsonElement: The JSON representation of the Question object.
     */
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

    /**
     * Deserializes a JSON element into a Question object.
     * @param json The Json data being deserialized
     * @param typeOfT (Type): The type of the Object to deserialize to
     * @param context (JsonDeserializationContext): Context for deserialization.
     * @return Question<?>: The deserialized Question object.
     * @throws JsonParseException: Thrown if the JSON element cannot be deserialized into a Question object.
     */
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
