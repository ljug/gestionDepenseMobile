/*created by ahmad chaaban*/
package com.accountingmobile;

import java.io.IOException;

import java.util.List;

import com.accountingmobile.categoryendpoint.Categoryendpoint;
import com.accountingmobile.categoryendpoint.model.Category;
import com.accountingmobile.categoryendpoint.model.CollectionResponseCategory;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Spinner;
@SuppressLint("SimpleDateFormat")
public class BilanFilterActivty extends Activity implements
OnItemSelectedListener{
	private List<Category> mCategoryList;
	Spinner spinner;
	protected static long cat_id; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bilan_filter);
		
		
		spinner = (Spinner) findViewById(R.id.spinnerFilterCategory);
		
		
		new ListOfCategoryAsyncRetriever().execute();
		spinner.setOnItemSelectedListener(this);
		
		
		
		
		 Button btnFilter = (Button) findViewById(R.id.Btn_filter);
		 btnFilter.setOnClickListener(new OnClickListener() {
		 
		   @Override
		   public void onClick(View v) {
		 
			
			   
			   Intent BilanIntent = new Intent(getBaseContext(),BilanActivity.class);
			   startActivity(BilanIntent);
		   }
		  });
		  
	}
	
			
		 /* AsyncTask for retrieving the list of categories 
		   */
		  private class ListOfCategoryAsyncRetriever extends AsyncTask<Void, Void, CollectionResponseCategory> {

		    @Override
		    protected CollectionResponseCategory doInBackground(Void... params) {


		    	Categoryendpoint.Builder endpointBuilder = new Categoryendpoint.Builder(
		          AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
		     
		      endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);


		      CollectionResponseCategory result;

		      Categoryendpoint endpoint = endpointBuilder.build();

		      try {
		        result = endpoint.listCategory().execute();
		      } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        result = null;
		      }
		      return result;
		    }

		    @Override
		    protected void onPostExecute(CollectionResponseCategory result) {	      
		    	mCategoryList=result.getItems();
		    	loadSpinnerData();
				 
		    }
		
		
	}

		  
		   /**
		     * Function to load the spinner data from the category entity
		     * */
		    private void loadSpinnerData() {
		      
		    	BCategory[] objectArray = new BCategory[mCategoryList.size()+1];
		    	objectArray[0]=new BCategory(Long.parseLong("1"),"All");
		    	for (int x = 0; x <mCategoryList.size(); x++) {
		    	 
		    		objectArray[x+1] = new BCategory(mCategoryList.get(x).getKey().getId().longValue(),
		    				 mCategoryList.get(x).getName());
		    	   
		    	  
		    	}
		    	
		    	// Creating adapter for spinner
		        ArrayAdapter<BCategory> dataAdapter = new ArrayAdapter<BCategory>(this,
		                android.R.layout.simple_spinner_item,objectArray);
		        
		        // Drop down layout style - list view with radio button
		        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 
		        // attaching data adapter to spinner
		        spinner.setAdapter(dataAdapter);
		        
		        
		    }
		    
		    
		    
		    
		    /* on item selected on spinner*/
		    
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				BCategory st = (BCategory)spinner.getSelectedItem();
				cat_id=st.id;
					//updatedExpense.setCategoryId(st.id);
				
				
			}






			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
				BCategory st = (BCategory)spinner.getSelectedItem();
					cat_id=st.id;
				
			}
		    
		    
		    
		    
		    /*Fake class to fill the spinner */
		    public class BCategory {
		    	public long id = 0;
		    	public String name = "";
		    	

		    	// A simple constructor for populating  variables 
		    	public BCategory( long _id, String _name)
		    	{
		    	    id = _id;
		    	    name = _name;
		    	   
		    	}

		    	// The toString method is extremely important to making this class work with a Spinner
		    	// (or ListView) object because this is the method called when it is trying to represent
		    	// this object within the control.  If you do not have a toString() method, you WILL
		    	// get an exception.
		    	public String toString()
		    	{
		    	    return( name  );
		    	}
		    	}
		 

		}

