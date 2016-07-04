package com.example.android.habittracker;

/**
 * Created by Gavin on 7/4/2016.
 */
public class Habit {

    private int mId;
    private String mHabit;
    private int mOcurrences;

    public Habit() {
    }

    public Habit(String habit, int occurences) {
        mHabit = habit;
        mOcurrences = occurences;
    }

    public String getHabit() {
        return mHabit;
    }

    public void setHabit(String mHabit) {
        this.mHabit = mHabit;
    }

    public void setOcurrences(int mOcurrences) {
        this.mOcurrences = mOcurrences;
    }

    public int getOcurrences() {
        return mOcurrences;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }


    @Override
    public String toString() {
        return "Habit{" +
                "mId=" + mId +
                ", mHabit='" + mHabit + '\'' +
                ", mOcurrences=" + mOcurrences +
                '}';
    }
}
