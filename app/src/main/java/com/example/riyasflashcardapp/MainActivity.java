package com.example.riyasflashcardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

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

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question_textview)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer_textview)).setText(allFlashcards.get(0).getAnswer());
        }

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // don't try to go to next card if you have no cards to begin with
                if (allFlashcards.size() == 0)
                    return;

                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;
                int randIndex = getRandomNumber(0, allFlashcards.size());
                // regenerate num if same as index
                while (randIndex == currentCardDisplayedIndex) {
                    randIndex = getRandomNumber(1, allFlashcards.size());
                }

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if(currentCardDisplayedIndex >= allFlashcards.size()) {
//                    Snackbar.make(flashcardQuestion,
//                            "Return back to start, end of cards reached.", Snackbar.LENGTH_SHORT).show();
                    currentCardDisplayedIndex = 0;
                }

                // set the question and answer TextViews with data from the database
                allFlashcards = flashcardDatabase.getAllCards();
                Flashcard flashcard = allFlashcards.get(currentCardDisplayedIndex);

                ((TextView) findViewById(R.id.flashcard_question_textview)).setText(flashcard.getAnswer());
                ((TextView) findViewById(R.id.flashcard_answer_textview)).setText(flashcard.getQuestion());
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question_textview)).getText().toString());
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_answer_textview)).getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayedIndex++;
                if(allFlashcards.size() == 0) {
                    ((TextView) findViewById(R.id.flashcard_question_textview)).setText("Add a new card!");
                }
            }
        });

    }

    // return random number
    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 100 && resultCode == RESULT_OK) {
            String question = data.getExtras().getString("questionString");
            String answer = data.getExtras().getString("answerString");

            ((TextView) findViewById(R.id.flashcard_question_textview)).setText(question);
            ((TextView) findViewById(R.id.flashcard_answer_textview)).setText(answer);

            flashcardDatabase.insertCard(new Flashcard(question, answer));
            allFlashcards = flashcardDatabase.getAllCards();

        }

    }
}