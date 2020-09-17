layui.use(['table','form', 'jquery', 'laydate', 'layer', 'laypage', 'dialog', 'element', 'upload', 'layedit'], function() {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laypage = layui.laypage,
        laydate = layui.laydate,
        layedit = layui.layedit,
        common = layui.common,
        // tool = layui.tool,
        table = layui.table,
        element = layui.element,
        dialog = layui.dialog;

    //表格渲染
    var equipmentcols = [[
        {type:'checkbox'}
        ,{field: 'id', width: 80, title: 'ID', hide:true}
        , {field: 'equipmentRfid', title: '设备标识', class: "hidden-xs", hide:true}
        , {field: 'equipmentName', title: '设备名称', class: "hidden-xs", width: "8%"}
        , {field: 'equipmentGrade', title: '设备等级', class: "hidden-xs" , width: "6%"} //minWidth：局部定义当前单元格的最小宽度，layui 2.2.1 新增
        , {field: 'equipmentClass', title: '设备分类', class: "hidden-xs", width: "6%"}
        , {field: 'equipmentPreId', title: '所属上级设备', class: "hidden-xs", width: "18%"}
        , {field: 'equipmentCompanyId', title: '所属公司标识', class: "hidden-xs", hide:true}
        , {field: 'equipmentCompanyName', title: '所属公司名称', class: "hidden-xs", width: "13%"}
        , {field: 'schemeId', title: '方案标识', class: "hidden-xs", hide:true}
        , {field: 'schemeName', title: '方案名称', class: "hidden-xs", width: "13.7%"}
        , {field: 'createTime', title: '创建时间', class: "hidden-xs", width: "6%"}
        , {field: 'editTime', title: '修改时间', class: "hidden-xs", width: "6%"}
        , {field: 'validState', title: '生效状态', class: "hidden-xs", width: "6%"}
        , {field: 'occupyState', title: '占用状态', class: "hidden-xs", width: "6%"}
        , {title: '操作', width: "9%", toolbar: "#equipmentLineBar"}
    ]];

    var postData = {};
    postData.equipmentRfid = null;
    postData.equipmentName = null;
    postData.equipmentGrade = null;
    postData.equipmentClass = null;
    postData.equipmentPreId = null;
    postData.equipmentCompanyId = null;
    postData.equipmentCompanyName = null;
    postData.schemeId = null;
    postData.schemeName = null;
    postData.validState = null;
    postData.occupyState = null;

    table.render({
        elem: '#equipmentDataGrid'
        ,url: '/wms/equipmentList'
        ,toolbar: '#equipmentHeadBar'
        ,defaultToolbar: ['filter']
        ,limit: 15 //初始化的数据数量
        ,limits: [15, 25, 30, 50, 100, 200] //初始化的数据数量
        ,where: postData
        ,cols: equipmentcols
        ,page: true
    });

    //新增装备提交
    form.on('submit(equipmentAddForm)', function (data) {

        let articleFrom = data.field;
        if (articleFrom.equipmentClass == null || articleFrom.equipmentClass == ""){
            articleFrom.equipmentClassName = "";
        }else {
            articleFrom.equipmentClassName = $("#classEquipmentSelect option:selected").text();
        }
        console.info(JSON.stringify(articleFrom));
        $.ajax({
            url: '/wms/equipmentIncrease',
            type: 'post',
            data: JSON.stringify(articleFrom),
            dataType: "JSON",
            async: true,
            processData: false,	//不处理发送的数据
            contentType: 'application/json',
            success: function (result) {
                // if (result.code == 100) {
                //     layer.msg('登录成功', {
                //         icon : 6,
                //         time : 1000,
                //         shade : 0.3,
                //         end : function() {
                //             location.href = "./index.html";
                //         }
                //     });
                // } else if (result.code == 101) {
                //     $("#password").focus();
                // }

                layer.msg(result.msg);
            },
            error: function (error) {
                layer.msg('系统错误' + error);
            }
        });

        return false;

    });

    //修改装备提交
    form.on('submit(equipmentEidtForm)', function (data) {

        let articleFrom = data.field;
        if (articleFrom.equipmentClass == null || articleFrom.equipmentClass == ""){
            articleFrom.equipmentClassName = "";
        }else {
            articleFrom.equipmentClassName = $("#classEquipmentEditSelect option:selected").text();
        }
        console.info(JSON.stringify(articleFrom));
        $.ajax({
            url: '/wms/equipmentEdit',
            type: 'post',
            data: JSON.stringify(articleFrom),
            dataType: "JSON",
            async: true,
            processData: false,	//不处理发送的数据
            contentType: 'application/json',
            success: function (result) {
                // if (result.code == 100) {
                //     layer.msg('登录成功', {
                //         icon : 6,
                //         time : 1000,
                //         shade : 0.3,
                //         end : function() {
                //             location.href = "./index.html";
                //         }
                //     });
                // } else if (result.code == 101) {
                //     $("#password").focus();
                // }

                layer.msg(result.msg);
            },
            error: function (error) {
                layer.msg('系统错误' + error);
            }
        });

        return false;

    });

    //监听行工具条
    table.on('tool(equipmentDataGridFilter)', function(obj) { //注：tool 是工具条事件名，equipmentDataGridFilter 是 table 原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        switch(layEvent){
            case 'info':
                // layer.msg('查看');
                window.location.href="/wms/toArticleInfo?id="+data.id;
                break;
            case 'edit':
                // layer.msg('修改');
                window.location.href="/wms/toArticleEdit?id="+data.id;
                break;
            case 'del':
                layer.confirm('你确定要删除该件设备吗?', {icon: 3, title:'提示'}, function(index){
                    let equipments = [];
                    equipments.push(data);
                    delEquipments(equipments);
                    layer.close(index);
                });
                break;
            default:
                break;
        };

    });

    //监听头部工具条
    table.on('toolbar(equipmentDataGridFilter)', function(obj) {
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        switch(layEvent){
            case 'adds':
                // layer.msg('查看');
                let iframeObj = $(window.frameElement).attr('name');
                parent.page("设备添加", "/wms/toArticleAdd", iframeObj, w = "700px", h = "620px");
                break;
            case 'edits':
                // layer.msg('修改');
                dialog.confirm({
                    message:'您确定要删除选中项',
                    success:function(){
                        let checkStatus = table.checkStatus('equipmentDataGrid').data;
                        // let equipments = [];
                        // equipments.push(data);
                        // delEquipments(equipments);
                        layer.msg('删除了');
                    },
                    cancel:function(){
                        layer.msg('取消了');
                    }
                });
                break;
            case 'dels':
                dialog.confirm({
                    message:'您确定要删除选中项',
                    success:function(){
                        let checkStatus = table.checkStatus('equipmentDataGrid').data;
                        // let equipments = [];
                        // equipments.push(data);
                        delEquipments(checkStatus);
                        layer.msg('删除了');
                    },
                    cancel:function(){
                        layer.msg('取消了');
                    }
                });
                break;
            default:
                break;
        };

    });

    //删除设备
    function delEquipments(objs){

        let url = "/wms/delEquipments";

        // let requestBody = {};
        // requestBody.ids = objIds;



        $.ajax({
            url: url,
            type: 'POST',
            data: JSON.stringify(objs),
            dataType: "json",
            cache: false,
            async: true,
            processData: false,	//不处理发送的数据
            contentType: 'application/json',
            success: function (data) {
                console.info(data);
                // var response = data; //转换为json对象   
                // var listHtml = '';
                // $.each(response, function (i) {
                //     if (response[i].value != "") {
                //         var divid= 'divWareHouse'+ response[i].wareHouseSn;
                //         var str = "<div style='float:left; margin-left: 1px; margin-top: 1px; border: 1px solid gray;' id='"+divid+"'><input onclick='ListFn.CheckboxOnclick(this)' type='checkbox' " + " name='wareHouseCheck'" + " id='" + Obj + response[i].wareHouseSn + "' value='" + response[i].wareHouseSn +"'/>" + response[i].wareHouseName+"</div>";
                //         listHtml += str;
                //     }
                // });
                // $("#" + Obj).html(listHtml);
            }
        });

    }


})

