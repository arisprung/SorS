package com.sprungsolutions.sitorstart.player_list;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.sprungsolutions.sitorstart.pick_player.NewPlayerSet;
import com.sprungsolutions.sitorstart.R;
import com.sprungsolutions.sitorstart.application.SitOrStartApplication;
import com.sprungsolutions.sitorstart.utility.SSSharedPreferencesManager;
import com.sprungsolutions.sitorstart.utility.SitStartUtility;
import com.sprungsolutions.sitorstart.views.FontTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class SSRecyclerViewAdapter extends RecyclerSwipeAdapter<SSRecyclerViewAdapter.SimpleViewHolder> {

    protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);
    private Activity mContext;
    private ArrayList<NewPlayerSet> mDataset;
    private SSSharedPreferencesManager ssPrefrenceManager;
    private  float INCREASE_BAR = 500.0f;


    public SSRecyclerViewAdapter(Activity context, ArrayList<NewPlayerSet> objects) {
        mContext = context;
        mDataset = objects;
        ssPrefrenceManager = SSSharedPreferencesManager.getInstance(mContext);

        switch (mContext.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                INCREASE_BAR = 200.0f;

                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                INCREASE_BAR = 200.0f;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                INCREASE_BAR = 200;
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                INCREASE_BAR = 200.0f;
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                INCREASE_BAR =400.0f;
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                INCREASE_BAR = 500.0f;
                break;
            case DisplayMetrics.DENSITY_TV:
                INCREASE_BAR = 200.0f;
                break;
        }
    }

    public SSRecyclerViewAdapter() {

    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
//        return new SimpleViewHolder(view);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        final SSRecyclerViewAdapter.SimpleViewHolder vh = new SimpleViewHolder(v, new SSRecyclerViewAdapter.SimpleViewHolder.IMyViewHolderClicks() {


            @Override
            public void onFirstLayout(LinearLayout view, int i) {
                NewPlayerSet item = mDataset.get(i);
              //  view.setBackgroundResource(R.drawable.border_layout_right_w);
                if (view.getTag().equals("selected")) {
                    ssPrefrenceManager.removeID(mContext, item.getId() + 1);
                    view.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(i, false)[0]);
                    view.setTag("not-selected");
                    item.getmPlayer1().setSelected(false);
                    layoutSelected(item.getId(), 1, false);
                } else {
                    ViewGroup row = (ViewGroup) view.getParent();
                    View view2 = row.getChildAt(1);
                    if (view2.getTag().equals("selected")) {
                        ssPrefrenceManager.removeID(mContext, item.getId() + 2);
                        view2.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(i, false)[1]);
                        view2.setTag("not-selected");
                        item.getmPlayer2().setSelected(false);
                        layoutSelected(item.getId(), 2, false);
                    }
                    ssPrefrenceManager.addID(mContext, item.getId() + 1);
                    item.getmPlayer1().setSelected(true);
                    view.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(i, true)[0]);
                    view.setTag("selected");
                    layoutSelected(item.getId(), 1, true);
                }

            }

            @Override
            public void onBackFirstLayout(LinearLayout view, int i) {
                NewPlayerSet item = mDataset.get(i);
                Intent webIntent = new Intent(mContext,PlayerWebView.class);
                webIntent.putExtra("player_url","http://m.mlb.com/player/"+item.getmPlayer1().getMlb_id());
                webIntent.putExtra("player_name",item.getmPlayer1().getMlb_name());

                mContext.startActivity(webIntent);
            }

            @Override
            public void onSecondLayout(LinearLayout view, int i) {
                NewPlayerSet item = mDataset.get(i);
                if (view.getTag().equals("selected")) {
                    ssPrefrenceManager.removeID(mContext, item.getId() + 2);
                    view.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(i, false)[1]);
                    view.setTag("not-selected");
                    item.getmPlayer2().setSelected(false);
                    layoutSelected(item.getId(), 2, false);
                } else {
                    ViewGroup row = (ViewGroup) view.getParent();
                    View view1 = row.getChildAt(0);

                    if (view1.getTag().equals("selected")) {
                        ssPrefrenceManager.removeID(mContext, item.getId() + 1);
                        view.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(i, false)[0]);
                        view1.setTag("not-selected");
                        item.getmPlayer1().setSelected(false);
                        layoutSelected(item.getId(), 1, false);
                    }
                    ssPrefrenceManager.addID(mContext, item.getId() + 2);
                    item.getmPlayer2().setSelected(true);
                    view.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(i, true)[1]);
                    view.setTag("selected");
                    layoutSelected(item.getId(), 2, true);


                }


            }

            @Override
            public void onBackSecondLayout(LinearLayout view, int i) {
                NewPlayerSet item = mDataset.get(i);
                Intent webIntent = new Intent(mContext,PlayerWebView.class);
                webIntent.putExtra("player_url","http://m.mlb.com/player/"+item.getmPlayer2().getMlb_id());
                webIntent.putExtra("player_name",item.getmPlayer2().getMlb_name());
                mContext.startActivity(webIntent);

            }
        });
        return vh;


    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        NewPlayerSet item = mDataset.get(position);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        viewHolder.mName1.setText(item.getmPlayer1().getMlb_name());
        viewHolder.mName2.setText(item.getmPlayer2().getMlb_name());

        viewHolder.mBackName1.setText(item.getmPlayer1().getMlb_name());
        viewHolder.mBackName2.setText(item.getmPlayer2().getMlb_name());

        viewHolder.mTeamName1.setText(item.getmPlayer1().getMlb_pos() + " - " + item.getmPlayer1().getMlb_team());
        viewHolder.mTeamName2.setText(item.getmPlayer2().getMlb_pos() + " - " + item.getmPlayer2().getMlb_team());

        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(Integer.valueOf(item.getmPlayer1().getMlb_id())), viewHolder.imageView1);
        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(Integer.valueOf(item.getmPlayer2().getMlb_id())), viewHolder.imageView2);


        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(Integer.valueOf(item.getmPlayer1().getMlb_id())), viewHolder.backImageView1);
        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(Integer.valueOf(item.getmPlayer2().getMlb_id())), viewHolder.backImageView2);


        if (item.getmPlayer2().isSelected()) {
            viewHolder.mLinearLayout2.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(position, true)[1]);
            viewHolder.mLinearLayout2.setTag("selected");


        } else {
            viewHolder.mLinearLayout2.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(position, false)[1]);
            viewHolder.mLinearLayout2.setTag("not-selected");

        }

        if (item.getmPlayer1().isSelected()) {
            viewHolder.mLinearLayout1.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(position, true)[0]);
            viewHolder.mLinearLayout1.setTag("selected");
        } else {
            viewHolder.mLinearLayout1.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(position, false)[0]);
            viewHolder.mLinearLayout1.setTag("not-selected");
        }

        int total = item.getmPlayer1().getScore() + item.getmPlayer2().getScore();


        float percent1 = (item.getmPlayer1().getScore() * 100.0f) / (total);
        float percent2 = (item.getmPlayer2().getScore() * 100.0f) / (total);


        int i1 = (int) Math.floor(percent1);
        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) viewHolder.mPercent1.getLayoutParams();
        double di1 = i1 / 100.0f;


        params1.width = (int) (di1 * INCREASE_BAR);
        viewHolder.mPercent1.setLayoutParams(params1);


        int i2 = (int) Math.floor(percent2);
        double di2 = i2 / 100.0f;
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams)
                viewHolder.mPercent2.getLayoutParams();
        params2.width = (int) (di2 * INCREASE_BAR);
        viewHolder.mPercent2.setLayoutParams(params2);

        viewHolder.mPercentText1.setText(i1 + "%");
        viewHolder.mPercentText2.setText(i2 + "%");


        if (i1 > i2) {
            viewHolder.mPercent1.setBackgroundResource(R.drawable.rounded_corner_left_w);
            viewHolder.mPercent2.setBackgroundResource(R.drawable.rounded_corner_right_l);
        } else if (i2 > i1) {
            viewHolder.mPercent2.setBackgroundResource(R.drawable.rounded_corner_right_w);
            viewHolder.mPercent1.setBackgroundResource(R.drawable.rounded_corner_left_l);
        }


        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    private void layoutSelected(String number, int type, final boolean incremnt) {


        String strType = "mPlayer1";

        if (type == 1) {
            strType = "mPlayer1";
        } else if (type == 2) {
            strType = "mPlayer2";
        }


        Firebase upvotesRef = new Firebase("https://sitorstart.firebaseio.com/sports/mlb/mlb_player_set/" + number + "/" + strType + "/score");
        upvotesRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if (currentData.getValue() == null) {
                    if (incremnt)
                        currentData.setValue(1);
                } else {
                    if (incremnt) {
                        currentData.setValue((Long) currentData.getValue() + 1);
                    } else {
                        currentData.setValue((Long) currentData.getValue() - 1);
                    }

                }
                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                //This method will be called once with the results of the transaction.
                // Toast.makeText(mContext, "Your selection was processed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public IMyViewHolderClicks mListener;
        private SwipeLayout swipeLayout;
        private ImageView imageView1;
        private ImageView imageView2;
        private ImageView backImageView1;
        private ImageView backImageView2;
        private FontTextView mName1;
        private FontTextView mName2;
        private FontTextView mBackName1;
        private FontTextView mBackName2;
        private FontTextView mTeamName1;
        private FontTextView mTeamName2;
        private LinearLayout mLinearLayout1;
        private LinearLayout mBackLinearLayout1;
        private LinearLayout mBackLinearLayout2;
        private View mPercent1;
        private View mPercent2;
        private LinearLayout mLinearLayout2;
        private FontTextView mPercentText1;
        private FontTextView mPercentText2;


        public SimpleViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            mTeamName1 = (FontTextView) itemView.findViewById(R.id.front_name_team_1);
            mTeamName2 = (FontTextView) itemView.findViewById(R.id.front_name_team_2);
            imageView1 = (CircleImageView) itemView.findViewById(R.id.front_image_1);
            imageView2 = (CircleImageView) itemView.findViewById(R.id.front_image_2);
            backImageView1 = (CircleImageView) itemView.findViewById(R.id.back_image_1);
            backImageView2 = (CircleImageView) itemView.findViewById(R.id.back_image_2);
            mLinearLayout1 = (LinearLayout) itemView.findViewById(R.id.front_ll_1);
            mLinearLayout2 = (LinearLayout) itemView.findViewById(R.id.front_ll_2);
            mBackLinearLayout1 = (LinearLayout) itemView.findViewById(R.id.back_ll_1);
            mBackLinearLayout2 = (LinearLayout) itemView.findViewById(R.id.back_ll_2);
            mName1 = (FontTextView) itemView.findViewById(R.id.front_name_1);
            mName2 = (FontTextView) itemView.findViewById(R.id.front_name_2);

            mBackName1 = (FontTextView) itemView.findViewById(R.id.back_name_1);
            mBackName2 = (FontTextView) itemView.findViewById(R.id.back_name_2);

            mPercent1 = (View) itemView.findViewById(R.id.player_percnt_1);
            mPercent2 = (View) itemView.findViewById(R.id.player_percnt_2);



            mPercentText1 = (FontTextView) itemView.findViewById(R.id.player_percnt_text_1);
            mPercentText2 = (FontTextView) itemView.findViewById(R.id.player_percnt_text_2);


            mLinearLayout1.setOnClickListener(this);
            mLinearLayout2.setOnClickListener(this);
            mBackLinearLayout1.setOnClickListener(this);
            mBackLinearLayout2.setOnClickListener(this);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    Log.d(getClass().getSimpleName(), "onItemSelected: " + textViewData.getText().toString());
////                    Toast.makeText(view.getContext(), "onItemSelected: " + textViewData.getText().toString(), Toast.LENGTH_SHORT).show();
//                }
//            });
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.front_ll_1) {
                mListener.onFirstLayout((LinearLayout) v, getLayoutPosition());
            } else if (v.getId() == R.id.front_ll_2) {
                mListener.onSecondLayout((LinearLayout) v, getLayoutPosition());
            } else if (v.getId() == R.id.back_ll_1) {
                mListener.onBackFirstLayout((LinearLayout) v, getLayoutPosition());
            } else if (v.getId() == R.id.back_ll_2) {
                mListener.onBackSecondLayout((LinearLayout) v, getLayoutPosition());
            }

        }


        public static interface IMyViewHolderClicks {
            public void onFirstLayout(LinearLayout view, int i);
            public void onBackFirstLayout(LinearLayout view, int i);

            public void onSecondLayout(LinearLayout view, int i);
            public void onBackSecondLayout(LinearLayout view, int i);
        }

    }
}
