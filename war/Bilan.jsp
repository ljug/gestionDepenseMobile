<%@page import="java.util.Formatter"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!--  /**
 * @author or Modified by  Kamal Mokh 8921f CNAM 2013 kamal.mokh@isae.edu.lb
 *
 */-->
<!DOCTYPE HTML>
<html>
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
<link href="http://www.gstatic.com/codesite/ph/17444577587916266307/css/ph_core.css" rel="stylesheet" type="text/css" />
 <link href="http://code.google.com/css/codesite.pack.04102009.css" rel="stylesheet" type="text/css" />
  <link  href="/css/styles.css" rel="stylesheet" type="text/css"   />
    <script  src='script/jquery-1.6.min.js'></script>
  <script   src='script/ajax.utilv6.js'></script>
  <style type="text/css"> 
    <title>Expenses Application v21</title>
        .style1
        {
            border-collapse: collapse;
            border-style: solid;
            border-width: 1px;
        }
        #Select1
        {
            width: 24px;
        }
        .style4
        {
            text-align: center;
            color: #000000;
        }
        .style5
        {
            width: 100%;
            border:1px;
        }
        .style6
        {
            text-align: center;
            color: #0000FF;
        }
        .style7
        {
            text-align: center;
            color: #FF0000;
        }
        .style8
        {
            text-align: center;
            font-weight: bold;
        }
        .style9
        {
            text-align: center;
            color: #000099;
        }
        
    </style>
</head>
  <body>
 <header>
     <h1 class="page_title">Expenses Application v21 from home</h1>
  
 <!-- tabs --> 
 <div id="tabs" class="gtb">
      <a id="home" href="http://cnamsmb215html.appspot.com/#home" class="tab">Home</a>	   
		
	  <a id="category" href="http://cnamsmb215html.appspot.com/#category" class="tab">Category</a> 
	  <a id="expense" href="http://cnamsmb215html.appspot.com/#expense" class="tab">Expense</a>	  
	  <a id="income" href="http://cnamsmb215html.appspot.com/#income" class="tab">Income</a>	 
	  <a id="bilan" href="http://cnamsmb215html.appspot.com/Bilan.jsp" class="tab">Bilan</a> 
	  <div class="gtbc"></div>
  </div>
    </header>
	<nav>
			<ul>
				<li><a href="http://cnamsmb215html.appspot.com/#category">Category</a></li>
				<li><a href="http://cnamsmb215html.appspot.com/#expense" >Expense</a></li>
					<li><a href="http://cnamsmb215html.appspot.com/#income" >Income</a></li>
				<li><a href="http://cnamsmb215html.appspot.com/Bilan.jsp" >Bilan</a></li>
				
					<li>
					<ul>
					
					<li><a href="http://cnamsmb215.appspot.com/" >First datastore Draft Version</a></li>
					<li><a href="http://kamalmokhthefirstdatastore.appspot.com/" >helllo appengine</a></li>
					<li><a href="http://kamalmokhweb.appspot.com/Login.jsp" >learn app engine login works</a></li>
				
					
					
					</ul>
					
					</li>
					<li><a href="http://kamalmokhweb.appspot.com/index.html">HTML 53</a></li>
			</ul>
		</nav>	

<article>

