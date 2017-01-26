package marathon.project0.campusrecruitmentsystem.adapters.dashboards;

import android.view.View;

import java.util.ArrayList;

import marathon.project0.campusrecruitmentsystem.base.BaseListRecyclerAdapter;
import marathon.project0.campusrecruitmentsystem.model.Company;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class CompaniesAdapter extends BaseListRecyclerAdapter {

    ArrayList<Company> companies = new ArrayList<>();

    public CompaniesAdapter(ArrayList<Company> companies,OnItemClick onItemClick) {
        this.companies = companies;
        setItemClick(onItemClick);
    }

    @Override
    public void onBindViewHolder(final BaseListRecyclerAdapter.ItemViewHolder holder, final int position) {
        holder.title.setText(companies.get(position).getName());
        holder.subTitle.setText(companies.get(position).getEmail());
        if(getItemClick() != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getItemClick().onItemClick(position,holder.itemView);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }
}
