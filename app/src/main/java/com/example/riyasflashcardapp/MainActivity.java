package com.example.riyasflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView flashcardQuestion = findViewById(R.id.flashcard_question_textview);
        TextView flashcardAnswer = findViewById(R.id.flashcard_answer_textview);
        TextView choice1 = findViewById(R.id.flashcard_choice1_textview);
        TextView choice2 = findViewById(R.id.flashcard_choice2_textview);
        TextView choice3 = findViewById(R.id.flashcard_choice3_textview);
        ImageView eye = findViewById(R.id.toggle_choices_visibility);
        boolean isShowingAnswers = true;

        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);
            }
        });

        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardAnswer.setVisibility(View.INVISIBLE);
                flashcardQuestion.setVisibility(View.VISIBLE);
            }
        });

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_choice1_textview).setBackgroundColor(getResources().getColor(R.color.red, null));
                findViewById(R.id.flashcard_choice3_textview).setBackgroundColor(getResources().getColor(R.color.green, null));
                findViewById(R.id.flashcard_choice2_textview).setBackgroundColor(getResources().getColor(R.color.tan, null));
            }
        });

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_choice2_textview).setBackgroundColor(getResources().getColor(R.color.red, null));
                findViewById(R.id.flashcard_choice3_textview).setBackgroundColor(getResources().getColor(R.color.green, null));
                findViewById(R.id.flashcard_choice1_textview).setBackgroundColor(getResources().getColor(R.color.tan, null));
            }
        });

        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_choice3_textview).setBackgroundColor(getResources().getColor(R.color.green, null));
                findViewById(R.id.flashcard_choice1_textview).setBackgroundColor(getResources().getColor(R.color.tan, null));
                findViewById(R.id.flashcard_choice2_textview).setBackgroundColor(getResources().getColor(R.color.tan, null));
            }
        });

        // how to change isShowingAnswers val throughout main activity file?
        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // current logic: switch the val of isShowingAnswers to the opposite of before
                //boolean isShowingAnswers = !isShowingAnswers;
                if (!isShowingAnswers) {
                    choice1.setVisibility(View.INVISIBLE);
                    choice2.setVisibility(View.INVISIBLE);
                    choice3.setVisibility(View.INVISIBLE);
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.eye_icon);
                }

                else {
                    choice1.setVisibility(View.VISIBLE);
                    choice2.setVisibility(View.VISIBLE);
                    choice3.setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.no_eye);
                }
            }
        });

    }
}