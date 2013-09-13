package com.accountingmobile;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class BilanActivity extends TabActivity {
    /** Called when the activity is first created. */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bilan);
        
        TabHost tabHost = getTabHost();
         
        // Tab for Expense
        TabSpec expspec = tabHost.newTabSpec("Expenses");
        expspec.setIndicator("Expenses");
        Intent expIntent = new Intent(this, ExpenseTabActivity.class);
        expspec.setContent(expIntent);
        
        // Tab for Income
        TabSpec incspec = tabHost.newTabSpec("Incomes");
        incspec.setIndicator("Incomes");
       Intent incIntent = new Intent(this, IncomeTabActivity.class);
       incspec.setContent(incIntent);
        
        // Tab for Balance
        TabSpec balspec = tabHost.newTabSpec("Balance");
        balspec.setIndicator("Balance");
        Intent balIntent = new Intent(this, BalanceTabActivity.class);
        balspec.setContent(balIntent);
        
        // Adding all TabSpec to TabHost
      
        tabHost.addTab(expspec); // Adding expense tab
        tabHost.addTab(incspec); // Adding income tab
        tabHost.addTab(balspec); // Adding balance tab
       
      
    }
    
   
    
}