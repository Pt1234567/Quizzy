package com.example.quizzy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    CardView sci,maths,eng,his;
    TextView sciT,mathT,engT,hisT;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().hide();
  getSupportActionBar().hide();
        sci=findViewById(R.id.scienceC);
        maths=findViewById(R.id.mathsC);
        eng=findViewById(R.id.engC);
        his=findViewById(R.id.historyC);

        sciT=findViewById(R.id.science);
        mathT=findViewById(R.id.maths);
        engT=findViewById(R.id.english);
        hisT=findViewById(R.id.history);

        OnClickCard(sci,sciT);
        OnClickCard(maths,mathT);
        OnClickCard(eng,engT);
        OnClickCard(his,hisT);

    }
    public void OnClickCard(CardView cardView,TextView text){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=new Toast(getApplicationContext());
                View view=getLayoutInflater().inflate(R.layout.toast,(ViewGroup)findViewById(R.id.Tcontainer) );
                toast.setView(view);
                toast.setGravity(Gravity.CENTER,0,0);
                TextView textView=view.findViewById(R.id.textMsg);

                textView.setText("Preparing Quizzes for You");
                toast.setDuration(Toast.LENGTH_LONG);

                toast.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String str=text.getText().toString();
                        Intent intent=new Intent(QuizActivity.this,QuestionActivity.class);

                        intent.putExtra("Type",str);
                        startActivity(intent);
                        finish();
                    }
                },3500);
            }
        });
    }
}