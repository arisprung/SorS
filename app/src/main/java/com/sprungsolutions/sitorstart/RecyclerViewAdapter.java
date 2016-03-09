package com.sprungsolutions.sitorstart;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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


public class RecyclerViewAdapter extends RecyclerSwipeAdapter<RecyclerViewAdapter.SimpleViewHolder> {

    protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);
    private Activity mContext;
    private ArrayList<PlayerSetFirebase> mDataset;
    private SSSharedPreferencesManager ssPrefrenceManager;


    public RecyclerViewAdapter(Activity context, ArrayList<PlayerSetFirebase> objects) {
        mContext = context;
        mDataset = objects;
        ssPrefrenceManager = SSSharedPreferencesManager.getInstance(mContext);
    }

    public RecyclerViewAdapter() {

    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
//        return new SimpleViewHolder(view);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        final RecyclerViewAdapter.SimpleViewHolder vh = new SimpleViewHolder(v, new RecyclerViewAdapter.SimpleViewHolder.IMyViewHolderClicks() {


            @Override
            public void onFirstLayout(LinearLayout view, int i) {
                PlayerSetFirebase item = mDataset.get(i);
                view.setBackgroundResource(R.drawable.border_layout);
                if (view.getTag().equals("selected")) {
                    ssPrefrenceManager.removeID(mContext, item.getSet_id() + 1);
                    view.setBackgroundResource(0);
                    view.setTag("not-selected");
                    item.setPlayer1selected(false);
                    layoutSelected(item.getSet_id(), 1,false);
                } else {
                    ViewGroup row = (ViewGroup) view.getParent();
                    View view2 = row.getChildAt(2);
                    if (view2.getTag().equals("selected")) {
                        ssPrefrenceManager.removeID(mContext, item.getSet_id() + 2);
                        view2.setBackgroundResource(0);
                        view2.setTag("not-selected");
                        item.setPlayer2selected(false);
                        layoutSelected(item.getSet_id(), 2,false);
                    }
                    ssPrefrenceManager.addID(mContext, item.getSet_id() + 1);
                    item.setPlayer1selected(true);
                    view.setBackgroundResource(R.drawable.border_layout);
                    view.setTag("selected");
                    layoutSelected(item.getSet_id(), 1,true);






                }

            }

            @Override
            public void onSecondLayout(LinearLayout view, int i) {
                PlayerSetFirebase item = mDataset.get(i);
                if (view.getTag().equals("selected")) {
                    ssPrefrenceManager.removeID(mContext, item.getSet_id() + 2);
                    view.setBackgroundResource(0);
                    view.setTag("not-selected");
                    item.setPlayer2selected(false);
                    layoutSelected(item.getSet_id(), 2,false);
                } else {
                    ViewGroup row = (ViewGroup) view.getParent();
                    View view1 = row.getChildAt(0);

                    if (view1.getTag().equals("selected")) {
                        ssPrefrenceManager.removeID(mContext, item.getSet_id() + 1);
                        view1.setBackgroundResource(0);
                        view1.setTag("not-selected");
                        item.setPlayer1selected(false);
                        layoutSelected(item.getSet_id(), 1,false);
                    }
                    ssPrefrenceManager.addID(mContext, item.getSet_id() + 2);
                    item.setPlayer2selected(true);
                    view.setBackgroundResource(R.drawable.border_layout);
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

        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(item.getMlb_id_1()), viewHolder.imageView1);
        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(item.getMlb_id_2()), viewHolder.imageView2);


        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(item.getMlb_id_1()), viewHolder.backImageView1);
        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(item.getMlb_id_2()), viewHolder.backImageView2);


        if (item.isPlayer2selected()) {
            viewHolder.mLinearLayout2.setBackgroundResource(R.drawable.border_layout);
            viewHolder.mLinearLayout2.setTag("selected");

        } else {
            viewHolder.mLinearLayout2.setBackgroundResource(0);
            viewHolder.mLinearLayout2.setTag("not-selected");
        }

        if (item.isPlayer1selected()) {
            viewHolder.mLinearLayout1.setBackgroundResource(R.drawable.border_layout);
            viewHolder.mLinearLayout1.setTag("selected");
        } else {
            viewHolder.mLinearLayout1.setBackgroundResource(0);
            viewHolder.mLinearLayout1.setTag("not-selected");
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
                Toast.makeText(mContext, "Your selection was processed", Toast.LENGTH_SHORT).show();
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
        private LinearLayout mLinearLayout1;
        private LinearLayout mLinearLayout2;

        public SimpleViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            imageView1 = (ImageView) itemView.findViewById(R.id.front_image_1);
            imageView2 = (ImageView) itemView.findViewById(R.id.front_image_2);
            backImageView1 = (ImageView) itemView.findViewById(R.id.back_image_1);
            backImageView2 = (ImageView) itemView.findViewById(R.id.back_image_2);
            mLinearLayout1 = (LinearLayout) itemView.findViewById(R.id.front_ll_1);
            mLinearLayout2 = (LinearLayout) itemView.findViewById(R.id.front_ll_2);
            mName1 = (FontTextView) itemView.findViewById(R.id.front_name_1);
            mName2 = (FontTextView) itemView.findViewById(R.id.front_name_2);
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
