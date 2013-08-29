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
 * This class handles CRUD operations related to Income entity.
 * 
 *
 */

public class Income {

  /**
   * Create or update Income for a particular product. Product entity has one to many
   * relation-ship with Income entity
   * 
   * @param productName
   *          : product name for which the income is created.
   * @param incomeName
   *          : income name
   * @param price
   *          : price of the income
   * @return
   */
 public static Entity createOrUpdateIncome(String paymentmethod,String payer,String CategoryNameincome, String incomeName,String priceincome,Date datecreated,Date paymentdate) {
	//  public static Entity createOrUpdateincome(String CategoryName, String incomeName,String price ) {
    Entity product =Category.getCategory(CategoryNameincome);
    Entity income = getSingleIncome(incomeName);
    if(income == null){
    	income = new Entity("Income",product.getKey());
    	income.setProperty("name", incomeName);
    	income.setProperty("paymentmethod", paymentmethod);
    	income.setProperty("payer", payer);
    	income.setProperty("priceincome", priceincome);
    	income.setProperty("categoryincome", CategoryNameincome);
    	
    	income.setProperty("paymentdate", paymentdate);
    	income.setProperty("datecreated", datecreated);
    }
    else{
      if (priceincome != null && !"".equals(priceincome)) {
    	  income.setProperty("priceincome", priceincome);
      }           
    }
    Util.persistEntity(income);
    return income;
  }

  /**
   * get All the items in the list
   * 
   * @param kind
   *          : item kind
   * @return all the items
   */
  public static Iterable<Entity> getAllItems() {
  	Iterable<Entity> entities = Util.listEntities("Income", null, null);
  	return entities;
  }

  /**
   * Get the item by name, return an Iterable
   * 
   * @param incomeName
   *          : income name
   * @return income Entity
   */
  public static Iterable<Entity> getIncome(String icnomeName) {
  	Iterable<Entity> entities = Util.listEntities("Income", "name", icnomeName);
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
  public static Iterable<Entity> getIncomeForCategory(String kind, String CategoryName) {
    Key ancestorKey = KeyFactory.createKey("Category", CategoryName);
    return Util.listChildren("Income", ancestorKey);
  }

  /**
   * get Item with income name
   * @param incomemName: get incomeName
   * @return  income entity
   */
  public static Entity getSingleIncome(String incomeName) {
    Query query = new Query("Income");
    query.addFilter("name", FilterOperator.EQUAL, incomeName);
    List<Entity> results = Util.getDatastoreServiceInstance().prepare(query).asList(FetchOptions.Builder.withDefaults());
    if (!results.isEmpty()) {
      return (Entity)results.remove(0);
    }
    return null;
  }
  
  public static String deleteIncome(String incomeKey)
  {
    Entity entity = getSingleIncome(incomeKey);    
    if(entity != null){
      Util.deleteEntity(entity.getKey());
      return("Income deleted successfully.");
    } else
      return("Income not found");      
  }
}
