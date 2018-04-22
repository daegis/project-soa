<#-- @ftlvariable name="activities" type="java.util.List" -->
<#-- @ftlvariable name="a" type="cn.aegisa.project.model.ActivityInfo" -->
<#-- @ftlvariable name="customer" type="cn.aegisa.project.model.CustomerInfo" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>摄影团免责声明</title>
    <#include "../common/import.ftl"/>
</head>
<body>
<#if pageList??>
    <#list pageList as table>
    <div style="page-break-after:always;">
        <p style="font-size: 25px;margin-top: 20px;text-align: center">
            ${name}免责声明
        </p>
        <hr class="layui-bg-green">
        <p style="font-size: 18px">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本人自愿报名参加这次自助游性质的摄影活动，
            活动中对自己的人身安全和摄影器材等物品的安全负责，
            如果发生任何意外事故和危险，由本团在中国平安保险公司为每人买的保险来赔付，
            本人自愿只享受保险待遇，任何其它组织、公司和任何个人无须对自己承担任何法律和任何经济责任。<br>
        </p>
        <p style="margin-left: 75%;font-size: 18px">
            ${time}
        </p>
        <div class="layui-form">
            <table class="layui-table">
                <colgroup>
                    <col width="60">
                    <col width="120">
                    <col width="180">
                    <col>
                </colgroup>
                <thead style="display:table-header-group">
                <tr>
                    <th>序号</th>
                    <th>姓名</th>
                    <th>身份证号码</th>
                    <th>本人签字</th>
                </tr>
                </thead>
                <tbody>
        <#if table??>
            <#list table as t>
            <tr style="height: 50px">
                <td style="font-size: large">
                    ${t.number}
                </td>
                <td style="font-size: x-large">
                    ${t.name}
                </td>
                <td style="font-size: large">
                    ${t.id}
                </td>
                <td></td>
            </tr>
            </#list>
        </#if>
                </tbody>
            </table>
        </div>
    </div>
    </#list>
</#if>

<script>

</script>
</body>
</html>