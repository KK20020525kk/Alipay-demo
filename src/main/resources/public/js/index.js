layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);
    /**
     * ⽤户登录 表单提交
     */
    form.on("submit(login)", function(data){
        // 获取表单元素的值 （⽤户名 + 密码）
        var fieldData = data.field;
        // 判断参数是否为空
        if (fieldData.username == "undefined" || fieldData.username.trim() == "") {
            layer.msg("⽤户名称不能为空！");
            return false;
        }
        if (fieldData.password == "undefined" || fieldData.password.trim() == "") {
            layer.msg("⽤户密码不能为空！");
            return false;
        }
        // 发送 ajax 请求，请求⽤户登录
        $.ajax({
            type:"post",
            url:ctx + "/user/login",
            data:{
                userName:fieldData.username,
                userPwd:fieldData.password
            },
            dataType:"json",
            success:function(data){
                // 判断是否登录成功
                if (data.code == 200) {
                    layer.msg("登录成功！", function () {
                        // 将⽤户信息存到cookie中
                        var result = data.result;
                        $.cookie("userIdStr", result.userIdStr);
                        $.cookie("userName", result.userName);
                        $.cookie("trueName", result.trueName);
                        // 如果⽤户选择"记住我"，则设置cookie的有效期为7天
                        if($("input[type='checkbox']").is(":checked")){
                            $.cookie("userIdStr", result.userIdStr, { expires: 7 });
                            $.cookie("userName", result.userName, { expires: 7 });
                            $.cookie("trueName", result.trueName, { expires: 7 });
                        }
                        // 登录成功后，跳转到⾸⻚
                        window.location.href = ctx + "/main";
                    });
                } else {
                    // 提示信息
                    layer.msg(data.msg);
                }
            }
        });
        // 阻⽌表单跳转
        return false;
    });
});

/*
layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);
    // 进行登录操作
    form.on('submit(login)', function (data) {
        data = data.field;
        if ( data.username =="undefined" || data.username =="" || data.username.trim()=="") {
            layer.msg('用户名不能为空');
            return false;
        }
        if ( data.password =="undefined" || data.password =="" || data.password.trim()=="")  {
            layer.msg('密码不能为空');
            return false;
        }
        /!*发送 ajax 请求，请求⽤户登录*!/
        $.ajax({
            type:"post",
            url:ctx+"/user/login",
            data:{
                userName:data.username,
                userPwd:data.password
            },
            dataType:"json",
            success:function (data) {
                if(data.code==200){
                    // 判断是否登录成功
                    layer.msg('登录成功', function () {
                        // 将⽤户信息存到cookie中
                        var result =data.result;
                        $.cookie("userIdStr",result.userIdStr);
                        $.cookie("userName",result.userName);
                        $.cookie("trueName",result.trueName);
                        // 如果点击记住我 设置cookie 过期时间7天
                        if($("input[type='checkbox']").is(':checked')){
                            // 写入cookie 7天
                            $.cookie("userIdStr",result.userIdStr, { expires: 7 });
                            $.cookie("userName",result.userName, { expires: 7 });
                            $.cookie("trueName",result.trueName, { expires: 7 });
                        }
                        // 登录成功后，跳转到⾸⻚
                        window.location.href=ctx+"/main";
                    });
                }else{
                    // 提示信息
                    layer.msg(data.msg);
                }
            }
        });
        // 阻⽌表单跳转
        return false;
    });
});*/
