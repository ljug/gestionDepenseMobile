
/*
 *Created by ahmad chaaban on 24-8-2013 *
 *Define the entity Expense with 5 persistence fields and automatic primary key*
 */
package com.accountingmobile;

import java.util.Date;

import com.google.appengine.api.datastore.Key;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Expense {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Key key; /*automatic primary key*/
   
   //persistence fields
   private String Name;
   private double price;
   private Date expenseDate;
   private Date createdDate;

   private long category_id;
   
  
   
   //Accessor Methods
   public Key getKey() {
       return key;
   }

   public String getName() {
       return Name;
   }
   public void setName(String Name) {
       this.Name = Name;
   }

   public double getPrice() {
       return price;
   }
   
   public void setPrice(double price) {
       this.price = price;
   }
   
   public Date getExpenseDate() {
	   return expenseDate;
   }

   public void setExpenseDate(Date expenseDate) {
	   this.expenseDate = expenseDate;
   }
	 
   public Date getCreatedDate() {
	   return createdDate;
   }

   public void setCreatedDate(Date createdDate) {
	   this.createdDate = createdDate;
   }
   
   public long getCategoryId() {
	   return category_id;
   }
	  
   public void setCategoryId(long category_id) {  
	   this.category_id = category_id;
   }

}