package com.example.save_v04;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.save_v04.databinding.ActivityItemDetailBinding;
import com.example.save_v04.placeholder.DB_Handler;
import com.example.save_v04.placeholder.PlaceholderContent;

public class ItemDetailHostActivity extends AppCompatActivity {

    // DB connection class
    private PlaceholderContent db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up DB, using context from this activity
        // Connect to DB first, because data is needed for recyclerView
        // otherwise, db object is null during recyclerView setup and fails
        // Connect to DB ... move this to background task later but
        // be mindful of the order these tasks are done.
        db = new PlaceholderContent(this);


        ActivityItemDetailBinding binding = ActivityItemDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_item_detail);
        NavController navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.
                Builder(navController.getGraph())
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_item_detail);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}