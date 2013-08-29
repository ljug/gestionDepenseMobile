
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
* This servlet responds to the request corresponding to items. The class
* creates and manages the ItemEntity
* 
* 
*/
@SuppressWarnings("serial")
public class ExpenseServlet extends BaseServlet {

private static final Logger logger = Logger.getLogger(ExpenseServlet.class.getCanonicalName());

/**
* Searches for the entity based on the search criteria and returns result in
* JSON format
*/
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
   throws ServletException, IOException {

 super.doGet(req, resp);
 logger.log(Level.INFO, "Obtaining Item listing");
 String searchBy = req.getParameter("expense-searchby");
 String searchFor = req.getParameter("q");
 PrintWriter out = resp.getWriter();
 if (searchFor == null || searchFor.equals("")) {
   Iterable<Entity> entities = Expense.getAllItems();
   out.println(Util.writeJSON(entities));
 } else if (searchBy == null || searchBy.equals("name")) {
   Iterable<Entity> entities = Expense.getExpense(searchFor);
   out.println(Util.writeJSON(entities));
 } else if (searchBy != null && searchBy.equals("category")) {
   Iterable<Entity> entities = Expense.getExpensesForCategory("expense", searchFor);
   
   out.println(Util.writeJSON(entities));
 }
}

/**
* Creates entity and persists the same
*/
protected void doPut(HttpServletRequest req, HttpServletResponse resp)
   throws ServletException, IOException {
 logger.log(Level.INFO, "Creating Item");
 String expenseName = req.getParameter("name");
 String categoryName = req.getParameter("category");
 String price = req.getParameter("price");
 String expensedatestring = req.getParameter("expensedate");
 Date expensedate=new Date();
 String string = "January 2, 2010";
 Date date =new Date();
 try {
	 date = new SimpleDateFormat("dd/mm/yyyy",Locale.FRENCH).parse(expensedatestring);
} catch (ParseException e) {
	
}
 Date datecreated= date;
 //DateTime datecreated = DateAndTime.parseRfc3339(req.getParameter("datecreated").toString());
 Expense.createOrUpdateExpense(categoryName, expenseName, price,datecreated,expensedate);
// Expense.createOrUpdateExpense(categoryName, expenseName, price);
}

/**
* Delete the entity from the datastore. Throws an exception if there are any
* orders associated with the item and ignores the delete action for it.
*/
protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
   throws ServletException, IOException {
	logger.log(Level.INFO, "deleting item");
 String expenseKey = req.getParameter("id");
 PrintWriter out = resp.getWriter();
 try{      
   out.println(Expense.deleteExpense(expenseKey));
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