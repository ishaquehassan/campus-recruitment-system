package marathon.project0.campusrecruitmentsystem.ui.dashboards;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
import marathon.project0.campusrecruitmentsystem.adapters.general.SimplePagerAdapter;
import marathon.project0.campusrecruitmentsystem.base.AuthBaseFragment;
import marathon.project0.campusrecruitmentsystem.base.BaseDashboardActivity;
import marathon.project0.campusrecruitmentsystem.base.BaseFragment;
import marathon.project0.campusrecruitmentsystem.base.BaseListRecyclerAdapter;
import marathon.project0.campusrecruitmentsystem.base.BasicFunctionalities;
import marathon.project0.campusrecruitmentsystem.base.DatePickerFragment;
import marathon.project0.campusrecruitmentsystem.model.Company;
import marathon.project0.campusrecruitmentsystem.model.Student;

public class StudentsDashboard extends BaseDashboardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.onCreate(savedInstanceState);
        setToolbarTitle("Student Dashboard");

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        ArrayList<BaseFragment> pages = new ArrayList<>();

        BaseFragment list1 = new ListFragment();
        list1.setTitle("COMPANIES");
        pages.add(list1);

        BaseFragment list2 = new EditProfileFragment();
        list2.setTitle("PROFILE");
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

            final List<Company> companies = new ArrayList<>();

            final RecyclerView.Adapter companiesAdapter = new CompaniesAdapter(companies,new BaseListRecyclerAdapter.OnItemClick(){

                @Override
                public void onItemClick(int position,View v) {

                }
            });

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
    }


    public static class EditProfileFragment extends AuthBaseFragment {
        public EditProfileFragment(){
            setTitle("SIGN UP");
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            setFragmentView(inflater.inflate(R.layout.sign_up_student,container,false));
            progressDialog = BasicFunctionalities.buildProgressDialog("Loading Profile...",getContext());
            progressDialog.show();

            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            database.getReference().child(getResources().getString(R.string.firebaseStudentsNode)).child(auth.getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Student student = dataSnapshot.getValue(Student.class);
                            loadStudentForm(student);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

            return getFragmentView();
        }

        protected void loadStudentForm(Student student){
            progressDialog.dismiss();
            final EditText studentNameEt = (EditText) findViewById(R.id.studentNameEt);
            final EditText studentIdEt = (EditText) findViewById(R.id.studentIdEt);
            final EditText studentEmailEt = (EditText) findViewById(R.id.studentEmailEt);
            final EditText studentPasswordEt = (EditText) findViewById(R.id.studentPasswordEt);
            final EditText studentCpasswordEt = (EditText) findViewById(R.id.studentCpasswordEt);
            final EditText studentDobEt = (EditText) findViewById(R.id.studentDob);
            final EditText studentGpaEt = (EditText) findViewById(R.id.studentGpaEt);
            final RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
            final RadioButton genderMaleRadioBtn = (RadioButton) findViewById(R.id.genderMaleRadioBtn);
            final RadioButton genderFemaleRadioBtn = (RadioButton) findViewById(R.id.genderFemaleRadioBtn);

            Button signUpBtn = (Button) findViewById(R.id.signUpBtn);

            studentEmailEt.setEnabled(false);

            studentNameEt.setText(student.getName());
            studentIdEt.setText(student.getStudentId());
            studentEmailEt.setText(student.getEmail());
            studentDobEt.setText(student.getDob());
            studentGpaEt.setText(String.valueOf(student.getMarks()));

            if(student.getGender() == 1){
                genderMaleRadioBtn.setChecked(true);
                genderRadioGroup.clearCheck();
            }else{
                genderFemaleRadioBtn.setChecked(true);
                genderRadioGroup.clearCheck();
            }

            studentDobEt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment newFragment = new DatePickerFragment();
                    newFragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener(){
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            String dob = day+"/"+(month+1)+"/"+year;
                            studentDobEt.setText(dob);
                        }
                    });
                    newFragment.show(getChildFragmentManager(), "datePicker");
                }
            });

            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String name = studentNameEt.getText().toString();
                    final String studentId = studentIdEt.getText().toString();
                    final String email = studentEmailEt.getText().toString();
                    final String password = studentPasswordEt.getText().toString();
                    String cpassword = studentCpasswordEt.getText().toString();
                    final String dob = studentDobEt.getText().toString();
                    final int genderGroupId = genderRadioGroup.getCheckedRadioButtonId();
                    final String gpa = studentGpaEt.getText().toString();

                    if(name.length() <= 0){
                        studentNameEt.setError("Please enter Name");
                        return;
                    }
                    if(studentId.length() <= 0){
                        studentIdEt.setError("Please enter Student ID");
                        return;
                    }
                    if(email.length() <= 0){
                        studentEmailEt.setError("Please enter Email");
                        return;
                    }
                    if(password.length() > 0){
                        if(cpassword.length() <= 0){
                            studentCpasswordEt.setError("Please Confirm Password");
                            return;
                        }
                        if(!cpassword.equals(password)){
                            studentCpasswordEt.setError("Confirmed Password does not math with Password");
                            return;
                        }
                        return;
                    }

                    if(dob.length() <= 0){
                        studentDobEt.setError("Please enter Date Of Birth");
                        return;
                    }
                    if(genderGroupId == -1){
                        genderMaleRadioBtn.setError("Please Select Gender");
                        return;
                    }
                    if(gpa.length() <= 0){
                        studentGpaEt.setError("Please Enter Percentage / GPA");
                        return;
                    }

                    progressDialog.show();

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference usersRef = db.getReference(getResources().getString(R.string.firebaseStudentsNode));
                    Student student = new Student(name,studentId,email,dob,((genderGroupId == R.id.genderMaleRadioBtn) ? 1 : 0),Double.valueOf(gpa),auth.getCurrentUser().getUid());
                    usersRef.child(auth.getCurrentUser().getUid()).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.userSahredPrefKey),0);
                            sharedPreferences.edit().putInt(getResources().getString(R.string.userSahredPrefUserType),3).apply();
                            auth.getCurrentUser().updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }
    }

}
