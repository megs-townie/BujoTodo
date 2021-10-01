package com.example.save_01;

import java.io.Serializable;

/**
 * Sample class to show storing an object
 */
public class myDataClass implements Serializable {

    // Some default data
    private String mySavedString = "Sample String";
    private Integer mySavedInteger = 99;

    public myDataClass(){

    }

    public String getMySavedString() {
        return mySavedString;
    }

    public void setMySavedString(String mySavedString) {
        this.mySavedString = mySavedString;
    }

    public Integer getMySavedInteger() {
        return mySavedInteger;
    }

    public void setMySavedInteger(Integer mySavedInteger) {
        this.mySavedInteger = mySavedInteger;
    }


}
