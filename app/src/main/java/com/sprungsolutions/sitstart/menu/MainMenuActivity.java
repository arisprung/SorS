package com.sprungsolutions.sitstart.menu;

import android.app.Activity;
import android.os.Bundle;

import com.sprungsolutions.sitstart.R;
import com.sprungsolutions.sitstart.views.LabelButtonView;

/**
 * Created by arisprung on 3/19/16.
 */
public class MainMenuActivity extends Activity {

    private LabelButtonView mButton1;
    private  LabelButtonView mButton2;
    private  LabelButtonView mButton3;
    private  LabelButtonView mButton4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }
}
