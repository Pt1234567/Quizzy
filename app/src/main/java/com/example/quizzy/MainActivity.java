package com.example.quizzy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {
ImageView img;
TextView text;
FirebaseAuth auth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        img=findViewById(R.id.imageView);
        text=findViewById(R.id.text);
        Animation moveR= AnimationUtils.loadAnimation(this,R.anim.move);
        Animation move=AnimationUtils.loadAnimation(this,R.anim.movel);
        auth=FirebaseAuth.getInstance();
        text.startAnimation(move);
        img.startAnimation(moveR);
   if(auth.getCurrentUser()!=null){
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               startActivity(new Intent(MainActivity.this,QuizActivity.class));
               finish();
           }
       },4000);
   }else{
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
               finish();
           }
       },4000);
   }

    }
}