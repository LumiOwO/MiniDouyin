package com.example.minidouyin.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.minidouyin.R;
import com.example.minidouyin.VideoPlayer.EmptyControlVideoPlayer;
import com.example.minidouyin.model.Video;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by guoshuyu on 2017/1/9.
 */

public class VideoRecyclerItemHolder extends RecyclerView.ViewHolder {
    public final static String TAG = "RecyclerView2List";

    RecyclerView.Adapter mRecyclerAdapter;

    protected Context context = null;

    @BindView(R.id.rv_video_gsyPlayer)
    EmptyControlVideoPlayer gsyVideoPlayer;

    @BindView(R.id.rv_video_btn_follow)
    Button mBtnFollow;

    @BindView(R.id.rv_video_btn_like)
    Button mBtnLike;

    @BindView(R.id.rv_video_btn_comment)
    Button mBtnComment;

    @BindView(R.id.rv_video_btn_share)
    Button mBtnShare;

    ImageView imageView;

    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public VideoRecyclerItemHolder(Context context, View v) {
        super(v);
        this.context = context;
        ButterKnife.bind(this, v);
        imageView = new ImageView(context);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    public void bind(final int position, Video video) {

        mBtnFollow.setText(video.getStudentId());
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