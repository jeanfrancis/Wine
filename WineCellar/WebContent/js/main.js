$(document).ready(function() {
	notice("document ready..");
	window.WineApp = null;
	var userRoles = new UserRoleCollection();
	userRoles.fetch({success: initialize});
});

function initialize() {
	
	notice("initializing...");
	window.user = new UserModel();
	
	ajaxSetup();	
	
	$("#submitLogin").click(function(){
		login($("#userId").val(), $("#password").val());
		checkPermissions();
	});
	
	if (getCookie("userId") != null){
		$("#userId").val(getCookie("userId"));
		checkPermissions();
		
	}
}

function processRole(user){
	if (user.isAdmin()){		
		notice(user.get("userName") + ", You are an Admin");
		startApp();			
	}else if (user.getRole() == "READ"){
		notice(user.get("userName") + ", You are READ only");
		startApp();
	} else{
		notice(user.get("userName") + ", You do not have permission to view this application.");
		stopApp();
	}
}

function startApp(){	
	if (WineApp == null){
		WineApp = new WineAppView({
			el : "#appView"
		});
	}else{		
		window.location = "";
	}	
}

function stopApp(){
	if (WineApp != null){
		WineApp.remove();
	}
}

function checkPermissions() {
	$.ajax({
		url: "/rest/login",
		dataType: "json",
		success: function(resp){
			user.set(resp);
			processRole(user);
		}
		,
		error: function(resp){
			stopApp();
		}
	});
	
}

function login(userId, password){
	setCookie("userId", userId, 30);
}

function setCookie(c_name, value, exdays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value = escape(value)
			+ ((exdays == null) ? "" : "; expires=" + exdate.toUTCString());
	document.cookie = c_name + "=" + c_value;
}

function getCookie(c_name){
var i,x,y,ARRcookies=document.cookie.split(";");
	for (i=0;i<ARRcookies.length;i++){
	  x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
	  y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
	  x=x.replace(/^\s+|\s+$/g,"");
	  if (x==c_name){
	    return unescape(y);
	  }
	}
	return null;
}

function ajaxSetup(){
	$.ajaxSetup({
		statusCode : {
			401 : function() {
				// 401 -- FORBIDDEN
				notice("You do not have the required permissions.");
				
			},
			403 : function() {
				// 403 -- UNAUTHORIZED
				notice("You are unauthorized to view this application.");
			}
		}
	});
}

function notice(msg){
	$("#notice").html(msg);
}
