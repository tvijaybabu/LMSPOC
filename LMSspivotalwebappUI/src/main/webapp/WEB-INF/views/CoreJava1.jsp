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
<TABLE id="headertabl" BORDER=0 width="100%" class="table-bordered">
<TR bgcolor="#G7750F" height=50 font-color="white"><TD nowrap align=center class=text10 >
<b><font size="5" color="white" class="bigfont">Learning Management System</font></b></TD></TR>
</TABLE>


<BR><BR><BR><BR>
<TABLE BORDER=0 width=70% align=center class="table-bordered" id="tablemain">
<TR><TD colspan=2 bgcolor="#G7750F"><b><font size="5" color="white">Core Java</font></b></TD></TR>
<TR><TD>
<font size="3" color="black">
Java is a programming language and a platform.
Java is a high level, robust, secured and object-oriented programming language.

Platform: Any hardware or software environment in which a program runs, is known as a platform. Since Java has its own runtime environment (JRE) and API, it is called platform.
</font>
</TD></TR>

</TABLE>
<% 
String coursename= request.getParameter("coursename");
coursename=coursename.replace(" ", "+");
System.out.println("coursename====="+coursename);
%>
<input type=button onClick="location.href='/course/Open?coursename=<%=coursename%>&Page=2'" value='Next'>
<INPUT TYPE=hidden name="Page" value="1">
<INPUT TYPE=hidden name="coursename" value="<%=coursename%>">
</form>
</body>
</html>