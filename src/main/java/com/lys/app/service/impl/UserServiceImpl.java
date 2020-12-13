package com.lys.app.service.impl;

import com.lys.app.config.AppConfig;
import com.lys.app.dao.FriendDao;
import com.lys.app.dao.TaskDao;
import com.lys.app.dao.UserDao;
import com.lys.app.manager.AppSecurityConfigurer;
import com.lys.app.manager.RongHelper;
import com.lys.app.manager.SVNManager;
import com.lys.app.manager.SendSms;
import com.lys.app.messages.TransMessage;
import com.lys.app.other.ApiRuntimeException;
import com.lys.app.service.FriendService;
import com.lys.app.service.UserService;
import com.lys.protobuf.*;
import com.lys.utils.LOG;
import com.lys.utils.RandomHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tmatesoft.svn.core.SVNException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService, UserDetailsService
{
	@Autowired
	private UserDao userDao = null;

	@Autowired
	private FriendDao friendDao = null;

	@Autowired
	private TaskDao taskDao = null;

	@Autowired
	private FriendService friendService = null;

	@Autowired
	private AppSecurityConfigurer securityConfigurer = null;

	@Autowired
	private RongHelper rongHelper = null;

	@Autowired
	private AppConfig appConfig = null;

	@Autowired
	private SVNManager svnManager = null;

	private final ConcurrentHashMap<String, String> phoneCodeMap = new ConcurrentHashMap<>();

	@PostConstruct
	public void init()
	{
		LOG.v(this.getClass().getSimpleName() + " init ...");
		userDao.createTable();
		AddUserIfNotExists("root", SUserType.SupterMaster, "0813", "超级管理员", AppConfig.defaultHead);
		AddUserIfNotExists("mst1", SUserType.Master, "123", "管理员1", AppConfig.defaultHead);
		AddUserIfNotExists("mst2", SUserType.Master, "123", "管理员2", AppConfig.defaultHead);
		AddUserIfNotExists("mst3", SUserType.Master, "123", "管理员3", AppConfig.defaultHead);
	}

	private void AddUserIfNotExists(String id, Integer userType, String psw, String name, String head)
	{
		if (userDao.get(id) == null)
		{
			SUser user = new SUser();
			user.id = id;
			user.userType = userType;
			user.psw = psw;
			user.name = name;
			user.head = head;
			user.createTime = System.currentTimeMillis();
			userDao.insert(user);
		}
	}

	@Override
	public SResponse_UserPhoneCode UserPhoneCode(SRequest_UserPhoneCode request)
	{
		SResponse_UserPhoneCode response = new SResponse_UserPhoneCode();

		String phone = request.phone;
		if (StringUtils.isEmpty(phone))
			throw new ApiRuntimeException("手机号为空");

		if (request.type.equals(SPhoneCodeType.Reg))
		{
			if (userDao.get(phone) != null)
				throw new ApiRuntimeException("手机号已存在，可直接登录");

			String code = RandomHelper.RandNumberString(4);
			if (!SendSms.send(phone, SendSms.AppMath, SendSms.TemplateReg, code))
				throw new ApiRuntimeException("验证码发送失败");
			phoneCodeMap.put(phone, code);
			LOG.v(phone + "获取注册验证码：" + code);
		}
		else if (request.type.equals(SPhoneCodeType.Login))
		{
			if (userDao.get(phone) == null)
				throw new ApiRuntimeException("手机号未注册");

			String code = RandomHelper.RandNumberString(4);
			if (!SendSms.send(phone, SendSms.AppMath, SendSms.TemplateLogin, code))
				throw new ApiRuntimeException("验证码发送失败");
			phoneCodeMap.put(phone, code);
			LOG.v(phone + "获取登录验证码：" + code);
		}

		return response;
	}

	@Override
	public SResponse_UserReg UserReg(SRequest_UserReg request)
	{
		SResponse_UserReg response = new SResponse_UserReg();

		String phone = request.phone;
		if (StringUtils.isEmpty(phone))
			throw new ApiRuntimeException("手机号为空");

		if (userDao.get(phone) != null)
			throw new ApiRuntimeException("手机号已存在，可直接登录");

		if (!phoneCodeMap.containsKey(phone) || !phoneCodeMap.get(phone).equals(request.code))
			throw new ApiRuntimeException("验证码错误或无效");

		String id;
		do
		{
			id = RandomHelper.RandNumberStringWithout4(8);
		} while (userDao.get(id) != null);

		SUser user = new SUser();
		user.id = id;
		user.userType = SUserType.Student;
		user.psw = "123";
		user.name = "未命名";
		user.head = AppConfig.defaultHead;
		user.createTime = System.currentTimeMillis();
		user.sex = SSex.Unknow;
		user.grade = 0;
		user.phone = phone;
		userDao.insert(user);

		String defaultMst = AppConfig.defaultMst2;

		friendService.addFriendImpl(id, defaultMst);
		friendService.addFriendImpl(defaultMst, id);

//		rongHelper.sendPrivateMessage(new TransMessage(AppConfig.TransEvt_FriendChange, null), id, defaultMst);

		response.userId = user.id;
		response.psw = user.psw;

		phoneCodeMap.remove(phone);

		return response;
	}

	@Override
	public SResponse_UserLogin UserLogin(SRequest_UserLogin request)
	{
		SResponse_UserLogin response = new SResponse_UserLogin();

		String idOrPhone = request.userId;
		String pswOrCode = request.psw;

		if (StringUtils.isEmpty(idOrPhone))
			throw new ApiRuntimeException("账号为空");

		LOG.v("login userId : " + request.userId);
		LOG.v("login psw : " + request.psw);

		SUser user = userDao.get(idOrPhone);
		if (user == null)
			throw new ApiRuntimeException("用户不存在");

		boolean pswOrCodeIsPass = false;
		if (!pswOrCodeIsPass)
		{
			String phone = user.phone;
			if (!StringUtils.isEmpty(phone))
			{
				if (phoneCodeMap.containsKey(phone) && phoneCodeMap.get(phone).equals(pswOrCode))
				{
					pswOrCodeIsPass = true;
					phoneCodeMap.remove(phone);
				}
			}
		}
		if (!pswOrCodeIsPass)
		{
			if (user.psw.equals(pswOrCode))
				pswOrCodeIsPass = true;
		}
		if (!pswOrCodeIsPass)
			throw new ApiRuntimeException("密码或验证码错误");

		if (user.useRong == 1 && StringUtils.isEmpty(user.token))
		{
			LOG.v("get token ...");
			String token = rongHelper.getToken(user.id, user.name, StringUtils.isEmpty(user.head) ? AppConfig.defaultHead : appConfig.checkUrl(user.head));
			if (StringUtils.isEmpty(token))
				throw new ApiRuntimeException("获取token失败");
			user.token = token;
			userDao.update(user);
		}

		response.user = user;

		return response;
	}

	@Override
	public SResponse_GetUser GetUser(SRequest_GetUser request)
	{
		SResponse_GetUser response = new SResponse_GetUser();
		response.user = userDao.get(request.userId);
		return response;
	}

	@Override
	public SResponse_ModifyHead ModifyHead(SRequest_ModifyHead request)
	{
		SResponse_ModifyHead response = new SResponse_ModifyHead();
		SUser user = userDao.get(request.userId);
		if (user == null)
			throw new ApiRuntimeException("用户不存在");
		user.head = request.head;
		userDao.update(user);
//		rongHelper.refreshUserInfo(user.id, user.name, appConfig.checkUrl(user.head));
		return response;
	}

	@Override
	public SResponse_ModifyName ModifyName(SRequest_ModifyName request)
	{
		SResponse_ModifyName response = new SResponse_ModifyName();
		SUser user = userDao.get(request.userId);
		if (user == null)
			throw new ApiRuntimeException("用户不存在");
		user.name = request.name;
		userDao.update(user);
//		rongHelper.refreshUserInfo(user.id, user.name, appConfig.checkUrl(user.head));
		return response;
	}

	@Override
	public SResponse_ModifySex ModifySex(SRequest_ModifySex request)
	{
		SResponse_ModifySex response = new SResponse_ModifySex();
		SUser user = userDao.get(request.userId);
		if (user == null)
			throw new ApiRuntimeException("用户不存在");
		user.sex = request.sex;
		userDao.update(user);
		return response;
	}

	@Override
	public SResponse_ModifyGrade ModifyGrade(SRequest_ModifyGrade request)
	{
		SResponse_ModifyGrade response = new SResponse_ModifyGrade();
		SUser user = userDao.get(request.userId);
		if (user == null)
			throw new ApiRuntimeException("用户不存在");
		user.grade = request.grade;
		userDao.update(user);
		return response;
	}

	@Override
	public SResponse_ModifyPsw ModifyPsw(SRequest_ModifyPsw request)
	{
		SResponse_ModifyPsw response = new SResponse_ModifyPsw();
		SUser user = userDao.get(request.userId);
		if (user == null)
			throw new ApiRuntimeException("用户不存在");
		if (!user.psw.equals(request.oldPsw))
			throw new ApiRuntimeException("旧密码错误");
		user.psw = request.newPsw;
		userDao.update(user);
		return response;
	}

	@Override
	public SResponse_GetUserList GetUserList(SRequest_GetUserList request)
	{
		SResponse_GetUserList response = new SResponse_GetUserList();
		response.users = userDao.getList(request.userType);
		return response;
	}

	@Override
	public SResponse_AddUser AddUser(SRequest_AddUser request)
	{
		SResponse_AddUser response = new SResponse_AddUser();

		String id = request.userId;
		if (StringUtils.isEmpty(id))
		{
			do
			{
				id = RandomHelper.RandNumberStringWithout4(8);
			} while (userDao.get(id) != null);
		}
		else
		{
			if (userDao.get(id) != null)
				throw new ApiRuntimeException("该用户不能重复添加");
		}

		SUser user = new SUser();
		user.id = id;
		user.userType = request.userType;
		user.psw = request.psw;
		user.name = request.name;
		user.head = StringUtils.isEmpty(request.head) ? AppConfig.defaultHead : request.head;
		user.createTime = System.currentTimeMillis();
		user.sex = request.sex;
		user.grade = request.grade;
		userDao.insert(user);

		String defaultMst;
		if (request.from == 1)
			defaultMst = AppConfig.defaultMst2;
		else
			defaultMst = AppConfig.defaultMst1;

		friendService.addFriendImpl(id, defaultMst);
		friendService.addFriendImpl(defaultMst, id);

//		rongHelper.sendPrivateMessage(new TransMessage(AppConfig.TransEvt_FriendChange, null), id, defaultMst);

		response.userId = user.id;
		response.psw = user.psw;

		return response;
	}

	@Override
	public SResponse_DeleteUser DeleteUser(SRequest_DeleteUser request)
	{
		SResponse_DeleteUser response = new SResponse_DeleteUser();

		SUser user = userDao.get(request.userId);
		if (user == null)
			throw new ApiRuntimeException("用户不存在");

		userDao.delete(request.userId);

		friendDao.deleteByUserId(request.userId);
		friendDao.deleteByFriendId(request.userId);

		taskDao.deleteByUserId(request.userId);
		try
		{
			synchronized (svnManager)
			{
				svnManager.deleteIfExists(request.userId, "delete user by server");
			}
		}
		catch (SVNException e)
		{
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public SResponse_SetVip SetVip(SRequest_SetVip request)
	{
		SResponse_SetVip response = new SResponse_SetVip();
		SUser user = userDao.get(request.userId);
		if (user == null)
			throw new ApiRuntimeException("用户不存在");
		user.vipLevel = request.vipLevel;
		user.vipTime = request.vipTime;
		userDao.update(user);
		return response;
	}

	@Override
	public SResponse_SetCp SetCp(SRequest_SetCp request)
	{
		SResponse_SetCp response = new SResponse_SetCp();
		if (true)
			throw new ApiRuntimeException("接口已废弃");
		return response;
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException
	{
		SUser user = userDao.get(id);
		if (user != null)
		{
			List<GrantedAuthority> authorityList = new ArrayList<>();
			if (user.userType.equals(SUserType.SupterMaster))
			{
				authorityList.add(new SimpleGrantedAuthority(AppConfig.ROLE_SupterMaster));
				authorityList.add(new SimpleGrantedAuthority(AppConfig.ROLE_Master));
				authorityList.add(new SimpleGrantedAuthority(AppConfig.ROLE_Teacher));
			}
			else if (user.userType.equals(SUserType.Master))
			{
				authorityList.add(new SimpleGrantedAuthority(AppConfig.ROLE_Master));
				authorityList.add(new SimpleGrantedAuthority(AppConfig.ROLE_Teacher));
			}
			else if (user.userType.equals(SUserType.Teacher))
			{
				authorityList.add(new SimpleGrantedAuthority(AppConfig.ROLE_Teacher));
			}
			else if (user.userType.equals(SUserType.Student))
			{
				authorityList.add(new SimpleGrantedAuthority(AppConfig.ROLE_Student));
			}
			return new User(user.id, securityConfigurer.encodePsw(user.psw), authorityList);
		}
		return null;
	}
}