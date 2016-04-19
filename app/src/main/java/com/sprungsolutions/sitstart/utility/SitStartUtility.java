package com.sprungsolutions.sitstart.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.sprungsolutions.sitstart.R;
import com.squareup.picasso.Picasso;

/**
 * Created by arisprung on 3/8/16.
 */
public class SitStartUtility {

    public static void setImageInView(Context context, String url, ImageView view){

        SSSharedPreferencesManager ssPrefrenceManager = SSSharedPreferencesManager.getInstance(context);

        if(ssPrefrenceManager.getBooleanSharedPreferences(SSSharedPreferencesManager.START_OR_SIT_UPDATE_DONE,false)){
            Picasso
                    .with(context)
                    .load(R.drawable.playerdummy)
                    .error(R.drawable.playerdummy)
                    .placeholder(R.drawable.playerdummy)
                    .fit()
                    .centerInside()
                    .into(view);
        }else{
            Picasso
                    .with(context)
                    .load(url)
                    .error(R.drawable.playerdummy)
                    .placeholder(R.drawable.playerdummy)
                    .fit()
                    .centerInside()
                    .into(view);
        }


    }

    public static String getImageUrl(int mlb_id_1) {
        return "http://mlb.mlb.com/mlb/images/players/head_shot/"+mlb_id_1+".jpg";
    }

    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
