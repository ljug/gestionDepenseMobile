package com.accountingmobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BalanceTabActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance_layout);
        TextView tvAllExpense=(TextView) findViewById(R.id.tvAllExpense);
        TextView tvAllIncome=(TextView) findViewById(R.id.tvAllIncome);
        TextView tvBalance=(TextView) findViewById(R.id.tvBalance);
        
        tvAllExpense.setText("Expense Total: " +String.valueOf(ExpenseTabActivity.Expense_Amount)+" L.L.");
        tvAllIncome.setText("Income Total: " +String.valueOf(IncomeTabActivity.Income_Amount)+" L.L.");
        tvBalance.setText("Balance: " +String.valueOf(IncomeTabActivity.Income_Amount-ExpenseTabActivity.Expense_Amount)+" L.L.");
        
    }
}