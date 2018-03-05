<#-- @ftlvariable name="join" type="cn.aegisa.project.model.JoinInfo" -->
<#-- @ftlvariable name="activities" type="java.util.List" -->
<#-- @ftlvariable name="formatter" type="java.time.format.DateTimeFormatter" -->
<#-- @ftlvariable name="customer" type="cn.aegisa.project.model.CustomerInfo" -->
<#-- @ftlvariable name="activity" type="cn.aegisa.project.model.ActivityInfo" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>参与活动信息</title>
    <#include "../common/import.ftl"/>
</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>为
    <#if customer??&&customer.realName??>${customer.realName}</#if>/
    <#if customer??&&customer.nickname??>${customer.nickname}</#if>
        <#if join??>
        编辑活动信息
        <#else>
        指派活动
        </#if>
    </legend>
</fieldset>
<div style="margin-top: 20px;margin-left: 20px;margin-right: 20px;">
    <form class="layui-form layui-form-pane" action="javascript:;" id="joinForm">
        <#if customer??&&customer.id??><input type="hidden" name="cid" value="${customer.id}"></#if>
        <#if join??><input type="hidden" name="id" value="${join.id}"></#if>
        <div class="layui-form-item">
            <label class="layui-form-label">选择活动</label>
            <div class="layui-input-inline">
                <#if activity??>
                    <input type="text" autocomplete="off"
                           value="${activity.activityName}" disabled
                           placeholder="不填写默认为今天" class="layui-input">
                <#else>
                 <select name="aid">
                    <#if (activities??&&activities?size>0)>
                        <option value="">请选择一个活动</option>
                        <#list activities as activity>
                            <option value="${activity.id}">${activity.activityName}</option>
                        </#list>
                    <#else>
                        <option value="">无有效的活动</option>
                    </#if>
                 </select>
                </#if>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">报名日期</label>
                <div class="layui-input-inline">
                    <input type="text" name="joinDate" id="date" autocomplete="off"
                           placeholder="不填写默认为今天" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">折扣</label>
                <div class="layui-input-inline">
                    <input type="tel" name="discount" placeholder="不填写默认为0" autocomplete="off"
                           <#if join??>
                               value="${join.discount?c}"
                           </#if>
                           class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">预付款</label>
                <div class="layui-input-inline">
                    <input type="tel" name="prepay" placeholder="不填写默认为0" autocomplete="off"
                           <#if join??>
                               value="${join.prepay?c}"
                           </#if>
                           class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">付款方式</label>
            <div class="layui-input-block">
                <select name="payMethod">
                    <option value="">不选择为未付款</option>
                    <optgroup label="常用">
                        <option value="微信">微信</option>
                        <option value="支付宝">支付宝</option>
                    </optgroup>
                    <optgroup label="银行汇款">
                        <option value="建设银行">建设银行</option>
                        <option value="工商银行">工商银行</option>
                        <option value="农业银行">农业银行</option>
                        <option value="招商银行">招商银行</option>
                        <option value="其他银行">其他银行</option>
                    </optgroup>
                    <optgroup label="其他">
                        <option value="现金">现金</option>
                        <#if join??>
                               <option selected value="${join.payMethod}">${join.payMethod}</option>
                        </#if>
                    </optgroup>
                </select>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">备注信息</label>
            <div class="layui-input-block">
                <textarea placeholder="请输入活动备注信息" class="layui-textarea"
                          name="joinComment"><#if join??&&join.joinComment??>${join.joinComment}</#if></textarea>
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
        form.render();
        laydate.render({
            elem: '#date'
            <#if join??&&join.joinDate??>
                , value: '${formatter.format(join.joinDate)}'
            </#if>
        });
        //监听提交
        form.on('submit(submitBtn)', function (data) {
            $.ajax({
                url: '${rc.contextPath}/join/add',
                type: 'post',
                data: data.field,
                dataType: 'json',
                error: function () {
                    alert('服务器连接失败');
                },
                success: function (data) {
                    if (data.success) {
                        layer.confirm('提交成功', {
                            btn: ['离开'] //按钮
                        }, function () {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            <#if join??>
                               parent.window.location.reload();
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