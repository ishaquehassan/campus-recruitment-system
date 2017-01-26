package marathon.project0.campusrecruitmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import marathon.project0.campusrecruitmentsystem.base.BaseActivity;
import marathon.project0.campusrecruitmentsystem.ui.auth.AdminAuth;
import marathon.project0.campusrecruitmentsystem.ui.auth.CompanyAuth;
import marathon.project0.campusrecruitmentsystem.ui.auth.StudentsAuth;
import marathon.project0.campusrecruitmentsystem.ui.dashboards.AdminDashboard;
import marathon.project0.campusrecruitmentsystem.ui.dashboards.CompaniesDashboard;
import marathon.project0.campusrecruitmentsystem.ui.dashboards.StudentsDashboard;

public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int loginType = getStoredSharedPreferences().getInt(getResources().getString(R.string.userSahredPrefUserType),0);
        if(isLoggedIn() && loginType > 0){
            switch (loginType){
                case 1:
                    startActivity(new Intent(MainActivity.this, AdminDashboard.class));
                    finish();
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, CompaniesDashboard.class));
                    finish();
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, StudentsDashboard.class));
                    finish();
                    break;
                default:
                    getAuth().signOut();
            }
        }

        Button adminSignUp = (Button) findViewById(R.id.adminSignUp);
        Button companySignUp = (Button) findViewById(R.id.companySignUp);
        Button studentSignUp = (Button) findViewById(R.id.studentSignUp);

        adminSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AdminAuth.class));
            }
        });

        companySignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CompanyAuth.class));
            }
        });


        studentSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StudentsAuth.class));
            }
        });
    }
}
