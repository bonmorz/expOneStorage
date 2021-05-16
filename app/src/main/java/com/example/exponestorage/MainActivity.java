package com.example.exponestorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public void saveSDCard(String information){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File sdCaraDir = Environment.getExternalStorageDirectory();
            File saveFile= new File(sdCaraDir,"SDCard.txt");
            try{
                FileOutputStream outStream = new FileOutputStream(saveFile,false);
                outStream.write(information.getBytes());
                outStream.close();
            } catch (Exception e){
                Toast.makeText(MainActivity.this,"保存文件失败",Toast.LENGTH_LONG);
            }

        }
    }
    public void readSDCard(TextView textX){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            File saveFile = new File(sdCardDir, "SDCard.txt");

            try{
                FileInputStream inStream = new FileInputStream(saveFile);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = -1;
                while((length = inStream.read(buffer))!=-1){
                    stream.write(buffer,0,length);
                    textX.setText(stream.toString());
                }
                stream.close();
                inStream.close();
                Toast.makeText(MainActivity.this, stream.toString(),Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                return;
            }
        }

    }


    private MyDatabaseHelper dbHelper;
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
        TextView textView4 = (TextView)findViewById(R.id.textView4);
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
                                   });

        //开始进入数据库部分
        dbHelper = new MyDatabaseHelper(this,"studentStorage.db",null,1);
        dbHelper.getWritableDatabase();

        //添加数据部分
        Button addSQLBtn = findViewById(R.id.button3);
        addSQLBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String names = name.getText().toString();
                //String pswds = pswd.getText().toString();
                //String sexs = sex.getText().toString();
                String ages = age.getText().toString();
                String numbers = number.getText().toString();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("name",names);
                values.put("id",numbers);
                values.put("age",ages);
                db.insert("studentList",null,values);
                values.clear();
                textView.setText("学号：" + numbers);
                textView2.setText("姓名：" + names);
                textView3.setText("年龄：" + ages);

            }
        });
        //数据库读取部分
        Button queryButton=findViewById(R.id.button4);
        queryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SQLiteDatabase db =dbHelper.getWritableDatabase();
                Cursor cursor = db.query("studentList",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String number = cursor.getString(cursor.getColumnIndex("id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String age = cursor.getString(cursor.getColumnIndex("age"));
                        textView.append(number+" "+name+" "+age+"\n");
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });

        //开始进入sd卡部分
        Button addSDBtn = findViewById(R.id.button5);
        addSDBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String names = name.getText().toString();
                //String pswds = pswd.getText().toString();
                //String sexs = sex.getText().toString();
                String ages = age.getText().toString();
                String numbers = number.getText().toString();
                String values = names+ages+numbers;
                saveSDCard(values);
                textView.setText("学号：" + numbers);
                textView2.setText("姓名：" + names);
                textView3.setText("年龄：" + ages);

            }
        });

        //SD卡读取部分
        Button readSDBtn = findViewById(R.id.button6);

        readBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                readSDCard(textView4);
            }
        });




    }
}