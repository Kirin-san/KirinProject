package com.example.kirinproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;


import android.app.Application;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Boolean signUpModeActive = true;
    TextView changeSignUpModeTextView;
    EditText passwordEditText;

    public void showNewsList(){
        Intent intent = new Intent(getApplicationContext(), NewsList.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        // First we check if we tap the textfield changing the button SignUp to Login
        if (view.getId() == R.id.changeSignUpModeTextView) {
            Button signUpButton = (Button) findViewById(R.id.signUpButton);

            if (signUpModeActive) {
                signUpButton.setText("Login");
                changeSignUpModeTextView.setText("SignUp");
                signUpModeActive = false;
                Toast.makeText(getApplicationContext(), "Switched to Login", Toast.LENGTH_SHORT).show();
            } else {
                signUpButton.setText("SignUp");

                changeSignUpModeTextView.setText("Login");
                signUpModeActive = true;
                Toast.makeText(getApplicationContext(), "Switched to SignUp", Toast.LENGTH_SHORT).show();

            }

        // And if we tap smwhr else the keyboard should disappear
        }else if (view.getId() == R.id.mainLayout || view.getId() ==R.id.kirinImageView){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    public void signUp (View view){
        EditText userNameEditText = (EditText) findViewById(R.id.userNameEditText);

        userNameEditText.setSelection(0);

        if (signUpModeActive) {
            if (userNameEditText.getText().toString().length() == 0 || passwordEditText.getText().toString().length() == 0) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_LONG).show();
            } else {
                ParseUser user = new ParseUser();
                user.setUsername(userNameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "SignUp successful", Toast.LENGTH_LONG).show();
                            showNewsList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
        else{
            ParseUser.logInInBackground(userNameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user!=null){
                        Toast.makeText(getApplicationContext(),"Login successsful!",Toast.LENGTH_SHORT).show();
                        showNewsList();
                    }else{
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If we tap somewhere outside the text windows we want the keyboard to disappear
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        changeSignUpModeTextView = (TextView) findViewById(R.id.changeSignUpModeTextView);
        ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        ImageView kirinImageView = (ImageView) findViewById(R.id.kirinImageView);
        mainLayout.setOnClickListener(this);
        kirinImageView.setOnClickListener(this);

        // If we are already loged in we just go to the next activity
        if (ParseUser.getCurrentUser() != null){
            showNewsList();
        }
        // If we tap the text view changing the SignUp to Login function
        changeSignUpModeTextView.setOnClickListener(this);






    }
}