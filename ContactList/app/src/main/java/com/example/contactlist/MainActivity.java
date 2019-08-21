package com.example.contactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
//import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String [] listitem ;
    TextView t1 ,t2 ;
    ListView l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=(TextView) findViewById(R.id.textView);
        l1 = (ListView) findViewById(R.id.contact);
        Resources res  = getResources();
        listitem = res.getStringArray(R.array.lists);

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.layout,R.id.textView2,listitem);
        l1.setAdapter(adapter);
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s= (String) adapter.getItem(position);
                t1.setText(s);

            }
        });
    }
}