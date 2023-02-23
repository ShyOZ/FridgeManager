package com.shyoz.fridgemanager.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.shyoz.fridgemanager.R;
import com.shyoz.fridgemanager.data.DataManager;
import com.shyoz.fridgemanager.data.DatabaseUser;
import com.shyoz.fridgemanager.data.Storage;
import com.shyoz.fridgemanager.databinding.ActivityMainBinding;
import com.shyoz.fridgemanager.databinding.NavHeaderMainBinding;
import com.shyoz.fridgemanager.model.DataModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DataModel dataModel;

    FirebaseUser firebaseUser = null;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;

    private int currentDestinationId = 0;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataModel = new ViewModelProvider(this).get(DataModel.class);
        setupUI();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null)
            signIn();
        else {
            Log.d("MainActivity", "User already signed in");
            setupAfterSignIn();
        }
    }

    private void setupUI() {
        Log.d("MainActivity", "Setting up UI");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        dataModel.getTitle().observe(this, binding.toolbar::setTitle);

        DrawerLayout drawerLayout = binding.drawerLayout;

        appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.categories_dest, R.id.settings_dest)
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

    public void signIn() {
        Log.d("MainActivity", "Signing in");
        dataModel = new ViewModelProvider(this).get(DataModel.class);
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                        List.of(new AuthUI.IdpConfig.GoogleBuilder().build())
                )
                .build();

        signInLauncher.launch(signInIntent);
    }

    private void setupAfterSignIn() {
        Log.d("MainActivity", "Setting up after sign in");
        // Successfully signed in
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        dataModel.setDisplayName(firebaseUser.getDisplayName())
                .setEmail(firebaseUser.getEmail());
        dataModel.setPhotoUrl(firebaseUser.getPhotoUrl());

        ValueEventListener loadDefaultStorage = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("MainActivity", "Loading default storage");
                    Storage storage = dataModel.setStorage(snapshot.getValue(Storage.class))
                            .getStorage().getValue();
                    assert storage != null;
                    storage.setId(snapshot.getKey());
                    Log.d("MainActivity", "Default storage loaded");
                    Log.d("MainActivity", "Navigating to storage: " + storage.getDisplayName());
                    dataModel.setTitle(storage.getDisplayName());
                    navController.navigate(R.id.action_global_signed_in, null, new NavOptions.Builder().setPopUpTo(currentDestinationId, true).build());
                } else
                    Log.e("MainActivity", "Default storage not found");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "Error loading default storage: " + error.getMessage());
            }
        };

        ValueEventListener loadUser = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("MainActivity", "Loading user data");
                    dataModel.setDatabaseUser(snapshot.getValue(DatabaseUser.class));
                    assert dataModel.getDatabaseUser().getValue() != null;
                    Log.d("MainActivity", "User data loaded");
                    Log.d("MainActivity", dataModel.getDatabaseUser().getValue().toString());
                    DataManager.getInstance().loadStorage(dataModel.getDatabaseUser().getValue().getDefaultStorageId(), loadDefaultStorage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "Error loading user: " + error.getMessage());
            }
        };

        DataManager.getInstance().loadUserData(firebaseUser.getUid(), loadUser);

    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        if (result.getResultCode() == RESULT_OK)
            setupAfterSignIn();
        else if (result.getResultCode() == RESULT_CANCELED) {
            navController.navigate(R.id.action_global_sign_in, null, new NavOptions.Builder().setPopUpTo(currentDestinationId, true).build());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        NavDestination currentDestination = navController.getCurrentDestination();
        if (currentDestination != null) {
            if (currentDestination.getId() == R.id.categories_dest) {
                getMenuInflater().inflate(R.menu.categories_options_menu, menu);
            } else if (currentDestination.getId() == R.id.items_dest) {
                getMenuInflater().inflate(R.menu.items_options_menu, menu);
            } else
                return false;
            return true;
        }
        return false;
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
        dataModel.getDisplayName().observe(this, headerBinding.navUsernameText::setText);
        dataModel.getEmail().observe(this, headerBinding.navEmailText::setText);
        dataModel.getPhotoUrl().observe(this, uri -> {
            if (uri != null)
                Glide.with(this)
                        .load(uri)
                        .into(headerBinding.navUserPhoto);
            else
                Glide.with(this)
                        .load(R.drawable.user_photo_placeholder)
                        .into(headerBinding.navUserPhoto);
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.change_account) {
                changeAccount();
                return true;
            }
            return false;
        });
    }

    private void setupNavigationListeners(NavController navController) {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            currentDestinationId = destination.getId();
            invalidateOptionsMenu();
            String dest;
            try {
                dest = getResources().getResourceEntryName(currentDestinationId);
            } catch (Resources.NotFoundException e) {
                dest = "" + (currentDestinationId);
            }

            Log.d("Navigation", "Navigated to " + dest);


            if ((destination.getId() == R.id.blank_dest) || (destination.getId() == R.id.sign_in_dest)) {
                Log.d("Navigation", "Blank or sign in destination");
                binding.toolbar.setVisibility(View.GONE);
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                return;
            }

            binding.toolbar.setVisibility(View.VISIBLE);
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            if (destination.getId() == R.id.categories_dest)
                dataModel.setTitle(Objects.requireNonNull(dataModel.getStorage().getValue()).getDisplayName());

            else if (destination.getId() == R.id.items_dest)
                dataModel.setTitle(Objects.requireNonNull(dataModel.getCategory().getValue()).getDisplayName());
        });
    }

    private void changeAccount() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(
                        task -> navController.navigate(R.id.action_global_sign_in, null, new NavOptions.Builder().setPopUpTo(currentDestinationId, true).build()));
    }
}