package marathon.project0.campusrecruitmentsystem.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import marathon.project0.campusrecruitmentsystem.R;
import marathon.project0.campusrecruitmentsystem.adapters.general.SimplePagerAdapter;
import marathon.project0.campusrecruitmentsystem.base.BaseActivity;
import marathon.project0.campusrecruitmentsystem.base.BaseFragment;
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

                startActivity(new Intent(StudentsAuth.this, StudentsDashboard.class));
            }
        });
        pages.add(signInFragment);
        pages.add(new SignUpFragment());

        SimplePagerAdapter adminSignPagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(),pages);
        adminSignPager.setAdapter(adminSignPagerAdapter);
        adminSignTabs.setupWithViewPager(adminSignPager);
    }



    public static class SignUpFragment extends BaseFragment{
        public SignUpFragment(){
            setTitle("SIGN UP");
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            setFragmentView(inflater.inflate(R.layout.sign_up_student,container,false));



            /* dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment newFragment = new DatePickerFragment();
                    newFragment.setOnDateSetListener(new DatePickerDialog.OnDateSetListener(){
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            dateOfBirth[0] = day+"/"+(month+1)+"/"+year;
                            dob.setText(dateOfBirth[0]);
                        }
                    });
                    newFragment.show(getSupportFragmentManager(), "datePicker");
                }
            });*/

            return getFragmentView();
        }
    }


}
