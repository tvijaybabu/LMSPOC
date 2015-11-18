<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@page import="java.util.*" %>
<html>
<head>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="/resources/css/emc-styles.css" type="text/css">
<title>Cloud Foundry sample Application -Login screen</title>

<% 
//session=null;
String ctxpath	=	request.getContextPath();
String error="";
if(request.getParameter("Error")!=null){
error	=	request.getParameter("Error");
}
if(error.equalsIgnoreCase("Failure")){
	error	=	"User Authentication Failed ,Please Try with Proper EmployeeNo and PassWord";
}
HashMap Ml = new HashMap();
if(request.getAttribute("ManagerMap")!=null)
	Ml=(HashMap)request.getAttribute("ManagerMap");

%>
<SCRIPT>
function fnnload(){
	
	document.loginform.UserName.focus();
}
function fnnlogin()
{
	if(trim(document.loginform.UserName.value)==""){
	alert("Fields cannot be blank");
	document.loginform.UserName.focus();
	return false;
	}
	if(trim(document.loginform.password.value)==""){
		alert("Fields cannot be blank");
		document.loginform.password.focus();
		return false;
	}
	var path="<%=ctxpath%>";
	document.loginform.event.value="login";
	document.loginform.action=path+"/login/Auth";
	document.loginform.submit();
}
function fnnRegister()
{
	if(trim(document.loginform.NewUserName.value)==""){
	alert("Fields cannot be blank");
	document.loginform.NewUserName.focus();
	return false;
	}
	if(trim(document.loginform.Newpassword.value)==""){
		alert("Fields cannot be blank");
		document.loginform.Newpassword.focus();
		return false;
	}
	if(trim(document.loginform.Newrepassword.value)==""){
		alert("Fields cannot be blank");
		document.loginform.Newrepassword.focus();
		return false;
	}
	if(trim(document.loginform.Newpassword.value)!=trim(document.loginform.Newrepassword.value))
		{
		alert("Password and Re enter password should be same");
		document.loginform.Newrepassword.focus();
		return false;
		}
	if(trim(document.loginform.NewEmail.value)==""){
		alert("Fields cannot be blank");
		document.loginform.NewEmail.focus();
		return false;
	}
	if(trim(document.loginform.RMID.value)=="-1"){
		alert("Fields cannot be blank");
		document.loginform.ReportingManager.focus();
		return false;
	}
/* 	alert(document.loginform.RMID.selectedIndex)
	alert()
 */	document.loginform.ReportingManager.value=document.loginform.RMID.options[document.loginform.RMID.selectedIndex].text;
	var path="<%=ctxpath%>";
	document.loginform.event.value="register";
	document.loginform.action=path+"/login/Auth";
	document.loginform.submit();
}
function trim(str) {
    return str.replace(/^\s+|\s+$/g,"");
}
</SCRIPT>
</head>
<BODY>
<%-- <h1>
	Hello world!  
</h1>

<P>  The time on the server is********* ${serverTime}. </P>
 --%>
<form method="POST" name="loginform" onload="javascript:fnnload()">
<TABLE id="headertabl" BORDER=1 width="100%">
<TR bgcolor="#G7750F" height=50 font-color="white"><TD nowrap align=center class=text10 >
<b><font size="5" color="white" class="bigfont">Learning Management System</font></b></TD></TR>
</TABLE>
<TABLE BORDER=0 align=center width=80% id="tablemain" class="table-bordered martop" >
<TR><TD nowrap align=center><font color=red><h3 class="mar00">${MS2}</h3></font></TD></TR>
<TR><TD nowrap align=center><font color=red><h3 class="mar00">${MS3}</h3></font></TD></TR>
</TABLE>
<TABLE BORDER=0 align=center width=80% id="tablemain" class="table-bordered">
<TR><TD nowrap align=center><font color=red><h3 class="mar00">${result}</h3></font></TD></TR>
</TABLE>
<BR><BR><BR><BR>
<font size="3" color="black">
<TABLE BORDER=0 align=center width=80%>
<tr><td width="50%">
<TABLE BORDER=0 id="bordertblhome" width="100%">
<TR><TD colspan=2 bgcolor="#G7750F"><b><font size="3" color="white">New User</font></b></TD></TR>
<TR><TD>Enter User Name</TD><td>
<INPUT TYPE=TEXT NAME="NewUserName" maxlength=30/>
</TD></TR>
<TR><TD>Enter Password</TD><TD>
<INPUT TYPE=password NAME="Newpassword" maxlength=12/>
</TD></TR>
<TR><TD>Re-Enter Password</TD><TD>
<INPUT TYPE=password NAME="Newrepassword" maxlength=12/>
</TD></TR>
<TR><TD>Enter Email-id</TD><TD>
<INPUT  type="TEXT" NAME="NewEmail" maxlength=30/>
</TD></TR>
<TR><TD>Select Role</TD><TD>
<select NAME="Role"><option value='User'>User</option>
<option value='Manager'>Manager</option>
<option value='Admin'>Admin</option>
</select>
</TD></TR>
<TR><TD>Select Reporting Manager</TD><TD>
<select NAME="RMID">
<option value='-1'>Select Reporting Manager</option>
<% 
if(Ml!=null){
	 Iterator it = Ml.entrySet().iterator();
	  while (it.hasNext())
	  {
		  Map.Entry pair = (Map.Entry)it.next();
		  Object k=pair.getKey();
		  Object v=pair.getValue();
	%>
		<option value=<%=k%>><%=v%></option>
<%
		  System.out.println("Ml elements in jsp.."+k+"::"+v);
}} %>
</select>
</TD></TR>

<TR><TD colspan=2 align=center><INPUT TYPE=BUTTON VALUE="Register" onclick="javascript:fnnRegister()" class="homeresister"></TD>

</TR>
</TABLE></td><td valign=top width="50%">
<TABLE BORDER=0 id="bordertblhome" width="100%">
<TR><TD colspan=2 bgcolor="#G7750F"><b><font size="3" color="white">Existing User</font></b></TD></TR>
<TR><TD>Enter User Name</TD><td>
<INPUT TYPE=TEXT NAME="UserName"/>
</TD></TR>
<TR><TD>Enter Password</TD><TD>
<INPUT TYPE=password NAME="password" maxlength=12/>
</TD></TR>
<TR><TD colspan=2 align=center><INPUT TYPE=BUTTON VALUE="Login" onclick="javascript:fnnlogin()" class="homelogin"></TD>
</TR>
</TABLE>
</td></tr></TABLE>
<br><br><br><br><br><TABLE BORDER=1 width="100%">
<TR bgcolor="#G7750F" height=50><TD nowrap align=center ></TD></TR>
</TABLE>
<INPUT TYPE=hidden name="event" value="login">
<INPUT TYPE=hidden name="ReportingManager" value="">

</font>
</center>
</form>
</body>
</html>
