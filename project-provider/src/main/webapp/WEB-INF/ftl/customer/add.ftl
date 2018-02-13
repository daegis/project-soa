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
<div style="margin-top: 20px;margin-left: 20px;margin-right: 20px;">
    <form class="layui-form layui-form-pane" action="javascript:;" id="customerForm">
        <#if (customer??&&customer.id??)><input name="id" value="${customer.id}" type="hidden"></#if>
        <div class="layui-form-item">
            <label class="layui-form-label">网名</label>
            <div class="layui-input-inline">
                <input type="text" name="nickname" autocomplete="off" placeholder="请输入网名"
                       <#if (customer??&&customer.nickname??)>value="${customer.nickname}"</#if>
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">真实姓名</label>
            <div class="layui-input-inline">
                <input type="text" name="realName" placeholder="请输入真实姓名" autocomplete="off"
                       <#if (customer??&&customer.realName??)>value="${customer.realName}"</#if>
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">手机号</label>
            <div class="layui-input-inline">
                <input type="text" name="telephone" placeholder="请输入手机号" autocomplete="off"
                       <#if (customer??&&customer.telephone??)>value="${customer.telephone}"</#if>
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">地址</label>
            <div class="layui-input-inline">
                <input type="text" name="address" placeholder="请输入地址" autocomplete="off"
                       <#if (customer??&&customer.address??)>value="${customer.address}"</#if>
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">身份证号</label>
            <div class="layui-input-inline">
                <input type="text" name="idNumber" placeholder="请输入身份证号" autocomplete="off"
                       <#if (customer??&&customer.idNumber??)>value="${customer.idNumber}"</#if>
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注信息</label>
            <div class="layui-input-inline">
                <input type="text" name="comment" placeholder="请输入备注信息" autocomplete="off"
                       <#if (customer??&&customer.comment??)>value="${customer.comment}"</#if>
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn" lay-submit="" lay-filter="submitBtn">确定</button>
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

        //监听提交
        form.on('submit(submitBtn)', function (data) {
            $.ajax({
                url: '${rc.contextPath}/customer/add',
                type: 'post',
                data: data.field,
                dataType: 'json',
                error: function () {
                    alert('服务器连接失败');
                },
                success: function (data) {
                    if (data.success) {
                        layer.confirm('添加成功!', {
                            btn: ['离开'] //按钮
                        }, function () {
                            window.location.href = '${rc.contextPath}/to/userList';
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