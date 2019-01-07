package com.example.rk.foodorder;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.*;

/**
 * Created by Rk on 10-04-2018.
 */

public class CategoryAdaptor extends ArrayAdapter{
    List list=new ArrayList();
    public CategoryAdaptor(@NonNull Context context, int resource) {
        super(context, resource);
    }


    public void add(CategoryDetails object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        //return super.getCount();
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row=convertView;
        CategoryHolder categoryHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.category_listview,parent,false);
            categoryHolder=new CategoryHolder();
            categoryHolder.txtName=(TextView)row.findViewById(R.id.txtName);
            row.setTag(categoryHolder);
        }
        else
        {
            categoryHolder=(CategoryHolder)row.getTag();
        }
        CategoryDetails categoryDetails=(CategoryDetails)getItem(position);
        categoryHolder.txtName.setText(categoryDetails.getName());
       // return super.getView(position, convertView, parent);
        return  row;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        //return super.getItem(position);
        return list.get(position);
    }
    static class CategoryHolder
    {
        TextView txtName;

    }
}
