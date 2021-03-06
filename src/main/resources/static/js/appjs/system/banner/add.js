var prefix = appPrefix + "/system/banner";

$().ready(function() {
	validateRule();
});
$(function(){


})
$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

layui.use('upload', function () {
	var upload = layui.upload;
	//执行实例
	var uploadInst = upload.render({
		elem: '#shopImgBtn', //绑定元素
		url: prefix + '/upload', //上传接口
		size: 1000,
		auto:false,
		bindAction: '#commit',
		accept: 'file',
		choose: function(obj){
		//预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
		obj.preview(function(index, file, result){
		//	console.log(index); //得到文件索引
		//	console.log(file); //得到文件对象
		//	console.log(result); //得到文件base64编码，比如图片
			$("#showImg").attr("src",result);
		});
	},
		done: function (data) {
			console.log(data);
			if(data.code == "0000"){
				var file = data.data;
				$("#fileId").val(file.id);
				$("#showImg").attr("src",file.url);

				// 保存信息
				var banner = {
					fileId: file.id,
					banImg: file.url,
					banName: $("#banName").val()
				};

				$.ajax({
					cache : true,
					type : "POST",
					url :prefix + "/save",
					data : banner,// 你的formid
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

			}else{
				parent.layer.alert(data.info);
			}
		},
		error: function (r) {
			layer.msg(r.msg);
		}
	});
});
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			deptName : {
				required : true,
				minlength : 2
			}
		},
		messages : {
			deptName : {
				required : icon + "请选择部门或网点",
			},
		}
	})
}
