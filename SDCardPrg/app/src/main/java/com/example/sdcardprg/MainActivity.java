package com.example.sdcardprg;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
Button submit,read;
TextView textView;
EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        checkPermission();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        String str = editText.getText().toString();
        if(isExternalStorageAvailable())
        {
            try
            {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsoluteFile()+"/MyDir");
                Toast.makeText(MainActivity.this,dir.toString(),Toast.LENGTH_SHORT).show();
                if(!dir.exists())
                    dir.mkdir();
                File file = new File(dir,"MyData.txt");
                FileOutputStream fout = new FileOutputStream(file,true);
                fout.write(str.getBytes());
                fout.close();
                Toast.makeText(MainActivity.this,"Data is saved",Toast.LENGTH_SHORT).show();

            }
            catch (Exception e)
            {
                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }

        }
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
            try
            {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsoluteFile()+"/MyDir");
                Toast.makeText(MainActivity.this,dir.toString(),Toast.LENGTH_SHORT).show();
                File file = new File(dir,"MyData.txt");
            String message;
                FileInputStream fin = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fin);
                BufferedReader bufferedReader= new BufferedReader(isr);
                StringBuffer stringBuffer= new StringBuffer();
                while(( message=bufferedReader.readLine())!=null)
                {
                stringBuffer.append(message+"\n");

                }
                textView.setText(stringBuffer);
            }
            catch (Exception e)
            {

            }
            }
        });
    }

    private boolean isExternalStorageAvailable() {
    return true;
    }

    private void checkPermission() {
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else
        {

        }
    }

    private void initialize() {
    submit=(Button)findViewById(R.id.submit);
    read=(Button)findViewById(R.id.Read);
    textView=(TextView) findViewById(R.id.readText);
    editText=(EditText)findViewById(R.id.textEnter);
    }
}
