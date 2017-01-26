package marathon.project0.campusrecruitmentsystem.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.MenuItem;

import marathon.project0.campusrecruitmentsystem.MainActivity;
import marathon.project0.campusrecruitmentsystem.R;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class BaseDashboardActivity extends BaseActivity {
    private CollapsingToolbarLayout toolbar_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
            toolbar_layout.setTitle("Dashboard");
        }catch (Exception e){
            e.printStackTrace();
        }

        /*try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    protected void setToolbarTitle(String title){
        toolbar_layout.setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    public void signOut(){
        getAuth().signOut();
        getStoredSharedPreferences().edit().remove(getResources().getString(R.string.userSahredPrefUserType)).apply();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
