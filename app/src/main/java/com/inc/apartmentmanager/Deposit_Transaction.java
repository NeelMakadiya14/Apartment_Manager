package com.inc.apartmentmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;


public class Deposit_Transaction extends Fragment {
    public Deposit_Transaction() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_deposit__transaction, container, false);

        Calendar myCalenar = Calendar.getInstance();
        int year = myCalenar.get(Calendar.YEAR);
        MyHelper myHelper=new MyHelper(getActivity());
        ArrayList<ArrayList<String>> detail=new ArrayList<>();
        detail=myHelper.get_detail(year,2);

        RecyclerView rv=view.findViewById(R.id.deposit_transaction_rv);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(new Custom_Adapter_Transaction(getActivity(),detail.get(0),detail.get(1),detail.get(2),detail.get(3),detail.get(4)));

        return view;
    }

}

