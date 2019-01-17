package com.bootdo.system.service.impl;

import com.bootdo.common.config.Constant;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.utils.BuildDeptTree;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.dao.DeptDao;
import com.bootdo.system.dao.UserDao;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.DeptService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DeptServiceImpl implements DeptService {
    private static Logger logger = LoggerFactory.getLogger(DeptServiceImpl.class);
    @Autowired
    private DeptDao sysDeptMapper;
    @Autowired
    private UserDao userDao;

    @Override
    public DeptDO get(Long deptId) {
        return sysDeptMapper.get(deptId);
    }

    @Override
    public DeptDO getTop(Long deptId) {
        return sysDeptMapper.getTop(deptId);
    }

    @Override
    public DeptDO getDept(Map<String, Object> map) {
        return sysDeptMapper.getDept(map);
    }

    @Override
    public List<DeptDO> list(Map<String, Object> map) {
        UserDO user = ShiroUtils.getUser();
        Long deptId = user.getDeptId();
        DeptDO deptDO = sysDeptMapper.get(deptId);
        List<DeptDO> deptDOList = new ArrayList<>();
        //此一步是为了jstree实现树形分级，必须将顶部的parentId设置为0
        deptDO.setParentId(0l);
        deptDOList.add(deptDO);
        getChildren(deptDO,deptDOList);
        //System.out.println(deptDOList.size());
        //deptDOList.forEach(deptDO1 -> System.out.println(deptDO1.toString()));
//        return sysDeptMapper.list(map);
        return deptDOList;
    }

    private void getChildren(DeptDO deptDO,List<DeptDO> deptDOList) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("parentId", deptDO.getDeptId());
        List<DeptDO> deptList = sysDeptMapper.list(param);
        if (deptList.size() > 0) {
            deptDOList.addAll(deptList);
        }
        deptList.forEach(dept -> {
            getChildren(dept,deptDOList);
        });
    }

    @Override
    public List<DeptDO> listEnableDept(Map<String, Object> map) {
        return sysDeptMapper.listEnableDept(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return sysDeptMapper.count(map);
    }

    @Override
    public int save(DeptDO sysDept) {
        return sysDeptMapper.save(sysDept);
    }

    @Override
    public int update(DeptDO sysDept) {
        return sysDeptMapper.update(sysDept);
    }

    @Override
    public int remove(Long deptId) {
        return sysDeptMapper.remove(deptId);
    }

    @Override
    public int batchRemove(Long[] deptIds) {
        return sysDeptMapper.batchRemove(deptIds);
    }

    @Override
    public Tree<DeptDO> getTree(Map<String,Object> params) {
        UserDO userDO = (UserDO) SecurityUtils.getSubject().getPrincipal();
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> sysDepts = sysDeptMapper.list(params);
        Long deptId = userDO.getDeptId();
        DeptDO deptDO = sysDeptMapper.get(deptId);

        for (DeptDO sysDept : sysDepts) {

            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(sysDept.getDeptId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);

            //第一级菜单展开，其他折叠
            if (Constant.DEPT_ROOT_PARENT_ID.equals(sysDept.getParentId())) {
                state.put("opened", true);
            } else {
                state.put("opened", false);
            }
            tree.setState(state);
            trees.add(tree);
        }


//        logger.info("获取到用户信息：{}",userDO);
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildDeptTree.build(trees, deptId.toString(), deptDO.getName(),params.get("payment").toString());
        return t;
    }

    @Override
    public boolean checkDeptHasUser(Long deptId) {
        // TODO Auto-generated method stub
        //查询部门以及此部门的下级部门
        int result = sysDeptMapper.getDeptUserNumber(deptId);
        return result == 0 ? true : false;
    }

}
