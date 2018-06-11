package com.example.ma806p.gridview_spinner;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainSpinnerActivity extends AppCompatActivity {

    private Spinner spinner;
    private String[] data = {"aaa", "bbb", "ccc", "ddd"};

    private Spinner spinerClass, spinnerSt;
    private List<String> classList = new ArrayList<String>();
    private Map<String, List<String>> studentMap = new HashMap<String, List<String>>();
    private List<String> selectStudents = new ArrayList<String>(); //选中的学生
    private ArrayAdapter<String> stuAdapter; //学生列表中的适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_spinner);

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                data
        ));


        //---------------------

        classList.add("Android");
        classList.add("iOS");

        List<String> androidSts = new ArrayList<String>();
        androidSts.add("android_1");
        androidSts.add("android_2");
        List<String> iosSts = new ArrayList<String>();
        iosSts.add("ios_1");
        iosSts.add("ios_2");

        studentMap.put(classList.get(0), androidSts);
        studentMap.put(classList.get(1), iosSts);


        spinerClass = (Spinner)findViewById(R.id.spinnerClass);
        spinerClass.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                classList
        ));




        spinnerSt = (Spinner)findViewById(R.id.spinnerSt);
        stuAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                selectStudents
        );
        spinnerSt.setAdapter(stuAdapter);


        //当spinner某个班级被选中的时候
        spinerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectStudents.clear();
                String className = classList.get(position);
                selectStudents.addAll(studentMap.get(className));
                stuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
