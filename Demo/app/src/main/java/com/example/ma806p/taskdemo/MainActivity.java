package com.example.ma806p.taskdemo;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private EditText editText;
    private ProgressBar progressBar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);


        editText = (EditText)findViewById(R.id.editText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }


//---------------- 子线程 读取文件功能 文件可能很大耗时所以在子线程中去做
    public void readFileAction(View view) {
        //执行子线程，完成读取文件

        File file = new File(Environment.getExternalStorageDirectory(), "NewTextFile.txt");
        Log.i("test", file.toString());
        new ReadFileTask().execute(file);

    }



    class ReadFileTask extends AsyncTask<File, Integer, String> {


        @Override
        protected String doInBackground(File... files) {
            return read(files[0]);
        }

        //运行在主线程中，当后台运行结束，自动调用的方法
        @Override
        protected void onPostExecute(String s) {
            editText.setText(s);
            super.onPostExecute(s);
        }


//        //进度指示的方法
//        //运行在主线程中，在这个方法中可以直接更新控件的属性
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//
//            int num = values[0];
//            //进度条
//            progressBar.setProgress(num);
//            super.onProgressUpdate(values);
//        }



        //读文件
        private String read(File file) {

            StringBuilder sb = new StringBuilder();
            BufferedReader br = null;

//            //得到文件的长度，可用之读取进度
//            long fileLength = file.length();
//            long readLength = 0;


            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = null;
                line = br.readLine();
                while (line != null) {
                    sb.append(line + "\n");


//                    //文件读取百分比
//                    readLength += line.getBytes().length;
//                    int per = (int) (readLength * 100 / fileLength);
//                    publishProgress(per);

                    //Thread.sleep(100);//模拟耗时
                    line = br.readLine();
                }
                return sb.toString();
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

            return null;
        }


    }





//---------------- 子线程 计数功能

    //按钮点击
    public void changeNum(View view) {
        new MyTask().execute();//自动调用doInBackground
    }


    //三个参数
    /**
     * 三个参数
     * Params 执行子线程所需传入的参数类型
     * Progress 进度指示时所需的类型
     * Result 运行后的结果类型
     * */
    class MyTask extends AsyncTask<Void, Integer, Void> {


        //运行在后台，相当于是 Thread 中 run 方法
        //运行在子线程中的方法
        @Override
        protected Void doInBackground(Void... voids) {

            for (int i=0;i<=10;i++){
                publishProgress(i); //自动调用 onProgressUpdate

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }


        //进度指示的方法
        //运行在主线程中，在这个方法中可以直接更新控件的属性
        @Override
        protected void onProgressUpdate(Integer... values) {

            int num = values[0];
            textView.setText("num " + num);

            //进度条
            progressBar.setProgress(num);

            super.onProgressUpdate(values);
        }


        //运行在主线程中，当后台运行结束，自动调用的方法
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(MainActivity.this, "子线程运行结束", Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);
        }
    }


}
