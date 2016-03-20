package com.sprungsolutions.sitorstart;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.daimajia.swipe.util.Attributes;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Handler handler = new Handler();
    private RecyclerView.Adapter mAdapter;

    private ArrayList<NewPlayerSet> mPlayerList;

    private SSSharedPreferencesManager ssPrefrenceManager;
    private SpotsDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ssPrefrenceManager = SSSharedPreferencesManager.getInstance(getApplicationContext());
        mDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
        mDialog.show();

      //  SitOrStartApplication.getInstance().getMyFirebaseRef().authAnonymously(new AuthResultHandler("anonymous"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                Intent intent = new Intent(getApplicationContext(),AddPlayerSetActivity.class);
//                startActivityForResult(intent,11);

                Bundle args = new Bundle();
                args.putString("title", "Dialog with Action Bar");
                AddPlayerSetFragment actionbarDialog = new AddPlayerSetFragment();
                actionbarDialog.setArguments(args);
                actionbarDialog.show(getSupportFragmentManager(),
                        "action_bar_frag");
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                runnable.run();
            }
        }, 500);

        // Layout Managers:
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPlayerList = new ArrayList<>();
        // Item Decorator:
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        mAdapter = new SSRecyclerViewAdapter(MainActivity.this, mPlayerList);
        ((SSRecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(onScrollListener);
        loadPLayersSetFromFireBase();

    }

    /**
     * Substitute for our onScrollListener for RecyclerView
     */
    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e("ListView", "onScrollStateChanged");
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Could hide open views here if you wanted. //
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * Utility class for authentication results
     */
    private class AuthResultHandler implements Firebase.AuthResultHandler {

        private final String provider;

        public AuthResultHandler(String provider) {
            this.provider = provider;
        }

        @Override
        public void onAuthenticated(AuthData authData) {



            //     setAuthenticatedUser(authData);
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
//            mAuthProgressDialog.hide();
//            showErrorDialog(firebaseError.toString());
            Log.e("", provider + " auth error");
        }
    }

    private void loadPLayersSetFromFireBase() {
        //  mAuthProgressDialog.hide();

        SitOrStartApplication.getInstance().getMyFirebaseRef().child("sports/mlb/mlb_player_set").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try{
                    mPlayerList.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        NewPlayerSet set = postSnapshot.getValue(NewPlayerSet.class);
                        set.setId(postSnapshot.getKey());
                        NewPlayerSet post1 = checkIfPlayerWasSelected(set);
                        mPlayerList.add(post1);

                    }
                    SitOrStartApplication.getInstance().setmPlayerSetArrayList(mPlayerList);
                    mAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println(error.toString());
            }


        });
    }

    private NewPlayerSet checkIfPlayerWasSelected(NewPlayerSet post) {

        ArrayList<String> list = ssPrefrenceManager.loadID(getApplicationContext());
        if (list != null) {
            for (String s : list) {
                String stemp = s.substring(0, s.length() - 1);

                if (stemp.equals(post.getId())) {
                    String send = s.substring(s.length() - 1, s.length());
                    if (send.equals("1")) {
                        post.getmPlayer1().setSelected(true);

                    } else {
                        post.getmPlayer2().setSelected(true);
                    }
                    return post;

                }

            }
        }

        return post;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ssPrefrenceManager.removeKey(SSSharedPreferencesManager.START_OR_SIT_SET_ID);
    }

    private Runnable runnable = new Runnable() {
        public void run() {
            Log.d("HomeActivty", "Checking to cancel Progress");
            if (SitOrStartApplication.getInstance().ismIsFireBAseInitLoaded()) {
                handler.postDelayed(this, 500);
            } else {
                mDialog.dismiss();
                handler.removeCallbacks(runnable);
            }
        }
    };
}
