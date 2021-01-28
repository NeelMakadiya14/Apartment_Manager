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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class view_owner_detail extends AppCompatActivity {

    Globalclass globalclass=Globalclass.getInstance();

    ArrayList<String> wingblockList=new ArrayList<>();
    ArrayList<String> namelist=new ArrayList<>();
    ArrayList<String> mobie1List=new ArrayList<>();
    ArrayList<String> mobile2list=new ArrayList<>();
    ArrayList<String> emaillist=new ArrayList<>();
    ArrayList<String> personlist=new ArrayList<>();
    ArrayList<String> vehiclelist=new ArrayList<>();
    ArrayList<String> occupationlist=new ArrayList<>();
    ArrayList<String> houseNolist=new ArrayList<>();


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
        setContentView(R.layout.activity_view_owner_detail);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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
                        String Smobile=ds.child("mobile1").getValue(String.class);
                        String Smobile1=ds.child("mobile2").getValue(String.class);
                        String Semail=ds.child("email").getValue(String.class);
                        String Sperson=ds.child("person").getValue(String.class);
                        String Svehicle=ds.child("vehicle").getValue(String.class);
                        String Soccupation=ds.child("occupation").getValue(String.class);
                        String ShouseNo=ds.child("houseNo").getValue(String.class);

                        wingblockList.add(wing+"-"+block);
                        namelist.add(name);
                        mobie1List.add(Smobile);
                        mobile2list.add(Smobile1);
                        emaillist.add(Semail);
                        personlist.add(Sperson);
                        vehiclelist.add(Svehicle);
                        occupationlist.add(Soccupation);
                        houseNolist.add(ShouseNo);
                    }

                    RecyclerView rv=findViewById(R.id.menu4_rv);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(view_owner_detail.this);
                    rv.setLayoutManager(layoutManager);
                    rv.setAdapter(new CustomAdapter_ViewOwnerdetail(view_owner_detail.this,wingblockList,namelist,mobie1List,mobile2list,emaillist,personlist,vehiclelist,occupationlist,houseNolist));


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(view_owner_detail.this, "Could not Read Data", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


}
