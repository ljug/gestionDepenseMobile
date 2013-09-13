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

@Api(name = "incomeendpoint", namespace = @ApiNamespace(ownerDomain = "accountingmobile.com", ownerName = "accountingmobile.com", packagePath = ""))
public class IncomeEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listIncome")
	public CollectionResponse<Income> listIncome(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Income> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Income as Income");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Income>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Income obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Income> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getIncome")
	public Income getIncome(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Income income = null;
		try {
			income = mgr.find(Income.class, id);
		} finally {
			mgr.close();
		}
		return income;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param income the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertIncome")
	public Income insertIncome(Income income) {
		EntityManager mgr = getEntityManager();
		try {
			/*if (containsIncome(income)) {
				throw new EntityExistsException("Object already exists");
			}*/
			mgr.persist(income);
		} finally {
			mgr.close();
		}
		return income;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param income the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateIncome")
	public Income updateIncome(Income income) {
		EntityManager mgr = getEntityManager();
		Income income1;
		try {
			/*if (!containsIncome(income)) {
				throw new EntityNotFoundException("Object does not exist");
			}*/
			income1 = mgr.find(Income.class, income.getKey().getId());
			income1.setName(income.getName());
			income1.setPayment_Type(income.getPayment_Type());
			income1.setPayer(income.getPayer());
			income1.setPrice(income.getPrice());
			income1.setIncomeDate(income.getIncomeDate());
			income1.setCreatedDate(income.getCreatedDate());
			income1.setCategoryId(income.getCategoryId());
			mgr.persist(income1);
		} finally {
			mgr.close();
		}
		return income1;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeIncome")
	public void removeIncome(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Income income = mgr.find(Income.class, id);
			mgr.remove(income);
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
	@ApiMethod(name = "listIncomeByCategory",
			   path="listIncomeByCategory/category_id/{category_id}/")
	public CollectionResponse<Income> listIncomeByCategory(
			@Named("category_id") long category_id,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Income> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Income as Income WHERE Income.category_id = "+category_id);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Income>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Income obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Income> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	
	/*private boolean containsIncome(Income income) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Income item = mgr.find(Income.class, income.getKey());
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
