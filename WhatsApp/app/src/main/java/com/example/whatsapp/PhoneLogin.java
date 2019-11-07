package com.example.whatsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneLogin extends AppCompatActivity {
    private Button sendVerificationCode , verifyButton;
    private EditText inputPhoneNumber,inputVerificationCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        sendVerificationCode=(Button)findViewById(R.id.send_verification_code_button);
        verifyButton=(Button)findViewById(R.id.verify_button);
        inputPhoneNumber=(EditText)findViewById(R.id.phone_number_input);
        inputVerificationCode=(EditText)findViewById(R.id.verification_code_input);
        sendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode.setVisibility(View.INVISIBLE);
                inputPhoneNumber.setVisibility(View.INVISIBLE);
                verifyButton.setVisibility(View.VISIBLE);
                inputVerificationCode.setVisibility(View.VISIBLE);
            }
        });
    }
}
