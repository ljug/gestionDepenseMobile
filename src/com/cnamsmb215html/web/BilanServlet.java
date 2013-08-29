  package com.cnamsmb215html.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@SuppressWarnings("serial")


	public class BilanServlet extends BaseServlet {

		  private static final Logger logger = Logger.getLogger(BilanServlet.class.getCanonicalName());
		  /**
		   * Get the entities in JSON format.
		   */

		  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		      throws ServletException, IOException {
			super.doGet(req, resp);
		    logger.log(Level.INFO, "Obtaining product listing");
		    
		      
		   
		  }

		  /**
		   * Create the entity and persist it.
		   */
		  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
		      throws ServletException, IOException {
		    logger.log(Level.INFO, "Creating Product");
		     
		  }

		  /**
		   * Delete the product entity
		   */
		  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
		      throws ServletException, IOException {
		    
		  }

		  /**
		   * Redirect the call to doDelete or doPut method
		   */
		  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		      throws ServletException, IOException {
		    
		  }

		}