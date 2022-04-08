package com.example.riyasflashcardapp;

import static android.view.View.VISIBLE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.Snackbar;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView flashcardQuestion = findViewById(R.id.flashcard_question_textview);
        TextView flashcardAnswer = findViewById(R.id.flashcard_answer_textview);
        TextView choice1 = findViewById(R.id.flashcard_choice1_textview);
        TextView choice2 = findViewById(R.id.flashcard_choice2_textview);
        TextView choice3 = findViewById(R.id.flashcard_choice3_textview);

//        //  ANIMATION ANSWER FLIP
//        findViewById(R.id.flashcard_question_textview).setCameraDistance(500);
//        findViewById(R.id.flashcard_answer_textview).setCameraDistance(500);
//        flashcardQuestion.animate()
//                .rotationY(90)
//                .setDuration(200)
//                .withEndAction(
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                flashcardQuestion.setVisibility(View.INVISIBLE);
//                                findViewById(R.id.flashcard_answer_textview).setVisibility(View.VISIBLE);
//                                // second quarter turn
//                                findViewById(R.id.flashcard_answer_textview).setRotationY(-90);
//                                findViewById(R.id.flashcard_answer_textview).animate()
//                                        .rotationY(0)
//                                        .setDuration(200)
//                                        .start();
//                            }
//                        }
//                ).start();

        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // STEP 4

                // get the center for the clipping circle
                int cx = flashcardAnswer.getWidth() / 2;
                int cy = flashcardAnswer.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(flashcardAnswer, cx, cy, 0f, finalRadius);

                // hide the question and show the answer to prepare for playing the animation!
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(VISIBLE);

                anim.setDuration(3000);
                anim.start();
            }
        });

        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardAnswer.setVisibility(View.INVISIBLE);
                flashcardQuestion.setVisibility(VISIBLE);
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
                eyeInvisible.setVisibility(VISIBLE);
                choice1.setVisibility(VISIBLE);
                choice2.setVisibility(VISIBLE);
                choice3.setVisibility(VISIBLE);
            }
        });

        eyeInvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eyeVisible.setVisibility(VISIBLE);
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
                // STEP 3
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
                // view
                flashcardAnswer.setVisibility(View.INVISIBLE);
                flashcardQuestion.setVisibility(View.VISIBLE);

                // COUNTDOWN TIMER
                startTimer();

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

                ((TextView) findViewById(R.id.flashcard_question_textview)).setText(flashcard.getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer_textview)).setText(flashcard.getAnswer());

                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);

                findViewById(R.id.flashcard_question_textview).startAnimation(leftOutAnim);
                findViewById(R.id.flashcard_question_textview).startAnimation(rightInAnim);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

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

        countDownTimer = new CountDownTimer(16000, 1000) {
            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.timer)).setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
            }
        };


        // CONFETTI
//        new ParticleSystem(MainActivity.this, 100, R.drawable.confetti, 3000)
//                .setSpeedRange(0.2f, 0.5f)
//                .oneShot(findViewById(R.id.flashcard_answer_textview), 100);
    }

    // return random number
    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

    // COUNTDOWN TIMER
    private void startTimer() {
        countDownTimer.cancel();
        countDownTimer.start();
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