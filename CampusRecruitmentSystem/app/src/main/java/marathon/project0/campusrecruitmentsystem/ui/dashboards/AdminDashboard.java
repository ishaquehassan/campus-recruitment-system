package marathon.project0.campusrecruitmentsystem.ui.dashboards;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import marathon.project0.campusrecruitmentsystem.R;
import marathon.project0.campusrecruitmentsystem.adapters.dashboards.CompaniesAdapter;
import marathon.project0.campusrecruitmentsystem.adapters.dashboards.StudentsAdapter;
import marathon.project0.campusrecruitmentsystem.adapters.general.SimplePagerAdapter;
import marathon.project0.campusrecruitmentsystem.base.BaseDashboardActivity;
import marathon.project0.campusrecruitmentsystem.base.BaseFragment;
import marathon.project0.campusrecruitmentsystem.base.BaseListRecyclerAdapter;
import marathon.project0.campusrecruitmentsystem.base.BasicFunctionalities;
import marathon.project0.campusrecruitmentsystem.model.Company;
import marathon.project0.campusrecruitmentsystem.model.Student;

public class AdminDashboard extends BaseDashboardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.onCreate(savedInstanceState);
        setToolbarTitle("Admin Dashboard");

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        ArrayList<BaseFragment> pages = new ArrayList<>();

        BaseFragment list1 = new CompaniesList();
        list1.setTitle("COMPANIES");
        pages.add(list1);

        BaseFragment list2 = new StudentsList();
        list2.setTitle("STUDENTS");
        pages.add(list2);
        pager.setOffscreenPageLimit(pages.size());

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


    public static class CompaniesList extends BaseFragment{
        public CompaniesList(){

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            setFragmentView(inflater.inflate(R.layout.dashboard_list,container,false));

            final ProgressBar loading = (ProgressBar) findViewById(R.id.loading);
            loading.setVisibility(View.VISIBLE);

            final RecyclerView companiesList = (RecyclerView) findViewById(R.id.recyclerList);

            final List<Company> companies = new ArrayList<>();

            final RecyclerView.Adapter companiesAdapter = new CompaniesAdapter(companies,new BaseListRecyclerAdapter.OnItemClick(){

                @Override
                public void onItemClick(int position,View v) {
                    viewDetails(companies.get(position));
                }
            },true);

            database = FirebaseDatabase.getInstance();
            DatabaseReference studentsNodeRef = database.getReference().child(getResources().getString(R.string.firebaseCompaniesNode));
            studentsNodeRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        try{
                            Company company = dataSnapshot.getValue(Company.class);
                            companies.add(company);
                            companiesList.scrollToPosition(companies.size() - 1);
                            companiesAdapter.notifyItemInserted(companies.size() - 1);
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

            companiesList.setAdapter(companiesAdapter);
            companiesList.setLayoutManager(new LinearLayoutManager(getContext()));

            return getFragmentView();
        }

        private void viewDetails(Company company){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.company_view_dialog, null);
            dialogBuilder.setView(dialogView);

            TextView name = (TextView) dialogView.findViewById(R.id.companyName);
            name.setText(company.getName());

            TextView email = (TextView) dialogView.findViewById(R.id.companyEmail);
            email.setText(company.getEmail());

            dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogBuilder.setCancelable(false);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
        }
    }

    public static class StudentsList extends BaseFragment{
        public StudentsList(){

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            setFragmentView(inflater.inflate(R.layout.dashboard_list,container,false));

            final ProgressBar loading = (ProgressBar) findViewById(R.id.loading);
            loading.setVisibility(View.VISIBLE);

            final ProgressDialog progressDialog = BasicFunctionalities.buildProgressDialog("Deleting Item",getContext());

            database = FirebaseDatabase.getInstance();
            final DatabaseReference studentsNodeRef = database.getReference().child(getResources().getString(R.string.firebaseStudentsNode));

            final RecyclerView companiesList = (RecyclerView) findViewById(R.id.recyclerList);

            final List<Student> students = new ArrayList<>();



            final StudentsAdapter studentsAdapter = new StudentsAdapter(students,new BaseListRecyclerAdapter.OnItemClick(){
                @Override
                public void onItemClick(final int position, View v) {
                    viewDetails(students.get(position));
                }
            },true);

            studentsAdapter.setDeleteClick(new BaseListRecyclerAdapter.OnDeleteClick() {
                @Override
                public void onDeleteClick(final int position, View v) {
                    progressDialog.show();
                    studentsNodeRef.child(students.get(position).getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            students.remove(position);
                            studentsAdapter.notifyItemRemoved(position);
                            Toast.makeText(getContext(),"Item Deleted",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),"Item Deleted",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                        }
                    });
                }
            });


            studentsNodeRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        try{
                            Student student = dataSnapshot.getValue(Student.class);
                            students.add(student);
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

        private void viewDetails(Student student){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.student_view_dialog, null);
            dialogBuilder.setView(dialogView);

            TextView name = (TextView) dialogView.findViewById(R.id.studentName);
            name.setText(student.getName());

            TextView id = (TextView) dialogView.findViewById(R.id.studentId);
            id.setText(student.getStudentId());

            TextView email = (TextView) dialogView.findViewById(R.id.studentEmail);
            email.setText(student.getEmail());

            TextView dob = (TextView) dialogView.findViewById(R.id.studentDob);
            dob.setText(student.getDob());

            TextView gpa = (TextView) dialogView.findViewById(R.id.studentGpa);
            gpa.setText(String.valueOf(student.getMarks()));

            dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogBuilder.setCancelable(false);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
        }
    }
}
