package com.example.ma806p.xml_json;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView showTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String tagName;
        XmlPullParser parser = getResources().getXml(R.xml.xmltest);
        showTextView = (TextView)findViewById(R.id.xmlShowTextView);

        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT: //文档开始
                        Log.i("test", "START_DOCUMENT");
                        break;

                    case XmlPullParser.END_DOCUMENT: //文档结束
                        break;

                    case XmlPullParser.START_TAG://标记(标签 元素 TAG NODE ELEMENT)开始
                        tagName = parser.getName();
                        if (tagName.equals("word")) {
                            Log.i("test", "START_TAG" + "  " + tagName + "  " + parser.getAttributeValue(0));
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        Log.i("test", "END_TAG");
                        break;

                }

                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        //---------------- json
//        String data = "{\"name\":\"Tom\"}";
//        JSONObject jsonObj = new JSONObject(data);
//        String value = jsonObj.getString("name");
//        Log.i("JSONTESR", value);

/*
        try {
            String personData = getContent(getResources(), R.raw.person);
            JSONObject person = new JSONObject(personData);
            String personName = person.getString("firstName");

            //对象
            JSONObject address = person.getJSONObject("address");
            String city = address.getString("address");

            //数组
            JSONArray phone = person.getJSONArray("phone");
            for (int index = 0; index < phone.length(); index++) {
                JSONObject obj = phone.getJSONObject(index);
                String number = obj.getString("number");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/

    }





//---------------- json


    private String getContent(Resources res, int id) throws IOException{

        InputStream is = null;
        try {

            StringBuilder sb = new StringBuilder();
            is = res.openRawResource(id);
            byte[] buffer = new byte[1024];
            int len = is.read(buffer, 0, 1024);
            while (len != 1) {
                String s = new String(buffer, 0, len);
                sb.append(s);
                len = is.read(buffer, 0, 1024);
            }
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return is.toString();
    }






//---------------- xml

    //点击视图，解析XML按钮，处理事件方法
    public void parser(View view) {

        Log.i("btn", "btn click");

        new Thread() {
            @Override
            public void run() {
                try {
                    List<String> contents = getPullParserContent(getResources(), R.xml.xmltest);

                    Message msg = handler.obtainMessage();
                    msg.what = MSG_FINISH;
                    msg.obj = contents;
                    handler.sendMessage(msg);

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }





    private static final int MSG_FINISH = 0x008;
    //获取到 XML 数据后，发消息主线程显示
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FINISH:
                    List<String> contents = (List<String>)msg.obj;
                    //在主线程显示
                    for (String content: contents) {
                        showTextView.append(content + "\n");
                    }
                    break;
            }
        };
    };



    //解析 XML 提取方法
    private List<String> getPullParserContent(Resources res, int id) throws XmlPullParserException, IOException {

        List<String> contents = null;
        XmlPullParser parser = getResources().getXml(R.xml.xmltest);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT: //文档开始
                        contents = new ArrayList<String>();
                        break;

                    case XmlPullParser.END_DOCUMENT: //文档结束
                        break;

                    case XmlPullParser.START_TAG://标记(标签 元素 TAG NODE ELEMENT)开始
                        String tagName = parser.getName();
                        if (tagName.equals("word")) {
                            String value = parser.getAttributeValue(0);
                            contents.add(value);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        break;
                }

                eventType = parser.next();
            }
            return contents;
    }


}
