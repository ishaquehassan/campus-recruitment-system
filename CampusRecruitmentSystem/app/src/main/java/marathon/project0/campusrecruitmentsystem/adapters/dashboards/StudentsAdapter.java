package marathon.project0.campusrecruitmentsystem.adapters.dashboards;

import android.view.View;

import java.util.List;

import marathon.project0.campusrecruitmentsystem.base.BaseListRecyclerAdapter;
import marathon.project0.campusrecruitmentsystem.model.Student;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class StudentsAdapter extends BaseListRecyclerAdapter {
    private List<Student> mDataSet;
    List<Student> students;
    private boolean isAdmin = false;

    public StudentsAdapter(List<Student> students, OnItemClick onItemClick) {
        this.students = students;
        setItemClick(onItemClick);
    }

    public StudentsAdapter(List<Student> students, OnItemClick onItemClick, boolean isAdmin) {
        this.students = students;
        setItemClick(onItemClick);
        this.isAdmin = isAdmin;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.title.setText(students.get(position).getName());
        holder.subTitle.setText(students.get(position).getEmail());

        if(getItemClick() != null){
            holder.viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getItemClick().onItemClick(position,holder.itemView);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}
