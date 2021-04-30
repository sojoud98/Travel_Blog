package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout textUsernameLayout;
    private TextInputLayout textPasswordInput;
    private Button loginButton;
    private ProgressBar progressBar;
    BlogPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = new BlogPreferences(this);
        if (preferences.isLoggedIn()) {
            startMainActivity();
            finish();
            return;
        }
        textUsernameLayout = findViewById(R.id.textUsernameLayout);
        textPasswordInput = findViewById(R.id.textPasswordInput);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> onLoginClick());

        textUsernameLayout.getEditText().addTextChangedListener(createTextWatcher(textUsernameLayout));
        textPasswordInput.getEditText().addTextChangedListener(createTextWatcher(textPasswordInput));

        progressBar = findViewById(R.id.progressBar);
    }

    private TextWatcher createTextWatcher(TextInputLayout textInputLayout) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        return textWatcher;

    }


    void onLoginClick() {
        String username = textUsernameLayout.getEditText().getText().toString();
        String password = textPasswordInput.getEditText().getText().toString();
        if (username.isEmpty()) {
            textUsernameLayout.setError("Please enter username");
        }
        if (password.isEmpty()) {
            textPasswordInput.setError("Please enter password");
        }
        if (!username.isEmpty() && ! password.isEmpty()) {
            if (username.equals("admin") && password.equals("admin")) {
                performLogin();
            }
            else {
                setErrorDialog();
            }
        }

    }

    private void performLogin() {
        preferences.setLoggedIn(true);
        loginButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        textPasswordInput.setEnabled(false);
        textUsernameLayout.setEnabled(false);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startMainActivity();
        }, 2000);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, BlogListActivity.class);
        finish();
        startActivity(intent);
    }

    private void setErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Failed")
                .setMessage("Username/password is not correct")
                .setPositiveButton("OK",((dialog, which) -> dialog.dismiss()))
                .show();
    }
}

