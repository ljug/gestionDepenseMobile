package com.accountingmobile;

import java.io.IOException;
import java.util.List;


import com.accountingmobile.expenseendpoint.Expenseendpoint;
import com.accountingmobile.expenseendpoint.model.CollectionResponseExpense;
import com.accountingmobile.expenseendpoint.model.Expense;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;
import android.app.ActivityGroup;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;


public class ExpenseTabActivity extends ActivityGroup {
	private List<Expense> mExpenseList;
	TypeAdapter adapter;
	protected static double Expense_Amount;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_layout);

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
	    	
	    	 
	    	  if (BilanFilterActivty.cat_id==1)
	    	  {
	    		  result = endpoint.listExpense().execute();
	    	  }
	    	  else
	    	  {
	    		  result = endpoint.listExpenseByCategory(BilanFilterActivty.cat_id).execute();
	    	  }
	        
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
	    	Expense_Amount=0;
	    	for(int i = 0; i < mExpenseList.size(); ++i) {
	    		Expense_Amount=Expense_Amount+ mExpenseList.get(i).getPrice();
	    	}
	    	// Create the list
			ListView listViewExpense = (ListView)findViewById(R.id.list_expense_cat);
			 adapter=new TypeAdapter(mExpenseList, getLayoutInflater(),true);
			 
			 listViewExpense.setAdapter(adapter);
			 
	
			 
			 
	    }
	  
	
	
}
	
	  
	
	 
    
}
