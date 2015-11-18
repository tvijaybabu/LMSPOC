<%@page import="java.util.*,com.hcl.util.CourseBean,com.hcl.util.UserCourse"%>
<%@ page session="false" import="java.util.concurrent.ConcurrentHashMap"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Cloud Foundry sample Application -Admin screen</title>

<SCRIPT>
var k;
var sz;
function fnnupdate()
{
//	for(var i=0;i<)SELRM_STATUS
//alert(k)
//alert(sz)
if(k==undefined || sz==undefined)
{
alert("Please select atleast one record");
return;
}
if(sz==1)
	document.managerform.RM_Reason.value=document.managerform.REASON.value;
else
	document.managerform.RM_Reason.value=document.managerform.REASON[k].value;

document.managerform.RM_status.value=document.managerform.selrmstatus[k].value;
//	document.managerform.RM_Reason.value=document.managerform.REASON[k].value;
//alert(document.managerform.Hidcoursestatus.value)
//alert(document.managerform.Hidcourseid.value)
if(trim(document.managerform.RM_Reason.value)=="")
	{
	alert("Please enter Reason");
	//document.managerform.catname.focus();
	
	}
else{
	document.managerform.method="POST";
	document.managerform.action="/manager/approve";
	document.managerform.submit();
}
}
function fnnselect(j,SELVALUE,size)
{
	k=j;
	sz=size;
//	alert(j+"--"+courseid+"--"+status);
	var userid=trim(SELVALUE.split("#")[0]);
	var courseid=trim(SELVALUE.split("#")[1]);
	document.managerform.userid.value=userid;
	document.managerform.courseid.value=courseid;
}function fnnlogout()
{
	
	document.managerform.method="POST";	
	document.managerform.action="/logout";
	document.managerform.submit();
}
function trim(str) {
    return str.replace(/^\s+|\s+$/g,"");
}
function fnndownload()
{
	
	//document.managerform.submit();

}
/* function fnnprint()
{
	document.managerform.action="/user/cert";
	document.managerform.submit();

} */
function fnnaddcat(){
	
 if(trim(document.managerform.catname.value)=="")
	{
	alert("Please enter catalog Name");
	document.managerform.catname.focus();
	
	}
else if(trim(document.managerform.CategoryDescp.value)=="")
{
alert("Please enter Catalog Description");
document.managerform.CategoryDescp.focus();

}else{
	document.managerform.method="POST";
	document.managerform.action="/catalog/Add";
	document.managerform.submit();
}
}
function trim(str) {
    return str.replace(/^\s+|\s+$/g,"");
}
function fnnaddcourse()
{
	//alert(document.managerform.catalog_id.value)
	if(document.managerform.catalog_id.value=="-1")
		{
		alert("Please select Catalog");
		document.managerform.catalog_id.focus();
		}
	else if(trim(document.managerform.coursename.value)=="")
		{
		alert("Please enter Course Name");
		document.managerform.coursename.focus();
		
		}
	else if(trim(document.managerform.courseDescp.value)=="")
	{
	alert("Please enter Course Description");
	document.managerform.courseDescp.focus();
	
	}
	else if(document.managerform.coursestatus.value=="-1")
	{
	alert("Please select status");
	document.managerform.coursestatus.focus();
	
	}
	else{
	document.managerform.method="POST";
	document.managerform.action="/course/Add";
	document.managerform.submit();
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
<form name="managerform" >
<%!

String url="";
%>
<% 
if(request.getAttribute("REPORT_DOWNLOAD_URL")!=null){
url	=	request.getAttribute("REPORT_DOWNLOAD_URL").toString();
System.out.println("downloadurl.."+url);
}

%>
<TABLE BORDER=1 width="100%">
<TR bgcolor="#G7750F" height=50 ><TD nowrap align=center class=text10 >
<b><font size="5" color="white">Learning & Development</font></b></TD>
<td align=right><font size="3" color="white">Welcome ${user}.
<br><a href="javascript:fnnlogout()">Log Out</a>
</font></td>
</TR>
</TABLE>
<ul class="nav nav-tabs">
  <li class="active"><a data-toggle="tab" href="#menu1">Home</a></li>
</ul>

<div class="tab-content">
    <div id="menu1" class="tab-pane fade in active">
    <br><font color=red><h3>${status}</h3></font>
   <%
   ArrayList<UserCourse> cl = null;//new ArrayList<UserCourse>();
    
    if(request.getAttribute("pendinglist")!=null)
    	cl	=	(ArrayList<UserCourse>)request.getAttribute("pendinglist");
    System.out.println("course list in jsp"+cl);
    if(cl!=null)
    {
    	%><br><br>
    	<TABLE border=1 width=70% align=center><TR  bgcolor="#G7750F"><TD>&nbsp;</TD><TD><font size="3" color="white">UserID</font></TD><TD><font size="3" color="white">Catalog Name</font></TD><TD><font size="3" color="white">Course Name</font></TD><TD><font size="3" color="white">Reporting Manager Status</TD></font><TD><font size="3" color="white">Select Status</TD></font><TD><font size="3" color="white">Reason</TD></font></TR>
    	<%
    	if(cl.size()>0){
    		UserCourse cb = null;
    	int clize=cl.size();
    	for(int i=0;i<clize;i++)
    	{
    		cb = (UserCourse)cl.get(i);
    		%>
    		<TR><TD><INPUT TYPE=RADIO name="course_id" VALUE="<%=cb.getUser_id()%>#<%=cb.getCourse_id()%>" onclick="javascript:fnnselect(<%=i%>,this.value,<%=clize%>)"></TD><TD><%=cb.getUser_id()%></TD><TD><%=cb.getCat_name()%></TD><TD><%=cb.getCourse_name()%></TD><TD><%=cb.getRm_status()%></TD>
    		<TD><select name="selrmstatus" ><option value="Approved">Approved</option><option value="Rejected">Rejected</option></select></TD>
    		<TD><textarea NAME='REASON' maxlength=40 rows=2 cols=20></textarea></TD>
    		</TR>
<!--     		<select name="coursestatus" disabled><option value="ACTIVE">ACTIVE</option><option value="INACTIVE">INACTIVE</option></select></TD></TR>
 -->    		<%
    }%>
    <tr><td COLSPAN=7 ALIGN=CENTER><input type=button onclick="fnnupdate()" value="Update"></td></tr>
    </TABLE>
    <% 	}
    	else
    	{%>
    	 <tr><td COLSPAN=4 ALIGN=CENTER><font color=red><h3>No Records found</h3></td></tr>
    </TABLE>
    <%} 		
    }
    else{
    %>
    <font color=red><h3>No Records found</h3>
    <% }%>
    
    <input type=hidden name="Hidcourseid">
    <input type=hidden name="Hidcoursestatus">  
    </div>
  </div>
<br><br><br><br><br><TABLE BORDER=1 width="100%">
<TR bgcolor="#G7750F" height=50><TD nowrap align=center ></TD></TR>
</TABLE>
<INPUT TYPE=hidden name="event" value="admin">
<INPUT TYPE=hidden name="user" value="${user}">
<INPUT TYPE=hidden name="userid" value="">
<INPUT TYPE=hidden name="Mgrid" value="${userid}">
<INPUT TYPE=hidden name="courseid" value="">
<INPUT TYPE=hidden name="RM_status" value="">
<INPUT TYPE=hidden name="RM_Reason" value="">
</center>
</form>
</body>
</html>