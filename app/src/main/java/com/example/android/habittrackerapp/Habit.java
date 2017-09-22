package com.example.android.habittrackerapp;

/**
 * Created by Toshiba on 21/09/17.
 */

public class Habit {
    private int mId;
    private String mName;
    private String mRepeat;


    public Habit(int Id, String Name, String Repeat) {
        mId = Id;
        mName = Name;
        mRepeat = Repeat;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getRepeat() {
        return mRepeat;
    }

}
