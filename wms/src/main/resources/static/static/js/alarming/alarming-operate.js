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
        , {field: 'discribetion', title: '警情描述', class: "hidden-xs", width: "48.3%"}
        , {field: 'level', title: '警情级别', class: "hidden-xs", width: "6%"}
        , {field: 'category', title: '警情类别', class: "hidden-xs", width: "6%"}
        , {field: 'createUserId', title: '创建人标识', class: "hidden-xs", width: "15%", hide: true}
        , {field: 'createUserName', title: '创建人', class: "hidden-xs", width: "6%"}
        , {field: 'validState', title: '生效状态', class: "hidden-xs", width: "6%"}
        , {title: '操作', width: "10%", toolbar: "#alarmingLineBar"}
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

    //修改装备提交
    form.on('submit(alarmingEditForm)', function (data) {

        let articleFrom = data.field;
        if (articleFrom.id == null || articleFrom.id == ""){
            alert("警情唯一标识不能为空！")
        }
        console.info(JSON.stringify(articleFrom));
        $.ajax({
            url: '/wms/alarmingEdit',
            type: 'post',
            data: JSON.stringify(articleFrom),
            dataType: "JSON",
            async: true,
            processData: false,	//不处理发送的数据
            contentType: 'application/json',
            success: function (result) {

                if (result.flag){


                    var name = $('#name').val();
                    var validState = $('#validState').val();
                    var level = $('#level').val();
                    var category = $('#category').val();
                    let postData1 = {};
                    postData1.name = name;
                    postData1.validState = validState;
                    postData1.level = level;
                    postData1.category = category;
                    table.reload('alarmingDataGrid', {
                        url: '/wms/alarmingList'
                        // ,methods:"post"
                        ,request: {
                            pageName: 'page' //页码的参数名称，默认：page
                            ,limitName: 'limit' //每页数据量的参数名，默认：limit
                        }
                        ,where: postData1
                        ,page: {
                            curr: 1
                        }
                    });
                }
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
            // case 'del':
            //     layer.confirm('你确定要删除该件设备吗?', {icon: 3, title:'提示'}, function(index){
            //         let equipments = [];
            //         equipments.push(data);
            //         delEquipments(equipments);
            //         layer.close(index);
            //     });
            //     break;
            default:
                break;
        };

    });
    var index = null;
    //监听头部工具条
    table.on('toolbar(alarmingDataGridFilter)', function(obj) {
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        let iframeObj = $(window.frameElement).attr('name');

        var name = $('#name').val();
        var validState = $('#validStateSelect').val();
        var level = $('#levelSelect').val();
        var category = $('#categorySelect').val();
        let postData1 = {};
        postData1.name = name;
        postData1.validState = validState;
        postData1.level = level;
        postData1.category = category;

        switch(layEvent){
            case 'adds':
                // layer.msg('查看');

                parent.page("警情添加", "/wms/toAlarmingAdd", iframeObj, w = "700px", h = "750px");
                break;
            case 'edits':
                // layer.msg('修改');
                let checkStatus = table.checkStatus('alarmingDataGrid').data;
                if(checkStatus == null || checkStatus.length ==0){
                    alert("请选择待编辑的数据!");
                    return false;
                }else if (checkStatus.length >1){
                    alert("不能选择多条数据！");
                    return false;
                }

                var urlparam = "?id="+checkStatus[0].id+"&name="+checkStatus[0].name+"&level="+checkStatus[0].level+"&category="+checkStatus[0].category+"&createUserId="+checkStatus[0].createUserId+"&createTime="+checkStatus[0].createTime+"&createUserName="+checkStatus[0].createUserName+"&validState="+checkStatus[0].validState+"&discribetion="+checkStatus[0].discribetion;
                index =parent.page("警情编辑", "/wms/toAlarmingEdit"+urlparam, iframeObj, w = "700px", h = "750px");

                layer.msg('编辑成功');


                break;
            case 'dels':
                dialog.confirm({
                    message:'您确定要删除选中项',
                    success:function(){
                        let checkStatus = table.checkStatus('alarmingDataGrid').data;
                        if(checkStatus == null || checkStatus.length ==0){
                            alert("请选择待作废的数据!");
                            return false;
                        }
                        // let equipments = [];
                        // equipments.push(data);
                        delAlarmings(checkStatus);
                        layer.msg('删除了');
                        table.reload('alarmingDataGrid', {
                            url: '/wms/alarmingList'
                            // ,methods:"post"
                            ,request: {
                                pageName: 'page' //页码的参数名称，默认：page
                                ,limitName: 'limit' //每页数据量的参数名，默认：limit
                            }
                            ,where: postData1
                            ,page: {
                                curr: 1
                            }
                        });
                    },
                    cancel:function(){
                        layer.msg('取消了');
                    }
                });
                break;
            case 'searchs':
                table.reload('alarmingDataGrid', {
                    url: '/wms/alarmingList'
                    // ,methods:"post"
                    ,request: {
                        pageName: 'page' //页码的参数名称，默认：page
                        ,limitName: 'limit' //每页数据量的参数名，默认：limit
                    }
                    ,where: postData1
                    ,page: {
                        curr: 1
                    }
                });
                break;
            default:
                break;
        };

    });

    //新增装备提交
    form.on('submit(alarmingAddForm)', function (data) {

        let articleFrom = data.field;
        if (articleFrom.name == null || articleFrom.name == ""){
            alert("请输入名称");
            return false;
        }
        if (articleFrom.category == null || articleFrom.category == ""){
            alert("请选择类别");
            return false;
        }
        if (articleFrom.level == null || articleFrom.level == ""){
            alert("请选择级别");
            return false;
        }
        console.info(JSON.stringify(articleFrom));
        $.ajax({
            url: '/wms/alarmingIncrease',
            type: 'post',
            data: JSON.stringify(articleFrom),
            dataType: "JSON",
            async: true,
            processData: false,	//不处理发送的数据
            contentType: 'application/json',
            success: function (result) {
                if (result.flag){
                    layer.msg(result.msg);
                }else{
                    layer.msg(result.msg);
                }


            },
            error: function (error) {
                layer.msg('系统错误' + error);
            }
        });

        return false;

    });


    //编辑警情
    function editAlarming(objs){

        let url = "/wms/editAlarming";

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
            }
        });

    }

    //作废警情
    function delAlarmings(objs){

        let url = "/wms/delAlarmings";

        // let requestBody = {};
        // requestBody.ids = objIds;



        $.ajax({
            url: url,
            type: 'POST',
            data: JSON.stringify(objs),
            dataType: "json",
            cache: false,
            async: false,
            processData: false,	//不处理发送的数据
            contentType: 'application/json',
            success: function (data) {
                console.info(data);
            }
        });

    }


})

