var prefix = appPrefix + "/sys/user";

// 以下为官方示例
$().ready(function() {
	validateRule();
	// $("#signupForm").validate();
});
$(function(){
	$('#mySwitch input').bootstrapSwitch();
	var status = $('#status').val();
	if(status == '0') {
		$('#mySwitch input').bootstrapSwitch('state',false);
	}
	$('#mySwitch input').on('switchChange.bootstrapSwitch', function (event,state) {
		if(state) {
			$('#status').val('1');
		}else {
			$('#status').val('0');
		}
	});
})
$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$("#roleIds").val(getCheckedRoles());
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.msg(data.msg);
			}

		}
	});

}
function getCheckedRoles() {
	var adIds = "";
	$("input:checkbox[name=role]:checked").each(function(i) {
		if (0 == i) {
			adIds = $(this).val();
		} else {
			adIds += ("," + $(this).val());
		}
	});
	return adIds;
}
function setCheckedRoles() {
	var roleIds = $("#roleIds").val();
	alert(roleIds);
	var adIds = "";
	$("input:checkbox[name=role]:checked").each(function(i) {
		if (0 == i) {
			adIds = $(this).val();
		} else {
			adIds += ("," + $(this).val());
		}
	});
	return adIds;
}

function checkMobile(){
    var phone = $("#mobile").val();
    if(!phone){
        return true;
    }

    if(!(/^1[34578]\d{9}$/.test(phone))){
        return false;
    }else{
        return true;
    }
}

jQuery.validator.addMethod("checkMobile", function(value, element) {
    return checkMobile();
}, "手机号格式不正确");

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
/*			name : {
				required : true
			},*/
			deptName : {
				required : true,
				minlength : 2
			},
			deadline : {
				required : true,
			},
			username : {
				required : true,
				minlength : 2,
				//remote : {
				//	url : "/sys/user/exit", // 后台处理程序
				//	type : "post", // 数据发送方式
				//	dataType : "json", // 接受数据格式
				//	data : { // 要传递的数据
				//		username : function() {
				//			return $("#username").val();
				//		}
				//	}
				//}
			},
			password : {
				required : true,
				minlength : 6
			},
			confirm_password : {
				required : true,
				minlength : 6,
				equalTo : "#password"
			},
			topic : {
				required : "#newsletter:checked",
				minlength : 2
			},
			mobile : {
                checkMobile : true,
                minlength : 11
			}
		},
		messages : {

/*			name : {
				required : icon + "请输入姓名"
			},*/
			deadline : {
				required : icon + "请选择服务截止时间"
			},
			deptName : {
				required : icon + "请选择部门或网点",
			},
			username : {
				required : icon + "请输入您的用户名",
				minlength : icon + "用户名必须两个字符以上",
			},
			password : {
				required : icon + "请输入您的密码",
				minlength : icon + "密码必须6个字符以上"
			},
			confirm_password : {
				required : icon + "请再次输入密码",
				minlength : icon + "密码必须6个字符以上",
				equalTo : icon + "两次输入的密码不一致"
			},
            mobile : {
                minlength : icon + "联系电话必须11个数字"
            },
		}
	})
}
var openDept = function(){
	layer.open({
		type:2,
		title:"选择部门",
		area : [ '620px', '420px' ],
		content:appPrefix + "/system/sysDept/treeView/0"
	})
}
function loadDept( deptId,deptName){
	$("#deptId").val(deptId);
	$("#deptName").val(deptName);
}
layui.use('laydate',function(){
	var date = layui.laydate;
	//执行一个laydate实例
	date.render({
		elem: '#deadline', //指定元素
		//type: 'datetime', //格式化
		//format:'yyyy年MM月dd日 HH:mm:ss',
		theme:'#393D49' //主题
		//trigger: 'click' //只读模式
	});
});