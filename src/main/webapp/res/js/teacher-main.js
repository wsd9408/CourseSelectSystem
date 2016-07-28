/**
 * 
 */
//存放所有用户
var users = users || {};
//操作类型
var operateType = "";
//存放搜索对象
var searchUsers = searchUsers || {};
//用户构造方法
var User = {
		Create:function(id,code,name,sex,passWord,age,rank,classFrom,type){
			this.id = id;
			this.code = code;
			this.name = name;
			this.sex = sex;
			this.passWord = passWord;
			this.age = age;
			this.rank = rank;
			this.classFrom = classFrom;
			this.type = type;
		},
		//添加用户
		addUserData:function(){
			if(this.code != ""){
				users[this.code] = this;                
			}
		},
		//删除用户
		deleteUserData:function (){
			var data = {code:this.code}
			$.ajax({
				url : 'delUser',
				type : "POST" ,
				dataType:'json',                	       
				data : data,
				success : function(data){
					data=eval(data);
					alert(data.msg);
					loadUserDatas();
				}
			}); 
		},
		//编辑用户
		editUserData:function(){
			for(var i in users){
				if(this.code == users[i].code){
					users[i].id = this.id;
					users[i].name = this.name;
					users[i].sex = this.sex;
					users[i].passWord = this.passWord;
					users[i].rank = this.rank;
					users[i].age = this.age;
					users[i].classFrom = this.classFrom;
				}

			}
		},
		//查找用户
		findUserData:function(data){
			for(var i in users){
				if(data.code.indexOf(users[i].code) >= 0 || 
						data.name.indexOf(users[i].name) >= 0){
					searchUsers[users[i].code] = users[i];
					refreshDatas(searchUsers);
				}
			}
		}
};
//课程构造方法
var Course = {
		Create:function(classId,className,classType,score,teacherName,MaxStuNum,selectedStuNum,selectStuNum,id,year){
			this.classId = classId;
			this.className = className;
			this.classType = classType;
			this.score = score;
			this.teacherName = teacherName;
			this.MaxStuNum = MaxStuNum;
			this.selectedStuNum = selectedStuNum;
			this.selectStuNum=selectStuNum;
			this.id = id;
			this.year = year;
		},
		//添加课程
		addClassData:function(){
			if(this.classId != ""){
				users[this.classId] = this;                
			}
		},
		//删除课程
		deleteClassData:function (){
			var data = {id:this.id};
			$.ajax({
				url : 'DelCourse',
				type : "POST" ,
				dataType:'json',                	       
				data : data,
				success : function(data){
					data=eval(data);
					alert(data.msg);
					loadClassDatas('class_msg_table');
				}
			}); 
		},
		//编辑课程
		editClassrData:function(){
			for(var i in classes){
				if(this.classId == users[i].classId){
					classes[i].className = this.className;
					classes[i].classType = this.classType;
					classes[i].score = this.score;
					classes[i].teacherName = this.teacherName;
					classes[i].MaxStuNum = this.MaxStuNum;
					classes[i].selectedStuNum = this.selectedStuNum;
					classes[i].selectStuNum = this.selectStuNum;
				}
			}
		},
};
var SelectCourseUser = {
	Create:function(year,score,startTime,closeTime,state){
		this.year = year;
		this.score = score;
		this.startTime = startTime;
		this.closeTime = closeTime;
		this.state = state;
	}	
		
};





function New(aClass,aParams){
	function new_(){
		aClass.Create.apply(this,aParams);
	}
	new_.prototype = aClass;
	return new new_();
}



/**
 * TODO 读取用户信息
 * @returns {___anonymous3304_3305}
 */
function loadUserDatas(){   
	var users = {};
	$.post('loadUsers',
			null,//如果你需要传参数的话，可以写在这里，格式为：{参数名：值,参数名：值...}                  
			function(data){//执行成功之后的回调函数           
		//alert(data); 
		var models = eval('('+data+')');// 对Servlet回传的字符串转换为json对象数组
		var us = models[0];
		for(k in us){
			var u = us[k];
			var initUser = New(User,[u.id,u.code,u.name,u.sex,u.password,u.age,u.rank,u.classFrom,u.type=="Teacher"?"教师":"学生"]);
			users[k] = initUser;
		}
		addRowData(users);
		refreshDatas(users);              
	}
	);
	return users;
}

/**
 * TODO 读取课程记录
 * @param id
 * @param year
 * @returns {___anonymous3904_3905}
 */
