/*created by ahmad chaaban*/
package com.accountingmobile;

import java.io.IOException;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;


import com.accountingmobile.categoryendpoint.Categoryendpoint;
import com.accountingmobile.categoryendpoint.model.Category;
import com.accountingmobile.expenseendpoint.Expenseendpoint;
import com.accountingmobile.expenseendpoint.model.Expense;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;




public class ExpenseActivity extends Activity {
	protected static Expense currentExpense;
	TextView tvExpenseName;
	TextView tvExpenseAmount;
	TextView tvExpenseDate;
	TextView tvExpenseCategory;
	TextView tvCreatedDate;
	Category category;
	String x="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense);
		
		tvExpenseName = (TextView) findViewById(R.id.tvExpenseName);
		tvExpenseAmount = (TextView) findViewById(R.id.tvExpenseAmount);
		tvExpenseDate = (TextView) findViewById(R.id.tvExpenseDate);
		tvCreatedDate = (TextView) findViewById(R.id.tvCreatedDate);
		
		tvExpenseName.setText(currentExpense.getName());
		tvExpenseAmount.setText(currentExpense.getPrice().toString());
		tvExpenseDate.setText(currentExpense.getExpenseDate().toString().substring(0, 10));
		
		tvCreatedDate.setText(currentExpense.getCreatedDate().toString().substring(0, 10));
		
		new GetCategory().execute();
		
	}
	
	 public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater Inflater = getMenuInflater();
			Inflater.inflate(R.menu.menu, menu);
			MenuItem item = menu.findItem(R.id.Add);
			item.setVisible(false);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		
			 if (item.getItemId() == R.id.Edit) {
				
				ExpenseFormActivity.updatedExpense=currentExpense;
				Intent CategoryFormIntent = new Intent(getBaseContext(),ExpenseFormActivity.class);
				startActivity(CategoryFormIntent);
			}
			else if (item.getItemId() == R.id.Delete)
			{
				new RemoveExpenseAsync().execute();
				Intent MainIntent = new Intent(getBaseContext(),MainActivity.class);
				startActivity(MainIntent);
			}
			
			return true;
		}
		
		
		
		
		
		 /* AsyncTask for removing a Expense
		   */
		  private class RemoveExpenseAsync extends AsyncTask<Void, Void, Void> {

		    @Override
		    protected Void doInBackground(Void... params) {


		    	Expenseendpoint.Builder endpointBuilder = new Expenseendpoint.Builder(
		          AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
		     
		      endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);

		      Expenseendpoint endpoint = endpointBuilder.build();

		      try {
		        endpoint.removeExpense(currentExpense.getKey().getId().longValue()).execute();
				
		      } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();        
		      }
		      return null;
		    }
		    
		  }
		
		  
		  /* AsyncTask for get a Category by a specific id
		   */
		private class GetCategory extends AsyncTask<Void, Void, Category> {

			   @Override
			   protected Category doInBackground(Void... params) {
			   
				   Categoryendpoint.Builder builder = new Categoryendpoint.Builder(
						         AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
						         null);
						         
						     builder = CloudEndpointUtils.updateBuilder(builder);
						     Categoryendpoint endpoint = builder.build();
						     try {
						   	
						    	category =endpoint.getCategory(currentExpense.getCategoryId()).execute();
						     } catch (IOException e) {
						       // TODO Auto-generated catch block
						       e.printStackTrace();
						     
						     }
						   
						    
						     return category;
		  
			   }
			   
			   protected void onPostExecute(Category category) { 
				
				tvExpenseCategory = (TextView) findViewById(R.id.tvExpenseCategory);
				tvExpenseCategory.setText(category.getName());
			} 
			   
		 }
		
		
		
		

}