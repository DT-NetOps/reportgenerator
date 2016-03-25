package com.dariuszterefenko.reportgenerator;

import android.database.Cursor;

//this class represent container
class Container {
    private int id = -1;
    private int client_id;
    private String barcode;
    private String comment = "";
    private String location1 = "";
    private String location2 = "";
    private String items = "";


    public Container(int client_id, String barcode, String location1, String location2, String comment){
        this.client_id = client_id;
        this.barcode = barcode;
        this.location1 = location1;
        this.location2 = location2;
        this.comment = comment;
    }

    public Container(Cursor cursor){
        id = cursor.getInt(0);
        client_id = cursor.getInt(1);
        barcode = cursor.getString(2);
        comment = cursor.getString(3);
        location1 = cursor.getString(4);
        location2 = cursor.getString(5);
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return client_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getComment() {
        return comment;
    }

    public String getLocation1() {
        return location1;
    }

    public String getLocation2() {
        return location2;
    }

    public String getItemsStr() {
        return items;
    }

    public void setItems(String items){
        this.items = items;
    }
}
