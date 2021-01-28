package com.inc.apartmentmanager;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Expense_Transaction extends Fragment {


    public Expense_Transaction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.expense__transaction, container, false);

        Calendar myCalenar = Calendar.getInstance();
        int year = myCalenar.get(Calendar.YEAR);
        MyHelper myHelper=new MyHelper(getActivity());
        ArrayList<ArrayList<String>> detail=new ArrayList<>();
        detail=myHelper.get_detail(year,1);

        RecyclerView rv=view.findViewById(R.id.expense_transaction_rv);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(new Custom_Adapter_Transaction(getActivity(),detail.get(0),detail.get(1),detail.get(2),detail.get(3),detail.get(4)));


        return view;
    }

}
