package marathon.project0.campusrecruitmentsystem.ui.dashboards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import marathon.project0.campusrecruitmentsystem.R;
import marathon.project0.campusrecruitmentsystem.adapters.dashboards.StudentsAdapter;
import marathon.project0.campusrecruitmentsystem.adapters.general.SimplePagerAdapter;
import marathon.project0.campusrecruitmentsystem.base.BaseDashboardActivity;
import marathon.project0.campusrecruitmentsystem.base.BaseFragment;
import marathon.project0.campusrecruitmentsystem.base.BaseListRecyclerAdapter;
import marathon.project0.campusrecruitmentsystem.model.Student;

public class CompaniesDashboard extends BaseDashboardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.onCreate(savedInstanceState);
        setToolbarTitle("Company Dashboard");

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        //tabs.setVisibility(View.GONE);

        ArrayList<BaseFragment> pages = new ArrayList<>();

        BaseFragment list1 = new ListFragment();
        list1.setTitle("STUDENTS");
        pages.add(list1);

        /*BaseFragment list2 = new ListFragment();
        list2.setTitle("PROFILE");
        pages.add(list2);
        pager.setOffscreenPageLimit(pages.size());*/

        SimplePagerAdapter adminSignPagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(),pages);
        pager.setAdapter(adminSignPagerAdapter);
        tabs.setupWithViewPager(pager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }


    public static class ListFragment extends BaseFragment{
        public ListFragment(){

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            setFragmentView(inflater.inflate(R.layout.dashboard_list,container,false));

            final ProgressBar loading = (ProgressBar) findViewById(R.id.loading);
            loading.setVisibility(View.VISIBLE);

            final RecyclerView companiesList = (RecyclerView) findViewById(R.id.recyclerList);

            final List<Student> students = new ArrayList<>();

            final RecyclerView.Adapter studentsAdapter = new StudentsAdapter(students,new BaseListRecyclerAdapter.OnItemClick(){

                @Override
                public void onItemClick(int position,View v) {
                    
                }
            });

            database = FirebaseDatabase.getInstance();
            DatabaseReference studentsNodeRef = database.getReference().child(getResources().getString(R.string.firebaseStudentsNode));
            studentsNodeRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        try{
                            Student student = dataSnapshot.getValue(Student.class);
                            students.add(student);
                            companiesList.scrollToPosition(students.size() - 1);
                            studentsAdapter.notifyItemInserted(students.size() - 1);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            studentsNodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    loading.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            companiesList.setAdapter(studentsAdapter);
            companiesList.setLayoutManager(new LinearLayoutManager(getContext()));

            return getFragmentView();
        }
    }

}
