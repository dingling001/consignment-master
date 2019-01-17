//import com.bootdo.BootdoApplication;
//import com.bootdo.common.domain.Tree;
//import com.bootdo.common.utils.BuildDeptTree;
//import com.bootdo.system.dao.DeptDao;
//import com.bootdo.system.dao.UserDao;
//import com.bootdo.system.domain.DeptDO;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Copyright (C) 2014-2016 天津紫藤科技有限公司. Co. Ltd. All Rights Reserved.
// *
// * @author Mr.liang
// * @version v1
// * @description PACKAGE_NAME bootdo
// * @serve
// * @module
// * @date 2018/7/3
// * @code
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = BootdoApplication.class)
//public class DataTest {
//    @Autowired
//    DeptDao deptMapper;
//    @Autowired
//    UserDao userMapper;
//    @Test
//    public void findAuthUser() {
//        List<Tree<DeptDO>> trees = new ArrayList<>();
//        List<DeptDO> sysDepts = deptMapper.list(new HashMap<>(16));
//        for (DeptDO sysDept : sysDepts) {
//            Tree<DeptDO> tree = new Tree<>();
//            tree.setId(sysDept.getDeptId().toString());
//            tree.setParentId(sysDept.getParentId().toString());
//            tree.setText(sysDept.getName());
//            Map<String, Object> state = new HashMap<>(16);
//            state.put("opened", true);
//            tree.setState(state);
//            trees.add(tree);
//        }
//        List<Tree<DeptDO>> treeList = BuildDeptTree.buildList(trees,"0");
//        List<String> idList = new ArrayList<>();
//        idList.add("0");
//        for (Tree<DeptDO> tree : treeList) {
//            System.out.println(tree);
//            String id = tree.getId();
//            idList.add(id);
//            System.out.println("id============>"+id);
//        }
////        List<UserDO> userDOList = userMapper.listAuthUser(idList);
////        System.out.println(userDOList.toString());
//    }
//}
