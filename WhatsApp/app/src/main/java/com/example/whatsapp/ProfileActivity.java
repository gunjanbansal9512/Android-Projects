package com.example.whatsapp;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private String receiveUserId,currentUserId;
    private FirebaseAuth mAuth;
    private TextView userName,userStatus;
    private String currentState;
    Button sendMessage,decline;
    private DatabaseReference userRef,chatRequestRef,contactsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        currentState="new";
        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();
        decline=(Button)findViewById(R.id.decline_message_request_button);
        receiveUserId=getIntent().getExtras().get("visitUserId").toString();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        chatRequestRef = FirebaseDatabase.getInstance().getReference().child("Chats Request");
        contactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
        userName=(TextView)findViewById(R.id.visit_user_name);
        userStatus=(TextView)findViewById(R.id.visit_user_status);
        sendMessage=(Button)findViewById(R.id.send_message_request_button);

        userRef.child(receiveUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String uname = dataSnapshot.child("name").getValue().toString();
                    String ustatus = dataSnapshot.child("status").getValue().toString();
                    userName.setText(uname);
                    userStatus.setText(ustatus);
                }
                manageChatRequest();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void manageChatRequest() {
        chatRequestRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(receiveUserId))
                {
                    String request_type = dataSnapshot.child(receiveUserId).child("request_type").getValue().toString();
                    if(request_type.equals("sent"))
                    {
                        currentState="request_sent";
                        sendMessage.setText("Cancel");
                    }
                    else if(request_type.equals("received"))
                    {
                        currentState="received";
                        sendMessage.setText("Accept Chat Request");
                        sendMessage.setEnabled(true);
                        decline.setVisibility(View.VISIBLE);
                        decline.setEnabled(true);
                        decline.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                cancelChatRequest();

                            }
                        });

                    }

                }
                else
                {
                    contactsRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(receiveUserId))
                            {
                                currentState="friends";
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(! currentUserId.equals(receiveUserId))
        {
            sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendMessage.setEnabled(false);
                    if(currentState.equals("new"))
                    {
                        sendChatRequest();
                    }
                    if(currentState.equals("request_sent"))
                    {
                        cancelChatRequest();
                    }
                    if(currentState.equals("received"))
                    {
                        acceptChatRequest();
                    }
                }
            });
        }
        else
        {
                sendMessage.setVisibility(View.INVISIBLE);

        }
    }

    private void cancelChatRequest() {
        chatRequestRef.child(currentUserId).child(receiveUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    chatRequestRef.child(receiveUserId).child(currentUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                sendMessage.setText("Send Message");
                                sendMessage.setEnabled(true);
                                currentState="new";
                                Toast.makeText(ProfileActivity.this, "Request has been cancelled !!!", Toast.LENGTH_SHORT).show();
                                decline.setVisibility(View.INVISIBLE);
                                decline.setEnabled(false);
                            }
                        }
                    });
                }
            }
        });
    }

    private void sendChatRequest() {
        chatRequestRef.child(currentUserId).child(receiveUserId).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ProfileActivity.this, "Request Send Successfully...", Toast.LENGTH_SHORT).show();
                    chatRequestRef.child(receiveUserId).child(currentUserId).child("request_type").setValue("received").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                sendMessage.setEnabled(true);
                                currentState="request_sent";
                                sendMessage.setText("Cancel");
                            }
                        }
                    });
                }
            }
        });
    }
    private void acceptChatRequest()
    {
        contactsRef.child(currentUserId).child(receiveUserId).child("Contacts").setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {   contactsRef.child(receiveUserId).child(currentUserId).child("Contacts").setValue("Saved").addOnCompleteListener(

                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(ProfileActivity.this, "Contacts has been saved..", Toast.LENGTH_SHORT).show();
                                    //sendMessage.setEnabled(true);
                                    currentState="friends";
                                    decline.setVisibility(View.INVISIBLE);
                                    sendMessage.setVisibility(View.INVISIBLE);

                                }
                            }
                        });

                }
            }
        });
    }
}