function loadClassDatas(id,year){   
	var classes = {};
	var data = {};
	if(year){
		data = {year : year};
	}
	$.post('LoadCourse',
			data,//如果你需要传参数的话，可以写在这里，格式为：{参数名：值,参数名：值...}                  
			function(data){//执行成功之后的回调函数           
		var models = eval('('+data+')');// 对Servlet回传的字符串转换为json对象数组
		var us = models[0];
		for(k in us){
			var u = us[k];
			var initClass = New(Course,[u.courseId,u.courseName,u.courseType,u.score,u.teacher.name,u.maxStuNum,u.selectedStuNum,u.selectStuNum,u.id,u.year]);
			classes[k] = initClass;
		}
		//$('#myModal').modal('hide');
		addClassRowData(classes,id,year);
		refreshClassDatas(classes,id,year);              
	}
	);
	return classes;
}
/**
 * TODO 读取选课记录
 * @param id
 */
function loadScmDatas(id){
	var scmes = {};
	$.post('LoadSCUser',null,function(data){
		var models = eval('('+data+')');// 对Servlet回传的字符串转换为json对象数组
		var us = models[0];
		for(k in us){
			var u = us[k];
			var initScm = New(SelectCourseUser,[u.year,u.limitScore,u.startTime,u.closeTime,u.state]);
			scmes[k] = initScm;
		}
		addScmRowData(scmes,id);
		refreshScmDatas(scmes,id);   
	});
};


/**
 * TODO 添加用户记录
 * @param datas
 */
function addRowData (datas){
	var tbodyElement = document.getElementById("tbody");
	var html = "";
	var color = "warning";
	var flag = true;
	for(var i in datas){
		if(flag){
			color = "info";
		}else{
			color = "warning";
		}
		html = html +  "<tr class='"+ color +"'><td style='width:30px;'><input type='checkBox'>" +
		"<td id='id' style='display:none;'>"
		+ datas[i].id+"</td><td id='code'>"
		+ datas[i].code +
		"</td><td id='userName'>"+ datas[i].name +"</td><td id='sex'>"
		+ datas[i].sex +"</td><td id='passWord'>"
		+ datas[i].passWord +"</td><td id='age'>"
		+ datas[i].age +"</td><td id='rank'>"
		+ datas[i].rank +"</td><td id='classFrom'>"
		+ datas[i].classFrom + "</td><td id='type'>"
		+ datas[i].type + "</td>"
		+"</tr>";
		flag = !flag;//颜色转换
	}
	tbodyElement.innerHTML = html;
}
/**
 * TODO 刷新用户记录
 * @param datas
 */
function refreshDatas(datas){
	addRowData(datas);
};
/**
 * TODO 添加课程记录
 * @param datas
 * @param id
 * @param year
 */
function addClassRowData(datas,id,year){
	var tbodyElement = document.getElementById(id);
	var html = "";
	var color = "warning";
	var flag = true;
	for(var i in datas){
		if(flag){
			color = "info";
		}else{
			color = "warning";
		}
		if(year){
			html = html +  "<tr id='tclass_"+datas[i].id+"' class='"+ color +"'>" +
			"<td style='width:50px;text-align:center'>" +
			"<input type='checkBox' name='checked'>" +
			"</td>" +
			"<td id='classId'>"+datas[i].classId +"</td>" +
			"<td id='className'>" + datas[i].className +"</td>" +
			"<td id='classType'>"+ datas[i].classType +"</td>" +
			"<td id='score'>"+ datas[i].score +"</td>" +
			"<td id='teacher'>"+ datas[i].teacherName +"</td>" +
			"<td id='MaxStuNum'>"+ datas[i].MaxStuNum+"</td>" +
			"<td id='selectedStuNum'>"+ datas[i].selectedStuNum +"</td>" +
			"<td id='selectStuNum'>"+ datas[i].selectStuNum +"</td>" +
			"<td id='id' style='display:none'>"+ datas[i].id +"</td>" +
			"<td id='selectStuNum'>"+ datas[i].year +"</td>" 
			+"</tr>";
		} else {
			html = html +  "<tr id='tclass_"+datas[i].id+"' class='"+ color +"'>" +
			"<td style='width:50px;text-align:center'>" +
			"<input type='checkBox'style='margin-right='10px' name='checked'>" +
			"</td>" +
			"<td id='classId'>"+datas[i].classId +"</td>" +
			"<td id='className'>" + datas[i].className +"</td>" +
			"<td id='classType'>"+ datas[i].classType +"</td>" +
			"<td id='score'>"+ datas[i].score +"</td>" +
			"<td id='teacher'>"+ datas[i].teacherName +"</td>" +
			"<td id='MaxStuNum'>"+ datas[i].MaxStuNum+"</td>" +
			"<td id='selectedStuNum'>"+ datas[i].selectedStuNum +"</td>" +
			"<td id='selectStuNum'>"+ datas[i].selectStuNum +"</td>" +
			"<td id='courseId' style='display:none'>"+ datas[i].id +"</td>" +
			"<td id='selectStuNum'>"+ datas[i].year +"</td>" 
			+"</tr>";
		}

		flag = !flag;//颜色转换
	}
	tbodyElement.innerHTML = html;
	$("class_msg_table tr").click(function(){
		if($(this).find("input").prop("checked")){
			$(this).find("input").prop("checked","");
		} else {
			$(this).find("input").prop("checked","true");
		}
	});
	$("#scresult_msg_table tr").click(function(){
		if($(this).find("input").prop("checked")){
			$(this).find("input").prop("checked","");
		} else {
			$(this).find("input").prop("checked","true");
		}
	});

}
/**
 * TODO 刷新课程记录
 * @param datas
 * @param id
 * @param year
 */
