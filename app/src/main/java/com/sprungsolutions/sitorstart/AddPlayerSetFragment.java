package com.sprungsolutions.sitorstart;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;

/**
 * Created by arisprung on 3/9/16.
 */
public class AddPlayerSetFragment extends DialogFragment {


    private ImageView mImage1;
    private ImageView mImage2;
    private BootstrapButton mAddButton;
    private BootstrapButton mCancelButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_set_layout, container, false);
        mImage1 = (ImageView) v.findViewById(R.id.image_1);
        mImage2 = (ImageView) v.findViewById(R.id.image_2);
        mAddButton = (BootstrapButton) v.findViewById(R.id.add_set);
        mCancelButton = (BootstrapButton) v.findViewById(R.id.cancel_set);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pickerIntent = new Intent(getContext(), PickPlayerActivity.class);
                startActivityForResult(pickerIntent, 01);

            }
        });
        mImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pickerIntent = new Intent(getContext(), PickPlayerActivity.class);
                startActivityForResult(pickerIntent, 02);

            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 01) {
            if (resultCode == Activity.RESULT_OK) {
                Gson gson = new Gson();
                String result = data.getStringExtra("player_result");
                Player player = gson.fromJson(result, Player.class);
                Log.d("", "");
                if (!mImage1.getTag().equals("set_image1")) {
                    SitStartUtility.setImageInView(getActivity().getApplicationContext(), SitStartUtility.getImageUrl(Integer.valueOf(player.getMlb_id())), mImage1);
                    mImage1.setTag("set_image1");
                }


            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        } else if (requestCode == 02) {
            if (resultCode == Activity.RESULT_OK) {
                Gson gson = new Gson();
                String result = data.getStringExtra("player_result");
                Player player = gson.fromJson(result, Player.class);

                if (!mImage2.getTag().equals("set_image1")) {
                    SitStartUtility.setImageInView(getActivity().getApplicationContext(), SitStartUtility.getImageUrl(Integer.valueOf(player.getMlb_id())), mImage2);
                    mImage2.setTag("set_image2");
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}
