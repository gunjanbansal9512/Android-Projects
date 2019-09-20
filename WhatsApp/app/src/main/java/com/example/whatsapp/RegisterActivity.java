package com.example.whatsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
protected Button registerButton;
protected EditText registerEmail,registerPassword;
protected TextView alreadyHaveAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeFields();
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alreadyIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(alreadyIntent);
            }
        });
    }

    private void initializeFields() {
    registerButton=(Button)findViewById(R.id.register_button);
    registerEmail=(EditText)findViewById(R.id.register_email);
    registerPassword=(EditText)findViewById(R.id.register_password);
    alreadyHaveAccount=(TextView)findViewById(R.id.already_have_account);
    }

}
