<%@page import="java.util.*,com.hcl.util.CourseBean"%>
<%@ page session="false" import="java.util.concurrent.ConcurrentHashMap"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="/resources/css/emc-styles.css" type="text/css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Cloud Foundry sample Application -Admin screen</title>

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
String uid="";
if(request.getAttribute("uuid")!=null)
	uid=request.getAttribute("uuid").toString();
%>
<SCRIPT>
var k;
function fnnupdate()
{
//	for(var i=0;i<)
//alert(k)
document.adminform.Hidcoursestatus.value=document.adminform.coursestatus[k].value;
//alert(document.adminform.Hidcoursestatus.value)
//alert(document.adminform.Hidcourseid.value)
document.adminform.method="POST";
document.adminform.action="/course/update";
	document.adminform.submit()
}
function fnnselect(j,courseid,status,size)
{
	k=j;
	//alert(j+"--"+courseid+"--"+status);
	for(var i=0;i<size;i++)
	{
	document.adminform.coursestatus[i].disabled=true;
	}
	document.adminform.coursestatus[j].disabled=false;
	//alert(document.adminform.coursestatus[j].selectedText)
	document.adminform.Hidcourseid.value=courseid;
	
}function fnnlogout()
{
	
	document.adminform.method="POST";	
	document.adminform.action="/logout";
	document.adminform.submit();
}
function trim(str) {
    return str.replace(/^\s+|\s+$/g,"");
}
function fnndownload()
{
	document.adminform.action="/report/{<%=uid%>}/download";
	document.adminform.submit();

}
/* function fnnprint()
{
	document.adminform.action="/user/cert";
	document.adminform.submit();

} */
function fnnaddcat(){
	
 if(trim(document.adminform.catname.value)=="")
	{
	alert("Please enter catalog Name");
	document.adminform.catname.focus();
	
	}
else if(trim(document.adminform.CategoryDescp.value)=="")
{
alert("Please enter Catalog Description");
document.adminform.CategoryDescp.focus();

}else{
	document.adminform.method="POST";
	document.adminform.action="/catalog/Add";
	document.adminform.submit();
}
}
function trim(str) {
    return str.replace(/^\s+|\s+$/g,"");
}
function fnnaddcourse()
{
	//alert(document.adminform.catalog_id.value)
	if(document.adminform.catalog_id.value=="-1")
		{
		alert("Please select Catalog");
		document.adminform.catalog_id.focus();
		}
	else if(trim(document.adminform.coursename.value)=="")
		{
		alert("Please enter Course Name");
		document.adminform.coursename.focus();
		
		}
	else if(trim(document.adminform.courseDescp.value)=="")
	{
	alert("Please enter Course Description");
	document.adminform.courseDescp.focus();
	
	}
	else if(document.adminform.coursestatus.value=="-1")
	{
	alert("Please select status");
	document.adminform.coursestatus.focus();
	
	}
	else{
	document.adminform.method="POST";
	document.adminform.action="/course/Add";
	document.adminform.submit();
	}
}

</SCRIPT>
</head>
<BODY>
<%-- <h1>
	Hello world!  
</h1>

<P>  The time on the server is ${REPORT_DOWNLOAD_URL}. </P>
 --%>
<form name="adminform" action="/report/new">
<%!

String url="";
%>
<% 
if(request.getAttribute("REPORT_DOWNLOAD_URL")!=null){
url	=	request.getAttribute("REPORT_DOWNLOAD_URL").toString();
System.out.println("downloadurl.."+url);
}

%>
<TABLE id="headertabl" BORDER="0" width="100%" class="table-bordered">
<TR height=50 ><TD nowrap align=center class=text10 >
<b><font size="5" color="#2f2f2f" class="bigfont">Learning & Development</font></b></TD>
<td align=center><font size="3" class="bigfont">Welcome ${user}.
<br><a href="javascript:fnnlogout()" class="bigfont">Log Out</a>
</font></td>
</TR>
</div>
</TABLE>
<ul class="nav nav-tabs">
  <li class="active"><a data-toggle="tab" href="#home">UserList</a></li>
  <li><a data-toggle="tab" href="#menu1">Edit Course</a></li>
  <li><a data-toggle="tab" href="#menu2">Catalog Creation</a></li>
  <li><a data-toggle="tab" href="#menu3">Course Creation</a></li>
  
</ul>

<div class="tab-content">
  <div id="home" class="tab-pane fade in active">
    <br><font color=red><h3>${status}</h3>
    <% if(url!=null && url!=""){%>
    <a href="<%=url%>" target="_blank">View report</a>
    <%}%>
    </font>
    <%-- <TABLE BORDER=0 align=center width=80% >
