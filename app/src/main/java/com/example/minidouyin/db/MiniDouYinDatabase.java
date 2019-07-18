package com.example.minidouyin.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {VideoRecord.class, HistoryRecord.class, CollectionRecord.class}, version = 1, exportSchema = false)
public abstract class MiniDouYinDatabase extends RoomDatabase {
	public abstract VideoDao videoDao();
	public abstract HistoryDao historyDao();
	public abstract CollectionDao collectionDao();

}
