package com.inc.apartmentmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Custom_Adapter_Transaction extends RecyclerView.Adapter<Custom_Adapter_Transaction.MyViewHolder> {

    ArrayList<String> datelist=new ArrayList<>();
    ArrayList<String> namelist=new ArrayList<>();
    ArrayList<String> categorylist=new ArrayList<>();
    ArrayList<String> amountlist=new ArrayList<>();
    ArrayList<String> descriptionlist=new ArrayList<>();
    Context context;

    public Custom_Adapter_Transaction(Context context,ArrayList<String> datelist,ArrayList<String> namelist,ArrayList<String> categorylist,ArrayList<String> amountlist,ArrayList<String> descriptionlist){
        this.context=context;
        this.datelist=datelist;
        this.namelist=namelist;
        this.categorylist=categorylist;
        this.amountlist=amountlist;
        this.descriptionlist=descriptionlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.transaction_rv,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv1.setText(datelist.get(position));
        holder.tv2.setText(namelist.get(position));
        if(categorylist.size()==0){
            holder.tv3.setText(null);
            holder.tv3.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }
        else {
            holder.tv3.setText(categorylist.get(position));
        }
        holder.tv4.setText(amountlist.get(position));
        holder.tv5.setText(descriptionlist.get(position));
    }

    @Override
    public int getItemCount() {
        return datelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.exoense_transaction_rv_tv1);
            tv2=itemView.findViewById(R.id.exoense_transaction_rv_tv2);
            tv3=itemView.findViewById(R.id.exoense_transaction_rv_tv3);
            tv4=itemView.findViewById(R.id.exoense_transaction_rv_tv4);
            tv5=itemView.findViewById(R.id.exoense_transaction_rv_tv5);
        }
    }
}
