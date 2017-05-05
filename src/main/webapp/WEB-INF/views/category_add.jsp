<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="UTF-8">
    <title>Lz_CMS-后台管理中心</title>
    <link rel="stylesheet" href="../js/layui/css/layui.css">
    <link rel="stylesheet" href="../css/global.css">
    <script type="text/javascript" src="../js/layui/layui.js"></script>
</head>
<body>
<div class="layui-tab-brief main-tab-container">
    <ul class="layui-tab-title main-tab-title">
      <a href="category_list.html"><li>栏目列表</li></a>
      <a href="category_add.html"><li class="layui-this">添加栏目</li></a>
      <div class="main-tab-item">栏目管理</div>
    </ul>
    <div class="layui-tab-content">
      <div class="layui-tab-item layui-show">
        <form class="layui-form">
          <div class="layui-tab layui-tab-card">
            <ul class="layui-tab-title">
              <li class="layui-this">基本选项</li>
              <li>模板设置</li>
              <li>SEO设置</li>
            </ul>
            <div class="layui-tab-content">
              <div class="layui-tab-item layui-show">
                <div class="layui-form-item">
                  <label class="layui-form-label">选择模型</label>
                  <div class="layui-input-inline input-custom-width">
                    <select name="model_id" lay-verify="required"><option value="">请选择</option><option value="1">单页模型</option><option value="2">文章模型</option><option value="3">图集模型</option><option value="4">链接模型</option><option value="5">下载模型</option></select>
                  </div>
                  <div class="layui-form-mid layui-word-aux"></div>
                </div>
                <div class="layui-form-item">
                  <label class="layui-form-label">上级栏目</label>
                  <div class="layui-input-inline input-custom-width">
                    <select name="parent_id" lay-verify=""><option value="">请选择</option><option value="0" selected="">≡ 作为一级栏目 ≡</option><option value="1">学无止境</option><option value="8">├─杂谈</option><option value="9">├─PHP</option><option value="10">├─建站</option><option value="11">├─WEB前端</option><option value="2">分享无价</option><option value="13">├─源码分享</option><option value="14">├─jQuery特效</option><option value="3">日记</option><option value="4">关于</option><option value="5">├─关于老张</option><option value="6">├─关于LzCMS</option><option value="7">├─留言</option></select>
                  </div>
                  <div class="layui-form-mid layui-word-aux"></div>
                </div>
                <div class="layui-form-item">
                  <label class="layui-form-label">栏目名称</label>
                  <div class="layui-input-inline input-custom-width">
                    <input type="text" name="name" value="" lay-verify="required" autocomplete="off" placeholder="请输入栏目名称" class="layui-input">
                  </div>
                </div>

                <div class="layui-form-item">
                  <label class="layui-form-label">栏目描述</label>
                  <div class="layui-input-inline input-custom-width">
                    <textarea name="description" lay-verify="" autocomplete="off" placeholder="请输入栏目描述" class="layui-textarea"></textarea>
                  </div>
                </div>

                <div class="layui-form-item">
                  <label class="layui-form-label">栏目图片</label>
                  <div class="layui-input-inline input-custom-width">
                    <input type="text" name="image_url" value="" lay-verify="" autocomplete="off" placeholder="" class="layui-input"><div class="layui-box layui-upload-button"><span class="layui-upload-icon"><i class="layui-icon"></i>图片</span></div>
                  </div>
                  <div class="layui-form-mid layui-word-aux"></div>
                </div>
                <div class="layui-form-item">
                  <label class="layui-form-label">排序</label>
                  <div class="layui-input-inline input-custom-width">
                    <input type="text" name="sort" value="20" lay-verify="number" autocomplete="off" placeholder="数字越小越靠前" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                  <label class="layui-form-label">是否导航显示</label>
                  <div class="layui-input-inline input-custom-width">
                    <input type="radio" name="is_menu" value="1" title="是" checked=""><div class="layui-unselect layui-form-radio layui-form-radioed"><i class="layui-anim layui-icon"></i><span>是</span></div>
                    <input type="radio" name="is_menu" value="0" title="否"><div class="layui-unselect layui-form-radio"><i class="layui-anim layui-icon"></i><span>否</span></div></div>
                    <div class="layui-form-mid layui-word-aux">
                  </div>
                </div>
              </div>
              <div class="layui-tab-item">
                <div class="layui-form-item">
                  <label class="layui-form-label">封面模版名称</label>
                  <div class="layui-input-inline input-custom-width">
                    <input type="text" name="name" value="" lay-verify="required" autocomplete="off" placeholder="请输入栏目名称" class="layui-input">
                  </div>
                </div>
                <div class="layui-form-item">
                  <label class="layui-form-label">列表页模版名称</label>
                  <div class="layui-input-inline input-custom-width">
                    <input type="text" name="name" value="" lay-verify="required" autocomplete="off" placeholder="请输入栏目名称" class="layui-input">
                  </div>
                </div>
                <div class="layui-form-item">
                  <label class="layui-form-label">详情页模版名称</label>
                  <div class="layui-input-inline input-custom-width">
                    <input type="text" name="name" value="" lay-verify="required" autocomplete="off" placeholder="请输入栏目名称" class="layui-input">
                  </div>
                </div>
              
              </div>
              <div class="layui-tab-item">
                <div class="layui-form-item">
                  <label class="layui-form-label">栏目名称</label>
                  <div class="layui-input-inline input-custom-width">
                    <input type="text" name="name" value="" lay-verify="required" autocomplete="off" placeholder="请输入栏目名称" class="layui-input">
                  </div>
                </div>

                <div class="layui-form-item">
                  <label class="layui-form-label">栏目描述</label>
                  <div class="layui-input-inline input-custom-width">
                    <textarea name="description" lay-verify="" autocomplete="off" placeholder="请输入栏目描述" class="layui-textarea"></textarea>
                  </div>
                </div>
              </div>
              <div class="layui-form-item">
                <div class="layui-input-block">
                  <button class="layui-btn" lay-submit="" lay-filter="cate_add">立即提交</button>
                </div>
              </div>
            </div>
          </div>
          
        </form>
      </div>
    </div>
</div>
<script type="text/javascript">
layui.use(['element', 'form', 'upload'], function(){
  var element = layui.element()
  ,form = layui.form()
  ,jq = layui.jquery;
  //图片上传
  layui.upload({
    url: ''
    ,elem:'#image'
    ,before: function(input){
      loading = layer.load(2, {
        shade: [0.2,'#000'] //0.2透明度的白色背景
      });
    }
    ,success: function(res){
      layer.close(loading);
      jq('input[name=image_url]').val(res.path);
      layer.msg(res.msg, {icon: 1, time: 1000});
    }
  }); 
  
  //监听提交
  form.on('submit(cate_add)', function(data){
    loading = layer.load(2, {
      shade: [0.2,'#000'] //0.2透明度的白色背景
    });
    var param = data.field;
    jq.post('',param,function(data){
      if(data.code == 200){
        layer.close(loading);
        layer.msg(data.msg, {icon: 1, time: 1000}, function(){
          location.reload();//do something
        });
      }else{
        layer.close(loading);
        layer.msg(data.msg, {icon: 2, anim: 6, time: 1000});
      }
    });
    return false;
  });
  
})
</script>
</body>
</html>