<section>

    <table align="center" class="style1">
        <tr>
            <td colspan="3">
                <b>Account</b>
            </td>
            <td colspan="3">
               
 <%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    String TheCatCAT="";
    try
    {
    	
    
    	
    	TheCatCAT=request.getQueryString().substring(2);
    //	TheCatCATrequest.getParameter("sex") ; 	
    	
    }
    catch(Exception e)
    {
    	TheCatCAT="All";
    }
    
    
    if (user != null) {
      pageContext.setAttribute("user", user);
%>
<p> <%= user.getNickname() %>  (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
    } else {
%>
<p> 
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
 </p>
<%
    }
%>

</td>
        </tr>
        <tr>
            <td colspan="3">
                Select</td>
            <td colspan="3">
                <select id="SelectCategory" name="D1" onchange="window.location ='http://cnamsmb215html.appspot.com/Bilan.jsp?c='+this.value;">
                    <option>CAR</option>
                     <option>Msrouf l bait</option>
                        <option selected >All</option>
                </select></td>
        </tr>
        <tr>
            <td class="style4" colspan="6">
                <input id="Radio1" checked="checked" name="R1" type="radio" value="V1" />All<input id="Radio2"  name="R2" type="radio" value="V1" />Weekly<input id="Radio4"   name="R4" type="radio" value="V1" />Monthly<input id="Radio3"  name="R3" type="radio" value="V1" />Yearly</td>
        </tr>
        <tr>
            <td class="style4">
                From</td>
            <td colspan="2">
                <input id="Text1" type="date" /></td>
            <td colspan="2">
                To</td>
            <td>
                <input id="Text2" type="date" /></td>
        </tr>
       
        <tr>
            <td class="style6">
                Expense</td>
            <td colspan="2">
                &nbsp;</td>
            <td colspan="2">
                &nbsp;</td>
            <td>
                &nbsp;</td>
        </tr>
        <tr>
            <td class="style4">
                &nbsp;</td>
            <td colspan="5">
                <table class="style5">
                    <tr>
                    <td>
                            Category</td>
                        <td>
                            Description</td>
                        <td>
                            Date</td>
                        <td>
                            Amount</td>
                    </tr>
                    
                    <%
                    Double allexp=new Double(0);
                    Double allincome=new Double(0);
                    Double balance=new Double(0);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key guestbookKey = KeyFactory.createKey("category", "CAR");
    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Greetings belonging to the selected Guestbook.
    Query query = new Query("Expense").addSort("datecreated", Query.SortDirection.DESCENDING);
    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
    if (greetings.isEmpty()) {
        %>
        <tr><td>
                            No Data</td>
                        <td>
                            No Data</td>
                        <td>
                            &nbsp;</td>
                        <td>
                            &nbsp;</td>
                    </tr>
        <%
    } else {
        %>
        
        <%
        for (Entity greeting : greetings) { 
        	if (TheCatCAT=="All"||TheCatCAT==null)
        	{
        	
        	
        	
        	allexp+=Double.parseDouble(greeting.getProperty("price").toString());
        	 
        	
        %>
        <tr>
        
        <td>
                            <%= greeting.getProperty("category")%> </td>
        <td>
                            <%= greeting.getProperty("name")%> </td>
                        <td>
                            <%= greeting.getProperty("datecreated")%></td>
                        <td>
                            <%= greeting.getProperty("price")%></td>
                    </tr>
         <%}
        	else
        	{
        	//response.getWriter().println("in datastore =|"+greeting.getProperty("category") + "|");
        	//	response.getWriter().println("in querystring =|" + TheCatCAT + "|");
        	//	response.getWriter().println("is equal"+greeting.getProperty("category").toString().equalsIgnoreCase(TheCatCAT));
        		
        		if (greeting.getProperty("category").toString().equalsIgnoreCase(TheCatCAT))
        		{
        			allexp+=Double.parseDouble(greeting.getProperty("price").toString());%>
        			 <tr>
        		        
        		        <td>
        		                            <%= greeting.getProperty("category")%> </td>
        		        <td>
        		                            <%= greeting.getProperty("name")%> </td>
        		                        <td>
        		                            <%= greeting.getProperty("datecreated")%></td>
        		                        <td>
        		                            <%= greeting.getProperty("price")%></td>
        		                    </tr>
        		                    <%
        		}
        		else
        		{
        			response.getWriter().print(greeting.getProperty("category"));
        		}
        	}
        	
        
        
        }
        
    
    
    
    
    
    }
    
    %>
         
                    
                    
                </table>
            </td>
        </tr>
           <tr>
            <td class="style6">
                Income</td>
            <td colspan="2">
                &nbsp;</td>
            <td colspan="2">
                &nbsp;</td>
            <td>
                &nbsp;</td>
        </tr>
        <tr>
            <td class="style4">
                &nbsp;</td>
            <td colspan="5">
                <table class="style5">
                    <tr>
                      <td>
                          Category</td>
                        <td>
                            Description</td>
                        <td>
                            Date</td>
                        <td>
                            Amount</td>
                    </tr>
                    
                    <%
   
    
    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Greetings belonging to the selected Guestbook.
    Query query1 = new Query("Income").addSort("datecreated", Query.SortDirection.DESCENDING);
    List<Entity> Incomes = datastore.prepare(query1).asList(FetchOptions.Builder.withLimit(5));
    if (Incomes.isEmpty()) {
        %>
        <tr>  <td>
                            No Data</td>
                        <td>
                            No Data</td>
                        <td>
                            &nbsp;</td>
                        <td>
                            &nbsp;</td>
                    </tr>
        <%
    } else {
        %>
        
        <%
        for (Entity greeting : Incomes) {
        	
        	String s="";
        	
        
        	allincome+=Double.parseDouble(greeting.getProperty("priceincome").toString());
        	
        	%>
        <tr>
        <td>
                            <%= greeting.getProperty("categoryincome")%> </td>
        <td>
                            <%= greeting.getProperty("name")%> </td>
                        <td>
                            <%= greeting.getProperty("datecreated")%></td>
                        <td>
                            <%= greeting.getProperty("priceincome")%></td>
                    </tr>
         <%}} %>
         
                    
                    
                </table>
            </td>
        </tr>
        
        <tr>
            <td class="style8" colspan="2">
                Balance</td>
            <td class="style9" colspan="2">
                Income Total</td>
            <td class="style4" colspan="2">
                Expense Total</td>
        </tr>
        <tr>
            <td class="style10" colspan="2">
            <%balance=allincome-allexp; %>
                <%=balance.toString() %> </td>
            <td class="style4" colspan="2">
                <%=allincome.toString() %></td>
            <td class="style4" colspan="2">
               <%=allexp.toString() %></td>
        </tr>
    </table>


</section>


 
 <section>

    <form action="/bilan" method="post">
      <div><textarea name="content" rows="3" cols="60"></textarea></div>
      <div><input type="submit" value="Get Bilan" /></div>
       
    </form>
    </section>
    </article>
<footer>
			<h4>About Mobile Accounting SMB215 - Kamal Mokh, Ahmad Shaaban 
			<p>CNAM LIBAN 2013 ©</p>
			<p>Best Viewed HTML5 with :Safari, Chrome and Opera.</p></h4>
		</footer>
  </body>
</html>