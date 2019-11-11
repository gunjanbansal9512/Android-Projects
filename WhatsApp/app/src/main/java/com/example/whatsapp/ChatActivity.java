package com.example.whatsapp;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
private String messageReceiverId,messageReceiverName,messageSenderId;
private TextView userName,userLastSeen;
private CircleImageView userImage;
private Toolbar chatToolBar;
private ImageButton sendMessageBtn;
private EditText messageInputText;
private FirebaseAuth mAuth;
private DatabaseReference rootRef;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatToolBar=(Toolbar)findViewById(R.id.chat_toolbar);
        setSupportActionBar(chatToolBar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater=(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView=layoutInflater.inflate(R.layout.custom_chat_bar,null);
        actionBar.setCustomView(actionBarView);
        userName=(TextView)findViewById(R.id.custom_profile_name);
        userLastSeen=(TextView)findViewById(R.id.custom_user_last_seen);
        userImage=(CircleImageView)findViewById(R.id.custom_profile_image);
        sendMessageBtn=(ImageButton)findViewById(R.id.send_message_button);
        mAuth=FirebaseAuth.getInstance();
        rootRef= FirebaseDatabase.getInstance().getReference();
        messageSenderId=mAuth.getCurrentUser().getUid();
        messageInputText=(EditText)findViewById(R.id.input_message);
        messageReceiverId=getIntent().getExtras().get("visit_user_id").toString();
        messageReceiverName=getIntent().getExtras().get("visit_user_name").toString();
        userName.setText(messageReceiverName);
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            SendMessage();
            }
        });

    }
    private void SendMessage()
    {
        final String messageText = messageInputText.getText().toString();
        if(TextUtils.isEmpty(messageText))
        {

        }
        else
        {
            String messageSenderRef="Messages/"+messageSenderId+"/"+messageReceiverId;
            String messageReciverRef="Messages/"+messageReceiverId+"/"+messageSenderId;
            DatabaseReference userMessageDatabaseRef=rootRef.child("Messages").child(messageSenderId).child(messageReceiverId).push();
            String messagePushId = userMessageDatabaseRef.getKey();
            Map messageTextBody = new HashMap();
            messageTextBody.put("message",messageText);
            messageTextBody.put("type","text");
            messageTextBody.put("from",messageSenderId);
            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef+"/"+messagePushId,messageTextBody);
            messageBodyDetails.put(messageReciverRef+"/"+messagePushId,messageTextBody);
        rootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(!task.isSuccessful())
                {
                    Toast.makeText(ChatActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                }
                messageInputText.setText(null);
            }
        });
        }
    }
}
