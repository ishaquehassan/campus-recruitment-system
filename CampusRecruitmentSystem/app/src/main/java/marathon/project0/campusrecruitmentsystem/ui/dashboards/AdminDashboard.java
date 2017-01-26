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

import java.util.ArrayList;
import java.util.Arrays;

import marathon.project0.campusrecruitmentsystem.R;
import marathon.project0.campusrecruitmentsystem.adapters.dashboards.CompaniesAdapter;
import marathon.project0.campusrecruitmentsystem.adapters.general.SimplePagerAdapter;
import marathon.project0.campusrecruitmentsystem.base.BaseDashboardActivity;
import marathon.project0.campusrecruitmentsystem.base.BaseFragment;
import marathon.project0.campusrecruitmentsystem.base.BaseListRecyclerAdapter;
import marathon.project0.campusrecruitmentsystem.model.Company;

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

        BaseFragment list1 = new ListFragment();
        list1.setTitle("COMPANIES");
        pages.add(list1);

        BaseFragment list2 = new ListFragment();
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


    public static class ListFragment extends BaseFragment{
        public ListFragment(){

        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            setFragmentView(inflater.inflate(R.layout.dashboard_list,container,false));

            RecyclerView companiesList = (RecyclerView) findViewById(R.id.recyclerList);

            ArrayList<Company> companies = new ArrayList<>();
            Company[] companiesArr = new Company[]{
                    new Company("Company Name","info@company.com"),
                    new Company("Company Name","info@company.com"),
                    new Company("Company Name","info@company.com"),
                    new Company("Company Name","info@company.com"),
                    new Company("Company Name","info@company.com"),
                    new Company("Company Name","info@company.com"),
                    new Company("Company Name","info@company.com"),
                    new Company("Company Name","info@company.com"),
                    new Company("Company Name","info@company.com"),
                    new Company("Company Name","info@company.com"),
                    new Company("Company Name","info@company.com"),
            };

            companies.addAll(Arrays.asList(companiesArr));

            RecyclerView.Adapter companiesAdapter = new CompaniesAdapter(companies,new BaseListRecyclerAdapter.OnItemClick(){

                @Override
                public void onItemClick(int position,View v) {

                }
            });

            companiesList.setAdapter(companiesAdapter);
            companiesList.setLayoutManager(new LinearLayoutManager(getContext()));

            return getFragmentView();
        }
    }
}
