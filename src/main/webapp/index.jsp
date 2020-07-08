<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<!-- 获取项目名 -->
<%
	pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
</head>
<!--
	 引入样式	
	引入 jQuery
	web路径 ： 不以/ 开始的相对路径,找资源,以当前资源为基准，经常容易出问题
			以/开始,找资源，以服务器为标准， 需要加上项目名
 -->
<script type="text/javascript" src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
<link href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<body>

	<!-- 员工添加的模态框 -->
	<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">员工添加</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
			  <div class="form-group">
			    <label class="col-sm-2 control-label">empName</label>
			    <div class="col-sm-10">
			      <input name="empName" type="text" class="form-control" id="empName_add_input" placeholder="empName" aria-describedby="empNameStatus">
			       <span id="empNameBlock" class="help-block"></span>
			        <span id="empNameStatus" class="sr-only"></span>
			    </div>
			  </div>
			  <div class="form-group">
			    <label class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-10">
			      <input name="email" type="text" class="form-control" id="email_add_input" placeholder="email@cloud.com">
			       <span id="helpBlock2" class="help-block"></span>
			    </div>
			  </div>
			   <div class="form-group">
				    <label class="col-sm-2 control-label">gender</label>
				    <div class="col-sm-10">
				      <label class="radio-inline">
						  <input type="radio" name="gender" id="gender1_add_input" value="1" checked="checked"> 男
					  </label>
					  <label class="radio-inline">
						  <input type="radio" name="gender" id="gender2_add_input" value="0"> 女
					  </label>
				    </div>
			  </div>
			  <div class="form-group">
				    <label class="col-sm-2 control-label">deptName</label>
				    <div class="col-sm-4">
				      	<select class="form-control" name="dId" id="dept_add_select">
						 	<!-- 部门提交部门ID -->
						</select>
				    </div>
			  </div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" id="emp_save">保存</button>
      </div>
    </div>
  </div>
</div>

	 <!-- 员工修改 -->
	 <!-- 员工添加的模态框 -->
	<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">员工修改</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal">
			  <div class="form-group">
			    <label class="col-sm-2 control-label">empName</label>
			    <div class="col-sm-10">
			     <p class="form-control-static" id="empName"></p>
			    </div>
			  </div>
			  <div class="form-group">
			    <label class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-10">
			      <input name="email" type="text" class="form-control" id="email_update_input" placeholder="email@cloud.com">
			       <span id="helpBlock2" class="help-block"></span>
			    </div>
			  </div>
			   <div class="form-group">
				    <label class="col-sm-2 control-label">gender</label>
				    <div class="col-sm-10">
				      <label class="radio-inline">
						  <input type="radio" name="gender" value="1" checked="checked"> 男
					  </label>
					  <label class="radio-inline">
						  <input type="radio" name="gender" value="0"> 女
					  </label>
				    </div>
			  </div>
			  <div class="form-group">
				    <label class="col-sm-2 control-label">deptName</label>
				    <div class="col-sm-4">
				      	<select class="form-control" name="dId" id="dept_update_select">
						 	<!-- 部门提交部门ID -->
						</select>
				    </div>
			  </div>
		</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" id="emp_update">更新</button>
      </div>
    </div>
  </div>
