package com.rpcl.domain;

import lombok.Data;

/**
 * This class is used to bind the userLogin form
 * @author Ritesh
 *
 */
@Data
public class UserLoginInfo {
	private String userEmail;
	private String password;

}
