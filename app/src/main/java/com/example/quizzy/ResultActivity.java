package com.example.quizzy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class ResultActivity extends AppCompatActivity {

   CircularProgressBar circularProgressBar;
   TextView res,exit;
   ImageView imageView;
    int corr,wrong;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        QuestionActivity questionActivity=new QuestionActivity();
        Intent intent=getIntent();
        corr=intent.getIntExtra("correct",0);
        wrong=intent.getIntExtra("wrong",0);

        circularProgressBar=findViewById(R.id.circularProgressBar);
        res=findViewById(R.id.result);

        circularProgressBar.setProgressMax(corr+wrong);

        circularProgressBar.setProgress(corr);
        res.setText(corr+"/"+(corr+wrong));

        imageView=findViewById(R.id.imageView4);
        exit=findViewById(R.id.exit);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this,QuizActivity.class));
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this,QuizActivity.class));
                finish();
            }
        });

    }


}