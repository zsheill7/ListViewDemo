package com.example.zoe.listviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView emailField;
    TextView passwordField;

    String regex = "^(.+)@(.+)$";
    Pattern pattern;
    Matcher matcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        pattern = Pattern.compile(regex);
        Log.i("onCreate", "in");

        mAuth = FirebaseAuth.getInstance();
        emailField = (TextView) findViewById(R.id.emailField);
        passwordField = (TextView) findViewById(R.id.passwordField);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }

    public void signUp(View view) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        Log.i("Signup", "in");
        matcher = pattern.matcher(email);
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Please enter a password 6 characters or more", Toast.LENGTH_LONG).show();
        } else if (!matcher.matches()) {
            Toast.makeText(getApplicationContext(), "Error: Invalid Email", Toast.LENGTH_LONG).show();
        } else {
            createAccount(email, password);
        }
    }

    public void logIn(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Sign in success
                            Log.d("sign in", "createUserWithEmail: success");
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            showUserList();
                        } else {
                            //signup fails
                            Log.w("sign in", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void showUserList() {
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }
}
