package com.lys.app.manager;

import com.lys.utils.LOG;
import io.rong.RongCloud;
import io.rong.messages.BaseMessage;
import io.rong.models.Result;
import io.rong.models.group.GroupMember;
import io.rong.models.group.GroupModel;
import io.rong.models.message.PrivateMessage;
import io.rong.models.message.SystemMessage;
import io.rong.models.response.CheckOnlineResult;
import io.rong.models.response.ResponseResult;
import io.rong.models.response.TokenResult;
import io.rong.models.user.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//https://github.com/rongcloud/server-sdk-java
@Component
public class RongHelper
{
	@Value("${rong.cloud.app.key}")
	private String appKey = null;

	@Value("${rong.cloud.app.secret}")
	private String appSecret = null;

	private RongCloud rongCloud = null;

	private RongCloud getRongCloud()
	{
		if (rongCloud == null)
		{
			LOG.v(String.format("rongCloud appKey = %s, appSecret = %s", appKey, appSecret));
			rongCloud = RongCloud.getInstance(appKey, appSecret);
		}
		return rongCloud;
	}

	@PostConstruct
	public void init()
	{
		LOG.v("-------------------- RongHelper --------- init ---------");
		getRongCloud();
	}

	public String getToken(String userId, String name, String head)
	{
		try
		{
			UserModel user = new UserModel() //
					.setId(userId) //
					.setName(name) //
					.setPortrait(head);
			TokenResult result = getRongCloud().user.register(user);
			if (result.getCode().equals(200))
				return result.getToken();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public boolean refreshUserInfo(String userId, String name, String head)
	{
		try
		{
			UserModel user = new UserModel() //
					.setId(userId) //
					.setName(name) //
					.setPortrait(head);
			Result result = getRongCloud().user.update(user);
			if (result.getCode().equals(200))
				return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean testCheckOnline(String userId)
	{
		try
		{
			UserModel user = new UserModel() //
					.setId(userId);
			CheckOnlineResult result = getRongCloud().user.onlineStatus.check(user);
			LOG.v("result : " + result);
			if (result.getCode().equals(200))
				return result.getStatus().equals("1");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public void sendSystemMessage(BaseMessage message, String... targetIds)
	{
		try
		{
			SystemMessage systemMessage = new SystemMessage() //
					.setSenderId("root") //
					.setTargetId(targetIds) //
					.setObjectName(message.getType()) //
					.setContent(message);
			ResponseResult result = getRongCloud().message.system.send(systemMessage);
			if (!result.getCode().equals(200))
				LOG.v("发送系统消息失败：" + result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void sendPrivateMessage(BaseMessage message, String senderId, String... targetIds)
	{
		try
		{
			PrivateMessage privateMessage = new PrivateMessage() //
					.setSenderId(senderId) //
					.setTargetId(targetIds) //
					.setObjectName(message.getType()) //
					.setContent(message);
			ResponseResult result = getRongCloud().message.msgPrivate.send(privateMessage);
			if (!result.getCode().equals(200))
				LOG.v("发送私聊消息失败：" + result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void createGroup(String groupId, String groupName, String... memberIds)
	{
		try
		{
			GroupMember[] members = new GroupMember[memberIds.length];
			for (int i = 0; i < members.length; i++)
			{
				members[i] = new GroupMember().setId(memberIds[i]);
			}
			GroupModel group = new GroupModel().setId(groupId).setName(groupName).setMembers(members);
			Result result = getRongCloud().group.create(group);
			if (!result.getCode().equals(200))
				LOG.v("创建组失败：" + result);
			else
				LOG.v("创建组成功：" + result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
