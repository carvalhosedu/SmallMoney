package com.example.smallmomey.smallmoney;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.smallmomey.smallmoney.Adapter.HeaderAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.Console;

public class HomeActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView textView;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            setupNavigationDrawerContent(navigationView);
          
        }
    }

    public void logout () {
        Intent i = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        Integer tipo = 999 ;
        bundle.putInt("tipo", tipo);
        i.putExtras(bundle);

        startActivity(i);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationDrawerContent(NavigationView navigationView) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup parent = (ViewGroup) navigationView.getParent().getParent();
        View layoutHeader = inflater.inflate(R.layout.navigation_drawer_header, parent, false);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String user = sp.getString("user", null);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        String usuario = bundle.getString("user");
        String email = bundle.getString("email");
        Integer tipo = bundle.getInt("tipo");

        TextView txtUsuario = (TextView) layoutHeader.findViewById(R.id.usuarioTxt);
        TextView txtEmail = (TextView) layoutHeader.findViewById(R.id.emailTxt);

        if(tipo == 1 ){
            txtUsuario.setText(usuario);
            txtEmail.setText(email);
        }

        if(tipo == 0){
            txtUsuario.setText(usuario);
            txtEmail.setText("");
        }

       // navigationView.inflateHeaderView(R.layout.navigation_drawer_header);
        navigationView.addHeaderView(layoutHeader);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        textView = (TextView) findViewById(R.id.textView);
                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_inbox:
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_starred:
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_sent_mail:
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_drafts:
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_navigation_drawer_settings:
                                menuItem.setChecked(true);
                                textView.setText(menuItem.getTitle());
                                Toast.makeText(HomeActivity.this, "Launching " + menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.item_navigation_drawer_help_and_feedback:
                               ///// logout();
                                        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                return;
            }
        });

                                return true;
                        }
                        return true;
                    }
                });
    }
}
