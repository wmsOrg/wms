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
        var rows = $("#visibleLayoutId").children("div");
        var rowsH = rows.html();
        if (rowsH == null || rowsH == undefined || rowsH.length == 0){
            alert("页面为空，无法保存！");
            return false;
        }
        var rowlength = rows.siblings().length;
        if (rowlength==0){
            rowlength = rowlength + 1;
        }
        maxRowLength = rowlength;
        for (var i = 0; i < rowlength; i++){
            var columns = rows.eq(parseInt(i)).find("div[class='span1 column ui-sortable']");
            // var columnsH = rows.eq(parseInt(i)).find("div[class='span1 column ui-sortable']").html();
            var columnLength = columns.siblings().length;
            if (columnLength==0){
                columnLength = columnLength + 1;
            }
            if (maxColumnLength < columnLength){
                maxColumnLength = columnLength;
            }
            for (var j = 0; j < columnLength; j++){
                var cell = columns.eq(parseInt(j));
                var rowCounts = i;
                var columnCounts = j;
                var companyId = cell.find("input[name='companyId']").val();
                var equipmentId = cell.find("input[name='equipmentId']").val();
                var equipmentName = cell.find("input[name='equipmentName']").val();
                var data = {};
                data.rowCounts = rowCounts;
                data.columnCounts = columnCounts;
                data.companyId = companyId;
                data.equipmentId = equipmentId;
                data.equipmentName = equipmentName;

                datas.push(data);
            }

        }
        request.schemeId = schemeId;
        request.maxRows = maxRowLength;
        request.maxColumns = maxColumnLength;
        request.datas = datas;
        VisibleEditLayout.showQRCode(request);
        // request.image = imageString;

        // $.ajax({
        //     url: '/wms/schemeEdit',
        //     type: 'post',
        //     data: JSON.stringify(request),
        //     dataType: "JSON",
        //     async: false,
        //     processData: false,	//不处理发送的数据
        //     contentType: 'application/json',
        //     success: function (result) {
        //
        //         if (result.flag){
        //             layer.msg(result.msg);
        //             // VisibleEditLayout.showQRCode();
        //         }else{
        //             layer.msg(result.msg);
        //         }
        //
        //     },
        //     error: function (error) {
        //         layer.msg('系统错误' + error);
        //     }
        // });

        return false;


        // e.preventDefault();
        // handleSaveLayout();
    });

});
var VisibleEditLayout = new function () {

    /**
     * 控制iframe窗口的刷新操作
     */
    var iframeObjName;

    return {
        //设备绑定界面
        editAIEvent: function (e) {

            var rowElement = $(e).parent("div").parent("div").parent("div").parent("div").prevAll();
            var rowCounts = rowElement.length;

            var columnElement = $(e).parent("div").prevAll();
            var columnCounts = columnElement.length;

            $(".lyrow ui-draggable").prevAll();

            var iframeObj = $(window.frameElement).attr('name');

            VisibleEditLayout.page("设备绑定", "/wms/toEquipmentBind?rowCounts=" + rowCounts + "&columnCounts=" + columnCounts, iframeObj, w = "700px", h = "620px");
            return false;


        },
        //父级弹出页面
        page: function (title, url, obj, w, h) {
            if (title == null || title == '') {
                title = false;
            }
            ;
            if (url == null || url == '') {
                url = "404.html";
            }
            ;
            if (w == null || w == '') {
                w = '700px';
            }
            ;
            if (h == null || h == '') {
                h = '350px';
            }
            ;
            iframeObjName = obj;
            //如果手机端，全屏显示
            if (window.innerWidth <= 768) {
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
                        if (tem.linkingNo != null && tem.linkingNo != ''){
                            layer.confirm('该设备有关联设备，添加该设备需要将其关联设备一起添加，确定添加吗?', {icon: 3, title:'提示'}, function(index) {

                                layer.close(index);
                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='rowCounts']").val(tem.rowCounts);
                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='columeCounts']").val(tem.columnCounts);
                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='companyId']").val(tem.pId);
                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='equipmentId']").val(tem.id);
                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='equipmentName']").val(tem.name);

                                $.ajax({
                                    url: '/wms/searchByLinkingNo?linkingNo='+tem.linkingNo,
                                    type: 'get',
                                    // data: JSON.stringify(articleFrom),
                                    dataType: "JSON",
                                    async: true,
                                    processData: false,	//不处理发送的数据
                                    contentType: 'application/json',
                                    success: function (result) {

                                        if (result.flag) {
                                            if (result.data != null && result.data.length != 0) {
                                                var resultLength = result.data.length;
                                                var htmlContent = "";
                                                for (var i = 0; i< resultLength; i++) {
                                                    htmlContent += "<div class=\"span1 column ui-sortable\" style=\"background: gray;\"><input type=\"text\" style=\"display: none;\" name=\"rowCounts\" value=\"" + tem.rowCounts + "\"><input type=\"text\" style=\"display: none;\" name=\"columnCounts\" value=\""+(parseInt(tem.columnCounts)+parseInt(i)+1)+"\"><input type=\"text\" style=\"display: none;\" name=\"companyId\" value=\""+result.data[i].companyId+"\"><input type=\"text\" style=\"display: none;\" name=\"equipmentId\" value=\""+result.data[i].id+"\"><input type=\"text\" style=\"display: none;\" name=\"equipmentName\" value=\""+result.data[i].equipmentName+"\"><a href=\"javascript:void(0);\" onclick=\"VisibleEditLayout.editAIEvent(this);\" style=\"position: absolute;top: 2px; right:2px;\" class=\"label label-important\"><i class=\"icon-edit icon-white\"></i>编辑</a></div>";
                                                }
                                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).after(htmlContent);
                                            }else{
                                                layer.msg('查询数据为空：' + result.msg);
                                            }


                                        }else{
                                            layer.msg('查询失败' + result.msg);
                                        }

                                    },
                                    error: function (error) {
                                        layer.msg('系统错误' + error);
                                    }
                                });
                            });
                        }else{
                            layer.close(index);
                            // var a = $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='rowCounts']").val();
                            $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='rowCounts']").val(tem.rowCounts);
                            $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='columeCounts']").val(tem.columnCounts);
                            $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='companyId']").val(tem.pId);
                            $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='equipmentId']").val(tem.id);
                            $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='equipmentName']").val(tem.name);
                            $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).css("background", "gray");
                        }
                        layer.close(index);
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
                        if (tem.linkingNo != null && tem.linkingNo != ''){
                            layer.confirm('该设备有关联设备，添加该设备需要将其关联设备一起添加，确定添加吗?', {icon: 3, title:'提示'}, function(index){
                                layer.close(index);
                                // var a = $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='rowCounts']").val();
                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='rowCounts']").val(tem.rowCounts);
                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='columeCounts']").val(tem.columnCounts);
                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='companyId']").val(tem.pId);
                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='equipmentId']").val(tem.id);
                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='equipmentName']").val(tem.name);
                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).css("background", "gray");

                                $.ajax({
                                    url: '/wms/searchByLinkingNo?linkingNo='+tem.linkingNo+"&equipmentId="+tem.id,
                                    type: 'get',
                                    // data: JSON.stringify(articleFrom),
                                    dataType: "JSON",
                                    async: true,
                                    processData: false,	//不处理发送的数据
                                    contentType: 'application/json',
                                    success: function (result) {

                                        if (result.flag) {
                                            if (result.data != null && result.data.length != 0) {
                                                var resultLength = result.data.length;
                                                var htmlContent = "";
                                                for (var i = 0; i< resultLength; i++) {
                                                    htmlContent += "<div class=\"span1 column ui-sortable\" style=\"background: gray;\"><input type=\"text\" style=\"display: none;\" name=\"rowCounts\" value=\"" + tem.rowCounts + "\"><input type=\"text\" style=\"display: none;\" name=\"columnCounts\" value=\""+(parseInt(tem.columnCounts)+parseInt(i)+1)+"\"><input type=\"text\" style=\"display: none;\" name=\"companyId\" value=\""+result.data[i].equipmentCompanyId+"\"><input type=\"text\" style=\"display: none;\" name=\"equipmentId\" value=\""+result.data[i].id+"\"><input type=\"text\" style=\"display: none;\" name=\"equipmentName\" value=\""+result.data[i].equipmentName+"\"><a href=\"javascript:void(0);\" onclick=\"VisibleEditLayout.editAIEvent(this);\" style=\"position: absolute;top: 2px; right:2px;\" class=\"label label-important\"><i class=\"icon-edit icon-white\"></i>编辑</a></div>";
                                                }
                                                $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).after(htmlContent);
                                            }else{
                                                layer.msg('查询数据为空：' + result.msg);
                                            }


                                        }else{
                                            layer.msg('查询失败' + result.msg);
                                        }

                                    },
                                    error: function (error) {
                                        layer.msg('系统错误' + error);
                                    }
                                });
                            });
                        }else {

                            // var a = $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='rowCounts']").val();
                            $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='rowCounts']").val(tem.rowCounts);
                            $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='columeCounts']").val(tem.columnCounts);
                            $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='companyId']").val(tem.pId);
                            $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='equipmentId']").val(tem.id);
                            $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).find("input[name='equipmentName']").val(tem.name);
                            $("#visibleLayoutId").children("div").eq(parseInt(tem.rowCounts)).find("div[class='span1 column ui-sortable']").eq(parseInt(tem.columnCounts)).css("background", "gray");
                        }
                        layer.close(index);
                    }, cancel: function () {
                        layer.close(index);
                        return true;
                    }
                });
                return index;
            }
        },
        showQRCode: function (obj) {
            // scrollTo(0, 0);
            var imageBase64 = "";

            //克隆节点，默认为false，即不复制方法属性，为true是全部复制。
            var cloneDom = $("#visibleLayoutId").clone(true);
            //设置克隆节点的z-index属性，只要比被克隆的节点层级低即可。
            cloneDom.css({
                "background-color": "#fafafa",
                "position": "absolute",
                "top": "0px",
                "left": "0px",
                "z-index": "-1",
                "height": document.body.scrollHeight,
                "width": document.body.scrollWidth
            });

            if (typeof html2canvas !== 'undefined') {
                //以下是对svg的处理
                var nodesToRecover = [];
                var nodesToRemove = [];
                var svgElem = cloneDom.find('visibleLayoutId');//divReport为需要截取成图片的dom的id
                svgElem.each(function (index, node) {
                    var parentNode = node.parentNode;
                    var svg = node.outerHTML.trim();

                    var canvas = document.createElement('canvas');
                    canvas.width = document.body.scrollWidth;
                    canvas.height = document.body.scrollHeight;
                    canvg(canvas, svg);
                    if (node.style.position) {
                        canvas.style.position += node.style.position;
                        canvas.style.left += node.style.left;
                        canvas.style.top += node.style.top;
                    }

                    nodesToRecover.push({
                        parent: parentNode,
                        child: node
                    });
                    parentNode.removeChild(node);

                    nodesToRemove.push({
                        parent: parentNode,
                        child: canvas
                    });

                    parentNode.appendChild(canvas);
                });

                //将克隆节点动态追加到body后面。
                $("body").append(cloneDom);

                html2canvas(cloneDom, {
                    onrendered: function (canvas) {
                        var imageString = canvas.toDataURL("image/png");
                        // console.info(url);
                        // window.location.href = imageString;

                        imageBase64 = imageString;
                        obj.image = imageBase64;

                        $.ajax({
                            url: '/wms/schemeEdit',
                            type: 'post',
                            data: JSON.stringify(obj),
                            dataType: "JSON",
                            async: false,
                            processData: false,	//不处理发送的数据
                            contentType: 'application/json',
                            success: function (result) {

                                if (result.flag){
                                    layer.msg(result.msg);
                                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引

                                    parent.layer.close(index); //再执行关闭
                                    // VisibleEditLayout.showQRCode();
                                }else{
                                    layer.msg(result.msg);
                                }

                            },
                            error: function (error) {
                                layer.msg('系统错误' + error);
                            }
                        });
                        cloneDom.remove();    //清空克隆的内容
                        return imageBase64;
                    },
                    background: "#fafafa",
                    timeout: 0
                });

            }

            return imageBase64;
        }
    }


}



