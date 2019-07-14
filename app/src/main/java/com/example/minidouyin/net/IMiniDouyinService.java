package com.example.minidouyin.net;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IMiniDouyinService {
	String HOST = "http://test.androidcamp.bytedance.com/mini_douyin/invoke/";
	String PATH = "video";

	@GET(PATH)
	Call<GetVideoResponse> getVideos();

	@Multipart
	@POST(PATH)
	Call<PostVideoResponse> postVideo(@Query("student_id") String studentId,
	                                  @Query("user_name") String userName,
	                                  @Part MultipartBody.Part coverImage,
	                                  @Part MultipartBody.Part video);
}
