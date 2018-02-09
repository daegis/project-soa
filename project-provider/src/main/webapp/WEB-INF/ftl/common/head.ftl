<#-- @ftlvariable name="from" type="java.lang.String" -->
<#-- @ftlvariable name="category" type="java.lang.String" -->
<div>
    <ul class="layui-nav">
        <li class="layui-nav-item<#if category??><#else> layui-this</#if>">
            <a href="/to/main.go">首页</a>
        </li>
        <li class="layui-nav-item<#if (category??&&category=='user')> layui-this</#if>">
            <a href="javascript:;">用户</a>
            <dl class="layui-nav-child">
                <dd <#if from??&&from=='userAdd' >class="layui-this"</#if>><a href="/to/userAdd.go">添加</a></dd>
                <dd <#if from??&&from=='userList' >class="layui-this"</#if>><a href="/to/userList.go">列表</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item">
            <a href="javascript:;">事件</a>
            <dl class="layui-nav-child">
                <dd><a href="">添加</a></dd>
                <dd><a href="">管理</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item">
            <a href="javascript:;">报表</a>
            <dl class="layui-nav-child">
                <dd><a href="">选项1</a></dd>
                <dd><a href="">选项2</a></dd>
                <dd><a href="">选项3</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item">
            <a href="javascript:;">
                用户名
            </a>
            <dl class="layui-nav-child">
                <dd><a href="">基本资料</a></dd>
                <dd><a href="">安全设置</a></dd>
            </dl>
        </li>
    </ul>
</div>

<script>
    layui.use('element', function () {
        var element = layui.element;
        element.render();
    });
</script>