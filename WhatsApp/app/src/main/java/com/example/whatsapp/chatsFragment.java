package com.example.whatsapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class chatsFragment extends Fragment {

private View PrivateChatView;
private RecyclerView chatList;
private DatabaseReference chatRef,userRef;
private FirebaseAuth mAuth;
private String currentUserId;
    public chatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        PrivateChatView=inflater.inflate(R.layout.fragment_chats, container, false);
            chatList=(RecyclerView)PrivateChatView.findViewById(R.id.chats_list);
            chatList.setLayoutManager(new LinearLayoutManager(getContext()));
            mAuth=FirebaseAuth.getInstance();
            currentUserId=mAuth.getCurrentUser().getUid();
        chatRef= FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUserId);
        userRef=FirebaseDatabase.getInstance().getReference().child("Users");
            return PrivateChatView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<contact>options=new FirebaseRecyclerOptions.Builder<contact>().setQuery(chatRef,contact.class)
                .build();
        FirebaseRecyclerAdapter<contact,ChatsViewHolder> adapter =new FirebaseRecyclerAdapter<contact, ChatsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatsViewHolder holder, int position, @NonNull contact model) {
            final String user_ids=getRef(position).getKey();
        userRef.child(user_ids).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  if(dataSnapshot.exists())
                  {
                      final String userIdName =dataSnapshot.child("name").getValue().toString();
                      final String userIdStatus =dataSnapshot.child("status").getValue().toString();
                      holder.u_name.setText(userIdName);
                      holder.u_status.setText("Last seen \n");
                      holder.itemView.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              Intent chatIntent =new Intent(getContext(),ChatActivity.class);
                              chatIntent.putExtra("visit_user_id",user_ids);
                              chatIntent.putExtra("visit_user_name",userIdName);
                              startActivity(chatIntent);
                          }
                      });
                  }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            }

            @NonNull
            @Override
            public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_display_layout,viewGroup,false);
                return new ChatsViewHolder(view);
            }
        };
        chatList.setAdapter(adapter);
        adapter.startListening();
    }
    public static class ChatsViewHolder extends RecyclerView.ViewHolder
    {
        TextView u_name,u_status;
        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            u_name =itemView.findViewById(R.id.user_name);
            u_status = itemView.findViewById(R.id.user_status);
        }

    }
}
