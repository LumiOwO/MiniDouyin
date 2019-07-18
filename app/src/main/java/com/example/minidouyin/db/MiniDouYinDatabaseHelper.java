package com.example.minidouyin.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.minidouyin.model.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MiniDouYinDatabaseHelper {

	private Context mContext;

	private static MiniDouYinDatabase mDatabase;

	private List<AsyncTask> mAsyncTasks = new ArrayList<>();

	public MiniDouYinDatabaseHelper(Context context) {
		mContext = context;
	}

	public static MiniDouYinDatabase getDatabase(Context context) {
		if (mDatabase == null) {
			mDatabase = Room.databaseBuilder(context, MiniDouYinDatabase.class, "video.db").build();
		}
		return mDatabase;
	}

	public void cancelAllAsyncTasks() {
		for (AsyncTask task : mAsyncTasks) {
			if (task != null && !task.isCancelled()) {
				task.cancel(true);
			}
			mAsyncTasks.remove(task);
		}
	}

	/*
	 *
	 * */
	private OnGetVideoRecordByIdListener mOnGetVideoRecordByIdListener;

	public void setOnGetVideoRecordByIdListener(@NonNull OnGetVideoRecordByIdListener listener) {
		mOnGetVideoRecordByIdListener = listener;
	}

	public interface OnGetVideoRecordByIdListener {
		void run(VideoRecord videoRecord);
	}

	public GetVideoRecordByIdTask executeGetVideoRecordById(String videoId) {
		GetVideoRecordByIdTask task = new GetVideoRecordByIdTask();
		mAsyncTasks.add(task);
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
			mAsyncTasks.remove(this);
			if (mOnGetVideoRecordByIdListener != null) {
				mOnGetVideoRecordByIdListener.run(lists);
			}
		}
	}

	/*
	 *
	 * */

	private OnGetVideoRecordByStudentIdListener mOnGetVideoRecordByStudentIdListener;

	public void setOnGetVideoRecordByStudentIdListener(@NonNull OnGetVideoRecordByStudentIdListener listener) {
		mOnGetVideoRecordByStudentIdListener = listener;
	}

	public interface OnGetVideoRecordByStudentIdListener {
		void run(List<VideoRecord> videoRecords);
	}

	public GetVideoRecordByStudentIdTask executeGetVideoRecordByStudentId(String studentId) {
		GetVideoRecordByStudentIdTask task = new GetVideoRecordByStudentIdTask();
		mAsyncTasks.add(task);
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
				mAsyncTasks.remove(this);
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
	private OnGetVideoCountByOneListener mOnGetVideoCountByOneListener;

	public void setOnGetVideoCountByOneListener(@NonNull OnGetVideoCountByOneListener listener) {
		mOnGetVideoCountByOneListener = listener;
	}

	public interface OnGetVideoCountByOneListener {
		void run(List<StudentVideoCountTuple> studentVideoCountTuples);
	}

	public GetVideoCountByOneTask executeGetVideoCountByOne() {
		return executeGetVideoCountByOne(5);
	}

	public GetVideoCountByOneTask executeGetVideoCountByOne(int limit) {
		GetVideoCountByOneTask task = new GetVideoCountByOneTask();
		mAsyncTasks.add(task);
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
			mAsyncTasks.remove(this);
			if (mOnGetVideoCountByOneListener != null) {
				mOnGetVideoCountByOneListener.run(lists);
			}
		}
	}

	/*
	 *
	 * */
	private OnGetVideoRecordByHotValueRankListener mOnGetVideoRecordByHotValueRankListener;

	public void setOnGetVideoRecordByHotValueRankListener(@NonNull OnGetVideoRecordByHotValueRankListener listener) {
		mOnGetVideoRecordByHotValueRankListener = listener;
	}

	public interface OnGetVideoRecordByHotValueRankListener {
		void run(List<VideoRecord> videoRecords);
	}

	public GetVideoRecordByHotValueRankTask executeGetVideoRecordByHotValueRank() {
		return executeGetVideoRecordByHotValueRank(10);
	}

	public GetVideoRecordByHotValueRankTask executeGetVideoRecordByHotValueRank(int limit) {
		GetVideoRecordByHotValueRankTask task = new GetVideoRecordByHotValueRankTask();
		mAsyncTasks.add(task);
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
			mAsyncTasks.remove(this);
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
		mAsyncTasks.add(task);
		return (InsertHistoryRecordTask) task.execute(historyRecord);
	}


	public class InsertHistoryRecordTask extends AsyncTask<HistoryRecord, Integer, Long> {
		@Override
		protected Long doInBackground(HistoryRecord... historyRecords) {
			return getDatabase(mContext).historyDao().insertHistoryRecord(historyRecords[0]);
		}

		@Override
		protected void onPostExecute(Long aLong) {
			super.onPostExecute(aLong);
			mAsyncTasks.remove(this);
		}
	}

	/*
	 *
	 * */
	private OnGetHistoryRecordByStudentIdListener mOnGetHistoryRecordByStudentIdListener;

	public void setOnGetHistoryRecordByStudentIdListener(@NonNull OnGetHistoryRecordByStudentIdListener listener) {
		mOnGetHistoryRecordByStudentIdListener = listener;
	}

	public interface OnGetHistoryRecordByStudentIdListener {
		void run(List<HistoryRecord> historyRecords);
	}

	public GetHistoryRecordByStudentIdTask executeGetHistoryRecordByStudentId(String studentID) {
		GetHistoryRecordByStudentIdTask task = new GetHistoryRecordByStudentIdTask();
		mAsyncTasks.add(task);
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
			mAsyncTasks.remove(this);
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
		mAsyncTasks.add(task);
		return (DeleteCollectionRecordTask) task.execute(collectionRecord);
	}


	public class DeleteCollectionRecordTask extends AsyncTask<CollectionRecord, Integer, Integer> {
		@Override
		protected Integer doInBackground(CollectionRecord... collectionRecords) {
			return getDatabase(mContext).collectionDao().deleteCollectionRecord(collectionRecords[0]);
		}

		@Override
		protected void onPostExecute(Integer integer) {
			super.onPostExecute(integer);
			mAsyncTasks.remove(this);
		}
	}

	/*
	 *
	 * */
	public InsertCollectionRecordTask executeInsertCollectionRecord(CollectionRecord collectionRecord) {
		InsertCollectionRecordTask task = new InsertCollectionRecordTask();
		mAsyncTasks.add(task);
		return (InsertCollectionRecordTask) task.execute(collectionRecord);
	}


	public class InsertCollectionRecordTask extends AsyncTask<CollectionRecord, Integer, Long> {
		@Override
		protected Long doInBackground(CollectionRecord... collectionRecords) {
			return getDatabase(mContext).collectionDao().insertCollectionRecord(collectionRecords[0]);
		}

		@Override
		protected void onPostExecute(Long aLong) {
			super.onPostExecute(aLong);
			mAsyncTasks.remove(this);
		}
	}

	/*
	 *
	 * */
	private OnGetCollectionRecordByStudentIdListener mOnGetCollectionRecordByStudentIdListener;

	public void setOnGetCollectionRecordByStudentIdListener(@NonNull OnGetCollectionRecordByStudentIdListener listener) {
		mOnGetCollectionRecordByStudentIdListener = listener;
	}

	public interface OnGetCollectionRecordByStudentIdListener {
		void run(List<CollectionRecord> collectionRecords);
	}

	public GetCollectionRecordByStudentIdTask executeGetCollectionRecordByStudentId(String studentId) {
		GetCollectionRecordByStudentIdTask task = new GetCollectionRecordByStudentIdTask();
		mAsyncTasks.add(task);
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
			mAsyncTasks.remove(this);
			if (mOnGetCollectionRecordByStudentIdListener != null) {
				mOnGetCollectionRecordByStudentIdListener.run(collectionRecords);
			}
		}
	}

	/*
	 *
	 * */
	private OnGetCollectionRecordListener mOnGetCollectionRecordListener;

	public void setOnGetCollectionRecordListener(@NonNull OnGetCollectionRecordListener listener) {
		mOnGetCollectionRecordListener = listener;
	}

	public interface OnGetCollectionRecordListener {
		void run(CollectionRecord collectionRecord);
	}

	public GetCollectionRecordTask executeGetCollectionRecord(String studentId, String videoId) {
		GetCollectionRecordTask task = new GetCollectionRecordTask();
		mAsyncTasks.add(task);
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
			mAsyncTasks.remove(this);
			if (mOnGetCollectionRecordListener != null) {
				mOnGetCollectionRecordListener.run(collectionRecord);
			}
		}
	}

	/*
	 *
	 * */
	private OnDeleteAllHistroyListener mOnDeleteAllHistroyListener;

	public interface OnDeleteAllHistroyListener {
		void run(int affectedCount);
	}

	public void setOnDeleteHistoryListener(OnDeleteAllHistroyListener listener) {
		mOnDeleteAllHistroyListener = listener;
	}

	public DeleteAllHistoryTask executeDeleteAllHistory() {
		DeleteAllHistoryTask task = new DeleteAllHistoryTask();
		mAsyncTasks.add(task);
		return (DeleteAllHistoryTask) task.execute();
	}

	public class DeleteAllHistoryTask extends AsyncTask<Void, Integer, Integer> {

		@Override
		protected Integer doInBackground(Void... voids) {
			return getDatabase(mContext).historyDao().deleteAllHistoryRecord();
		}

		@Override
		protected void onPostExecute(Integer integer) {
			super.onPostExecute(integer);
			mAsyncTasks.remove(this);
			if (mOnDeleteAllHistroyListener != null) {
				mOnDeleteAllHistroyListener.run(integer);
			}
		}
	}


}
