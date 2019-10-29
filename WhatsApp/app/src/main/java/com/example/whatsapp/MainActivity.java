package com.example.whatsapp;

import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
private Toolbar mToolBar;
private ViewPager myViewPager;
private TabLayout myTabLayout;
private TabsAccessAdapter myTabsAccessAdapter;
private FirebaseUser currentUser;
private FirebaseAuth mAuth;
private DatabaseReference RootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        RootRef= FirebaseDatabase.getInstance().getReference();
        currentUser=mAuth.getCurrentUser();
        mToolBar=(Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("WhatsApp");
        myViewPager=(ViewPager)findViewById(R.id.main_tab_pager);
        myTabsAccessAdapter =  new TabsAccessAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessAdapter );
      myTabLayout=(TabLayout)findViewById(R.id.main_tabs);
      myTabLayout.setupWithViewPager(myViewPager);


    }



    @Override
    protected void onStart()
    {
        super.onStart();
    if(currentUser==null)
    {
        SendUserToLoginActivity();
    }
    else
    {
        verifyUserExistence();
    }

    }

    private void verifyUserExistence() {
    String currentUid=mAuth.getCurrentUser().getUid();
    RootRef.child("Users").child(currentUid).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if((dataSnapshot.child("name").exists()))
            {
                Toast.makeText(MainActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
            }
            else
            {
                SendUserToSettingActivity();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }

    private void SendUserToLoginActivity() {
    Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

         super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.options_menu,menu);
    return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.main_logout_option)
        {
            mAuth.signOut();
            SendUserToLoginActivity();
        }
        if(item.getItemId()==R.id.main_settings_option)
        {
            SendUserToSettingActivity();

        }
        if(item.getItemId()==R.id.main_find_friends_option)
        {

        }
        return true;
    }
    private void SendUserToSettingActivity() {
        Intent settingIntent = new Intent(MainActivity.this,SettingsActivity.class);
        settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settingIntent);
        finish();

    }
}
