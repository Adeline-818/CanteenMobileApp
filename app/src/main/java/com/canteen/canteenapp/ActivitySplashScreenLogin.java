package com.canteen.canteenapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ActivitySplashScreenLogin extends AppCompatActivity {

    private DatabaseReference db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_welcome_after_login);
        final String College = LoginActivity.getDefaults("college" , this);
        final TextView po = findViewById(R.id.collegeTextWelcome);



        db = FirebaseDatabase.getInstance().getReference("Foods/"+College);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("oprClosing").exists()){
                    po.setText("Welcome to "+ College +" \nOperational Time :"+ new SimpleDateFormat("hh:mm aa").format(new Time(snapshot.child("oprOpening").getValue(Long.class))) +" - "+
                            new SimpleDateFormat("hh:mm aa").format(new Time(snapshot.child("oprClosing").getValue(Long.class))));

                }else{
                    po.setText("Welcome to \n"+ College );
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ActivitySplashScreenLogin.this,HomeMenuStudentActivity.class));
                finish();
            }
        },2000);

    }
}
