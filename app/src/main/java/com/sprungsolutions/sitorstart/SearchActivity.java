package com.sprungsolutions.sitorstart;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by arisprung on 3/17/16.
 */
public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_pick_layout);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PlayerPickerFragment.newInstance())
                    .commit();
        }
    }
}
