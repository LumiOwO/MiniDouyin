package com.example.minidouyin.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {VideoRecord.class}, version = 1, exportSchema = false)
public abstract class MiniDouYinDatabase extends RoomDatabase {
	public abstract VideoDao videoDao();

}
