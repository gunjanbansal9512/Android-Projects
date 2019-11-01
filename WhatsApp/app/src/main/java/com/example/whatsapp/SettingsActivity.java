package com.example.whatsapp;

import android.content.Intent;
import android.renderscript.Script;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
private Button updateAccountSetting;
private EditText userName,userStatus;
private String CurrentUserId;
private FirebaseAuth mAuth;
private CircleImageView profileImage;
private DatabaseReference RootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAuth = FirebaseAuth.getInstance();
        CurrentUserId=mAuth.getCurrentUser().getUid();
        RootRef= FirebaseDatabase.getInstance().getReference();
        initializeFields();
        updateAccountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 UpdateSettings();
            }
        });
         RetrieveUserInformation();
    }




    private void UpdateSettings() {
        String setUserName = userName.getText().toString();
        String setUserStatus = userStatus.getText().toString();
        if(TextUtils.isEmpty(setUserName))
        {
            Toast.makeText(getApplicationContext(),"Please Give Your Username....",Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(setUserStatus))
        {
            Toast.makeText(getApplicationContext(),"Please Give Status....",Toast.LENGTH_LONG).show();
        }
        else
        {
            HashMap<String,String> profileMap=new HashMap<>();
            profileMap.put("uid",CurrentUserId);
            profileMap.put("name",setUserName);
            profileMap.put("status",setUserStatus);
            RootRef.child("Users").child(CurrentUserId).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
        if(task.isSuccessful())
        {
            sendUserMainActivity();
            Toast.makeText(getApplicationContext(),"Profile updated Successfully",Toast.LENGTH_LONG).show();

        }
        else
        {
            String error = task.getException().toString();
            Toast.makeText(SettingsActivity.this,"Error "+error,Toast.LENGTH_LONG).show();
        }
                }
            });
        }
    }

    private void initializeFields() {
     updateAccountSetting=(Button)findViewById(R.id.update_setting_button);
     userName=(EditText)findViewById(R.id.set_user_name);
     userStatus=(EditText)findViewById(R.id.set_profile_status);
     profileImage=(CircleImageView)findViewById(R.id.set_profile_image);

    }
    private void sendUserMainActivity() {
        Intent i = new Intent(SettingsActivity.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();

    }
    private void RetrieveUserInformation() {
        RootRef.child("Users").child(CurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if((dataSnapshot.exists())&&(dataSnapshot.hasChild("name")&& dataSnapshot.hasChild("status") && dataSnapshot.hasChild("profileImage")))
            {
            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                String retrieveUserStatus = dataSnapshot.child("status").getValue().toString();
                String retrieveProfileImage=dataSnapshot.child("profileImage").getValue().toString();
                userName.setText(retrieveUserName);
                userStatus.setText(retrieveUserStatus);
            }
            else if((dataSnapshot.exists())&&(dataSnapshot.hasChild("name")))
            {
                String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                    String retrieveUserStatus = dataSnapshot.child("status").getValue().toString();
                userName.setText(retrieveUserName);
                userStatus.setText(retrieveUserStatus);

            }
            else
            {
                Toast.makeText(SettingsActivity.this,"Please update your profile information",Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
