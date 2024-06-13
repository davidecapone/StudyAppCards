package project.study.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import project.study.app.model.dao.QuestionListConverter;
import project.study.app.model.domain.FreeTextAnswer;
import project.study.app.model.domain.MultipleChoiceTextAnswer;
import project.study.app.model.domain.Question;

public class JSONSerializationTest {

    private List<Question> questions;

    @Before
    public void set_up() {
        this.questions = new ArrayList<>();
    }

    @Test
    public void from_question_list_to_json_converter() {

        assertTrue(true);
    }

    @Test
    public void from_json_string_to_question_list() {
        assertTrue(false);
    }
}
