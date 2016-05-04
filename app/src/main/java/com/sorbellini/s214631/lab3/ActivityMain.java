package com.sorbellini.s214631.lab3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityMain extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        FragmentShowRestaurants.FragmentListener,
        FragmentShowOffers.FragmentListener{

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    //drawer stuff
    String[] mDrawerListItems;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    //spinner
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_spinner);
        /**
         * Drawer (The side menu)
         */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerListItems = getResources().getStringArray(R.array.drawer_options);
        //set drawer adapter
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerListItems));
        //set listener
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
                invalidateOptionsMenu();
                syncState();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mDrawerToggle.syncState();

        /**
         * Spinner (the switch on the toolbar)
         */
        spinner = (Spinner) findViewById(R.id.spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    //the fragment that contains the offers
                    FragmentShowOffers fo;
                    switch (position) {
                        case 0:
                            //offers ordered by distance
                            fo = (FragmentShowOffers) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                            if(fo != null)
                                fo.sortByDistance();
                            break;
                        case 1:
                            //offers ordered by price
                            fo = (FragmentShowOffers) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                            if(fo != null)
                                fo.sortByPrice();
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    //
                }

            });
        }

        /**
         * Set Api for location and place Offers fragment
         */

        if (mGoogleApiClient == null) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        //Offers take the place of frame container
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Fragment fragment = new FragmentShowOffers();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment).commit();

    }

    /**
     * Drawer Functions
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    //when screen rotates drawer keeps it's state
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Switch fragments inside the frame container
     *
     */

    public void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                mDrawerLayout.closeDrawer(mDrawerList);
                spinner.setVisibility(View.VISIBLE);
                mDrawerList.setSelection(0);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                fragment = new FragmentShowOffers();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                break;
            case 1:
                mDrawerLayout.closeDrawer(mDrawerList);
                mDrawerList.setSelection(1);
                getSupportActionBar().setTitle("Restaurants");
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                fragment = new FragmentShowRestaurants();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                break;
            case 2:
                // TODO
                break;
            case 3:
                // TODO
                break;
        }
    }

    /**
     * Menu items management
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //drawer action button
            case android.R.id.home: {
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /**
     * here location methods part
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            }
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        //if a fragment with offers has already been created updates the view
        FragmentShowOffers fo = (FragmentShowOffers)getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if(fo!=null){
            fo.updateDistance();
            fo.sortByDistance();
        }

    }

    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.d("TAG", "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
        Toast.makeText(getApplicationContext(),
                "This device is not supported.", Toast.LENGTH_LONG)
                .show();
        finish();
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    //this get method is used by fragments inside this activity
    public Location getLocation(){
        return this.mLastLocation;
    }

}