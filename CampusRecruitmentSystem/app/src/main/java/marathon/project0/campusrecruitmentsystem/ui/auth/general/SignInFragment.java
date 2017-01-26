package marathon.project0.campusrecruitmentsystem.ui.auth.general;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import marathon.project0.campusrecruitmentsystem.R;
import marathon.project0.campusrecruitmentsystem.base.AuthBaseFragment;
import marathon.project0.campusrecruitmentsystem.base.BasicFunctionalities;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class SignInFragment extends AuthBaseFragment {
    private OnLoggedInListener loggedInListener;

    public OnLoggedInListener getLoggedInListener() {
        return loggedInListener;
    }

    public void setLoggedInListener(OnLoggedInListener loggedInListener) {
        this.loggedInListener = loggedInListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setFragmentView(inflater.inflate(R.layout.sign_in,container,false));

        final EditText signInEmail = (EditText) findViewById(R.id.signInEmail);
        final EditText signInPassword = (EditText) findViewById(R.id.signInPassword);
        Button signInBtn = (Button) findViewById(R.id.signInBtn);

        auth = FirebaseAuth.getInstance();
        progressDialog = BasicFunctionalities.buildProgressDialog("Logging In",getContext());

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signInEmail.getText().toString();
                String password = signInPassword.getText().toString();

                if(email.length() <= 0){
                    signInEmail.setError("Please enter Email");
                    return;
                }
                if(password.length() <= 0){
                    signInPassword.setError("Please enter Password");
                    return;
                }

                progressDialog.show();
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (!task.isSuccessful()) {
                            Toast.makeText(getContext(), task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        } else {
                            loggedInListener.onLoggedIn();
                        }
                    }
                });
            }
        });

        return getFragmentView();
    }

    public interface OnLoggedInListener {
        void onLoggedIn();
    }
}