
//Copyright 2011, Google Inc. All Rights Reserved.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
package  com.cnamsmb215html.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.server.spi.types.DateAndTime;
import com.google.appengine.api.datastore.Entity;



/**
* This servlet responds to the request corresponding to Income. The class
* creates and manages the IncomeEntity
* 
* 
*/
@SuppressWarnings("serial")
public class IncomeServlet extends BaseServlet {

private static final Logger logger = Logger.getLogger(IncomeServlet.class.getCanonicalName());

/**
* Searches for the entity based on the search criteria and returns result in
* JSON format
*/
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
   throws ServletException, IOException {

 super.doGet(req, resp);
 logger.log(Level.INFO, "Obtaining Income listing");
 String searchBy = req.getParameter("income-searchby");
 String searchFor = req.getParameter("q");
 PrintWriter out = resp.getWriter();
 if (searchFor == null || searchFor.equals("")) {
   Iterable<Entity> entities = Income.getAllItems();
   out.println(Util.writeJSON(entities));
 } else if (searchBy == null || searchBy.equals("name")) {
   Iterable<Entity> entities = Income.getIncome(searchFor);
   out.println(Util.writeJSON(entities));
 } else if (searchBy != null && searchBy.equals("categoryincome")) {
   Iterable<Entity> entities = Income.getIncomeForCategory("income", searchFor);
   
   out.println(Util.writeJSON(entities));
 }
}

/**
* Creates entity and persists the same
*/
protected void doPut(HttpServletRequest req, HttpServletResponse resp)
   throws ServletException, IOException {
	
	resp.getWriter().print("This is a test before create");
 logger.log(Level.INFO, "Creating Income");
 String incomeName = req.getParameter("name");
 String categoryNameincome = req.getParameter("categoryincome");
 String priceincome = req.getParameter("priceincome");
 String paymentmethod = req.getParameter("paymentmethod");
 String payer = req.getParameter("payer");
 
/* String incomedatestring = req.getParameter("incomedate");
 
 
 Date incomedate=new Date();
 * */
 Date date =new Date();
 /*try {
	 date = new SimpleDateFormat("dd/mm/yyyy",Locale.FRENCH).parse(incomedatestring);
} catch (ParseException e) {
	
}
*/
 Date datecreated= date;
 
 Income.createOrUpdateIncome(paymentmethod,payer,categoryNameincome,incomeName,priceincome,datecreated,datecreated);
resp.getWriter().print("This is a test after create");
}

/**
* Delete the entity from the datastore. Throws an exception if there are any
* orders associated with the item and ignores the delete action for it.
*/
protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
   throws ServletException, IOException {
	logger.log(Level.INFO, "deleting Income");
 String incomeKey = req.getParameter("id");
 PrintWriter out = resp.getWriter();
 try{      
   out.println(Income.deleteIncome(incomeKey));
 } catch(Exception e) {
   out.println(Util.getErrorMessage(e));
 }      
}

/**
* Redirects to delete or insert entity based on the action in the HTTP
* request.
*/
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
   throws ServletException, IOException {
 String action = req.getParameter("action");
 if (action.equalsIgnoreCase("delete")) {
	 
	// resp.getWriter().print("Deleting performed");
   doDelete(req, resp);
   return;
 } else if (action.equalsIgnoreCase("put")) {
   doPut(req, resp);
   return;
 }
}
}