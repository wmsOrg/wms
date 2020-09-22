$(function () {

    $.ajax({
        url: '/wms/schemeContentList?schemeId='+$("#schemeId").val(),
        type: 'get',
        // data: JSON.stringify(requestBody),
        dataType: "JSON",
        async: true,
        processData: false,	//不处理发送的数据
        // contentType: 'application/json',
        success: function (result) {

            if (result.flag){
                // layer.msg(result.msg);
                var data = result.data;
                var schemeId = data.schemeId;
                var maxRows = data.totalRowNums;
                var maxColumns = data.totalColumnNums;
                var datas = data.categorys;
                var length = datas.length;
                var divHtmlContent = "";
                var rowFlag = 0;
                for (var i = 0; i < length; i++){
                    var entity = datas[i];
                    var equipmentParentId = entity.equipmentIdLv1;
                    var currentRowNum = entity.rowNum;
                    var currentColumnNum = entity.columnNum;
                    if (currentColumnNum == 0){
                        // divHtmlContent +="<div class=\"lyrow ui-draggable\" style=\"display: block;\">  \n" +
                        //     "                            <div class=\"preview\">\n" +
                        //     "                                <input value=\"1\" type=\"text\">\n" +
                        //     "                            </div>\n" +
                        //     "                            <div class=\"view\">\n" +
                        //     "                                <div class=\"row-fluid clearfix\">" ;
                        divHtmlContent += "<ul>\n";
                    }

                    divHtmlContent += "          <li class=\"category\">\n" +
                        "            <a href=\"javascript:;\" onclick=\"searchEquipmentLV2InScheme('" + equipmentParentId + "');return false;\" class=\"clearfix\">\n" +
                        "              <div class=\"icon-bg-up bg-gray f-l\"></div>\n" +
                        "              <div class=\"icon-bg-down bg-gray f-l\"></div>\n" +
                        "              <div class=\"right-text-con\">\n" +
                        "                <p class=\"name\">"+entity.equipmentNameLv1+"</p>\n" +
                        "                <p>内部设备:总计<span class=\"color-gray\">" + entity.equipmentLv2Number + "</span></p>\n" +
                        "              </div>\n" +
                        "            </a>\n" +
                        "          </li>";
                    if (i+1<length){
                        if (datas[i].rowNum != datas[i+1].rowNum){
                            divHtmlContent += "</ul>";
                        }
                    }else{
                        divHtmlContent += "</ul>";
                    }
                }
                $("#currentcategory").html(divHtmlContent);
            }else{
                layer.msg(result.msg);
            }

        },
        error: function (error) {
            layer.msg('系统错误' + error);
        }
    });

});