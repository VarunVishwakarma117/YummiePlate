package com.vishwakarma.yummieplate.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.vishwakarma.yummieplate.model.ProfileModel;

@Database(entities = {ProfileModel.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract DAO dao();
}
