package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    Button b_zero , b_one, b_two , b_three , b_four ,b_five , b_six , b_seven , b_eight ,b_nine,add_btn,sub_btn,mul_btn,div_btn,clear_btn,equal_btn;
    TextView variable;
    boolean add=false,sub=false,mul=false,div=false;
    int op1,op2,result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        variable = (TextView)findViewById(R.id.editText);
        variable.setText("");
        b_zero = (Button)findViewById(R.id.button0);
        b_one = (Button)findViewById(R.id.button1);
        b_two = (Button)findViewById(R.id.button2);
        b_three = (Button)findViewById(R.id.button3);
        b_four = (Button)findViewById(R.id.button4);
        b_five = (Button)findViewById(R.id.button5);
        b_six = (Button)findViewById(R.id.button6);
        b_seven = (Button)findViewById(R.id.button7);
        b_eight = (Button)findViewById(R.id.button8);
        b_nine = (Button)findViewById(R.id.button9);
        add_btn = (Button)findViewById(R.id.buttona);
        sub_btn = (Button)findViewById(R.id.buttonm);
        mul_btn = (Button)findViewById(R.id.buttonmu);
        div_btn= (Button)findViewById(R.id.buttond);
        equal_btn =(Button)findViewById(R.id.buttone);
        clear_btn = (Button)findViewById(R.id.buttonc);
        b_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setText(variable.getText().toString()+"0");
            }
        });
        b_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setText(variable.getText().toString()+"1");
            }
        });
        b_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setText(variable.getText().toString()+"2");
            }
        });
        b_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setText(variable.getText().toString()+"3");
            }
        });
        b_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setText(variable.getText().toString()+"4");
            }
        });
        b_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setText(variable.getText().toString()+"5");
            }
        });
        b_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setText(variable.getText().toString()+"6");
            }
        });
        b_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setText(variable.getText().toString()+"7");
            }
        });
        b_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setText(variable.getText().toString()+"8");
            }
        });
        b_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setText(variable.getText().toString()+"9");
            }
        });
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setText("");
            }
        });

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variable.setText("");
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op1 = Integer.parseInt(variable.getText().toString());
                add = true;
                variable.setText("");
            }
        });
        equal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (add == true )
                {
                    op2 = Integer.parseInt(variable.getText().toString());
                    result = op1 + op2;
                    add=false;
                }
                else
                if (sub == true )
                {
                    op2 = Integer.parseInt(variable.getText().toString());
                    result = op1 - op2;
                    sub = false;
                }
                else
                if (mul == true )
                {
                    op2 = Integer.parseInt(variable.getText().toString());
                    result = op1 * op2;
                    mul = false;
                }
                else
                if (div == true )
                {
                    op2 = Integer.parseInt(variable.getText().toString());
                    if(op2!=0)
                    result = op1 / op2;
                    else
                        result = 0;
                    div = false;
                }
                else
                {
                    result = 0;
                }
                String s =Integer.toString(result);
                variable.setText(s);
            }
        });
        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sub = true;
                op1 = Integer.parseInt(variable.getText().toString());
                variable.setText("");
            }
        });
        mul_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mul = true;
                op1 = Integer.parseInt(variable.getText().toString());
                variable.setText("");
            }
        });
        div_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                div = true;
                op1 = Integer.parseInt(variable.getText().toString());
                variable.setText("");
            }
        });
    }
}
