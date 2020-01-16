package com.rpcl.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rpcl.constant.AppConstants;
import com.rpcl.domain.UserForgotPwdInfo;
import com.rpcl.domain.UserInfo;
import com.rpcl.domain.UserLoginInfo;
import com.rpcl.domain.UserUnlockInfo;
import com.rpcl.entity.UserEntity;
import com.rpcl.repository.UserDetailsRepository;
import com.rpcl.utils.EmailUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to write the business logic
 * @author Ritesh
 *
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private UserDetailsRepository userDtlsRepo;
	
	/**
	 * This method is used to register user by passing UserInfo
	 */
	@Override
	public boolean registerUser(UserInfo userInfo) {
		UserEntity userEntity=new UserEntity();
		BeanUtils.copyProperties(userInfo, userEntity);
		userEntity.setStatus(AppConstants.LOCKED);
		userEntity.setPazzword(generateRandomPassword(8));
		userEntity = userDtlsRepo.save(userEntity);
		boolean isSaved=userEntity.getUserId()>0;
		if(isSaved)
			sendMailToUnlockAccount(userEntity);
		return isSaved;
	}
	
	/**
	 * This method is used to send mail to unlock the account
	 * @param userEntity
	 */
	private void sendMailToUnlockAccount(UserEntity userEntity) {
		String to=userEntity.getUserEmail();
		String subject="Unlock Account";
		String body;
		try {
			body = getBody(userEntity);
			emailUtils.sendMailWithHiperlink(to, subject, body);
		} catch (Exception e1) {
			log.error("Exception During Sending Mail To Unlock Account", e1);
		}
	}
	
	/**
	 * This method is used to get body of mail from email-template.txt file to unlock the account
	 * @param userEntity
	 * @return String
	 * @throws IOException
	 */
	private String getBody(UserEntity userEntity) throws IOException {
		File file=new File("email-template.txt");
		FileReader reader=new FileReader(file);
		BufferedReader br=new BufferedReader(reader);
		String line;
		StringBuilder builder=new StringBuilder();
		while((line=br.readLine())!=null) {
			if(line.contains(AppConstants.NAME_PLACEHOLDER)) {
				line=line.replace(AppConstants.NAME_PLACEHOLDER, userEntity.getUserName());
			}
			if(line.contains(AppConstants.PSWD_PLACEHOLDER)) {
				line=line.replace(AppConstants.PSWD_PLACEHOLDER, userEntity.getPazzword());
			}
			if(line.contains(AppConstants.EMAIL_PLACEHOLDER)) {
				line=line.replace(AppConstants.EMAIL_PLACEHOLDER, userEntity.getUserEmail());
			}
			builder.append(line);
		}
		br.close();
		return builder.toString();
	}

	/**
	 * This method is used to generate alphanumeric random password 
	 * @param len
	 * @return String
	 */
	private String generateRandomPassword( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
	
	/**
	 * This method is used to get the password from database table by passing mail to unlock the account by checking the password 
	 */
	@Override
	public boolean getPasswordByEmail(UserUnlockInfo userUnlockInfo) {
		UserEntity userEntity = userDtlsRepo.getEntityByEmailAndPassword(userUnlockInfo.getUserEmail(), userUnlockInfo.getTempPwd());
		if(userEntity!=null) {
			userEntity.setPazzword(userUnlockInfo.getNewPwd());
			userEntity.setStatus(AppConstants.UNLOCKED);
			userEntity = userDtlsRepo.save(userEntity);
		}
		
		return userEntity!=null;
	}
	
	/**
	 * This method is used to get entity by passing password and email to check valid credentials or not
	 */
	@Override
	public boolean checkEmailAndPwdValid(UserLoginInfo userLoginInfo) {
		UserEntity userEntity = userDtlsRepo.getEntityByEmailAndPassword(userLoginInfo.getUserEmail(), userLoginInfo.getPassword());
		return userEntity!=null;
		
	}

	/**
	 * This method is used to get entity by passing password and mail to check the status is unlocked or not
	 */
	@Override
	public boolean checkUserStatus(UserLoginInfo userLoginInfo) {
		UserEntity userEntity = userDtlsRepo.getEntityByEmailAndPassword(userLoginInfo.getUserEmail(), userLoginInfo.getPassword());
		boolean flag=false;
		if(userEntity!=null) {
			flag=AppConstants.UNLOCKED.equals(userEntity.getStatus());
		}
		return flag;
	}

	/**
	 * This method is used to get entity by passing mail to check the valid email or not
	 */
	@Override
	public boolean checkUserByEmail(UserForgotPwdInfo userForgotPwdInfo) {
		UserEntity userEntity = userDtlsRepo.findByUserEmail(userForgotPwdInfo.getUserEmail());
		return userEntity!=null;
	}
	
	/**
	 * This method is used to get entity by passing mail to check the status is unlocked or not to perform recovery mail functionality
	 */
	@Override
	public boolean checkStatusByEmail(UserForgotPwdInfo userForgotPwdInfo) {
		UserEntity userEntity = userDtlsRepo.findByUserEmail(userForgotPwdInfo.getUserEmail());
		boolean flag=false;
		if(userEntity!=null ) {
			flag=AppConstants.UNLOCKED.equals(userEntity.getStatus());
			if(flag) {
				sendMailWithPwdToRecoveryAccount(userEntity);
			}
		}
		return flag;
	}
	
	/**
	 * This method is used to send recovery mail for forgot password functionality 
	 * @param userEntity
	 */
	private void sendMailWithPwdToRecoveryAccount(UserEntity userEntity) {
		String to=userEntity.getUserEmail();
		String subject="Recovery Account";
		String body;
		try {
			body = getBodyForRecoveryPwd(userEntity);
			emailUtils.sendMailWithHiperlink(to, subject, body);
		} catch (Exception e1) {
			log.error("Exception During Sending Mail To Recovery Account", e1);
		}
	}
	
	/**
	 * This method is used to get body of recovery mail from email-template-recovery.txt file for forgot password functionality
	 * @param userEntity
	 * @return String
	 * @throws IOException
	 */
	private String getBodyForRecoveryPwd(UserEntity userEntity) throws IOException {
		File file=new File("email-template-recovery.txt");
		FileReader reader=new FileReader(file);
		BufferedReader br=new BufferedReader(reader);
		String line;
		StringBuilder builder=new StringBuilder();
		while((line=br.readLine())!=null) {
			if(line.contains(AppConstants.NAME_PLACEHOLDER)) {
				line=line.replace(AppConstants.NAME_PLACEHOLDER, userEntity.getUserName());
			}
			if(line.contains(AppConstants.PSWD_PLACEHOLDER)) {
				line=line.replace(AppConstants.PSWD_PLACEHOLDER, userEntity.getPazzword());
			}
			builder.append(line);
		}
		br.close();
		return builder.toString();
	}

	/**
	 * This method is used to check for user in the database by passing email as argument
	 * @return String
	 */
	@Override
	public String checkUserByEmail(String email) {
		UserEntity userEntity = userDtlsRepo.findByUserEmail(email);
		if(userEntity!=null) {
			return "Duplicate";
		}
		return "Unique";
	}

}
