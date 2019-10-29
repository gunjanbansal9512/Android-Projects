package com.example.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
protected Button registerButton;
protected EditText registerEmail,registerPassword;
protected TextView alreadyHaveAccount;
private FirebaseAuth mAuth;
private ProgressDialog loadingBar;
private DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        rootRef= FirebaseDatabase.getInstance().getReference();
        initializeFields();
        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CreateNewAccount();
                    }
                }
        );
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
    loadingBar=new ProgressDialog(this);
    }
private void CreateNewAccount()
{
    String email = registerEmail.getText().toString();
    String password = registerPassword.getText().toString();
    if(TextUtils.isEmpty(email))
    {
        Toast.makeText(RegisterActivity.this,"Please enter Email id.....",Toast.LENGTH_LONG).show();
    }
    else if(TextUtils.isEmpty(password))
    {
        Toast.makeText(RegisterActivity.this,"Please enter Password.....",Toast.LENGTH_LONG).show();
    }
else
    {
        loadingBar.setTitle("Creating New Account");
        loadingBar.setMessage("Please wait while we are creating new account");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();
    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful())
            {
                String currentUserId = mAuth.getCurrentUser().getUid();
                rootRef.child("Users").child(currentUserId).setValue("");
                Toast.makeText(RegisterActivity.this,"Account created successfully..",Toast.LENGTH_LONG).show();
                loadingBar.dismiss();
                sendUserToMainActivity();
            }
            else
            {
                String message = task.getException().toString();
                Toast.makeText(RegisterActivity.this,"Error "+message,Toast.LENGTH_LONG).show();
            loadingBar.dismiss();
            }
        }
    });
    }
}
private void sendUserToMainActivity()
{
    Intent i = new Intent(RegisterActivity.this,MainActivity.class);
   i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(i);
    finish();
}
}
