package com.example.exponestorage;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    //创建一个SharedPreferences    类似于创建一个数据库，库名为 data
    public static SharedPreferences share(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("date", Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    //name 账号
    //调用上面的 share(Context context) 方法 获取标识为 "name" 的数据
    public static Object getName(Context context){
        return share(context).getString("name",null);
    }
    //调用上面的 share(Context context) 方法 将数据存入，并标识为 "name"
    //使用 commit() 方法保存会给出反应（保存成功或失败）
    public static boolean setName(String name, Context context){
        SharedPreferences.Editor e = share(context).edit();
        e.putString("name",name);
        Boolean bool = e.commit();
        return bool;
    }
    //age 年龄
    public static String getAge(Context context){
        return share(context).getString("age",null);
    }
    public static void setAge(String age, Context context){
        SharedPreferences.Editor e = share(context).edit();
        e.putString("age",age);
        e.apply();
    }
    //学号
    public static String getNumber(Context context){
        return share(context).getString("number",null);
    }
    public static  void setNumber(String number,Context context){
        SharedPreferences.Editor e = share(context).edit();
        e.putString("number",number);
        e.apply();
    }
}
