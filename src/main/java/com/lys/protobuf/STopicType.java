package com.lys.protobuf;

public class STopicType
{
	public static final int selectSingle = 0;
	public static final int selectMulti = 1;
	public static final int gap = 2;
	public static final int answer = 3;

	public static String name(int value)
	{
		return ProtocolExam.TopicType.valueOf(value).name().substring("TopicType_".length());
	}
}
