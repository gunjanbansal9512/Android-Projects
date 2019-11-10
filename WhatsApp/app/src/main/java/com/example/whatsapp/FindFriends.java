package com.example.whatsapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FindFriends extends AppCompatActivity {
private Toolbar mToolbar;
private RecyclerView findFriendRecyclerView;
private DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        findFriendRecyclerView=(RecyclerView) findViewById(R.id.find_friends_list);
        findFriendRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mToolbar=(Toolbar)findViewById(R.id.find_friends_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Find Friends");


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<contact> options = new FirebaseRecyclerOptions.Builder<contact>().setQuery(userRef,contact.class).build();
        FirebaseRecyclerAdapter<contact,FindFriendViewHolder> adapter=new FirebaseRecyclerAdapter<contact, FindFriendViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FindFriendViewHolder holder, final int position, @NonNull contact model) {
                    holder.userName.setText(model.getName());
                    holder.userStatus.setText(model.getStatus());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String visitUserID=getRef(position).getKey();
                            Intent ProfileIntent = new Intent(FindFriends.this,ProfileActivity.class);
                            ProfileIntent.putExtra("visitUserId",visitUserID);
                            startActivity(ProfileIntent);
                        }
                    });

            }

            @NonNull
            @Override
            public FindFriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View  view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_display_layout,viewGroup,false);
                FindFriendViewHolder viewHolder = new FindFriendViewHolder(view);
                return viewHolder;
            }
        };
        findFriendRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public static class FindFriendViewHolder extends  RecyclerView.ViewHolder
    {
        TextView userName ,userStatus;

        public FindFriendViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.user_name);
            userStatus=itemView.findViewById(R.id.user_status);
        }
    }

}
