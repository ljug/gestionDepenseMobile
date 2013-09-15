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
	  <a id="downloadsource" href="https://github.com/pascalfares/gestionDepenseMobile/archive/19edacca93de2edda6ae573bcf3db1479bdcd6f9.zip" class="tab">download source</a>
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
<h1>Bulk upload Offline data</h1>
 <table>
 
 <tr>
 <td> Allowed files is XML only </td>
 <td><input type="file" accept=".xml"/>
 </td>
 <td> <input type="submit" value="Upload data"> </td>
 </tr>
 
 </table>



</section>


 
 
    </article>
<footer>
			<h4>About Mobile Accounting SMB215 - Kamal Mokh, Ahmad Shaaban 
			<p>CNAM LIBAN 2013 ©</p>
			<p>Best Viewed HTML5 with :Safari, Chrome and Opera.</p></h4>
		</footer>
  </body>
</html>