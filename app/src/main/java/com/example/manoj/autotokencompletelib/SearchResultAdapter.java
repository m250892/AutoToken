package com.example.manoj.autotokencompletelib;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manoj.autotokencompletelib.searchtext.BaseTokenRecyclerAdapter;

import java.util.List;

/**
 * Created by manoj on 06/04/16.
 */
public class SearchResultAdapter extends BaseTokenRecyclerAdapter<String, SearchResultAdapter.ViewHolder> {

    private List<String> dataSet;

    public SearchResultAdapter(List<String> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_dropdown_item_1line, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchResultAdapter.ViewHolder holder, int position) {
        holder.textView.setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public String getItem(int position) {
        return dataSet.get(position);
    }

    public class ViewHolder extends BaseTokenRecyclerAdapter.ViewHolder {

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    @Override
    public List<String> getDataSet() {
        return dataSet;
    }

    @Override
    public void updateResult(List<String> values) {
        clear();
        dataSet.addAll(values);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        dataSet.clear();
        notifyDataSetChanged();
    }

    @Override
    public boolean keepObject(String object, String mask) {
        if (object != null && mask != null) {
           return object.contains(mask);
        }
        return false;
    }

}
