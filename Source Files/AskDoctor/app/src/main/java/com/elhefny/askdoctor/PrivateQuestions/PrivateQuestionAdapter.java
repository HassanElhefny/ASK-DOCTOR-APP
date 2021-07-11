package com.elhefny.askdoctor.PrivateQuestions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.elhefny.askdoctor.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PrivateQuestionAdapter extends FirestoreRecyclerAdapter<PrivateQuestion, PrivateQuestionAdapter.questionViewHolder> {

    Context context;
    ArrayList<PrivateQuestion> privateQuestions = new ArrayList<>();
    getClickedQuestion clickedQuestion;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PrivateQuestionAdapter(Context context, @NonNull @NotNull FirestoreRecyclerOptions<PrivateQuestion> options, getClickedQuestion clickedQuestion) {
        super(options);
        this.context = context;
        this.clickedQuestion = clickedQuestion;
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull questionViewHolder holder, int position, @NonNull @NotNull PrivateQuestion model) {
        holder.date.setText(model.getQuestionTime());
        holder.questionBody.setText(model.getQuestionBody());
        holder.questionAnswer.setText(model.getQuestionAnswer());
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();
        TextDrawable ic1 = builder.build(model.getQuestionSender().trim().toUpperCase().substring(0, 1), color1);
        holder.imageView.setImageDrawable(ic1);
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickedQuestion.getQuestion(getSnapshots().getSnapshot(position), position);
                }
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public questionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new questionViewHolder(LayoutInflater.from(context).inflate(R.layout.private_question_design, parent, false));
    }


    public class questionViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView date, questionBody, questionAnswer;

        public questionViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recycler_Private_question_iv_sender_first_character_name);
            date = itemView.findViewById(R.id.recycler_private_question_tv_question_date);
            questionBody = itemView.findViewById(R.id.recycler_private_question_tv_question_body);
            questionAnswer = itemView.findViewById(R.id.recycler_private_question_tv_question_answer);
        }
    }

    public interface getClickedQuestion {
        void getQuestion(DocumentSnapshot dataSnapshot, int position);
    }
}
