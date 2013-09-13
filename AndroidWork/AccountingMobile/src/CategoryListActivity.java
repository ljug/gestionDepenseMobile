/*created by ahmad chaaban*/
package com.accountingmobile;


import java.io.IOException;
import java.util.List;

import com.accountingmobile.categoryendpoint.Categoryendpoint;
import com.accountingmobile.categoryendpoint.model.Category;
import com.accountingmobile.categoryendpoint.model.CollectionResponseCategory;
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

public class CategoryListActivity extends Activity {
	
	private List<Category> mCategoryList;
	private EditText et;
	CategoryAdapter adapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_list);

	    et = (EditText)findViewById(R.id.etCategorySearch);
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
		 
		 
		 new ListOfCategoryAsyncRetriever().execute();	
		
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
	    	// Create the list
			ListView listViewCategory = (ListView)findViewById(R.id.list_category);
			 adapter=new CategoryAdapter(mCategoryList, getLayoutInflater());
			 
			 listViewCategory.setAdapter(adapter);
			 
			 
			 listViewCategory.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
						Category selectedCategory=(Category) adapter.getItem(position);
						CategoryActivity.currentCategory=selectedCategory;
						Intent CategoryIntent = new Intent(getBaseContext(),CategoryActivity.class);				
						startActivity(CategoryIntent);
						
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
				CategoryFormActivity.updatedCategory=null;
				startActivity(new Intent(getBaseContext(), CategoryFormActivity.class));
			}
			
			return true;
		}
	  

}