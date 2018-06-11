package com.example.ma806p.viewpagedemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static com.example.ma806p.viewpagedemo.R.layout.activity_main_draw_layout;

public class MainDrawLayoutActivity extends AppCompatActivity {

    private DrawerLayout layout;
    private ListView left_list;
    private String[] titles = {"fragment1", "fragment2", "fragment3"};
    private Fragment[] fragments = {new MyFragment1(), new MyFragment2(), new MyFragment3()};
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main_draw_layout);

        fm = getSupportFragmentManager();

        layout = (DrawerLayout)findViewById(R.id.drawer_layout);

        left_list = (ListView) findViewById(R.id.left_list);
        left_list.setAdapter(new ArrayAdapter<String >(
                MainDrawLayoutActivity.this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                titles
        ));
        left_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment f = fragments[position];
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_container_layout, f);
                ft.commit();

                layout.closeDrawer(left_list);
            }
        });

    }
}
