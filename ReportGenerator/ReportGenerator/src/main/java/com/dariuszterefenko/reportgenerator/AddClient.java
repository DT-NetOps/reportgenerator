package com.dariuszterefenko.reportgenerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


//this activity adds new clients
public class AddClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //displays menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //if save selected
        if (id == R.id.action_save) {

            //saves to db, if not successful then close the activity
            if (onSave())
                finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //tries to save client to db
    //return true if successful
    private boolean onSave(){
        //checks for firstname
        EditText firstnameView = (EditText) findViewById(R.id.firstname);
        String firstname = firstnameView.getText().toString().trim();
        if (firstname.length()<3) {
            //if field doesn't exist then returns error
            firstnameView.setError(getString(R.string.error_field_required));
            firstnameView.requestFocus();
            return false;
        }

        //validation for remaining fields
        EditText surnameView = (EditText) findViewById(R.id.surname);
        String surname = surnameView.getText().toString().trim();
        if (surname.length()<3) {
            surnameView.setError(getString(R.string.error_field_required));
            surnameView.requestFocus();
            return false;
        }
        EditText emailView = (EditText) findViewById(R.id.email);
        String email = emailView.getText().toString().trim();
        if (email.length()<3) {
            emailView.setError(getString(R.string.error_field_required));
            emailView.requestFocus();
            return false;
        }
        EditText phoneView = (EditText) findViewById(R.id.phone);
        String phone = phoneView.getText().toString().trim();
        if (phone.length()<3) {
            phoneView.setError(getString(R.string.error_field_required));
            phoneView.requestFocus();
            return false;
        }
        EditText address1View = (EditText) findViewById(R.id.address1);
        String address1 = address1View.getText().toString().trim();
        if (address1.length()<3) {
            address1View.setError(getString(R.string.error_field_required));
            address1View.requestFocus();
            return false;
        }
        EditText address2View = (EditText) findViewById(R.id.address2);
        String address2 = address2View.getText().toString().trim();
        EditText address3View = (EditText) findViewById(R.id.address3);
        String address3 = address3View.getText().toString().trim();
        EditText address4View = (EditText) findViewById(R.id.address4);
        String address4 = address4View.getText().toString().trim();
        EditText address5View = (EditText) findViewById(R.id.address5);
        String address5 = address5View.getText().toString().trim();
        EditText comentView = (EditText) findViewById(R.id.comment);
        String comment = comentView.getText().toString().trim();

        //creates client with vars
        Client client = new Client(firstname, surname, email, phone, address1, address2, address3, address4, address5, comment, "");
        //access to db
        DBHelper helper = new DBHelper(this);
        //adds client to db
        helper.addClient(client);
        return true;
    }

}
