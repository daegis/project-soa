<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>活动添加</title>
    <#include "../common/import.ftl"/>
</head>
<body>
<#include "../common/head.ftl"/>
<div style="margin-top: 20px;margin-left: 20px;margin-right: 20px;">
    <form class="layui-form layui-form-pane" action="javascript:;" id="customerForm">
        <div class="layui-form-item">
            <label class="layui-form-label">活动名称</label>
            <div class="layui-input-inline">
                <input type="text" name="name" autocomplete="off" placeholder="请输入活动名称"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">活动日期</label>
            <div class="layui-input-inline">
                <input type="text" name="date" placeholder="请输入真实姓名" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">活动天数</label>
            <div class="layui-input-inline">
                <input type="text" name="dayCount" placeholder="请输入活动天数" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">活动价格</label>
            <div class="layui-input-inline">
                <input type="text" name="price" placeholder="请输入活动价格" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn" lay-submit="" lay-filter="submitBtn">确认添加</button>
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
                url: '${rc.contextPath}/activity/add',
                type: 'post',
                data: data.field,
                dataType: 'json',
                error: function () {
                    alert('服务器连接失败');
                },
                success: function (data) {
                    if (data.success) {
                        window.location.href = '${rc.contextPath}/to/activityList';
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