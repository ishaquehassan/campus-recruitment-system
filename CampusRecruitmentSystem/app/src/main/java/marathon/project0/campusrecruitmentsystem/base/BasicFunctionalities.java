package marathon.project0.campusrecruitmentsystem.base;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class BasicFunctionalities {

    public static ProgressDialog buildProgressDialog(String message, Context context){
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage(message);
        pd.setCancelable(false);
        return pd;
    }
}
