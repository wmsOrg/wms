$(function () {
    // schemeExt.querySchemes(null);
});
var schemeExt = new function () {

    /**
     * 控制iframe窗口的刷新操作
     */
    var iframeObj = $(window.frameElement).attr('name');
    return {

        querySchemes: function(data){
            // let articleFrom = data.field;
            let articleFrom = {};
            articleFrom.schemeName = "";
            // if (articleFrom.schemeName == null || articleFrom.schemeName == ""){
            //     alert("方案名不能为空;");
            //     return false;
            // }else {
            //     articleFrom.lecel = $("#levelSelect option:selected").text();
            //     articleFrom.disaster = $("#classDisasterSelect option:selected").text();
            // }
            // console.info(JSON.stringify(articleFrom));
            $.ajax({
                url: '/wms/schemeList',
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

                            var divhtml = "<form class=\"layui-form\" id=qwe"+i+">\n" +
                                "                <div class=\"layui-col-xs6 layui-col-md3\" style=\"padding: 15px;text-align: center\">\n" +
                                "                    <div class=\"layui-inline tool-btn\" style=\"float: left;width: 3px;height: 3px\">\n" +
                                // "                        <input type=\"checkbox\" name=\"schemeLineCbx\" lay-skin=\"primary\"  lay-filter=\"schemeLineCbx\" style='display: block'>\n" +
                                "                        <input type=\"text\" name=\"schemeId\" style=\"display: none\" value='"+divData.id+"'>\n" +
                                "                    </div>\n" +
                                "\n" +
                                "                        <div class=\"layui-inline\">\n" +
                                "                            <input type=\"checkbox\" name=\"validState\" lay-skin=\"switch\" checked=\"\" style=\"min-width: 0px;height: 0px;\">\n" +
                                "                        </div>\n" +
                                "\n" +
                                "                    <div class=\"layui-inline tool-btn\" style=\"float: right;\">\n" +
                                "                        <button class=\"layui-btn layui-btn-xs layui-btn-normal schemeLineBtn\" lay-event=\"info\"><i class=\"layui-icon\" style=\"color: #323232;\">&#xe705;</i></button>\n" +
                                "                        <button class=\"layui-btn layui-btn-xs layui-btn-warm schemeLineBtn\" onclick='schemeExt.editScheme(\""+divData.id+"\")' lay-filter='editSchemeLineBtn' lay-event=\"edit\"><i class=\"layui-icon\" style=\"color: #323232;\">&#xe642;</i></button>\n" +
                                "                        <button class=\"layui-btn layui-btn-xs layui-btn-danger schemeLineBtn\" lay-event=\"dels\"><i class=\"layui-icon\" style=\"color: #323232;\">&#xe640;</i></button>\n" +
                                "                    </div>\n" +
                                "                    <a class=\"template store-list-box\" href=\"/css/i/463043/1.html\"> <img class=\"store-list-cover\" src=\""+imageUrl+"\"> <h2 class=\"layui-elip\">"+divData.schemeName+"</h2>\n" +
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
        },

        //编辑提交
        editScheme: function (obj) {
            if (obj == null || obj == ""){
                alert("无法编辑，未找到该方案主键标识;");
                return false;
            }else {
                // $("#table-list").dialog();
                var url="/wms/toVisibleEditLayout?schemeId="+obj;
                // schemeExt.page("方案设备添加", url, iframeObj, w = "95%", h = "90%");
                layer.open({
                    type: 1 //Page层类型
                    ,area: ['500px', '300px']
                    ,title: '你好，layer。'
                    ,shade: 0.6 //遮罩透明度
                    ,maxmin: true //允许全屏最小化
                    ,anim: 1 //0-6的动画形式，-1不开启
                    ,content: '<div style="padding:50px;">这是一个非常普通的页面层，传入了自定义的html</div>'
                    ,btn:['按钮bai1','按钮2','按钮3'],
                    time: 10000,
                    yes:function(index,layero){
                        alert("按钮1");
                    },
                    btn2:function(index,layero) {
                        alert("按钮2");
                    }
                })
            }
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
                    content: url
                });
                layer.full(index);
            } else {
                var index = layer.open({
                    type: 2,
                    title: title,
                    area: [w, h],
                    fixed: false, //不固定
                    content: url
                });
            }

        }
    }


}



