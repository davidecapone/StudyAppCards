package project.study.app.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.study.app.R;
import project.study.app.model.domain.Question;

/**
 * Adapter for the questions
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    // List of questions
    private List<Question> questions;

    /**
     * Sets the questions
     * @param questions the list of questions
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setQuestions(List<Question> questions) {

        this.questions = questions;
        notifyDataSetChanged();
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at
     *               the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {

        Question question = questions.get(position);
        holder.bind(question);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return questions != null ? questions.size() : 0;
    }

    /**
     * ViewHolder for the questions
     */
    static class QuestionViewHolder extends RecyclerView.ViewHolder {

        // TextView for the question
        private final TextView textViewQuestion;

        /**
         * Constructor
         *
         * @param itemView The view
         */
        public QuestionViewHolder(@NonNull View itemView) {

            super(itemView);
            textViewQuestion = itemView.findViewById(R.id.textViewQuestion);
        }

        /**
         * Binds the question to the view
         *
         * @param question The question
         */
        public void bind(final Question question) {
            textViewQuestion.setText(question.getText());
        }
    }
}