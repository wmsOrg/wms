$(function () {
});
var Authenticate = new function () {
    return {
        //注册
        regist: function () {
            let requestData = {};
            requestData.userName = $("#username_regist").val();
            requestData.password = $("#password_regist").val();
            requestData.confirmPassword = $("#confirm_password_regist").val();
            requestData.mobile = $("#mobile_regist").val();
            let url = "/wms/regist";

            $.ajax({
                url: url,
                type: 'POST',
                data: JSON.stringify(requestData),
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
        },
        //登录
        login: function () {
            let requestData = {};
            requestData.userName = $("#username_login").val();
            requestData.password = $("#password_login").val();
            let url = "/wms/login";
            console.info(JSON.stringify(requestData));
            $.ajax({
                url: url,
                type: 'POST',
                data: JSON.stringify(requestData),
                dataType: "json",
                cache: false,
                async: true,
                processData: false,	//不处理发送的数据
                contentType: 'application/json',
                success: function (data) {
                    console.info(data);
                    window.location.href="/wms/toIndex";
                }
            });
        }
    }
}