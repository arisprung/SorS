package com.sprungsolutions.sitorstart.pick_player;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.firebase.client.Firebase;
import com.google.gson.Gson;
import com.sprungsolutions.sitorstart.R;
import com.sprungsolutions.sitorstart.application.SitOrStartApplication;
import com.sprungsolutions.sitorstart.utility.SitStartUtility;
import com.sprungsolutions.sitorstart.views.FontTextView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

/**
 * Created by arisprung on 3/9/16.
 */
public class AddPlayerSetFragment extends DialogFragment {


    private BootstrapButton mAddButton;
    private BootstrapButton mCancelButton;
    private Dialog dialog;
    private Player mPlayer1;
    private Player mPlayer2;
    private View mView;
    private FontTextView mName1;
    private FontTextView mName2;
    private CircleImageView mCircleImage1;
    private CircleImageView mCircleImage2;
    private Handler mHandler;
    private SpotsDialog mDialog;
    private String mPackageName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.add_set_layout, container, false);
        mAddButton = (BootstrapButton) mView.findViewById(R.id.add_set);
        mCancelButton = (BootstrapButton) mView.findViewById(R.id.cancel_set);
        mCircleImage1 = (CircleImageView) mView.findViewById(R.id.circle_image1);
        mCircleImage2 = (CircleImageView) mView.findViewById(R.id.circle_image2);
        mName1 = (FontTextView) mView.findViewById(R.id.name1);
        mName2 = (FontTextView) mView.findViewById(R.id.name2);
        mName1.setVisibility(View.GONE);
        mName2.setVisibility(View.GONE);
        mPackageName = getActivity().getApplicationContext().getPackageName();

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCircleImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pickerIntent = new Intent(getContext(), PickPlayerActivity.class);
                startActivityForResult(pickerIntent, 01);

            }
        });
        mCircleImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pickerIntent = new Intent(getContext(), PickPlayerActivity.class);
                startActivityForResult(pickerIntent, 02);

            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPlayer1 == null || mPlayer2 == null) {
                    playSnackBar("You need to select 2 players");
                    return;
                }
                sendPlayerToServer();
            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mDialog.dismiss();
                if(msg.getData().getBoolean("exist")){
                    if(getActivity() != null){
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("This combination already exist please try a different one")
                                .show();
                    }else{
                        Toast.makeText(getContext(),"This combination already exist please try a different one", Toast.LENGTH_SHORT).show();
                    }



                }else{
                    sendSetToFireBase();

                }
            }
        };

        mDialog = new SpotsDialog(getActivity(), R.style.Custom);
        mDialog.setMessage("Sending...Hold tight!");

    }

    private void sendSetToFireBase() {

        long i = System.currentTimeMillis();


        Firebase playerRef = SitOrStartApplication.getInstance().getMyFirebaseRef().child("sports/mlb/mlb_player_set");
        //PlayerSetFirebase set = new PlayerSetFirebase();
        mPlayer1.setScore(0);
        mPlayer2.setScore(0);
        NewPlayerSet set = new NewPlayerSet(mPlayer1, mPlayer2);
        set.setId(String.valueOf(i));
        playerRef.push().setValue(set);

        showShareDialog();
    }

    private void showShareDialog() {

        if(getActivity() != null){

            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Player Set Added")
                    .setContentText("Your player set was added succesfully.\nDo you want to ask your freinds who to start?")
                    .setCancelText("No thank you")
                    .setConfirmText("Yes!")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Intent in = new Intent("com.sprungsolutions.sharerecoever");
                            SitOrStartApplication.getInstance().sendBroadcast(in);
                            sDialog.cancel();
                        }
                    })
                    .show();
        }

    }

    private void sendPlayerToServer() {
        mDialog.show();
        dialog.dismiss();

        Runnable runnable = new Runnable() {
            public void run() {
                boolean isExist = checkIfSetExist();
                Message msg = mHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putBoolean("exist", isExist);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();




    }

    private boolean checkIfSetExist() {

        if (SitOrStartApplication.getInstance().getmPlayerSetArrayList() != null) {
            for (NewPlayerSet set : SitOrStartApplication.getInstance().getmPlayerSetArrayList()) {

                if (set.getmPlayer1().getMlb_name().equals(mPlayer1.getMlb_name()) || set.getmPlayer1().getMlb_name().equals(mPlayer2.getMlb_name())) {
                    Log.d("","PLayer 1 exist");
                    if (set.getmPlayer2().getMlb_name().equals(mPlayer1.getMlb_name()) || set.getmPlayer2().getMlb_name().equals(mPlayer2.getMlb_name())) {
                        Log.d("","PLayer 2 exist");
                        return true;
                    } else {
                        Log.d("","PLayer 2 does not  exist");

                    }

                }

                if (set.getmPlayer2().getMlb_name().equals(mPlayer1.getMlb_name()) || set.getmPlayer1().getMlb_name().equals(mPlayer2.getMlb_name())) {
                    Log.d("","**PLayer 2 exist");
                    if (set.getmPlayer1().getMlb_name().equals(mPlayer1.getMlb_name()) || set.getmPlayer1().getMlb_name().equals(mPlayer2.getMlb_name())) {
                        Log.d("","**PLayer 2 exist");
                        return true;
                    }else {
                        Log.d("","**PLayer 2 does not  exist");
                    }

                }

            }
            return false;
        }
        return false;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);
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
                mPlayer1 = gson.fromJson(result, Player.class);
                if (mPlayer2 != null) {
                    if (mPlayer2.getMlb_name().equals(mPlayer1.getMlb_name())) {
                        playSnackBar("Player has been selected already");
                        return;
                    }
                }
                mCircleImage1.setBackgroundResource(0);
                mName1.setVisibility(View.VISIBLE);
                mName1.setText(mPlayer1.getMlb_name());
                SitStartUtility.setImageInView(getActivity().getApplicationContext(), SitStartUtility.getImageUrl(Integer.valueOf(mPlayer1.getMlb_id())), mCircleImage1);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                playSnackBar("Player has been canceled");
            }
        } else if (requestCode == 02) {
            if (resultCode == Activity.RESULT_OK) {
                Gson gson = new Gson();
                String result = data.getStringExtra("player_result");
                mPlayer2 = gson.fromJson(result, Player.class);
                if (mPlayer1 != null) {
                    if (mPlayer1.getMlb_name().equals(mPlayer2.getMlb_name())) {
                        playSnackBar("Player has been selected already");
                        return;
                    }
                }
                mCircleImage2.setBackgroundResource(0);
                mName2.setVisibility(View.VISIBLE);
                mName2.setText(mPlayer2.getMlb_name());
                SitStartUtility.setImageInView(getActivity().getApplicationContext(), SitStartUtility.getImageUrl(Integer.valueOf(mPlayer2.getMlb_id())), mCircleImage2);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                playSnackBar("Player has been canceled");
            }
        }
    }

    private void playSnackBar(String text) {
        Snackbar.make(mView, text, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
}
