package com.example.minidouyin.db;

import androidx.room.RoomDatabase;

public abstract class VideoDatabase extends RoomDatabase {

	public abstract VideoDao videoDao();

}
