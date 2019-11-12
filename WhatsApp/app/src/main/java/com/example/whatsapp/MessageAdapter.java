package com.example.whatsapp;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>

{

    private  List<Messages> userMessageList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    public MessageAdapter(List<Messages> userMessageList)
    {
        this.userMessageList=userMessageList;
    }
    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView senderMessageText,reciverMessageText;
        public CircleImageView receiverProfileImage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessageText=(TextView)itemView.findViewById(R.id.sender_message_text);
            reciverMessageText=(TextView)itemView.findViewById(R.id.receiver_message_text);
        }
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).
               inflate(R.layout.custom_messages_layout,viewGroup,false);
        mAuth=FirebaseAuth.getInstance();

        return new MessageViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
            String messageSenderId=mAuth.getCurrentUser().getUid();
        Messages messages=userMessageList.get(i);
        String fromUserId = messages.getFrom();
        String fromMessageType=messages.getType();
        usersRef= FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserId);
            usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            if(fromMessageType.equals("text"))
            {
                messageViewHolder.reciverMessageText.setVisibility(View.INVISIBLE);
                //messageViewHolder.receiverProfileImage.setVisibility(View.INVISIBLE);

               if(fromUserId.equals(messageSenderId))
               {
                   messageViewHolder.senderMessageText.setBackgroundResource(R.drawable.sender_message_layout);
                messageViewHolder.senderMessageText.setTextColor(Color.BLACK);
                   messageViewHolder.senderMessageText.setText(messages.getMessage());
                //   messageViewHolder.reciverMessageText.setVisibility(View.INVISIBLE);
               }
               else
               {
                   messageViewHolder.reciverMessageText.setVisibility(View.VISIBLE);
                   messageViewHolder.senderMessageText.setVisibility(View.INVISIBLE);
                   //messageViewHolder.receiverProfileImage.setVisibility(View.VISIBLE);
                   messageViewHolder.reciverMessageText.setBackgroundResource(R.drawable.receiver_message_layout);
                   messageViewHolder.reciverMessageText.setText(messages.getMessage());
                   messageViewHolder.reciverMessageText.setTextColor(Color.BLACK);

               }
            }
            }



    @Override
    public int getItemCount() {
        return userMessageList.size();
    }



}
