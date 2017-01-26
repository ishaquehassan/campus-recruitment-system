package marathon.project0.campusrecruitmentsystem.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import marathon.project0.campusrecruitmentsystem.R;
import marathon.project0.campusrecruitmentsystem.adapters.general.SimplePagerAdapter;
import marathon.project0.campusrecruitmentsystem.base.BaseActivity;
import marathon.project0.campusrecruitmentsystem.base.BaseFragment;
import marathon.project0.campusrecruitmentsystem.ui.auth.general.SignInFragment;
import marathon.project0.campusrecruitmentsystem.ui.dashboards.AdminDashboard;

public class AdminAuth extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_pager);

        ViewPager adminSignPager = (ViewPager) findViewById(R.id.pager);
        TabLayout adminSignTabs = (TabLayout) findViewById(R.id.tabs);
        adminSignTabs.setVisibility(View.GONE);

        ArrayList<BaseFragment> pages = new ArrayList<>();
        SignInFragment signInFragment = new SignInFragment();
        signInFragment.setTitle("ADMIN SIGN IN");
        signInFragment.setLoggedInListener(new SignInFragment.OnLoggedInListener() {
            @Override
            public void onLoggedIn() {
                SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.userSahredPrefKey),0);
                sharedPreferences.edit().putInt(getResources().getString(R.string.userSahredPrefUserType),1).apply();

                Intent intent = new Intent(AdminAuth.this, AdminDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        pages.add(signInFragment);

        SimplePagerAdapter adminSignPagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(),pages);
        adminSignPager.setAdapter(adminSignPagerAdapter);
        adminSignTabs.setupWithViewPager(adminSignPager);
    }

}
