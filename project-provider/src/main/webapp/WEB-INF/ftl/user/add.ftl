<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户添加</title>
    <#include "../common/import.ftl"/>
</head>
<body>
<#include "../common/head.ftl"/>
<div style="margin-top: 20px;margin-left: 20px;margin-right: 20px;">
    <form class="layui-form layui-form-pane" action="javascript:;" id="customerForm">
        <div class="layui-form-item">
            <label class="layui-form-label">网名</label>
            <div class="layui-input-inline">
                <input type="text" name="nickname" autocomplete="off" placeholder="请输入网名"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">真实姓名</label>
            <div class="layui-input-inline">
                <input type="text" name="realName" placeholder="请输入真实姓名" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">手机号</label>
            <div class="layui-input-inline">
                <input type="text" name="telephone" placeholder="请输入手机号" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">地址</label>
            <div class="layui-input-inline">
                <input type="text" name="address" placeholder="请输入地址" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">身份证号</label>
            <div class="layui-input-inline">
                <input type="text" name="idNumber" placeholder="请输入身份证号" autocomplete="off"
                       class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">备注信息</label>
            <div class="layui-input-inline">
                <input type="text" name="comment" placeholder="请输入备注信息" autocomplete="off"
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

        //监听提交
        form.on('submit(submitBtn)', function (data) {
            $.ajax({
                url: '${rc.contextPath}/customer/add.go',
                type: 'post',
                data: data.field,
                dataType: 'json',
                error: function () {
                    alert('服务器连接失败');
                },
                success: function (data) {
                    if (data.success) {
                        layer.confirm('添加成功!还要继续添加吗？', {
                            btn: ['继续', '离开'] //按钮
                        }, function () {
                            layer.closeAll();
                            $('#customerForm')[0].reset();
                        }, function () {
                            window.location.href = '${rc.contextPath}/';
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