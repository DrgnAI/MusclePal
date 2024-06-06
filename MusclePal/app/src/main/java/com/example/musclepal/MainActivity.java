package com.example.musclepal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editUsername, editPass;
    private ProgressBar progressBar;
    private Button signIn;
    private TextView register;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.login);
        signIn.setOnClickListener(this);

        editUsername = (EditText) findViewById(R.id.username);
        editPass = (EditText) findViewById(R.id.Password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();//firebase authentication



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.login:
                loginUser();
                break;
        }
    }
    private void loginUser(){
        String userName = editUsername.getText().toString().trim();
        String pass = editPass.getText().toString().trim();

        if (userName.isEmpty()) {
            editUsername.setError("Username Required!");
            editUsername.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            editPass.setError("Password Required!");
            editPass.requestFocus();
            return;
        }
        if (pass.length()<6){
            pass+="eee";
        }

        mAuth.signInWithEmailAndPassword(userName+"@gmail.com", pass)//needed this way as firebase requires email, but spec wants eee
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(MainActivity.this, AppHome.class);
                            i.putExtra("key",editUsername.getText().toString().trim());
                            startActivity(i);
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }


}

