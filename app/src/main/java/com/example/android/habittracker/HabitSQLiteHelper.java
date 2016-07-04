package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Gavin on 7/4/2016.
 */
public class HabitSQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "HabitApp.db";

    public HabitSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Habits table name
    private static final String TABLE_HABITS = "habits";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_HABIT = "habit";
    private static final String KEY_OCCURRENCES = "occurrences";

    private static final String[] COLUMNS = {KEY_ID, KEY_HABIT, KEY_OCCURRENCES};

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_HABIT_TABLE = "CREATE TABLE habits ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "habit TEXT, " +
                "occurrences INTEGER )";

        // create books table
        db.execSQL(CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop habits table if existed
        db.execSQL("DROP TABLE IF EXISTS habits");
        this.onCreate(db);
    }


    public void addHabit(Habit habit) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HABIT, habit.getHabit()); // get habit
        values.put(KEY_OCCURRENCES, habit.getOcurrences()); // get occurrences

        db.insert(TABLE_HABITS,
                null,
                values);

        db.close();
        Log.d("addHabit", habit.toString());
    }

    public Habit getHabit(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_HABITS, //  table
                        COLUMNS, //  column names
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        Habit habit = new Habit();
        habit.setId(Integer.parseInt(cursor.getString(0)));
        habit.setHabit(cursor.getString(1));
        habit.setOcurrences(Integer.parseInt(cursor.getString(2)));

        cursor.close();

        //log
        Log.d("getHabit(" + id + ")", habit.toString());


        return habit;
    }

    public int updateHabit(Habit habit) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HABIT, habit.getHabit()); // get habit
        values.put(KEY_OCCURRENCES, habit.getOcurrences()); // get occurrences

        // 3. updating row
        int rowsUpdated = db.update(TABLE_HABITS,
                values,
                KEY_ID + " = ?",
                new String[]{String.valueOf(habit.getId())}); //selection args

        // 4. close
        db.close();
        Log.d("updateHabit", "MODIFIED ROWS: " + String.valueOf(rowsUpdated));
        return rowsUpdated;

    }

    public void deleteHabit(Habit habit) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_HABITS, //table name
                KEY_ID + " = ?",  // selections
                new String[]{String.valueOf(habit.getId())}); //selections args

        db.close();

        //log
        Log.d("deleteHabit", habit.toString());

    }

    public void deleteAllHabits() {

        SQLiteDatabase db = this.getWritableDatabase();

        // delete all rows with null WHERE clause
        db.delete(TABLE_HABITS, null, null);

        db.close();

        // Log for testing.
        Log.d("deleteAllHabits", "All habits should be deleted.");

    }

    public List<Habit> getAllHabits() {
        List<Habit> habits = new LinkedList<>();

        String query = "SELECT  * FROM " + TABLE_HABITS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // build list for array
        Habit habit = null;
        if (cursor.moveToFirst()) {
            do {
                habit = new Habit();
                habit.setId(Integer.parseInt(cursor.getString(0)));
                habit.setHabit(cursor.getString(1));
                habit.setOcurrences(Integer.parseInt(cursor.getString(2)));

                // Add habit to habit list
                habits.add(habit);
            } while (cursor.moveToNext());
        }

        cursor.close();
        Log.d("getAllHabits()", habits.toString());

        // return books
        return habits;
    }
}
