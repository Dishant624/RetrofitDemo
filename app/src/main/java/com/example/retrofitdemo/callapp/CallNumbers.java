package com.example.retrofitdemo.callapp;

import java.sql.Array;
import java.util.ArrayList;

class CallNumbers {

    public int index = 0;

    private static final CallNumbers ourInstance = new CallNumbers();

    ArrayList<String> numberlist =new ArrayList<>();

    static CallNumbers getInstance() {
        return ourInstance;
    }

    private CallNumbers() {
    }

    public ArrayList<String> getNumberlist() {
        return numberlist;
    }

    public void setNumberlist(ArrayList<String> numberlist) {
        this.numberlist = numberlist;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
