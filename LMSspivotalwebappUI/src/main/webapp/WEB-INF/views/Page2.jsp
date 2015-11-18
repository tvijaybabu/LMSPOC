<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<script>
window.name="2home";
</script>
<body>
<form name="page2form">
<TABLE BORDER=1 width="100%">
<TR bgcolor="#G7750F" height=50 font-color="white"><TD nowrap align=center class=text10 >
<b><font size="5" color="white">Learning Management System</font></b></TD></TR>
</TABLE>


<BR><BR><BR><BR>
<TABLE BORDER=1 width=70% align=center>
<TR><TD colspan=2 bgcolor="#G7750F"><b><font size="5" color="white">LMS</font></b></TD></TR>
<TR><TD>
<font size="3" color="black">
Application Functionalities
The below are the functionalities of LMS application.
<ul>
<li>Registering the user.</li>
<li>Sending Mail after successful registration.</li>
<li>Caching the user details.</li>
<li>Catalog and courses creation.</li>
<li>Creating the user reports in pdf format</li>
<li>Assigning the Courses to the user.</li>
<li>Printing the Course completion certificate.</li>
</font>
</TD></TR>

</TABLE>
<input type=button onClick="location.href='/course/Open?Page=1'" value='previous'>
<input type=button onClick="location.href='/course/Open?Page=3'" value='Next'>
<INPUT TYPE=hidden name="Page" value="2">
</form>
</body>
</html>