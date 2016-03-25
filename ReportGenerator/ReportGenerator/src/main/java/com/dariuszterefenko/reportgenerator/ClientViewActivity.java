package com.dariuszterefenko.reportgenerator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//this activity displays the client
public class ClientViewActivity extends AppCompatActivity {
    public static final String CLIENT_ID_STR = "CLIENT_ID";
    private int client_id;
    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey(CLIENT_ID_STR)){
            finish();
            return;
        }
        client_id = extras.getInt(CLIENT_ID_STR);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if users click then add container for the client
                Intent intent = new Intent(ClientViewActivity.this, AddContainer.class);
                intent.putExtra(CLIENT_ID_STR, client_id);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = new DBHelper(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        //displays info about the client
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

        //displays list of containers for the client
        RecyclerView listView = (RecyclerView)findViewById(R.id.containerList);
        Cursor cursor = helper.getClientContainers(client_id);
        ContainerAdapter adapter = new ContainerAdapter(this, cursor);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_raport) {
            //displays report summary
            Intent intent = new Intent(ClientViewActivity.this, ConfirmReportActivity.class);
            intent.putExtra(CLIENT_ID_STR, client_id);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
