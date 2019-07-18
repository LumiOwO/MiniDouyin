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
		void run(List<StudentVideoCountTuple> hotValues);
	}

	public GetVideoCountByOneTask executeGetVideoCountByOne() {
		GetVideoCountByOneTask task = new GetVideoCountByOneTask();
		return (GetVideoCountByOneTask) task.execute();
	}

	public class GetVideoCountByOneTask extends AsyncTask<Void, Integer, List<StudentVideoCountTuple>> {
		@Override
		protected List<StudentVideoCountTuple> doInBackground(Void... voids) {
			return getDatabase(mContext).videoDao().getVideoCountByOne();
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
		void run(List<HistoryRecord> hotValues);
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

}
