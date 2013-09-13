package com.accountingmobile;



import java.util.ArrayList;
import java.util.List;

import com.accountingmobile.categoryendpoint.model.Category;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class CategoryAdapter extends BaseAdapter implements Filterable {

	private List<Category> mCategoryList;
	private List<Category> mCategoryList1;
	private LayoutInflater mInflater;
	filter_here filter;

	public CategoryAdapter(List<Category> list, LayoutInflater inflater) {
		mCategoryList = list;
		mCategoryList1=list;
		mInflater = inflater;
		 filter = new filter_here();
	}

	@Override
	public int getCount() {
		return mCategoryList.size();
	}

	@Override
	public Object getItem(int position) {
		return mCategoryList.get(position);
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

			
			item.CategoryTitle = (TextView) convertView
					.findViewById(R.id.TextViewPrototype);
			
			convertView.setTag(item);
		} else {
			item = (ViewItem) convertView.getTag();
		}

		Category catgory = mCategoryList.get(position);

		item.CategoryTitle.setText(catgory.getName());	
		
		return convertView;
	}

	private class ViewItem {
		TextView CategoryTitle;
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
                Result.values = mCategoryList1;
                Result.count = mCategoryList1.size();
                return Result;
            }

            List<Category> Filtered_Names=new ArrayList<Category>();
            String filterString = constraint.toString().toLowerCase();
            Category filterableCategory;

            for(int i = 0; i<mCategoryList1.size(); i++){
            	filterableCategory = mCategoryList1.get(i);
                if(filterableCategory.getName().toLowerCase().contains(filterString))
                {
                	Filtered_Names.add(filterableCategory);
                }
            }
            Result.values = Filtered_Names;
            Result.count = Filtered_Names.size();

            return Result;
        }

        @SuppressWarnings("unchecked")
		@Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
        	mCategoryList= (List<Category>) results.values;
        	notifyDataSetChanged();
        	

        }

    }

}


