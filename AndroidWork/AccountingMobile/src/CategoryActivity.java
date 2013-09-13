/*created by ahmad chaaban*/
package com.accountingmobile;
import java.io.IOException;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;
import android.os.AsyncTask;
import com.accountingmobile.categoryendpoint.Categoryendpoint;
import com.accountingmobile.categoryendpoint.model.Category;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;


public class CategoryActivity extends Activity {
	protected static Category currentCategory;
	TextView tvCategoryName;
	TextView tvCategoryDesc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);
		
		tvCategoryDesc = (TextView) findViewById(R.id.tvCategoryDesc);
		tvCategoryName = (TextView) findViewById(R.id.tvCategoryName);
		tvCategoryName.setText(currentCategory.getName());
		tvCategoryDesc.setText(currentCategory.getDescription());
		
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
				
				CategoryFormActivity.updatedCategory=currentCategory;
				Intent CategoryFormIntent = new Intent(getBaseContext(),CategoryFormActivity.class);
				startActivity(CategoryFormIntent);
			}
			else if (item.getItemId() == R.id.Delete)
			{
				new RemoveCategoryAsync().execute();
				Intent MainIntent = new Intent(getBaseContext(),MainActivity.class);
				startActivity(MainIntent);
			}
			
			return true;
		}
		
		
		 /* AsyncTask for removing a category
		   */
		  private class RemoveCategoryAsync extends AsyncTask<Void, Void, Void> {

		    @Override
		    protected Void doInBackground(Void... params) {


		    	Categoryendpoint.Builder endpointBuilder = new Categoryendpoint.Builder(
		          AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
		     
		      endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);

		      Categoryendpoint endpoint = endpointBuilder.build();

		      try {
		        endpoint.removeCategory(currentCategory.getKey().getId().longValue()).execute();
				
		      } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();        
		      }
		      return null;
		    }
		    
		  }	
	

}