package com.example.layoutmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {
Button con,liner,table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ConstaraintLayout.class);
                startActivity(i);
            }
        });
        liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(MainActivity.this,LinearExample.class);
                startActivity(i2);
            }
        });
    }

    private void initialize() {
    con=(Button)findViewById(R.id.constrain);
    liner=(Button)findViewById(R.id.liner);
    }
}
