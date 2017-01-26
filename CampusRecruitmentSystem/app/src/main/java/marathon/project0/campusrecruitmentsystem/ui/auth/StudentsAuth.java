package marathon.project0.campusrecruitmentsystem.ui.auth;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import marathon.project0.campusrecruitmentsystem.R;
import marathon.project0.campusrecruitmentsystem.adapters.general.SimplePagerAdapter;
import marathon.project0.campusrecruitmentsystem.base.AuthBaseFragment;
import marathon.project0.campusrecruitmentsystem.base.BaseActivity;
import marathon.project0.campusrecruitmentsystem.base.BaseFragment;
import marathon.project0.campusrecruitmentsystem.base.BasicFunctionalities;
import marathon.project0.campusrecruitmentsystem.base.DatePickerFragment;
import marathon.project0.campusrecruitmentsystem.model.Student;
import marathon.project0.campusrecruitmentsystem.ui.auth.general.SignInFragment;
import marathon.project0.campusrecruitmentsystem.ui.dashboards.StudentsDashboard;

public class StudentsAuth extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_pager);
        flatToolbar();

        setTitle("STUDENTS SIGN UP / SIGN IN");

        ViewPager adminSignPager = (ViewPager) findViewById(R.id.pager);
        TabLayout adminSignTabs = (TabLayout) findViewById(R.id.tabs);

        ArrayList<BaseFragment> pages = new ArrayList<>();
        SignInFragment signInFragment = new SignInFragment();
        signInFragment.setTitle("STUDENT SIGN IN");
        signInFragment.setLoggedInListener(new SignInFragment.OnLoggedInListener() {
            @Override
            public void onLoggedIn() {
                SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.userSahredPrefKey),0);
                sharedPreferences.edit().putInt(getResources().getString(R.string.userSahredPrefUserType),3).apply();

                Intent intent = new Intent(StudentsAuth.this, StudentsDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        pages.add(signInFragment);
        pages.add(new SignUpFragment());

        SimplePagerAdapter adminSignPagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(),pages);
        adminSignPager.setAdapter(adminSignPagerAdapter);
        adminSignTabs.setupWithViewPager(adminSignPager);
    }



    public static class SignUpFragment extends AuthBaseFragment{
        public SignUpFragment(){
            setTitle("SIGN UP");
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            setFragmentView(inflater.inflate(R.layout.sign_up_student,container,false));

            auth = FirebaseAuth.getInstance();

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
            progressDialog = BasicFunctionalities.buildProgressDialog("Logging In",getContext());

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
                    String password = studentPasswordEt.getText().toString();
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
                    if(password.length() <= 0){
                        studentPasswordEt.setError("Please enter Password");
                        return;
                    }
                    if(cpassword.length() <= 0){
                        studentCpasswordEt.setError("Please Confirm Password");
                        return;
                    }
                    if(!cpassword.equals(password)){
                        studentCpasswordEt.setError("Confirmed Password does not math with Password");
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

                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    } else {
                                        database = FirebaseDatabase.getInstance();
                                        DatabaseReference usersRef = database.getReference(getResources().getString(R.string.firebaseStudentsNode));
                                        Student student = new Student(name,studentId,email,dob,((genderGroupId == R.id.genderMaleRadioBtn) ? 1 : 0),Double.valueOf(gpa),auth.getCurrentUser().getUid());
                                        usersRef.child(auth.getCurrentUser().getUid()).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.userSahredPrefKey),0);
                                                sharedPreferences.edit().putInt(getResources().getString(R.string.userSahredPrefUserType),3).apply();

                                                progressDialog.dismiss();
                                                Intent intent = new Intent(getContext(), StudentsDashboard.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }
                            });
                }
            });

            return getFragmentView();
        }
    }


}
