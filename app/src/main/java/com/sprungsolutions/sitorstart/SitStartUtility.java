package com.sprungsolutions.sitorstart;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by arisprung on 3/8/16.
 */
public class SitStartUtility {

    public static void setImageInView(Context context, String url, ImageView view){


        Picasso
                .with(context)
                .load(url)
                .fit()
                .centerInside()
                .into(view);
    }

    public static String getImageUrl(int mlb_id_1) {
        return "http://mlb.mlb.com/mlb/images/players/head_shot/"+mlb_id_1+".jpg";
    }
}
