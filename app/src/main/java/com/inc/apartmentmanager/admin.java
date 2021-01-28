package com.inc.apartmentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class admin extends AppCompatActivity {

    FirebaseAuth mAuth;
    private int RC_SIGN_IN = 2;
    private String TAG = "admin";
    LinearLayout ll1;
    ScrollView ll2;
    ProgressBar progressBar;
    Globalclass globalclass=Globalclass.getInstance();

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
        setContentView(R.layout.activity_admin);

        mAuth = FirebaseAuth.getInstance();

        ll1 = findViewById(R.id.adminll1);
        ll2 = findViewById(R.id.adminll2);
        progressBar = findViewById(R.id.adminpb);

        Button signin = findViewById(R.id.adminbtn1);
        Button signup1 = findViewById(R.id.adminbtn2);
        Button signup2 = findViewById(R.id.signupbtn);

        final EditText loginname = findViewById(R.id.adminet1);
        final EditText loginpassword = findViewById(R.id.adminet2);

        final EditText name = findViewById(R.id.name);
        final EditText password = findViewById(R.id.passsword);
        final EditText pin = findViewById(R.id.pin);
        final EditText address = findViewById(R.id.address);
        final EditText wings = findViewById(R.id.wings);
        final EditText units = findViewById(R.id.units);
        final EditText mobile = findViewById(R.id.mobile);
        final EditText admin_name=findViewById(R.id.admin_name);


        signup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(view.GONE);
                ll2.setVisibility(view.getVisibility());
                progressBar.setVisibility(view.GONE);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ll2.setVisibility(view.GONE);
                ll1.setVisibility(view.GONE);
                progressBar.setVisibility(view.getVisibility());

                final String flatname = loginname.getText().toString();
                final String password = loginpassword.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference(flatname);
                myRef.keepSynced(true);
                if (myRef != null) {
                    DatabaseReference BasicInfo = myRef.child("BasicInfo");
                    BasicInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String flatnamefinal = dataSnapshot.child("flatname").getValue(String.class);
                            String passwordfinal = dataSnapshot.child("password").getValue(String.class);
                            if (flatnamefinal != null) {
                                if (password.equals(passwordfinal)) {
                                    signInAnonymously(flatname);
                                } else {
                                    Snackbar.make(ll1, "Please enter correct Password", Snackbar.LENGTH_LONG).show();
                                    loginpassword.setError("Wrong Password");
                                    //Toast.makeText(context, "Please enter correct Password", Toast.LENGTH_LONG).show();
                                    ll1.setVisibility(view.getVisibility());
                                    progressBar.setVisibility(view.GONE);
                                    ll2.setVisibility(view.GONE);
                                }
                            } else {
                                Snackbar.make(ll1, flatname + " doesn't exist.", Snackbar.LENGTH_LONG).show();
                                loginname.setError(flatname + " doesn't exist.");
                                //Toast.makeText(context, flatname+" doesn't exist.", Toast.LENGTH_LONG).show();
                                ll1.setVisibility(view.getVisibility());
                                progressBar.setVisibility(view.GONE);
                                ll2.setVisibility(view.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(admin.this, "Could not Read Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(view.getVisibility());
                ll1.setVisibility(view.GONE);
                ll2.setVisibility(view.GONE);

                if (name.getText().toString().isEmpty()) {
                    name.setError("Required");
                    progressBar.setVisibility(view.GONE);
                    ll1.setVisibility(view.GONE);
                    ll2.setVisibility(view.getVisibility());
                }
                else if (admin_name.getText().toString().isEmpty()) {
                    admin_name.setError("Required");
                    progressBar.setVisibility(view.GONE);
                    ll1.setVisibility(view.GONE);
                    ll2.setVisibility(view.getVisibility());
                }
                else if (password.getText().toString().isEmpty()) {
                    password.setError("Required");
                    progressBar.setVisibility(view.GONE);
                    ll1.setVisibility(view.GONE);
                    ll2.setVisibility(view.getVisibility());
                }
                else if (pin.getText().toString().isEmpty()) {
                    pin.setError("Required");
                    progressBar.setVisibility(view.GONE);
                    ll1.setVisibility(view.GONE);
                    ll2.setVisibility(view.getVisibility());
                }
                else if (mobile.getText().toString().isEmpty()) {
                    mobile.setError("Required");
                    progressBar.setVisibility(view.GONE);
                    ll1.setVisibility(view.GONE);
                    ll2.setVisibility(view.getVisibility());
                }
                else if (wings.getText().toString().isEmpty()) {
                    wings.setError("Required");
                    progressBar.setVisibility(view.GONE);
                    ll1.setVisibility(view.GONE);
                    ll2.setVisibility(view.getVisibility());
                }
                else if (units.getText().toString().isEmpty()) {
                    units.setError("Required");
                    progressBar.setVisibility(view.GONE);
                    ll1.setVisibility(view.GONE);
                    ll2.setVisibility(view.getVisibility());
                }
                else if (address.getText().toString().isEmpty()) {
                    address.setError("Required");
                    progressBar.setVisibility(view.GONE);
                    ll1.setVisibility(view.GONE);
                    ll2.setVisibility(view.getVisibility());
                }

              /*  else if(check_name_exist(name.getText().toString())){
                    name.setError("Please type unique name");
                    progressBar.setVisibility(view.GONE);
                    ll1.setVisibility(view.GONE);
                    ll2.setVisibility(view.getVisibility());
                }*/

                else if(mobile.getText().toString().length()!=10){
                    mobile.setError("Please enter valid mobile number");
                    progressBar.setVisibility(view.GONE);
                    ll1.setVisibility(view.GONE);
                    ll2.setVisibility(view.getVisibility());
                }

                else {
                    check_name_exist_and_upload(view,name,admin_name,password,pin,wings,units,mobile,address);
                }

            }
        });

    }

    private void signInAnonymously(final String flatname) {
        // [START signin_anonymously]
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            String UID=user.getUid();
                            globalclass.setName(flatname);
                            globalclass.setKey("admin");
                            globalclass.setUID(UID);
                            HashMap<String,Object> key=new HashMap<>();
                            key.put("key","admin");
                            key.put("flatname",flatname);
                            key.put("wing","");
                            key.put("block","");
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef = database.getReference(UID);
                            myRef.updateChildren(key);

                            MainActivity.download_SQLData(admin.this,flatname);

                            Intent i = new Intent(admin.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(admin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END signin_anonymously]
    }

    public void check_name_exist_and_upload(final View view, final EditText name, final EditText admin_name, final EditText password, final EditText pin, final EditText wings, final EditText units, final EditText mobile, final EditText address) {
        final FirebaseDatabase[] database = {FirebaseDatabase.getInstance()};
        DatabaseReference myRef = database[0].getReference(name.getText().toString());
        myRef.keepSynced(true);
        if (myRef != null) {
            DatabaseReference BasicInfo = myRef.child("BasicInfo");
            BasicInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String passwordfinal = dataSnapshot.child("password").getValue(String.class);
                    if (passwordfinal != null) {
                        System.out.println(passwordfinal);
                        name.setError("Please type unique name");
                        progressBar.setVisibility(view.GONE);
                        ll1.setVisibility(view.GONE);
                        ll2.setVisibility(view.getVisibility());
                    } else {
                        System.out.println("passNULL");

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference(name.getText().toString());
                        myRef.keepSynced(true);
                        //myRef.setValue(null);

                        HashMap<String,Object> result=new HashMap<>();
                        result.put("flatname",name.getText().toString());
                        result.put("admin",admin_name.getText().toString());
                        result.put("pin",pin.getText().toString());
                        result.put("password",password.getText().toString());
                        result.put("wings",wings.getText().toString());
                        result.put("units",units.getText().toString());
                        result.put("mobile",mobile.getText().toString());
                        result.put("address",address.getText().toString());

                        myRef.child("BasicInfo").updateChildren(result);

                        signInAnonymously(name.getText().toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(admin.this, "Sorry", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}