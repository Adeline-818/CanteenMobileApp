package com.canteen.canteenapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.canteen.canteenapp.models.RegistrationFoodSellerDTO;
import com.canteen.canteenapp.models.RegistrationStudentDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword , inputPassword2 , matricOrStallName, firstName , lastName , openTime , closeTime;
    private Spinner collegeBranches;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseUser userManage;
    private FirebaseDatabase db;
    private DatabaseReference dbReferences;
    private LinearLayout time;
    private Switch acc;
    private Date closingTime , openingTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputPassword2 = (EditText) findViewById(R.id.password2);
        matricOrStallName = (EditText) findViewById(R.id.matricID);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        collegeBranches = (Spinner) findViewById(R.id.collegeSelector);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        acc = (Switch) findViewById(R.id.switchAcc);
        time = (LinearLayout) findViewById(R.id.timeForSeller);
        openTime = (EditText) findViewById(R.id.OpenTime);
        closeTime = (EditText) findViewById(R.id.CloseTime);



        acc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    matricOrStallName.setHint("Name Of Food Stall");
                    acc.setText("Food Seller");
                    time.setVisibility(View.VISIBLE);

                }else{
                    matricOrStallName.setHint("Matric ID");
                    acc.setText("Customer");
                    time.setVisibility(View.GONE);


                }
            }
        });

        openTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(SignupActivity.this , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Date time = new  Time(hourOfDay , minute , 00);
                        openingTime = time;
                        openTime.setText(new SimpleDateFormat("hh:mm aa").format(time));
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });

        closeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(SignupActivity.this , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Date time = new  Time(hourOfDay , minute , 00);
                        closingTime = time;
                        closeTime.setText( new SimpleDateFormat("hh:mm aa").format(time));
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String matric = matricOrStallName.getText().toString().trim();
                String fname = firstName.getText().toString().trim();
                String lname = lastName.getText().toString().trim();

                if (TextUtils.isEmpty(fname)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(lname)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
                    Toast.makeText(getApplicationContext(), "Please enter the correct email address", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!acc.isChecked()){
                    if(!matric.matches("^[a-zA-Z].{1,}(?=.*\\d).{2,}$")){
                        Toast.makeText(getApplicationContext(), "Please put your correct matric number", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                progressBar.setVisibility(View.VISIBLE);
                //create user

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();



                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    db = FirebaseDatabase.getInstance();
                                    userManage = FirebaseAuth.getInstance().getCurrentUser();

                                    if(acc.isChecked()){
                                        dbReferences = db.getReference("/Seller/"+userManage.getUid()+"/");
                                        dbReferences.setValue(new RegistrationFoodSellerDTO(firstName.getText().toString() , lastName.getText().toString() ,  collegeBranches.getSelectedItem().toString() , matricOrStallName.getText().toString(), openingTime.getTime(), closingTime.getTime() , 41400000L , 41400000L));


                                    }else{

                                        dbReferences = db.getReference("/Student/"+userManage.getUid()+"/");
                                        dbReferences.setValue(new RegistrationStudentDTO(firstName.getText().toString() , lastName.getText().toString() ,  collegeBranches.getSelectedItem().toString() , matricOrStallName.getText().toString() ));



                                    }

                                    auth.signOut();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }

                        });

            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        progressBar.setVisibility(View.GONE);
//    }
}
