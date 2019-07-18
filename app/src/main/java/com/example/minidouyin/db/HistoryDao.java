package com.example.minidouyin.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

	@Query("select * from history where stuid=:studentId")
	public List<HistoryRecord> getHistoryRecordByStudentId(String studentId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public Long insertHistoryRecord(HistoryRecord historyRecord);

	@Delete
	public int deleteHistoryRecord(HistoryRecord historyRecord);

	@Query("delete from history")
	public int deleteAllHistoryRecord();
}
