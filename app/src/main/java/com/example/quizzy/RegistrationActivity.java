package com.example.quizzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText Uname,pass,email;
    Button regiButton;

    String pattern="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
        Uname=findViewById(R.id.RUname);
        email=findViewById(R.id.Rmail);
        pass=findViewById(R.id.RPass);
        regiButton=findViewById(R.id.Rbutton);

        regiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=Uname.getText().toString();
                String mail=email.getText().toString();
                String password=pass.getText().toString();

                if(name.isEmpty() || mail.isEmpty()||password.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Please fill the details", Toast.LENGTH_SHORT).show();
                }else if(!mail.matches(pattern)){
                    Toast.makeText(RegistrationActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegistrationActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}