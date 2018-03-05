<#-- @ftlvariable name="customer" type="cn.aegisa.project.model.CustomerInfo" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户添加</title>
    <#include "../common/import.ftl"/>
</head>
<body>
<#if noHead??><#else><#include "../common/head.ftl"/></#if>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 15px;">
    <legend>活动报表</legend>
</fieldset>
<div class="layui-form">
    <table class="layui-table">
        <colgroup>
            <col width="300">
            <col width="120">
            <col width="150">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>活动名称</th>
            <th colspan="3">报表操作</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                name
            </td>
            <td colspan="3">
                <a class="layui-btn layui-btn-small layui-btn-normal insurance">保险单</a>
                <a class="layui-btn layui-btn-small" target="_blank">免责声明</a>
                <a class="layui-btn layui-btn-small layui-btn-danger">常规人员登记表</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<#include "../common/util.ftl"/>
<script>

</script>
</body>
</html>