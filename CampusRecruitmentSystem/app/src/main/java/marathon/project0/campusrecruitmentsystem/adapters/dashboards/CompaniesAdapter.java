package marathon.project0.campusrecruitmentsystem.adapters.dashboards;

import android.view.View;

import java.util.List;

import marathon.project0.campusrecruitmentsystem.base.BaseListRecyclerAdapter;
import marathon.project0.campusrecruitmentsystem.model.Company;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class CompaniesAdapter extends BaseListRecyclerAdapter {
    private List<Company> mDataSet;
    List<Company> companies;
    private boolean isAdmin = false;

    public CompaniesAdapter(List<Company> companies,OnItemClick onItemClick) {
        this.companies = companies;
        setItemClick(onItemClick);
    }

    public CompaniesAdapter(List<Company> companies,OnItemClick onItemClick,boolean isAdmin) {
        this.companies = companies;
        setItemClick(onItemClick);
        this.isAdmin = isAdmin;
    }

    @Override
    public void onBindViewHolder(final BaseListRecyclerAdapter.ItemViewHolder holder, final int position) {
        holder.title.setText(companies.get(position).getName());
        holder.subTitle.setText(companies.get(position).getEmail());
        if(getItemClick() != null){
            holder.viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getItemClick().onItemClick(position,holder.itemView);
                }
            });
        }
        if(!isAdmin){
            holder.itemDeleteBtn.setVisibility(View.GONE);
            if(getDeleteClick() != null){
                holder.itemDeleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDeleteClick().onDeleteClick(position,null);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }
}
