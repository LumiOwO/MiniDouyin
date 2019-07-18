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
			return getDatabase(mContext).videoDao().insertVideoRecords(array);
		}
	}

	OnGetAllHotValueListener mOnGetAllHotValueListener;

	public void setOnGetAllHotValueListener(@NonNull OnGetAllHotValueListener listener) {
		mOnGetAllHotValueListener = listener;
	}

	public interface OnGetAllHotValueListener {
		void run(List<StudentVideoCount> hotValues);
	}

	public GetVideoCountByOneTask executeGetVideoCountByOne() {
		GetVideoCountByOneTask task = new GetVideoCountByOneTask();
		return (GetVideoCountByOneTask) task.execute();
	}

	public class GetVideoCountByOneTask extends AsyncTask<Void, Integer, List<StudentVideoCount>> {
		@Override
		protected List<StudentVideoCount> doInBackground(Void... voids) {
			return getDatabase(mContext).videoDao().getVideoCountByOne();
		}

		@Override
		protected void onPostExecute(List<StudentVideoCount> lists) {
			super.onPostExecute(lists);
			if (mOnGetAllHotValueListener != null) {
				mOnGetAllHotValueListener.run(lists);
			}
		}
	}

}
