package com.example.ma806p.adapterlistview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private String[] data = {"aaa", "bbb", "ccc",
            "aaa", "bbb", "ccc",
            "aaa", "bbb", "ccc",
            "aaa", "bbb", "ccc",
            "aaa", "bbb", "ccc",
            "aaa", "bbb", "ccc",
            "aaa", "bbb", "ccc"};
    private ListView listView;

    private ArrayList<Map<String, String>> listData;
    private ListView listView2;

    private ArrayList<Map<String, String>> listXmlData;


    private static final int MSG_TESTFINISH = 0x018;
    private static final int MSG_TESTEND = 0x019;
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_TESTFINISH:

                    SimpleAdapter adapter2 = new SimpleAdapter(
                            MainActivity.this,
                            listXmlData,
                            R.layout.listview_simple_item,  //项目布局
                            new String[]{"name", "age"},  //显示数据在数据源中的Key
                            new int[]{R.id.textViewName, R.id.textViewAge} //显示数据控件的id
                    );
                    listView2.setAdapter(adapter2);
                    break;

                case MSG_TESTEND:
                    Toast.makeText(MainActivity.this, "数据出现异常", Toast.LENGTH_SHORT).show();
            }
        }
    };




    private ListView listViewBase;
    private int[] imageIds = {
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
    };
    private String[] names = {"111", "222", "333", "444", "555", "666"};
    private static int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Select" + " " + data[position], Toast.LENGTH_SHORT).show();
            }
        });

//        //适配器
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                this, //上下文
//                R.layout.listview_item, //项目布局
//                R.id.listItemTextView,  //数据要显示控件id
//                data    //数据源
//        );
//        listView.setAdapter(arrayAdapter);

        //系统定义的布局
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1, //系统中已经定义过的布局文件
                android.R.id.text1, //布局文件中的 TextView id
                data);
        listView.setAdapter(adapter);





//        listData = new ArrayList<Map<String, String>>();
//        Map<String, String> stu1 = new HashMap<String, String>();
//        stu1.put("name", "Tom1");
//        stu1.put("age", "20");
//        listData.add(stu1);
//        Map<String, String> stu2 = new HashMap<String, String>();
//        stu2.put("name", "Tom2");
//        stu2.put("age", "22");
//        listData.add(stu2);

//        listView2 = (ListView)findViewById(R.id.listViewSimpleAapter);
//        SimpleAdapter adapter2 = new SimpleAdapter(
//                this,
//                listData,
//                R.layout.listview_simple_item,  //项目布局
//                new String[]{"name", "age"},  //显示数据在数据源中的Key
//                new int[]{R.id.textViewName, R.id.textViewAge} //显示数据控件的id
//        );
//        listView2.setAdapter(adapter2);



        //使用线程加载数据，主线程刷新UI
        listView2 = (ListView)findViewById(R.id.listViewSimpleAapter);
        new Thread() {
            @Override
            public void run() {

                try {
                    fillData();
                    handler.sendEmptyMessage(MSG_TESTFINISH);

                } catch (XmlPullParserException e) {
                    handler.sendEmptyMessage(MSG_TESTEND);
                } catch (IOException e) {
                    handler.sendEmptyMessage(MSG_TESTEND);
                }

            };
        }.start();



        //带有图标的 listView
        listViewBase = (ListView)findViewById(R.id.listViewBaseAdapter);
        listViewBase.setAdapter(new MyBaseAdapter());


    }



    public void fillData() throws XmlPullParserException, IOException {

        List<Student> stuList = new ArrayList<Student>();
        Student stu = null;

        XmlPullParser parser = getResources().getXml(R.xml.stu);
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;

                case XmlPullParser.START_TAG:
                    String name = parser.getName();
                    if (name.equals("student")) {
                        stu = new Student();
                    }
                    if (name.equals("name")) {
                        stu.setName(parser.nextText());
                    }
                    if (name.equals("age")) {
                        stu.setAge(parser.nextText());
                    }
                    break;

                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equals("student")) {
                        stuList.add(stu);
                    }
                    break;
            }

            eventType = parser.next();
        }

        //填充数据
        listXmlData = new ArrayList<Map<String, String>>();
        for (Student s: stuList) {
            Map<String, String> stu1 = new HashMap<String, String>();
            stu1.put("name",  s.getName());
            stu1.put("age", s.getAge());
            listXmlData.add(stu1);
        }

    }


    static class Student {
        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public String getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(String age) {
            this.age = age;
        }

//        public Student(String name, String age) {
//            this.name = name;
//            this.age = age;
//        }
    }



    //-------------------------
    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return position ;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //显示的 view
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view;
            ViewHolder viewHolder = null;
            if (convertView == null) { //使用convertView提高效率，相当于重用

                //解析布局文件，成为View
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                view = inflater.inflate(R.layout.listview_base_item, parent, false);

                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder); //在View上保存需要的子View数据

            } else {
                view = convertView;
                viewHolder = (ViewHolder)view.getTag();
            }

            //填充当前项的数据
            ImageView image = viewHolder.getImageView(); //从持有者中得到控件
            image.setImageResource(imageIds[position]);

            TextView textView = viewHolder.getTextView();
            textView.setText(names[position]);

            return view;
        }
    }


    class ViewHolder {
        private View view;
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View view) {
            this.view = view;
        }

        public ImageView getImageView() {
            if (imageView == null) {
                imageView = (ImageView)view.findViewById(R.id.imageViewBase);
            }
            return imageView;
        }

        public TextView getTextView() {
            if (textView == null) {
                textView = (TextView)view.findViewById(R.id.textViewBase);
            }
            return textView;
        }
    }

}
