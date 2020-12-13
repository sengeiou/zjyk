package com.lys.app.service;

import com.lys.protobuf.*;

public interface UserService
{
	SResponse_UserPhoneCode UserPhoneCode(SRequest_UserPhoneCode request);

	SResponse_UserReg UserReg(SRequest_UserReg request);

	SResponse_UserLogin UserLogin(SRequest_UserLogin request);

	SResponse_GetUser GetUser(SRequest_GetUser request);

	SResponse_ModifyHead ModifyHead(SRequest_ModifyHead request);

	SResponse_ModifyName ModifyName(SRequest_ModifyName request);

	SResponse_ModifySex ModifySex(SRequest_ModifySex request);

	SResponse_ModifyGrade ModifyGrade(SRequest_ModifyGrade request);

	SResponse_ModifyPsw ModifyPsw(SRequest_ModifyPsw request);

	SResponse_GetUserList GetUserList(SRequest_GetUserList request);

	SResponse_AddUser AddUser(SRequest_AddUser request);

	SResponse_DeleteUser DeleteUser(SRequest_DeleteUser request);

	SResponse_SetVip SetVip(SRequest_SetVip request);

	SResponse_SetCp SetCp(SRequest_SetCp request);
}