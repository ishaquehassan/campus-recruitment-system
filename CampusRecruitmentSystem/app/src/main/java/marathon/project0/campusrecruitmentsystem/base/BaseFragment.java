package marathon.project0.campusrecruitmentsystem.base;


import android.support.v4.app.Fragment;
import android.view.View;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    private String title;
    private View fragmentView;

    public View getFragmentView() {
        return fragmentView;
    }

    public void setFragmentView(View fragmentView) {
        this.fragmentView = fragmentView;
    }

    public BaseFragment() {
        // Required empty public constructor
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected View findViewById(int id){
        return getFragmentView().findViewById(id);
    }
}
