package com.accountingmobile;

import com.accountingmobile.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
//import javax.persistence.EntityExistsException;
//import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "expenseendpoint", namespace = @ApiNamespace(ownerDomain = "accountingmobile.com", ownerName = "accountingmobile.com", packagePath = ""))
public class ExpenseEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listExpense")
	public CollectionResponse<Expense> listExpense(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Expense> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Expense as Expense");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Expense>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Expense obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Expense> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getExpense")
	public Expense getExpense(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Expense expense = null;
		try {
			expense = mgr.find(Expense.class, id);
		} finally {
			mgr.close();
		}
		return expense;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param expense the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertExpense")
	public Expense insertExpense(Expense expense) {
		EntityManager mgr = getEntityManager();
		try {
			/*if (containsExpense(expense)) {
				throw new EntityExistsException("Object already exists");
			}*/
			mgr.persist(expense);
		} finally {
			mgr.close();
		}
		return expense;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param expense the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateExpense")
	public Expense updateExpense(Expense expense) {
		EntityManager mgr = getEntityManager();
		Expense expense1;
		try {
			/*if (!containsExpense(expense)) {
				throw new EntityNotFoundException("Object does not exist");
			}*/
			expense1 = mgr.find(Expense.class, expense.getKey().getId());
			expense1.setName(expense.getName());
			expense1.setPrice(expense.getPrice());
			expense1.setExpenseDate(expense.getExpenseDate());
			expense1.setCreatedDate(expense.getCreatedDate());
			expense1.setCategoryId(expense.getCategoryId());
			mgr.persist(expense1);
		} finally {
			mgr.close();
		}
		return expense1;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeExpense")
	public void removeExpense(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Expense expense = mgr.find(Expense.class, id);
			mgr.remove(expense);
		} finally {
			mgr.close();
		}
	}

	
	/**
	 * This method lists all the entities inserted in datastore by category.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listExpenseByCategory",
			path="listExpenseByCategory/category_id/{category_id}/")
	public CollectionResponse<Expense> listExpenseByCategory(
			@Named("category_id") long category_id,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Expense> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Expense as Expense WHERE Expense.category_id = "+category_id);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Expense>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Expense obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Expense> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}


	
	/*private boolean containsExpense(Expense expense) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Expense item = mgr.find(Expense.class, expense.getKey());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}*/

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
