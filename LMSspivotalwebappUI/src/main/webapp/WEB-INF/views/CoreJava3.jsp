<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script>
function fnnfinish()
{
	//alert(window.opener.name)
	window.opener.fnnpop();
	window.close();
	/* document.page3form.method="POST";
	document.page3form.action="/course/complete";
	//document.page3form.target="_parent";
	//document.page3form.target="parent";
	document.page3form.target=window.opener.name;//.document.forms.userform;
	document.page3form.submit();
	window.close();
	 *///var myParent = opener.document.forms.userform;
	//myParent.h1.value=aValue;
	//myParent.fnnpop(); 
	}
</script>
</head>
<body>
<form name="page3form">
<TABLE BORDER=1 width="100%">
<TR bgcolor="#G7750F" height=50 font-color="white"><TD nowrap align=center class=text10 >
<b><font size="5" color="white">Learning Management System</font></b></TD></TR>
</TABLE>


<BR><BR><BR><BR>
<TABLE BORDER=1 width=70% align=center>
<TR><TD colspan=2 bgcolor="#G7750F"><b><font size="5" color="white">Core Java</font></b></TD></TR>
<TR><TD><font size="3" color="black">
<b>Features of Java</b>
<ul>
<li>Features of Java</li>
<li>Simple</li>
<li>Object-Oriented</li>
<li>Platform Independent</li>
<li>secured</li>
<li>Robust</li>
<li>Architecture Neutral</li>
<li>Portable</li>
<li>High Performance</li>
<li>Distributed</li>
<li>Multi-threaded</li>
</ul></font>
</TD></TR>

</TABLE>
<% 
String coursename= request.getParameter("coursename");
coursename=coursename.replace(" ", "+");
%>
<INPUT TYPE=hidden name="coursename" value="<%=coursename%>">
<input type=button onClick="location.href='/course/Open?coursename=<%=coursename%>&Page=2'" value='previous'>
<input type=button onClick="fnnfinish()" value='Finish'>
<INPUT TYPE=hidden name="Page" value="3">
<INPUT TYPE=hidden name="userid" value="462">
<INPUT TYPE=hidden name="courseid" value="102">
<INPUT TYPE=hidden name="username" value="162">
<INPUT TYPE=hidden name="password" value="162">

<INPUT TYPE=hidden name="email" value="162">
<INPUT TYPE=hidden name="role" value="162">

<INPUT TYPE=hidden name="userid" value="162">
<INPUT TYPE=hidden name="courseid" value="162">

</form>


</body>
</html>