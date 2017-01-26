package marathon.project0.campusrecruitmentsystem.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class BaseViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<BaseFragment> pages = new ArrayList<>();

    public BaseViewPagerAdapter(FragmentManager fm,ArrayList<BaseFragment> pages) {
        super(fm);
        this.pages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}
