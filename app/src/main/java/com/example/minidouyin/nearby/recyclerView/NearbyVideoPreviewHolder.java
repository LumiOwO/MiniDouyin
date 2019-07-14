package com.example.minidouyin.nearby.recyclerView;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.minidouyin.R;

public class NearbyVideoPreviewHolder extends RecyclerView.ViewHolder
{
	private ImageView mImageView;

	public NearbyVideoPreviewHolder(@NonNull View itemView)
	{
		super(itemView);
		mImageView = itemView.findViewById(R.id.preview_nearby);
	}

	public void setImage(String url)
	{
		Glide.with(mImageView.getContext()).load(url).into(mImageView);
	}
}
