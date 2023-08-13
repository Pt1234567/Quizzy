package com.example.quizzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class QuestionActivity extends AppCompatActivity {
    TextView quizT,quest,countDown;
Dialog dialog;
  public   int correct_count=0;
   public  int wrong_count=0;
    String strType;
    int index=0;

    Button opt1,opt2,opt3,opt4,nextBtn;
    ArrayList<QuestionModel> quesList;
    QuestionModel questionModel;
    DatabaseReference databaseReference;
    CountDownTimer countDownTimer;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getSupportActionBar().hide();

     countDown=findViewById(R.id.timer);

         dialog=new Dialog(this);
        dialog.setContentView(R.layout.timeover);

         nextBtn=findViewById(R.id.Next);
        quizT=findViewById(R.id.QuizType);
        quest=findViewById(R.id.question);
        opt1=findViewById(R.id.option1);
        opt2=findViewById(R.id.option2);
        opt3=findViewById(R.id.option3);
        opt4=findViewById(R.id.option4);

        //Intent get data to get type of card selected
        Intent intent=getIntent();
        strType=intent.getStringExtra("Type");
        String s=strType+" based Quiz";
        quizT.setText(s);
       quesList=new ArrayList<>();





        databaseReference= FirebaseDatabase.getInstance().getReference(strType);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    QuestionModel questionModel1=dataSnapshot.getValue(QuestionModel.class);
                    quesList.add(questionModel1);
                }

                Collections.shuffle(quesList);
                Log.d("QuestionActivity","Size of questList is "+ String.valueOf(quesList.size()));
              SetData();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("QuestionActivity", "Database Error: " + error.getMessage());
            }
        });



        //progress bar
//        countDownTimer=new CountDownTimer(10000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                timer=timer-1;
//            progressBar.setProgress(timer);
//            }
//
//            @Override
//            public void onFinish() {
//                QuestionActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Dialog dialog=new Dialog(getApplicationContext());
//                        dialog.setContentView(R.layout.timeover);
//
//                        dialog.show();
//
//                        dialog.findViewById(R.id.tryagain).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                startActivity(new Intent(QuestionActivity.this,QuizActivity.class));
//                            }
//                        });
//                    }
//                });
//
//            }
//        }.start();
        nextBtn.setClickable(false);



    }

    public void SetData(){


        if(!quesList.isEmpty()){
           endTimer();
           starTimer();

            questionModel=quesList.get(index);

            quest.setText(questionModel.getQuestion());
            opt1.setText(questionModel.getOption1());
            opt2.setText(questionModel.getOption2());
            opt3.setText(questionModel.getOption3());
            opt4.setText(questionModel.getOption4());
//            if(countDownTimer!=null){
//
//            }
        }

        enableButton();


        opt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnOptionClick(opt1);
            }
        });
        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnOptionClick(opt2);
            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnOptionClick(opt3);
            }
        });
        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnOptionClick(opt4);
            }
        });
    }

    public void correct(){


        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                    index++;
                    questionModel=quesList.get(index);
                    SetData();
                    resetColor();


            }
        });


    }

    public void wrong(Button button){
        button.setBackgroundColor(getResources().getColor(R.color.red));
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           enableButton();
                wrong_count++;
                if(index<quesList.size()-1) {
                    index++;
                    questionModel=quesList.get(index);
                    SetData();
                    resetColor();

                }else{
                    GameResult();
                }
            }
        });


    }
    public void GameResult(){
        Log.d("QuestionActivity", "Going to ResultActivity...");
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuestionActivity.this,ResultActivity.class);
                intent.putExtra("correct",correct_count);
                intent.putExtra("wrong",wrong_count);
                startActivity(intent);
                finish();

            }
        });



    }

    public void resetColor(){
        opt1.setBackgroundColor(getResources().getColor(R.color.original));
        opt2.setBackgroundColor(getResources().getColor(R.color.original));
        opt3.setBackgroundColor(getResources().getColor(R.color.original));
        opt4.setBackgroundColor(getResources().getColor(R.color.original));
    }

    public void enableButton() {
        opt1.setClickable(true);
        opt2.setClickable(true);
        opt3.setClickable(true);
        opt4.setClickable(true);
    }

    public void disableButton() {
        opt1.setClickable(false);
        opt2.setClickable(false);
        opt3.setClickable(false);
        opt4.setClickable(false);
    }




    public void OnOptionClick(@NonNull Button button) {
        disableButton();
        nextBtn.setClickable(true);

        if (!button.getText().toString().equals("")) {
            if (button.getText().toString().equals(questionModel.getAns())) {
                button.setBackgroundColor(getResources().getColor(R.color.green));
                correct_count++;
               if(index<quesList.size()-1){
                   correct();


               }else{
                   GameResult();
               }
            } else {
                wrong(button);
            }

        }

    }
public void starTimer(){
    countDownTimer=new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

            countDown.setText(String.valueOf ((int)millisUntilFinished/1000));

        }

        @Override
        public void onFinish() {
            showTimeUpDialog();
        }
    }.start();
}

public void endTimer(){
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
}

    public void showTimeUpDialog(){

        if(!isFinishing()){

            dialog.show();

            dialog.findViewById(R.id.tryagain).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),QuizActivity.class));

                }
            });
        }


    }

}