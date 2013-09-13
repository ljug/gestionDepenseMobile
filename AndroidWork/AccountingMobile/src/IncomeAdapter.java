/*created by ahmad chaaban*/
package com.accountingmobile;
import java.util.ArrayList;
import java.util.List;
import com.accountingmobile.incomeendpoint.model.Income;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class IncomeAdapter extends BaseAdapter implements Filterable {

	private List<Income> mIncomeList;
	private List<Income> mIncomeList1;
	private LayoutInflater mInflater;
	filter_here filter;

	public IncomeAdapter(List<Income> list, LayoutInflater inflater) {
		mIncomeList = list;
		mIncomeList1=list;
		mInflater = inflater;
		 filter = new filter_here();
	}

	@Override
	public int getCount() {
		return mIncomeList.size();
	}

	@Override
	public Object getItem(int position) {
		return mIncomeList.get(position);
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

			
			item.IncomeTitle = (TextView) convertView
					.findViewById(R.id.TextViewPrototype);
			
			convertView.setTag(item);
		} else {
			item = (ViewItem) convertView.getTag();
		}

		Income income = mIncomeList.get(position);

		item.IncomeTitle.setText(income.getName());	
			
		
		return convertView;
	}

	private class ViewItem {
		TextView IncomeTitle;
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
                Result.values = mIncomeList1;
                Result.count = mIncomeList1.size();
                return Result;
            }

            List<Income> Filtered_Names=new ArrayList<Income>();
            String filterString = constraint.toString().toLowerCase();
            Income filterableIncome;

            for(int i = 0; i<mIncomeList1.size(); i++){
            	filterableIncome = mIncomeList1.get(i);
                if(filterableIncome.getName().toLowerCase().contains(filterString))
                {
                	Filtered_Names.add(filterableIncome);
                }
            }
            Result.values = Filtered_Names;
            Result.count = Filtered_Names.size();

            return Result;
        }

        @SuppressWarnings("unchecked")
		@Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
        	mIncomeList= (List<Income>) results.values;
        	notifyDataSetChanged();
        	

        }

    }

}


