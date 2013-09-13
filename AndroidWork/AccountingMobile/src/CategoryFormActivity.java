/*created by ahmad chaaban*/
package com.accountingmobile;

import java.io.IOException;

import com.accountingmobile.categoryendpoint.Categoryendpoint;
import com.accountingmobile.categoryendpoint.model.Category;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;
public class CategoryFormActivity extends Activity{
	protected static Category updatedCategory;
	Category category;
	EditText etCatName;
	EditText etCatDesc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_form);
		
		etCatName = (EditText)findViewById(R.id.etCategoryName);
		etCatDesc = (EditText)findViewById(R.id.etCategoryDesc);
		if (updatedCategory!=null){
			etCatName.setText(updatedCategory.getName());
			etCatDesc.setText(updatedCategory.getDescription());
		}
		else
		{
			etCatName.setText("");
			etCatDesc.setText("");
		}
		
		 Button btnSave = (Button) findViewById(R.id.Btn_save);
		 btnSave.setOnClickListener(new OnClickListener() {
		 
		   @Override
		   public void onClick(View v) {
		 
			   new callTasks().execute();
			   Intent MainIntent = new Intent(getBaseContext(),MainActivity.class);
				startActivity(MainIntent);
		   }
		  });
		  
	}
	
	/**
	  * AsyncTask for insert or updating category *
	  */
	 private class callTasks extends AsyncTask<Void, Void, Void> {

	   @Override
	   protected Void doInBackground(Void... params) {

		   if (updatedCategory!=null)
		   {
			   updatedCategory.setName(etCatName.getText().toString().trim());
				
			   updatedCategory.setDescription(etCatDesc.getText().toString().trim());
		   }
		   else
			   {
			   category= new Category();  
			     
				  
			   category.setName(etCatName.getText().toString().trim());
				
			   category.setDescription(etCatDesc.getText().toString().trim());
			   }
			   Categoryendpoint.Builder builder = new Categoryendpoint.Builder(
				         AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				         null);
				         
				     builder = CloudEndpointUtils.updateBuilder(builder);

				     Categoryendpoint endpoint = builder.build();
				     try {
				    	 if (updatedCategory!=null)
						   {
				    		 endpoint.updateCategory(updatedCategory).execute();
						   }
				    	 else
				    	 {
				    		 endpoint.insertCategory(category).execute();
				    	 }
				       
				     } catch (IOException e) {
				       // TODO Auto-generated catch block
				       e.printStackTrace();
				       Toast.makeText(CategoryFormActivity.this,"Remove Failed!", Toast.LENGTH_SHORT).show();
				     }
				     return null;
		   
		 		  
	   }
	 }
	
}
