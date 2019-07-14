package com.example.minidouyin.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetVideosResponse
{
	// "feeds": [
	// {
	// 		"student_id": "222",
	// 		"user_name": "name",
	// 		"image_url": "...",
	// 		"_id": "...",
	// 		"video_url": "...",
	// 		"createdAt": "...",
	// 		"updatedAt": "...",
	// 		"image_w": 756,
	// 		"image_h": 1008
	// }
	// ...
	// ],
	// "success": true

	@SerializedName("feeds") 	private List<Feed> feeds;
	@SerializedName("success") 	private boolean success;

	public static class Feed
	{
		@SerializedName("student_id") 	private String student_id;
		@SerializedName("user_name") 	private String user_name;
		@SerializedName("image_url") 	private String image_url;
		@SerializedName("_id") 			private String _id;
		@SerializedName("video_url") 	private String video_url;
		@SerializedName("createdAt") 	private String createdAt;
		@SerializedName("updatedAt") 	private String updatedAt;
		@SerializedName("image_w") 		private String image_w;
		@SerializedName("image_h") 		private String image_h;

		public String getStudent_id()
		{
			return student_id;
		}

		public void setStudent_id(String student_id)
		{
			this.student_id = student_id;
		}

		public String getUser_name()
		{
			return user_name;
		}

		public void setUser_name(String user_name)
		{
			this.user_name = user_name;
		}

		public String getImage_url()
		{
			return image_url;
		}

		public void setImage_url(String image_url)
		{
			this.image_url = image_url;
		}

		public String get_id()
		{
			return _id;
		}

		public void set_id(String _id)
		{
			this._id = _id;
		}

		public String getVideo_url()
		{
			return video_url;
		}

		public void setVideo_url(String video_url)
		{
			this.video_url = video_url;
		}

		public String getCreatedAt()
		{
			return createdAt;
		}

		public void setCreatedAt(String createdAt)
		{
			this.createdAt = createdAt;
		}

		public String getUpdatedAt()
		{
			return updatedAt;
		}

		public void setUpdatedAt(String updatedAt)
		{
			this.updatedAt = updatedAt;
		}

		public String getImage_w()
		{
			return image_w;
		}

		public void setImage_w(String image_w)
		{
			this.image_w = image_w;
		}

		public String getImage_h()
		{
			return image_h;
		}

		public void setImage_h(String image_h)
		{
			this.image_h = image_h;
		}

		@Override
		public String toString()
		{
			return "Feed{" +
					"student_id='" + student_id + '\'' +
					", user_name='" + user_name + '\'' +
					", image_url='" + image_url + '\'' +
					", _id='" + _id + '\'' +
					", video_url='" + video_url + '\'' +
					", createdAt='" + createdAt + '\'' +
					", updatedAt='" + updatedAt + '\'' +
					", image_w=" + image_w +
					", image_h=" + image_h +
					'}';
		}
	}

	public List<Feed> getFeeds()
	{
		return feeds;
	}

	public void setFeeds(List<Feed> feeds)
	{
		this.feeds = feeds;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	@Override
	public String toString()
	{
		String ret = "";
		ret += "GetVideosResponse{\n" +
				"feeds = [\n";
		for(int i=0; i<feeds.size(); i++)
			ret += feeds.get(i).toString() + "\n";
		ret += "], success = " + success + "}";
		return ret;
	}
}
