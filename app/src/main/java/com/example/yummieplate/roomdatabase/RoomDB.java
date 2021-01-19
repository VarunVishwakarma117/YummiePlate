package com.example.yummieplate.roomdatabase;

import android.content.Context;

import androidx.room.Room;

public class RoomDB {

    public static MyDatabase myDatabase;

    public static MyDatabase database(Context context) {

        if (myDatabase == null) {
            myDatabase = Room.databaseBuilder(context, MyDatabase.class, "profiledata")
                    .allowMainThreadQueries().build();
        }
        return myDatabase;
    }

}
