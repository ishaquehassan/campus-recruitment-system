package marathon.project0.campusrecruitmentsystem.ui.auth;

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
import android.widget.EditText;
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
import marathon.project0.campusrecruitmentsystem.model.Company;
import marathon.project0.campusrecruitmentsystem.ui.auth.general.SignInFragment;
import marathon.project0.campusrecruitmentsystem.ui.dashboards.CompaniesDashboard;

public class CompanyAuth extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_pager);
        flatToolbar();

        setTitle("COMPANIES SIGN UP / SIGN IN");

        ViewPager adminSignPager = (ViewPager) findViewById(R.id.pager);
        TabLayout adminSignTabs = (TabLayout) findViewById(R.id.tabs);

        ArrayList<BaseFragment> pages = new ArrayList<>();
        SignInFragment signInFragment = new SignInFragment();
        signInFragment.setTitle("COMPANY SIGN IN");
        signInFragment.setLoggedInListener(new SignInFragment.OnLoggedInListener() {
            @Override
            public void onLoggedIn() {
                SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.userSahredPrefKey),0);
                sharedPreferences.edit().putInt(getResources().getString(R.string.userSahredPrefUserType),2).apply();

                Intent intent = new Intent(CompanyAuth.this, CompaniesDashboard.class);
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
            setFragmentView(inflater.inflate(R.layout.sign_up_company,container,false));
            auth = FirebaseAuth.getInstance();

            final EditText companyNameEt = (EditText) findViewById(R.id.companyNameEt);
            final EditText companyEmailEt = (EditText) findViewById(R.id.companyEmailEt);
            final EditText companyPasswordEt = (EditText) findViewById(R.id.companyPasswordEt);
            final EditText companyCpasswordEt = (EditText) findViewById(R.id.companyCpasswordEt);
            Button signUpBtn = (Button) findViewById(R.id.signUpBtn);

            progressDialog = BasicFunctionalities.buildProgressDialog("Logging In",getContext());

            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String name = companyNameEt.getText().toString();
                    final String email = companyEmailEt.getText().toString();
                    String password = companyPasswordEt.getText().toString();
                    String cpassword = companyCpasswordEt.getText().toString();

                    if(name.length() <= 0){
                        companyNameEt.setError("Please enter Company Name");
                        return;
                    }
                    if(email.length() <= 0){
                        companyEmailEt.setError("Please enter Company Email");
                        return;
                    }
                    if(password.length() <= 0){
                        companyPasswordEt.setError("Please enter Password");
                        return;
                    }
                    if(cpassword.length() <= 0){
                        companyCpasswordEt.setError("Please Confirm Password");
                        return;
                    }
                    if(!cpassword.equals(password)){
                        companyCpasswordEt.setError("Confirmed Password does not math with Password");
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
                                        DatabaseReference usersRef = database.getReference(getResources().getString(R.string.firebaseCompaniesNode));
                                        String key =  usersRef.push().getKey();
                                        Company company = new Company(name,email,key,auth.getCurrentUser().getUid());
                                        usersRef.child(key).setValue(company).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.userSahredPrefKey),0);
                                                sharedPreferences.edit().putInt(getResources().getString(R.string.userSahredPrefUserType),2).apply();

                                                progressDialog.dismiss();
                                                Intent intent = new Intent(getContext(), CompaniesDashboard.class);
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
