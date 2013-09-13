
package com.accountingmobile;

import java.io.IOException;
import java.util.List;



import com.accountingmobile.incomeendpoint.Incomeendpoint;
import com.accountingmobile.incomeendpoint.model.CollectionResponseIncome;
import com.accountingmobile.incomeendpoint.model.Income;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;

import android.widget.ListView;


public class IncomeTabActivity extends Activity {
	private List<Income> mIncomeList;
	TypeAdapter adapter;
	protected static double Income_Amount; 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_layout);
        new ListOfIncomeAsyncRetriever().execute();
    }
    
    
	 /* AsyncTask for retrieving the list of incomes 
	   */
	  private class ListOfIncomeAsyncRetriever extends AsyncTask<Void, Void, CollectionResponseIncome> {

	    @Override
	    protected CollectionResponseIncome doInBackground(Void... params) {


	    	Incomeendpoint.Builder endpointBuilder = new Incomeendpoint.Builder(
	          AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
	     
	      endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);


	      CollectionResponseIncome result;

	      Incomeendpoint endpoint = endpointBuilder.build();

	      try {
	    	
	    	 
	    	  if (BilanFilterActivty.cat_id==1)
	    	  {
	    		  result = endpoint.listIncome().execute();
	    	  }
	    	  else
	    	  {
	    		  result = endpoint.listIncomeByCategory(BilanFilterActivty.cat_id).execute();
	    	  }
	        
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
	    	Income_Amount=0;
	    	for(int i = 0; i < mIncomeList.size(); ++i) {
	    		Income_Amount=Income_Amount+ mIncomeList.get(i).getPrice();
	    	}
			ListView listViewIncome = (ListView)findViewById(R.id.list_income_cat);
			 adapter=new TypeAdapter(mIncomeList, getLayoutInflater(),false);
			 
			 listViewIncome.setAdapter(adapter);
			 
			 
			 
			 
	    }
	
	
}
    
    
}