function refreshClassDatas(datas,id,year){
	addClassRowData(datas,id,year);
};
/**
 * TODO 添加选课
 * @param datas
 * @param id
 */
function addScmRowData(datas,id){
	var tbodyElement = document.getElementById(id);
    var html = "";
    var color = "warning";
    var flag = true;
    for(var i in datas){
        if(flag){
            color = "info";
        }else{
            color = "warning";
        }
        var closeTime = datas[i].closeTime?datas[i].closeTime:"-- : -- :-- ";
        var startTime = datas[i].startTime?datas[i].startTime:"-- : -- :-- ";
        
        var state = datas[i].state?"正在选课":"选课结束";
        html = html +  "<tr id='trscm_"+datas[i].year+"' class='"+ color +"'>" +
        		"<td style='width:50px;text-align:center'>" +
        			"<input type='checkBox' name='checked'>" +
        		"</td>" +
        		"<td id='year'>"+datas[i].year +"</td>" +
        		"<td id='startTime'>" + startTime +"</td>" +
        		"<td id='closeTime'>"+ closeTime +"</td>" +
        		"<td id='state'>"+ state +"</td>" +
                "</tr>";
        flag = !flag;//颜色转换
    }
    tbodyElement.innerHTML = html;
	$("#sc_msg_table tr").click(function(){
		$(this).find("input").prop("checked","checked");
	});
}
/**
 * TODO 刷新选课
 * @param datas
 * @param id
 */
function refreshScmDatas(datas,id){
	addScmRowData(datas,id);
};





/**
 * 收集一行数据
 */
function collectionRowData(param){
	var tdElement = param.getElementsByTagName("td");
	var userArr = [];
	for(var i=1;i<tdElement.length;i++){
		var temp =  tdElement[i].textContent;
		userArr[i-1] = temp;
	}
	var user = New(User,userArr);
	return user;
}
function collectionClassRowData(param){
	var tdElement = param.getElementsByTagName("td");
	var userArr = [];
	for(var i=1;i<tdElement.length;i++){
		var temp =  tdElement[i].textContent;
		userArr[i-1] = temp;
	}
	var user = New(Course,userArr);
	return user;
}


/**
 * 用户操作方法
 */
function optionUserData(param){
	//获得操作类别
	var optionType = param.getAttribute("id");
	if(optionType == "user_add"){
		operateType = "add";
		$("#btnSave").show();
		$("#btnModify").hide();
	}else if(optionType == "user_delete"){
		var checkRowData = isCheckedData("tbody");
		var user = collectionRowData(checkRowData);
		user.deleteUserData();        
	}else if(optionType == "user_edit"){
		operateType = "edit";
		var checkRowData = isCheckedData("tbody");
		var user = collectionRowData(checkRowData);
		var modal_body = document.getElementById("modal-body");
		var inputElements=  modal_body.getElementsByTagName("input");
		for(var i=0;i<inputElements.length;i++){
			var temp = inputElements[i].id.substring(2,inputElements[i].id.length)
			inputElements[i].value = user[temp];
			$("#btnSave").hide();
			$("#btnModify").show();
		}
	}else if(optionType == "user_find"){
		var s_code =  document.getElementById("t_code").value;
		var s_userName =  document.getElementById("t_userName").value;
		var s_all=  document.getElementById("t_all").value;
		//搜索数据
		findUser("SearchUser",id,s_code,s_userName,s_all);
	}else{

	}
}
/**
 * 是否选中数据,返回选中数据的行
 */
