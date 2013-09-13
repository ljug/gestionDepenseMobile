/*Created by ahmad chaaban*/
package com.accountingmobile;


import java.util.ArrayList;
import java.util.Arrays;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
	ListView mainListView ;
	private ArrayAdapter<String> listAdapter ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		 // Find the ListView resource.   
	    mainListView = (ListView) findViewById( R.id.list_home );  
	  
	    // Create and populate a List of activity names.  
	    String[] array_home = new String[] { "Category", "Expense", "Income","Bilan"};    
	    ArrayList<String> homeList = new ArrayList<String>();  
	    homeList.addAll( Arrays.asList(array_home) );  
	      
	    // Create ArrayAdapter using the home list.  
	    listAdapter = new ArrayAdapter<String>(this, R.layout.list_home_item,R.id.tvRowHome,homeList);  
	      
	    // Set the ArrayAdapter as the ListView's adapter.  
	    mainListView.setAdapter( listAdapter ); 
	    
	    
	    mainListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

				switch (position)
				{
					case 0 : 						
						Intent CategoryIntent = new Intent(getBaseContext(), CategoryListActivity.class);
						startActivity(CategoryIntent);
						break;
					case 1 : 	
						Intent ExpenseIntent = new Intent(getBaseContext(), ExpenseListActivity.class);
						startActivity(ExpenseIntent);
						break;
		    		case 2 :	
		    			Intent IncomeIntent = new Intent(getBaseContext(), IncomeListActivity.class);
						startActivity(IncomeIntent);
		    			break;
		    		case 3 :	
		    			Intent BilanIntent = new Intent(getBaseContext(),BilanFilterActivty.class);
						startActivity(BilanIntent);
		    			break;
				}
				
			}
		});
	    
	}
	
	

}
