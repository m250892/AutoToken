package com.example.manoj.autotokencompletelib.searchtext;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj on 07/04/16.
 */
public abstract class BaseTokenRecyclerAdapter<T, VH extends BaseTokenRecyclerAdapter.ViewHolder> extends RecyclerView.Adapter<VH> implements Filterable {

    private AppFilter filter;
    private SearchOnItemClickListener searchOnItemClickListener;

    public void setSearchOnItemClickListener(SearchOnItemClickListener searchOnItemClickListener) {
        this.searchOnItemClickListener = searchOnItemClickListener;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new AppFilter(getDataSet());
        }
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (searchOnItemClickListener != null) {
                searchOnItemClickListener.onSearchItemSelected(itemView, getAdapterPosition());
            }
        }

    }

    private class AppFilter extends Filter {

        private ArrayList<T> sourceObjects;

        public AppFilter(List<T> objects) {
            setSourceObjects(objects);
        }

        public void setSourceObjects(List<T> objects) {
            synchronized (this) {
                sourceObjects = new ArrayList<>(objects);
            }
        }

        @Override
        public CharSequence convertResultToString(Object object) {
            return object.toString();
        }

        @Override
        protected FilterResults performFiltering(CharSequence chars) {
            FilterResults result = new FilterResults();
            if (chars != null && chars.length() > 0) {
                String mask = chars.toString();
                List<T> keptObjects = new ArrayList<>();
                Log.d("manoj", "Search result for : " + chars);
                for (T object : sourceObjects) {
                    if (keepObject(object, mask)) {
                        keptObjects.add(object);
                    }
                }
                Log.d("manoj", "Find Results for : " + keptObjects);
                result.count = keptObjects.size();
                result.values = keptObjects;
            } else {
                // add all objects
                result.values = sourceObjects;
                result.count = sourceObjects.size();
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results.count > 0) {
                updateResult((List<T>) results.values);
                notifyDataSetChanged();
            } else {
                notifyDataSetChanged();
            }
        }
    }

    interface SearchOnItemClickListener {

        void onSearchItemSelected(View view, int position);
    }

    public abstract T getItem(int position);

    public abstract List<T> getDataSet();

    public abstract void updateResult(List<T> values);

    public abstract void clear();

    public abstract boolean keepObject(T object, String mask);
}
