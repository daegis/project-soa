<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
    <#include "../common/import.ftl"/>
</head>
<body>
<hr class="layui-bg-green">
<div style="margin-top: 20px;margin-left: 20px;margin-right: 20px;">
    <form class="layui-form layui-form-pane" action="javascript:;" id="loginForm">
        <div class="layui-form-item">
            <label class="layui-form-label">用户名</label>
            <div class="layui-input-inline">
                <input type="text" name="name" autocomplete="off" placeholder="请输入用户名" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">密码</label>
                <div class="layui-input-inline">
                    <input type="password" name="password" autocomplete="off" placeholder="请输入密码" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn" lay-submit="" lay-filter="submitBtn">确认</button>
        </div>
    </form>
</div>
<#include "../common/util.ftl"/>
<script>
    layui.use(['form', 'jquery', 'laydate'], function () {
        var form = layui.form
                , layer = layui.layer
                , $ = layui.jquery
                , laydate = layui.laydate;

        form.on('submit(submitBtn)', function (data) {
            $.ajax({
                url: '${rc.contextPath}/user/doLogin',
                type: 'post',
                data: data.field,
                dataType: 'json',
                error: function () {
                    alert('服务器连接失败');
                },
                success: function (data) {
                    if (data.success) {
                        window.location.href = '${rc.contextPath}/';
                    } else {
                        doAlert(layer, data.message);
                    }
                }
            });
            return false;
        });
    });
</script>
</body>
</html>