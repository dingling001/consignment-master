package com.bootdo.system.controller;

import com.bootdo.common.config.Constant;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.utils.R;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.service.DeptService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:40:36
 */

@Controller
@RequestMapping("/system/sysDept")
public class DeptController extends BaseController {
	private String prefix ="system/dept";
	@Autowired
	private DeptService sysDeptService;

	@GetMapping("/index")
	@RequiresPermissions("system:sysDept:sysDept")
	String dept(Model model) {
		model.addAttribute("prefix",appPrefix);
		return prefix + "/dept";
	}


	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:sysDept:sysDept")
	public List<DeptDO> list() {
		Map<String, Object> query = new HashMap<>(16);
		List<DeptDO> sysDeptList = sysDeptService.list(query);
		return sysDeptList;
	}

	@GetMapping("/add/{pId}")
	@RequiresPermissions("system:sysDept:add")
	String add(@PathVariable("pId") Long pId, Model model) {
		model.addAttribute("prefix",appPrefix);
		model.addAttribute("pId", pId);
		model.addAttribute("pName", sysDeptService.get(pId).getName());
		return  prefix + "/add";
	}

	@GetMapping("/edit/{deptId}")
	@RequiresPermissions("system:sysDept:edit")
	String edit(@PathVariable("deptId") Long deptId, Model model) {
		model.addAttribute("prefix",appPrefix);
		DeptDO sysDept = sysDeptService.get(deptId);
		model.addAttribute("sysDept", sysDept);
		if(Constant.DEPT_ROOT_ID.equals(sysDept.getParentId())) {
			DeptDO highestDept = sysDeptService.get(sysDept.getParentId());
			model.addAttribute("parentDeptName", highestDept.getName());
		}else {
			if (Constant.DEPT_ROOT_PARENT_ID.equals(sysDept.getParentId())) {
				model.addAttribute("parentDeptName", "无");
			} else {
				DeptDO parDept = sysDeptService.get(sysDept.getParentId());
				model.addAttribute("parentDeptName", parDept.getName());
			}
		}
		return  prefix + "/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:sysDept:add")
	public R save(DeptDO sysDept) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}

		DeptDO deptDO = sysDeptService.get(sysDept.getParentId());
		sysDept.setDeptLevel((deptDO.getDeptLevel() + 1));

		if(deptDO.getDeptLevel() == 2){
			sysDept.setDeptData("1");
		}else{
			sysDept.setDeptData("0");
		}

		if (sysDeptService.save(sysDept) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:sysDept:edit")
	public R update(DeptDO sysDept) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		DeptDO deptDO = sysDeptService.get(sysDept.getParentId());
		sysDept.setDeptLevel((deptDO.getDeptLevel() + 1));
		if (sysDeptService.update(sysDept) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("system:sysDept:remove")
	public R remove(Long deptId) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", deptId);
		if (deptId == 0) {
			return R.error(1, "最高级节点不允许删除，请联系管理员");
		}
		if(sysDeptService.count(map)>0) {
			return R.error(1, "包含下级部门,不允许修改");
		}
		if(sysDeptService.checkDeptHasUser(deptId)) {
			if (sysDeptService.remove(deptId) > 0) {
				return R.ok();
			}
		}else {
			return R.error(1, "部门包含用户,不允许修改");
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("system:sysDept:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] deptIds) {
		if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		sysDeptService.batchRemove(deptIds);
		return R.ok();
	}

	@GetMapping("/tree/{payment}")
	@ResponseBody
	public Tree<DeptDO> tree(@PathVariable String payment) {
		Tree<DeptDO> tree = new Tree<DeptDO>();
        Map<String,Object> map = new HashMap<>();
		map.put("payment",payment);
        tree = sysDeptService.getTree(map);
		return tree;
	}



	@GetMapping("/treeView/{payment}")
	String treeView(@PathVariable String payment, Model model) {
		model.addAttribute("prefix",appPrefix);
        String pay = null;
        if(!StringUtils.isEmpty(payment)){
            pay = payment;
        }
        model.addAttribute("payment",pay);

		return  prefix + "/deptTree";
	}
	@GetMapping("/treeViewProvince")
	String treeViewProvince(Model model) {
		model.addAttribute("prefix",appPrefix);
		return  prefix + "/provinceTree";
	}

}
