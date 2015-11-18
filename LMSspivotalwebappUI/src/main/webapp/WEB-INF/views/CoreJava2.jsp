<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
 <link rel="stylesheet" href="/resources/css/emc-styles.css" type="text/css">
<title>Insert title here</title>
</head>
<script>
window.name="2home";
</script>
<body>
<form name="page2form">
<TABLE id="headertabl" BORDER=0 width="100%" class="table-bordered">
<TR bgcolor="#G7750F" height=50 font-color="white"><TD nowrap align=center class=text10 >
<b><font size="5" color="white" class="bigfont">Learning Management System</font></b></TD></TR>
</TABLE>


<BR><BR><BR><BR>
<TABLE BORDER=0 width=70% align=center id="tablemain" class="table-bordered">
<TR><TD colspan=2 bgcolor="#G7750F"><b><font size="5" color="white">Core Java</font></b></TD></TR>
<TR><TD>
<font size="3" color="black">
Types of Java Applications<BR>
There are mainly 4 type of applications that can be created using java programming:
<BR>
<UL>
<li>Standalone Application</li>

It is also known as desktop application or window-based application. An application that we need to install on every machine such as media player, antivirus etc. AWT and Swing are used in java for creating standalone applications.

<li> Web Application</li>

An application that runs on the server side and creates dynamic page, is called web application. Currently, servlet, jsp, struts, jsf etc. technologies are used for creating web applications in java.

<li>Enterprise Application</li>

An application that is distributed in nature, such as banking applications etc. It has the advantage of high level security, load balancing and clustering. In java, EJB is used for creating enterprise applications.

<li> Mobile Application</li>

An application that is created for mobile devices. Currently Android and Java ME are used for creating mobile applications.
</UL></font>
</TD></TR>

</TABLE>
<% 
String coursename= request.getParameter("coursename");
		coursename=coursename.replace(" ", "+");
%>
<INPUT TYPE=hidden name="coursename" value="<%=coursename%>">
<input type=button onClick="location.href='/course/Open?coursename=<%=coursename%>&Page=1'" value='previous'>
<input type=button onClick="location.href='/course/Open?coursename=<%=coursename%>&Page=3'" value='Next'>
<INPUT TYPE=hidden name="Page" value="2">
</form>
</body>
</html>