package com.inc.apartmentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;

public class BalanceSheet extends AppCompatActivity {

    Globalclass globalclass=Globalclass.getInstance();
    MyHelper myHelper=new MyHelper(BalanceSheet.this);

    ArrayList<String> wingblockList=new ArrayList<>();
    ArrayList<String> namelist=new ArrayList<>();
    ArrayList<String> DepositList=new ArrayList<>();
    ArrayList<String> PaidExpenseList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int savedRadioIndex = sharedPreferences.getInt("SAVED_RADIO_BUTTON_INDEX",0);
        if(savedRadioIndex==0){
            setTheme(R.style.MyTheme_Dark);
        }
        else {
            setTheme(R.style.MyTheme_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_sheet);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView tv1=findViewById(R.id.balance_tv1);
        final TextView tv2=findViewById(R.id.balance_tv2);
        final TextView tv3=findViewById(R.id.balance_tv3);
        TextView tv4=findViewById(R.id.balance_tv4);
        TextView tv5=findViewById(R.id.balance_tv5);
        TextView tv6=findViewById(R.id.balance_tv6);
        TextView tv7=findViewById(R.id.balance_tv7);
        TextView tv8=findViewById(R.id.balance_tv8);

        final Calendar myCalenar = Calendar.getInstance();
        final int year = myCalenar.get(Calendar.YEAR);

        tv8.setText("NOTE : Following Data is from January "+year);
        tv1.setText("Total Expense : "+myHelper.TotalExpense_by_Year(year));
        tv4.setText("Total Income+Deposit : "+myHelper.TotalIncome_by_Year(year));
        tv5.setText("Available Balance : "+(myHelper.TotalIncome_by_Year(year)-myHelper.TotalExpense_by_Year(year)));
        tv6.setText("Total Deposit : "+(myHelper.TotalIncome_by_Year(year)-myHelper.Deposit_of_block_by_year("common",year)));
        tv7.setText("Extra/Common Income : "+myHelper.Deposit_of_block_by_year("common",year));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(globalclass.getName());
        myRef.keepSynced(true);
        if(myRef!=null) {
            DatabaseReference Table1 = myRef.child("Owner detail");
            Table1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String wing=ds.child("wing").getValue(String.class);
                        String block=ds.child("block").getValue(String.class);
                        String name=ds.child("owner").getValue(String.class);

                        wingblockList.add(wing+"-"+block);
                        namelist.add(name);
                        DepositList.add(Double.toString(myHelper.Deposit_of_block_by_year(wing+"-"+block,year)));
                        PaidExpenseList.add(Double.toString(myHelper.expense_paid_by_block_by_year(wing+"-"+block,year)));
                    }

                    tv2.setText("Total Blocks : "+Integer.toString(namelist.size()));
                    double expensePerHead=myHelper.TotalExpense_by_Year(year)/namelist.size();
                    tv3.setText("Expense Per Block : "+Double.toString(expensePerHead));

                    RecyclerView rv=findViewById(R.id.balance_rv);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(BalanceSheet.this);
                    rv.setLayoutManager(layoutManager);
                    rv.setAdapter(new CustomAdapter_Balancesheet(BalanceSheet.this,wingblockList,namelist,DepositList,PaidExpenseList,expensePerHead));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(BalanceSheet.this, "Could not Read Data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
