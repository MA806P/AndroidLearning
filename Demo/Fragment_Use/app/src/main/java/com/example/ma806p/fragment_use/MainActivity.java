package com.example.ma806p.fragment_use;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SelectedListener listener = new SelectedListener() {
        @Override
        public void selectedItem(String text) {
            Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();

            //跳转下一页
            Intent intent = new Intent(MainActivity.this, MainBActivity.class);
            intent.putExtra("item", text);
            startActivity(intent);
        }
    };

    private FragmentA fragmentA;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentA = (FragmentA)fragmentManager.findFragmentById(R.id.mainActivity_fragment_a);
        fragmentA.setOnSelectedItemListener(listener);

    }
}
