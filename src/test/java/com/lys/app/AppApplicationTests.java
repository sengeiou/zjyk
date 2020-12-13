package com.lys.app;

import com.lys.app.dao.FriendDao;
import com.lys.app.dao.TaskDao;
import com.lys.app.dao.TeachRecordDao;
import com.lys.app.service.ConfigService;
import com.lys.app.service.EventService;
import com.lys.app.service.TaskService;
import com.lys.app.service.TeachRecordService;
import com.lys.protobuf.*;
import com.lys.utils.LOG;
import com.lys.utils.LOGJson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AppApplicationTests
{
	@Autowired
	private TaskDao taskDao = null;

	@Autowired
	private TaskService taskService = null;

	@Autowired
	private FriendDao friendDao = null;

	@Autowired
	private TeachRecordDao teachRecordDao = null;

	@Autowired
	private TeachRecordService teachRecordService = null;

	@Autowired
	private ConfigService configService = null;

	@Autowired
	private EventService eventService = null;

	@Test
	void contextLoads()
	{
		if (false)
		{
			for (int i = 0; i < 1; i++)
			{
				SRequest_Test request = new SRequest_Test();
				SResponse_Test response = configService.Test(request);
				LOGJson.log(response.saveToStr());
			}
		}

		if (false)
		{
			SRequest_GetEventList request = new SRequest_GetEventList();
			request.userId = "mst1";
			request.actions.add("InTask");
			request.actions.add("OutTask");
			SResponse_GetEventList response = eventService.GetEventList(request);
			LOGJson.log(response.saveToStr());
		}

		if (false)
		{
			List<String> keywords = new ArrayList<String>();
			keywords.add("上班");
			keywords.add("刚V");
			taskDao.search(null, null, null, null, null, keywords, 0, 10);
			taskDao.searchCount(null, null, null, null, null, keywords);
		}

		if (false)
		{
			SRequest_AddEvent request = new SRequest_AddEvent();
			request.event = new SEvent();
			request.event.userId = "root";
			request.event.action = "InTask";
			request.event.target = "3080fa62-6d37-4c93-a539-6519958af09c";
			request.event.des = "进入《刚刚》";
			SResponse_AddEvent response = eventService.AddEvent(request);
			LOGJson.log(response.saveToStr());
		}

		if (false)
		{
			SRequest_TeachStart request = new SRequest_TeachStart();
			request.teachId = "1111111111111111111111";
			request.userId = "root";
			request.targetIds.add("mst1");
			request.targetIds.add("mst2");
			request.taskId = "1d291485-db06-4a38-85bc-17e914b02cd1";
			SResponse_TeachStart response = teachRecordService.TeachStart(request);
			LOGJson.log(response.saveToStr());

//			STeachRecord teachRecord = teachRecordDao.get("6c394f5d-b19b-43db-90a5-0322d81b8694", "lysls");
//			LOGJson.log(teachRecord.saveToStr());

//			List<STeachRecord> teachRecords = teachRecordDao.find("6c394f5d-b19b-43db-90a5-0322d81b8694");
//			LOG.v(teachRecords.size());
//			LOGJson.log(STeachRecord.saveList(teachRecords).toString());
		}

		if (false)
		{
			SFriend friend = friendDao.get("mst1", "root");
			LOGJson.log(friend.saveToStr());

			List<SUser> friends = friendDao.getList("mst1");
			LOG.v(friends.size());
			LOGJson.log(SUser.saveList(friends).toString());
		}

		if (false)
		{
			SPTask task = taskDao.get("66a55189-ec93-49d4-9e06-c8d9d9727d9f");
			LOGJson.log(task.saveToStr());
		}

		if (false)
		{
			List<SPTask> tasks = taskDao.find("上班除非", "默认分组");
			LOGJson.log(SPTask.saveList(tasks).toString());
		}

		if (false)
		{
			SRequest_GetTaskList request = new SRequest_GetTaskList();
			request.userId = "mst2";
			SResponse_GetTaskList response = taskService.GetTaskList(request);
			LOG.v(response.tasks.size());
		}

		if (false)
		{
			SRequest_CreateTask request = new SRequest_CreateTask();
			request.userId = "mst2";
			request.type = SPTaskType.Job;
			request.jobType = SPJobType.MultTopic;
			request.group = "组啊";
			request.name = "名字啊";
			SResponse_CreateTask response = taskService.CreateTask(request);
			LOGJson.log(response.saveToStr());
		}
	}

}
