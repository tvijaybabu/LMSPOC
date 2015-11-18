<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="/resources/css/emc-styles.css" type="text/css">
<title>Course1</title>
</head>
<body>
<form name="page1form">
<TABLE id="headertabl" BORDER=1 width="100%">
<TR bgcolor="#G7750F" height=50 font-color="white"><TD nowrap align=center class=text10 >
<b><font size="5" color="white" class="bigfont">Learning Management System</font></b></TD></TR>
</TABLE>


<BR><BR><BR><BR>
<TABLE BORDER=0 width=70% align=center id="tablemain" class="table-bordered">
<TR><TD colspan=2 bgcolor="#G7750F"><b><font size="5" color="white">LMS</font></b></TD></TR>
<TR><TD>
<font size="3" color="black">
Introduction: The application allows the user to register into the application , an email will be end to user after successful registration.<br>
After registering the user can plan his/her development plan by selecting the courses available. After the completion of the user can print the course completion certificate. 

</font>
</TD></TR>

</TABLE>
<input type=button onClick="location.href='/course/Open?Page=2'" value='Next' class="martopLeft">
<INPUT TYPE=hidden name="Page" value="1">
</form>
</body>
</html>