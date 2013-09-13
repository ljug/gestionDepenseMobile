/*created by ahmad chaaban*/
package com.accountingmobile;



import java.util.ArrayList;
import java.util.List;


import com.accountingmobile.expenseendpoint.model.Expense;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class ExpenseAdapter extends BaseAdapter implements Filterable {

	private List<Expense> mExpenseList;
	private List<Expense> mExpenseList1;
	private LayoutInflater mInflater;
	filter_here filter;

	public ExpenseAdapter(List<Expense> list, LayoutInflater inflater) {
		mExpenseList = list;
		mExpenseList1=list;
		mInflater = inflater;
		 filter = new filter_here();
	}

	@Override
	public int getCount() {
		return mExpenseList.size();
	}

	@Override
	public Object getItem(int position) {
		return mExpenseList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewItem item;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.prototype, null);
			item = new ViewItem();

			
			item.ExpenseTitle = (TextView) convertView
					.findViewById(R.id.TextViewPrototype);
			
			convertView.setTag(item);
		} else {
			item = (ViewItem) convertView.getTag();
		}

		Expense expense = mExpenseList.get(position);

		item.ExpenseTitle.setText(expense.getName());	
			
		
		return convertView;
	}

	private class ViewItem {
		TextView ExpenseTitle;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return filter;
	}
	
	
	public class filter_here extends Filter{

       
		@Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
			
            FilterResults Result = new FilterResults();
            // if constraint is empty return the original names
            if(constraint.length() == 0 ){
                Result.values = mExpenseList1;
                Result.count = mExpenseList1.size();
                return Result;
            }

            List<Expense> Filtered_Names=new ArrayList<Expense>();
            String filterString = constraint.toString().toLowerCase();
            Expense filterableExpense;

            for(int i = 0; i<mExpenseList1.size(); i++){
            	filterableExpense = mExpenseList1.get(i);
                if(filterableExpense.getName().toLowerCase().contains(filterString))
                {
                	Filtered_Names.add(filterableExpense);
                }
            }
            Result.values = Filtered_Names;
            Result.count = Filtered_Names.size();

            return Result;
        }

        @SuppressWarnings("unchecked")
		@Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
        	mExpenseList= (List<Expense>) results.values;
        	notifyDataSetChanged();
        	

        }

    }

}


