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
<TR><TD colspan=2 bgcolor="#G7750F"><b><font size="3" color="white">HIBERNATE</font></b></TD></TR>
<TR><TD>
<font size="3" color="black">
<b>Hibernate Tutorial</b>
<br>
<b>hibernate tutorial with example</b><br>
This hibernate tutorial provides in-depth concepts of Hibernate Framework with simplified examples. It was started in 2001 <br>
by Gavin King as an alternative to EJB2 style entity bean. <br>
The stable release of Hibernate till July 16, 2014, is hibernate 4.3.6. It is helpful for beginners and experienced persons.<br>

<B>Advantages of Hibernate Framework</B>

There are many advantages of Hibernate Framework. They are as follows:<br>

1) Opensource and Lightweight: Hibernate framework is opensource under the LGPL license and lightweight.<br>

2) Fast performance: The performance of hibernate framework is fast because cache is internally used in <br>
hibernate framework. There are two types of cache in hibernate framework first level cache and second level cache. 
First level cache is enabled bydefault.<br>

3) Database Independent query: HQL (Hibernate Query Language) is the object-oriented version of SQL.<br>
 It generates the database independent queries. So you don't need to write database specific queries. Before Hibernate,<br>
 If database is changed for the project, we need to change the SQL query as well that leads to the maintenance problem.<br>

4) Automatic table creation: Hibernate framework provides the facility to create the tables of the database automatically. <br>
So there is no need to create tables in the database manually.<br>

5) Simplifies complex join: To fetch data form multiple tables is easy in hibernate framework.<br>

6) Provides query statistics and database status: Hibernate supports Query cache and provide statistics about query and database status.
</font>
</TD></TR>

</TABLE>

<input type=button onClick="fnnfinish()" value='Finish'>
<INPUT TYPE=hidden name="Page" value="1">
</form>
</body>
</html>