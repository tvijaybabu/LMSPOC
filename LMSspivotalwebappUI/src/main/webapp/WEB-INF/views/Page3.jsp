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
<TR><TD colspan=2 bgcolor="#G7750F"><b><font size="5" color="white">LMS</font></b></TD></TR>
<TR><TD>
<font size="3" color="black">
<b>Micro services</b><br>

The application is divided into 3 different micro services.
<ol>
   <li>User registration module.</li>
   <li>User course assignment module.</li>
    <li>Admin module</li>
    </ol>
<ul>
<li>Separate war in deployed for each micro service.</ul>
<li>Communication between micro services is achieved by RESTFul web services.</li>
<li>Separate Database is maintained for each micro service.</li>
</ul>
</font>
</TD></TR>

</TABLE>

<input type=button onClick="location.href='/course/Open?Page=2'" value='previous'>
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