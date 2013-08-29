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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
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
    <title>Expenses Application v19</title>
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
                <select id="SelectCategory" name="D1">
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
                            Description</td>
                        <td>
                            Date</td>
                        <td>
                            Amount</td>
                    </tr>
                    
                    <%
                    Double allexp=new Double(0);
                    Double allincome=new Double(0);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key guestbookKey = KeyFactory.createKey("category", "CAR");
    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Greetings belonging to the selected Guestbook.
    Query query = new Query("Expense").addSort("datecreated", Query.SortDirection.DESCENDING);
    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
    if (greetings.isEmpty()) {
        %>
        <tr>
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
        
        	allexp+=Double.parseDouble(greeting.getProperty("price").toString());
        	 
        	
        %>
        <tr>
        <td>
                            <%= greeting.getProperty("name")%> </td>
                        <td>
                            <%= greeting.getProperty("datecreated")%></td>
                        <td>
                            <%= greeting.getProperty("price")%></td>
                    </tr>
         <%}} %>
         
                    
                    
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
        <tr>
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
        	allincome+=Double.parseDouble(greeting.getProperty("priceincome").toString());
        	
        	%>
        <tr>
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
                <%=allincome-allexp %> </td>
            <td class="style4" colspan="2">
                <%=allincome %></td>
            <td class="style4" colspan="2">
               <%=allexp %></td>
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