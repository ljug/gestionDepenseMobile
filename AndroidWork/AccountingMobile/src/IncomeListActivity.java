/*created by ahmad chaaban*/
package com.accountingmobile;

import java.io.IOException;
import java.util.List;
import com.accountingmobile.incomeendpoint.Incomeendpoint;
import com.accountingmobile.incomeendpoint.model.CollectionResponseIncome;
import com.accountingmobile.incomeendpoint.model.Income;
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
public class IncomeListActivity extends Activity{
	private List<Income> mIncomeList;
	private EditText et;
	IncomeAdapter adapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income_list);

	    et = (EditText)findViewById(R.id.etIncomeSearch);
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
		 
		 
		 new ListOfIncomesAsyncRetriever().execute();	
		
	}	
	
	 /* AsyncTask for retrieving the list of expenses 
	   */
	  private class ListOfIncomesAsyncRetriever extends AsyncTask<Void, Void, CollectionResponseIncome> {

	    @Override
	    protected CollectionResponseIncome doInBackground(Void... params) {


	    	Incomeendpoint.Builder endpointBuilder = new Incomeendpoint.Builder(
	          AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
	     
	      endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);


	      CollectionResponseIncome result;

	      Incomeendpoint endpoint = endpointBuilder.build();

	      try {
	        result = endpoint.listIncome().execute();
	      } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        result = null;
	      }
	      return result;
	    }

	    @Override
	    protected void onPostExecute(CollectionResponseIncome result) {	      
	    	mIncomeList=result.getItems();
	    	// Create the list
			ListView listViewIncome = (ListView)findViewById(R.id.list_income);
			 adapter=new IncomeAdapter(mIncomeList, getLayoutInflater());
			 
			 listViewIncome.setAdapter(adapter);
			 
			 
			 listViewIncome.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
						Income selectedIncome=(Income) adapter.getItem(position);
						IncomeActivity.currentIncome=selectedIncome;
						
						Intent ExpenseIntent = new Intent(getBaseContext(),IncomeActivity.class);				
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
				IncomeFormActivity.updatedIncome=null;
				startActivity(new Intent(getBaseContext(), IncomeFormActivity.class));
			}
			
			return true;
		}

	  
}
