package com.dariuszterefenko.reportgenerator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

//displays the list of clients
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adds new client
                Intent intent = new Intent(MainActivity.this, AddClient.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateList();
    }

    private void updateList(){
        //displays list of clients
        DBHelper helper = new DBHelper(this);
        RecyclerView listView = (RecyclerView)findViewById(R.id.clientList);
        Cursor cursor = helper.getAllClients();
        ClientAdapter adapter = new ClientAdapter(this, cursor);
        listView.setAdapter(adapter);
    }
}
