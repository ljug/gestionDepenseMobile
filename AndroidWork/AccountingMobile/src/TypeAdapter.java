package com.accountingmobile;

import java.util.List;

import com.accountingmobile.expenseendpoint.model.Expense;
import com.accountingmobile.incomeendpoint.model.Income;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class TypeAdapter extends BaseAdapter {

	private List<?> TypeList;
	private LayoutInflater mInflater;
	private boolean g_check;
	

	public TypeAdapter(List<?> list, LayoutInflater inflater,boolean check) {
		TypeList = list;
		g_check=check;
		mInflater = inflater;
		//filter = new filter_here();
	}

	@Override
	public int getCount() {
		return TypeList.size();
	}

	@Override
	public Object getItem(int position) {
		return TypeList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewItem item;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.type_item, null);
			item = new ViewItem();

			
			item.TypeName = (TextView) convertView
					.findViewById(R.id.tvName);

			/*item.TypeCategory = (TextView) convertView
					.findViewById(R.id.tvCategory);*/
			
			item.TypeAmount = (TextView) convertView
					.findViewById(R.id.tvAmount);
			
			item.TypeDate = (TextView) convertView
					.findViewById(R.id.tvDate);
			
			  

			convertView.setTag(item);
		} else {
			item = (ViewItem) convertView.getTag();
		}

		if(g_check)
		{
			Expense expType=(Expense) TypeList.get(position);
			item.TypeName.setText(expType.getName());
			item.TypeAmount.setText(expType.getPrice().toString());
			item.TypeDate.setText(expType.getExpenseDate().toString().substring(0,10));
		}
		
		else
		{
			Income incType=(Income) TypeList.get(position);
			item.TypeName.setText(incType.getName());
			item.TypeAmount.setText(incType.getPrice().toString());
			item.TypeDate.setText(incType.getIncomeDate().toString().substring(0,10));
		}
		
		

	
		
		
		return convertView;
	}

	private class ViewItem {
		
		TextView TypeName;
		//TextView TypeCategory;
		TextView TypeAmount;
		TextView TypeDate;
	
	}

	

}
