package com.canteen.canteenapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.Toolbar;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.ContentValues.TAG;


public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset , btnLoginGuest;
    private Switch loginSwit;
    private DatabaseReference dbReferences;
    private FirebaseUser userManage;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editor2;
    private int flagAccount = 0;
    private Intent intent = null;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Stetho.initializeWithDefaults(this);
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor2 = sharedPref.edit();

        if (auth.getCurrentUser() != null) {

            int flag = sharedPref.getInt("saved_account",0);

            if( flag == 1){
                startActivity(new Intent(LoginActivity.this, HomeMenuStudentActivity.class));
                finish();

            }else if (flag == 0){
                startActivity(new Intent(LoginActivity.this, HomeMenuSellerActivity.class));
                finish();
            }


        }

        // set the view now
        setContentView(R.layout.activity_login);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup_student);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_resetpass);
        btnLoginGuest = (Button) findViewById(R.id.btn_login_guest);
        loginSwit = (Switch) findViewById(R.id.AccountLogin);

        loginSwit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    loginSwit.setText("Login as Customer");
                }else{
                    loginSwit.setText("Login as Food Seller");

                }
            }
        });

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });


        btnLoginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LoginAsGuestActivity.class));
            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {

                                    userManage = FirebaseAuth.getInstance().getCurrentUser();

                                    if (loginSwit.isChecked()){

                                        Toast.makeText(LoginActivity.this, "Student ", Toast.LENGTH_LONG).show();

                                        dbReferences = FirebaseDatabase.getInstance().getReference("/Student/" );

                                        flagAccount = 1;

                                    }else {

                                        Toast.makeText(LoginActivity.this, "Seller", Toast.LENGTH_LONG).show();

                                        dbReferences = FirebaseDatabase.getInstance().getReference("/Seller/");
                                    }

                                    authenticate(new callback1() {


                                        @Override
                                        public void onCallBack() {

                                            getCollegeNameAsStudent(new callback12() {
                                                @Override
                                                public void onCallBack(String college) {
                                                    setDefaults("college" , college , getContext());

                                                    signToken(new callback2() {
                                                        @Override
                                                        public void onCallBack(String token) {

                                                            setDefaults("guest" , "noguest", LoginActivity.this);


                                                            if(flagAccount == 1){
                                                                editor.putInt("saved_account", 1);
                                                                editor.apply();
                                                                dbReferences = FirebaseDatabase.getInstance().getReference("/Student/"+userManage.getUid()+"/");
                                                                System.out.println(userManage.getUid() +" "+token);
                                                                dbReferences.child("token").setValue(token);
                                                                setDefaults("token" , token , getContext());


                                                                intent = new Intent(LoginActivity.this, ActivitySplashScreenLogin.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }else if(flagAccount == 0){
                                                                editor.putInt("saved_account", 0);
                                                                editor.apply();
                                                                dbReferences = FirebaseDatabase.getInstance().getReference("/Seller/"+userManage.getUid()+"/");
                                                                System.out.println(userManage.getUid());
                                                                dbReferences.child("token").setValue(token);
                                                                setDefaults("token" , token , getContext());


                                                                intent = new Intent(LoginActivity.this, HomeMenuSellerActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }



                                                        }
                                                    });


                                                }
                                            });






                                        }
                                    });






                                }
                            }
                        });
            }
        });
    }

    private void signToken(final callback2 call){
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

    private void getCollegeNameAsStudent(final callback12 callback12){
        if (flagAccount == 1){
            dbReferences = FirebaseDatabase.getInstance().getReference("/Student/"+userManage.getUid()+"/college" );
            dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG ,snapshot.getValue(String.class) );
                    callback12.onCallBack(snapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            dbReferences = FirebaseDatabase.getInstance().getReference("/Seller/"+userManage.getUid()+"/college" );
            dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d(TAG ,snapshot.getValue(String.class) );
                    callback12.onCallBack(snapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }


    private void authenticate(final callback1 call){
        dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(userManage.getUid()).exists()){
                    call.onCallBack();
                }else{
                    Toast.makeText(LoginActivity.this, "Your Data dont exist !", Toast.LENGTH_LONG).show();
                    flagAccount = 0;
                    auth.signOut();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private interface  callback12{
        void onCallBack(String college);
    }

    private interface callback2 {
        void onCallBack(String token);
    }

    private interface callback1 {
        void onCallBack();
    }

    public Context getContext(){
        Context mContext = LoginActivity.this;
        return mContext;
    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }



}

