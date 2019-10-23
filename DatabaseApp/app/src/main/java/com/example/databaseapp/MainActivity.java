package com.example.databaseapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button insert,select,update;
EditText id,name,marks;
DHelper dHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    insert=(Button)findViewById(R.id.insert);
    select=(Button)findViewById(R.id.display);
    update=(Button)findViewById(R.id.delete);
    id=(EditText)findViewById(R.id.id);
    name=(EditText)findViewById(R.id.name);
    marks=(EditText)findViewById(R.id.marks);
    dHelper = new DHelper(this);
    insert.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
boolean result=dHelper.insertData(name.getText().toString(),marks.getText().toString());
if(result==false)
{
    Toast.makeText(getApplicationContext(),"Error in insertion",Toast.LENGTH_LONG).show();
}
else
    Toast.makeText(getApplicationContext(),"Data is inserted",Toast.LENGTH_LONG).show();

        }
    });
    select.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Cursor cur=dHelper.getData();
            if(cur.getCount()==0)
            {
                Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_LONG).show();
            }
            else
            {
                StringBuffer buffer = new StringBuffer();
                while(cur.moveToNext())
                {
                    buffer.append("id "+cur.getString(0)+"\n");
                    buffer.append("name "+cur.getString(1)+"\n");
                    buffer.append("marks "+cur.getString(2)+"\n");
                }
                showMessage("Data",buffer.toString());
            }
        }
    });
    update.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean result = dHelper.updateData(id.getText().toString(),name.getText().toString(),marks.getText().toString());
            if(result==true)
                Toast.makeText(MainActivity.this, "Data updated sucessfully", Toast.LENGTH_SHORT).show();
        }
    });
    }
    public  void showMessage(String title,String data)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(data);
        builder.show();
    }
}
