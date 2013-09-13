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
import com.accountingmobile.incomeendpoint.Incomeendpoint;
import com.accountingmobile.incomeendpoint.model.Income;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;

public class IncomeActivity extends Activity {
	protected static Income currentIncome;
	TextView tvIncomeName;
	TextView tvIncomeAmount;
	TextView tvIncomeDate;
	TextView tvIncomeCategory;
	TextView tvCreatedDate;
	TextView tvPaymentMethod;
	TextView tvPayer;
	Category category;
	String x="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income);
		
		tvIncomeName = (TextView) findViewById(R.id.tvIncomeName);
		tvIncomeAmount = (TextView) findViewById(R.id.tvIncomeAmount);
		tvIncomeDate = (TextView) findViewById(R.id.tvIncomeDate);
		tvCreatedDate = (TextView) findViewById(R.id.tvCreatedDate);
		tvPaymentMethod = (TextView) findViewById(R.id.tvPaymentMethod);
		tvPayer = (TextView) findViewById(R.id.tvPayer);
		
		tvIncomeName.setText(currentIncome.getName());
		tvIncomeAmount.setText(currentIncome.getPrice().toString());
		tvIncomeDate.setText(currentIncome.getIncomeDate().toString().substring(0, 10));	
		tvCreatedDate.setText(currentIncome.getCreatedDate().toString().substring(0, 10));
		tvPaymentMethod.setText(currentIncome.getPaymentType());
		tvPayer.setText(currentIncome.getPayer());
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
				
				IncomeFormActivity.updatedIncome=currentIncome;
				Intent IncomeFormIntent = new Intent(getBaseContext(),IncomeFormActivity.class);
				startActivity(IncomeFormIntent);
			}
			else if (item.getItemId() == R.id.Delete)
			{
				new RemoveIncomeAsync().execute();
				Intent MainIntent = new Intent(getBaseContext(),MainActivity.class);
				startActivity(MainIntent);
			}
			
			return true;
		}
		
		
		
		
		
		 /* AsyncTask for removing a Expense
		   */
		  private class RemoveIncomeAsync extends AsyncTask<Void, Void, Void> {

		    @Override
		    protected Void doInBackground(Void... params) {


		    	Incomeendpoint.Builder endpointBuilder = new Incomeendpoint.Builder(
		          AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
		     
		      endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);

		      Incomeendpoint endpoint = endpointBuilder.build();

		      try {
		        endpoint.removeIncome(currentIncome.getKey().getId().longValue()).execute();
				
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
						   	
						    	category =endpoint.getCategory(currentIncome.getCategoryId()).execute();
						     } catch (IOException e) {
						       // TODO Auto-generated catch block
						       e.printStackTrace();
						     
						     }
						   
						    
						     return category;
		  
			   }
			   
			   protected void onPostExecute(Category category) { 
				
				   tvIncomeCategory = (TextView) findViewById(R.id.tvIncomeCategory);
				   tvIncomeCategory.setText(category.getName());
			} 
			   
		 }
		

}