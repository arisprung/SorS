package com.sprungsolutions.sitorstart;

import android.app.Application;

/**
 * Created by arisprung on 3/9/16.
 */
public class SitOrStartApplication extends Application {

   public static int[][] mColorArray ;
    public static int[][]  mColorSelectedArray;
    private static SitOrStartApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        initSelectors();
        instance = this;

    }

    public static SitOrStartApplication getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }
    private void initSelectors() {
        mColorArray = new int [][] { { R.drawable.border_layout_color_1,R.drawable.border_layout_color_2},
                { R.drawable.border_layout_color_3, R.drawable.border_layout_color_4},
                { R.drawable.border_layout_color_5, R.drawable.border_layout_color_6},
                { R.drawable.border_layout_color_7, R.drawable.border_layout_color_8}};
        mColorSelectedArray = new int [][] { { R.drawable.border_layout_color_1_selected,R.drawable.border_layout_color_2_selected},
                { R.drawable.border_layout_color_3_selected, R.drawable.border_layout_color_4_selected},
                { R.drawable.border_layout_color_5_selected, R.drawable.border_layout_color_6_selected},
                { R.drawable.border_layout_color_7_selected, R.drawable.border_layout_color_8_selected}};

    }
    public int[] getSelectorResourceId(int position,boolean isSelected) {

        int i = (position + mColorArray.length) % mColorArray.length;
        if(isSelected){
            return mColorSelectedArray[i];
        }else{
            return mColorArray[i];
        }

    }
}
