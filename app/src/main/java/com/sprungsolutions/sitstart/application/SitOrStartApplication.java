package com.sprungsolutions.sitstart.application;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.crashlytics.android.Crashlytics;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.sprungsolutions.sitstart.R;
import com.sprungsolutions.sitstart.pick_player.NewPlayerSet;
import com.sprungsolutions.sitstart.pick_player.Player;
import com.sprungsolutions.sitstart.utility.SSSharedPreferencesManager;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by arisprung on 3/9/16.
 */
public class SitOrStartApplication extends Application {

    public static int[][] mColorArray;
    public static int[][] mColorSelectedArray;
    private static SitOrStartApplication instance;
    private Firebase myFirebaseRef;
    private ArrayList<Player> mPlayerArrayList;
    private ArrayList<NewPlayerSet> mPlayerSetArrayList;
    private  boolean mIsFireBaseInitLoaded;
    private SSSharedPreferencesManager ssPrefrenceManager;


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Firebase.setAndroidContext(this);
        initSelectors();
        instance = this;
        TypefaceProvider.registerDefaultIconSets();
        ssPrefrenceManager = SSSharedPreferencesManager.getInstance(getApplicationContext());
        myFirebaseRef = new Firebase(getString(R.string.firebase_url));
       init();

    }

    private void init() {

        mIsFireBaseInitLoaded = true;
        SitOrStartApplication.getInstance().getMyFirebaseRef().child("sports/mlb/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                mPlayerArrayList  = new ArrayList<Player>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    if (postSnapshot.getKey().equals("mlb_players")) {
                        for(HashMap<String,String> hashMap :(ArrayList<HashMap<String,String>>)postSnapshot.getValue()){
                            Player player = new Player();

                            String name = hashMap.get("mlb_name");
                            String bats = hashMap.get("bats");
                            String arm_throws = hashMap.get("arm_throws");
                            String position = hashMap.get("mlb_pos");
                            String mlbid = hashMap.get("mlb_id");
                            String mlbteam = hashMap.get("mlb_team");
                            String mlblongteam = hashMap.get("mlb_team_long");
                            String mlbBithYear = hashMap.get("birth_year");
                            player.setMlb_name(name);
                            player.setArm_throws(arm_throws);
                            player.setMlb_pos(position);
                            player.setMlb_id(mlbid);
                            player.setBats(bats);
                            player.setMlb_team(mlbteam);
                            player.setMlb_team_long(mlblongteam);
                            player.setBirth_year(mlbBithYear);
                            mPlayerArrayList.add(player);


                        }




//                        for(Player p: mPlayerArrayList){
//
//                        }
                    } else if (postSnapshot.getKey().equals("settings")) {

                        for (DataSnapshot postSnapshot1 : postSnapshot.getChildren()) {
                            if(postSnapshot1.getKey().equals("new_version_update")){
                                boolean update = (boolean)postSnapshot1.getValue();
                                ssPrefrenceManager.putBooleanSharedPreferences(SSSharedPreferencesManager.START_OR_SIT_UPDATE_DONE,update);
                            }

                        }
                    }
                }

                mIsFireBaseInitLoaded = false;


            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println(error.toString());
                mIsFireBaseInitLoaded = false;
            }


        });
    }

    public static SitOrStartApplication getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    private void initSelectors() {
        mColorArray = new int[][]{{R.drawable.border_layout_color_1, R.drawable.border_layout_color_2},
                {R.drawable.border_layout_color_3, R.drawable.border_layout_color_4},
                {R.drawable.border_layout_color_5, R.drawable.border_layout_color_6},
                {R.drawable.border_layout_color_7, R.drawable.border_layout_color_8}};
        mColorSelectedArray = new int[][]{{R.drawable.border_layout_color_1_selected, R.drawable.border_layout_color_2_selected},
                {R.drawable.border_layout_color_3_selected, R.drawable.border_layout_color_4_selected},
                {R.drawable.border_layout_color_5_selected, R.drawable.border_layout_color_6_selected},
                {R.drawable.border_layout_color_7_selected, R.drawable.border_layout_color_8_selected}};

    }

    public int[] getSelectorResourceId(int position, boolean isSelected) {

        int i = (position + mColorArray.length) % mColorArray.length;
        if (isSelected) {
            return mColorSelectedArray[i];
        } else {
            return mColorArray[i];
        }

    }

    public Firebase getMyFirebaseRef() {
        return myFirebaseRef;
    }

    public ArrayList<Player> getmPlayerArrayList() {
        return mPlayerArrayList;
    }

    public boolean ismIsFireBAseInitLoaded() {
        return mIsFireBaseInitLoaded;
    }

    public ArrayList<NewPlayerSet> getmPlayerSetArrayList() {
        return mPlayerSetArrayList;
    }

    public void setmPlayerSetArrayList(ArrayList<NewPlayerSet> mPlayerSetArrayList) {
        this.mPlayerSetArrayList = mPlayerSetArrayList;
    }
}
