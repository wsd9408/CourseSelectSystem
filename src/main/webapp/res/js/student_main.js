$(document).ready(function(){
	$.getUrlParam = function (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	};
	var uid = $.getUrlParam("id");
	if(uid != ""){
		$.ajax({
			url:"LoginData",
			type : "POST",
			data:{
				uid:uid,
			},
			dataType:"json",
			success: function(data) {;
			var us = data[0];
			for(var i in us){
				$("#Stuid").html(us[i].id);
				$("#code").html(us[i].code);
				$("#name").html(us[i].name);
				$("#sex").html(us[i].sex);
				$("#age").html(us[i].age);
				$("#rank").html(us[i].classFrom);
			}
			},
		});
	}
	$.ajax({
		url : 'LoadSelectedUser',
		type : "POST",
		dataType : 'json',
		data : {},
		success : function(data) {
			if(data.state){
				saveYear = data.year;
				loadClassDatas('class_msg_table',saveYear);
			} else {
				$("#TabMain").hide();
				alert('暂未开通');
			}
		}
	});
	
	$("#query_allsc").click(function(){
		displayCourseResult(saveYear);
	});
	$("#delete_sc").click(function(){
		var param = isCheckedData('scresult_msg_table');
		if(param){
			var sclass = collectionClassRowData(param);
			$.ajax({
				url : 'DelSelectCourse',
				type : "POST",
				dataType : 'json',
				data : {
					id:sclass.id,
					code:$("#code").text(),
				},
				success : function(data) {
					alert(data.msg);
					displayCourseResult(saveYear);
				}
			});
		}
	});
	

	
	$("#add_sc").click(function(){
		var param = isCheckedData('class_msg_table');
		if(param){
			var sclass = collectionClassRowData(param);
			$.ajax({
				url : 'AddSelectCourse',
				type : "POST",
				dataType : 'json',
				data : {
					stuId:$("#Stuid").text(),
					id:sclass.id,
					code: $("#code").text(),
				},
				success : function(data) {
					alert(data.msg);
					loadClassDatas("class_msg_table",saveYear);
				}
			});
		}
	});
	
	
});


function displayCourseResult(year){
	$.ajax({
		url : 'LoadSCResult',
		type : "POST",
		dataType : 'json',
		data : {
			year:year,
			code:$("#code").text(),
		},
		success : function(data) {
			var us = data[0];
			classes = {};
			for(k in us){
				var u = us[k];
				var initClass = New(Course,[u.courseId,u.courseName,u.courseType,u.score,u.teacher.name,u.maxStuNum,u.selectedStuNum,u.selectStuNum,u.id,u.year]);
			    classes[k] = initClass;
			}
			addClassRowData(classes,'scresult_msg_table');
			refreshClassDatas(classes,'scresult_msg_table');   
		}
	});
}


