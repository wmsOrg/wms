// layui.use(['table','form', 'jquery', 'laydate', 'layer', 'laypage', 'dialog', 'element', 'upload', 'layedit'], function() {
//     var form = layui.form,
//         layer = layui.layer,
//         $ = layui.jquery,
//         laypage = layui.laypage,
//         laydate = layui.laydate,
//         layedit = layui.layedit,
//         common = layui.common,
//         // tool = layui.tool,
//         table = layui.table,
//         element = layui.element,
//         dialog = layui.dialog;
//
//     //获取当前iframe的name值
//     var iframeObj = $(window.frameElement).attr('name');
//     $('.editAI').click(function() {
//         var url="/wms/toVisibleEditLayout";
//         //将iframeObj传递给父级窗口,执行操作完成刷新
//         parent.page("方案设备绑定", url, iframeObj, w = "80%", h = "70%");
//         return false;
//
//     }).mouseenter(function() {
//
//         dialog.tips('添加', '.addBtn');
//
//     });
//
//
// })
$(function () {



    $("[data-target=#shareModal]").click(function(e) {



        var request = {};
        var datas = [];
        var maxRowLength=0;
        var maxColumnLength=0;

        var schemeId = $("#schemeId").val();
        var alarmingId = $("#alarmingId").val();

        var requestBody = [];
        if ($("input:checkbox[name='cupboard']:checked").length <=0){
            return false;
        }
        $('input[name="cupboard"]:checked').each(function() {
            var entity = JSON.parse($(this).val());
            entity.alarmingId = alarmingId;
            if (entity != null && entity != "") {

                requestBody.push(entity);
            }else {
                alert("不能选择空柜！");
                return false;
            }
        });

        layer.open({
            type: 1
            ,area: ['340px', '180px']
            ,content: '<div style="text-align: center;margin-top: 8%;">预案名：<input id="planName" type="text" placeholder="请输入预案名称"/></div>'
            ,btn: ['确定', '取消']
            ,yes: function(){
                var requestContent = {};
                let planName = $("#planName").val();
                if (planName == null || planName == ""){
                    alert("请输入预案名称");
                    return;
                }else{
                    requestContent.planName = planName;
                    requestContent.alarmingId = alarmingId;
                    requestContent.schemeId = schemeId;
                    requestContent.datas = requestBody;
                }
                layer.close();
                $.ajax({
                    url: '/wms/alarmingBindEquipmentsInScheme',
                    type: 'post',
                    data: JSON.stringify(requestContent),
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
            }
            ,cancel: function () {
                layer.close();
            }
        });

        // $.ajax({
        //     url: '/wms/alarmingBindEquipmentsInScheme',
        //     type: 'post',
        //     data: JSON.stringify(requestBody),
        //     dataType: "JSON",
        //     async: true,
        //     processData: false,	//不处理发送的数据
        //     contentType: 'application/json',
        //     success: function (result) {
        //
        //         if (result.flag){
        //             layer.msg(result.msg);
        //         }else{
        //             layer.msg(result.msg);
        //         }
        //
        //     },
        //     error: function (error) {
        //         layer.msg('系统错误' + error);
        //     }
        // });

    });

    BindEquipements.initLayout();

});
var BindEquipements = new function () {

    /**
     * 控制iframe窗口的刷新操作
     */
    var iframeObjName;

    return {

        //初始化界面
        initLayout: function() {

            // let requestBody = {};
            // requestBody.schemeId = $("#schemeId").val();


            $.ajax({
                url: '/wms/searchEquipmentsBySchemeId?schemeId='+$("#schemeId").val(),
                type: 'get',
                // data: JSON.stringify(requestBody),
                dataType: "JSON",
                async: true,
                processData: false,	//不处理发送的数据
                // contentType: 'application/json',
                success: function (result) {

                    if (result.flag){
                        layer.msg(result.msg);
                        var data = result.data;
                        var schemeId = data.schemeId;
                        var maxRows = data.maxRows;
                        var maxColumns = data.maxColumns;
                        var datas = data.datas;
                        var length = datas.length;
                        var divHtmlContent = "";
                        var rowFlag = 0;
                        for (var i = 0; i < length; i++){
                            var entity = datas[i];

                            var currentRowNum = entity.rowNum;
                            var currentColumnNum = entity.columnNum;
                            if (currentColumnNum == 0){
                                divHtmlContent +="<div class=\"lyrow ui-draggable\" style=\"display: block;\">  \n" +
                                    "                            <div class=\"preview\">\n" +
                                    "                                <input value=\"1\" type=\"text\">\n" +
                                    "                            </div>\n" +
                                    "                            <div class=\"view\">\n" +
                                    "                                <div class=\"row-fluid clearfix\">" ;
                            }

                            divHtmlContent += "                     <div class=\"span1 column ui-sortable\">" +
                                "                                       <input name=\"cupboard\" type=\"checkbox\" value='"+JSON.stringify(entity)+"' ></input>" +
                                "                                       <input type=\"text\" style=\"display: none;\" name=\"rowCounts\" value=\""+entity.rowCounts+"\">" +
                                "                                       <input type=\"text\" style=\"display: none;\" name=\"columnCounts\" value=\""+entity.columnCounts+"\">" +
                                "                                       <input type=\"text\" style=\"display: none;\" name=\"companyId\" value=\""+entity.companyId+"\">" +
                                "                                       <input type=\"text\" style=\"display: none;\" name=\"equipmentId\" value=\""+entity.equipmentId+"\">" +
                                "                                       <input type=\"text\" style=\"display: none;\" name=\"equipmentName\" value=\""+entity.equipmentName+"\">    " +
                                "                                    </div>" ;
                            var temEntity;
                            if (i+1 < length){
                                temEntity = datas[i+1];
                                if (temEntity.rowNum !=  currentRowNum){
                                    divHtmlContent +=  "                                   </div>\n" +
                                        "                            </div>\n" +
                                        "                        </div>";
                                    rowFlag = currentRowNum;
                                }
                            }else if ((i+1) == length){
                                divHtmlContent +=  "                                   </div>\n" +
                                    "                            </div>\n" +
                                    "                        </div>";
                                rowFlag = currentRowNum;
                            }
                        }
                        $("#bindEquipmentId").html(divHtmlContent);
                    }else{
                        layer.msg(result.msg);
                    }

                },
                error: function (error) {
                    layer.msg('系统错误' + error);
                }
            });

        },


        //设备选定功能
        eventSelectEvent: function (e) {

            // var rowElement = $(e).parent("div").parent("div").parent("div").parent("div").prevAll();
            // var rowCounts = rowElement.length;
            //
            // var columnElement = $(e).parent("div").prevAll();
            // var columnCounts = columnElement.length;
            //
            // $(".lyrow ui-draggable").prevAll();
            //
            // var iframeObj = $(window.frameElement).attr('name');
            //
            // VisibleEditLayout.page("设备绑定", "/wms/toEquipmentBind?rowCounts="+rowCounts+"&columnCounts="+columnCounts, iframeObj, w = "700px", h = "620px");
            // return false;


        },
        //父级弹出页面
        page: function(title, url, obj, w, h) {
            if(title == null || title == '') {
                title = false;
            };
            if(url == null || url == '') {
                url = "404.html";
            };
            if(w == null || w == '') {
                w = '700px';
            };
            if(h == null || h == '') {
                h = '350px';
            };
            iframeObjName = obj;
            //如果手机端，全屏显示
            if(window.innerWidth <= 768) {
                var index = layer.open({
                    type: 2,
                    title: title,
                    area: [320, h],
                    fixed: false, //不固定
                    content: url,
                    btn: ['确认', '关闭'],
                    btnclass: ['btn btn-primary', 'btn btn-danger'],
                    yes: function (index, layero) {
                        var tem = $(layero).find("iframe")[0].contentWindow.bindCallback();
                        // PicCallBack(tem);
                        layer.close(index);

                        $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='rowCounts']").val(tem.rowCounts);
                        $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='columeCounts']").val(tem.columnCounts);
                        $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='companyId']").val(tem.pId);
                        $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='equipmentId']").val(tem.id);
                        $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='equipmentName']").val(tem.name);
                        }, cancel: function () {
                        return true;
                    }
                });
                layer.full(index);
                return index;
            } else {
                var index = layer.open({
                    type: 2,
                    title: title,
                    area: [w, h],
                    fixed: false, //不固定
                    content: url,
                    btn: ['确认', '关闭'],
                    btnclass: ['btn btn-primary', 'btn btn-danger'],
                    yes: function (index, layero) {
                        var tem = $(layero).find("iframe")[0].contentWindow.bindCallback();
                        // PicCallBack(tem);
                        layer.close(index);
                        // var a = $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='rowCounts']").val();
                        $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='rowCounts']").val(tem.rowCounts);
                        $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='columeCounts']").val(tem.columnCounts);
                        $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='companyId']").val(tem.pId);
                        $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='equipmentId']").val(tem.id);
                        $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='equipmentName']").val(tem.name);
                        $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).css("background","gray");
                    }, cancel: function () {
                        return true;
                    }
                });
                return index;
            }
        }
    }


}



