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

@Api(name = "categoryendpoint", namespace = @ApiNamespace(ownerDomain = "accountingmobile.com", ownerName = "accountingmobile.com", packagePath = ""))
public class CategoryEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listCategory")
	public CollectionResponse<Category> listCategory(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Category> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Category as Category");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Category>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Category obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Category> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getCategory")
	public Category getCategory(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Category category = null;
		try {
			category = mgr.find(Category.class, id);
		} finally {
			mgr.close();
		}
		return category;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param category the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertCategory")
	public Category insertCategory(Category category) {
		EntityManager mgr = getEntityManager();
		try {
			/*
			 * ignored by ahmad chaaban
			 * if (containsCategory(category)) {
				throw new EntityExistsException("Object already exists");
			}*/

			mgr.persist(category);
		} finally {
			mgr.close();
		}
		return category;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param category the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateCategory")
	public Category updateCategory(Category category) {
		EntityManager mgr = getEntityManager();
		Category category1;
		try {
			
			/* ignored by ahmad chaaban
			  if (!containsCategory(category)) {
				throw new EntityNotFoundException("Object does not exist");
			}*/
			/*added this code by ahmad chaaaban to get the category updated*/
			 category1 = mgr.find(Category.class, category.getKey().getId());
			category1.setName(category.getName());
			category1.setDescription(category.getDescription());

			mgr.persist(category1);
		} finally {
			mgr.close();
		}
		return category1;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeCategory")
	public void removeCategory(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Category category = mgr.find(Category.class, id);
			mgr.remove(category);
		} finally {
			mgr.close();
		}
	}

	/* ignored by ahmad chaaban not used
	 private boolean containsCategory(Category category) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Category item = mgr.find(Category.class, category.getKey());
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
