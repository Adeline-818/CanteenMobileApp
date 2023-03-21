package com.canteen.canteenapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class HomeMenuStudentActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference db;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dropdown_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logOut:
                auth.signOut();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();

        String College = LoginActivity.getDefaults("college" , this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu_student);


        final TextView po = findViewById(R.id.oprtime);
        db = FirebaseDatabase.getInstance().getReference("Foods/"+College);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("oprClosing").exists()){
                    po.setText( "Operational Time : "+ new SimpleDateFormat("hh:mm aa").format(new Time(snapshot.child("oprClosing").getValue(Long.class))) +" - "+
                            new SimpleDateFormat("hh:mm aa").format(new Time(snapshot.child("oprOpening").getValue(Long.class))));
                }else{
                    po.setText(" Your canteen doesnt have operational time");
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BottomNavigationView navViewStudent = findViewById(R.id.nav_view_student);
        NavController navControllerStudent = Navigation.findNavController(this, R.id.nav_host_fragment_student);

        AppBarConfiguration appBarConfigurationStudent = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications , R.id.navigation_status_student)
                .build();


        NavigationUI.setupActionBarWithNavController(this, navControllerStudent, appBarConfigurationStudent);

        NavigationUI.setupWithNavController(navViewStudent, navControllerStudent);

    }

}
