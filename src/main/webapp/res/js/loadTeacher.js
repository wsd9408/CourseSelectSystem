var uid;
$(document).ready(function(){
	$.getUrlParam = function (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
	};
	 uid = $.getUrlParam("id");
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
				$("#t_id").html(us[i].id);
				$("#t_teacherCode").html(us[i].code);
				$("#t_name").html(us[i].name);
				$("#t_sex").html(us[i].sex);
				$("#t_age").html(us[i].age);
				$("#t_rank").html(us[i].rank);
			}
			},
		});
	}
});
