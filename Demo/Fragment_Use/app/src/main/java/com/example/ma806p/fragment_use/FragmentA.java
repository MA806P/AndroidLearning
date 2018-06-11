package com.example.ma806p.fragment_use;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentA extends ListFragment {

    private String[] data = {"aaa", "bbb", "ccc"};
    private SelectedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                data));



    }

    public void setOnSelectedItemListener(SelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        listener.selectedItem(data[position]);
        super.onListItemClick(l, v, position, id);
    }
}