<TR><TD nowrap align=center><font color=red><h3>${status}</h3></font></TD></TR>
</TABLE> --%>
  <!-- <input type=button value="download User Report" onclick="javascript:fnndownload()"> -->  
  <TABLE id="tablemain" BORDER=0 width="50%" class="table-bordered">
  <TR  bgcolor="#G7750F"><b><TD><font size="3" color="white">User List from MemCache Service</font></TD></b><td>
  <input type=submit value="Generate User Report"></td></TR>
  <TR  bgcolor="#G7750F"><b><TD><font size="2" color="white">UserName</font></TD><TD><font size="2" color="white">Email-ID</TD></font></b></TR>
  <%
  ConcurrentHashMap<String, String> userlist	=	null;
  userlist	=	(ConcurrentHashMap<String, String>)request.getAttribute("userList");
  System.out.println("userlist in jsp.."+userlist);
  Enumeration e = userlist.keys();
  while (e.hasMoreElements())
  {
	  Object k=e.nextElement();
	  Object v=userlist.get(k);
	%>
	<TR><TD><%=k%></TD><TD><%=v%></TD></TR>
	  <%
	  System.out.println("userlist elements in jsp.."+k+"::"+userlist.get(k));
  }
  %>
  </TABLE>
  </div>
  <div id="menu1" class="tab-pane fade">
   <%
    ArrayList<CourseBean> cl = null;//new ArrayList<CourseBean>();
    if(request.getAttribute("courseInfoList")!=null)
    	cl	=	(ArrayList<CourseBean>)request.getAttribute("courseInfoList");
    System.out.println("course list in jsp"+cl);
    if(cl!=null)
    {
    	%><br><br>
    	<TABLE border=0 width=70% align=center id="tablemain" class="table-bordered"><TR  ><TD>&nbsp;</TD><TD><font size="3" color="white">CourseID</font></TD><TD><font size="3" color="white">Course Name</font></TD><TD><font size="3" color="white">Course Status</TD></font></TR>
    	<%
    	if(cl.size()>0){
    	CourseBean cb = null;
    	int clize=cl.size();
    	for(int i=0;i<clize;i++)
    	{
    		cb = (CourseBean)cl.get(i);
    		%>
    		<TR><TD><INPUT TYPE=RADIO name="course_id" VALUE="<%=cb.getCourse_id()%>" onclick="javascript:fnnselect(<%=i%>,<%=cb.getCourse_id()%>,'<%=cb.getCourse_Status()%>',<%=clize%>)"></TD><TD><%=cb.getCourse_id()%></TD><TD><%=cb.getCourse_name()%></TD><TD>
    		<select name="coursestatus" disabled><option value="ACTIVE">ACTIVE</option><option value="INACTIVE">INACTIVE</option></select></TD></TR>
    		<%
    }%>
    <tr><td COLSPAN=4 ALIGN=CENTER><input type=button onclick="fnnupdate()" value="Update"></td></tr>
    </TABLE>
    <% 	}
    	else
    	{%>
    	 <tr><td COLSPAN=4 ALIGN=CENTER><font color=red><h3>No Courses found</h3></td></tr>
    </TABLE>
    <%} 		
    }
    %>
    
    <input type=hidden name="Hidcourseid">
    <input type=hidden name="Hidcoursestatus">  
    </div>
  <div id="menu2" class="tab-pane fade">
       <BR><BR>
    <TABLE id="bordertbl" width="35%"><TR><TD class="pull-right">Catalog Name</TD><TD><input type=text name="catname" maxlength=10></TD></TR>
    <TR><TD class="pull-right">Catalog Description</TD><TD><textarea row=5 cols=24 name="CategoryDescp"></textarea></TD></TR>
    <!-- <TR><TD>Catalog Status</TD><TD><select name="catstatus"><option value="Active">Active</option><option value="InActive">InActive</option></select></TD></TR> -->
    <tr><td colspan=2 ALIGN=center><input type=button value="Submit" onclick="javacript:fnnaddcat()" class="marleft"></td></tr>
    </TABLE>
  </div>
  <div id="menu3" class="tab-pane fade">
  
  </TABLE>
    <BR><BR>
    <TABLE id="bordertbl" width="35%" >
    <TR><TD class="pull-right">Select Catalog</TD><TD><select name="catalog_id" >
    <option value=-1>Select Catalog</option>
  <%
  HashMap<Integer, String> catlist	=	null;
  catlist	=	(HashMap<Integer, String>)request.getAttribute("catList");
  System.out.println("catlist in jsp.."+catlist);
  if(catlist!=null)
  {
	  Iterator it = catlist.entrySet().iterator();
	  while (it.hasNext())
	  {
		  Map.Entry pair = (Map.Entry)it.next();
		  Object k=pair.getKey();
		  Object v=pair.getValue();
		%><option value=<%=k%>><%=v%></option>
		  <%
		  System.out.println("catlit elements in jsp.."+k+"::"+v);
	  }
  }
   %>
 </select></TD></TR>
    <TR><TD class="pull-right">Course Name</TD><TD><input type=text name="coursename" maxlength=20></TD></TR>
    <TR><TD class="pull-right">Course Description</TD><TD><textarea row=5 cols=23 name="courseDescp"></textarea></TD></TR>
    <TR><TD class="pull-right">Course Status</TD><TD><select name="coursestatus"><option value="ACTIVE">ACTIVE</option><option value="INACTIVE">INACTIVE</option></select></TD></TR>
     <tr><td colspan=2 ALIGN=center><input type=button value="Submit" onclick="javacript:fnnaddcourse()" class="marleft"></td></tr>
    </TABLE>
  </div>
</div>

<br><br><br><br><br><TABLE id="headertabl" BORDER=1 width="100%">
<TR bgcolor="#G7750F" height=50><TD nowrap align=center ></TD></TR>
</TABLE>
<INPUT TYPE=hidden name="event" value="admin">
<INPUT TYPE=hidden name="user" value="${user}">
</center>
</form>
</body>
</html>