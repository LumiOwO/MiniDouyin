package com.example.minidouyin.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
	private OnGetVideoByIdListener mOnGetVideoByIdListener;

	public void setOnGetVideoByIdListener(@NonNull OnGetVideoByIdListener listener) {
		mOnGetVideoByIdListener = listener;
	}

	public interface OnGetVideoByIdListener
	{
		void run(VideoRecord videoRecord);
	}

	public GetVideoByIdTask executeGetVideoById(String videoId) {
		GetVideoByIdTask task = new GetVideoByIdTask();
		mAsyncTasks.add(task);
		return (GetVideoByIdTask) task.execute(videoId);
	}

	public class GetVideoByIdTask extends AsyncTask<String, Integer, VideoRecord> {
		@Override
		protected VideoRecord doInBackground(String... videoIds) {
			return getDatabase(mContext).videoDao().getVideoById(videoIds[0]);
		}

		@Override
		protected void onPostExecute(VideoRecord lists) {
			super.onPostExecute(lists);
			mAsyncTasks.remove(this);
			if (mOnGetVideoByIdListener != null) {
				mOnGetVideoByIdListener.run(lists);
			}
		}
	}

	/*
	 *
	 * */

	private OnGetVideoByStudentIdListener mOnGetVideoByStudentIdListener;

	public void setOnGetVideoByStudentIdListener(@NonNull OnGetVideoByStudentIdListener listener) {
		mOnGetVideoByStudentIdListener = listener;
	}

	public interface OnGetVideoByStudentIdListener
	{
		void run(List<VideoRecord> videoRecords);
	}

	public GetVideoByStudentIdTask executeGetVideoByStudentId(String studentId) {
		GetVideoByStudentIdTask task = new GetVideoByStudentIdTask();
		mAsyncTasks.add(task);
		return (GetVideoByStudentIdTask) task.execute(studentId);
	}

	public class GetVideoByStudentIdTask extends AsyncTask<String, Integer, List<VideoRecord>> {
		@Override
		protected List<VideoRecord> doInBackground(String... studentIds) {
			return getDatabase(mContext).videoDao().getVideoByStudentId(studentIds[0]);
		}

		@Override
		protected void onPostExecute(List<VideoRecord> lists) {
			super.onPostExecute(lists);
			if (mOnGetVideoByStudentIdListener != null) {
				mAsyncTasks.remove(this);
				mOnGetVideoByStudentIdListener.run(lists);
			}
		}
	}

	/*
	 *
	 * */
	public InsertVideoTask executeInsertVideoRecords(List<Video> videoList) {
		InsertVideoTask task = new InsertVideoTask();
		return (InsertVideoTask) task.execute(videoList);
	}

	public class InsertVideoTask extends AsyncTask<List<Video>, Integer, List<Long>> {
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
				Log.d("setOnGetVideoCountByOneListener", "notnull");
				mOnGetVideoCountByOneListener.run(lists);
			}
		}
	}

	/*
	 *
	 * */
	private OnGetVideoByHotValueRankListener mOnGetVideoByHotValueRankListener;

	public void setOnGetVideoByHotValueRankListener(@NonNull OnGetVideoByHotValueRankListener listener) {
		mOnGetVideoByHotValueRankListener = listener;
	}

	public interface OnGetVideoByHotValueRankListener
	{
		void run(List<VideoRecord> videoRecords);
	}

	public GetVideoByHotValueRankTask executeGetVideoByHotValueRank() {
		return executeGetVideoByHotValueRank(10);
	}

	public GetVideoByHotValueRankTask executeGetVideoByHotValueRank(int limit) {
		GetVideoByHotValueRankTask task = new GetVideoByHotValueRankTask();
		mAsyncTasks.add(task);
		return (GetVideoByHotValueRankTask) task.execute(limit);
	}

	public class GetVideoByHotValueRankTask extends AsyncTask<Integer, Integer, List<VideoRecord>> {
		@Override
		protected List<VideoRecord> doInBackground(Integer... limits) {
			return getDatabase(mContext).videoDao().getVideoByHotValueRank(limits[0]);
		}

		@Override
		protected void onPostExecute(List<VideoRecord> lists) {
			super.onPostExecute(lists);
			mAsyncTasks.remove(this);
			if (mOnGetVideoByHotValueRankListener != null) {
				mOnGetVideoByHotValueRankListener.run(lists);
			}
		}
	}

	/*
	 *
	 * */
	public InsertHistoryTask executeInsertHistory(HistoryRecord historyRecord) {
		InsertHistoryTask task = new InsertHistoryTask();
		mAsyncTasks.add(task);
		return (InsertHistoryTask) task.execute(historyRecord);
	}


	public class InsertHistoryTask extends AsyncTask<HistoryRecord, Integer, Long> {
		@Override
		protected Long doInBackground(HistoryRecord... historyRecords) {
			return getDatabase(mContext).historyDao().insertHistory(historyRecords[0]);
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
	private OnGetHistoryByStudentIdListener mOnGetHistoryByStudentIdListener;

	public void setOnGetHistoryByStudentIdListener(@NonNull OnGetHistoryByStudentIdListener listener) {
		mOnGetHistoryByStudentIdListener = listener;
	}

	public interface OnGetHistoryByStudentIdListener
	{
		void run(List<HistoryRecord> historyRecords);
	}

	public GetHistoryByStudentIdTask executeGetHistoryByStudentId(String studentID) {
		GetHistoryByStudentIdTask task = new GetHistoryByStudentIdTask();
		mAsyncTasks.add(task);
		return (GetHistoryByStudentIdTask) task.execute(studentID);
	}

	public class GetHistoryByStudentIdTask extends AsyncTask<String, Integer, List<HistoryRecord>> {
		@Override
		protected List<HistoryRecord> doInBackground(String... studentIds) {
			return getDatabase(mContext).historyDao().getHistoryRecordByStudentId(studentIds[0]);
		}

		@Override
		protected void onPostExecute(List<HistoryRecord> historyRecords) {
			super.onPostExecute(historyRecords);
			mAsyncTasks.remove(this);
			if (mOnGetHistoryByStudentIdListener != null) {
				mOnGetHistoryByStudentIdListener.run(historyRecords);
			}
		}
	}

	/*
	 *
	 * */
	public DeleteCollectionTask executeDeleteCollection(CollectionRecord collectionRecord) {
		DeleteCollectionTask task = new DeleteCollectionTask();
		mAsyncTasks.add(task);
		return (DeleteCollectionTask) task.execute(collectionRecord);
	}


	public class DeleteCollectionTask extends AsyncTask<CollectionRecord, Integer, Integer> {
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
	public InsertCollectionTask executeInsertCollection(CollectionRecord collectionRecord) {
		InsertCollectionTask task = new InsertCollectionTask();
		mAsyncTasks.add(task);
		return (InsertCollectionTask) task.execute(collectionRecord);
	}


	public class InsertCollectionTask extends AsyncTask<CollectionRecord, Integer, Long> {
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
	private OnGetCollectionByStudentIdListener mOnGetCollectionByStudentIdListener;

	public void setOnGetCollectionByStudentIdListener(@NonNull OnGetCollectionByStudentIdListener listener) {
		mOnGetCollectionByStudentIdListener = listener;
	}

	public interface OnGetCollectionByStudentIdListener
	{
		void run(List<CollectionRecord> collectionRecords);
	}

	public GetCollectionByStudentIdTask executeGetCollectionByStudentId(String studentId) {
		GetCollectionByStudentIdTask task = new GetCollectionByStudentIdTask();
		mAsyncTasks.add(task);
		return (GetCollectionByStudentIdTask) task.execute(studentId);
	}

	public class GetCollectionByStudentIdTask extends AsyncTask<String, Integer, List<CollectionRecord>> {
		@Override
		protected List<CollectionRecord> doInBackground(String... studentIds) {
			return getDatabase(mContext).collectionDao().getCollectionRecordByStudentId(studentIds[0]);
		}

		@Override
		protected void onPostExecute(List<CollectionRecord> collectionRecords) {
			super.onPostExecute(collectionRecords);
			mAsyncTasks.remove(this);
			if (mOnGetCollectionByStudentIdListener != null) {
				mOnGetCollectionByStudentIdListener.run(collectionRecords);
			}
		}
	}

	/*
	 *
	 * */
	private OnGetCollectionListener mOnGetCollectionListener;

	public void setOnGetCollectionListener(@NonNull OnGetCollectionListener listener) {
		mOnGetCollectionListener = listener;
	}

	public interface OnGetCollectionListener
	{
		void run(CollectionRecord collectionRecord);
	}

	public GetCollectionTask executeGetCollection(String studentId, String videoId) {
		GetCollectionTask task = new GetCollectionTask();
		mAsyncTasks.add(task);
		return (GetCollectionTask) task.execute(studentId, videoId);
	}

	public class GetCollectionTask extends AsyncTask<String, Integer, CollectionRecord> {
		@Override
		protected CollectionRecord doInBackground(String... studentIds) {
			return getDatabase(mContext).collectionDao().getCollectionRecord(studentIds[0], studentIds[1]);
		}

		@Override
		protected void onPostExecute(CollectionRecord collectionRecord) {
			super.onPostExecute(collectionRecord);
			mAsyncTasks.remove(this);
			if (mOnGetCollectionListener != null) {
				mOnGetCollectionListener.run(collectionRecord);
			}
		}
	}

	/*
	 *
	 * */
	private OnDeleteAllHistroyListener mOnDeleteAllHistroyListener;

	public interface OnDeleteHistroyListener {
		void run(int affectedCount);
	}

	public void setOnDeleteHistoryListener(OnDeleteAllHistroyListener listener) {
		mOnDeleteAllHistroyListener = listener;
	}

	public DeleteHistoryTask executeDeleteHistory(HistoryRecord historyRecord) {
		DeleteHistoryTask task = new DeleteHistoryTask();
		mAsyncTasks.add(task);
		return (DeleteHistoryTask) task.execute(historyRecord);
	}

	public class DeleteHistoryTask extends AsyncTask<HistoryRecord, Integer, Integer> {

		@Override
		protected Integer doInBackground(HistoryRecord... historyRecords) {
			return getDatabase(mContext).historyDao().deleteHistory(historyRecords[0]);
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

	/*
	 *
	 * */
	private OnDeleteAllHistroyListener mOnDeleteHistroyListener;

	public interface OnDeleteAllHistroyListener {
		void run(int affectedCount);
	}

	public void setOnDeleteAllHistoryListener(OnDeleteAllHistroyListener listener) {
		mOnDeleteAllHistroyListener = listener;
	}

	public DeleteAllHistoryTask executeDeleteAllHistory(String studentId) {
		DeleteAllHistoryTask task = new DeleteAllHistoryTask();
		mAsyncTasks.add(task);
		return (DeleteAllHistoryTask) task.execute(studentId);
	}

	public class DeleteAllHistoryTask extends AsyncTask<String, Integer, Integer> {

		@Override
		protected Integer doInBackground(String... studentIds) {
			return getDatabase(mContext).historyDao().deleteAllHistory(studentIds[0]);
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


	/*
	 *
	 * */
	public UpdateVideoTask executeUpdateVideo(VideoRecord videoRecord) {
		UpdateVideoTask task = new UpdateVideoTask();
		mAsyncTasks.add(task);
		return (UpdateVideoTask) task.execute(videoRecord);
	}

	public class UpdateVideoTask extends AsyncTask<VideoRecord, Integer, Integer> {

		@Override
		protected Integer doInBackground(VideoRecord... videos) {
			return getDatabase(mContext).videoDao().updateVideo(videos[0]);
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
	public HotValueIncrementTask executeHotValueIncrement(String videoId) {
		HotValueIncrementTask task = new HotValueIncrementTask();
		mAsyncTasks.add(task);
		return (HotValueIncrementTask) task.execute(videoId);
	}

	public class HotValueIncrementTask extends AsyncTask<String, Integer, Long> {

		@Override
		protected Long doInBackground(String... videoIds) {
			return getDatabase(mContext).videoDao().hotValueIncrement(videoIds[0]);
		}

		@Override
		protected void onPostExecute(Long aLong) {
			super.onPostExecute(aLong);
			mAsyncTasks.remove(this);
		}
	}
}
