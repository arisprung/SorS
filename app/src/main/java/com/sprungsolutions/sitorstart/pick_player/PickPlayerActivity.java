package com.sprungsolutions.sitorstart.pick_player;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sprungsolutions.sitorstart.views.DividerItemDecoration;
import com.sprungsolutions.sitorstart.R;
import com.sprungsolutions.sitorstart.utility.SSSharedPreferencesManager;
import com.sprungsolutions.sitorstart.application.SitOrStartApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arisprung on 3/14/16.
 */
public class PickPlayerActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView mRecyclerView;

    private FilteredPlayerAdapter mAdapter;

    private ArrayList<Player> mPLayerList;
    private SSSharedPreferencesManager ssPrefrenceManager;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SearchView serachView = (SearchView) getLayoutInflater().inflate(R.layout.search_view_layout, null);
        serachView.setIconified(false);
        toolbar.addView(serachView);
        serachView.setOnQueryTextListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        mSearchView.setVisibility(View.VISIBLE);
        changeSearchViewTextColor(mSearchView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        // Layout Managers:
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPLayerList = SitOrStartApplication.getInstance().getmPlayerArrayList();
        // Item Decorator:
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider)));
        mAdapter = new FilteredPlayerAdapter(getApplicationContext(), mPLayerList);
        // ((PickPlayerAdapter) mAdapter).setMode(Attributes.Mode.Single);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new FilteredPlayerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Player player) {
             //   Toast.makeText(getApplicationContext(), player.toString(), Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                Gson gson = new Gson();
                returnIntent.putExtra("player_result", gson.toJson(player));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });


    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Player> filteredModelList = filter(mPLayerList, query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    private List<Player> filter(List<Player> models, String query) {
        query = query.toLowerCase();

        final List<Player> filteredModelList = new ArrayList<>();
        for (Player model : models) {
            final String text = model.getMlb_name().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }
}
