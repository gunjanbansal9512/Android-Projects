package com.example.whatsapp;

import android.content.Intent;
import android.content.IntentSender;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
private Toolbar mToolBar;
private ViewPager myViewPager;
private TabLayout myTabLayout;
private TabsAccessAdapter myTabsAccessAdapter;
private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    }

    private void SendUserToLoginActivity() {
    Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
    startActivity(loginIntent);
    }
}
