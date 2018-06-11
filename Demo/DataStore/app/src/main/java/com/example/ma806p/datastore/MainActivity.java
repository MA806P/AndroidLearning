package com.example.ma806p.datastore;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
        textView = (TextView)findViewById(R.id.textView);

        //得到 SharedPreferences 对象， 文件名 进入模式MODE_PRIVATE只可以当前应用程序使用
        preferences = getSharedPreferences("test", Context.MODE_PRIVATE); //test.xml

    }



//------------ 数据库操作
    public void dbTestAction(View view) {

//        MyOpenHelper openHelper = new MyOpenHelper(this, "test.db");
//
//        //getWritableDatabase() 返回数据库可读写
//        //返回数据库，如果没有问题与getWritableDatabase完全相同，如果磁盘空间不足，则返回的数据库为只读
//        SQLiteDatabase database = openHelper.getReadableDatabase();
//
//        String sql = "insert into test()";
//        database.execSQL(sql);
//
//
//        //数据库关闭
//        database.close();
//        openHelper.close();


        Intent intent = new Intent(this, MainDBTestActivity.class);
        startActivity(intent);



    }













//------------ SharedPreferences
    public void dataWriteAction(View view) {
        //写入 SharedPreferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("test", editText.getText().toString());
        editor.commit(); //提交

        Toast.makeText(this, "write success", Toast.LENGTH_SHORT).show();

    }

    public void dataReadAction(View view) {

        String test = preferences.getString("test", " ");
        textView.setText(test);
    }


//------------ 文件写入, 读出

    public void fileWriteAction(View view) {
        String content = editText.getText().toString();
        FileOutputStream fos = null;
        BufferedWriter writer = null;

        try {
            fos = openFileOutput("fileName.txt", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(content);
            writer.flush();

            Toast.makeText(this, "写入成功 " + content, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //文件读出
    public void fileReadAction(View view) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        try {
            br = new BufferedReader(new InputStreamReader(openFileInput("fileName.txt")));
            String content = null;
            content = br.readLine();

            Log.i("read", content);


            while (content != null) {
                sb.append(content);
                content = br.readLine();
            }
            Toast.makeText(this, "读出成功 " + sb.toString(), Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


//------------ 读出资源文件
    public void readRawFileAction(View view) {

        InputStream is = getResources().openRawResource(R.raw.test);
        BufferedReader br = null;
        try {
            //InputStreamReader(is)
            br = new BufferedReader(new InputStreamReader(is,"GBK")); //中文编码
            String content;
            StringBuilder sb = new StringBuilder();
            content = br.readLine();
            while (content != null){
                sb.append(content);
                content = br.readLine();
            }

            Toast.makeText(this, "读出raw成功 " + sb.toString(), Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


 //----------- 读写SD卡
    public void writeSDAction(View view) {


        /**
         * 动态获取权限，Android 6.0 新特性，一些保护权限，除了要在AndroidManifest中声明权限，还要使用如下代码动态获取
         */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }




        //判断SD卡状态
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            //SD卡可用
            //得到路径
            File dir = Environment.getExternalStorageDirectory();
            File file = new File(dir, "test.txt");
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                write(fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            Toast.makeText(this, "SD卡有问题", Toast.LENGTH_LONG).show();
        }
    }


    public void readSDAction(View view) {

        //判断SD卡状态
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            //SD卡可用
            //得到路径
            File dir2 = Environment.getExternalStorageDirectory();
            File file2 = new File(dir2, "test.txt");
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file2);
                read(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            Toast.makeText(this, "SD卡有问题", Toast.LENGTH_LONG).show();
        }
    }





    public void write(OutputStream os) throws IOException {

        String content = editText.getText().toString();
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(content);
            writer.flush();
            Toast.makeText(this, "写入完成。。。", Toast.LENGTH_LONG).show();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void read(InputStream is) throws IOException {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        try {
            br = new BufferedReader(new InputStreamReader(is));
            String content = br.readLine();
            while (content != null) {
                sb.append(content);
                content = br.readLine();
            }

            Toast.makeText(this, "读取完成。。。" + sb.toString(), Toast.LENGTH_LONG).show();

        } finally {
            if (br != null) {
                br.close();
            }
        }
    }



}
