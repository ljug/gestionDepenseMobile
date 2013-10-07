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
<%@ page import="java.text.NumberFormat" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!--  /**
 * @author or Modified by  Kamal Mokh 8921f CNAM 2013 kamal.mokh@isae.edu.lb
 *https://github.com/pascalfares/gestionDepenseMobile
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
     <h1 class="page_title">Manage Expense Application v3.0</h1>
  
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
     <fmt:setLocale value="ar_LB"/>
      <c:set var="greetinggreetingexp" value="<%=0%>" /> 
 <c:set var="greetinggreeting" value="<%=0%>" /> 
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
<%
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
Query query5 = new Query("Category") ;
List<Entity> catlst = datastore.prepare(query5).asList(FetchOptions.Builder.withLimit(1500));

%>
<section>
<div >
 Select  Category
                <select id="SelectCategory" name="D1" onchange="window.location ='http://cnamsmb215html.appspot.com/Bilan.jsp?c='+this.value;">
                <% if (catlst.isEmpty()) {
                    %>
                    <option>---</option>
                    <%
                }
                else
                {
                    %>
                     <option selected >All</option>
                       <%for (Entity caa : catlst) {
                    	   String ccc="";
                    	   ccc=caa.getKey().toString();
                    	   ccc=ccc.substring(10,ccc.length()-2);
                    	   %>
                       
                    <option><%=ccc%></option>
                   <%}
                       }%>
                </select>
    <table align="center" cellspacing="0" cellpadding="2" border="0" style="border-collapse:collapse;" >
     <thead>
        <tr>
            <td colspan="3">
                <b>Account</b>
            </td>
            <td colspan="3">
            
 <%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    String TheCatCAT="";
    double currentexp=0;
    double currentincome=0;
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
           Category Selected is  </td>
            <td colspan="3">
            <h2> <%=TheCatCAT %> </h2>
              </td>
        </tr>
       
     </thead>   
        <tr>
            <td style="color:red;text-align:center;">
               All Expenses(-)</td>
            <td colspan="5">
               </td>
            
        </tr>
        <tr>
            <td class="style4">
                &nbsp;</td>
            <td colspan="5">
                <table class="style5">
                    <tr style="background-color:#ebeff9">
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
                    Double balancestring=new Double(0);
                    
  
    Key guestbookKey = KeyFactory.createKey("category", "CAR");
    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Greetings belonging to the selected Guestbook.
    Query query = new Query("Expense").addSort("datecreated", Query.SortDirection.DESCENDING);
    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1500));
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
        	currentexp=Double.parseDouble(greeting.getProperty("price").toString());
        	
        %>
        <tr>
        
        <td>
                            <%= greeting.getProperty("category")%> </td>
        <td>
                            <%= greeting.getProperty("name")%> </td>
                        <td>
                            <%= greeting.getProperty("datecreated")%></td>
                        <td>
                                             <c:set var="greetinggreetingexp" value="<%=currentexp%>" />
                                             <fmt:formatNumber  value="${greetinggreetingexp}" type="currency"/>  </td>
                    </tr>
         <%}
        	else
        	{
        	//response.getWriter().println("in datastore =|"+greeting.getProperty("category") + "|");
        	//	response.getWriter().println("in querystring =|" + TheCatCAT + "|");
        	//	response.getWriter().println("is equal"+greeting.getProperty("category").toString().equalsIgnoreCase(TheCatCAT));
        		
        		if (greeting.getProperty("category").toString().equalsIgnoreCase(TheCatCAT))
        		{
        			allexp+=Double.parseDouble(greeting.getProperty("price").toString());
        			
        	currentexp=Double.parseDouble(greeting.getProperty("price").toString());
        	%>
        			 <tr>
        		        
        		        <td>
        		                            <%= greeting.getProperty("category")%> </td>
        		        <td>
        		                            <%= greeting.getProperty("name")%> </td>
        		                        <td>
        		                            <%= greeting.getProperty("datecreated")%></td>
        		                        <td>
        		                         <c:set var="greetinggreetingexp" value="<%=currentexp%>" />
                                         <fmt:formatNumber  value="${greetinggreetingexp}" type="currency"/>
        		                            </td>
        		                    </tr>
        		                    <%
        		}
        		else
        		{
        			response.getWriter().print("");
        		}
        	}
        	
        
        
        }
        
    
    
    
    
    
    }
    
    %>
         
                    
                    
                </table>
            </td>
        </tr>
           <tr>
            <td style="color:green;text-align:center;">
              All Incomes (+)</td>
            <td colspan="5">
                &nbsp;</td>
             
        </tr>
        <tr>
            <td class="style4">
                &nbsp;</td>
            <td colspan="5">
                <table class="style5">
                    <tr style="background-color:#ebeff9">
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
    List<Entity> Incomes = datastore.prepare(query1).asList(FetchOptions.Builder.withLimit(1500));
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
        	if (TheCatCAT=="All"||TheCatCAT==null)
        	{
        	
        	String s="";
        	
        
        	allincome+=Double.parseDouble(greeting.getProperty("priceincome").toString());
        	
        	currentincome=Double.parseDouble(greeting.getProperty("priceincome").toString());
        	%>
        <tr>
        <td>
                            <%= greeting.getProperty("categoryincome")%> </td>
        <td>
                            <%= greeting.getProperty("name")%> </td>
                        <td>
                            <%= greeting.getProperty("datecreated")%></td>
                        <td>
                         <c:set var="greetinggreeting" value="<%=currentincome%>" />
                         <fmt:formatNumber  value="${greetinggreeting}" type="currency"/>
                        </td>
                    </tr>
         <%}
        	
        	else
        	{
        		
        		if (greeting.getProperty("categoryincome").toString().equalsIgnoreCase(TheCatCAT))
        		{
        			 
        			allincome+=Double.parseDouble(greeting.getProperty("priceincome").toString());
        			currentincome=Double.parseDouble(greeting.getProperty("priceincome").toString());
        	%>
        			 <tr>
        		        
        		        <td>
        		                            <%= greeting.getProperty("categoryincome")%> </td>
        		        <td>
        		                            <%= greeting.getProperty("name")%> </td>
        		                        <td>
        		                            <%= greeting.getProperty("datecreated")%></td>
        		                        <td>
        		                         <c:set var="greetinggreeting" value="<%=currentincome%>" />
                         <fmt:formatNumber  value="${greetinggreeting}" type="currency"/>
        		                            </td>
        		                    </tr>
        		                    <%
        		}
        		else
        		{
        			response.getWriter().print("");
        		}    		
        		
        	}
        	}
        
        }
    
         %>
         
                    
                    
                </table>
            </td>
        </tr>
        
        <tr>
            <td style="color:blue;text-align:center;" colspan="2">
                Balance</td>
            <td style="color:green;text-align:center;" colspan="2">
                Income Total</td>
            <td  style="color:red;text-align:center;" colspan="2">
                Expense Total</td>
        </tr>
        <tr>
        
            <td style="color:blue;text-align:center;"  colspan="2">
            <%balancestring=allincome-allexp; %>
             <c:set var="balance" value="<%=balancestring %>" />
             <c:set var="Fallincome" value="<%=allincome %>" />
             <c:set var="Fallexp" value="<%=allexp %>" />
            
			 <fmt:formatNumber value="${balance}" type="currency"/>
            
                 </td>
            <td style="color:green;text-align:center;" colspan="2">
              
                 <fmt:formatNumber value="${Fallincome}" type="currency"/>
                </td>
            <td style="color:red;text-align:center;" colspan="2">
            
                <fmt:formatNumber value="${Fallexp}" type="currency"/>
               </td>
        </tr>
    </table>

</div>
</section>


 
 
    </article>
<footer>
			<h4>About Mobile Accounting SMB215 - Kamal Mokh, Ahmad Shaaban 
			<p>CNAM LIBAN 2013 ©</p>
			<p>Best Viewed HTML5 with :Safari, Chrome and Opera.</p></h4>
		</footer>
  </body>
</html>