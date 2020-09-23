$(function () {
    initPage();

    setInterval(function(){ initPage(); }, 3000);


});

function initPage() {
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
}