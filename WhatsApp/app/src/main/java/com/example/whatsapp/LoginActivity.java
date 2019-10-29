 package com.example.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

 public class LoginActivity extends AppCompatActivity {
     private FirebaseAuth mAuth;
     private Button LoginButton,PhoneLoginButton;
     private EditText UserEmail,UserPassword;
     private TextView NeedNewAccount,ForgetPassword;
    private ProgressDialog progressDialog;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        initializeFields();
         NeedNewAccount.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent RegisterIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                 startActivity(RegisterIntent);

             }
         });
         LoginButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 AllowUserToLogin();
             }
         });
    }

     private void AllowUserToLogin() {
     String email = UserEmail.getText().toString();
     String password= UserPassword.getText().toString();
         if(TextUtils.isEmpty(email))
         {
             Toast.makeText(LoginActivity.this,"Please enter Email id.....",Toast.LENGTH_LONG).show();
         }
          if(TextUtils.isEmpty(password))
         {
             Toast.makeText(LoginActivity.this,"Please enter Password.....",Toast.LENGTH_LONG).show();
         }
         else
         {
             progressDialog.setTitle("Login In");
             progressDialog.setMessage("Please wait while we are login into account");
             progressDialog.setCanceledOnTouchOutside(true);
             progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {

                        sendUserMainActivity();
                    Toast.makeText(LoginActivity.this,"Successfully Login...",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(LoginActivity.this,"Error "+message,Toast.LENGTH_LONG).show();
                    }
                }
            });
         }
     }

     private void initializeFields() {
         LoginButton=(Button)findViewById(R.id.login_button);
         PhoneLoginButton=(Button)findViewById(R.id.phone_login_button);
         ForgetPassword = (TextView)findViewById(R.id.forgot_password_link);
         UserEmail = (EditText)findViewById(R.id.login_email);
         UserPassword=(EditText)findViewById(R.id.login_password);
         NeedNewAccount=(TextView)findViewById(R.id.need_new_account);
         progressDialog = new ProgressDialog(this);


     }


     private void sendUserMainActivity() {
         Intent i = new Intent(LoginActivity.this,MainActivity.class);
         i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
         startActivity(i);
         finish();

     }
 }
