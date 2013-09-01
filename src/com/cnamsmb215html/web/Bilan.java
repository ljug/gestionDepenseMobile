package  com.cnamsmb215html.web;
/**
 * @author or Modified by  Kamal Mokh 8921f CNAM 2013 kamal.mokh@isae.edu.lb
 *
 */
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

import java.util.Date;
import java.util.List;

/**
 * This class handles all the CRUD operations related to
 * Product entity.
 *
 */
public class Bilan {
	public static Iterable<Entity> entitiesincome;
	    public static Iterable<Entity> entitiesexpenses;
	    public static String useruser;
	    public static String TheCategory;
	    public static double TotalIncome;
	    public static double TotalExpense;
	    public static double TotalBalance;
	    
	public static void createOrUpdateBilan(String CategoryName) {
		//  public static Entity createOrUpdateExpense(String CategoryName, String expenseName,String price ) {
	    Entity product =Category.getCategory(CategoryName);
	    TheCategory=CategoryName;
	    Iterable<Entity> entitiesincome=Income.getIncomeForCategory("Income", CategoryName);
	    Iterable<Entity> entitiesexpenses= Expense.getExpensesForCategory("Expense", CategoryName);
	   
	  }
}
