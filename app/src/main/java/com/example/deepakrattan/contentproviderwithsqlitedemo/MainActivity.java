package com.example.deepakrattan.contentproviderwithsqlitedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private WordListAdapter adapter;
    private WordListOpenHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new WordListOpenHelper(this);

        //findViewByID
        rv = (RecyclerView) findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        adapter = new WordListAdapter(this, db);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }
}
