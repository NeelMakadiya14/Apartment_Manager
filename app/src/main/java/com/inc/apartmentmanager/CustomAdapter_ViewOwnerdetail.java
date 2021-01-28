package com.inc.apartmentmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaCasException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter_ViewOwnerdetail extends RecyclerView.Adapter<CustomAdapter_ViewOwnerdetail.MyViewHolder> {

    ArrayList<String> wingblockList=new ArrayList<>();
    ArrayList<String> namelist=new ArrayList<>();
    ArrayList<String> mobie1List=new ArrayList<>();
    ArrayList<String> mobile2list=new ArrayList<>();
    ArrayList<String> emaillist=new ArrayList<>();
    ArrayList<String> personlist=new ArrayList<>();
    ArrayList<String> vehiclelist=new ArrayList<>();
    ArrayList<String> occupationlist=new ArrayList<>();
    ArrayList<String> houseNolist=new ArrayList<>();

    Context context;

    public CustomAdapter_ViewOwnerdetail(Context context,ArrayList<String> wingblockList,ArrayList<String> namelist,ArrayList<String> mobie1List,ArrayList<String> mobie2List,ArrayList<String> emaillist,ArrayList<String> personlist,ArrayList<String> vehiclelist,ArrayList<String> occupationlist,ArrayList<String> houseNolist){
        this.context=context;
        this.namelist=namelist;
        this.wingblockList=wingblockList;
        this.mobie1List=mobie1List;
        this.mobile2list=mobie2List;
        this.emaillist=emaillist;
        this.personlist=personlist;
        this.vehiclelist=vehiclelist;
        this.occupationlist=occupationlist;
        this.houseNolist=houseNolist;
    }


    @NonNull
    @Override
    public CustomAdapter_ViewOwnerdetail.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.view_owner_detail_rv,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter_ViewOwnerdetail.MyViewHolder holder, final int position) {

        holder.tv3.setText(wingblockList.get(position));
        holder.tv1.setText(namelist.get(position));
        holder.tv2.setText(mobie1List.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView tv1=new TextView(context);
                tv1.setText(namelist.get(position));
                tv1.setTextSize(context.getResources().getDimension(R.dimen.font));
                tv1.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_person_black_24dp),null,null,null);
                tv1.setCompoundDrawablePadding(10);
                tv1.setPadding(10,10,10,10);

                TextView tv2=new TextView(context);
                tv2.setText(mobie1List.get(position));
                tv2.setTextSize(context.getResources().getDimension(R.dimen.font));
                tv2.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_phone_black_24dp),null,null,null);
                tv2.setCompoundDrawablePadding(10);
                tv2.setPadding(10,10,10,10);

                TextView tv3=new TextView(context);
                tv3.setText(mobile2list.get(position));
                tv3.setTextSize(context.getResources().getDimension(R.dimen.font));
                tv3.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_phone_black_24dp),null,null,null);
                tv3.setCompoundDrawablePadding(10);
                tv3.setPadding(10,10,10,10);

                TextView tv4=new TextView(context);
                tv4.setText(emaillist.get(position));
                tv4.setTextSize(context.getResources().getDimension(R.dimen.font));
                tv4.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_email_black_24dp),null,null,null);
                tv4.setCompoundDrawablePadding(10);
                tv4.setPadding(10,10,10,10);

                TextView tv5=new TextView(context);
                tv5.setText("No. of Person : "+personlist.get(position));
                tv5.setTextSize(context.getResources().getDimension(R.dimen.font));
                tv5.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_people_black_24dp),null,null,null);
                tv5.setCompoundDrawablePadding(10);
                tv5.setPadding(10,10,10,10);

                TextView tv6=new TextView(context);
                tv6.setText("No. of Vehicle : "+vehiclelist.get(position));
                tv6.setTextSize(context.getResources().getDimension(R.dimen.font));
                tv6.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_car_black_24dp),null,null,null);
                tv6.setCompoundDrawablePadding(10);
                tv6.setPadding(10,10,10,10);

                TextView tv7=new TextView(context);
                tv7.setText(occupationlist.get(position));
                tv7.setTextSize(context.getResources().getDimension(R.dimen.font));
                tv7.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_work_black_24dp),null,null,null);
                tv7.setCompoundDrawablePadding(10);
                tv7.setPadding(10,10,10,10);

                TextView tv8=new TextView(context);
                tv8.setText(houseNolist.get(position));
                tv8.setTextSize(context.getResources().getDimension(R.dimen.font));
                tv8.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_home_black_24dp),null,null,null);
                tv8.setCompoundDrawablePadding(10);
                tv8.setPadding(10,10,10,10);

                LinearLayout linearLayout=new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(tv1);
                linearLayout.addView(tv2);
                linearLayout.addView(tv3);
                linearLayout.addView(tv4);
                linearLayout.addView(tv5);
                linearLayout.addView(tv6);
                linearLayout.addView(tv7);
                linearLayout.addView(tv8);

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setView(linearLayout);
                builder.setTitle(wingblockList.get(position));

                AlertDialog dialog=builder.create();
                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return wingblockList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv1;
        TextView tv2;
        TextView tv3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1=itemView.findViewById(R.id.menu4_rv_tv1);
            tv2=itemView.findViewById(R.id.menu4_rv_tv2);
            tv3=itemView.findViewById(R.id.menu4_rv_tv3);
        }
    }
}
