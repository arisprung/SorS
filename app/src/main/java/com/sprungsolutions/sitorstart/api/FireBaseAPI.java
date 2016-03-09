package com.sprungsolutions.sitorstart.api;

import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by arisprung on 3/8/16.
 */
public class FireBaseAPI {

    private static FireBaseAPI instance = null;
    private Firebase myFirebaseRef = null;

    private FireBaseAPI(Context context){
        super();

        Firebase.setAndroidContext(context);
        myFirebaseRef = new Firebase("https://dazzling-heat-6818.firebaseio.com/");
    }

    public static FireBaseAPI getInstance(){
        if (instance == null)
            throw new NullPointerException("Wrong getInstance was called before providing a context.");

        return instance;
    }

    public static FireBaseAPI getInstance(Context context){
        if (instance == null)
            instance = new FireBaseAPI(context);

        return instance;
    }

    public void write(){
        myFirebaseRef.child("message").setValue("Do you have data? You'll love Firebase.");
    }
}
