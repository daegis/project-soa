<#-- @ftlvariable name="rc" type="javax.servlet.http.HttpServletRequest" -->
<#-- @ftlvariable name="activity" type="cn.aegisa.project.model.ActivityInfo" -->
<#-- @ftlvariable name="formatter" type="java.time.format.DateTimeFormatter" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>活动详情</title>
    <#include "../common/import.ftl"/>
</head>
<body>
<#include "../common/head.ftl"/>
<fieldset class="layui-elem-field layui-field-title">
    <legend>当前活动: ${activity.activityName}</legend>
</fieldset>
<div>
    <table class="layui-table">
        <colgroup>
            <col width="250">
            <col width="150">
            <col width="120">
            <col width="120">
            <col width="120">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>活动名称</th>
            <th>开始日期</th>
            <th>天数</th>
            <th>当前人数</th>
            <th>价格</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${activity.activityName}</td>
            <td>${formatter.format(activity.activityDate)}</td>
            <td>${activity.dayCount?c}</td>
            <td>--</td>
            <td>${activity.price}</td>
        </tr>
        </tbody>
    </table>
</div>
<fieldset class="layui-elem-field layui-field-title">
    <legend>参加当前活动的人员列表</legend>
</fieldset>

<#include "../common/util.ftl"/>
<script>

</script>
</body>
</html>