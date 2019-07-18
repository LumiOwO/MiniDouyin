package com.example.minidouyin.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CollectionDao {

	@Query("select * from collection where stuid=:studentId")
	public List<CollectionRecord> getCollectionRecordByStudentId(String studentId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public Long insertCollectionRecord(CollectionRecord collectionRecord);

	@Delete
	public int deleteCollectionRecord(CollectionRecord collectionRecord);
}
