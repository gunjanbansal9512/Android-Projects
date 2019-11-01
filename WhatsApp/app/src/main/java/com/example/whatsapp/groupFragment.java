package com.example.whatsapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class groupFragment extends Fragment {

private  View groupFragmentView;
private ListView listView;
private ArrayAdapter <String> adapter;
private ArrayList<String> list_of_group=new ArrayList<>();
private DatabaseReference groupRef;

public groupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       groupFragmentView= inflater.inflate(R.layout.fragment_group, container, false);
       groupRef= FirebaseDatabase.getInstance().getReference().child("Groups");
       Intializefields();
       RetrieveAndDisplayGroups();
      return groupFragmentView;
    }


    private void Intializefields() {
    listView =(ListView) groupFragmentView.findViewById(R.id.list_view);
    adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,list_of_group);
    listView.setAdapter(adapter);


    }
    private void RetrieveAndDisplayGroups() {
groupRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Set<String> set = new HashSet<>();
        Iterator iterator = dataSnapshot.getChildren().iterator();
        while (iterator.hasNext())
        {
        set.add(((DataSnapshot)iterator.next()).getKey());
        }
        list_of_group.clear();
        list_of_group.addAll(set);
        adapter.notifyDataSetChanged(); 
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
})   ;
}


}
