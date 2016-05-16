package com.example.manoj.autotokencompletelib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    SampleSearchTokenEditText searchTextView;
    RecyclerView recyclerView;
    SearchResultAdapter searchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        searchTextView = (SampleSearchTokenEditText) findViewById(R.id.sreach_text);

        //searchTextView.setTokenListener(this);
        searchTextView.setTokenClickStyle(SampleSearchTokenEditText.TokenClickStyle.Select);

        recyclerView = (RecyclerView) findViewById(R.id.search_results);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<String> dataSet = new ArrayList<>(Arrays.asList(COUNTRIES));
        searchResultAdapter = new SearchResultAdapter(dataSet);
        recyclerView.setAdapter(searchResultAdapter);
        searchTextView.setAdapter(searchResultAdapter);
    }

    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain", "Manoj", "Gagan"
    };

}
