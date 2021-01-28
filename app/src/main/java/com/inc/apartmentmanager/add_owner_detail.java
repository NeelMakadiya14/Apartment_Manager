package com.inc.apartmentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class add_owner_detail extends AppCompatActivity {

    Globalclass globalclass=Globalclass.getInstance();

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
        setContentView(R.layout.activity_add_owner_detail);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final ScrollView scrollView=findViewById(R.id.menu1_scroll);
        final EditText wing=findViewById(R.id.menu1_wing);
        final EditText block=findViewById(R.id.menu1_block);
        final EditText owner=findViewById(R.id.menu1_name);
        final EditText mobile=findViewById(R.id.menu1_mobile);
        final EditText mobile1=findViewById(R.id.menu1_mobile1);
        final EditText email=findViewById(R.id.menu1_email);
        final EditText person=findViewById(R.id.menu1_person);
        final EditText vehicle=findViewById(R.id.menu1_vehicle);
        final EditText occupation=findViewById(R.id.menu1_occupation);
        final EditText houseNo=findViewById(R.id.menu1_houseNo);

        Button button=findViewById(R.id.menu1_btn);

        wing.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!(wing.getText().toString().isEmpty() || block.getText().toString().isEmpty())){

                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference(globalclass.getName()).child("Owner detail").child(wing.getText().toString()+"-"+block.getText().toString());
                    myRef.keepSynced(true);
                    if(myRef!=null) {
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name=dataSnapshot.child("owner").getValue(String.class);
                                String Smobile=dataSnapshot.child("mobile1").getValue(String.class);
                                String Smobile1=dataSnapshot.child("mobile2").getValue(String.class);
                                String Semail=dataSnapshot.child("email").getValue(String.class);
                                String Sperson=dataSnapshot.child("person").getValue(String.class);
                                String Svehicle=dataSnapshot.child("vehicle").getValue(String.class);
                                String Soccupation=dataSnapshot.child("occupation").getValue(String.class);
                                String ShouseNo=dataSnapshot.child("houseNo").getValue(String.class);

                                owner.setText(name);
                                mobile.setText(Smobile);
                                mobile1.setText(Smobile1);
                                email.setText(Semail);
                                person.setText(Sperson);
                                vehicle.setText(Svehicle);
                                occupation.setText(Soccupation);
                                houseNo.setText(ShouseNo);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });

        block.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!(wing.getText().toString().isEmpty() || block.getText().toString().isEmpty())){

                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference(globalclass.getName()).child("Owner detail").child(wing.getText().toString()+"-"+block.getText().toString());
                    myRef.keepSynced(true);
                    if(myRef!=null) {
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String name=dataSnapshot.child("owner").getValue(String.class);
                                String Smobile=dataSnapshot.child("mobile1").getValue(String.class);
                                String Smobile1=dataSnapshot.child("mobile2").getValue(String.class);
                                String Semail=dataSnapshot.child("email").getValue(String.class);
                                String Sperson=dataSnapshot.child("person").getValue(String.class);
                                String Svehicle=dataSnapshot.child("vehicle").getValue(String.class);
                                String Soccupation=dataSnapshot.child("occupation").getValue(String.class);
                                String ShouseNo=dataSnapshot.child("houseNo").getValue(String.class);

                                owner.setText(name);
                                mobile.setText(Smobile);
                                mobile1.setText(Smobile1);
                                email.setText(Semail);
                                person.setText(Sperson);
                                vehicle.setText(Svehicle);
                                occupation.setText(Soccupation);
                                houseNo.setText(ShouseNo);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wing.getText().toString().isEmpty()){
                    wing.setError("Required");
                }
                if(block.getText().toString().isEmpty()){
                    block.setError("Required");
                }
                if(owner.getText().toString().isEmpty()){
                    owner.setError("Required");
                }
                if(mobile.getText().toString().isEmpty()){
                    mobile.setError("Required");
                }
                if(mobile.getText().toString().length()!=10){
                    mobile.setError("Enter Valid Mobile Number");
                }
                if(mobile1.getText().toString().length()!=10 && mobile1.getText().toString().length()!=0){
                    mobile1.setError("Enter Valid Mobile Number");
                }
                if (!(wing.getText().toString().isEmpty() || block.getText().toString().isEmpty()|| owner.getText().toString().isEmpty() || mobile.getText().toString().isEmpty() || mobile.getText().toString().length()!=10)){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(globalclass.getName());
                    myRef.keepSynced(true);

                    HashMap<String,Object> result=new HashMap<>();

                    result.put("wing",wing.getText().toString());
                    result.put("block",block.getText().toString());
                    result.put("owner",owner.getText().toString());
                    result.put("mobile1",mobile.getText().toString());
                    result.put("mobile2",mobile1.getText().toString());
                    result.put("email",email.getText().toString());
                    result.put("person",person.getText().toString());
                    result.put("occupation",occupation.getText().toString());
                    result.put("vehicle",vehicle.getText().toString());
                    result.put("houseNo",houseNo.getText().toString());

                    HashMap<String,Object> update=new HashMap<>();
                    update.put(wing.getText().toString()+"-"+block.getText().toString(),result);

                    myRef.child("Owner detail").updateChildren(update);

                    Snackbar.make(scrollView,"Data Saved Successfully ",Snackbar.LENGTH_SHORT).show();

                    Intent i=getIntent();
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }
            }
        });

    }

  /*  @Override
    public void onBackPressed(){
        Intent i=new Intent(add_owner_detail.this,MainActivity.class);
        finish();
        startActivity(i);
    }*/
}
