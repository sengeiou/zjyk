package com.lys.app.messages;

import com.lys.protobuf.ProtocolPair;
import com.lys.protobuf.SPTask;
import io.rong.messages.BaseMessage;
import io.rong.util.GsonUtil;

public class TaskMessage extends BaseMessage
{
	public String id = null;
	public String userId = null;
	public String sendUser_id = null;
	public /* SUserType */ Integer sendUser_userType = ProtocolPair.User.getDefaultInstance().getUserType().getNumber();
	public String sendUser_name = null;
	public String sendUser_head = null;
	public /* SPTaskType */ Integer type = ProtocolPair.PTask.getDefaultInstance().getType().getNumber();
	public String group = null;
	public String name = null;
	public Long createTime = 0L;
	public Integer state = 0;

	private transient static final String TYPE = "app:TaskMsg";

	public TaskMessage(SPTask task)
	{
		this.id = task.id;
		this.userId = task.userId;
		if (task.sendUser != null)
		{
			this.sendUser_id = task.sendUser.id;
			this.sendUser_userType = task.sendUser.userType;
			this.sendUser_name = task.sendUser.name;
			this.sendUser_head = task.sendUser.head;
		}
		this.type = task.type;
		this.group = task.group;
		this.name = task.name;
		this.createTime = task.createTime;
		this.state = task.state;
	}

	@Override
	public String getType()
	{
		return TYPE;
	}

	@Override
	public String toString()
	{
		return GsonUtil.toJson(this, TaskMessage.class);
	}
}