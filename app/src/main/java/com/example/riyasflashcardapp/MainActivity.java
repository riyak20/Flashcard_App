package com.example.riyasflashcardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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


        ImageView eyeVisible = findViewById(R.id.eye_visible);
        ImageView eyeInvisible = findViewById(R.id.eye_invisible);

        eyeVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eyeVisible.setVisibility(View.INVISIBLE);
                eyeInvisible.setVisibility(View.VISIBLE);
                choice1.setVisibility(View.VISIBLE);
                choice2.setVisibility(View.VISIBLE);
                choice3.setVisibility(View.VISIBLE);
            }
        });

        eyeInvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eyeVisible.setVisibility(View.VISIBLE);
                eyeInvisible.setVisibility(View.INVISIBLE);
                choice1.setVisibility(View.INVISIBLE);
                choice2.setVisibility(View.INVISIBLE);
                choice3.setVisibility(View.INVISIBLE);
            }
        });

        // go to Add Activity
        findViewById(R.id.flashcard_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 100 && resultCode == RESULT_OK) {
            String question = data.getExtras().getString("questionString");
            String answer = data.getExtras().getString("answerString");

            ((TextView) findViewById(R.id.flashcard_question_textview)).setText(question);
            ((TextView) findViewById(R.id.flashcard_answer_textview)).setText(answer);
        }
//        Snackbar.make(findViewById(R.id.flashcard_question_textview),
//                "The message to display",
//                Snackbar.LENGTH_SHORT)
//                .show();
    }
}