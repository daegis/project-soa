<#-- @ftlvariable name="rc" type="javax.servlet.http.HttpServletRequest" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>活动列表</title>
    <#include "../common/import.ftl"/>
</head>
<body>
<#include "../common/head.ftl"/>
<table id="dataTable" lay-filter="dataTable"></table>
<#include "../common/util.ftl"/>
<script>
    layui.use(['table', 'jquery'], function () {
        var $ = layui.jquery;
        var table = layui.table;
        var tableIns = table.render({
            elem: '#dataTable',
            cols: [[
                {field: 'id', title: 'ID', width: 50, align: 'center'},
                {field: 'name', title: '活动名称', width: 300, align: 'center'},
                {field: 'date', title: '活动日期', width: 120, align: 'center'},
                {field: 'day', title: '天数', width: 80, align: 'center', templet: '#sexTpl'},
                {field: 'price', title: '价格', width: 120, align: 'center'},
                {field: 'currentCount', title: '当前活动人数', width: 120, align: 'center'},
                {field: 'status', title: '活动状态', width: 90, align: 'center'},
                {width: 260, align: 'center', toolbar: '#bar'}
            ]],
            url: '${rc.contextPath}/activity/findOnPage'
        });
        table.on('tool(dataTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                window.location = "${rc.contextPath}/to/activityDetail/" + data.id;
            } else if (obj.event === 'del') {
                $.ajax({
                    url: '${rc.contextPath}/activity/end',
                    type: 'post',
                    data: {
                        aid: data.id
                    },
                    dataType: 'json',
                    error: function () {
                        alert('服务器连接失败');
                    },
                    success: function (data) {
                        if (data.success) {
                            window.location.reload();
                        } else {
                            alert(data.message);
                        }
                    }
                })
            } else if (obj.event === 'edit') {
                layer.open({
                    type: 2,
                    title: '修改人员信息',
                    area: ['400px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    skin: 'layui-layer-rim',
                    content: ['${rc.contextPath}/to/activityEdit/' + data.id, 'no']
                });
            } else if (obj.event === 'showHistory') {
                alert('showHistory');
            }
        });
        $('#cancelBtn').on('click', function () {
            window.location.reload();
        });
    })
</script>
<script type="text/html" id="bar">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看活动详情</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">调整状态</a>
</script>
</body>
</html>