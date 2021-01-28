package com.inc.apartmentmanager;

import com.google.firebase.database.FirebaseDatabase;

public class Firebase extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

