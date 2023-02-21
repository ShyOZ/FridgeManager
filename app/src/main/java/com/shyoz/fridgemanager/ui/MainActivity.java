package com.shyoz.fridgemanager.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.shyoz.fridgemanager.R;
import com.shyoz.fridgemanager.databinding.ActivityMainBinding;
import com.shyoz.fridgemanager.databinding.NavHeaderMainBinding;
import com.shyoz.fridgemanager.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private UserViewModel currentUser;

    private AppBarConfiguration appBarConfiguration;
    ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupAll();
    }

    private void setupAll() {
        currentUser = new ViewModelProvider(this).get(UserViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        DrawerLayout drawerLayout = binding.drawerLayout;
        Log.d("Drawer", "onCreate: " + drawerLayout);

        appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.home_dest)
                .setOpenableLayout(drawerLayout)
                .build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        setupActionBar(navController, appBarConfiguration);

        setupNavigationMenu(navController);

        setupNavigationListeners(navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setupActionBar(NavController navController, AppBarConfiguration appBarConfiguration) {
       NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void setupNavigationMenu(NavController navController) {
        NavigationView navigationView = binding.navView;
        NavigationUI.setupWithNavController(navigationView, navController);
        NavHeaderMainBinding headerBinding = NavHeaderMainBinding.bind(navigationView.getHeaderView(0));
        currentUser.getUsername().observe(this, headerBinding.navUsernameText::setText);
        currentUser.getEmail().observe(this, headerBinding.navEmailText::setText);
    }

    private void setupNavigationListeners(NavController navController) {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            String dest;
            try {
                dest = getResources().getResourceEntryName(destination.getId());
            } catch (Resources.NotFoundException e) {
                dest = "" + (destination.getId());
            }

            Log.d("Navigation", "Navigated to " + dest);
        });
    }
}