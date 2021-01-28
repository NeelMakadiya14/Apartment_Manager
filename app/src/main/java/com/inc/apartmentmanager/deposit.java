package com.inc.apartmentmanager;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class deposit extends Fragment {

    Globalclass globalclass=Globalclass.getInstance();
    int day, month, year;

    public deposit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.deposit, container, false);

        final Context context = getActivity();
        final MyHelper myHelper = new MyHelper(context);

        Button button2 = rootView.findViewById(R.id.menu2_btn);

        final LinearLayout linearLayout = rootView.findViewById(R.id.depositll);
        final Switch Switch = rootView.findViewById(R.id.switch2);

        final EditText date2 = rootView.findViewById(R.id.menu2_date);
        final EditText amount2 = rootView.findViewById(R.id.menu2_amount);
        final EditText description2 = rootView.findViewById(R.id.menu2_Description);
        final EditText wing2 = rootView.findViewById(R.id.menu2_wing);
        final EditText block2 = rootView.findViewById(R.id.menu2_block);
        final EditText name2=rootView.findViewById(R.id.menu2_name);
        final EditText mobile2=rootView.findViewById(R.id.menu2_mobile);

        final Calendar myCalenar = Calendar.getInstance();
        day = myCalenar.get(Calendar.DAY_OF_MONTH);
        month = myCalenar.get(Calendar.MONTH) + 1;
        year = myCalenar.get(Calendar.YEAR);

        date2.setText(Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year));

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int Year, int monthofYear, int dayofMonth) {
                        myCalenar.set(Calendar.YEAR, Year);
                        myCalenar.set(Calendar.MONTH, monthofYear);
                        myCalenar.set(Calendar.DAY_OF_MONTH, dayofMonth);
                        day = dayofMonth;
                        month = monthofYear + 1;
                        year = Year;
                        date2.setText(dayofMonth + "/" + (monthofYear + 1) + "/" + Year);
                    }
                };
                DatePickerDialog picker = new DatePickerDialog(context, date, myCalenar.get(Calendar.YEAR), myCalenar.get(Calendar.MONTH), myCalenar.get(Calendar.DAY_OF_MONTH));
                picker.show();
            }
        });

        if (Switch.isChecked()) {
            linearLayout.setVisibility(rootView.GONE);
        }

        Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    linearLayout.setVisibility(rootView.GONE);
                }
                else {
                    linearLayout.setVisibility(rootView.getVisibility());
                }
            }
        });

        wing2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!(wing2.getText().toString().isEmpty() || block2.getText().toString().isEmpty())){

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference(globalclass.getName()).child("Owner detail").child(wing2.getText().toString()+"-"+block2.getText().toString());
                    myRef.keepSynced(true);
                    if(myRef!=null) {
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name=dataSnapshot.child("owner").getValue(String.class);
                                String mobile=dataSnapshot.child("mobile1").getValue(String.class);
                                name2.setText(name);
                                mobile2.setText(mobile);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }
            }
        });

        block2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!(wing2.getText().toString().isEmpty() || block2.getText().toString().isEmpty())){

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference(globalclass.getName()).child("Owner detail").child(wing2.getText().toString()+"-"+block2.getText().toString());
                    myRef.keepSynced(true);
                    if(myRef!=null) {
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name=dataSnapshot.child("owner").getValue(String.class);
                                String mobile=dataSnapshot.child("mobile1").getValue(String.class);
                                name2.setText(name);
                                mobile2.setText(mobile);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Switch.isChecked()) {
                    if (amount2.getText().toString().isEmpty()) {
                        amount2.setError("Required");
                    }
                    if (description2.getText().toString().isEmpty()) {
                        description2.setError("Required");
                    }
                    if (!(amount2.getText().toString().isEmpty() || description2.getText().toString().isEmpty())) {
                        myHelper.insert_into_table(day, month, year, amount2.getText().toString(), description2.getText().toString(), "common", 2);
                    }
                } else {
                    if (amount2.getText().toString().isEmpty()) {
                        amount2.setError("Required");
                    }
                    if (description2.getText().toString().isEmpty()) {
                        description2.setError("Required");
                    }
                    if (wing2.getText().toString().isEmpty()) {
                        wing2.setError("Required");
                    }
                    if (block2.getText().toString().isEmpty()) {
                        block2.setError("Required");
                    }
                    if (!(amount2.getText().toString().isEmpty() || description2.getText().toString().isEmpty() || wing2.getText().toString().isEmpty() || block2.getText().toString().isEmpty())) {
                        myHelper.insert_into_table(day, month, year, amount2.getText().toString(), description2.getText().toString(), wing2.getText().toString() + "-" + block2.getText().toString(), 2);
                    }
                }
                upload_income(context);

                Intent i=getActivity().getIntent();
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        return rootView;

    }

    public void upload_income(Context context) {
        MyHelper myHelper = new MyHelper(context);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(globalclass.getName());
        myRef.keepSynced(true);
        // myRef.setValue(null);
        myRef.child("Income").setValue(null);
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM INCOME", null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, Object> result = new HashMap<>();
                result.put("Day", cursor.getInt(1));
                result.put("Month", cursor.getInt(2));
                result.put("Year", cursor.getInt(3));
                result.put("Amount", cursor.getDouble(4));
                result.put("Description", cursor.getString(5));
                result.put("Payee", cursor.getString(6));

                String key = Integer.toString(cursor.getInt(0));
                HashMap<String, Object> childUpdate = new HashMap<>();
                childUpdate.put("raw" + key, result);
                myRef.child("Income").updateChildren(childUpdate);
            } while (cursor.moveToNext());
        }
    }

}
