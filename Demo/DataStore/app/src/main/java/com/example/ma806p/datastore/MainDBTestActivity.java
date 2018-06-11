package com.example.ma806p.datastore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainDBTestActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private ListView listView;

    private MyOpenHelper helper;
    private Cursor cursor;

    private Dialog dlg;
    private String studentID = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dbtest);

        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);

        listView = (ListView)findViewById(R.id.listViewId);

        helper = new MyOpenHelper(this, "student.db");
        cursor = helper.getReadableDatabase().query("student",
                null,
                null,
                null,
                null,
                null,
                null);

        publicShowAllStudent();
    }



    //添加学生
    public void addStudentAction(View view) {

        String name = editText1.getText().toString().trim();
        String age = editText2.getText().toString().trim();
        String score = editText3.getText().toString().trim();

        SQLiteDatabase db = helper.getReadableDatabase();

        //插入参数, 表名  列名  插入对象contentValue--map
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("score", score);
        values.put("age", age);
        long rowId = db.insert("student", null, values);
        Log.i("test", rowId + " ");

        if (rowId != -1) {
            Toast.makeText(this, "数据插入成功", Toast.LENGTH_LONG).show();
        }
        db.close();


        publicShowAllStudent();

    }

    //显示所有学生
    public void showAllStudent(View view) {

        SQLiteDatabase db = helper.getReadableDatabase();

        //select 列名，  from 表名， where _id=? or name = ？ group by 列名 having .. order by ..
        Cursor cur = db.query(
                "student",
                null, //选择的列名，null表示所有的
                null, //where 后面的语句
                null, //行条件参数
                null, //group by 语句
                null,
                null //排序
        );

//        CursorAdapter adapter = new CursorAdapter(this, cur) {
//            @Override
//            public View newView(Context context, Cursor cursor, ViewGroup parent) {
//                View view1 = LayoutInflater.from(MainDBTestActivity.this).inflate(R.layout.student_item, null);
//                return view1;
//            }
//
//            @Override
//            public void bindView(View view, Context context, Cursor cursor) {
//
//                TextView tvName = (TextView)view.findViewById(R.id.tvName);
//                TextView tvAge = (TextView)view.findViewById(R.id.tvAge);
//                TextView tvScore = (TextView)view.findViewById(R.id.tvScore);
//
//                String name = cursor.getString(cursor.getColumnIndex("name"));
//                String age = cursor.getString(cursor.getColumnIndex("age"));
//                String score = cursor.getString(cursor.getColumnIndex("score"));
//
//                tvName.setText(name);
//                tvAge.setText(age);
//                tvScore.setText(score);
//            }
//        };
//
//        listView.setAdapter(adapter);


        SimpleCursorAdapter adapter1 = new SimpleCursorAdapter(
                this,
                R.layout.student_item,
                cur,
                new String[]{"name", "age", "score"},
                new int[]{R.id.tvName, R.id.tvAge, R.id.tvScore}
        );
        listView.setAdapter(adapter1);


    }



    //浏览数据
    public void browser(View view) {
        switch (view.getId()) {
            case R.id.btnFirst:
                if (cursor.moveToFirst()) {
                    displayStudent();
                }
                break;
            case R.id.btnPrev:
                if (cursor.moveToPrevious()) {
                    displayStudent();
                }
                break;
            case R.id.btnNext:
                if (cursor.moveToNext()) {
                    displayStudent();
                }
                break;
            case R.id.btnLast:
                if (cursor.moveToLast()) {
                    displayStudent();
                }
                break;

        }
    }

    private void displayStudent() {

        String name = cursor.getString(cursor.getColumnIndex("name"));
        String age = cursor.getString(cursor.getColumnIndex("age"));
        String score = cursor.getString(cursor.getColumnIndex("score"));

        editText1.setText(name);
        editText2.setText(age);
        editText3.setText(score);
    }





    //显示数据库所有学生信息
    private void publicShowAllStudent() {
        cursor = helper.getReadableDatabase().query("student",
                null,
                null,
                null,
                null,
                null,
                null);
        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(
                this,
                R.layout.student_item,
                cursor,
                new String[]{"name", "age", "score"},
                new int[]{R.id.tvName, R.id.tvAge, R.id.tvScore}
        );
        listView.setAdapter(adapter2);
    }




//------- 查找
    public void searchAction(View view) {

        Log.i("searchAction", "searchAction");

        //显示输入对话框
        View view1 = LayoutInflater.from(this).inflate(R.layout.student_search_id, null);
        final EditText etStudentId = (EditText)view1.findViewById(R.id.etSearchInput);

        dlg = new AlertDialog.Builder(this)
                .setView(view1)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //得到输入的 ID
                        studentID = etStudentId.getText().toString().trim();

                        Log.i("searchAction", "sure" + studentID);

                        Cursor cur = helper.getReadableDatabase().query(
                                "student",
                                null, //选择的列名，null表示所有的
                                "_id=?", //where 后面的语句
                                new String[]{studentID}, //行条件参数
                                null, //group by 语句
                                null,
                                null //排序
                        );

                        if (cur.moveToFirst()) {

                            String name = cur.getString(cur.getColumnIndex("name"));
                            String age = cur.getString(cur.getColumnIndex("age"));
                            String score = cur.getString(cur.getColumnIndex("score"));

                            editText1.setText(name);
                            editText2.setText(age);
                            editText3.setText(score);

                        } else {
                            Toast.makeText(MainDBTestActivity.this, "没有找到数据", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        studentID = "-1";
                    }
                })
                .create();


        dlg.show();
    }


//----------- 删除
    public void deleteStudentAction(View view) {

        String id = cursor.getString(cursor.getColumnIndex("_id"));
        SQLiteDatabase db = helper.getReadableDatabase();
        //delete方法返回行数
        int line = db.delete("student", "_id=?", new String[]{id});
        if (line > 0) {
            Toast.makeText(this, "数据删除成功", Toast.LENGTH_LONG).show();
        }
        db.close();
        publicShowAllStudent();

    }

//---------- 修改
    public void changeStudentAction(View view) {

        //游标指向的id
        String id = cursor.getString(cursor.getColumnIndex("_id"));
        SQLiteDatabase db = helper.getReadableDatabase();

        //插入参数, 表名  列名  插入对象contentValue--map
        ContentValues values = new ContentValues();
        values.put("name", editText1.getText().toString().trim());
        values.put("age", editText2.getText().toString().trim());
        values.put("score", editText3.getText().toString().trim());


        int line = db.update("student", values, "_id=?", new String[]{id});
        if (line > 0) {
            Toast.makeText(this, "数据修改成功", Toast.LENGTH_LONG).show();
        }
        db.close();
        publicShowAllStudent();

    }


}
