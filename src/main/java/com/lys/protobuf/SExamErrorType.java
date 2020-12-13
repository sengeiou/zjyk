package com.lys.protobuf;

public class SExamErrorType
{
	public static final int A = 0;
	public static final int B = 1;
	public static final int C = 2;
	public static final int D = 3;

	public static String name(int value)
	{
		return ProtocolExam.ExamErrorType.valueOf(value).name().substring("ExamErrorType_".length());
	}
}
