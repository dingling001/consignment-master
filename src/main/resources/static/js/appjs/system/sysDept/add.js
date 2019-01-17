var prefix = appPrefix + "/system/sysDept";

$().ready(function() {
	validateRule();
});
$(function(){
	$('#mySwitch input').bootstrapSwitch();
	$('#mySwitch input').on('switchChange.bootstrapSwitch', function (event,state) {
		if(state) {
			$('#delFlag').val('1');
		}else {
			$('#delFlag').val('0');
		}
	});
})
$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url :prefix + "/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			}
		}
	})
}
var openProvince = function(){
	layer.open({
		type:2,
		title:"选择部门",
		area : [ '300px', '450px' ],
		content:appPrefix + "/system/sysDept/treeViewProvince"
	})

}
function loadProvince( numbers,cityName){
	$("#deptProvince").val(numbers[0]);
	$("#deptCity").val(numbers[1]);
	$("#deptArea").val(numbers[2]);
	$("#cityName").val(cityName);

}

