package com.example.ma806p.datastore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyOpenHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    private static final String CREAT_TABLE_TEST = "create table test(_id integer primary key autoincrement, name)";

    public MyOpenHelper(Context context, String name) {
        super(context, name, null, VERSION);
    }


    /**
     * name 数据库文件名
     * factory 如果为null 使用默认方式
     * version 版本
     * */
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, VERSION);
    }

    //当数据库文件不存在，创建数据库文件，并且第一次使用时。只调用一次
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("opneHelper", "onCreate");

        //创建表只需一次
        //db.execSQL(CREAT_TABLE_TEST);

        String sql = "create table student(_id integer primary key autoincrement, name, age, score)";
        db.execSQL(sql);

    }


    //版本更新时 VERSION+1
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("opneHelper", "onUpgrade" + oldVersion + "  " + newVersion);

        //更改表结构
        //1.删除表
        db.execSQL("drop table if exists student");
        //2.创建表
        db.execSQL("create table student(_id integer primary key autoincrement, name, age, score)");

    }
}
