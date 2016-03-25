package com.dariuszterefenko.reportgenerator;

import android.database.Cursor;

//this class represents the client
class Client {
    private String firstname;
    private String surname;
    private String email;
    private String phone;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;
    private String comment;
    private String date="";
    private int id = -1;

    //constructs the client for the db
    public Client(String firstname, String surname, String email, String phone, String address1,
                  String address2, String address3, String address4, String address5, String comment,
                  String date){
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.address4 = address4;
        this.address5 = address5;
        this.comment = comment;
        this.date = date;
    }

    //creates the client object for db
    public Client(Cursor c){
        id = c.getInt(0);
        firstname = c.getString(1);
        surname = c.getString(2);
        email = c.getString(3);
        phone = c.getString(4);
        address1 = c.getString(5);
        address2 = c.getString(6);
        address3 = c.getString(7);
        address4 = c.getString(8);
        address5 = c.getString(9);
        comment = c.getString(10);
        date = c.getString(11);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getAddress3() {
        return address3;
    }

    public String getAddress4() {
        return address4;
    }

    public String getAddress5() {
        return address5;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
