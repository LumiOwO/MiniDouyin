package com.example.minidouyin.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.minidouyin.model.Video;

import java.util.List;
import java.util.stream.Collectors;

public class MiniDouYinDatabaseHelper {

	private Context mContext;

	private static MiniDouYinDatabase mDatabase;

	public MiniDouYinDatabaseHelper(Context context) {
		mContext = context;
	}

	public static MiniDouYinDatabase getDatabase(Context context) {
		if (mDatabase == null) {
			mDatabase = Room.databaseBuilder(context, MiniDouYinDatabase.class, "video.db").build();
		}
		return mDatabase;
	}


	/*
	 *
	 * */

	OnGetVideoRecordByIdListener mOnGetVideoRecordByIdListener;

	public void setOnGetVideoRecordByIdListener(@NonNull OnGetVideoRecordByIdListener listener) {
		mOnGetVideoRecordByIdListener = listener;
	}

	public interface OnGetVideoRecordByIdListener {
		void run(VideoRecord videoRecord);
	}

	public GetVideoRecordByIdTask executeGetVideoRecordById(String videoId) {
		GetVideoRecordByIdTask task = new GetVideoRecordByIdTask();
		return (GetVideoRecordByIdTask) task.execute(videoId);
	}

	public class GetVideoRecordByIdTask extends AsyncTask<String, Integer, VideoRecord> {
		@Override
		protected VideoRecord doInBackground(String... videoIds) {
			return getDatabase(mContext).videoDao().getVideoById(videoIds[0]);
		}

		@Override
		protected void onPostExecute(VideoRecord lists) {
			super.onPostExecute(lists);
			if (mOnGetVideoRecordByIdListener != null) {
				mOnGetVideoRecordByIdListener.run(lists);
			}
		}
	}

	/*
	 *
	 * */

	OnGetVideoRecordByStudentIdListener mOnGetVideoRecordByStudentIdListener;

	public void setOnGetVideoRecordByStudentIdListener(@NonNull OnGetVideoRecordByStudentIdListener listener) {
		mOnGetVideoRecordByStudentIdListener = listener;
	}

	public interface OnGetVideoRecordByStudentIdListener {
		void run(List<VideoRecord> videoRecords);
	}

	public GetVideoRecordByStudentIdTask executeGetVideoRecordByStudentId(String studentId) {
		GetVideoRecordByStudentIdTask task = new GetVideoRecordByStudentIdTask();
		return (GetVideoRecordByStudentIdTask) task.execute(studentId);
	}

	public class GetVideoRecordByStudentIdTask extends AsyncTask<String, Integer, List<VideoRecord>> {
		@Override
		protected List<VideoRecord> doInBackground(String... studentIds) {
			return getDatabase(mContext).videoDao().getVideoByStudentId(studentIds[0]);
		}

		@Override
		protected void onPostExecute(List<VideoRecord> lists) {
			super.onPostExecute(lists);
			if (mOnGetVideoRecordByStudentIdListener != null) {
				mOnGetVideoRecordByStudentIdListener.run(lists);
			}
		}
	}

	/*
	 *
	 * */
	public InsertVideoRecordsTask executeInsertVideoRecords(List<Video> videoList) {
		InsertVideoRecordsTask task = new InsertVideoRecordsTask();
		return (InsertVideoRecordsTask) task.execute(videoList);
	}

	public class InsertVideoRecordsTask extends AsyncTask<List<Video>, Integer, List<Long>> {
		@Override
		protected List<Long> doInBackground(List<Video>... lists) {
			List<VideoRecord> list = lists[0].stream().map(Video::createRecord).collect(Collectors.toList());
			VideoRecord[] array = new VideoRecord[list.size()];
			list.toArray(array);
			return getDatabase(mContext).videoDao().insertVideoRecord(array);
		}
	}

	/*
	 *
	 * */
	OnGetVideoCountByOneListener mOnGetVideoCountByOneListener;

	public void setOnGetVideoCountByOneListener(@NonNull OnGetVideoCountByOneListener listener) {
		mOnGetVideoCountByOneListener = listener;
	}

	public interface OnGetVideoCountByOneListener {
		void run(List<StudentVideoCountTuple> studentVideoCountTuples);
	}

