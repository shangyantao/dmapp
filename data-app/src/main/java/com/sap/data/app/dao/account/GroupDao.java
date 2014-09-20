package com.sap.data.app.dao.account;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sap.data.app.entity.account.Group;

/**
 * 权限组对象的Dao interface.
 * 
 */

public interface GroupDao extends PagingAndSortingRepository<Group, Long>, GroupDaoCustom {
}
