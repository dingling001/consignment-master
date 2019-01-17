var prefix = appPrefix + "/system/banner";
$(function () {
    load();

});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                showRefresh: false,
                // showToggle : true,
                // showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                cardView: true,
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                search: false, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                //sortable:true,
                //sortOrder:'asc',
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者
                // "server"
                queryParams: function (params) {
                    return {
                        // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset: params.offset,
                        text: $('#textActivity').val(),
                        deptId: $('#developmentId').val()
                    };
                },
                //table中数据加载完成后执行的函数
                onLoadSuccess: function () {
                    $('.J_menuItem').on('click', function (e) {

                    });
                    return !1
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        field: 'banId', // 列字段名
                        title: '序号', // 列标题
                        width:'10%',
                        switchable: false,
                        formatter: function (value, row, index) {
                            var pageSize = $('#exampleTable').bootstrapTable('getOptions').pageSize;
                            var pageNumber = $('#exampleTable').bootstrapTable('getOptions').pageNumber;
                            return pageSize * (pageNumber - 1) + index + 1;
                        }
                    },
                    {
                        field: 'banName',
                        title: 'Banner名称'
                    },
                    {
                        title: '图片预览',
                        field: 'banImg',
                        align: 'center',
                        valign: 'center',
                        width: '150px',
                        formatter: function (item, index) {
                            var result = item == '' || item == null;
                            return result ? '' : '<img src=' + item + '  height="36" />';
                        }
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var e = '<a  class="btn btn-primary btn-sm " href="#" mce_href="#" title="编辑" ' +
                                'onclick="edit(\'' + row.banId + '\',\'2\')"><i class="fa fa-edit "></i></a> ';
                            var d = '<a class="btn btn-danger btn-sm " href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.banId
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            return  e + d ;
                        }
                    }]
            });
}



function reLoad() {
    var params = {
        query: {
            offset:0
        }
    };
    $('#exampleTable').bootstrapTable('refresh', params);
    $('#exampleTable').bootstrapTable('refreshOptions',{pageNumber:1});
}

function add() {
    // iframe层
    layer.open({
        type: 2,
        title: '增加',
        maxmin: false,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/add',
    });
}
function remove(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'id': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}
function edit(id) {
    //type:1为查看详情；2为编辑

    layer.open({
        type: 2,
        title: "编辑",
        maxmin: true,
        shadeClose: false,
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}

