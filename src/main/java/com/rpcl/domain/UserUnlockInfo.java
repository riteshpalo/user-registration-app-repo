package com.rpcl.domain;

import lombok.Data;

/**
 * This class is used to bind the userUnlock form
 * @author Ritesh
 *
 */
@Data
public class UserUnlockInfo {
	private String userEmail;
	private String tempPwd;
	private String newPwd;
	private String cnfPwd;

}
