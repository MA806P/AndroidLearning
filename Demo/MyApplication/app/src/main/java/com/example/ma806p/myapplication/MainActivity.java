package com.example.ma806p.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private Button btnTest;
    private EditText etContent;
    private ImageView image;
    private Boolean switchFlag;

    private RadioGroup rgCourse;
    private RadioButton radJava, radAndroid, radGoogle;
    private TextView tvChoice;

    private CheckBox boxCN, boxEN;

    private TextView synacTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchFlag = new Boolean(false);

        btnTest = (Button)findViewById(R.id.button);
        Listener listener = new Listener();
        btnTest.setOnClickListener( listener );

//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("Test", "匿名类方法，实现单击");
//            }
//        });

        etContent = (EditText)findViewById(R.id.editText);
        etContent.setText("Hello world");


        image = (ImageView)findViewById(R.id.imageView);


        tvChoice = (TextView)findViewById(R.id.textView);
        rgCourse = (RadioGroup)findViewById(R.id.radioGroup);
        radJava = (RadioButton)findViewById(R.id.radio_java);
        radAndroid = (RadioButton)findViewById(R.id.radio_android);
        radGoogle = (RadioButton)findViewById(R.id.radio_google);

        radAndroid.setChecked(true);

        rgCourse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("Radio Group", "Check"+checkedId);

                switch (checkedId) {
                    case R.id.radio_java:
                        tvChoice.setText("Java");
                        break;
                    case R.id.radio_android:
                        tvChoice.setText("Android");
                        break;
                    case R.id.radio_google:
                        tvChoice.setText("Google");
                        break;
                }

                //if (radAndroid.isChecked())
            }
        });


        boxCN = (CheckBox)findViewById(R.id.checkBox1);
        boxEN = (CheckBox)findViewById(R.id.checkBox2);

        //boxEN.setChecked(true);

        CheckChangeListener checkBoxListener = new CheckChangeListener();
        boxCN.setOnCheckedChangeListener(checkBoxListener);
        boxEN.setOnCheckedChangeListener(checkBoxListener);


        synacTextView = (TextView)findViewById(R.id.asyncTextView);
        synacTextView.setText("zhishi");
    }


    //android:onClick="click"/>
//    public void click(View view) {
//        Log.i("Test", "以XML中，实现单击");
//    }

    class Listener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            Log.i("Test", "按钮被单击了");
            switchFlag = !switchFlag;
            if (switchFlag) {
                etContent.setText("Hello World !");
                image.setImageResource(R.drawable.ic_launcher_background);
            } else {
                etContent.setText("Nice to meet you");
                image.setImageResource(R.drawable.ic_launcher_foreground);
            }


        }

    }



    class CheckChangeListener implements CompoundButton.OnCheckedChangeListener {

        StringBuilder sb = new StringBuilder();
        String str;

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Log.i("CheckBox", "Check"+isChecked);

            switch (buttonView.getId()) {
                case R.id.checkBox1:
                    str = "China";
                    changeString(str, isChecked);
                    break;
                case R.id.checkBox2:
                    str = "English";
                    changeString(str, isChecked);
                    break;
            }

            tvChoice.setText(sb.toString());

        }


        private  void  changeString(String str, boolean isChecked) {
            if (isChecked) {
                sb.append(str);
            } else {
                int start = sb.indexOf(str);
                if (start != -1) {
                    int end = str.length() + start;
                    sb.delete(start, end);
                }
            }
        }

    }





    public static final String EXTRA_MESSAGE = "com.example.ma806p.myapplication.MESSAGE";

    public void sendMessage(View view) {

        Intent intent = new Intent(this, NextPageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText2);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

        /*
        * Intent 构造函数采用两个参数：
        * Context 是第一个参数（之所以使用 this 是因为 Activity 类是 Context 的子类）
        * 应用组件的 Class，系统应将 Intent（在本例中，为应启动的 Activity）传递至该类。
        * putExtra() 函数将 EditText 的值添加到 intent。Intent 能够以名为 extra 的键值对形式携带数据类型。
        * 您的键是一个公共常量 EXTRA_MESSAGE，因为下一个 Activity 将使用该键来检索文本值。
        * 为 intent extra 定义键时最好使用应用的软件包名称作为前缀。
        * 这可以确保在您的应用与其他应用交互时这些键始终保持唯一。
        * startActivity() 函数将启动 Intent 指定的 DisplayMessageActivity 实例。现在，您需要创建该类。
        * */





    }



    //侧滑视图
    public void to_drawerLayout_page(View view) {
        Intent intentToDrawLayout = new Intent(this, LoginActivity.class);
        startActivity(intentToDrawLayout);
    }

    //滚动视图
    public void to_scrolling_page(View view) {
        Intent intentToScrolling = new Intent(this, ScrollViewActivity.class);
        startActivity(intentToScrolling);
    }






    //消息处理机制，多线程
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0x0001:
                    int index = msg.arg1;

                    Log.i("test", "111" + " " + index);

                    //synacTextView.setText(index); //在主线程中允许修改 UI
                    break;
            }
        }


//        @Override
//        public void dispatchMessage(Message msg) {
//            if (msg.what == 0x0002) {
//                Log.i("test", "111" + " " + msg.arg1);
//            }
//        }


    };

    public void handler_test(View view) {

        switch (view.getId()) {
            case R.id.btnAsync:

                new  Thread() {
                    @Override
                    public void run() {
                        for  (int i=0; i<10; i++) {
                            //tvChoice.setText(i);//不允许，子线程不可更新UI线程中控件的属性
                            Message msg = new Message();
                            msg.what = 0x0002;
                            msg.arg1 = i;
                            handler.sendMessage(msg);

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }.start();
                break;
        }

    }




}

