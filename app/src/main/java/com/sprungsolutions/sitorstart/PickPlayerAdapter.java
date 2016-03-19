package com.sprungsolutions.sitorstart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by arisprung on 10/27/15.
 */
public class PickPlayerAdapter extends RecyclerView.Adapter<PickPlayerAdapter.ViewHolder> implements Filterable{
    private ArrayList<Player> mArrayList;
    private ArrayList<Player> mFilteredArrayList;
    OnItemClickListener  mItemClickListner;


    private Context mContext;

    @Override
    public Filter getFilter() {
        return null;
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public FontTextView mPlayerName;
        public FontTextView mPlayerTeam;
        public CircleImageView mPlayerImage;




        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mPlayerName = (FontTextView)itemLayoutView.findViewById(R.id.player_name);
            mPlayerTeam = (FontTextView) itemLayoutView.findViewById(R.id.player_team_position);
            mPlayerImage = (CircleImageView)itemLayoutView.findViewById(R.id.player_image);

            itemLayoutView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if (mItemClickListner != null){
                mItemClickListner.onItemClick(v,getPosition());
            }
        }
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        mItemClickListner = mItemClickListener;
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public PickPlayerAdapter(ArrayList<Player> myDataset, Context context) {
        mArrayList = myDataset;
        mContext = context;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public PickPlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_pick_item_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element




        Player player = mArrayList.get(position);
        holder.mPlayerName.setText(player.getMlb_name());
        holder.mPlayerTeam.setText("("+player.getMlb_pos()+") "+player.getMlb_team_long());
//        holder.mPlayerTeam.setChecked(cc.isSelected());

        SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(Integer.valueOf(player.getMlb_id())), holder.mPlayerImage);






    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }




}