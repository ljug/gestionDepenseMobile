/*created by ahmad chaaban*/
package com.accountingmobile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.accountingmobile.categoryendpoint.Categoryendpoint;
import com.accountingmobile.categoryendpoint.model.Category;
import com.accountingmobile.categoryendpoint.model.CollectionResponseCategory;
import com.accountingmobile.expenseendpoint.Expenseendpoint;
import com.accountingmobile.expenseendpoint.model.Expense;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Spinner;
@SuppressLint("SimpleDateFormat")
public class ExpenseFormActivity extends Activity implements
OnItemSelectedListener{
	protected static Expense updatedExpense;
	Expense expense;
	private List<Category> mCategoryList;
	EditText etExpenseName;
	EditText etExpenseAmount;
	TextView tvExpenseDate;
	Spinner spinner;
	static final int DATE_DIALOG_ID = 999;
	int id_from,id_to,id_test;
	private int year;
	private int month;
	private int day;
	long add_cat_id; 
	SimpleDateFormat dateFormat;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_form);
		
		etExpenseName = (EditText)findViewById(R.id.etExpenseName);
		etExpenseAmount = (EditText)findViewById(R.id.etExpenseAmount);
		tvExpenseDate=(TextView) findViewById(R.id.tvExpenseDate);
		spinner = (Spinner) findViewById(R.id.spinner);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		new ListOfCategoryAsyncRetriever().execute();
		spinner.setOnItemSelectedListener(this);
		tvExpenseDate.setOnClickListener(new OnClickListener() {
			 
			   
			@Override
			   public void onClick(View v) {
				   id_test=v.getId();
				   showDialog(DATE_DIALOG_ID);
			   }
		});
		
		
		if (updatedExpense!=null){
			etExpenseName.setText(updatedExpense.getName());
			etExpenseAmount.setText(updatedExpense.getPrice().toString());
			tvExpenseDate.setText(updatedExpense.getExpenseDate().toString().substring(0, 10));
		}
		else
		{
			etExpenseName.setText("");
			etExpenseAmount.setText("");
			tvExpenseDate.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(year).append("-").append(month + 1).append("-")
				.append(day).append(" "));
			
			
		}
		
		
		
		
		 Button btnSave = (Button) findViewById(R.id.Btn_save_expense);
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
	  * AsyncTask for insert or updating expense *
	  */
	 private class callTasks extends AsyncTask<Void, Void, Void> {

	   @Override
	   protected Void doInBackground(Void... params) {

		   if (updatedExpense!=null)
		   {
			   updatedExpense.setName(etExpenseName.getText().toString().trim());
				
			   updatedExpense.setPrice(Double.parseDouble(etExpenseAmount.getText().toString().trim()));
			   try {
				updatedExpense.setExpenseDate(new DateTime(dateFormat.parse(tvExpenseDate.getText().toString().trim())));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		   else
			   {
			   expense= new Expense();  
			     
				  
			   expense.setName(etExpenseName.getText().toString().trim());
				
			   expense.setPrice(Double.parseDouble(etExpenseAmount.getText().toString().trim()));
			   expense.setCategoryId(add_cat_id);
			   try {
				   expense.setExpenseDate(new DateTime(dateFormat.parse(tvExpenseDate.getText().toString().trim())));
				   expense.setCreatedDate(new DateTime(dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime()))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
		   Expenseendpoint.Builder builder = new Expenseendpoint.Builder(
				         AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				         null);
				         
				     builder = CloudEndpointUtils.updateBuilder(builder);

				     Expenseendpoint endpoint = builder.build();
				     try {
				    	 if (updatedExpense!=null)
						   {
				    		 endpoint.updateExpense(updatedExpense).execute();
						   }
				    	 else
				    	 {
				    		 endpoint.insertExpense(expense).execute();
				    	 }
				       
				     } catch (IOException e) {
				       // TODO Auto-generated catch block
				       e.printStackTrace();
				       
				     }
				     return null;
		   
		 		  
	   }
	 }
	 
	 
	 
	 /*building the calendar*/
	 @Override
		protected Dialog onCreateDialog(int id) {
			switch (id) {
			case DATE_DIALOG_ID:
			   // set date picker as current date
			   return new DatePickerDialog(this, datePickerListener, 
	                         year, month,day);
			}
			return null;
		}
	 
		private DatePickerDialog.OnDateSetListener datePickerListener 
	                = new DatePickerDialog.OnDateSetListener() {
	 
			// when dialog box is closed, below method will be called.
			public void onDateSet(DatePicker view, int selectedYear,
					int selectedMonth, int selectedDay) {
				year = selectedYear;
				month = selectedMonth;
				day = selectedDay;
	 
				// set selected date into text view
				if (id_test==R.id.tvExpenseDate)
				{
					tvExpenseDate.setText(new StringBuilder().append(year)
				   .append("-").append(month + 1).append("-").append(day)
				   .append(" "));
				}
			
				
	 
			}
		};
		
	
		
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
		      
		    	ExpCategory[] objectArray = new ExpCategory[mCategoryList.size()];
		    	
		    	for (int x = 0; x <mCategoryList.size(); x++) {
		    	 
		    		objectArray[x] = new ExpCategory(mCategoryList.get(x).getKey().getId().longValue(),
		    				 mCategoryList.get(x).getName());
		    	   
		    	  
		    	}
		    	
		    	// Creating adapter for spinner
		        ArrayAdapter<ExpCategory> dataAdapter = new ArrayAdapter<ExpCategory>(this,
		                android.R.layout.simple_spinner_item,objectArray);
		        
		        // Drop down layout style - list view with radio button
		        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 
		        // attaching data adapter to spinner
		        spinner.setAdapter(dataAdapter);
		        
		        if (updatedExpense!=null)
				{	
		        	for(int i = 0; i < objectArray.length; ++i) {
		                if(objectArray[i].id == updatedExpense.getCategoryId())
		                		{
		                			spinner.setSelection(i);
		                			i=objectArray.length;
		                		}
		            }
				}
		    }
		    
		    
		    
		    
		    /* on item selected on spinner*/
		    
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				ExpCategory st = (ExpCategory)spinner.getSelectedItem();
				if (updatedExpense!=null)
				{
					updatedExpense.setCategoryId(st.id);
				}
				else
				{
					add_cat_id=st.id;
				}
				
			}






			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
				if (updatedExpense==null)
				{
					add_cat_id=arg0.getAdapter().getItemId(0);
				}
			}
		    
		    
		    
		    
		    /*Fake class to fill the spinner */
		    public class ExpCategory {
		    	public long id = 0;
		    	public String name = "";
		    	

		    	// A simple constructor for populating  variables 
		    	public ExpCategory( long _id, String _name)
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
