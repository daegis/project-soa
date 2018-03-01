<#-- @ftlvariable name="rc" type="javax.servlet.http.HttpServletRequest" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户列表</title>
    <#include "../common/import.ftl"/>
</head>
<body>
<#include "../common/head.ftl"/>
<div class="demoTable" style="margin-top: 10px;margin-left: 10px">
    搜索:
    <div class="layui-inline">
        <input class="layui-input" name="keyword" id="reloadInput" autocomplete="off">
    </div>
    <button class="layui-btn" data-type="reload" id="reloadBtn">搜索</button>
    <button class="layui-btn layui-btn-danger" id="cancelBtn">重置搜索</button>
</div>
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
                {field: 'nickname', title: '网名', width: 100, align: 'center'},
                {field: 'name', title: '真实姓名', width: 90, align: 'center'},
                {field: 'telephone', title: '手机号', width: 120, align: 'center'},
                {field: 'gender', title: '性别', width: 60, align: 'center', templet: '#sexTpl'},
                {field: 'age', title: '年龄', width: 60, align: 'center', templet: '#ageTpl'},
                {field: 'address', title: '地址', width: 120, align: 'center'},
                {field: 'idNumber', title: '身份证号', width: 185, align: 'center'},
                {field: 'lastModified', title: '最后编辑时间', sort: true, width: 180, align: 'center'},
                {field: 'comment', title: '备注信息', width: '180', align: 'center'},
                {title: '操作', width: 260, align: 'center', toolbar: '#bar'}
            ]],
            page: true,
            url: '${rc.contextPath}/customer/findOnPage'
        });
        table.on('tool(dataTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'detail') {
                layer.open({
                    type: 2,
                    title: '指派活动',
                    area: ['400px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    skin: 'layui-layer-rim',
                    content: ['${rc.contextPath}/to/join/customer/' + data.id, 'no']
                });
            } else if (obj.event === 'del') {
                layer.alert("为保证数据安全, 页面不提供删除功能. 如需删除具体人员或活动, 请将人员或者活动的id告知管理员", {
                    icon: 0,
                    offset: '100px'
                });
            } else if (obj.event === 'edit') {
                layer.open({
                    type: 2,
                    title: '修改人员信息',
                    area: ['400px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    skin: 'layui-layer-rim',
                    content: ['${rc.contextPath}/to/userEdit/' + data.id, 'no']
                });
            } else if (obj.event === 'showHistory') {
                alert('showHistory');
            }
        });
        $('#reloadBtn').on('click', function () {
            var $keyword = $('#reloadInput').val();
            tableIns.reload({
                where: {
                    keyword: $keyword
                }
            });
        });
        $('#cancelBtn').on('click', function () {
            window.location.reload();
        });
    })
</script>
<script type="text/html" id="bar">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">指派活动</a>
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="showHistory">历史活动</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script type="text/html" id="sexTpl">
    {{#  if(d.gender === '女'){ }}
    <span style="color: deeppink;">{{ d.gender }}</span>
    {{#  } else { }}
    {{ d.gender }}
    {{#  } }}
</script>
<script type="text/html" id="ageTpl">
    {{#  if(d.age === '- -'){ }}
    <span style="color: red">{{ d.age }}</span>
    {{#  } else if(d.age >= 70){ }}
    <span style="color: green">{{ d.age }}</span>
    {{#  } else if(d.age >= 65){ }}
    <span style="color: crimson">{{ d.age }}</span>
    {{#  } else if(d.age >= 60){ }}
    <span style="color: blue">{{ d.age }}</span>
    {{#  } else { }}
    {{ d.age }}
    {{#  } }}
</script>
</body>
</html>