package com.example.ma806p.dialogviewtest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private AlertDialog dlgRadio;
    private AlertDialog dlgMulit;
    private ProgressDialog progressDialog;
    private final  static  int MSG_PROGRESS = 0x0008;
    private final  static  int MSG_FINISH = 0x0009;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_PROGRESS:
                    //运行在UI线程中，设置对话框的进度条
                    progressDialog.setProgress(msg.arg1);
                    break;

                case MSG_FINISH:
                    progressDialog.dismiss();
                    break;
            }
        }
    };


    private Dialog customDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final String[] items = {"aaa", "bbb", "ccc"};
        //单选对话框
        dlgRadio = new  AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle("列表对话框")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

                    /*
                     * Dialog 发出事件的对话框
                     * which 列表项的索引
                     * */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("确定", null)
                .create();

        //多选对话框
        dlgMulit = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle("多选对话框")
                .setMultiChoiceItems(items, new boolean[]{false, true, false}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Toast.makeText(MainActivity.this, which + " " + items[which] + isChecked, Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("确定", null)
                .create();


        //进度条对话框
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher_round);
        progressDialog.setMessage("message");
        progressDialog.setTitle("进度条");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //ProgressBar progressBar = new ProgressBar(this);


        //自定义视图对话框
        LayoutInflater inflater = getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_view, null);
        final EditText etUserName = (EditText)customView.findViewById(R.id.customUserInput);
        final EditText etPwd = (EditText)customView.findViewById(R.id.customPwdInput);
        Button btnOK = (Button)customView.findViewById(R.id.customDialogSubmitBtn);
        btnOK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = etUserName.getText().toString();
                String password = etPwd.getText().toString();
                Toast.makeText(MainActivity.this, name + " " + password, Toast.LENGTH_SHORT).show();

                //关闭对话框
                customDlg.dismiss();
            }
        });

        customDlg = new Dialog(this);
        customDlg.setTitle("自定义");
        customDlg.setContentView(customView);


    }




    public void showToast(View view) {

        switch (view.getId()) {
            case R.id.showToastButton:
                //使用静态方法 makeText
                //context上下文，text消息内容， duration持续时间
                //show()方法显示出来
                Toast.makeText(this, "Toast Test Show", Toast.LENGTH_LONG).show();
                break;

            case R.id.showConToastBtn:
                //构造来构建Toast
                Toast toast = new Toast(this);
                ImageView image = new ImageView(this);
                image.setImageResource(R.drawable.ic_launcher_foreground);
                toast.setView(image);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
                break;

            case R.id.showCon2ToastBtn:
                View toastView = LayoutInflater.from(this).inflate(R.layout.toast_view, null);

                toast = new Toast(this);
                toast.setView(toastView);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0);
                toast.show();
                break;
        }

    }


    public void showDialog(View view) {

        switch (view.getId()) {
            case R.id.showDialogBtn:

                AlertDialog dlg = new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setTitle("Test")
                        .setMessage("Message")
                        .setPositiveButton("确定", null)
                        .create();
                dlg.show();
                break;

            case R.id.showDialogManyBtn:

                //显示多个按钮的对话框
                new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setTitle("有多个按钮")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "点击确定", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "点击取消", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNeutralButton("忽略",null)
                        .show();
                break;
        }
    }



    public void showDialogList(View view) {

        final String[] items = {"aaa", "bbb", "ccc"};
        new  AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle("列表对话框")
                .setItems(items, new DialogInterface.OnClickListener() {

                    /*
                     * Dialog 发出事件的对话框
                     * which 列表项的索引
                     * */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }


    public void showDialogRadioList(View view) {

        //每次显示不会重新创建，这样就可以保存上次选的那一项
        //dlgRadio.show();

        //多选对话框
        dlgMulit.show();
    }



    public void showProgressDialog(View view) {

        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                for (int i=0; i<20; i++) {
                    Message msg = handler.obtainMessage();
                    msg.what = MSG_PROGRESS;
                    msg.arg1 = i*5;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(MSG_FINISH);
            };
        }.start();
    }




    public void showDialogCustom(View view){

//        LayoutInflater inflater = getLayoutInflater();
//        View customView = inflater.inflate(R.layout.dialog_view, null);
//        final EditText etUserName = (EditText)customView.findViewById(R.id.customUserInput);
//        final EditText etPwd = (EditText)customView.findViewById(R.id.customPwdInput);
//
//        new AlertDialog.Builder(this)
//                .setTitle("自定义")
//                .setView(customView)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String name = etUserName.getText().toString();
//                        String password = etPwd.getText().toString();
//                        Toast.makeText(MainActivity.this, name + " " + password, Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .show();


        customDlg.show();


    }






}
