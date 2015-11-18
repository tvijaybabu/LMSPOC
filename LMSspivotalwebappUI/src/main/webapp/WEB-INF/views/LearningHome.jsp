<%@page import="com.hcl.util.UserCourse"%>
<%@page import="com.hcl.util.CourseBean"%>
<%@ page import="java.util.*" session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/resources/css/emc-styles.css" type="text/css">
<title>Cloud Foundry sample Application -User screen</title>
<%!
String url="";
%>
<% 
String user	=	"";
String pwd	=	"";
String role	=	"";
String email=	"";
String userid=	"";
String rmid	=	"";
String reportingManager="";
ArrayList cl = null;
if(request.getAttribute("courselist")!=null)
	cl=(ArrayList)request.getAttribute("courselist");

System.out.println("cl=====>.."+cl);
//System.out.println("cl=====>.."+cl.size());
if(request.getAttribute("user")!=null)
	user=request.getAttribute("user").toString();
if(request.getAttribute("pwd")!=null)
	pwd=request.getAttribute("pwd").toString();
if(request.getAttribute("role")!=null)
	role=request.getAttribute("role").toString();
if(request.getAttribute("email")!=null)
	email=request.getAttribute("email").toString();
if(request.getAttribute("userid")!=null)
	userid=request.getAttribute("userid").toString();
if(request.getAttribute("rmid")!=null)
	rmid=request.getAttribute("rmid").toString();

if(request.getAttribute("ReportingManager")!=null)
	reportingManager=request.getAttribute("ReportingManager").toString();

HashMap Ml = new HashMap();
if(request.getAttribute("ManagerMap")!=null)
	Ml=(HashMap)request.getAttribute("ManagerMap");


String ctxpath	=	request.getContextPath();
String error="";
if(request.getParameter("Error")!=null){
error	=	request.getParameter("Error");
}
if(error.equalsIgnoreCase("Failure")){
	error	=	"User Authentication Failed ,Please Try with Proper EmployeeNo and PassWord";
}
 
	if(request.getAttribute("REPORT_DOWNLOAD_URL")!=null){
	url	=	request.getAttribute("REPORT_DOWNLOAD_URL").toString();
	System.out.println("downloadurl.."+url);
	}
	HashMap catmap=new HashMap<String,String>();
	ArrayList<UserCourse> usercourselist = null;//new ArrayList<UserCourse>();
	if(request.getAttribute("usercourselist")!=null)
		usercourselist = (ArrayList<UserCourse>)request.getAttribute("usercourselist");
	
	System.out.println("usercourselist in jsp.."+usercourselist);
%>

<SCRIPT>
window.name="Lhome";
function fnnpop()
{
	//alert("test buddy...");
	//alert(document.userform.USERID.value);
	//alert(document.userform.compcourseid.value);
	document.userform.method="POST";
	 document.userform.action="/course/complete";
	 document.userform.submit();
	
	}

