<#-- @ftlvariable name="formatter" type="java.time.format.DateTimeFormatter" -->
<#-- @ftlvariable name="activity" type="cn.aegisa.project.model.ActivityInfo" -->
<!DOCTYPE html>
<html lang="cn">
<head>
    <meta charset="UTF-8">
    <title>活动添加</title>
    <#include "../common/import.ftl"/>
</head>
<body>
<#if noHead??><#else><#include "../common/head.ftl"/></#if>
<div style="margin-top: 20px;margin-left: 20px;margin-right: 20px;">
    <form class="layui-form layui-form-pane" action="javascript:;" id="customerForm">
        <#if (activity??&&activity.id??)><input name="id" value="${activity.id}" type="hidden"></#if>
        <div class="layui-form-item">
            <label class="layui-form-label">活动名称</label>
            <div class="layui-input-inline">
                <input type="text" name="name" autocomplete="off" placeholder="请输入活动名称"
                       <#if (activity??&&activity.activityName??)>value="${activity.activityName}"</#if>
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">日期选择</label>
                <div class="layui-input-inline">
                    <input type="text" name="date" id="date" autocomplete="off"
                           placeholder="请选择活动时间" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">活动天数</label>
            <div class="layui-input-inline">
                <input type="text" name="dayCount" placeholder="请输入活动天数" autocomplete="off"
                       <#if (activity??&&activity.dayCount??)>value="${activity.dayCount?c}"</#if>
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">活动价格</label>
            <div class="layui-input-inline">
                <input type="text" name="price" placeholder="请输入活动价格" autocomplete="off"
                       <#if (activity??&&activity.price??)>value="${activity.price?c}"</#if>
                       class="layui-input">
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

        laydate.render({
            elem: '#date'
            <#if (activity??&&activity.activityDate??)>
                , value: '${formatter.format(activity.activityDate)}'
            </#if>
        });

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
                        layer.confirm('<#if (activity??&&activity.id??)>修改成功<#else>添加成功</#if>', {
                            btn: ['离开'] //按钮
                        }, function () {
                           <#if (activity??&&activity.id??)>
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.window.location.reload();
                           <#else>
                            window.location.href = '${rc.contextPath}/to/activityList';
                           </#if>
                        });
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