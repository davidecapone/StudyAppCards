package project.study.app.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import project.study.app.R;

public class QuestionSetDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_set_details);

        String questionSetName = getIntent().getStringExtra("questionSetName");
        // Fetch the question set by name and display its details
    }
}
