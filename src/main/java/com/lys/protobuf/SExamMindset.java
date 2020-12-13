package com.lys.protobuf;

public class SExamMindset
{
	public static final int Normal = 0; // 一般般
	public static final int JinZhang = 1; // 紧张
	public static final int FangShong = 2; // 放松

	public static String name(int value)
	{
		return ProtocolExam.ExamMindset.valueOf(value).name().substring("ExamMindset_".length());
	}
}
