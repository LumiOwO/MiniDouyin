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
	public Long insertHistory(HistoryRecord historyRecord);

	@Delete
	public int deleteHistory(HistoryRecord historyRecord);

	@Delete
	public int deleteHistories(HistoryRecord... historyRecords);

	@Query("delete from history where stuid=:studentId")
	public int deleteAllHistory(String studentId);
}
