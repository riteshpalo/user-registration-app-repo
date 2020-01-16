package com.rpcl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * This class is used to map with database table 
 * @author Ritesh
 *
 */
@Entity
@Table(name = "USER_MASTER")
@Data
public class UserEntity {
	@Id
	@GeneratedValue
	@Column(name = "USER_ID",length = 10)
	private Integer userId;
	@Column(name = "USER_NAME",length = 50)
	private String userName;
	@Column(name = "USER_EMAIL",length = 50)
	private String userEmail;
	@Column(name = "USER_CONTACT_NO",length = 10)
	private Long userContactNo; 
	@Column(name = "STATUS",length = 20)
	private String status;
	@Column(name = "PASSWORD",length = 10)
	private String pazzword;
	

}
