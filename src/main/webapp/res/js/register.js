

/**
 * 
 */
//��������û�
var users = users || {};
//��������
var operateType = "";
//�����������
var searchUsers = searchUsers || {};
//�û����췽��
var User = {
        Create:function(code,name,sex,passWord,age,type){
            this.code = code;
            this.name = name;
            this.sex = sex;
            this.passWord = passWord;
            this.age = age;
            this.type = type;
        },
        //����û�
        addUserData:function(){
            if(this.code != ""){
                users[this.code] = this;                
            }
        },
        //ɾ���û�
        deleteUserData:function (){
//            for(var i in users){
//                if(this.code == users[i].code){
//                    delete users[i];
//                }
//            }
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
        //�༭�û�
        editUserData:function(){
            for(var i in users){
                if(this.code == users[i].code){
                    users[i].name = this.name;
                    users[i].sex = this.sex;
                    users[i].passWord = this.passWord;
                    users[i].type = this.type;
                    users[i].age = this.age;
                }
            }
        },
        //�����û�
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


function New(aClass,aParams){
    function new_(){
        aClass.Create.apply(this,aParams);
    }
    new_.prototype = aClass;
    return new new_();
}



/**
 * �״μ���ҳ��ִ�з���
 */
function loadUserDatas(){   
//    var initUser1 = New(User,["1001","С��","Ů","1234","13","1991-1-1"]);
//    var initUser2 = New(User,["1002","С��","��","1234","13","1991-1-1"]);
//    var initUser3 = New(User,["1003","����","Ů","1234","13","1991-1-1"]);
//    var initUser4 = New(User,["1004","����","Ů","1234","13","1991-1-1"]);
//    users[initUser1.code] = initUser1;
//    users[initUser2.code] = initUser2;
//    users[initUser3.code] = initUser3;
//    users[initUser4.code] = initUser4;
//    return users;
	var users = {};
	$.post('loadUsers',
			null,//�������Ҫ������Ļ�������д�������ʽΪ��{������ֵ,������ֵ...}                  
			function(data){//ִ�гɹ�֮��Ļص�����           
				//alert(data); 
				var models = eval('('+data+')');// ��Servlet�ش����ַ�ת��Ϊjson��������
				var us = models[0];
				for(k in us){
					var u = us[k];
					var initUser = New(User,[u.code,u.name,u.sex,u.password,u.age,u.type]);
				    users[k] = initUser;
				}
				//$('#myModal').modal('hide');
				addRowData(users);
				refreshDatas(users);              
			}
	);

//    var initUser4 = New(User,["1004","����","Ů","1234","13","1991-1-1"]);
//    users[initUser1.code] = initUser1;
//    users[initUser2.code] = initUser2;
//    users[initUser3.code] = initUser3;
//    users[initUser4.code] = initUser4;
    return users;
}

/**
 * �������һ��html���
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
        html = html +  "<tr class='"+ color +"'><td style='width:30px;'><input type='checkbox'></td><td id='code'>"
                + datas[i].code +"</td><td id='userName'>"
                + datas[i].name +"</td><td id='sex'>"
                + datas[i].sex +"</td><td id='passWord'>"
                + datas[i].passWord +"</td><td id='age'>"
                + datas[i].age +"</td><td id='type'>"
                + datas[i].type +"</td>" 
                +"</tr>";
                
        flag = !flag;//��ɫת��
    }
    tbodyElement.innerHTML = html;
}
/**
 * ˢ���û����
 */
function refreshDatas(datas){
    addRowData(datas);
};

/**
 * �ռ�һ�����
 */
function collectionRowData(param){
    var tdElement = param.getElementsByTagName("td");
    var userArr = [];
    for(var i=1;i<tdElement.length;i++){
        var temp =  tdElement[i].textContent;
        userArr[i-1] = temp;
    }
    var user = New(User,userArr);
   // alert(user);
    return user;
}
/**
 * �û���������
 */
function optionUserData(param){
    //��ò������
    var optionType = param.getAttribute("id");
    if(optionType == "user_add"){
        operateType = "add";
    }else if(optionType == "user_delete"){
        var checkRowData = isCheckedData();
        var user = collectionRowData(checkRowData);
        user.deleteUserData();        
    }else if(optionType == "user_edit"){
        operateType = "edit";
        var checkRowData = isCheckedData();
        var user = collectionRowData(checkRowData);
        var modal_body = document.getElementById("modal-body");
        var inputElements=  modal_body.getElementsByTagName("input");
        for(var i=0;i<inputElements.length;i++){
            var temp = inputElements[i].id.substring(2,inputElements[i].id.length)
            inputElements[i].value = user[temp];
        }
    }else if(optionType == "user_find"){
    	operateType = "find";
        var s_code =  document.getElementById("s_code").value;
        var s_userName =  document.getElementById("s_userName").value;
        var s_all=  document.getElementById("s_all").value;
        //�������
        var s_data = s_data || {};
        s_data.code = s_code;
        s_data.name = s_userName;
        s_data.all = s_all;
        var user = New(User,[]);
        user.findUserData(s_data);
    }else{
        
    }
}

/**
 * �Ƿ�ѡ�����,����ѡ����ݵ���
 */
function isCheckedData(){
    var tbodyElement =document.getElementById("tbody");
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
        alert("��ѡ��һ����¼��");
        $('#myModal').unbind("on");
    }
}
