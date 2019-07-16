package com.example.minidouyin.fragments.nearby;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.minidouyin.R;
import com.example.minidouyin.activities.PlayActivity;
import com.example.minidouyin.model.Video;

import java.util.List;

public class NearbyVideoPreviewHolder extends RecyclerView.ViewHolder
{
	private ImageView mImageView;

	public NearbyVideoPreviewHolder(@NonNull View itemView, List<Video> playlist)
	{
		super(itemView);
		mImageView = itemView.findViewById(R.id.preview_nearby);

		itemView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
//				Log.d("click", getAdapterPosition() + "");
				PlayActivity.launch(getActivity(v), playlist, getAdapterPosition());
			}
		});
	}

	public void setImage(String url)
	{
		Glide.with(mImageView.getContext()).load(url).into(mImageView);
	}

	private Activity getActivity(View view) {
		Activity ret = null;

		Context context = view.getContext();
		while (context instanceof ContextWrapper) {
			if (context instanceof Activity) {
				ret = (Activity)context;
				break;
			}
			context = ((ContextWrapper)context).getBaseContext();
		}
		return ret;
	}

}
