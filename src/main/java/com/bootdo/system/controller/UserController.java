package com.bootdo.system.controller;

import com.bootdo.common.annotation.Log;
import com.bootdo.common.config.Constant;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.utils.*;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.RoleDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.DeptService;
import com.bootdo.system.service.RoleService;
import com.bootdo.system.service.UserService;
import com.bootdo.system.utils.RandomUtils;
import com.bootdo.system.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequestMapping("/sys/user")
@Controller
public class UserController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	private String prefix= "system/user";
	@Autowired
	UserService userService;
	@Autowired
	DeptService deptService;
	@Autowired
	RoleService roleService;

	private static String randomNumber;


	@RequiresPermissions("sys:user:user")
	@GetMapping("/index")
	String user(Model model) {
		model.addAttribute("prefix",appPrefix);
		String deptId = ((UserDO)SecurityUtils.getSubject().getPrincipal()).getDeptId().toString();
		model.addAttribute("deptId",deptId);
		return prefix + "/user";
	}


    @GetMapping("/devList")
    @ResponseBody
    PageUtils devList(@RequestParam Map<String,Object> params) {
        Long deptId = Long.valueOf(params.get("deptId").toString());
        DeptDO deptDO = deptService.get(deptId);
        //// TODO: 2018/7/4 禁用部门后有两种显示方式，第一种为部门及以下的部门都不会显示
        //// TODO: 2018/7/4 以下是第一种方式    开始
//		boolean f = checkIsEnabled(deptDO);
        List<UserDO> sysUserList = new ArrayList<>();
//		if (!f) {
//			PageUtils pageUtil = new PageUtils(sysUserList, 0);
//			return pageUtil;
//		}
        // // TODO: 2018/7/4 结束
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> sysDepts = deptService.list(new HashMap<String, Object>(16));
        for (DeptDO sysDept : sysDepts) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(sysDept.getDeptId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
//		logger.info("执行的参数trees：{}，deptId：{}，deptDO：{}",trees,deptId,deptDO);
        Tree<DeptDO> deptDOTree = BuildDeptTree.build(trees,deptId.toString(),deptDO.getName(),"0");
        List<String> idList = new ArrayList<>();
        idList.add(deptDOTree.getId());
        List<Tree<DeptDO>> treeList = deptDOTree.getChildren();
        getChildren(treeList,idList);
        //// TODO: 2018/7/4 第二种    开始
        List<DeptDO> deptDOList = deptService.listEnableDept(new HashMap<>(16));
//		logger.info("查询出的禁用的部门为{}个",deptDOList.size());
//		logger.info("去除禁用部门前，idList的个数为：{}",idList.size());
        for (DeptDO deptDO1 : deptDOList) {
            Long id = deptDO1.getDeptId();
            if (idList.contains(id.toString())) {
                boolean f = idList.remove(id.toString());
//				logger.info("f=========>>>>>{}",f);
            }
        }
//		logger.info("去除禁用部门后，idList的个数为：{}",idList.size());
        if (idList.size() == 0) {
            PageUtils pageUtil = new PageUtils(sysUserList, 0);
            return pageUtil;
        }
        //// TODO: 2018/7/4 结束
        params.put("list",idList);
        // 查询列表数据
        Query query = new Query(params);
        sysUserList = userService.devList(query);
        int total = userService.devCount(query);
        PageUtils pageUtil = new PageUtils(sysUserList, total);
        return pageUtil;
    }





	@GetMapping("/list")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<UserDO> sysUserList = userService.list(query);
		int total = userService.count(query);
		PageUtils pageUtil = new PageUtils(sysUserList, total);
		return pageUtil;
	}

	@GetMapping("/listAuthUsers")
	@ResponseBody
	PageUtils listAuthUsers(@RequestParam Map<String,Object> params) {
		Long deptId = Long.valueOf(params.get("deptId").toString());
		DeptDO deptDO = deptService.get(deptId);
		//// TODO: 2018/7/4 禁用部门后有两种显示方式，第一种为部门及以下的部门都不会显示
		//// TODO: 2018/7/4 以下是第一种方式    开始
//		boolean f = checkIsEnabled(deptDO);
		List<UserDO> sysUserList = new ArrayList<>();
//		if (!f) {
//			PageUtils pageUtil = new PageUtils(sysUserList, 0);
//			return pageUtil;
//		}
		// // TODO: 2018/7/4 结束
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> sysDepts = deptService.list(new HashMap<String, Object>(16));
		for (DeptDO sysDept : sysDepts) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(sysDept.getDeptId().toString());
			tree.setParentId(sysDept.getParentId().toString());
			tree.setText(sysDept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
//		logger.info("执行的参数trees：{}，deptId：{}，deptDO：{}",trees,deptId,deptDO);
		Tree<DeptDO> deptDOTree = BuildDeptTree.build(trees,deptId.toString(),deptDO.getName(),"0");
		List<String> idList = new ArrayList<>();
		idList.add(deptDOTree.getId());
		List<Tree<DeptDO>> treeList = deptDOTree.getChildren();
		getChildren(treeList,idList);
		//// TODO: 2018/7/4 第二种    开始
		List<DeptDO> deptDOList = deptService.listEnableDept(new HashMap<>(16));
//		logger.info("查询出的禁用的部门为{}个",deptDOList.size());
//		logger.info("去除禁用部门前，idList的个数为：{}",idList.size());
		for (DeptDO deptDO1 : deptDOList) {
			Long id = deptDO1.getDeptId();
			if (idList.contains(id.toString())) {
				boolean f = idList.remove(id.toString());
//				logger.info("f=========>>>>>{}",f);
			}
		}
//		logger.info("去除禁用部门后，idList的个数为：{}",idList.size());
		if (idList.size() == 0) {
			PageUtils pageUtil = new PageUtils(sysUserList, 0);
			return pageUtil;
		}
		//// TODO: 2018/7/4 结束
		params.put("list",idList);
		// 查询列表数据
		Query query = new Query(params);
		sysUserList = userService.listAuthUsers(query);
		int total = userService.countAuthUsers(query);
		PageUtils pageUtil = new PageUtils(sysUserList, total);
		return pageUtil;
	}

//	/**
//	 * 判断父节点是否有禁用的 第一种涉及到的方法
//	 * @param deptDO
//	 * @return
//     */
//	private boolean checkIsEnabled(DeptDO deptDO) {
//		boolean flag = false;
//		Integer delFlag = deptDO.getDelFlag();
//		if (delFlag == 1) {
//			flag = true;
//			DeptDO dept = deptService.get(deptDO.getParentId());
//			if (dept != null) {
////				boolean f = checkIsEnabled(dept);
//				return checkIsEnabled(dept);
//			}
//		}
//		return flag;
//	}

	private void getChildren(List<Tree<DeptDO>> list,List<String> idList) {
		if (list.size() > 0 && list != null) {
			for (Tree<DeptDO> tree : list) {
				String id = tree.getId();
				idList.add(id);
				getChildren(tree.getChildren(),idList);
			}
		}
	}


	@RequiresPermissions("sys:user:add")
	@Log("添加用户")
	@GetMapping("/add")
	String add(Model model) {
		model.addAttribute("prefix",appPrefix);
		List<RoleDO> roles = roleService.list();
		model.addAttribute("roles", roles);
		generateRandomNumber();
		model.addAttribute("randomNumber",randomNumber);
		return prefix + "/add";
	}

	private void generateRandomNumber() {
		//randomNumber = RandomUtils.generateString(6);
		randomNumber = RandomUtils.getNewAccount();
		Map<String,Object> query = new HashMap<>();
		query.put("uniqueNo",randomNumber);
		List<UserDO> list = userService.list(query);
		if (list.size() > 0) {
			generateRandomNumber();
		}
	}
	@RequiresPermissions(value = {"sys:user:edit","sys:user:check"},logical = Logical.OR)
	@Log("编辑用户")
	@GetMapping("/edit/{id}/{type}")
	String edit(Model model, @PathVariable("id") Long id,@PathVariable("type")String type) {
		model.addAttribute("prefix",appPrefix);
		UserDO userDO = userService.get(id);
//		String deadlineStr = DateUtils.format(userDO.getDeadline(),"yyyy-MM-dd");
		model.addAttribute("user", userDO);
//		model.addAttribute("deadline",deadlineStr);
		//List<RoleDO> roles = roleService.list(id);

        List<RoleDO> userRoles = roleService.getByUserId(id);
        System.out.println("userId = " + getUserId());
        System.out.println("userRoles = " + userRoles);

        List<RoleDO> roles = roleService.list();
		model.addAttribute("roles", roles);
		model.addAttribute("userRoles", userRoles);
		model.addAttribute("type",type);
		return prefix+"/edit";
	}

	@RequiresPermissions("sys:user:add")
	@Log("保存用户")
	@PostMapping("/save")
	@ResponseBody
	R save(UserDO user) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		//// TODO: 2018/7/31 保存用户加密方法 下面一行代码
		//// TODO 后台管理系统中增加用户不需要用户名和密码，只有注册时，增加用户名和密码
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		//// TODO: 2018/7/31 保存用户不加密 下面一行代码
		//user.setPassword(user.getPassword());
		Date date = new Date();
		user.setGmtCreate(date);
		user.setGmtModified(date);
        user.setIsDelete("0");
		DeptDO deptDO = deptService.get(user.getDeptId());
        user.setCityNumber("0".equals(deptDO.getDeptCity()) ? deptDO.getDeptProvince() : deptDO.getDeptCity());
		DeptDO top = deptService.getTop(deptDO.getDeptId());
		while (top.getDeptLevel() != 2){
			top = deptService.getTop(top.getDeptId());
		}


		user.setUniqueNo(String.valueOf(top.getDeptId()));


		if (userService.save(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:edit")
	@Log("更新用户")
	@PostMapping("/update")
	@ResponseBody
	R update(UserDO user) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
        //user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));

        Date date = new Date();
        user.setGmtModified(date);
        DeptDO deptDO = deptService.get(user.getDeptId());
        user.setCityNumber("0".equals(deptDO.getDeptCity()) ? deptDO.getDeptProvince() : deptDO.getDeptCity());
		DeptDO top = deptService.getTop(deptDO.getDeptId());
		while (top.getDeptLevel() != 2){
			top = deptService.getTop(top.getDeptId());
		}


		user.setUniqueNo(String.valueOf(top.getDeptId()));

		if (userService.update(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@RequiresPermissions("sys:user:edit")
	@Log("更新用户")
	@PostMapping("/updatePeronal")
	@ResponseBody
	R updatePeronal(UserDO user) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (userService.updatePersonal(user) > 0) {
			return R.ok();
		}
		return R.error();
	}


	@RequiresPermissions("sys:user:remove")
	@Log("删除用户")
	@PostMapping("/remove")
	@ResponseBody
	R remove(Long id) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (userService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:batchRemove")
	@Log("批量删除用户")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] userIds) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		int r = userService.batchremove(userIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !userService.exit(params);
	}

	@RequiresPermissions("sys:user:resetPwd")
	@Log("请求更改用户密码")
	@GetMapping("/resetPwd/{id}")
	String resetPwd(@PathVariable("id") Long userId, Model model) {
		model.addAttribute("prefix",appPrefix);

		UserDO userDO = new UserDO();
		userDO.setUserId(userId);
		model.addAttribute("user", userDO);
		return prefix + "/reset_pwd";
	}

	@Log("提交更改用户密码")
	@PostMapping("/resetPwd")
	@ResponseBody
	R resetPwd(UserVO userVO) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		try{
			userService.resetPwd(userVO,getUser());
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}
	@RequiresPermissions("sys:user:resetPwd")
	@Log("admin提交更改用户密码")
	@PostMapping("/adminResetPwd")
	@ResponseBody
	R adminResetPwd(UserVO userVO) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		try{
			userService.adminResetPwd(userVO);
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}
	@GetMapping("/tree")
	@ResponseBody
	public Tree<DeptDO> tree() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = userService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView(Model model) {
		model.addAttribute("prefix",appPrefix);
		return  prefix + "/userTree";
	}

	@GetMapping("/personal")
	String personal(Model model) {
		model.addAttribute("prefix",appPrefix);
		UserDO userDO  = userService.get(getUserId());
		model.addAttribute("user",userDO);
		return prefix + "/personal";
	}
	@ResponseBody
	@PostMapping("/uploadImg")
	R uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		Map<String, Object> result = new HashMap<>();
		try {
			result = userService.updatePersonalImg(file, avatar_data, getUserId());
		} catch (Exception e) {
			return R.error("更新图像失败！");
		}
		if(result!=null && result.size()>0){
			return R.ok(result);
		}else {
			return R.error("更新图像失败！");
		}
	}
}
