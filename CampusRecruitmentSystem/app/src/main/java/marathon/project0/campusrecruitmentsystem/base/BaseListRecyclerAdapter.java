package marathon.project0.campusrecruitmentsystem.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import marathon.project0.campusrecruitmentsystem.R;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public abstract class BaseListRecyclerAdapter extends RecyclerView.Adapter<BaseListRecyclerAdapter.ItemViewHolder> {

    private OnItemClick itemClick;

    public BaseListRecyclerAdapter(){

    }

    public OnItemClick getItemClick() {
        return itemClick;
    }

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView subTitle;
        public ImageButton itemDeleteBtn;

        ItemViewHolder(View itemView ) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.itemTitle);
            subTitle = (TextView) itemView.findViewById(R.id.itemSubTitle);
            itemDeleteBtn = (ImageButton) itemView.findViewById(R.id.itemDeleteBtn);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item, parent, false));
    }

    public interface OnItemClick{
        void onItemClick(int position,View v);
    }
}