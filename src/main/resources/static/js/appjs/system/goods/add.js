var prefix = appPrefix + "/system/goods";
var base_1,base_2,base_3,base_4,base_5,suffix_1,suffix_2,suffix_3,suffix_4,suffix_5;

$().ready(function() {

	validateRule();
});
$(function(){
    loadRootTypes();

    $('#mySwitch input').bootstrapSwitch();
    $('#mySwitch input').on('switchChange.bootstrapSwitch', function (event,state) {
        if(state) {
            $('#goodsStatus').val('0');
        }else {
            $('#goodsStatus').val('1');
        }
    });

    $('#mySwitch1 input').bootstrapSwitch();
    $('#mySwitch1 input').on('switchChange.bootstrapSwitch', function (event,state) {
        if(state) {
            $('#goodsTop').val('0');
        }else {
            $('#goodsTop').val('1');
        }
    });
})
$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
$("#goodsImgBtn").click(function(){
    $("#goodsImg").click();
})

$("#goodsDetailBtn").click(function(){
    $("#goodsDetail").click();
})

$("#addCarouselImg").click(function(){
    $("#detailInput").click();
})
$("#goodsImg").change(function(){
    var r= new FileReader();
    var file =document.getElementById('goodsImg').files[0];
    var upFileName = file.name;
    var index1=upFileName.lastIndexOf(".");
    var index2=upFileName.length;

    var size = file.size/( 1024 * 1024 );
        if(parseFloat(size) > 1){
     parent.layer.alert("您上传的图片太大（最大不超过1M），请重新上传~");
     return false;
     }

    suffix_1=upFileName.substring(index1+1,index2);//后缀名

    if(suffix_1 != "gif" && suffix_1 != "jpg" && suffix_1 != "jpeg" && suffix_1 != "png"){
        parent.layer.alert("图片格式不对，请选择格式为 *.gif、*.jpg、*.jpeg、*.png的图片上传");
        return false;
    }

    r.readAsDataURL(file);
    r.onload=function (e) {
        document.getElementById('goodsImgUrl').src=this.result;
    };
    var reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = function(e){
        base_1 = btoa(e.target.result);
        $("#goodsImgUrl").show();
    }
});

$("#goodsDetail").change(function(){
    var r= new FileReader();
    var file =document.getElementById('goodsDetail').files[0];
    var upFileName = file.name;
    var index1=upFileName.lastIndexOf(".");
    var index2=upFileName.length;

    var size = file.size/( 1024 * 1024 );
    if(parseFloat(size) > 1){
        parent.layer.alert("您上传的图片太大（最大不超过1M），请重新上传~");
        return false;
    }

    suffix_2=upFileName.substring(index1+1,index2);//后缀名

    if(suffix_2 != "gif" && suffix_2 != "jpg" && suffix_2 != "jpeg" && suffix_2 != "png"){
        parent.layer.alert("图片格式不对，请选择格式为 *.gif、*.jpg、*.jpeg、*.png的图片上传");
        return false;
    }

    r.readAsDataURL(file);
    r.onload=function (e) {
        document.getElementById('goodsDetailUrl').src=this.result;
    };
    var reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = function(e){
        base_2 = btoa(e.target.result);
        $("#goodsDetailUrl").show();
    }
});

