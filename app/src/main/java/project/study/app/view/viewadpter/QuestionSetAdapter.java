package project.study.app.view.viewadpter;

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

/**
 * Adapter for the question sets
 */
public class QuestionSetAdapter extends RecyclerView.Adapter<QuestionSetAdapter.QuestionSetViewHolder> {

    // List of question sets
    private List<QuestionSet> questionSets;

    // Listener for the question set clicks
    private final QuestionSetClickListener listener;

    /**
     * Interface for the question set clicks
     */
    public interface QuestionSetClickListener {

        /**
         * Called when the delete button is clicked
         *
         * @param questionSet the question set
         */
        void onDeleteButtonClicked(QuestionSet questionSet);

        /**
         * Called when the start examination button is clicked
         *
         * @param questionSet the question set
         */
        void onStartExaminationButtonClicked(QuestionSet questionSet);

        /**
         * Called when the modify button is clicked
         *
         * @param questionSet the question set
         */
        void onModifyButtonClicked(QuestionSet questionSet); // New method for Modify button
    }

    /**
     * Constructor
     *
     * @param listener the listener
     */
    public QuestionSetAdapter(QuestionSetClickListener listener) {
        this.listener = listener;
    }

    /**
     * Sets the question sets
     *
     * @param questionSets the list of question sets
     */
    public void setQuestionSets(List<QuestionSet> questionSets) {
        this.questionSets = questionSets;
        notifyDataSetChanged();
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public QuestionSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question_set, parent, false);
        return new QuestionSetViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at
     *                 the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull QuestionSetViewHolder holder, int position) {
        QuestionSet questionSet = questionSets.get(position);
        holder.bind(questionSet, listener);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return questionSets != null ? questionSets.size() : 0;
    }

    /**
     * ViewHolder for the question sets
     */
    static class QuestionSetViewHolder extends RecyclerView.ViewHolder {

        // TextView for the question set name
        private final TextView textViewQuestionSetName;

        // Buttons for delete, start examination, and modify
        private final Button buttonDelete;
        private final Button buttonStartExamination;
        private final Button buttonModify;

        /**
         * Constructor
         *
         * @param itemView
         */
        public QuestionSetViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuestionSetName = itemView.findViewById(R.id.textViewQuestionSetName);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonStartExamination = itemView.findViewById(R.id.buttonStartExamination);
            buttonModify = itemView.findViewById(R.id.buttonModify); // Initialize Modify button
        }

        /**
         * Binds the question set to the view
         *
         * @param questionSet the question set
         * @param listener the listener
         */
        public void bind(final QuestionSet questionSet, final QuestionSetClickListener listener) {

            // Set the question set name
            textViewQuestionSetName.setText(questionSet.getQuestionSetName());

            // Set onClick listeners for the buttons
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

            buttonModify.setOnClickListener(new View.OnClickListener() {
                // Set onClick listener for Modify button
                @Override
                public void onClick(View v) {
                    listener.onModifyButtonClicked(questionSet);
                }
            });
        }
    }
}