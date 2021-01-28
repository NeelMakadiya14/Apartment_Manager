package com.inc.apartmentmanager;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Globalclass{

    private String name;
    private String key;
    private String UID;
    private String wing;
    private String block;
    private static Globalclass instance;





    public void setName(String Name){
        name=Name;
    }

    public String getName(){
        return name;
    }

    public void setKey(String Key){
        key=Key;
    }

    public String getKey(){
        return key;
    }

    public void setWing(String Wing){
        wing=Wing;
    }

    public String getWing(){
        return wing;
    }

    public void setBlock(String Block){
        block=Block;
    }

    public String getBlock(){
        return block;
    }

    public void setUID(String Uid){
        UID=Uid;
    }

    public String getUID(){
        return UID;
    }

    public static synchronized Globalclass getInstance(){
        if(instance==null){
            instance=new Globalclass();
        }
        return instance;
    }
}

