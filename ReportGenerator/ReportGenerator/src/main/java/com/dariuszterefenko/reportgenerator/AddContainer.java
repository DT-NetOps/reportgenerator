package com.dariuszterefenko.reportgenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

//this activity adds containers for clients
public class AddContainer extends AppCompatActivity {
    //gets client_id for the container
    private int client_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //checks if client_id was passed to the client
        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey(ClientViewActivity.CLIENT_ID_STR)){
            //if not then finish the activity
            finish();
            return;
        }

        //gets the client_id for the client
        client_id = extras.getInt(ClientViewActivity.CLIENT_ID_STR);

        findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //runs the barcode scanner
                IntentIntegrator scanIntegrator = new IntentIntegrator(AddContainer.this);
                scanIntegrator.initiateScan();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_container, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            if (onSave())
                finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //runs at the end of barcode scanning process
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //checks was anything scanned
        if (scanningResult != null && scanningResult.getContents() != null) {
            //if barcode scanned then it moved it to textview
            TextView tv = (TextView)findViewById(R.id.barcode);
            tv.setText(scanningResult.getContents());
        }
    }

    private boolean onSave() {
        EditText barcodeView = (EditText) findViewById(R.id.barcode);
        String barcode = barcodeView.getText().toString().trim();
        if (barcode.length()<1) {
            barcodeView.setError(getString(R.string.error_field_required));
            barcodeView.requestFocus();
            return false;
        }

        EditText location1View = (EditText) findViewById(R.id.location1);
        String location1 = location1View.getText().toString().trim();

        EditText location2View = (EditText) findViewById(R.id.location2);
        String location2 = location2View.getText().toString().trim();

        EditText commentView = (EditText) findViewById(R.id.comment);
        String comment = commentView.getText().toString().trim();

        Container container = new Container(client_id, barcode, location1, location2, comment);
        DBHelper helper = new DBHelper(this);
        helper.addContainer(container);

        return true;
    }

}
