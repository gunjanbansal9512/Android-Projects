 package com.example.whatsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

 public class LoginActivity extends AppCompatActivity {
     private FirebaseUser currentUser;
     private Button LoginButton,PhoneLoginButton;
     private EditText UserEmail,UserPassword;
     private TextView NeedNewAccount,ForgetPassword;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeFields();
    }
     private void initializeFields() {
         LoginButton=(Button)findViewById(R.id.login_button);
         PhoneLoginButton=(Button)findViewById(R.id.phone_login_button);
         ForgetPassword = (TextView)findViewById(R.id.forgot_password_link);
         UserEmail = (EditText)findViewById(R.id.login_email);
         UserPassword=(EditText)findViewById(R.id.login_password);
         NeedNewAccount=(TextView)findViewById(R.id.need_new_account);
        NeedNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegisterIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(RegisterIntent);

            }
        });
     }
    @Override
protected void onStart()
{
    super.onStart();
if(currentUser != null)
{
    sendUserMainActivity();
}
}

     private void sendUserMainActivity() {
         Intent loginIntent = new Intent(LoginActivity.this,MainActivity.class);
         startActivity(loginIntent);
    }
 }
