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
    var alarmingcols = [[
        {type:'checkbox'}
        ,{field: 'id', width: 80, title: 'ID', hide:true}
        , {field: 'name', title: '警情名称', class: "hidden-xs", width: "15%"}
        , {field: 'discribetion', title: '警情描述', class: "hidden-xs", width: "49.3%"}
        , {field: 'level', title: '警情级别', class: "hidden-xs", width: "6%"}
        , {field: 'category', title: '警情类别', class: "hidden-xs", width: "6%"}
        , {field: 'createUserId', title: '创建人标识', class: "hidden-xs", width: "15%", hide: true}
        , {field: 'createUserName', title: '创建人', class: "hidden-xs", width: "6%"}
        , {field: 'validState', title: '生效状态', class: "hidden-xs", width: "6%"}
        , {title: '操作', width: "9%", toolbar: "#alarmingLineBar"}
    ]];

    var postData = {};
    postData.id = null;
    postData.name = null;
    postData.level = null;
    postData.category = null;
    postData.beginTime = null;
    postData.endTime = null;
    postData.validState = null;

    table.render({
        elem: '#alarmingDataGrid'
        ,url: '/wms/alarmingList'
        ,toolbar: '#alarmingHeadBar'
        ,defaultToolbar: ['filter']
        ,limit: 15 //初始化的数据数量
        ,limits: [15, 25, 30, 50, 100, 200] //初始化的数据数量
        ,where: postData
        ,cols: alarmingcols
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
    table.on('tool(alarmingDataGridFilter)', function(obj) { //注：tool 是工具条事件名，equipmentDataGridFilter 是 table 原始容器的属性 lay-filter="对应的值"
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        switch(layEvent){
            case 'info':
                // layer.msg('查看');
                // window.location.href="/wms/toArticleInfo?id="+data.id;
                let iframeObj1 = $(window.frameElement).attr('name');
                parent.page("警情方案启用", "/wms/toUseSchemes?alarmingId="+data.id, iframeObj1, w = "95%", h = "90%");
                break;
            case 'edit':
                // layer.msg('修改');
                let iframeObj2 = $(window.frameElement).attr('name');
                parent.page("警情方案绑定", "/wms/toBindSchemes?alarmingId="+data.id, iframeObj2, w = "95%", h = "90%");
                // window.location.href="/wms/toBindSchemes";
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
    table.on('toolbar(alarmingDataGridFilter)', function(obj) {
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

