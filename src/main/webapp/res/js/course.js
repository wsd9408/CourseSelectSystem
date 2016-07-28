$(document).ready(function() {
	//bootstrap模态框事件
	$('#myModal').on('hide.bs.modal', function() {
		loadUserDatas("tbody");
		loadUserDatas("tbody_stu");
	});
	$("#yes").click(function() {
		var url = "index.html";
		window.location = url;
	});
	//用户增加或者修改
	$('#btnSave').click(function() {
		UserAddOrUpdate('addUser');
	});

	$('#btnModify').click(function() {
		UserAddOrUpdate('ModifyUser');
	});

	$('#btnSaveClass').click(function(){
		classAddOrUpdata('AddCourse');
	});
	$('#btnUpdateClass').click(function(){
		classAddOrUpdata('ModifyCourse');
	});

	$("#sc_close").click(function(){
		var param = isCheckedData('sc_msg_table');
		if(param){
			var tdElement = param.getElementsByTagName("td");
			changeScmState(false, tdElement[1]);
		}

	});
	$("#sc_open").click(function(){
		var param = isCheckedData('sc_msg_table');
		if(param){
			var tdElement = param.getElementsByTagName("td");
			changeScmState(true, tdElement[1]);
		}

	});
	$("#sc_add").click(function(){
		var score = 10;
		$.ajax({
			url:"AddCourseUser",
			type : "POST",
			data:{
				score:score
			},
			dataType:"json",
			success : function(data) {
				alert(data.msg);
				loadScmDatas('sc_msg_table');
			},
		});

	});
	function changeScmState(state,obj){
		$.ajax({
			url:"ModifyScmState",
			type : "POST",
			data:{
				year:obj.textContent,
				state:state,
			},
			dataType:"json",
			success : function(data) {
				alert(data.msg);
				loadScmDatas('sc_msg_table');
			},
		});
	}
});

/**
 * TODO 添加或者更改Class
 * @param url
 * @returns {Boolean}
 */
function classAddOrUpdata(url){
	var data = {
			id:$("#c_id").val(),
			classId:$("#m_classId").val(),
			className : $("#m_className").val(),
			type : $("#m_classType").val(),
			score : $("#m_score").val(),
			MaxStuNum : $("#m_limitStuNum").val(),
			teacher : $("#courseTeacher").val(),
	};
	$.ajax({
		url : url,
		type : "POST",
		dataType : 'json',
		data : data,
		success : function(data) {
			data = eval(data);
			alert(data.msg);
			loadClassDatas('class_msg_table');
		},
		error : function(data) {
			$.messager.alert("提示", "操作失败", "info");
		}
	});
	return false;
}
/**
 * TODO 添加或者修改User
 * @param url
 * @returns {Boolean}
 */
function UserAddOrUpdate(url){
	var data = {
			id : $("#m_id").val(),
			code : $("#m_code").val(),
			name : $("#m_name").val(),
			sex : $("#m_sex").val(),
			password : $("#m_passWord").val(),
			age : $("#m_age").val(),
			type : $("#m_type").val(),
			info : $("#m_info").val()
	};
	$.ajax({
		url : url,
		type : "POST",
		dataType : 'json',
		data : data,
		success : function(data) {
			data = eval(data);
			alert(data.msg);
			loadUserDatas(id);
		},
		error : function(data) {
			$.messager.alert("提示", "操作失败", "info");
		}
	});
	return false;
}





