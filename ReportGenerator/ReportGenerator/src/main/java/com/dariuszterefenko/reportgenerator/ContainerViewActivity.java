package com.dariuszterefenko.reportgenerator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

//this class displays details about the container and list of items within
public class ContainerViewActivity extends AppCompatActivity {
    public static final String CONTAINER_ID_STR = "CONTAINER_ID";
    private int container_id;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey(CONTAINER_ID_STR)){
            finish();
            return;
        }

        container_id = extras.getInt(CONTAINER_ID_STR);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(ContainerViewActivity.this);
                scanIntegrator.initiateScan();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = new DBHelper(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null && scanningResult.getContents() != null) {

            Item item = new Item(container_id, scanningResult.getContents());
            helper.addItem(item);
            updateList();
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        Container container = helper.getContainer(container_id);
        TextView tv = (TextView)findViewById(R.id.scancode);
        tv.setText(""+container.getBarcode());
        tv = (TextView)findViewById(R.id.location1);
        tv.setText(container.getLocation1());
        tv = (TextView)findViewById(R.id.location2);
        if (container.getLocation2().length() != 0){
            tv.setText(container.getLocation2());
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.GONE);
        }
        tv = (TextView)findViewById(R.id.comment);
        if (container.getComment().length() != 0){
            tv.setText(container.getComment());
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.GONE);
        }
        updateList();
    }

    private void updateList(){
        RecyclerView listView = (RecyclerView)findViewById(R.id.itemsList);
        Cursor cursor = helper.getContainerItems(container_id);
        ItemAdapter adapter = new ItemAdapter(this, cursor);
        listView.setAdapter(adapter);
    }
}