function fnnsavecourse()
{
	var courselen=document.userform.UserCourses.length;
	//alert(courselen);
	var count=0;
	if (courselen==undefined)
		{
		if(document.userform.UserCourses.checked==true)
			{
			count=1;
			}
		}
	
	for(var i=0;i<courselen;i++)
		{
		if(document.userform.UserCourses[i].checked==true)
			{
			count++;
			}
		}
	//alert("count"+count)
if(count==0)
	{
	alert("Please select atlease one course");
	}else
		{
	document.userform.method="POST";
	document.userform.action="/course/save";
	document.userform.submit(); }
}
function fnnprint(catname,coursename,comp_date,user)
{
	document.userform.method="POST";
	//document.userform.action="/report/cert";
	//document.userform.action="/user/cert";
	if(comp_date==""){
	//	comp_date= new date();
	
		var today = new Date();
	    var dd = today.	getDate();
	    var mm = today.getMonth()+1; //January is 0!

	    var yyyy = today.getFullYear();
	    if(dd<10){
	        dd='0'+dd
	    } 
	    if(mm<10){
	        mm='0'+mm
	    } 
	    comp_date = dd+'/'+mm+'/'+yyyy;
		
	}
	window.open("/user/cert?catname="+catname+"&coursename="+coursename+"&compdate="+comp_date+"&user="+user);
	//document.userform.submit();
}
function fnnstart(courseid,coursename)
{
	var pagenum=document.userform.Page.value;
	document.userform.compcourseid.value=courseid;
window.open("/course/Open?coursename="+coursename+"&Page="+pagenum,"_blank","resizable=no,scrollbars=auto,titlebar=no,toolbar=no,width=800,height=800");
}
function fnnsearch()
{
	document.userform.method="POST";
	document.userform.action="/course/search";
	document.userform.submit();	
	
}
function fnnload()
{
document.userform.role.selectedText="<%=role%>";
}
function fnnlogout()
{
	
	
//	alert(path);
	//document.userform.event.value="login";
	//document.userform.action=path+"/SubmitData";
	//document.userform.submit();
	document.userform.method="POST";
	document.userform.action="/logout";
	document.userform.submit();
}
function fnnUpdateUser()
{
/* 	if(trim(document.userform.UserName.value)==""){
	alert("Fields cannot be blank");
	document.userform.UserName.focus();
	return false;
	}
 */	if(trim(document.userform.oldpassword.value)==""){
		alert("Fields cannot be blank");
		document.userform.oldpassword.focus();
		return false;
	}
	if(trim(document.userform.Email.value)==""){
		alert("Fields cannot be blank");
		document.userform.Email.focus();
		return false;
	}
	document.userform.method="POST";
	document.userform.event.value="updateuser";
	document.userform.action="/user/update";
	document.userform.submit();
}
function trim(str) {
    return str.replace(/^\s+|\s+$/g,"");
}
</SCRIPT>
</head>
<BODY>
<form method="GET" name="userform" onload="javacript:fnnload()">

<TABLE id="headertabl" BORDER=1 width="100%">
<TR bgcolor="#G7750F" height=50 font-color="white"><TD nowrap align=center class=text10 >
<b><font size="5" color="white" class="bigfont">Learning & Development</font></b></TD>
<td align=center><font size="3" color="white" class="bigfont">Welcome ${user}.<br><a href="javascript:fnnlogout()" class="bigfont">Log Out</a></font></td>
</TR>
</TABLE>
<div class="container">
 <ul class="nav nav-tabs">
  <li class="active"><a data-toggle="tab" href="#home">Home</a></li>
  <li><a data-toggle="tab" href="#menu1">DevelopmentPlan</a></li>
   <li><a data-toggle="tab" href="#menu2">Edit Profile</a></li>
