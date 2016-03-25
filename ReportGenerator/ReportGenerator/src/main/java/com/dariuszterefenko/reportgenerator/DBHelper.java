package com.dariuszterefenko.reportgenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//this class uses db and acts as helper
public class DBHelper extends SQLiteOpenHelper {
    //db version
    private static int DB_VERSION = 9;
    //db name
    private static String LOCAL_DB_NAME = "local.db";


    public DBHelper(Context context) {
        super(context, LOCAL_DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //creates tables
        db.execSQL("CREATE TABLE CLIENTS (_id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT, surname TEXT, email TEXT, phone TEXT, address1 TEXT, address2 TEXT, address3 TEXT, address4 TEXT, address5 TEXT, comment TEXT, date TEXT)");
        db.execSQL("CREATE TABLE CONTAINER (_id INTEGER PRIMARY KEY AUTOINCREMENT, client_id INTEGER, barcode TEXT, comment TEXT, location1 TEXT, location2 TEXT)");
        db.execSQL("CREATE TABLE ITEM (_id INTEGER PRIMARY KEY AUTOINCREMENT, container_id INTEGER, barcode TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    //returns all clients from db
    public Cursor getAllClients(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor ret = db.query("CLIENTS", null, null, null, null, null, null, null);
        return ret;
    }

    //adds client to db
    public void addClient(Client client){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("firstname", client.getFirstname());
        cv.put("surname", client.getSurname());
        cv.put("email", client.getEmail());
        cv.put("phone", client.getPhone());
        cv.put("address1", client.getAddress1());
        cv.put("address2", client.getAddress2());
        cv.put("address3", client.getAddress3());
        cv.put("address4", client.getAddress4());
        cv.put("address5", client.getAddress5());
        cv.put("comment", client.getComment());
        cv.put("date", client.getDate());
        db.insert("CLIENTS", null, cv);
        db.close();
    }

    //updates client in db
    public void updateClient(Client client){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("firstname", client.getFirstname());
        cv.put("surname", client.getSurname());
        cv.put("email", client.getEmail());
        cv.put("phone", client.getPhone());
        cv.put("address1", client.getAddress1());
        cv.put("address2", client.getAddress2());
        cv.put("address3", client.getAddress3());
        cv.put("address4", client.getAddress4());
        cv.put("address5", client.getAddress5());
        cv.put("comment", client.getComment());
        cv.put("date", client.getDate());
        db.update("CLIENTS", cv, "_id="+client.getId(), null);
        db.close();
    }

    //gets the client by
    //client id from db
    public Client getClient(int id){
        SQLiteDatabase db = getReadableDatabase();
        String[] args={String.format("%d", id)};
        Cursor cursor = db.query("CLIENTS", null, "_id=?", args, null, null, null, null);
        cursor.moveToFirst();
        return new Client(cursor);
    }

    //adds container to db
    public void addContainer(Container container){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("client_id", container.getClientId());
        cv.put("barcode", container.getBarcode());
        cv.put("comment", container.getComment());
        cv.put("location1", container.getLocation1());
        cv.put("location2", container.getLocation2());
        db.insert("CONTAINER", null, cv);
        db.close();
    }

    //gets list of containers for the client from db
    public Cursor getClientContainers(int client_id){
        SQLiteDatabase db = getReadableDatabase();
        String[] args={String.format("%d", client_id)};
        Cursor ret = db.query("CONTAINER", null, "client_id=?", args, null, null, null, null);
        return ret;
    }

    //gets container from db
    public Container getContainer(int id){
        SQLiteDatabase db = getReadableDatabase();
        String[] args={String.format("%d", id)};
        Cursor cursor = db.query("CONTAINER", null, "_id=?", args, null, null, null, null);
        cursor.moveToFirst();
        return new Container(cursor);
    }

    //adds item to db
    public void addItem(Item item){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("container_id", item.getContainerId());
        cv.put("barcode", item.getBarcode());
        db.insert("ITEM", null, cv);
        db.close();
    }

    //gets list of items for the client from db
    public Cursor getContainerItems(int container_id){
        SQLiteDatabase db = getReadableDatabase();
        String[] args={String.format("%d", container_id)};
        Cursor ret = db.query("ITEM", null, "container_id=?", args, null, null, null, null);
        return ret;
    }
}