</div>
	 

	<div class="container">
		<!-- 标题行 -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			 
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary" id="emp_add_Modal_btn">新增</button>
				<button class="btn btn-danger" id="emp_delete_all_btn">删除</button>
			</div>
		</div>
		<!-- 显示表格、数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th>
								<input type="checkbox" id="check_all"/>
							</th>
							<th>#</th>
							<th>empName</th>
							<th>gender</th>
							<th>email</th>
							<th>deptName</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div>
		</div>
		<!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6" id="page_info_area">
			
			</div>
			<!-- 分页条信息 -->
			<div class="col-md-6" id="page_nav_area">
				
			</div>
			<script type="text/javascript">
			
				//定义总记录数
				var totalRecord = null,currentPage=null;
				//页面加载完成以后直接发送 ajax请求，要到分页数据
				$(function(){
					//一进入,就去首页
					to_page(1);
					
				})
				
				function to_page(pn){
					$.ajax({
						url: "${APP_PATH}/emps",
						data:"pn="+pn,
						success: function(result){
					  		//console.log(result);
					  		//1、解析并显示员工数据
					  		build_emps_table(result);
					  		//2、解析并显示分页信息
					  		build_page_info(result);
					  		//3、解析并显示分页条
					  		build_page_nav(result);
					  	}

					})					
				}
				function build_emps_table(result){
					totalRecord = result.extend.pageInfo.total;
					currentPage = result.extend.pageInfo.pageNum;
					$("#emps_table tbody").empty();
					var emps = result.extend.pageInfo.list;
					$.each(emps,function(index,item){
						var checkboxTd = $("<td><input type='checkbox' class='check_item' /></td>");
						var empIdTd = $("<td></td>").append(item.empId);
						var empNameTd = $("<td></td>").append(item.empName);
						var genderTd = $("<td></td>").append(item.gender == '1'?'男':'女');
						var emailTd = $("<td></td>").append(item.email);
						var deptNameTd = $("<td></td>").append(item.department.deptName);
						/*
						<button class="btn btn-primary btn-sm" >
							<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑
						</button>
						*/
						var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
											.append($("<span></span>").addClass("glyphicon glyphicon-pencil"))
													.append("编辑");
						//为编辑按钮添加一个自定义的属性,来表示当前id
						editBtn.attr("edit_id",item.empId);
						var delBtn =$("<button></button>").addClass("btn btn-danger  btn-sm delete_btn")
										.append($("<span></span>").addClass("glyphicon glyphicon-trash"))
												.append("删除");
						//为删除按钮添加一个自定义属性,来表示id
						delBtn.attr("delete_id",item.empId);
						//将两个按钮追加到一个单元格中
						var btnTr = $("<td></td>").append(editBtn).append(" ").append(delBtn);
						
						//append方法执行完成后还是返回原来的元素
					 	$("<tr></tr>").append(checkboxTd).append(empIdTd).append(empNameTd)
					 		.append(genderTd).append(emailTd)
					 			.append(deptNameTd).append(btnTr).appendTo("#emps_table tbody");
					})
				}
				
				//解析显示分页信息
				function build_page_info(result){
					$("#page_info_area").empty();
					$("#page_info_area").append("当前"+result.extend.pageInfo.pageNum+"页,总共"+result.extend.pageInfo.pages+"页,总"+
							result.extend.pageInfo.total+"记录数");
				}
				
				//解析显示分页条,点击分页要实现动作
				function build_page_nav(result){
					$("#page_nav_area").empty();
					var ul = $("<ul></ul>").addClass("pagination");
					var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
					var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;").attr("href","#"));
					if(result.extend.pageInfo.hasPreviousPage == false){
						firstPageLi.addClass("disabled");
						prePageLi.addClass("disabled");
					}
					//为元素添加点击翻页的事件
					firstPageLi.click(function(){
						to_page(1);
					});
					prePageLi.click(function(){
						to_page(result.extend.pageInfo.pageNum - 1);
					})
					
					var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;").attr("href","#"));
					var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
					if(result.extend.pageInfo.hasNextPage == false){
						nextPageLi.addClass("disabled");
						lastPageLi.addClass("disabled");
					}
					lastPageLi.click(function(){
						to_page(result.extend.pageInfo.pages);
					});
					nextPageLi.click(function(){
						to_page(result.extend.pageInfo.pageNum + 1);
					})
					
					//添加首页和前一页 的提示
					ul.append(firstPageLi).append(prePageLi);
					//1.2.3
					$.each(result.extend.pageInfo.navigatepageNums,function(index,item){
						var numLi =  $("<li></li>").append($("<a></a>").append(""+item+"").attr("href","#"));
						if(item == result.extend.pageInfo.pageNum){
							numLi.addClass("active");
						}
						numLi.click(function(){
							to_page(item);
						})
						ul.append(numLi);
					})
					//遍历完之后添加下一页和末页
					ul.append(nextPageLi).append(lastPageLi);
					var navEle = $("<nav></nav>").append(ul);
					navEle.appendTo($("#page_nav_area"));
				}
				
				//清空表单样式及内容
				function reset_form(ele){
					$(ele)[0].reset();
					//清空表单样式
					$(ele).find("*").removeClass("has-error has-success");
					$(ele).find(".help-block").text("");
				}
				
				//显示模态框
				$("#emp_add_Modal_btn").click(function(){
					//清除表单数据(表单重置)因为jQuery对象没有reset方法,所以要使用dom对象的reset方法
					reset_form("#empAddModal form");
					//发送ajax请求,查出部门信息,显示在下拉列表中
					getDepts("#empAddModal select");
					
					//显示模态框
					$("#empAddModal").modal({
						backdrop:"static"
					});
					
				})
				
				//查出所有的部门信息显示在下拉列表中
				function getDepts(ele){
					$(ele).empty();
					$.ajax({
						url:"${APP_PATH}/depts",
						success: function(data){
							//console.log(data);
							//显示部门信息的下拉框
							$.each(data.extend.depts,function(index,item){
								var optionEle = $("<option></option>").append(this.deptName)
													.attr("value",this.deptId);
								optionEle.appendTo(ele);
							})
						}
					})
				}
				
				//查出所有的员工信息
				function getEmp(id){
					$.ajax({
						url:"${APP_PATH}/emp/"+id,
						type:"GET",
						success:function(result){
							//alert(result);
							var empData = result.extend.emp;
							$("#empName").text(empData.empName);
							$("#email_update_input").val(empData.email);
							$("#empUpdateModal input[name='gender']").val([empData.gender]);
							$("#empUpdateModal select").val(empData.dId);
						}
					})
				}
				
				function validate_add_form(){
					//1、拿到要校验的数据，使用正则表达式
					var empName = $("#empName_add_input").val();
					//要求是6-16位
					var regName = /(^[a-zA-Z0-9_-]{4,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
					if(!regName.test(empName)){
						//每次显示信息之前都应该清除之前的元素
						//alert("用户名可以是2-5w位中文或者6-16位英文字符和数字的组合");
						show_validate_msg("#empName_add_input","error","用户名可以是2-5位中文或者4-16位英文字符和数字的组合");
						return false;
					}else{
						
						show_validate_msg("#empName_add_input","success","");
					}
					//2、校验邮箱信息
					var email = $("#email_add_input").val();
					var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
					if(!regEmail.test(email)){
						//alert("邮箱格式错误");
						show_validate_msg("#email_add_input","error","邮箱格式错误");
						$("#email_add_input").attr("email_va","error");
						return false;
					}else{
						show_validate_msg("#email_add_input","success","");
					}
					return true;
				}
				
				//ele 表示校验的元素,status表示状态,msg 表示信息
				function show_validate_msg(ele,status,msg){
					//清除当前元素的状态 has-success has-error 中间一定要有空格
					$(ele).parent().removeClass("has-success has-error");
					if("success"==status){
						$(ele).parent().addClass("has-success");
						$(ele).next("span").text(msg);
					}else if("error"==status){
						$(ele).parent().addClass("has-error");
						$(ele).next("span").text(msg);
					}
				}
				
				//点击保存,保存员工
				$("#emp_save").click(function(){
					//1、将模态框中填写的表单数据提交给服务器,进行保存
					//1、对提交的数据进行校验
					if(!validate_add_form()){
						return false;
					}
					//在发送ajax请求之前,先判断用户名是否校验成功,如果成功就继续
					if($(this).attr("ajax-va") == "error"){
						return false;
					}
					//2、发送ajax 请求保存员工
					//alert();
					$.ajax({
						url:"${APP_PATH}/emp",
						type:"POST",
						data:$("#empAddModal form").serialize(),
						success:function(result){
							//alert(result.name);
							//员工状态保存成功
							if(result.code ==100){
								//1、关闭模态框
								$("#empAddModal").modal('hide');
								//2、显示到最后一页,查看刚才添加的数据
								to_page(totalRecord);
							}else{
								//显示失败信息
								//console.log(result);
								//有哪个字段的错误信息就显示哪个字段的错误信息
								if(undefined != result.extend.errorFields.email){
									//显示邮箱的错误信息
									show_validate_msg("#email_add_input","error",result.extend.errorFields.email);
								}
								if(undefined != result.extend.errorFields.empName){
									//显示员工名字的错误信息
									show_validate_msg("#empName_add_input","error",result.extend.errorFields.empName);
								}
							}
						}
					})
				})
				$("#empName_add_input").change(function(){
					//发送ajax请求校验用户名是否可用
					var empName = this.value;
					$.ajax({
						url:"${APP_PATH}/checkUser",
						data:"empName="+empName,
						type:"POST",
						success:function(result){
							if(result.code==100){
								show_validate_msg("#empName_add_input","success","用户名可用");
								$("#emp_save").attr("ajax-va","success");
							}else{
								show_validate_msg("#empName_add_input","error",result.extend.va_msg);
								$("#emp_save").attr("ajax-va","error");
							}
						}
					})
				})
				
			//我们在按钮创建之前就绑定了click,所以帮不上
			//1、可以在创建的时候绑定上,但是耦合性太高 2、点击绑定 live()
				//jQuery 在新版本已经删除live 用on 替代
			$(document).on("click",".edit_btn",function(){
				//alert("edit");
				
				//1、查出部门信息,并显示部门列表
				getDepts("#empUpdateModal select");
				//0、查出员工信息，显示员工信息
				var empId = $(this).attr("edit_id");
				getEmp(empId);
				
				//把员工的id传递给模态框的更新按钮
				$("#emp_update").attr("edit_Id",$(this).attr("edit_id"));
				//弹出模态框
				$("#empUpdateModal").modal({
					backdrop:"static"
				});
			})
					
			//点击更新，更新员工信息
			$("#emp_update").click(function(){
				//验证邮箱是否合法
				var email = $("#email_update_input").val();
				var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
				if(!regEmail.test(email)){
					//alert("邮箱格式错误");
					show_validate_msg("#email_update_input","error","邮箱格式错误");
					$("#email_update_input").attr("email_va","error");
					return false;
				}
				
				
				//2、发送ajax 请求保存更新的员工信息
				$.ajax({
					url:"${APP_PATH}/emp/"+$(this).attr("edit_id"),
					//ajax可以之间发送PUT请求,不用加上_method="PUT"
					type:"PUT",
					data:$("#empUpdateModal form").serialize(),
					success:function(result){
						/* if(undefined != result.extend.errorFields.email){
							//显示邮箱的错误信息
							show_validate_msg("#email_add_input","error",result.extend.errorFields.email);
						}else{
							show_validate_msg("#email_update_input","success","");
						} */
						//alert(result.name);
						//1、关闭对话框，回到本页面
						$("#empUpdateModal").modal("hide");
						//2、回到本页面
						to_page(currentPage);
					}
					
				})
			})
			
			//单个删除按钮
			$(document).on("click",".delete_btn",function(){
				//1、弹出是否确认对话框
				var empName = $(this).parents("tr").find("td:eq(2)").text();
				var empId = $(this).attr("delete_id");
				if(confirm("确认删除【"+empName+"】吗？")){
					//确认，发送ajax请求
					$.ajax({
						url:"${APP_PATH}/emp/"+empId,
						type:"DELETE",
						success:function(result){
							//alert(result.name);
							//处理成功之后，回到本页
							to_page(currentPage);
						}
					})
				}
					
			})
			
			//全选/全不选功能
			$("#check_all").click(function(){
				//attr获取 checked 是 undefined
				//我们这些dom原生的属性：推荐使用prop获取此类值，attr获取自定义属性的值
				//prop 修改和读取dom原生的值
				$(".check_item").prop("checked",$(this).prop("checked"));
			})
			
			//check_item
			$(document).on("click",".check_item",function(){
				//判断当前选择中的个数是不是选满了
				var flag = $(".check_item:checked").length == $(".check_item").length
				$("#check_all").prop("checked",flag);
			})
			
			
			//点击全部删除，批量删除
			$("#emp_delete_all_btn").click(function(){
				var empNames="";
				var del_idstr="";
				$.each($(".check_item:checked"),function(){
					empNames += ","+ $(this).parents("tr").find("td:eq(2)").text();
					del_idstr += "-"+ $(this).parents("tr").find("td:eq(1)").text();
				})
				//去除empNames多余的逗号
				empNames = empNames.substring(1,empNames.length);
				del_idstr = del_idstr.substring(1,del_idstr.length);
				if(confirm("确认删除【"+empNames+"】吗？")){
					//发送ajax请求删除
					$.ajax({
						url:"${APP_PATH}/emp/"+del_idstr,
						type:"DELETE",
						success:function(result){
							alert(result.name);
							to_page(currentPage);
						}
					})
				}
			})
			</script>
		</div>
	</div>
</body>
</html>