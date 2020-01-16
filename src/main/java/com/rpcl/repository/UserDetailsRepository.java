package com.rpcl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rpcl.entity.UserEntity;

/**
 * This interface is used to interact with database for CRUD operation
 * @author Ritesh
 *
 */
@Repository
public interface UserDetailsRepository extends JpaRepository<UserEntity, Integer> {

	@Query(value = "from UserEntity where userEmail=:email and pazzword=:pwd")
	public UserEntity getEntityByEmailAndPassword(String email,String pwd);
	
	
	public UserEntity findByUserEmail(String userEmail);
}
