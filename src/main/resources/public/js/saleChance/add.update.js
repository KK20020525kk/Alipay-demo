layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    /**
     * 关闭弹出层
     */
    $("#closeBtn").click(function () {
        //当你在iframe页面关闭自身时
        var index=parent.layer.getFrameIndex(window.name); //得到当前iframe的索引
        parent.layer.close(index);
    });

    $.post(ctx+"/user/queryAllCustomerManager",function (res) {
        for (var i = 0; i < res.length; i++) {
            if($("input[name='man']").val() == res[i].id ){
                $("#assignMan").append("<option value=\"" + res[i].id + "\" selected='selected' >" + res[i].name + "</option>");
            }else {
                $("#assignMan").append("<option value=\"" + res[i].id + "\">" + res[i].name + "</option>");
            }
        }
        //重新渲染
        layui.form.render("select");
    });


    form.on("submit(addOrUpdateSaleChance)", function (data) {
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8});
        //弹出loading
        var url=ctx + "/sale_chance/add";
        var saleChanceId=$("[name='id']").val();
        if(saleChanceId!=null &&saleChanceId !=''){
            url=ctx + "/sale_chance/update";
        }
        $.post(url, data.field, function (result) {
            if (result.code == 200) {
                top.layer.msg("操作成功！",{icon:6});
                    layer.close(index);
                    layer.closeAll("iframe");
                    parent.location.reload();
            } else {
                layer.msg(result.msg, {icon: 5});
            }
        });
        return false;
    });

    /**
     * 加载指派人的下拉框
     *
     */

/*    $.ajax({
        type:"get",
        url:ctx+"/user/queryAllSales",
        data:{},
        success:function (data){
            if(data!=null){
                var assignManId=$("#assignManId").val();
                for(var i=0;i<data.length;i++){
                    console.log(i)
                    var opt="";
                    if(assignManId==data[i].id){
                        opt="<option value='"+data[i].id+"'selected>"+data[i].userName+"</option>";
                    }else {
                        opt="<option value='"+data[i].id+"'>"+data[i].userName+"</option>";
                    }
                    $("#assignMan").append(opt);
                }
            }
            layui.from.render("select");
        }
    });*/

    /**
     * 加载下拉框
     */
  /*  $.post(ctx + "/user/queryAllSales",function (data) {
        // 如果是修改操作，判断当前修改记录的指派⼈的值
        var assignMan = $("input[name='man']").val();
        for (var i = 0; i < data.length; i++) {
            // 当前修改记录的指派⼈的值 与 循环到的值 相等，下拉框则选中
            if (assignMan == data[i].id) {
                $("#assignMan").append('<option value="'+data[i].id+'"selected>'+data[i].uname+'</option>');
            } else {
                $("#assignMan").append('<optionvalue="'+data[i].id+'">'+data[i].uname+'</option>');
            }
        }
        // 重新渲染下拉框内容
        layui.form.render("select");
    });*/

    /**
     * 加载指派人的下拉框
     */
    $.ajax({
        type:"get",
        url:ctx + "/user/queryAllSales",
        data:{},
        success:function (data) {
            // console.log(data);
            // 判断返回的数据是否为空
            if (data != null) {
                // 获取隐藏域设置的指派人ID
                var assignManId = $("#assignManId").val();
                // 遍历返回的数据
                for(var i = 0; i < data.length; i++) {
                    var opt = "";
                    // 如果循环得到的ID与隐藏域的ID相等，则表示被选中
                    if (assignManId == data[i].id) {
                        // 设置下拉选项  设置下拉选项选中
                        opt = "<option value='"+data[i].id+"' selected>"+data[i].uname+"</option>";
                    } else {
                        // 设置下拉选项
                        opt = "<option value='"+data[i].id+"'>"+data[i].uname+"</option>";
                    }

                    // 将下拉项设置到下拉框中
                    $("#assignMan").append(opt);
                }
            }
            // 重新渲染下拉框的内容
            layui.form.render("select");
        }
    });


});