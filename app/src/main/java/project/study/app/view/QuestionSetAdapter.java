package project.study.app.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.study.app.R;
import project.study.app.model.domain.QuestionSet;

public class QuestionSetAdapter extends RecyclerView.Adapter<QuestionSetAdapter.QuestionSetViewHolder> {

    private List<QuestionSet> questionSets;
    private final QuestionSetClickListener listener;

    public interface QuestionSetClickListener {
        void onQuestionSetClicked(QuestionSet questionSet);
        void onDeleteButtonClicked(QuestionSet questionSet);
        void onStartExaminationButtonClicked(QuestionSet questionSet);
    }

    public QuestionSetAdapter(QuestionSetClickListener listener) {
        this.listener = listener;
    }

    public void setQuestionSets(List<QuestionSet> questionSets) {
        this.questionSets = questionSets;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_set, parent, false);
        return new QuestionSetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionSetViewHolder holder, int position) {
        QuestionSet questionSet = questionSets.get(position);
        holder.bind(questionSet, listener);
    }

    @Override
    public int getItemCount() {
        return questionSets != null ? questionSets.size() : 0;
    }

    static class QuestionSetViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewQuestionSetName;
        private final Button buttonDelete;
        private final Button buttonStartExamination;

        public QuestionSetViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestionSetName = itemView.findViewById(R.id.textViewQuestionSetName);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonStartExamination = itemView.findViewById(R.id.buttonStartExamination);
        }

        public void bind(final QuestionSet questionSet, final QuestionSetClickListener listener) {
            textViewQuestionSetName.setText(questionSet.getQuestionSetName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onQuestionSetClicked(questionSet);
                }
            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteButtonClicked(questionSet);
                }
            });
            buttonStartExamination.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onStartExaminationButtonClicked(questionSet);
                }
            });
        }
    }
}