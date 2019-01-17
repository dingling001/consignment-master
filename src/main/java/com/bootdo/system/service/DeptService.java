package com.bootdo.system.service;

import com.bootdo.common.domain.Tree;
import com.bootdo.system.domain.DeptDO;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:28:36
 */
public interface DeptService {
	
	DeptDO get(Long deptId);

	DeptDO getTop(Long deptId);

	DeptDO getDept(Map<String, Object> map);
	
	List<DeptDO> list(Map<String, Object> map);

	List<DeptDO> listEnableDept(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DeptDO sysDept);
	
	int update(DeptDO sysDept);
	
	int remove(Long deptId);
	
	int batchRemove(Long[] deptIds);

	Tree<DeptDO> getTree(Map<String,Object> params);
	
	boolean checkDeptHasUser(Long deptId);
}
