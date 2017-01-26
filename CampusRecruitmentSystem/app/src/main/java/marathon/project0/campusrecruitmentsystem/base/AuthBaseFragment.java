package marathon.project0.campusrecruitmentsystem.base;

import android.app.ProgressDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class AuthBaseFragment extends BaseFragment {
    public FirebaseAuth auth;
    public FirebaseDatabase database;
    public ProgressDialog progressDialog;


    @Override
    public void onDestroy() {
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        super.onDestroy();
    }
}