	public GetVideoCountByOneTask executeGetVideoCountByOne() {
		GetVideoCountByOneTask task = new GetVideoCountByOneTask();
		return (GetVideoCountByOneTask) task.execute(5);
	}

	public GetVideoCountByOneTask executeGetVideoCountByOne(int limit) {
		GetVideoCountByOneTask task = new GetVideoCountByOneTask();
		return (GetVideoCountByOneTask) task.execute(limit);
	}

	public class GetVideoCountByOneTask extends AsyncTask<Integer, Integer, List<StudentVideoCountTuple>> {
		@Override
		protected List<StudentVideoCountTuple> doInBackground(Integer... limits) {
			return getDatabase(mContext).videoDao().getVideoCountByOne(limits[0]);
		}

		@Override
		protected void onPostExecute(List<StudentVideoCountTuple> lists) {
			super.onPostExecute(lists);
			if (mOnGetVideoCountByOneListener != null) {
				mOnGetVideoCountByOneListener.run(lists);
			}
		}
	}

	/*
	 *
	 * */
	OnGetVideoRecordByHotValueRankListener mOnGetVideoRecordByHotValueRankListener;

	public void setOnGetVideoRecordByHotValueRankListener(@NonNull OnGetVideoRecordByHotValueRankListener listener) {
		mOnGetVideoRecordByHotValueRankListener = listener;
	}

	public interface OnGetVideoRecordByHotValueRankListener {
		void run(List<VideoRecord> videoRecords);
	}

	public GetVideoRecordByHotValueRankTask executeGetVideoRecordByHotValueRank() {
		GetVideoRecordByHotValueRankTask task = new GetVideoRecordByHotValueRankTask();
		return (GetVideoRecordByHotValueRankTask) task.execute(10);
	}

	public GetVideoRecordByHotValueRankTask executeGetVideoRecordByHotValueRank(int limit) {
		GetVideoRecordByHotValueRankTask task = new GetVideoRecordByHotValueRankTask();
		return (GetVideoRecordByHotValueRankTask) task.execute(limit);
	}

	public class GetVideoRecordByHotValueRankTask extends AsyncTask<Integer, Integer, List<VideoRecord>> {
		@Override
		protected List<VideoRecord> doInBackground(Integer... limits) {
			return getDatabase(mContext).videoDao().getVideoByHotValueRank(limits[0]);
		}

		@Override
		protected void onPostExecute(List<VideoRecord> lists) {
			super.onPostExecute(lists);
			if (mOnGetVideoRecordByHotValueRankListener != null) {
				mOnGetVideoRecordByHotValueRankListener.run(lists);
			}
		}
	}

	/*
	 *
	 * */
	public InsertHistoryRecordTask executeInsertHistoryRecord(HistoryRecord historyRecord) {
		InsertHistoryRecordTask task = new InsertHistoryRecordTask();
		return (InsertHistoryRecordTask) task.execute(historyRecord);
	}


	public class InsertHistoryRecordTask extends AsyncTask<HistoryRecord, Integer, Long> {
		@Override
		protected Long doInBackground(HistoryRecord... historyRecords) {
			return getDatabase(mContext).historyDao().insertHistoryRecord(historyRecords[0]);
		}
	}

	/*
	 *
	 * */
	OnGetHistoryRecordByStudentIdListener mOnGetHistoryRecordByStudentIdListener;

	public void setOnGetHistoryRecordByStudentIdListener(@NonNull OnGetHistoryRecordByStudentIdListener listener) {
		mOnGetHistoryRecordByStudentIdListener = listener;
	}

	public interface OnGetHistoryRecordByStudentIdListener {
		void run(List<HistoryRecord> historyRecords);
	}

	public GetHistoryRecordByStudentIdTask executeGetHistoryRecordByStudentId(String studentID) {
		GetHistoryRecordByStudentIdTask task = new GetHistoryRecordByStudentIdTask();
		return (GetHistoryRecordByStudentIdTask) task.execute(studentID);
	}

	public class GetHistoryRecordByStudentIdTask extends AsyncTask<String, Integer, List<HistoryRecord>> {
		@Override
		protected List<HistoryRecord> doInBackground(String... studentIds) {
			return getDatabase(mContext).historyDao().getHistoryRecordByStudentId(studentIds[0]);
		}

