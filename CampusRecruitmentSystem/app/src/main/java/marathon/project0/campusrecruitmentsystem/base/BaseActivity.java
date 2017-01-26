package marathon.project0.campusrecruitmentsystem.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import marathon.project0.campusrecruitmentsystem.R;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(getResources().getString(R.string.userSahredPrefKey),0);
    }

    public SharedPreferences getStoredSharedPreferences() {
        return sharedPreferences;
    }

    public void flatToolbar(){
        try{
            getSupportActionBar().setElevation(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public boolean isLoggedIn(){
        return (auth.getCurrentUser() != null);
    }
}
