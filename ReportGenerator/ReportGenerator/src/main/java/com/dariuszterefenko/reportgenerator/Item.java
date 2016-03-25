package com.dariuszterefenko.reportgenerator;

import android.database.Cursor;

//this class represents single item
class Item {
    private int id = -1;
    private int container_id;
    private String barcode;

    public Item(int container_id, String barcode){
        this.container_id = container_id;
        this.barcode = barcode;
    }

    public Item(Cursor cursor){
        id = cursor.getInt(0);
        container_id = cursor.getInt(1);
        barcode = cursor.getString(2);
    }

    public int getId() {
        return id;
    }

    public int getContainerId(){
        return container_id;
    }

    public String getBarcode(){
        return barcode;
    }
}
