package  com.cnamsmb215html.web;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import java.util.List;

/**
 * This class handles all the CRUD operations related to
 * Category entity.
 *
 */
public class Category {

  /**
   * Update the Category
   * @param name: name of the Category
   * @param description : description
   * @return  updated Category
   */
  public static void createOrUpdateCategory(String name, String description) {
    Entity Category = getCategory(name);
  	if (Category == null) {
  	  Category = new Entity("Category", name);
  	  Category.setProperty("description", description);
  	} else {
  	  Category.setProperty("description", description);
  	}
  	Util.persistEntity(Category);
  }

  /**
   * Retrun all the Categorys
   * @param kind : of kind Category
   * @return  Categorys
   */
  public static Iterable<Entity> getAllCategorys(String kind) {
    return Util.listEntities(kind, null, null);
  }

  /**
   * Get Category entity
   * @param name : name of the Category
   * @return: Category entity
   */
  public static Entity getCategory(String name) {
  	Key key = KeyFactory.createKey("Category",name);
  	return Util.findEntity(key);
  }

  /**
   * Get all items for a Category
   * @param name: name of the Category
   * @return list of items
   */
  
  public static List<Entity> getItems(String name) {
  	Query query = new Query();
  	Key parentKey = KeyFactory.createKey("Category", name);
  	query.setAncestor(parentKey);
  	query.addFilter(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.GREATER_THAN, parentKey);
  		List<Entity> results = Util.getDatastoreServiceInstance()
  				.prepare(query).asList(FetchOptions.Builder.withDefaults());
  		return results;
	}
  
  /**
   * Delete Category entity
   * @param CategoryKey: Category to be deleted
   * @return status string
   */
  public static String deleteCategory(String CategoryKey)
  {
	  Key key = KeyFactory.createKey("Category",CategoryKey);	   
	  
	  List<Entity> items = getItems(CategoryKey);	  
	  if (!items.isEmpty()){
	      return "Cannot delete, as there are items associated with this Category.";	      
	    }	    
	  Util.deleteEntity(key);
	  return "Category deleted successfully";
	  
  }
}
