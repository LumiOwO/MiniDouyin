package com.example.minidouyin.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.minidouyin.R;
import com.example.minidouyin.VideoPlayer.VideoPlayer;
import com.example.minidouyin.adapter.VideoRecyclerAdapter;
import com.example.minidouyin.db.CollectionRecord;
import com.example.minidouyin.db.MiniDouYinDatabaseHelper;
import com.example.minidouyin.db.VideoRecord;
import com.example.minidouyin.model.CurrentUser;
import com.example.minidouyin.model.Video;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoRecyclerItemHolder extends RecyclerView.ViewHolder {
    public final static String TAG = "VideoRecyclerItemHolder";

    VideoRecyclerAdapter mRecyclerAdapter;

    protected Context context = null;

    @BindView(R.id.rv_video_gsyPlayer)
    VideoPlayer gsyVideoPlayer;

    @BindView(R.id.rv_video_btn_follow)
    ShineButton mBtnFollow;

    @BindView(R.id.rv_video_btn_like)
    ShineButton mBtnLike;

    @BindView(R.id.rv_video_hot_value)
    TextView mHotValue;

    @BindView(R.id.rv_video_btn_share)
    ShineButton mBtnShare;

    ImageView imageView;

    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    private MiniDouYinDatabaseHelper mDatabaseHelper;

    public VideoRecyclerItemHolder(Context context, View v, View.OnClickListener... listeners) {
        super(v);
        this.context = context;
        mDatabaseHelper = new MiniDouYinDatabaseHelper(context);

        ButterKnife.bind(this, v);
        mBtnFollow.init((Activity)context);
        mBtnLike.init((Activity)context);
        mBtnShare.init((Activity)context);
        setOnClickListeners(listeners);

        imageView = new ImageView(context);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
        gsyVideoPlayer.setOnDoubleClickListener(new VideoPlayer.OnDoubleClickListener()
        {
            @Override
            public void run()
            {
                mBtnLike.callOnClick();
            }
        });
        mBtnLike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mBtnLike.isChecked())
                    mBtnLike.setChecked(false);

                int hotValue = (int)(Float.parseFloat(mHotValue.getText().toString()) * 10);
                updateHotValue(hotValue + 1);

                Video video = getRecyclerBaseAdapter().getData().get(getAdapterPosition());

//                mDatabaseHelper.executeInsertVideoRecords();
            }
        });
    }

    public void setOnClickListeners(View.OnClickListener... listeners) {
        if (listeners != null && listeners.length > 0) {
            mBtnFollow.setOnClickListener(listeners[0]);
            if (listeners.length > 1) {
                mBtnLike.setOnClickListener(listeners[1]);
                if (listeners.length > 2) {
                    mBtnShare.setOnClickListener(listeners[2]);
                }
            }
        }
    }

    public void bind(Context context, final int position, Video video) {

        gsyVideoOptionBuilder.setUrl(video.getVideoUrl()).build(gsyVideoPlayer);
        gsyVideoPlayer.setLooping(true);

        ImageView imageView = new ImageView(context);
        Glide.with(context)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(0)
                )
                .load(video.getVideoUrl())
                .into(imageView);
        gsyVideoPlayer.setThumbImageView(imageView);

        final String studentId = video.getStudentId();
        final String videoId = video.getId();

        mDatabaseHelper.setOnGetVideoByIdListener(new MiniDouYinDatabaseHelper.OnGetVideoByIdListener() {
            @Override
            public void run(VideoRecord videoRecord) {
                int hotValue = videoRecord.getHotValue();
                // TODO 1: 设置热度值
                updateHotValue(hotValue);
            }
        });
        mDatabaseHelper.setOnGetCollectionListener(new MiniDouYinDatabaseHelper.OnGetCollectionListener() {
            @Override
            public void run(CollectionRecord collectionRecord) {
                if (collectionRecord != null && collectionRecord.getStudentId().equals(CurrentUser.getStudentID()) && collectionRecord.getVideoId().equals(videoId)) {
                    mBtnFollow.setChecked(true);
                } else {
                    mBtnFollow.setChecked(false);
                }
            }
        });
        mDatabaseHelper.executeGetVideoById(videoId);
        mDatabaseHelper.executeGetCollection(CurrentUser.getStudentID(), videoId);
    }

    public VideoRecyclerAdapter getRecyclerBaseAdapter() {
        return mRecyclerAdapter;
    }

    public void setRecyclerBaseAdapter(VideoRecyclerAdapter recyclerAdapter) {
        this.mRecyclerAdapter = recyclerAdapter;
    }

    private void updateHotValue(int hotValue)
    {
        mHotValue.setText(String.format(Locale.getDefault(), "%.1f", hotValue / 10.0));
    }

}