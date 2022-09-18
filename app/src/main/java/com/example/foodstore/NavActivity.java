package com.example.foodstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.foodstore.fragments.AboutFragment;
import com.example.foodstore.fragments.CartFragment;
import com.example.foodstore.fragments.HistoryFragment;
import com.example.foodstore.fragments.MenuFragment;
import com.example.foodstore.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class NavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(navigationItemSelectedListener);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MenuFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_menu);
            bottomNavigationView.setSelectedItemId(R.id.tab_menu);
        }
    }

    private final BottomNavigationView.OnItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.tab_menu:
                            selectedFragment = new MenuFragment();
                            navigationView.setCheckedItem(R.id.nav_menu);
                            toolbar.setTitle("Menu");
                            break;
                        case R.id.tab_profile:
                            selectedFragment = new ProfileFragment();
                            navigationView.setCheckedItem(R.id.nav_profile);
                            toolbar.setTitle("Profile");
                            break;
                        case R.id.tab_cart:
                            selectedFragment = new CartFragment();
                            navigationView.setCheckedItem(R.id.nav_cart);
                            toolbar.setTitle("Cart");
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MenuFragment()).commit();
                bottomNavigationView.setSelectedItemId(R.id.tab_menu);
                toolbar.setTitle("Menu");
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                bottomNavigationView.setSelectedItemId(R.id.tab_profile);
                toolbar.setTitle("Profile");
                break;
            case R.id.nav_cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CartFragment()).commit();
                bottomNavigationView.setSelectedItemId(R.id.tab_cart);
                toolbar.setTitle("Cart");
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HistoryFragment()).commit();
                toolbar.setTitle("History");
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutFragment()).commit();
                toolbar.setTitle("About");
                break;
            case R.id.nav_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.nav_support:
                Intent intent1 = new Intent(this, SupportNavActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.nav_website:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://app.uizard.io/p/3ecc9bd6"));
                startActivity(browserIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialogInterface, i) -> finish())
                    .setNegativeButton("No", null)
                    .show();
        }
    }
}