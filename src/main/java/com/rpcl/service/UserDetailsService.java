package com.rpcl.service;

import com.rpcl.domain.UserForgotPwdInfo;
import com.rpcl.domain.UserInfo;
import com.rpcl.domain.UserLoginInfo;
import com.rpcl.domain.UserUnlockInfo;

/**
 * This interface is used to write business logic
 * @author Ritesh
 *
 */
public interface UserDetailsService {
	public boolean registerUser(UserInfo userInfo);
	public boolean getPasswordByEmail(UserUnlockInfo userUnlockInfo);
	public boolean checkEmailAndPwdValid(UserLoginInfo userLoginInfo);
	public boolean checkUserStatus(UserLoginInfo userLoginInfo);
	public boolean checkUserByEmail(UserForgotPwdInfo userForgotPwdInfo);
	public boolean checkStatusByEmail(UserForgotPwdInfo userForgotPwdInfo);
	public String checkUserByEmail(String email);
}
