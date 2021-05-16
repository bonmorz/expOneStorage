package com.example.exponestorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText name=(EditText)findViewById(R.id.name);
        EditText age=(EditText)findViewById(R.id.age);
        EditText number=(EditText)findViewById(R.id.number);
        TextView textView = (TextView)findViewById(R.id.textView);
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        TextView textView3 = (TextView)findViewById(R.id.textView3);

        Button addBtn = findViewById( R.id.button);
        Button readBtn = findViewById(R.id.button2);
        
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入框的内容
                String names = name.getText().toString();
                //String pswds = pswd.getText().toString();
                //String sexs = sex.getText().toString();
                String ages = age.getText().toString();
                String numbers = number.getText().toString();

                //在 MySharedPreferences类里定义好存取方法后，就可以调用了
                //这里将数据保存进去  注意：(name 我是定义了有返回值的，试试看)
                Boolean bool = MySharedPreferences.setName(names, MainActivity.this);
                //MySharedPreferences.setPswd(pswds, MainActivity.this);
                //MySharedPreferences.setSex(sexs,   MainActivity.this);
                MySharedPreferences.setAge(ages, MainActivity.this);
                MySharedPreferences.setNumber(numbers, MainActivity.this);

                //看看保存成功没
                if (bool)
                    Toast.makeText(MainActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "保存失败！", Toast.LENGTH_SHORT).show();

                //跳转到展示界面
                //startActivity(new Intent(MainActivity.this, Main2Activity.class));
                String names_two = (String) MySharedPreferences.getName(MainActivity.this);
                String ages_two = (String) MySharedPreferences.getAge(MainActivity.this);
                String numbers_two =  (String)  MySharedPreferences.getNumber(MainActivity.this);

                textView.setText("学号：" + numbers_two);
                textView2.setText("姓名：" + names_two);
                textView3.setText("年龄：" + ages_two);

            }
        });
        readBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String names_two = (String) MySharedPreferences.getName(MainActivity.this);
                String ages_two = (String) MySharedPreferences.getAge(MainActivity.this);
                String numbers_two =  (String)  MySharedPreferences.getNumber(MainActivity.this);

                textView.setText("学号：" + numbers_two);
                textView2.setText("姓名：" + names_two);
                textView3.setText("年龄：" + ages_two);
            }
                                   }


        );
    }
}