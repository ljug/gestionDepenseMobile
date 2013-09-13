/*created by ahmad chaaban*/
package com.accountingmobile;

import java.io.IOException;
import java.util.List;


import com.accountingmobile.expenseendpoint.Expenseendpoint;
import com.accountingmobile.expenseendpoint.model.CollectionResponseExpense;
import com.accountingmobile.expenseendpoint.model.Expense;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
public class ExpenseListActivity extends Activity{
	private List<Expense> mExpenseList;
	private EditText et;
	ExpenseAdapter adapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_list);

	    et = (EditText)findViewById(R.id.etExpenseSearch);
		 et.addTextChangedListener(new TextWatcher() {

	            @Override
	            public void onTextChanged(CharSequence s, int start, int before, int count) {
	      
	            	adapter.getFilter().filter(s);
	            	
	            }

	            @Override
	            public void beforeTextChanged(CharSequence s, int start, int count,
	                    int after) {
	                // TODO Auto-generated method stub

	            }

	            @Override
	            public void afterTextChanged(Editable s) {
	                // TODO Auto-generated method stub

	            }
	        });
		 
		 
		 new ListOfExpensesAsyncRetriever().execute();	
		
	}	
	
	 /* AsyncTask for retrieving the list of expenses 
	   */
	  private class ListOfExpensesAsyncRetriever extends AsyncTask<Void, Void, CollectionResponseExpense> {

	    @Override
	    protected CollectionResponseExpense doInBackground(Void... params) {


	    	Expenseendpoint.Builder endpointBuilder = new Expenseendpoint.Builder(
	          AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
	     
	      endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);


	      CollectionResponseExpense result;

	      Expenseendpoint endpoint = endpointBuilder.build();

	      try {
	        result = endpoint.listExpense().execute();
	      } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        result = null;
	      }
	      return result;
	    }

	    @Override
	    protected void onPostExecute(CollectionResponseExpense result) {	      
	    	mExpenseList=result.getItems();
	    	// Create the list
			ListView listViewExpense = (ListView)findViewById(R.id.list_expense);
			 adapter=new ExpenseAdapter(mExpenseList, getLayoutInflater());
			 
			 listViewExpense.setAdapter(adapter);
			 
			 
			 listViewExpense.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
						Expense selectedExpense=(Expense) adapter.getItem(position);
						ExpenseActivity.currentExpense=selectedExpense;
						Intent ExpenseIntent = new Intent(getBaseContext(),ExpenseActivity.class);				
						startActivity(ExpenseIntent);
						
					}
				});
			 
	    }
	
	
}
	  
	  
	  public boolean onCreateOptionsMenu(Menu menu) {
			MenuInflater Inflater = getMenuInflater();
			Inflater.inflate(R.menu.menu, menu);
			MenuItem edit = menu.findItem(R.id.Edit);
			edit.setVisible(false);
			
			MenuItem delete = menu.findItem(R.id.Delete);
			delete.setVisible(false);
			return true;
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		
			if (item.getItemId() == R.id.Add)
			{
				ExpenseFormActivity.updatedExpense=null;
				startActivity(new Intent(getBaseContext(), ExpenseFormActivity.class));
			}
			
			return true;
		}

	  
}
