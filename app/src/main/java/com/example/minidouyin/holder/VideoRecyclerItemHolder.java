package com.example.minidouyin.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.minidouyin.R;
import com.example.minidouyin.VideoPlayer.EmptyControlVideoPlayer;
import com.example.minidouyin.model.Video;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoRecyclerItemHolder extends RecyclerView.ViewHolder {
    public final static String TAG = "RecyclerView2List";

    RecyclerView.Adapter mRecyclerAdapter;

    protected Context context = null;

    @BindView(R.id.rv_video_gsyPlayer)
    EmptyControlVideoPlayer gsyVideoPlayer;

    @BindView(R.id.rv_video_btn_follow)
    ShineButton mBtnFollow;

    @BindView(R.id.rv_video_btn_like)
    ShineButton mBtnLike;

    @BindView(R.id.rv_video_btn_comment)
    ShineButton mBtnComment;

    @BindView(R.id.rv_video_btn_share)
    ShineButton mBtnShare;

    ImageView imageView;

    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public VideoRecyclerItemHolder(Context context, View v) {
        super(v);
        this.context = context;
        ButterKnife.bind(this, v);
        mBtnFollow.init((Activity)context);
        mBtnLike.init((Activity)context);
        mBtnComment.init((Activity)context);
        mBtnShare.init((Activity)context);
        imageView = new ImageView(context);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    public void bind(final int position, Video video) {

        //防止错位，离开释放
        //gsyVideoPlayer.initUIState();
        gsyVideoOptionBuilder.setUrl(video.getVideoUrl()).build(gsyVideoPlayer);
//        gsyVideoPlayer.startPlayLogic();

    }

    public RecyclerView.Adapter getRecyclerBaseAdapter() {
        return mRecyclerAdapter;
    }

    public void setRecyclerBaseAdapter(RecyclerView.Adapter recyclerAdapter) {
        this.mRecyclerAdapter = recyclerAdapter;
    }

}