		@Override
		protected void onPostExecute(List<HistoryRecord> historyRecords) {
			super.onPostExecute(historyRecords);
			if (mOnGetHistoryRecordByStudentIdListener != null) {
				mOnGetHistoryRecordByStudentIdListener.run(historyRecords);
			}
		}
	}

	/*
	 *
	 * */
	public DeleteCollectionRecordTask executeDeleteCollectionRecord(CollectionRecord collectionRecord) {
		DeleteCollectionRecordTask task = new DeleteCollectionRecordTask();
		return (DeleteCollectionRecordTask) task.execute(collectionRecord);
	}


	public class DeleteCollectionRecordTask extends AsyncTask<CollectionRecord, Integer, Integer> {
		@Override
		protected Integer doInBackground(CollectionRecord... collectionRecords) {
			return getDatabase(mContext).collectionDao().deleteCollectionRecord(collectionRecords[0]);
		}
	}

	/*
	 *
	 * */
	public InsertCollectionRecordTask executeInsertCollectionRecord(CollectionRecord collectionRecord) {
		InsertCollectionRecordTask task = new InsertCollectionRecordTask();
		return (InsertCollectionRecordTask) task.execute(collectionRecord);
	}


	public class InsertCollectionRecordTask extends AsyncTask<CollectionRecord, Integer, Long> {
		@Override
		protected Long doInBackground(CollectionRecord... collectionRecords) {
			return getDatabase(mContext).collectionDao().insertCollectionRecord(collectionRecords[0]);
		}
	}

	/*
	 *
	 * */
	OnGetCollectionRecordByStudentIdListener mOnGetCollectionRecordByStudentIdListener;

	public void setOnGetCollectionRecordByStudentIdListener(@NonNull OnGetCollectionRecordByStudentIdListener listener) {
		mOnGetCollectionRecordByStudentIdListener = listener;
	}

	public interface OnGetCollectionRecordByStudentIdListener {
		void run(List<CollectionRecord> collectionRecords);
	}

	public GetCollectionRecordByStudentIdTask executeGetCollectionRecordByStudentId(String studentId) {
		GetCollectionRecordByStudentIdTask task = new GetCollectionRecordByStudentIdTask();
		return (GetCollectionRecordByStudentIdTask) task.execute(studentId);
	}

	public class GetCollectionRecordByStudentIdTask extends AsyncTask<String, Integer, List<CollectionRecord>> {
		@Override
		protected List<CollectionRecord> doInBackground(String... studentIds) {
			return getDatabase(mContext).collectionDao().getCollectionRecordByStudentId(studentIds[0]);
		}

		@Override
		protected void onPostExecute(List<CollectionRecord> collectionRecords) {
			super.onPostExecute(collectionRecords);
			if (mOnGetCollectionRecordByStudentIdListener != null) {
				mOnGetCollectionRecordByStudentIdListener.run(collectionRecords);
			}
		}
	}

	/*
	 *
	 * */
	OnGetCollectionRecordListener mOnGetCollectionRecordListener;

	public void setOnGetCollectionRecordListener(@NonNull OnGetCollectionRecordListener listener) {
		mOnGetCollectionRecordListener = listener;
	}

	public interface OnGetCollectionRecordListener {
		void run(CollectionRecord collectionRecord);
	}

	public GetCollectionRecordTask executeGetCollectionRecord(String studentId, String videoId) {
		GetCollectionRecordTask task = new GetCollectionRecordTask();
		return (GetCollectionRecordTask) task.execute(studentId, videoId);
	}

	public class GetCollectionRecordTask extends AsyncTask<String, Integer, CollectionRecord> {
		@Override
		protected CollectionRecord doInBackground(String... studentIds) {
			return getDatabase(mContext).collectionDao().getCollectionRecord(studentIds[0], studentIds[1]);
		}

		@Override
		protected void onPostExecute(CollectionRecord collectionRecord) {
			super.onPostExecute(collectionRecord);
			if (mOnGetCollectionRecordListener != null) {
				mOnGetCollectionRecordListener.run(collectionRecord);
			}
		}
	}

}
