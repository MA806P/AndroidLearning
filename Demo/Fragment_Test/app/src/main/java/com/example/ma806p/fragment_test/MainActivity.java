package com.example.ma806p.fragment_test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private BlankFragment blankFragment;
    private BlankFragment tagBlankFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取fragmentManager对象
        mFragmentManager = getFragmentManager(); //android.app.FragmentManager对象
        //查找
        blankFragment = (BlankFragment)mFragmentManager.findFragmentById(R.id.myFragment);
        tagBlankFragment = (BlankFragment)mFragmentManager.findFragmentByTag("blankFragmentTag");


    }


    //改变 Fragment text
    public void changeGreetingflakjsldkfj(View view) {
        blankFragment.setGreeting("adf");
        tagBlankFragment.setGreeting("tagChange");
    }


    //动态添加 Fragment
    public void addFragmentAction(View view) {


        //添加Fragment

        //1
        FragmentManager fm = getFragmentManager();
        //2
        FragmentTransaction ft = fm.beginTransaction();

        //3 将Fragment添加到容器中
        BlankFragment fragment = new BlankFragment();
        //容器id  Fragment对象  Fragment tag
        ft.add(R.id.mainLayoutFragmentTest, fragment, "test");

        //4 提交
        ft.commit();

    }

    //移除 fragment
    public void removeFragmentAction(View view) {

        //移除Fragment

        //1
        FragmentManager fm = getFragmentManager();
        //2
        FragmentTransaction ft = fm.beginTransaction();
        //3 移除
        BlankFragment fragment = (BlankFragment)fm.findFragmentByTag("test"); //tag是上面添加的tag
        ft.remove(fragment);

        //4 提交
        ft.commit();
    }

    //隐藏、显示 fragment
    public void hideFragmentAction(View view) {
        //1
        FragmentManager fm = getFragmentManager();
        //2
        FragmentTransaction ft = fm.beginTransaction();
        //3
        BlankFragment fragment = (BlankFragment)fm.findFragmentByTag("test"); //tag是上面添加的tag
        if (fragment.isHidden()) {
            ft.show(fragment);
        } else  {
            ft.hide(fragment);
        }

        //4 提交
        ft.commit();
    }

    //替换 fragment
    private int num = 0;
    public void replaceFragmentAction(View view) {
        num++;

        BlankFragment fragment = BlankFragment.newInstance(num);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.mainLayoutFragmentRplace, fragment)
                .addToBackStack(null) //添加到栈，退出一层层退出
                .commit();
    }



//------- 显示对话框
    public void showDialog(View view) {
        MyDialog dlg = MyDialog.newInstance("this is title");
        dlg.show(getFragmentManager(), "dialog");
    }

    public  void ok() {
        Toast.makeText(MainActivity.this, "click ok", Toast.LENGTH_SHORT).show();
    }

    public static class MyDialog extends DialogFragment {
        private String title;
        public static MyDialog newInstance(String title) {
            MyDialog dlg = new MyDialog();
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            dlg.setArguments(bundle);
            return dlg;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            title = getArguments().getString("title");

            Dialog dlg = new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((MainActivity)getActivity()).ok();
                        }
                    })
                    .setNegativeButton("canncel", null)
                    .create();

            return dlg;
        }


    }


//-------

    public void showListFragment(View view) {
        String[] data = {"aaa", "bbb", "ccc"};
        MyListFagment f = MyListFagment.newInstance(data);
        getFragmentManager().beginTransaction().add(R.id.layoutListFragment, f).commit();
    }

    public static class MyListFagment extends ListFragment {
        private String[] data;
        public static MyListFagment newInstance(String[] data){
            MyListFagment lf = new MyListFagment();
            Bundle args = new Bundle();
            args.putStringArray("data", data);
            lf.setArguments(args);
            return lf;
        }

        //设置适配器

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            data = getArguments().getStringArray("data");
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    data));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Toast.makeText(getActivity(), data[position], Toast.LENGTH_SHORT).show();
            super.onListItemClick(l, v, position, id);
        }
    }







//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.i("Main", "onStart");
//    }
//
//    //可见，显示出来获得焦点
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.i("Main", "onResume");
//    }
//
//
//    //可见，失去焦点
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.i("Main", "onPause");
//    }
//
//    //不可见
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.i("Main", "onStop");
//    }
//
//    //在整个生命周期中只调用一次
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.i("Main", "onDestroy");
//    }
}
