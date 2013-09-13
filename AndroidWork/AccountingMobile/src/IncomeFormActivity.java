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
import com.accountingmobile.incomeendpoint.Incomeendpoint;
import com.accountingmobile.incomeendpoint.model.Income;
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
public class IncomeFormActivity extends Activity implements
OnItemSelectedListener{
	protected static Income updatedIncome;
	Income income;
	private List<Category> mCategoryList;
	EditText etIncomeName;
	EditText etIncomePayer;
	EditText etIncomeAmount;
	TextView tvIncomeDate;
	Spinner spinnerIncomeCategory;
	Spinner spinner_payment;
	static final int DATE_DIALOG_ID = 999;
	int id_from,id_to,id_test;
	private int year;
	private int month;
	private int day;
	long add_cat_id; 
	String paymtType;
	SimpleDateFormat dateFormat;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income_form);
		
		etIncomeName = (EditText)findViewById(R.id.etIncomeName);
		etIncomeAmount = (EditText)findViewById(R.id.etIncomeAmount);
		etIncomePayer=(EditText) findViewById(R.id.etIncomePayer);
		tvIncomeDate=(TextView) findViewById(R.id.tvIncomeDate);
		spinner_payment = (Spinner) findViewById(R.id.spinner_payment);
		spinnerIncomeCategory = (Spinner) findViewById(R.id.spinnerIncomeCategory);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		new ListOfCategoryAsyncRetriever().execute();
		spinnerIncomeCategory.setOnItemSelectedListener(this);
		spinner_payment.setOnItemSelectedListener(this);
		tvIncomeDate.setOnClickListener(new OnClickListener() {
			 
			   
			@Override
			   public void onClick(View v) {
				   id_test=v.getId();
				   showDialog(DATE_DIALOG_ID);
			   }
		});
		
		
		if (updatedIncome!=null){
			etIncomeName.setText(updatedIncome.getName());
			etIncomeAmount.setText(updatedIncome.getPrice().toString());
			etIncomePayer.setText(updatedIncome.getPayer().toString());
			tvIncomeDate.setText(updatedIncome.getIncomeDate().toString().substring(0, 10));

			
		}
		else
		{
			etIncomeName.setText("");
			etIncomeAmount.setText("");
			etIncomePayer.setText("");
			tvIncomeDate.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(year).append("-").append(month + 1).append("-")
				.append(day).append(" "));
			
			
		}
		
		
		
		
		 Button btnSave = (Button) findViewById(R.id.Btn_save_income);
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
	  * AsyncTask for insert or updating income *
	  */
	 private class callTasks extends AsyncTask<Void, Void, Void> {

	   @Override
	   protected Void doInBackground(Void... params) {

		   if (updatedIncome!=null)
		   {
			 
				updatedIncome.setName(etIncomeName.getText().toString().trim());
				updatedIncome.setPayer(etIncomePayer.getText().toString().trim());
				updatedIncome.setPrice(Double.parseDouble(etIncomeAmount.getText().toString().trim()));
			   try {
				   updatedIncome.setIncomeDate(new DateTime(dateFormat.parse(tvIncomeDate.getText().toString().trim())));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		   else
			   {
			  
			   income= new Income();  
			     
				  
				income.setName(etIncomeName.getText().toString().trim());
				income.setPayer(etIncomePayer.getText().toString().trim());
				income.setPrice(Double.parseDouble(etIncomeAmount.getText().toString().trim()));
				income.setCategoryId(add_cat_id);
				income.setPaymentType(paymtType);
			   try {
				   income.setIncomeDate(new DateTime(dateFormat.parse(tvIncomeDate.getText().toString().trim())));
				   income.setCreatedDate(new DateTime(dateFormat.parse(dateFormat.format(Calendar.getInstance().getTime()))));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
		   Incomeendpoint.Builder builder = new Incomeendpoint.Builder(
				         AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				         null);
				         
				     builder = CloudEndpointUtils.updateBuilder(builder);

				     Incomeendpoint endpoint = builder.build();
				     try {
				    	 if (updatedIncome!=null)
						   {
				    		 endpoint.updateIncome(updatedIncome).execute();
						   }
				    	 else
				    	 {
				    		 endpoint.insertIncome(income).execute();
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
				if (id_test==R.id.tvIncomeDate)
				{
					tvIncomeDate.setText(new StringBuilder().append(year)
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
		      
		    	IncCategory[] objectArray = new IncCategory[mCategoryList.size()];
		    	
		    	for (int x = 0; x <mCategoryList.size(); x++) {
		    	 
		    		objectArray[x] = new IncCategory(mCategoryList.get(x).getKey().getId().longValue(),
		    				 mCategoryList.get(x).getName());
		    	   
		    	  
		    	}
		    	
		    	// Creating adapter for spinnerIncomeCategory
		        ArrayAdapter<IncCategory> dataAdapter = new ArrayAdapter<IncCategory>(this,
		                android.R.layout.simple_spinner_item,objectArray);
		        
		        // Drop down layout style - list view with radio button
		        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 
		        // attaching data adapter to spinner
		        spinnerIncomeCategory.setAdapter(dataAdapter);
		        
		        
		        
		        String[] PaymentMethod=new String[2];
		        PaymentMethod[0]="Check";
		        PaymentMethod[1]="Cash";
		        
		        // Creating adapter for spinner_payment
		        ArrayAdapter<String> PaydataAdapter = new ArrayAdapter<String>(this,
		                android.R.layout.simple_spinner_item,PaymentMethod);
		        
		        // Drop down layout style - list view with radio button
		        PaydataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 
		        // attaching data adapter to spinner
		        spinner_payment.setAdapter(PaydataAdapter);
		        
		        
		        
		        
		        if (updatedIncome!=null)
				{	
		        	for(int i = 0; i < objectArray.length; ++i) {
		                if(objectArray[i].id == updatedIncome.getCategoryId())
		                		{
		                			spinnerIncomeCategory.setSelection(i);
		                			i=objectArray.length;
		                		}
		            }
		        	
		        	for(int i = 0; i < PaymentMethod.length; ++i) {
		        		if(PaymentMethod[i].equals(updatedIncome.getPaymentType()))
		        		{
		        			spinner_payment.setSelection(i);
		        			i=PaymentMethod.length;
		        		}
		        	}
				}
		        
		        

		    }
		    
		    
		    
		    
		    /* on item selected on spinner*/
		    
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Spinner spin_cat = (Spinner)arg0;
		        Spinner spin_pay = (Spinner)arg0;
				if(spin_cat.getId()==R.id.spinnerIncomeCategory)
				{
					IncCategory st = (IncCategory)spinnerIncomeCategory.getSelectedItem();
					if (updatedIncome!=null)
					{
						updatedIncome.setCategoryId(st.id);
					}
					else
					{
						add_cat_id=st.id;
					}
				}
				
				if(spin_pay.getId()==R.id.spinner_payment)
				{
					String payType=(String)spinner_payment.getSelectedItem();
					if (updatedIncome!=null)
					{
						updatedIncome.setPaymentType(payType);
					}
					else
					{
						paymtType=payType;
					}
				}
				
				
			}






			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Spinner spin_cat = (Spinner)arg0;
		        Spinner spin_pay = (Spinner)arg0;
				if(spin_cat.getId()==R.id.spinnerIncomeCategory)
				{
					if (updatedIncome==null)
					{
						add_cat_id=arg0.getAdapter().getItemId(0);
					}
				}
				if(spin_pay.getId()==R.id.spinner_payment)
				{
					if (updatedIncome==null)
					{
						paymtType=arg0.getAdapter().getItem(0).toString();
					}
				}
				
			}
		    
		    
		    
		    
		    /*Fake class to fill the spinner */
		    public class IncCategory {
		    	public long id = 0;
		    	public String name = "";
		    	

		    	// A simple constructor for populating  variables 
		    	public IncCategory( long _id, String _name)
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
