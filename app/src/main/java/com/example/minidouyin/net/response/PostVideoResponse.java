package com.example.minidouyin.net.response;

import com.google.gson.annotations.SerializedName;

public class PostVideoResponse
{
	// "result": {},
	// "url": "..."
	// "success": true

	@SerializedName("result") 	private Result result;
	@SerializedName("url") 		private String url;
	@SerializedName("success") 	private boolean success;

	public static class Result
	{

	}

	public Result getResult()
	{
		return result;
	}

	public void setResult(Result result)
	{
		this.result = result;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
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
		return "PostVideoResponse{" +
				"result = {}" +
				", url = '" + url + '\'' +
				", success = " + success +
				'}';
	}
}
