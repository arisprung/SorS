package com.sprungsolutions.sitorstart;

import android.app.Application;
import android.util.Log;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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
    private  boolean mIsFireBAseInitLoaded;


    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        initSelectors();
        instance = this;
        TypefaceProvider.registerDefaultIconSets();
        myFirebaseRef = new Firebase(getString(R.string.firebase_url));
       init();

    }

    private void init() {


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

                    }

                }//

                Log.d("","");


            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println(error.toString());
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
        return mIsFireBAseInitLoaded;
    }
}
