package com.example.musclepal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{
    private TextView banner, registerUser;
    private EditText editFullName, editEmail, editUsername, editPass;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();//connect to firebase for registration

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.Register);
        registerUser.setOnClickListener(this);
        editFullName = (EditText) findViewById(R.id.fullname);
        editUsername = (EditText) findViewById(R.id.username);
        editEmail = (EditText) findViewById(R.id.email);
        editPass = (EditText) findViewById(R.id.Password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.Register:
                registerUser();
        }

    }

    private void registerUser() {
        String fullName = editFullName.getText().toString().trim();
        String userName = editUsername.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String pass = editPass.getText().toString().trim();
        //validation below

        if (fullName.isEmpty()) {
            editFullName.setError("FullName Required!");
            editFullName.requestFocus();
            return;
        }

        if (userName.isEmpty()) {
            editUsername.setError("Username Required!");
            editUsername.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editEmail.setError("Email Address Required!");
            editEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid email!");
            editEmail.requestFocus();
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
    //create account with email and pass
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(fullName, email, userName);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        showMainActivity();
                                    }else {
                                        Toast.makeText(RegisterUser.this, "Failed to register, Try Again!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }
                            });

                        } else {
                            Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    });
    }

    private void showMainActivity(){//toast on register success, go to login page
        Toast.makeText(RegisterUser.this, "Success!", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        startActivity(new Intent(this, MainActivity.class));
        progressBar.setVisibility(View.GONE);
    }

}