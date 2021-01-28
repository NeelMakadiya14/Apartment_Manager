package com.inc.apartmentmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter_Balancesheet extends RecyclerView.Adapter<CustomAdapter_Balancesheet.MyViewHolder> {

    ArrayList<String> wingblockList=new ArrayList<>();
    ArrayList<String> namelist=new ArrayList<>();
    ArrayList<String> DepositList=new ArrayList<>();
    ArrayList<String> PaidExpenseList=new ArrayList<>();
    Double ExpensePerHead;
    Context context;

    public CustomAdapter_Balancesheet(Context context,ArrayList<String> wingblockList,ArrayList<String> namelist,ArrayList<String> DepositList,ArrayList<String> PaidExpenseList,double ExpensePerHead){
        this.context=context;
        this.wingblockList=wingblockList;
        this.DepositList=DepositList;
        this.PaidExpenseList=PaidExpenseList;
        this.namelist=namelist;
        this.ExpensePerHead=ExpensePerHead;
    }

    @NonNull
    @Override
    public CustomAdapter_Balancesheet.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.balancesheet_rv,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter_Balancesheet.MyViewHolder holder, int position) {
        holder.tv1.setText(wingblockList.get(position));
        holder.tv2.setText(namelist.get(position));
        holder.tv3.setText("Deposit : "+DepositList.get(position));
        holder.tv4.setText("Paid Expense : "+PaidExpenseList.get(position));
        holder.tv5.setText("Balance : "+Double.toString(Double.valueOf(DepositList.get(position))-ExpensePerHead));
    }

    @Override
    public int getItemCount() {
        return namelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1=itemView.findViewById(R.id.balance_rv_tv1);
            tv2=itemView.findViewById(R.id.balance_rv_tv2);
            tv3=itemView.findViewById(R.id.balance_rv_tv3);
            tv4=itemView.findViewById(R.id.balance_rv_tv4);
            tv5=itemView.findViewById(R.id.balance_rv_tv5);
        }
    }
}
