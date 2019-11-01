package com.example.whatsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class GroupChatActivity extends AppCompatActivity {
private Toolbar mtoolbar;
ImageButton sendMessage;
EditText userMessageInput;
private ScrollView mscrollView;
private TextView playTextMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
    InitializeFields();
    }
    private void InitializeFields()
    {
        mtoolbar=(Toolbar) findViewById(R.id.group_chat_bar_layout);
        setSupportActionBar(mtoolbar);
        getActionBar().setTitle("Group Name");
        sendMessage = (ImageButton)findViewById(R.id.send_message_button);
        userMessageInput=(EditText)findViewById(R.id.input_group_message);
        playTextMessages=(TextView)findViewById(R.id.group_chat_display);
        mscrollView = (ScrollView)findViewById(R.id.my_scroll_view);

    }
}
