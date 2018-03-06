<#-- @ftlvariable name="count" type="java.lang.Number" -->
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
            <td>${count}</td>
            <td>${activity.price}</td>
        </tr>
        </tbody>
    </table>
</div>
<fieldset class="layui-elem-field layui-field-title">
    <legend>参加当前活动的人员列表</legend>
</fieldset>
<table id="dataTable" lay-filter="dataTable"></table>


<#include "../common/util.ftl"/>
<script>
    layui.use(['element', 'table', 'jquery'], function () {
        var element = layui.element;
        var $ = layui.jquery;
        element.init();
        var table = layui.table;
        table.render({
            elem: '#dataTable',
            cols: [[
                {field: 'nickname', title: '昵称', width: 120, align: 'center'}
                , {field: 'realName', title: '姓名', width: 90, align: 'center'}
                , {field: 'joinDate', title: '报名日期', sort: true, width: 120, align: 'center'}
                , {field: 'gender', title: '性别', sort: true, width: 75, align: 'center', templet: '#genderTpl'}
                , {field: 'age', title: '年龄', sort: true, width: 75, align: 'center', templet: '#ageTpl'}
                , {field: 'discount', title: '折扣', width: 90, align: 'center'}
                , {field: 'prepay', title: '预付', width: 90, align: 'center'}
                , {field: 'payMethod', title: '方式', sort: true, width: 120, align: 'center', templet: '#methodTpl'}
                , {field: 'restPay', title: '余款', sort: true, width: 120, align: 'center'}
                , {
                    field: 'busSeat',
                    title: '汽车座位',
                    sort: true,
                    width: 100,
                    align: 'center',
                    style: 'cursor: pointer;',
                    event: 'setSeat'
                }
                , {field: 'joinComment', title: '备注信息', width: 120, align: 'center'}
                , {title: '操作', width: 250, align: 'center', toolbar: '#barDemo'}
            ]],
            url: '${rc.contextPath}/join/customers/${activity.id}'
        });

        table.on('tool(dataTable)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值
            var tr = obj.tr; //获得当前行 tr 的DOM对象


            if (obj.event === 'setSeat') {
                layer.prompt({
                    formType: 0
                    , title: '设置名字为 [' + data.realName + '](' + data.nickname + ') 的用户的座位号'
                    , value: ''
                }, function (value, index) {
                    $.ajax({
                        url: '${rc.contextPath}/join/setBusSeat',
                        type: 'post',
                        data: {id: data.id, seat: value},
                        dataType: 'json',
                        success: function (data) {
                            if (data.success) {
                                layer.close(index);
                                window.location.reload();
                            } else {
                                alert(data.message);
                            }
                        }
                    });

                });
            }

            if (layEvent === 'detail') { //查看
                layer.open({
                    type: 2,
                    title: '编辑活动信息',
                    area: ['400px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    skin: 'layui-layer-rim',
                    content: ['${rc.contextPath}/to/join/joinEdit/' + data.id, 'no']
                });
            } else if (layEvent === 'del') { //删除
                layer.confirm('真的要从当前活动中移除人员【' + data.realName + '(' + data.nickname + ')】吗?请注意, 从活动中移除一位成员并不会从人员列表中移除这个人, 稍后可以在其他活动中重新添加这个人员.', function (index) {
                    layer.close(index);
                    //向服务端发送删除指令
                    $.ajax({
                        url: '${rc.contextPath}/join/deleteFromActivity',
                        type: 'post',
                        data: {'id': data.id},
                        dataType: 'json',
                        success: function (data) {
                            if (data.success) {
                                obj.del(); //删除对应行（tr）的DOM结构
                            }
                            layer.msg('移除成功');
                        }
                    })
                });
            } else if (layEvent === 'edit') { //编辑
                layer.open({
                    type: 2,
                    title: '修改人员信息',
                    area: ['400px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    skin: 'layui-layer-rim',
                    content: ['${rc.contextPath}/to/userEdit/' + data.cid, 'no']
                });
            }
        });
    });

</script>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="detail">活动信息</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">客户信息</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">从活动中移除</a>
</script>
<script type="text/html" id="genderTpl">
    {{#  if(d.gender === '- -'){ }}
    <span style="color: red">{{ d.gender }}</span>
    {{#  } else if(d.gender === '女'){ }}
    <span style="color: deeppink">{{ d.gender }}</span>
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