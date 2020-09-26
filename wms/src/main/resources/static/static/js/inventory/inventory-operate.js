$(function () {

    var alarmingId = $("#alarmingId").val();
    var planId = $("#planId").val();
    var schemeId = $("#schemeId").val();
    var articleFrom = {};
    articleFrom.alarmingId = alarmingId;
    articleFrom.planId = planId;
    articleFrom.schemeId = schemeId;

    $.ajax({
        url: '/wms/planContentList',
        type: 'post',
        data: JSON.stringify(articleFrom),
        dataType: "JSON",
        async: true,
        processData: false,	//不处理发送的数据
        contentType: 'application/json',
        success: function (result) {
            if (result.flag) {
                var datas= result.data;
                if (datas != null) {
                    var categories = datas.categorys;
                    if (categories != null && categories.length != 0) {
                        var length = categories.length;
                        var htmlContent = "";
                        for (var i = 0; i < length; i++) {
                            var data = categories[i];
                            var equipmentParentId = data.equipmentIdLv1;
                            if (data.columnNum == 0) {
                                htmlContent += "<ul>\n";
                            }

                            if(data.equipmentSelectedLv1 != "1") {
                                htmlContent += "          <li class=\"category\">\n" +
                                    "            <a href=\"javascript:;\" onclick=\"searchEquipment('" + equipmentParentId + "');return false;\" class=\"clearfix\">\n" +
                                    "              <div class=\"icon-bg-up bg-gray f-l\"></div>\n" +
                                    "              <div class=\"icon-bg-down bg-gray f-l\"></div>\n" +
                                    "              <div class=\"right-text-con\">\n" +
                                    "                <p class=\"name\">"+data.equipmentNameLv1+"</p>\n" +
                                    "                <p>内部设备:总计<span class=\"color-gray\">" + data.equipmentLv2Number + "</span>入库<span class=\"color-gray\">" + data.equipmentLv2InNumber + "</span>出库<span class=\"color-gray\">" + data.equipmentLv2OutNumber + "</span></p>\n" +
                                    "              </div>\n" +
                                    "            </a>\n" +
                                    "          </li>";
                            }else{
                                if (data.equipmentInLv1 == '1') {
                                    if (data.equipmentLv2Number == data.equipmentLv2InNumber) {
                                        htmlContent += "          <li class=\"category\">\n" +
                                            "            <a href=\"javascript:;\" onclick=\"searchEquipment('" + equipmentParentId + "');return false;\" class=\"clearfix\">\n" +
                                            "              <div class=\"icon-bg-up bg-green f-l\"></div>\n" +
                                            "              <div class=\"icon-bg-down bg-green f-l\"></div>\n" +
                                            "              <div class=\"right-text-con\">\n" +
                                            "                <p class=\"name\">"+data.equipmentNameLv1+"</p>\n" +
                                            "                <p>内部设备:总计<span class=\"color-org\">" + data.equipmentLv2Number + "</span>入库<span class=\"color-green\">" + data.equipmentLv2InNumber + "</span>出库<span class=\"color-red\">" + data.equipmentLv2OutNumber + "</span></p>\n" +
                                            "              </div>\n" +
                                            "            </a>\n" +
                                            "          </li>";
                                    }else {
                                        htmlContent += "          <li class=\"category\">\n" +
                                            "            <a href=\"javascript:;\" onclick=\"searchEquipment('" + equipmentParentId + "');return false;\" class=\"clearfix\">\n" +
                                            "              <div class=\"icon-bg-up bg-green f-l\"></div>\n" +
                                            "              <div class=\"icon-bg-down bg-red f-l\"></div>\n" +
                                            "              <div class=\"right-text-con\">\n" +
                                            "                <p class=\"name\">"+data.equipmentNameLv1+"</p>\n" +
                                            "                <p>内部设备:总计<span class=\"color-org\">" + data.equipmentLv2Number + "</span>入库<span class=\"color-green\">" + data.equipmentLv2InNumber + "</span>出库<span class=\"color-red\">" + data.equipmentLv2OutNumber + "</span></p>\n" +
                                            "              </div>\n" +
                                            "            </a>\n" +
                                            "          </li>";
                                    }
                                }else{
                                    htmlContent += "          <li class=\"category\">\n" +
                                        "            <a href=\"javascript:;\" onclick=\"searchEquipment('" + equipmentParentId + "');return false;\" class=\"clearfix\">\n" +
                                        "              <div class=\"icon-bg-up bg-red f-l\"></div>\n" +
                                        "              <div class=\"icon-bg-down bg-red f-l\"></div>\n" +
                                        "              <div class=\"right-text-con\">\n" +
                                        "                <p class=\"name\">"+data.equipmentNameLv1+"</p>\n" +
                                        "                <p>内部设备:总计<span class=\"color-org\">" + data.equipmentLv2Number + "</span>入库<span class=\"color-green\">" + data.equipmentLv2InNumber + "</span>出库<span class=\"color-red\">" + data.equipmentLv2OutNumber + "</span></p>\n" +
                                        "              </div>\n" +
                                        "            </a>\n" +
                                        "          </li>";
                                }
                            }
                            if (i+1<length){
                                if (categories[i].rowNum != categories[i+1].rowNum){
                                    htmlContent += "</ul>";
                                }
                            }else{
                                htmlContent += "</ul>";
                            }
                        }
                        $("#currentcategory").html(htmlContent);
                    }
                }
            }

            layer.msg(result.msg);
        },
        error: function (error) {
            layer.msg('系统错误' + error);
        }
    });

    websocketCongig();

});

function websocketCongig() {

    var websocket = null;

//判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/websocket/1");
    } else {
        alert('当前浏览器 Not support websocket')
    }

//连接发生错误的回调方法
    websocket.onerror = function() {
        console.log("WebSocket连接发生错误");
    };

//连接成功建立的回调方法
    websocket.onopen = function() {
        console.log("WebSocket连接成功");
    }

//接收到消息的回调方法
    websocket.onmessage = function(event) {
        //返回数据转JSON
        // var json=JSON.parse(event.data);
        //result为bootstrap table 返回数据
        // var rows=result.rows;
        // for(var i=0;i<rows.length;i++){
        //     var row=rows[i];
        //     if(row.id==json.id){
        //         //判断列Id相同时刷新表格
        //         //$('#dataGrid').bootstrapTable('updateByUniqueId', {index: i, row: row});'refresh'
        //         $('#dataGrid').bootstrapTable('refresh');
        //     }
        // }
        console.log(event);
    }

//连接关闭的回调方法
    websocket.onclose = function() {
        console.log("WebSocket连接关闭");
    }

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function() {
        closeWebSocket();
    }

//关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

}