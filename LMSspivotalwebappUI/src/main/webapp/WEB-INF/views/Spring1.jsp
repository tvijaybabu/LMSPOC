<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course1</title>
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
<form name="page1form">
<TABLE BORDER=1 width="100%">
<TR bgcolor="#G7750F" height=50 font-color="white"><TD nowrap align=center class=text10 >
<b><font size="5" color="white">Learning Management System</font></b></TD></TR>
</TABLE>


<BR><BR><BR><BR>
<TABLE BORDER=1 width=70% align=center>
<TR><TD colspan=2 bgcolor="#G7750F"><b><font size="3" color="white">Spring Frame Work</font></b></TD></TR>
<TR><TD>
<font size="3" color="black">
Spring framework is an open source Java platform that provides comprehensive infrastructure support for developing <br>
robust Java applications very easily and very rapidly.<br>

Spring framework was initially written by Rod Johnson and was first released under the Apache 2.0 license in June 2003.<br>

This tutorial has been written based on Spring Framework version 4.1.6 released in Mar 2015.

<br>
<b>Dependency Injection (DI):</b>
The technology that Spring is most identified with is the Dependency Injection (DI) flavor of <br>
Inversion of Control. The Inversion of Control (IoC) is a general concept, and it can be expressed <br>
in many different ways and Dependency Injection is merely one concrete example of Inversion of Control.<br>

When writing a complex Java application, application classes should be as independent as possible of other <br>
Java classes to increase the possibility to reuse these classes and to test them independently of other classes<br> 
while doing unit testing. Dependency Injection helps in gluing these classes together and same time keeping them independent.
</font>
</TD></TR>

</TABLE>

<input type=button onClick="fnnfinish()" value='Finish'>
<INPUT TYPE=hidden name="Page" value="1">
</form>
</body>
</html>