function isCheckedData(id){
	var tbodyElement =document.getElementById(id);
	var trElements = tbodyElement.getElementsByTagName("tr");
	var flag = false;
	for(var i=0;i<trElements.length;i++){
		var inputElement = trElements[i].getElementsByTagName("input")[0];
		if(inputElement.checked){
			flag = true;
			return trElements[i];
		}
	}
	if(!flag){
		alert("请选择一条记录！");
		$('#myModal').unbind("on");
	}
}

/**
 * TODO 查找用户
 * @param url
 * @param id
 * @param code
 * @param name
 * @param all
 * @returns {Boolean}
 */
function findUser(url,id,code,name,all){
	var data ={
			code :code,name:name,all:all	
	};
	$.ajax({
		url:url,
		type:"POST",
		data:data,
		success : function(data) {
			var models = eval('('+data+')');// 对Servlet回传的字符串转换为json对象数组
			var us = models[0];
			users = {};
			for(k in us){
				var u = us[k];
				var initUser = New(User,[u.id,u.code,u.name,u.sex,u.password,u.age,u.rank,u.classFrom]);
				users[k] = initUser;
			}
			//$('#myModal').modal('hide');s
			addRowData(users,id);
		},
		error : function(data) {
			$.messager.alert("提示", "操作失败", "info");
		}
	});
	return false;
}

function findCourse(url,id,courseId,courseName,all){
	var data = {
			courseId : courseId,
			courseName : courseName,
			all : all
		};
		$.ajax({
			url : url,
			type : "POST",
			data : data,
			success : function(data) {
				var models = eval('('+data+')');// 对Servlet回传的字符串转换为json对象数组
				var us = models[0];
				classes = {};
				for(k in us){
					var u = us[k];
					var initClass = New(Course,[u.courseId,u.courseName,u.courseType,u.score,u.teacher.name,u.maxStuNum,u.selectedStuNum,u.selectStuNum,u.id,u.year]);
				    classes[k] = initClass;
				}
				addClassRowData(classes,id);
			},
			error : function(data) {
				$.messager.alert("提示", "操作失败", "info");
			}
		});
		return false;
}

//选课
function getAllTeacher(text){
	var html = "";
	$("#courseTeacher").empty();
	$.post('loadUsers',
			null,//如果你需要传参数的话，可以写在这里，格式为：{参数名：值,参数名：值...}                  
			function(data){//执行成功之后的回调函数           
		var models = eval('('+data+')');// 对Servlet回传的字符串转换为json对象数组
		var us = models[0];
		for(k in us){
			var u = us[k];
			if(u.type=="Teacher"){
				html+= "<option value='"+u.id+"'>"+u.name+"</option>";
			}
		}
		$("#courseTeacher").html(html);
		if(text){
			$("#courseTeacher option").each(function(){
				if($(this).text() == text){
					$(this).prop("selected","selected");
				}
			});
		}
	});
}

function optionClassData(param,id){
	//获得操作类别
	var optionType = param.getAttribute("id");
	if(optionType == "class_add"){
		operateType = "add";
		getAllTeacher();
		$("#btnUpdateClass").hide();
		$("#btnSaveClass").show();
	}else if(optionType == "class_delete"){
		var checkRowData = isCheckedData(id);
		var user = collectionClassRowData(checkRowData);
		user.deleteClassData();        
	}else if(optionType == "class_edit"){
		operateType = "edit";
		var checkRowData1 = isCheckedData(id);
		if(checkRowData1){
			$("#btnUpdateClass").show();
			$("#btnSaveClass").hide();
			$("#class_edit").attr("data-target","#classModal");
			var user = collectionClassRowData(checkRowData1);
			var class_modal = document.getElementById("class_modal");
			var inputElements=  class_modal.getElementsByTagName("input");
			getAllTeacher(user['teacherName']);
			for(var i=0;i<inputElements.length;i++){
				var temp = inputElements[i].id.substring(2,inputElements[i].id.length);
				inputElements[i].value = user[temp];
			}

		} 
	}else if(optionType == "class_find"){
		var s_code =  document.getElementById("c_code").value;
		var s_userName =  document.getElementById("c_name").value;
		var s_all=  document.getElementById("c_all").value;
		findCourse("SearchCourse",id, s_code, s_userName, s_all);
	}else{

	}
}
