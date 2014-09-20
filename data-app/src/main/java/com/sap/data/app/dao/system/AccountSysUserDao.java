package com.sap.data.app.dao.system;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sap.data.app.entity.system.AccountSysUser;

public interface AccountSysUserDao extends PagingAndSortingRepository<AccountSysUser, String> {

}
