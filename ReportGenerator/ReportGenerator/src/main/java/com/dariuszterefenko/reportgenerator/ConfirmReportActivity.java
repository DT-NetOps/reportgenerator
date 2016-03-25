package com.dariuszterefenko.reportgenerator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ConfirmReportActivity extends AppCompatActivity {
    private int client_id;
    private DBHelper helper;
    private ArrayList<Container> containers = new ArrayList<Container>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button sendBtn = (Button) findViewById(R.id.send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkbox = (CheckBox)findViewById(R.id.confirm);
                if (checkbox.isChecked()){
                    Intent intent = new Intent(ConfirmReportActivity.this, SendReportActivity.class);
                    intent.putExtra(ClientViewActivity.CLIENT_ID_STR, client_id);
                    startActivity(intent);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey(ClientViewActivity.CLIENT_ID_STR)){
            finish();
            return;
        }
        client_id = extras.getInt(ClientViewActivity.CLIENT_ID_STR);
        helper = new DBHelper(this);
        countItems();
        populateView();
    }

    //counts the containers and items for display
    private void countItems(){
        Cursor containers = helper.getClientContainers(client_id);
        String containersStr = getString(R.string.containersqty) + containers.getCount();
        TextView tv = (TextView) findViewById(R.id.containers);
        tv.setText(containersStr);

        int itemsSum = 0;
        for (int i=0;i<containers.getCount();++i){
            containers.moveToPosition(i);
            Container t = new Container(containers);
            Cursor items = helper.getContainerItems(t.getId());
            itemsSum += items.getCount();
            this.containers.add(t);
            String itemsStr = getItemsStr(items);
            t.setItems(itemsStr);
        }

        String itemsStr = getString(R.string.itemqty) + itemsSum;
        tv = (TextView) findViewById(R.id.items);
        tv.setText(itemsStr);
    }

    //adds all items to string
    private String getItemsStr(Cursor cursor){
        String ret = "";
        for (int i=0;i<cursor.getCount();++i) {
            if (i!=0)
                ret = ret + "\n";
            cursor.moveToPosition(i);
            Item t = new Item(cursor);
            ret = ret + t.getBarcode();
        }
        return ret;
    }

    private void populateView(){
        //displays client info
        Client client = helper.getClient(client_id);
        TextView tv = (TextView)findViewById(R.id.name);
        tv.setText(client.getFirstname()+" "+ client.getSurname());
        tv = (TextView)findViewById(R.id.email);
        tv.setText(client.getEmail());
        tv = (TextView)findViewById(R.id.phone);
        tv.setText(client.getPhone());
        tv = (TextView)findViewById(R.id.address1);
        if (client.getAddress1().length() != 0){
            tv.setText(client.getAddress1());
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.GONE);
        }
        tv = (TextView)findViewById(R.id.address2);
        if (client.getAddress2().length() != 0){
            tv.setText(client.getAddress2());
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.GONE);
        }
        tv = (TextView)findViewById(R.id.address3);
        if (client.getAddress3().length() != 0){
            tv.setText(client.getAddress3());
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.GONE);
        }
        tv = (TextView)findViewById(R.id.address4);
        if (client.getAddress4().length() != 0){
            tv.setText(client.getAddress4());
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.GONE);
        }
        tv = (TextView)findViewById(R.id.address5);
        if (client.getAddress5().length() != 0){
            tv.setText(client.getAddress5());
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.GONE);
        }
        tv = (TextView)findViewById(R.id.comment);
        if (client.getComment().length() != 0){
            tv.setText(client.getComment());
            tv.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.GONE);
        }

        //displays list of containers and items
        RecyclerView listView = (RecyclerView)findViewById(R.id.containerList);
        ConfirmAdapter adapter = new ConfirmAdapter(containers);
        listView.setAdapter(adapter);
    }

}
