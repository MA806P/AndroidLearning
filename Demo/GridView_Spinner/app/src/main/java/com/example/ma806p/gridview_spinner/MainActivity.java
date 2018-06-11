package com.example.ma806p.gridview_spinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private static final String[] data = {"aaa", "bbb", "ccc", "aaa"};

    private GridView gridViewIcon;

    private GridView gridViewAdd;
    private Button buttonAdd;
    private EditText editTextAdd;
    List<String> studentNames = new ArrayList<String>();
    ArrayAdapter<String> adapterAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


 // -----------------
        gridView = (GridView)findViewById(R.id.gridView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                data
        );
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, data[position], Toast.LENGTH_SHORT).show();
            }
        });


// -----------------
        gridViewIcon = (GridView)findViewById(R.id.gridViewIcon);
        gridViewIcon.setAdapter(new MyAdatper());

// -----------------


        gridViewAdd = (GridView)findViewById(R.id.gridViewAdd);
        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        editTextAdd = (EditText)findViewById(R.id.editTextAdd);

        adapterAdd = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                studentNames
        );
        gridViewAdd.setAdapter(adapterAdd);


    }


    // ----------------- 添加学生

    //按钮点击
    public void onClickbuttonAdd(View view) {
        String name = editTextAdd.getText().toString().trim();
        studentNames.add(name);
        //适配器发出通知更新GridView
        adapterAdd.notifyDataSetChanged();
    }





    //--------------- have icon gridview

    class MyAdatper extends BaseAdapter {
        private int[] imgIds = {
                R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round,
                R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round,};
        private String[] names = {"aaa", "bbb", "ccc", "ddd"};

        @Override
        public int getCount() {
            return imgIds.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.gridview_item, null);
                viewHolder = new ViewHolder();

                viewHolder.imageView = (ImageView)view.findViewById(R.id.imageView);
                viewHolder.textView = (TextView)view.findViewById(R.id.textView);
                view.setTag(viewHolder);

            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            ImageView img = viewHolder.imageView;
            img.setImageResource(imgIds[position]);
            TextView textView = viewHolder.textView;
            textView.setText(names[position]);

            return view;
        }

    }


    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }


// ----------------- 去下一页 Spinner

    public void toNextSpinner (View view) {

        Intent intent = new Intent(this, MainSpinnerActivity.class);
        startActivity(intent);

    }


}
