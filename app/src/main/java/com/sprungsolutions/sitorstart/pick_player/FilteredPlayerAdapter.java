package com.sprungsolutions.sitorstart.pick_player;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sprungsolutions.sitorstart.views.FontTextView;
import com.sprungsolutions.sitorstart.R;
import com.sprungsolutions.sitorstart.utility.SitStartUtility;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by arisprung on 3/18/16.
 */
public class FilteredPlayerAdapter extends RecyclerView.Adapter<FilteredPlayerAdapter.ViewHolder> {

    private final LayoutInflater mInflater;
    private final List<Player> mModels;
    private Context mContext;
    OnItemClickListener mItemClickListner;

    public FilteredPlayerAdapter(Context context, List<Player> models) {
        mInflater = LayoutInflater.from(context);
        mModels = new ArrayList<>(models);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.player_pick_item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Player model = mModels.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public void animateTo(List<Player> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Player> newModels) {
        for (int i = mModels.size() - 1; i >= 0; i--) {
            final Player model = mModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Player> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Player model = newModels.get(i);
            if (!mModels.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Player> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Player model = newModels.get(toPosition);
            final int fromPosition = mModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(Player player);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        mItemClickListner = mItemClickListener;
    }

    public Player removeItem(int position) {
        final Player model = mModels.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Player model) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Player model = mModels.remove(fromPosition);
        mModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public FontTextView mPlayerName;
        public RelativeLayout mLinearLayout;
        public FontTextView mPlayerTeam;
        public CircleImageView mPlayerImage;




        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mPlayerName = (FontTextView)itemLayoutView.findViewById(R.id.player_name);
            mPlayerTeam = (FontTextView) itemLayoutView.findViewById(R.id.player_team_position);
            mPlayerImage = (CircleImageView)itemLayoutView.findViewById(R.id.player_image);
            mLinearLayout = (RelativeLayout)itemLayoutView.findViewById(R.id.picker_player_root);




        }

        public void bind(final Player model) {
            mPlayerName.setText(model.getMlb_name());
            mPlayerTeam.setText("("+model.getMlb_pos()+") "+model.getMlb_team_long());
//        holder.mPlayerTeam.setChecked(cc.isSelected());

            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListner != null){
                        mItemClickListner.onItemClick(model);
                    }
                }
            });

            SitStartUtility.setImageInView(mContext, SitStartUtility.getImageUrl(Integer.valueOf(model.getMlb_id())),mPlayerImage );
        }



    }
}
