package com.example.minidouyin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.minidouyin.R;
import com.example.minidouyin.holder.VideoRecyclerItemHolder;
import com.example.minidouyin.model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoRecyclerAdapter extends RecyclerView.Adapter {
    private final static String TAG = "RecyclerBaseAdapter";

    private List<Video> mItemDataList = new ArrayList<>();
    private Context mContext = null;

    private View.OnClickListener[] mListeners = null;

    public VideoRecyclerAdapter(Context context, View.OnClickListener... listeners) {
        this.mContext = context;
        setOnClickListeners(listeners);
    }

    public VideoRecyclerAdapter(Context context, List<Video> itemDataList, View.OnClickListener... listeners) {
        this.mItemDataList = itemDataList;
        this.mContext = context;
        setOnClickListeners(listeners);
    }

    public void setOnClickListeners(View.OnClickListener... listeners) {
        mListeners = listeners;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_rv_video, parent, false);
        final RecyclerView.ViewHolder holder = new VideoRecyclerItemHolder(mContext, v, mListeners);
        return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        VideoRecyclerItemHolder recyclerItemViewHolder = (VideoRecyclerItemHolder) holder;
        recyclerItemViewHolder.setRecyclerBaseAdapter(this);
        recyclerItemViewHolder.bind(mContext, position, mItemDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemDataList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    public void setListData(List<Video> data) {
        mItemDataList = data;
        notifyDataSetChanged();
    }
}
