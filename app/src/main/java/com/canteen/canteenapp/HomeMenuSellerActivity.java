package com.canteen.canteenapp;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class HomeMenuSellerActivity extends AppCompatActivity {

    private FirebaseAuth authd;

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
                authd.signOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        authd = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activrty_home_menu_seller);

        BottomNavigationView navViewSeller = findViewById(R.id.nav_view_seller);
        NavController navControllerSeller = Navigation.findNavController(this, R.id.nav_host_fragment_seller);

        AppBarConfiguration appBarConfigurationSeller = new AppBarConfiguration.Builder(
                R.id.orderSeller, R.id.foodSeller, R.id.profileSeller)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navControllerSeller, appBarConfigurationSeller);

        NavigationUI.setupWithNavController(navViewSeller, navControllerSeller);






    }
}


