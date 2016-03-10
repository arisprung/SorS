package com.sprungsolutions.sitorstart;

import android.app.Activity;
import android.app.IntentService;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class SSRecyclerViewAdapter extends RecyclerSwipeAdapter<SSRecyclerViewAdapter.SimpleViewHolder> {

    protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);
    private Activity mContext;
    private ArrayList<PlayerSetFirebase> mDataset;
    private SSSharedPreferencesManager ssPrefrenceManager;


    public SSRecyclerViewAdapter(Activity context, ArrayList<PlayerSetFirebase> objects) {
        mContext = context;
        mDataset = objects;
        ssPrefrenceManager = SSSharedPreferencesManager.getInstance(mContext);
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
                PlayerSetFirebase item = mDataset.get(i);
                view.setBackgroundResource(R.drawable.border_layout);
                if (view.getTag().equals("selected")) {
                    ssPrefrenceManager.removeID(mContext, item.getSet_id() + 1);
                    view.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(i,false)[0]);
                    view.setTag("not-selected");
                    item.setPlayer1selected(false);
                    layoutSelected(item.getSet_id(), 1,false);
                } else {
                    ViewGroup row = (ViewGroup) view.getParent();
                    View view2 = row.getChildAt(1);
                    if (view2.getTag().equals("selected")) {
                        ssPrefrenceManager.removeID(mContext, item.getSet_id() + 2);
                        view2.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(i,false)[1]);
                        view2.setTag("not-selected");
                        item.setPlayer2selected(false);
                        layoutSelected(item.getSet_id(), 2,false);
                    }
                    ssPrefrenceManager.addID(mContext, item.getSet_id() + 1);
                    item.setPlayer1selected(true);
                    view.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(i,true)[0]);
                    view.setTag("selected");
                    layoutSelected(item.getSet_id(), 1,true);
                }

            }

            @Override
            public void onSecondLayout(LinearLayout view, int i) {
                PlayerSetFirebase item = mDataset.get(i);
                if (view.getTag().equals("selected")) {
                    ssPrefrenceManager.removeID(mContext, item.getSet_id() + 2);
                    view.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(i,false)[1]);
                    view.setTag("not-selected");
                    item.setPlayer2selected(false);
                    layoutSelected(item.getSet_id(), 2,false);
                } else {
                    ViewGroup row = (ViewGroup) view.getParent();
                    View view1 = row.getChildAt(0);

                    if (view1.getTag().equals("selected")) {
                        ssPrefrenceManager.removeID(mContext, item.getSet_id() + 1);
                        view.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(i,false)[0]);
                        view1.setTag("not-selected");
                        item.setPlayer1selected(false);
                        layoutSelected(item.getSet_id(), 1,false);
                    }
                    ssPrefrenceManager.addID(mContext, item.getSet_id() + 2);
                    item.setPlayer2selected(true);
                    view.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(i,true)[1]);
                    view.setTag("selected");
                    layoutSelected(item.getSet_id(), 2,true);


                }


            }
        });
        return vh;


    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        PlayerSetFirebase item = mDataset.get(position);
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
//        viewHolder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
//            @Override
//            public void onOpen(SwipeLayout layout) {
//               // YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
//            }
//        });
//        viewHolder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
//            @Override
//            public void onDoubleClick(SwipeLayout layout, boolean surface) {
//                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
//            }
//        });


        viewHolder.mName1.setText(item.getMlb_name_1());
        viewHolder.mName2.setText(item.getMlb_name_2());

        viewHolder.mTeamName1.setText(item.getMlb_pos_1()+" - "+item.getMlb_team_1());
        viewHolder.mTeamName2.setText(item.getMlb_pos_2()+" - "+item.getMlb_team_2());

        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(item.getMlb_id_1()), viewHolder.imageView1);
        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(item.getMlb_id_2()), viewHolder.imageView2);


        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(item.getMlb_id_1()), viewHolder.backImageView1);
        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(item.getMlb_id_2()), viewHolder.backImageView2);


        if (item.isPlayer2selected()) {
            viewHolder.mLinearLayout2.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(position,true)[1]);
            viewHolder.mLinearLayout2.setTag("selected");


        } else {
            viewHolder.mLinearLayout2.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(position,false)[1]);
            viewHolder.mLinearLayout2.setTag("not-selected");

        }

        if (item.isPlayer1selected()) {
            viewHolder.mLinearLayout1.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(position,true)[0]);
            viewHolder.mLinearLayout1.setTag("selected");
        } else {
            viewHolder.mLinearLayout1.setBackgroundResource(SitOrStartApplication.getInstance().getSelectorResourceId(position,false)[0]);
            viewHolder.mLinearLayout1.setTag("not-selected");
        }

        int total = item.getMlb_set_score_1()+item.getMlb_set_score_2();
//        float percent1 = 0.1f;
//        if(item.getMlb_set_score_1()!=0){
//            percent1 = (total * 100.0f) / item.getMlb_set_score_1();
//        }
//
//        float percent2 = 0.10f;
//        if(item.getMlb_set_score_2()!=0){
//            percent2 = (total * 100.0f) / item.getMlb_set_score_2();
//        }


        float percent1 = (item.getMlb_set_score_1() * 100.0f) / total;
        float percent2 = (item.getMlb_set_score_2() * 100.0f) / total;
        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams)
                viewHolder.mPercent1 .getLayoutParams();
        int i1 = (int) Math.floor(percent1);
        params1.width = i1*2;
        viewHolder.mPercent1 .setLayoutParams(params1);

        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams)
                viewHolder.mPercent2 .getLayoutParams();
        
        int i2 = (int)Math.floor(percent2);
        params2.width = i2*2;
        viewHolder.mPercent2 .setLayoutParams(params2);

        viewHolder.mPercentText1.setText(i1+"%");
        viewHolder.mPercentText2.setText(i2+"%");



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


        String strType = "mlb_set_score_1";

        if (type == 1) {
            strType = "mlb_set_score_1";
        } else if (type == 2) {
            strType = "mlb_set_score_2";
        }


        Firebase upvotesRef = new Firebase("https://sitorstart.firebaseio.com/sports/mlb/mlb_player_set/" + number + "/" + strType);
        upvotesRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if (currentData.getValue() == null) {
                    if(incremnt)
                    currentData.setValue(1);
                } else {
                    if(incremnt){
                        currentData.setValue((Long) currentData.getValue() + 1);
                    }else{
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
        private FontTextView mTeamName1;
        private FontTextView mTeamName2;
        private LinearLayout mLinearLayout1;
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
            mName1 = (FontTextView) itemView.findViewById(R.id.front_name_1);
            mName2 = (FontTextView) itemView.findViewById(R.id.front_name_2);
            mPercent1 = (View) itemView.findViewById(R.id.player_percnt_1);
            mPercent2 = (View) itemView.findViewById(R.id.player_percnt_2);

            mPercentText1 = (FontTextView) itemView.findViewById(R.id.player_percnt_text_1);
            mPercentText2 = (FontTextView) itemView.findViewById(R.id.player_percnt_text_2);



            mLinearLayout1.setOnClickListener(this);
            mLinearLayout2.setOnClickListener(this);


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
            }

        }


        public static interface IMyViewHolderClicks {
            public void onFirstLayout(LinearLayout view, int i);

            public void onSecondLayout(LinearLayout view, int i);
        }

    }
}
