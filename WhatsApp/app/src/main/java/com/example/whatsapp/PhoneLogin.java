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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLogin extends AppCompatActivity {
    private Button sendVerificationCode , verifyButton;
    private EditText inputPhoneNumber,inputVerificationCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
   private  String mVerificationId;
   private PhoneAuthProvider.ForceResendingToken mResendToken;
   private FirebaseAuth mAuth;
   private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        sendVerificationCode=(Button)findViewById(R.id.send_verification_code_button);
        verifyButton=(Button)findViewById(R.id.verify_button);
        inputPhoneNumber=(EditText)findViewById(R.id.phone_number_input);
        inputVerificationCode=(EditText)findViewById(R.id.verification_code_input);
        mAuth=FirebaseAuth.getInstance();
        loading = new ProgressDialog(PhoneLogin.this);
        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(PhoneLogin.this, "Please check your country code or Phone Number...", Toast.LENGTH_SHORT).show();
                sendVerificationCode.setVisibility(View.VISIBLE);
                inputPhoneNumber.setVisibility(View.VISIBLE);
                verifyButton.setVisibility(View.INVISIBLE);
                inputVerificationCode.setVisibility(View.INVISIBLE);

            }
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {


                loading.dismiss();
                mVerificationId = verificationId;
                mResendToken = token;
                Toast.makeText(PhoneLogin.this, "Code has been sent!!", Toast.LENGTH_SHORT).show();
                sendVerificationCode.setVisibility(View.INVISIBLE);
                inputPhoneNumber.setVisibility(View.INVISIBLE);
                verifyButton.setVisibility(View.VISIBLE);
                inputVerificationCode.setVisibility(View.VISIBLE);

            }
        };
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode.setVisibility(View.INVISIBLE);
                inputPhoneNumber.setVisibility(View.VISIBLE);
                String verificationcode=inputVerificationCode.getText().toString();
                if(TextUtils.isEmpty(verificationcode))
                {
                    Toast.makeText(PhoneLogin.this, "Please enter code!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    loading.setTitle("Verification");
                    loading.setMessage("Please wait while we are verifying...");
                    loading.show();
                    loading.setCanceledOnTouchOutside(false);


                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,verificationcode);
                signInWithPhoneAuthCredential(credential);
                }
            }
        });
        sendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phno=inputPhoneNumber.getText().toString();
                if(TextUtils.isEmpty(phno))
                    Toast.makeText(PhoneLogin.this, "Please Enter phone number...", Toast.LENGTH_SHORT).show();
                else
                {

                    loading.setTitle("Phone Verification");
                    loading.setMessage("Please wait ...");
                    loading.show();
                    loading.setCanceledOnTouchOutside(false);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phno,60,TimeUnit.SECONDS,PhoneLogin.this,callbacks);
                }
            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        loading.dismiss();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(PhoneLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PhoneLogin.this, "Congratulation You have signed in...!!", Toast.LENGTH_SHORT).show();
                            sendUserTOMainActivity();
                        } else {
                            Toast.makeText(PhoneLogin.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendUserTOMainActivity() {
        Intent mainIntent = new Intent(PhoneLogin.this,MainActivity.class);
        startActivity(mainIntent);
    }

}
