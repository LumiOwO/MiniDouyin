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
	List<CollectionRecord> getCollectionRecordByStudentId(String studentId);

	@Query("select * from collection where stuid=:studentId and video_id=:videoId")
	CollectionRecord getCollectionRecord(String studentId, String videoId);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Long insertCollectionRecord(CollectionRecord collectionRecord);

	@Delete
	int deleteCollectionRecord(CollectionRecord collectionRecord);
}
