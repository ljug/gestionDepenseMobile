package  com.cnamsmb215html.web;

import java.util.List;

import java.io.IOException;
import java.util.Date;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;




/**
 * This class handles CRUD operations related to Expense entity.
 * 
 *
 */

public class Expense {

  /**
   * Create or update Expense for a particular product. Product entity has one to many
   * relation-ship with Expense entity
   * 
   * @param productName
   *          : product name for which the Expense is created.
   * @param expenseName
   *          : expense name
   * @param price
   *          : price of the expense
   * @return
   */
 public static Entity createOrUpdateExpense(String CategoryName, String expenseName,String price,Date datecreated,Date expensedate) {
	//  public static Entity createOrUpdateExpense(String CategoryName, String expenseName,String price ) {
    Entity product =Category.getCategory(CategoryName);
    Entity expense = getSingleExpense(expenseName);
    if(expense == null){
    	expense = new Entity("Expense",product.getKey());
    	expense.setProperty("name", expenseName);
    	expense.setProperty("category", CategoryName);
    	expense.setProperty("price", price);
    	expense.setProperty("expensedate", expensedate);
    	expense.setProperty("datecreated", datecreated);
    }
    else{
      if (price != null && !"".equals(price)) {
    	  expense.setProperty("price", price);
      }           
    }
    Util.persistEntity(expense);
    return expense;
  }

  /**
   * get All the items in the list
   * 
   * @param kind
   *          : item kind
   * @return all the items
   */
  public static Iterable<Entity> getAllItems() {
  	Iterable<Entity> entities = Util.listEntities("Expense", null, null);
  	return entities;
  }

  /**
   * Get the item by name, return an Iterable
   * 
   * @param expenseName
   *          : expense name
   * @return expense Entity
   */
  public static Iterable<Entity> getExpense(String expenseName) {
  	Iterable<Entity> entities = Util.listEntities("Expense", "name", expenseName);
  	return entities;
  }

  /**
   * Get all the items for a product
   * 
   * @param kind
   *          : item kind
   * @param productName
   *          : product name
   * @return: all items of type product
   */
  public static Iterable<Entity> getExpensesForCategory(String kind, String CategoryName) {
    Key ancestorKey = KeyFactory.createKey("Category", CategoryName);
    return Util.listChildren("Expense", ancestorKey);
  }

  /**
   * get Item with expense name
   * @param expensemName: get expenseName
   * @return  expense entity
   */
  public static Entity getSingleExpense(String expenseName) {
    Query query = new Query("Expense");
    query.addFilter("name", FilterOperator.EQUAL, expenseName);
    List<Entity> results = Util.getDatastoreServiceInstance().prepare(query).asList(FetchOptions.Builder.withDefaults());
    if (!results.isEmpty()) {
      return (Entity)results.remove(0);
    }
    return null;
  }
  
  public static String deleteExpense(String expenseKey)
  {
    Entity entity = getSingleExpense(expenseKey);    
    if(entity != null){
      Util.deleteEntity(entity.getKey());
      return("Expense deleted successfully.");
    } else
      return("Expense not found");      
  }
}
