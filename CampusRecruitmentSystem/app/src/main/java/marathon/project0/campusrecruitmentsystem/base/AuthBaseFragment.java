package marathon.project0.campusrecruitmentsystem.base;

import android.app.ProgressDialog;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class AuthBaseFragment extends BaseFragment {
    public FirebaseAuth auth;
    public ProgressDialog progressDialog;


    @Override
    public void onDestroy() {
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        super.onDestroy();
    }
}
