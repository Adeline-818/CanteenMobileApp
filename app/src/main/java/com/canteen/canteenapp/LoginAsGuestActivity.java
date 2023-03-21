package com.canteen.canteenapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.canteen.canteenapp.models.RegistrationStudentDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static java.security.AccessController.getContext;

//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;

public class LoginAsGuestActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnLoginGuest, btnBack;
    private ProgressBar progressBar;
    private Spinner collegeBranches;

    private DatabaseReference dbReferences;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private static final String TAG = "LoginGuest";

    private String token;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private Intent intent = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_guest);

        btnLoginGuest = (Button) findViewById(R.id.btnLoginAsGuest);
        collegeBranches = (Spinner) findViewById(R.id.spinnerCollegeGuest);
        progressBar = (ProgressBar) findViewById(R.id.progressBarGuest);
        collegeBranches = (Spinner) findViewById(R.id.spinnerCollegeGuest);
        auth = FirebaseAuth.getInstance();

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();


        btnLoginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                auth.signInAnonymously().addOnCompleteListener(LoginAsGuestActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            user =FirebaseAuth.getInstance().getCurrentUser();
                            // Sign in success, update UI with the signed-in user's information
                            dbReferences = FirebaseDatabase.getInstance().getReference("/Student/" + user.getUid() + "/");
                            dbReferences.setValue(new RegistrationStudentDTO("anonymous", "anonymous", collegeBranches.getSelectedItem().toString(), "anonymous"));

                            signToken(new writeLogin() {
                                @Override
                                public void onCallBack(String Token) {
                                    dbReferences.child("token").setValue(Token);
                                    LoginActivity.setDefaults("token" , Token , LoginAsGuestActivity.this);

                                }
                            });

                            editor.putInt("saved_account", 1);
                            editor.apply();

                            LoginActivity.setDefaults("college" , collegeBranches.getSelectedItem().toString() , LoginAsGuestActivity.this);
                            LoginActivity.setDefaults("guest" , "guest", LoginAsGuestActivity.this);


                            intent = new Intent(LoginAsGuestActivity.this, ActivitySplashScreenLogin.class);
                            startActivity(intent);
                            finish();


                            Toast.makeText(LoginAsGuestActivity.this, "Authentication Success.",
                                    Toast.LENGTH_SHORT).show();

                        } else {

                            System.out.println(task.getException());
                            Log.w(TAG, "signInAnonymously:failure", task.getException());

                            Toast.makeText(LoginAsGuestActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }


                    }


                });
            }
        });


    }

    private void signToken(final writeLogin call){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }
                        token = task.getResult().getToken();
                        call.onCallBack(token);


                    }
                });

    }

    private interface writeLogin{
        void onCallBack(String Token);
    }
}
