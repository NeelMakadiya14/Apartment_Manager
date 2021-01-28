package com.inc.apartmentmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MenuItem;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Globalclass globalclass=Globalclass.getInstance();
    MyHelper myHelper=new MyHelper(MainActivity.this);
    int year;
    int savedRadioIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        savedRadioIndex = sharedPreferences.getInt("SAVED_RADIO_BUTTON_INDEX",0);
        if(savedRadioIndex==0){
            setTheme(R.style.MyTheme_Dark_NoActionbar);
        }
        else {
            setTheme(R.style.MyTheme_Light_NoActionbar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppBarLayout appBarLayout=findViewById(R.id.appBar);
        if(savedRadioIndex==0){
            appBarLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(globalclass.getKey().charAt(0)=='a'){
                    Intent i=new Intent(MainActivity.this,add.class);
                    startActivity(i);
                }
                else {
                    Snackbar.make(view,"Sorry, You don't have a right to edit data.",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout main_admin_dashboard=findViewById(R.id.main_admin_dashboard);

        CardView notice_admin=findViewById(R.id.card1);
        final CardView notice_member=findViewById(R.id.card2);
        final CardView personalise=findViewById(R.id.card3);
        CardView memberDetail=findViewById(R.id.card5);

        LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
        ViewGroup viewGroup=findViewById(android.R.id.content);
        final View view=inflater.inflate(R.layout.activity_main,viewGroup,false);

        TextInputLayout textInputLayout=findViewById(R.id.notice_textinputlayout);
        final LinearLayout linearLayout_member_notice=findViewById(R.id.notice_ll);


        if(globalclass.getKey().charAt(0)=='m'){

            Menu menu=navigationView.getMenu();
            menu.findItem(R.id.add_owner).setVisible(false);

            CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
            p.setAnchorId(View.NO_ID);
            fab.setLayoutParams(p);
            fab.setVisibility(View.GONE);

            notice_admin.setVisibility(view.GONE);
            textInputLayout.setVisibility(view.GONE);

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference(globalclass.getName()).child("notice");
            myRef.keepSynced(true);
            if(myRef!=null) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String notice=dataSnapshot.child("notice1").getValue(String.class);
                        if(notice!=null){
                            if(!(notice.isEmpty())){
                                TextView tv=findViewById(R.id.member_notice);
                                linearLayout_member_notice.setVisibility(view.getVisibility());
                                notice_member.setVisibility(view.getVisibility());
                                tv.setText(notice);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

        if(globalclass.getKey().charAt(0)=='a'){
            textInputLayout.setVisibility(view.getVisibility());
            notice_admin.setVisibility(view.getVisibility());
            linearLayout_member_notice.setVisibility(view.GONE);
            notice_member.setVisibility(view.GONE);

            final TextInputEditText textInputEditText=findViewById(R.id.notice);

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference(globalclass.getName()).child("notice");
            myRef.keepSynced(true);
            if(myRef!=null) {
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String notice=dataSnapshot.child("notice1").getValue(String.class);
                        if(notice!=null){
                            if(!(notice.isEmpty())){
                                textInputEditText.setText(notice);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String notice=textInputEditText.getText().toString();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(globalclass.getName());
                    myRef.keepSynced(true);

                    HashMap<String,Object> result=new HashMap<>();

                    result.put("notice1",notice);
                    myRef.child("notice").updateChildren(result);

                    Toast.makeText(MainActivity.this,"Notice is Published", Toast.LENGTH_SHORT).show();

                }
            });
        }

        personalise_experience();
        update_nav_header_member_dashboard();

        final Calendar myCalenar = Calendar.getInstance();
        year = myCalenar.get(Calendar.YEAR);

        TextView tv1=findViewById(R.id.main_admin_tv1);
        TextView tv2=findViewById(R.id.main_admin_tv2);
        TextView tv3=findViewById(R.id.main_admin_tv3);
        TextView tv4=findViewById(R.id.main_admin_tv4);
        TextView tv5=findViewById(R.id.main_admin_tv5);
        TextView tv6=findViewById(R.id.main_admin_tv6);
        TextView tv7=findViewById(R.id.main_admin_tv7);
        TextView tv8=findViewById(R.id.main_admin_tv8);

        tv1.setText("NOTE : Following Data is from January "+year);
        tv2.setText("Expense Paid From Common Fund : "+myHelper.expense_paid_by_block_by_year("common",year));
        tv3.setText("Expense Paid by Member : "+(myHelper.TotalExpense_by_Year(year)-myHelper.expense_paid_by_block_by_year("common",year)));
        tv4.setText("Total Expense : "+myHelper.TotalExpense_by_Year(year));
        tv5.setText("Total Deposit : "+(myHelper.TotalIncome_by_Year(year)-myHelper.Deposit_of_block_by_year("common",year)));
        tv6.setText("Extra/Common Income : "+myHelper.Deposit_of_block_by_year("common",year));
        tv7.setText("Total Income+Deposit : "+myHelper.TotalIncome_by_Year(year));
        tv8.setText("Available Common Fund : "+(myHelper.TotalIncome_by_Year(year)-myHelper.TotalExpense_by_Year(year)));

        Button personalise_btn=findViewById(R.id.personalise_btn);
        personalise_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout linearLayout=findViewById(R.id.personalise_ll);

                EditText wing=findViewById(R.id.main_wing);
                EditText block=findViewById(R.id.main_block);

                globalclass.setBlock(block.getText().toString());
                globalclass.setWing(wing.getText().toString());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference(globalclass.getUID());
                myRef.keepSynced(true);
                HashMap<String,Object> data=new HashMap<>();
                data.put("wing",wing.getText().toString());
                data.put("block",block.getText().toString());

                myRef.updateChildren(data);

                update_nav_header_member_dashboard();

                linearLayout.setVisibility(view.GONE);
                personalise.setVisibility(view.GONE);

            }
        });

        ArrayList<ArrayList<String>> detail_expense=new ArrayList<>();
        ArrayList<ArrayList<String>> detail_deposit=new ArrayList<>();
        detail_expense=myHelper.get_detail_last(year,1,5);
        detail_deposit=myHelper.get_detail_last(year,2,5);

        RecyclerView expense_rv=findViewById(R.id.main_expense_last);
        RecyclerView deposit_rv=findViewById(R.id.main_deposit_last);

        LinearLayoutManager layoutManager1=new LinearLayoutManager(MainActivity.this);
        LinearLayoutManager layoutManager2=new LinearLayoutManager(MainActivity.this);

        expense_rv.setLayoutManager(layoutManager1);
        deposit_rv.setLayoutManager(layoutManager2);

        expense_rv.setAdapter(new Custom_Adapter_Transaction(MainActivity.this,detail_expense.get(0),detail_expense.get(1),detail_expense.get(2),detail_expense.get(3),detail_expense.get(4)));
        deposit_rv.setAdapter(new Custom_Adapter_Transaction(MainActivity.this,detail_deposit.get(0),detail_deposit.get(1),detail_deposit.get(2),detail_deposit.get(3),detail_deposit.get(4)));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.theme){
            final CharSequence[] items = {" Dark Theme "," Light Theme "};

            AlertDialog dialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Theme");
            builder.setSingleChoiceItems(items, savedRadioIndex, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    switch(item)
                    {
                        case 0:
                            Toast.makeText(MainActivity.this, "Dark Theme Applied", Toast.LENGTH_SHORT).show();
                            SavePreferences("SAVED_RADIO_BUTTON_INDEX", 0);
                            if(savedRadioIndex==1){
                                Intent intent=getIntent();
                                finish();
                                startActivity(intent);
                            }
                            break;
                        case 1:
                            Toast.makeText(MainActivity.this, "Light Theme Applied", Toast.LENGTH_SHORT).show();
                            SavePreferences("SAVED_RADIO_BUTTON_INDEX", 1);
                            if(savedRadioIndex==0){
                                Intent intent=getIntent();
                                finish();
                                startActivity(intent);
                            }
                            break;
                    }
                    dialog.dismiss();
                }
            });
            dialog = builder.create();
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_owner) {
            Intent intent=new Intent(MainActivity.this,add_owner_detail.class);
            startActivity(intent);
        } else if (id == R.id.view_owner_detail) {
            Intent intent=new Intent(MainActivity.this,view_owner_detail.class);
            startActivity(intent);
        } else if (id == R.id.view_owner_balance) {
            Intent intent=new Intent(MainActivity.this,BalanceSheet.class);
            startActivity(intent);
        } else if (id == R.id.view_transaction) {
            Intent intent=new Intent(MainActivity.this,View_Transaction.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.about) {

        } else if(id==R.id.signOut){
            signOut();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();

        globalclass.setWing(null);
        globalclass.setBlock(null);
        String UID=globalclass.getUID();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(UID);
        myRef.keepSynced(true);
        myRef.setValue(null);
      /*  GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("755521298480-i18m1nr2s1idafvj3ndlcojc7n7kolot.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            }
        });*/
        Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(MainActivity.this,sign.class);
        startActivity(i);
        finish();
    }

    public void personalise_experience(){

        final LinearLayout linearLayout=findViewById(R.id.personalise_ll);
        LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
        ViewGroup viewGroup=findViewById(android.R.id.content);
        final View view=inflater.inflate(R.layout.activity_main,viewGroup,false);

        final CardView personalise=findViewById(R.id.card3);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(globalclass.getUID());
        myRef.keepSynced(true);
        if(myRef!=null) {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String Wing=dataSnapshot.child("wing").getValue().toString();
                    String Block=dataSnapshot.child("block").getValue().toString();
                    if(Block.length()!=0){
                        globalclass.setWing(Wing);
                        globalclass.setBlock(Block);
                        update_nav_header_member_dashboard();

                    }
                    else {
                        linearLayout.setVisibility(view.getVisibility());
                        personalise.setVisibility(view.getVisibility());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void update_nav_header_member_dashboard(){
        NavigationView navigationView=findViewById(R.id.nav_view);
        View HeaderView=navigationView.getHeaderView(0);

        CardView memberDetail=findViewById(R.id.card5);

        LayoutInflater inflater=LayoutInflater.from(MainActivity.this);
        ViewGroup viewGroup=findViewById(android.R.id.content);
        final View view=inflater.inflate(R.layout.activity_main,viewGroup,false);

        final LinearLayout member_dashboard=findViewById(R.id.main_member_dashboard);
        final TextView tv9=findViewById(R.id.main_member_tv1);
        final TextView tv10=findViewById(R.id.main_member_tv2);
        final TextView tv11=findViewById(R.id.main_member_tv3);
        final TextView tv12=findViewById(R.id.main_member_tv4);

        TextView tv1=HeaderView.findViewById(R.id.nav_tv1);
        TextView tv2=HeaderView.findViewById(R.id.nav_tv2);
        TextView tv3=HeaderView.findViewById(R.id.nav_tv3);

        tv1.setText(globalclass.getName());
        tv2.setText(globalclass.getKey());
        if(globalclass.getBlock()!=null){
            tv3.setText(globalclass.getWing()+"-"+globalclass.getBlock());
            member_dashboard.setVisibility(view.getVisibility());
            memberDetail.setVisibility(view.getVisibility());
            member_balance_dashboard(tv9,tv10,tv11,tv12);
        }

       /* String key=globalclass.getKey();
        if(key.charAt(0)=='a'){
            tv3.setVisibility(HeaderView.GONE);
        }*/
    }

    public void member_balance_dashboard(TextView tv1, final TextView tv2, final TextView tv3, final TextView tv4){
        tv1.setText("Deposit Paid by You : "+myHelper.Deposit_of_block_by_year(globalclass.getWing()+"-"+globalclass.getBlock(),year));
        tv2.setText("Expense Paid by You : "+myHelper.expense_paid_by_block_by_year(globalclass.getWing()+"-"+globalclass.getBlock(),year));

        final int[] totalBlock = {0};
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(globalclass.getName());
        myRef.keepSynced(true);
        if(myRef!=null) {
            DatabaseReference Table1 = myRef.child("Owner detail");
            Table1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        totalBlock[0]++;
                    }

                    double expensePerHead = myHelper.TotalExpense_by_Year(year) / totalBlock[0];
                    tv3.setText("Expense Per Block : " + Double.toString(expensePerHead));
                    tv4.setText("Your Balance : " + (myHelper.Deposit_of_block_by_year(globalclass.getWing() + "-" + globalclass.getBlock(), year) - expensePerHead));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Could not Read Data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void SavePreferences(String key, int value){
        SharedPreferences sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void download_SQLData(final Context context,String flatname){
        final MyHelper myHelper=new MyHelper(context);

        myHelper.onUpgrade(myHelper.getReadableDatabase(),1,2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(flatname);
        myRef.keepSynced(true);
        if(myRef!=null) {
            DatabaseReference Table1 = myRef.child("Expense");
            Table1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        int Day = ds.child("Day").getValue(Integer.class);
                        int Month = ds.child("Month").getValue(Integer.class);
                        int Year = ds.child("Year").getValue(Integer.class);
                        Double Amount = ds.child("Amount").getValue(Double.class);
                        String amount = Double.toString(Amount);
                        String Description = ds.child("Description").getValue(String.class);
                        String Payee = ds.child("Payee").getValue(String.class);

                        myHelper.insert_into_table(Day, Month, Year, amount, Description, Payee, 1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, "Could not Read Data", Toast.LENGTH_SHORT).show();
                }
            });

            DatabaseReference Table2 = myRef.child("Income");
            Table2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        int Day = ds.child("Day").getValue(Integer.class);
                        int Month = ds.child("Month").getValue(Integer.class);
                        int Year = ds.child("Year").getValue(Integer.class);
                        Double Amount = ds.child("Amount").getValue(Double.class);
                        String amount = Double.toString(Amount);
                        String Description = ds.child("Description").getValue(String.class);
                        String Payee = ds.child("Payee").getValue(String.class);

                        myHelper.insert_into_table(Day, Month, Year, amount, Description, Payee, 2);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, "Could not Read Data", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
