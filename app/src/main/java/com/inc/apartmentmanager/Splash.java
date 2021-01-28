package com.inc.apartmentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Globalclass globalclass=Globalclass.getInstance();
    int progress=0;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        int savedRadioIndex = sharedPreferences.getInt("SAVED_RADIO_BUTTON_INDEX",0);
        if(savedRadioIndex==0){
            setTheme(R.style.MyTheme_Dark_NoActionbar);
        }
        else {
            setTheme(R.style.MyTheme_Light_NoActionbar);
        }
       if (getSupportActionBar()!=null){
           getSupportActionBar().hide();
       }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        progressBar=findViewById(R.id.splash_progressBar);
        setProgressValue(progress);

    }


    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            Intent i=new Intent(Splash.this,sign.class);
            startActivity(i);
            finish();
        }
        else if(currentUser!=null){

            String UID=currentUser.getUid();
            globalclass.setUID(UID);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference(UID);
            myRef.keepSynced(true);
            if(myRef!=null) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String flatname=dataSnapshot.child("flatname").getValue(String.class);
                        globalclass.setName(flatname);
                        String key=dataSnapshot.child("key").getValue(String.class);
                        globalclass.setKey(key);

                        Intent i=new Intent(Splash.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        }
    }

    private void setProgressValue(final int progress) {

        // set the progress
        progressBar.setProgress(progress);
        // thread is used to change the progress value
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10);
            }
        });
        thread.start();
    }

}
