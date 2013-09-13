package com.accountingmobile;

import java.util.Date;

import com.google.appengine.api.datastore.Key;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Income {
	@Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Key key; /*automatic primary key*/
	   
	   //persistence fields
	   private String Name;
	   private String Payment_Type;
	   private String Payer;
	   private double price;
	   private Date IncomeDate;
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
	   
	   public String getPayment_Type() {
	       return Payment_Type;
	   }
	   public void setPayment_Type(String Payment_Type) {
	       this.Payment_Type = Payment_Type;
	   }
	   
	   public String getPayer() {
	       return Payer;
	   }
	   public void setPayer(String Payer) {
	       this.Payer = Payer;
	   }

	   public double getPrice() {
	       return price;
	   }
	   
	   public void setPrice(double price) {
	       this.price = price;
	   }
	   
	   public Date getIncomeDate() {
		   return IncomeDate;
	   }

	   public void setIncomeDate(Date IncomeDate) {
		   this.IncomeDate = IncomeDate;
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