$("#commit").click(function(){

    var goods = {
        goodsName:$("#goodsName").val(),
        goodsType:$("#choseType").val(),
        goodsDesc:$("#goodsDesc").val(),
        goodsBrand:$("#goodsBrand").val(),
        goodsCost:$("#goodsCost").val(),
        goodsPrice:$("#goodsPrice").val(),
        goodsText:$("#goodsText").val(),
        goodsCode:$("#goodsCode").val(),
        goodsStatus:$("#goodsStatus").val(),
        goodsTop:$("#goodsTop").val()
    }

    var data= {
        goods:goods,
        base_1:base_1,
        base_2:base_2,
        base_3:base_3,
        base_4:base_4,
        base_5:base_5,
    }



    $.ajax({
        type:'post',
        url:prefix +  "/saveGoods",
        contentType: "application/json; charset=utf-8",
        data:JSON.stringify(data),
        dataType : 'json',
        async: false,
        error: function (request) {
            parent.layer.alert("Connection error");
        },
        success: function (data) {
            if (data.code == 0) {
                parent.layer.msg("保存成功");
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);

            } else {
                parent.layer.alert(data.msg)
            }

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

var model;
function showImg(){
    $(".shop-img").click(function(){
        var src = $(this).attr("src");
        var index = $(this).attr("data-index");
        var dom = '<a class="btn btn-primary btn-sm " href="#" mce_href="#" title="编辑" onclick="edit(' + index + ')">' +
            '<i class="fa fa-edit"></i>' +
            '</a>' +
            '<a class="btn btn-warning btn-sm " href="#" title="删除" mce_href="#" onclick="remove('+ index +')">' +
            '<i class="fa fa-remove"></i>' +
            '</a>';
        $("#option").html(dom);
        $("#showImg").attr("src",src);
        $(".layui-layer-shade").remove();
        model = layer.open({
            type: 1,
            content: $('#showBox'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            end: function () {
                $(".layui-layer-shade").remove();
            }
        });
        var z = $(".layui-layer-shade").css("z-index");
        $(".layui-layer-shade").css("z-index",(z-2));
    });
}
function edit(index){
    $("#upload").attr("data-index",index);
    $("#upload").click();
}

$("#upload").change(function(){
    var r= new FileReader();
    var file =document.getElementById('upload').files[0];
    var upFileName = file.name;
    var index1=upFileName.lastIndexOf(".");
    var index2=upFileName.length;
    var index = $(this).attr("data-index");

    var size = file.size/( 1024 * 1024 );
    if(parseFloat(size) > 1.5){
        parent.layer.alert("您上传的图片太大（最大不超过1M），请重新上传~")
        document.getElementById('privew').value = "";
        return false;
    }

    var suffix,base;
    suffix=upFileName.substring(index1+1,index2);//后缀名

    if(suffix != "gif" && suffix != "jpg" && suffix != "jpeg" && suffix != "png"){
        parent.layer.alert("图片格式不对，请选择格式为 *.gif、*.jpg、*.jpeg、*.png的图片上传")
        return false;
    }

    r.readAsDataURL(file);
    r.onload=function (e) {
        //document.getElementById('shopFaceUrl').src=this.result;
        if( 0 == index){
            $("#goodsImgUrl").show().attr("src",this.result);
        }else if(1 == index){
            $("#goodsDetailUrl").show().attr("src",this.result);
        }else{
            var li = $("#imgBox").find("li");
            li.eq((index - 2)).find("img").attr("src",this.result);
        }
    };
    var reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = function(e){
        base = btoa(e.target.result);
        switch (index){
            case "0" : base_1 = base;suffix_1 = suffix;layer.closeAll();
                break;
            case "1" : base_2 = base;suffix_2 = suffix;layer.closeAll();
                break;
            case "2" : base_3 = base;suffix_3 = suffix;layer.closeAll();
                break;
            case "3" : base_4 = base;suffix_4 = suffix;layer.closeAll();
                break;
            case "4" : base_5 = base;suffix_5 = suffix;layer.closeAll();
                break;
            default:;
        }
    }

});

$("#detailInput").change(function(){
    var li = $("#imgBox").find("li");
    var files = $(this).prop("files");
    if(files.length + li.length > 3){
        layer.alert("轮播图片最多上传3张~");
        return false;
    }

    if(files.length > 0){
        for(var i = 0; i < files.length; i++){
            var lis = '<li><img class="layui-upload-img shop-img float-left" data-index="' + (li.length + i + 2) + '"></li>';
            var flag =  createLi(li.length + i,files[i]);
            if(flag){
                $("#imgBox").append(lis);
            }
        }
    }
    showImg();


});


function createLi(index,file){
    var r= new FileReader();
    var upFileName = file.name;
    var index1=upFileName.lastIndexOf(".");
    var index2=upFileName.length;

    var size = file.size/( 1024 * 1024 );

     if(parseFloat(size) > 1){
     parent.layer.alert("您上传的图片太大（最大不超过1.5M），请重新上传~");
     return false;
     }

    var suffix,base;
    suffix =upFileName.substring(index1+1,index2);//后缀名

    if(suffix != "gif" && suffix != "jpg" && suffix != "jpeg" && suffix != "png"){
        parent.layer.alert("图片格式不对，请选择格式为 *.gif、*.jpg、*.jpeg、*.png的图片上传");
        return false;
    }

    r.readAsDataURL(file);
    r.onload=function (e) {
        var li = $("#imgBox").find("li");
        li.eq(index).find("img").attr("src",this.result);
        //document.getElementById('shopFaceUrl').src=this.result;
    };
    var reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = function(e){
        base = btoa(e.target.result);
        switch (index){
            case 0 : base_3 = base;suffix_3 = suffix;
                break;
            case 1 :base_4 = base;suffix_4 = suffix;
                break;
            case 2 :base_5 = base;suffix_5 = suffix;
                break;
            default:;
        }
    }
    return true;
}


function remove(index){
    switch (index){
        case 0: $("#shopFaceUrl").hide();
            base_1 = null;
            suffix_1 = null;
            layer.closeAll();
            break;
        case 1: $("#shopFaceUrl").hide();
            base_2 = null;
            suffix_2 = null;
            layer.closeAll();
            break;
        case 2: var img = $(".shop-img");
            for(var i = 0; i < img.length; i++){
                if(img.eq(i).attr("data-index") == index){
                    img.eq(i).closest("li").remove();
                };
            }
            base_3 = null;
            suffix_3 = null;
            var li = $("#imgBox").find("li");
            for(var i = 0; i < li.length; i ++){
                li.eq(i).find("img").attr("data-index",(i + 2));
            }
            layer.closeAll();
            break;
        case 3: var img = $(".shop-img");
            for(var i = 0; i < img.length; i++){
                if(img.eq(i).attr("data-index") == index){
                    img.eq(i).closest("li").remove();
                };
            }
            var li = $("#imgBox").find("li");
            for(var i = 0; i < li.length; i ++){
                li.eq(i).find("img").attr("data-index",(i + 2));
            }
            base_4 = null;
            suffix_4 = null;
            layer.closeAll();
            break;
        case 4: var img = $(".shop-img");
            for(var i = 0; i < img.length; i++){
                if(img.eq(i).attr("data-index") == index){
                    img.eq(i).closest("li").remove();
                };
            }
            var li = $("#imgBox").find("li");
            for(var i = 0; i < li.length; i ++){
                li.eq(i).find("img").attr("data-index",(i + 2));
            }
            base_5 = null;
            suffix_5 = null;
            layer.closeAll();
            break;
        default:;
    }
}


function loadRootTypes(){
    //var html = '<option value="">请选择</option>';
    var html = '';
    $.ajax({
        url: prefix + '/types',
        type:'get',
        dataType:'json',
        success: function (data) {
            //加载数据
            for (var i = 0; i < data.length; i++) {
                html += '<option value="' + data[i].typeId + '">' + data[i].typeName + '</option>'
            }
            $("#choseType").html(html);
            $("#choseType").chosen({
                maxHeight: 200
            });
            $("#choseType").trigger('chosen:updated');
        }
    });
}


