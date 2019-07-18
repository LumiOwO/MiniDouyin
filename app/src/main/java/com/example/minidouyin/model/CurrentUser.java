package com.example.minidouyin.model;

public final class CurrentUser
{
	private static String Student_ID = "7777777";
	private static String Username = "user007";

	public static String getStudentID()
	{
		return Student_ID;
	}

	public static void setStudentID(String student_ID)
	{
		Student_ID = student_ID;
	}

	public static String getUsername()
	{
		return Username;
	}

	public static void setUsername(String username)
	{
		Username = username;
	}
}
