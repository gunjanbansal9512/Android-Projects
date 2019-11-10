package com.example.whatsapp;


import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class contactFragment extends Fragment {

private View ContactView;
private RecyclerView myContactList;
private DatabaseReference contactRef;
private FirebaseAuth mAuth;
private String currentUID;
public contactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ContactView= inflater.inflate(R.layout.fragment_contact, container, false);
        myContactList=(RecyclerView)ContactView.findViewById(R.id.contact_recycler_list);
        myContactList.setLayoutManager(new LinearLayoutManager(getContext()));
        currentUID=mAuth.getCurrentUser().getUid();
        contactRef= FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUID);

        return ContactView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<contact>().setQuery(contactRef,contact.class).build();
    FirebaseRecyclerAdapter<contact,ContactViewHolder> adapter = new FirebaseRecyclerAdapter<contact, ContactViewHolder>() {
        @Override
        protected void onBindViewHolder(@NonNull ContactViewHolder holder, int position, @NonNull contact model) {
            String usersIds= getRef();

        }

        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_display_layout,viewGroup,false);
            ContactViewHolder viewHolder= new ContactViewHolder(view);
            return viewHolder;

        }
    };
    }
    public static class ContactViewHolder extends RecyclerView.ViewHolder
    {
        TextView uname,ustatus;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            uname=itemView.findViewById(R.id.user_name);
            ustatus=itemView.findViewById(R.id.user_status);
        }
    }
}
