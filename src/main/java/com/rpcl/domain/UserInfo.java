package com.rpcl.domain;

import lombok.Data;
/**
 * This class is used to bind the registrationForm
 * @author Ritesh
 *
 */
@Data
public class UserInfo {
	private String userName;
	private String userEmail;
	private Long userContactNo; 
}
