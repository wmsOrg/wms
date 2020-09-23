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

    function querySchemes(){
        let articleFrom = {};
        articleFrom.alarmingId = $("#alarmingId").val();
        $.ajax({
            url: '/wms/planList',
            type: 'post',
            data: JSON.stringify(articleFrom),
            dataType: "JSON",
            async: true,
            processData: false,	//不处理发送的数据
            contentType: 'application/json',
            success: function (result) {
                var divHtmls = "";
                if (result.code == "9") {
                    var divDatas = result.data;
                    var length = divDatas.length;
                    for (var i = 0; i < length; i++){
                        var divData = divDatas[i];
                        var imageUrl = "";
                        if (divData.imageUrl == null || divData.imageUrl ==""){
                            imageUrl = "../static/images/visibleEditLayout/fileadd.svg";
                        }else{
                            imageUrl = "/wms/showImageByPath?fileName="+divData.imageUrl;
                        }
                        var divhtml = "<form class=\"layui-form\">\n" +
                            "                <div class=\"layui-col-xs6 layui-col-md3\" style=\"padding: 15px;text-align: center\">\n" +
                            // "                    <div class=\"layui-inline tool-btn\" style=\"float: left;width: 3px;height: 3px\">\n" +
                            // "                        <input type=\"checkbox\" name=\"schemeLineCbx\" lay-skin=\"primary\"  lay-filter=\"schemeLineCbx\" style='display: block'>\n" +
                            "                        <input type=\"text\" name=\"id\" style=\"display: none\" value='"+divData.id+"'>\n" +
                            "                        <input type=\"text\" name=\"alarmingId\" style=\"display: none\" value='"+divData.alarmingId+"'>\n" +
                            // "                    </div>\n" +
                            "\n" +
                            "                        <div class=\"layui-inline\">\n" +
                            "                            <input type=\"checkbox\" name=\"validState\" lay-skin=\"switch\" checked=\"\" style=\"min-width: 0px;height: 0px;\">\n" +
                            "                        </div>\n" +
                            "\n" +
                            "                    <div class=\"layui-inline tool-btn\" style=\"float: right;\">\n" +
                            "                        <button class=\"layui-btn layui-btn-xs layui-btn-normal schemeLineBtn\" onclick='infoScheme(\""+divData.id+"\",\""+divData.alarmingId+"\",\""+divData.schemeId+"\");return false;'  lay-event=\"info\"><i class=\"layui-icon\" style=\"color: #323232;\">&#xe705;</i></button>\n" +
                            "                        <button class=\"layui-btn layui-btn-xs layui-btn-warm schemeLineBtn\" onclick='startPlan(\""+divData.id+"\",\""+divData.alarmingId+"\",\""+divData.schemeId+"\");return false;' lay-filter='outEquipment' lay-event=\"edit\"><i class=\"layui-icon\" style=\"color: #323232;\">&#xe652;</i></button>\n" +
                            "                        <button class=\"layui-btn layui-btn-xs layui-btn-warm schemeLineBtn\" onclick='stopPlan(\""+divData.id+"\",\""+divData.alarmingId+"\",\""+divData.schemeId+"\");return false;' lay-filter='inEquipment' lay-event=\"edit\"><i class=\"layui-icon\" style=\"color: #323232;\">&#xe651;</i></button>\n" +
                            "                        <button class=\"layui-btn layui-btn-xs layui-btn-danger schemeLineBtn\" lay-event=\"dels\"><i class=\"layui-icon\" style=\"color: #323232;\">&#xe640;</i></button>\n" +
                            "                    </div>\n" +
                            "                    <a class=\"template store-list-box\" href=\"/css/i/463043/1.html\"> <img class=\"store-list-cover\" src=\""+imageUrl+"\"> <h2 class=\"layui-elip\">"+divData.planName+"</h2>\n" +
                            "                    </a>\n" +
                            "                </div>\n" +
                            "            </form>";
                        divHtmls += divhtml;

                    }
                    $("#table-list").html(divHtmls);
                } else if (result.code == 4) {
                    layer.msg(result.msg);
                    $("#table-list").html("");
                }
            },
            error: function (error) {
                layer.msg('系统错误' + error);
            }
        });

        return false;
    }
    querySchemes();

    //获取当前iframe的name值
    var iframeObj = $(window.frameElement).attr('name');

    //预案详情
    window.infoScheme = function (planId,alarmingId,schemeId) {
        if (alarmingId == null || alarmingId == ""){
            alert("无法查阅预案详情，未找到该警情主键标识;");
            return false;
        }else if (planId == null || planId == ""){
            alert("无法查阅预案详情，未找到该预案主键标识;");
            return false;
        }else if (schemeId == null || schemeId == ""){
            alert("无法查阅预案详情，未找到该方案主键标识;");
            return false;
        }else {
            var url="/wms/toInfoPlan?planId="+planId+"&alarmingId="+alarmingId+"&schemeId="+schemeId;
            parent.page("预案详情", url, iframeObj, w = "95%", h = "90%");
            return false;
        }
    }

    //预案详情
    window.startPlan = function (planId,alarmingId,schemeId) {
        if (alarmingId == null || alarmingId == ""){
            alert("无法查阅预案详情，未找到该警情主键标识;");
            return false;
        }else if (planId == null || planId == ""){
            alert("无法查阅预案详情，未找到该预案主键标识;");
            return false;
        }else if (schemeId == null || schemeId == ""){
            alert("无法查阅预案详情，未找到该方案主键标识;");
            return false;
        }else {
            var url="/wms/toInfoPlan?planId="+planId+"&alarmingId="+alarmingId+"&schemeId="+schemeId;
            parent.page("预案详情", url, iframeObj, w = "95%", h = "90%");
            return false;
        }
    }

    //预案详情
    window.stopPlan = function (planId,alarmingId,schemeId) {
        if (alarmingId == null || alarmingId == ""){
            alert("无法查阅预案详情，未找到该警情主键标识;");
            return false;
        }else if (planId == null || planId == ""){
            alert("无法查阅预案详情，未找到该预案主键标识;");
            return false;
        }else if (schemeId == null || schemeId == ""){
            alert("无法查阅预案详情，未找到该方案主键标识;");
            return false;
        }else {
            var url="/wms/toInfoPlan?planId="+planId+"&alarmingId="+alarmingId+"&schemeId="+schemeId;
            parent.page("预案详情", url, iframeObj, w = "95%", h = "90%");
            return false;
        }
    }

    //警情方案启用设备详情
    window.useScheme = function (schemeId) {
        var alarmingId = $("#alarmingId").val();
        if (alarmingId == null || alarmingId == ""){
            alert("无法启用该预案，未找到该警情主键标识;");
            return false;
        }else if (schemeId == null || schemeId == ""){
            alert("无法查阅该预案，未找到该方案主键标识;");
            return false;
        }else {
            var url="/wms/toBindEquipments?schemeId="+schemeId+"&alarmingId="+alarmingId;
            parent.page("预案启用", url, iframeObj, w = "95%", h = "90%");
            return false;
        }
    }

    //添加方案按钮事件
    // $('.addSchemeBtn').click(function() {
    //     var url="/wms/toSchemeAdd";
    //     //将iframeObj传递给父级窗口,执行操作完成刷新
    //     parent.page("方案添加", url, iframeObj, w = "40%", h = "45%");
    //     return false;
    //
    // }).mouseenter(function() {
    //
    //     dialog.tips('添加', '.addSchemeBtn');
    //
    // });

    //新增方案提交
    // form.on('submit(schemeAddForm)', function (data) {
    //
    //     let articleFrom = data.field;
    //     if (articleFrom.schemeName == null || articleFrom.schemeName == ""){
    //         alert("方案名不能为空;");
    //         return false;
    //     }else {
    //         articleFrom.lecel = $("#levelSelect option:selected").text();
    //         articleFrom.disaster = $("#classDisasterSelect option:selected").text();
    //     }
    //     console.info(JSON.stringify(articleFrom));
    //     $.ajax({
    //         url: '/wms/schemeIncrease',
    //         type: 'post',
    //         data: JSON.stringify(articleFrom),
    //         dataType: "JSON",
    //         async: true,
    //         processData: false,	//不处理发送的数据
    //         contentType: 'application/json',
    //         success: function (result) {
    //
    //             layer.msg(result.msg);
    //         },
    //         error: function (error) {
    //             layer.msg('系统错误' + error);
    //         }
    //     });
    //
    //     return false;
    //
    // });


})

