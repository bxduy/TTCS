package com.appbishoy.packyourbag.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.appbishoy.packyourbag.DAO.ItemsDao;
import com.appbishoy.packyourbag.Models.Items;

@Database(entities = Items.class,version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;
    private static String DATABASE_NAME = "MyDb";

    public static synchronized RoomDB getInstance(Context context){
        if(database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,DATABASE_NAME).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    public abstract ItemsDao mainDao();
}
