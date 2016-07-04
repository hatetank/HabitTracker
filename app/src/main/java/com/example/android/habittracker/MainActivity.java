package com.example.android.habittracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HabitSQLiteHelper db = new HabitSQLiteHelper(this);

        // add Habits to start
        db.addHabit(new Habit("Trying to finish on leaderboard", 12));
        db.addHabit(new Habit("Forgetting insulin", 2));
        db.addHabit(new Habit("Getting a good nights sleep", 0));

        List<Habit> list = db.getAllHabits();
        // delete one Habit
        db.deleteHabit(list.get(0));

        list = db.getAllHabits();

        db.deleteAllHabits();

        list = db.getAllHabits();

        // now add a new item to the db.
        Habit habitToAdd = new Habit("Doing things incorrectly", 1);
        db.addHabit(habitToAdd);

        // modify the contents of an item in the list and update.
        list = db.getAllHabits();
        list.get(0).setOcurrences(4);
        db.updateHabit(list.get(0));

        // read console log, final contents of db
        db.getAllHabits();

    }

}
