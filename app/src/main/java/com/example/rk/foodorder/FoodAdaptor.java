package com.example.rk.foodorder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rk on 16-04-2018.
 */

public class FoodAdaptor extends ArrayAdapter {
    Context context1;
    List list=new ArrayList();
    public static final String user="userkey";
    public static final String pass="passkey";


    ArrayList<FoodDetails> foodArrayList =new ArrayList<>();
    public FoodAdaptor(@NonNull Context context, int resource) {
        super(context, resource);
        context1 =context;
    }

    public void add(FoodDetails object) {
        super.add(object);
        list.add(object);
        foodArrayList.add(object);
    }

    @Override
    public int getCount() {
        //return super.getCount();
        return list.size();
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row=convertView;
        final FoodHolder foodHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.food_listview,parent,false);
            foodHolder=new FoodAdaptor.FoodHolder();
            foodHolder.txtName=(TextView)row.findViewById(R.id.txtName);
            foodHolder.txtPrice=(TextView)row.findViewById(R.id.txtPrice);
            foodHolder.txtQty=row.findViewById(R.id.txtQty);
            foodHolder.btnAdd=row.findViewById(R.id.btnAdd);
            row.setTag(foodHolder);
        }
        else
        {
            foodHolder=(FoodAdaptor.FoodHolder)row.getTag();
        }
        FoodDetails foodDetails=(FoodDetails) getItem(position);
        foodHolder.txtName.setText(foodDetails.getName());
        foodHolder.txtPrice.setText(foodDetails.getPrice());
        foodHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un,up;
                SharedPreferences sharedPreferences;
                sharedPreferences=context1.getSharedPreferences(MainActivity.loginPref,0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                un=sharedPreferences.getString(user,null);
                up=sharedPreferences.getString(pass,null);
                //Toast.makeText(context1, "un"+un, Toast.LENGTH_SHORT).show();
                //Toast.makeText(context1, "up"+up, Toast.LENGTH_SHORT).show();
                String fn=foodHolder.txtName.getText().toString();
                String q=foodHolder.txtQty.getText().toString();
                OrderFood orderFood=new OrderFood(context1);
                orderFood.execute(un,up,fn,q);
                //Toast.makeText(context1, "qty"+q.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return  row;
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        //return super.getItem(position);
        return  list.get(position);
    }
    static class FoodHolder
    {
        TextView txtName,txtPrice;
        EditText txtQty;
        Button btnAdd;
    }
}
