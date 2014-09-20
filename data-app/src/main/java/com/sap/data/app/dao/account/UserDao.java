package com.sap.data.app.dao.account;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sap.data.app.entity.account.User;

/**
 * 用户对象的Dao interface.
 * 
 */
public interface UserDao extends PagingAndSortingRepository<User, Long> {

	User findByLoginName(String loginName);
}
