package com.inc.apartmentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class sign extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    private int RC_SIGN_IN=2;
    private String TAG="sign";
    LinearLayout signll;
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
        setContentView(R.layout.activity_sign);

        mAuth = FirebaseAuth.getInstance();

        signll=findViewById(R.id.signll);
        progressBar=findViewById(R.id.signpb);

        final Button admin=findViewById(R.id.admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(sign.this,admin.class);
                startActivity(i);
            }
        });

        Button member=findViewById(R.id.member);
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showalertdilogue(view);
                //signInAnonymously();
            }
        });
    }

    private void signInAnonymously(final String flatname) {

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
                            globalclass.setKey("member");
                            globalclass.setUID(UID);
                            HashMap<String,Object> key=new HashMap<>();
                            key.put("key","member");
                            key.put("flatname",flatname);
                            key.put("wing","");
                            key.put("block","");
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef = database.getReference(UID);
                            myRef.updateChildren(key);

                            MainActivity.download_SQLData(sign.this,flatname);

                            Intent i=new Intent(sign.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(sign.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END signin_anonymously]
    }

    public void showalertdilogue(final View view){
        final Context context = sign.this;

        final EditText et4 = new EditText(context);
        et4.setHint("Enter Apartment/Flat Name");
        et4.setPadding(10,10,10,10);
        et4.setTextSize(24);
        et4.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_flat_black_24dp),null,null,null);
        et4.setCompoundDrawablePadding(10);
        LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(25,70,25,0);


        TextInputLayout textInputLayout1=new TextInputLayout(context,null,R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox);
        textInputLayout1.addView(et4);
        textInputLayout1.setLayoutParams(params1);

        final EditText et5 = new EditText(context);
        et5.setHint("Enter Pin");
        et5.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et5.setTransformationMethod(PasswordTransformationMethod.getInstance());
        et5.setPadding(10,10,10,10);
        et5.setTextSize(24);
        et5.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_password_black_24dp),null,null,null);
        et5.setCompoundDrawablePadding(10);

        LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.setMargins(25,20,25,70);


        TextInputLayout textInputLayout2=new TextInputLayout(context,null,R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox);
        textInputLayout2.addView(et5);
        textInputLayout2.setLayoutParams(params2);


        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(textInputLayout1);
        layout.addView(textInputLayout2);


     /*   LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);*/

        TextView textView=new TextView(context);
        textView.setText("Log In as Member");
        textView.setTextColor(Color.WHITE);

        TypedValue typedValue = new TypedValue();
        TypedArray a = sign.this.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorPrimary });
        int color = a.getColor(0, 0);

        textView.setBackgroundColor(color);
        textView.setTextSize(28);
        textView.setPadding(40,20,10,20);

        final AlertDialog.Builder builder = new AlertDialog.Builder(sign.this);
        builder.setView(layout);
        builder.setCustomTitle(textView);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                signll.setVisibility(view.GONE);
                progressBar.setVisibility(view.getVisibility());

                final String flatname=et4.getText().toString();
                final String pin=et5.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference(flatname);
                myRef.keepSynced(true);
                if(myRef!=null) {
                    DatabaseReference BasicInfo = myRef.child("BasicInfo");
                    BasicInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String flatnamefinal=dataSnapshot.child("flatname").getValue(String.class);
                            String pinfinal=dataSnapshot.child("pin").getValue(String.class);
                            System.out.println(flatname);
                            System.out.println(flatnamefinal);
                            System.out.println(pin);
                            System.out.println(pinfinal);
                            if(flatnamefinal!=null){
                                if(pin.equals(pinfinal)){
                                    signInAnonymously(flatname);
                                }
                                else {
                                    Snackbar.make(signll,"Please enter correct Password",Snackbar.LENGTH_LONG).show();
                                    //Toast.makeText(context, "Please enter correct Password", Toast.LENGTH_LONG).show();
                                    signll.setVisibility(view.getVisibility());
                                    progressBar.setVisibility(view.GONE);
                                    showalertdilogue(view);

                                }
                            }
                            else {
                                Snackbar.make(signll,flatname+" doesn't exist.",Snackbar.LENGTH_LONG).show();
                                //Toast.makeText(context, flatname+" doesn't exist.", Toast.LENGTH_LONG).show();
                                signll.setVisibility(view.getVisibility());
                                progressBar.setVisibility(view.GONE);
                                showalertdilogue(view);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(context, "Could not Read Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                signll.setVisibility(view.getVisibility());
                progressBar.setVisibility(view.GONE);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



}
