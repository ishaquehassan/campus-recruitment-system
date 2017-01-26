package marathon.project0.campusrecruitmentsystem.adapters.general;

import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

import marathon.project0.campusrecruitmentsystem.base.BaseFragment;
import marathon.project0.campusrecruitmentsystem.base.BaseViewPagerAdapter;

public class SimplePagerAdapter extends BaseViewPagerAdapter {
    public SimplePagerAdapter(FragmentManager fm, ArrayList<BaseFragment> pages) {
        super(fm, pages);
    }
}