</ul>
<div class="tab-content">
<font color=red><h2>${result}</h2></font>
	<div id="home" class="tab-pane fade in active">
    <br><br>
    <TABLE border=0 width=60% id="bordertblhome"><TR bgcolor="#G7750F" ><TD colspan=4><font size="2" color="white" class="fontWeightBold">Catalog Search</font></TD></TR>
    <tr><TD colspan=4>&nbsp;</TD></tr>
    <TR ><TD><select name="Cat_id">
    <option value="-1">Select Catalog</option>
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
	%>
		<option value=<%=k%>><%=v%></option>
 <%
		  System.out.println("catlit elements in jsp.."+k+"::"+v);
	  }
  }
  %>
    </select></TD>
    <TD>Enter Course Name</TD>
    <TD><INPUT TYPE=TEXT NAME="course_name" maxlength=20></TD><TD align="middle"><input type=button value="Search" onclick="javascript:fnnsearch()"></TD>
    </TR>
    <tr><TD colspan=4>&nbsp;</TD></tr>
    <TR bgcolor="#G7750F" ><TD colspan=4>&nbsp;</TD></TR>
    </TABLE>
    
    
    <br><br>
    <%
    if(cl!=null){
    	
    	if(cl.size()>0){
    %>
	<h3>Search Results</h3>
    	<TABLE border=0 id="tableLearning" width=60% class="table-bordered">
   
    <TR bgcolor="#G7750F"><td>&nbsp;</td><td><font size="2" color="white">Catalog Name</font></td><td><font size="2" color="white">Course Name</font></td></TR>
    <%-- <%Iterator catit = catmap.entrySet().iterator();
	  while (catit.hasNext())
	  {
		  Map.Entry pair1 = (Map.Entry)catit.next();
		  Object k1=pair1.getKey();
		  ArrayList<String> v1=(ArrayList<String>)pair1.getValue();
		  
		  for(int i=0;i<v1.size();i++)
		  {
	%>
     --%><%-- <tr><td><input type="checkbox" value=""></td><TD><%=k1%></TD><TD><%=v1.get(i)%></TD></tr> --%>
    <%
    for(int i=0;i<cl.size();i++)
    {
    	CourseBean cb = (CourseBean)cl.get(i);
    	
    	%>
   <tr><td><input name="UserCourses" type="checkbox" value="<%=cb.getCat_id()%>#<%=cb.getCourse_id()%>"></td><TD><%=cb.getCat_name()%></TD><TD><%=cb.getCourse_name()%></TD></tr>
    
    <%
    }%>
    <tr><TD colspan=3 align=center><input type=button value="Add Courses" onclick="javacript:fnnsavecourse()"></TD></tr>
    </TABLE>
	<%}else{
		%>
		<TABLE border=0 id="bordertblhome" width=60%>
	    <TR bgcolor="#G7750F" ><TD colspan=3><font size="2" color="white">Search Results</font></TD></TR>
	    <TR><TD colspan=3><b><font size="2" color="red">No Results found</font></b></TD></TR>
	    </TABLE>
	    <%}}
	    %>
	
    </div>
  <div id="menu1" class="tab-pane fade">
    <h3>My Plans</h3>
    <TABLE><TR><TD nowrap>
    Development can be carrier oriented and/or can expand<br>
    skills while focused on current or future role.Employees are accountable for planning and managing<br>
    their development activities and progress,engaging their manager's support. Use the bottom portion <br>
    of the screen to go through development activities to your development plan as per added courses.
    </TD><td><!-- <img src="/resources/crash.jpg" /></td> -->
    <!-- <TR><TD nowrap><br>Development Summary.</TD></TR>
    <TR><TD nowrap><TextArea rows=6 cols=60 maxlength="100"></TextArea></TD></TR> -->
    </TR>
    <TR><TD nowrap>View the activities that are on your current plan.Goals that are visible only to assignees are not included in the plans. </TD></TR>
    </TABLE><br><br>
    <div class="container">
		 <ul class="nav nav-tabs">
		  <li class="active"><a data-toggle="tab" href="#activities">Activities List</a></li>
		  
		</ul>
		<div class="tab-content">
		<div id="activities" class="tab-pane fade in active">
		<TABLE width=100% border=0 id="tablemain" class="table-bordered">
		<TR bgcolor="#G7750F" ><TD><font size="2" color="white">Catalog Name</font></TD><TD><font size="2" color="white">Course Name</font></TD><TD><font size="2" color="white">Completion Date</font></TD><TD><font size="2" color="white">Status</font></TD><TD><font size="2" color="white">Approval Status</font></TD><TD><font size="2" color="white">Reason</font></TD><TD>&nbsp;</TD><TD>&nbsp;</TD></font></TR>
		<% 
		String startdisabld="disabled";
		String certdisabld="disabled";
		String compdate="NC";
		String compcolor="bgcolor='#G7750F'";
		if(usercourselist!=null){
			UserCourse uc =null;
		
			for(int i=0;i<usercourselist.size();i++)
			{
				
			
				uc = usercourselist.get(i);	
				System.out.println("uc.getCourse_type()=====>.."+uc.getCourse_type());
				System.out.println("uc.getStatus()=====>.."+uc.getStatus());
				if((uc.getCourse_type().equalsIgnoreCase("ACTIVE"))&& (uc.getRm_status().equalsIgnoreCase("Approved")) ){
					startdisabld="";
					//System.out.println("1");	
				}
			else
					startdisabld="disabled";
				
				if((uc.getStatus().equalsIgnoreCase("completed"))){
					
					startdisabld="disabled";
					certdisabld="";
					//System.out.println("2");
					compcolor="bgcolor='#G7750F'";
				}
				else{
					certdisabld="disabled";
					compcolor="bgcolor=red";
					//System.out.println("3");
				}
				System.out.println("uc.getCompletiondate()=====>.."+uc.getCompletiondate()+"--len--"+uc.getCompletiondate().trim().length());
				if(uc.getCompletiondate().trim().length()>4)
					{System.out.println("condition................");
					compdate=uc.getCompletiondate().trim();
					}
				else{compdate="NC";}
					
		%>
		<TR><TD><%=uc.getCat_name()%></TD><TD><%=uc.getCourse_name()%></TD><TD><%=compdate%></TD>
<TD <%=compcolor %>><%=uc.getStatus()%></TD><TD><%=uc.getRm_status()%></TD><TD><%=uc.getRm_reason()%></TD>
		<td><input type=button <%=startdisabld%> value="Start" onclick="javascript:fnnstart(<%=uc.getCourse_id()%>,'<%=uc.getCourse_name()%>')"></td>
		<td><input type=button <%=certdisabld %> value="Create Certificate" onclick="javascript:fnnprint('<%=uc.getCat_name()%>','<%=uc.getCourse_name()%>','<%=uc.getCompletiondate()%>','<%=user%>')">
		<% if(url!=null && url!=""){%>
    	<a href="<%=url%>" target="_blank">View Certificate</a>
    	<%}%>
    	</td>
		</TR>
		<% }}%>
		</TABLE>
		</div>
		</div>
   </div>
  </div>
	 <div id="menu2" class="tab-pane fade">
	 <br><br>
	 <TABLE BORDER=0 id="bordertbluserProfile" width="60%">
	 <TR bgcolor="#G7750F"><TD colspan=2><font size="2" color="white">User Profile</font></TD></TR>
	<TR><TD>User Name</TD><td>
	<INPUT TYPE=TEXT NAME="UserName" value="<%=user%>" maxlength=30 readOnly=true/>
	</TD></TR>
	<TR><TD> Old Password</TD><TD>
	<INPUT TYPE=password NAME="oldpassword" value="<%=pwd%>" readOnly=true maxlength=12/>
	</TD></TR>  
	<TR><TD> New Password</TD><TD>
	<INPUT TYPE=password NAME="Newpassword" value=""maxlength=12/>
	</TD></TR>
	<TR><TD> Retype New Password</TD><TD>
	<INPUT TYPE=password NAME="RetypeNewpassword" value=""maxlength=12/>
	</TD></TR>
	<TR><TD>Email-id</TD><TD>
	<INPUT NAME="Email" type="text" maxlength=30 value="<%=email%>"/>
	</TD></TR>
	<TR><TD>Select Role</TD><TD>
	<% 
	String v1="";
	String v2="";
	String v3="";
	if(role.equalsIgnoreCase("User")){
		
		v1="selected";
		v2="";
		v3="";
    	}
	else if(role.equalsIgnoreCase("Admin")){
			v2="selected";
    		v1="";
    		v3="";
    	}
	else{
		v3="selected";
		v1="";
		v2="";
	}
	
		
	
	%>
	<select NAME="Role">
	<!-- <option value='Manager'>Manager</option> -->
	<option value='Admin' <%=v2%>>Admin</option>
	<option value='User' <%=v1 %>>User</option>
	<option value='Manager' <%=v3  %>>Manager</option>
	</select>
	</TD></TR>
	<TR><TD>Select Reporting Manager</TD><TD>
	<select NAME="RMID">
	<option value='-1'>Select Reporting Manager</option>
	<% 
		if(Ml!=null){
		String selected="";	
		
			 Iterator it = Ml.entrySet().iterator();
			  while (it.hasNext())
			  {
				  Map.Entry pair = (Map.Entry)it.next();
				  Object k=pair.getKey();
				  Object v=pair.getValue();
				  if(rmid.equalsIgnoreCase(k.toString())){
					  selected="selected";
				  }
				  else
					  selected="";
			%>
				<option <%=selected%> value=<%=k%>><%=v%></option>
		<%
				  System.out.println(rmid+"Ml elements in jsp.."+k+"::"+v);
		}} %>
			
	</select>
</TD></TR>
	<TR><TD colspan=2 align=center><INPUT TYPE=BUTTON VALUE="Update" onclick="javascript:fnnUpdateUser()" class="lerninghomeUpdate"></TD>
	</TR>
	</TABLE>
	 </div>

</div>
</div>
<br><br><br><br><br>
<TABLE BORDER=1 width="100%" id="tablemain" class="table-bordered">
<TR bgcolor="#G7750F" height=50><TD nowrap align=center ></TD></TR>
</TABLE>
<INPUT TYPE=hidden name="event" value="login">
<INPUT TYPE=hidden name="Page" value="1">
<INPUT TYPE=hidden name="compcourseid" value="">
<INPUT TYPE=hidden name="USERID" value="<%=userid%>">
<INPUT TYPE=hidden name="ReportingManager" value="<%=reportingManager %>">
<INPUT TYPE=hidden name="RpMgr_id" value="<%=rmid%>">
</form>
</body>
</html>
