package com.sorbellini.s214631.lab3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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

public class ActivityMain extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        FragmentShowRestaurants.FragmentListener,
        FragmentShowOffers.FragmentListener,
        FragmentShowReservations.FragmentListener{

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    //drawer stuff
    String[] mDrawerListItems;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    int mDrawerPosition;
    boolean mDrawerClick;
    //spinner
    Spinner spinner;
    //used to pre
    int current_fragment=0;
    int spinner_position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to know after a screen rotation what was current fragment in view
        if(savedInstanceState != null){
            current_fragment = savedInstanceState.getInt("current_fragment");
            spinner_position = savedInstanceState.getInt("spinner_position");
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_spinner);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        /**
         * Set Api for location
         */

        if (mGoogleApiClient == null) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }

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
                mDrawerPosition = position;
                mDrawerClick = true;
                mDrawerLayout.closeDrawer(mDrawerList);
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
                if(mDrawerClick) {
                    displayView(mDrawerPosition);
                    mDrawerClick = false;
                }
            }

            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
                invalidateOptionsMenu();
                syncState();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        /**
         * Spinner (the switch on the toolbar)
         *
         * */
        spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_options, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    //the fragment that contains the offers
                    FragmentShowOffers fo;
                    switch (position) {
                        case 0:
                            //offers ordered by distance
                            //fo = (FragmentShowOffers) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                            fo = (FragmentShowOffers) getSupportFragmentManager().findFragmentByTag("OFFERS");
                            if(fo != null) {
                                //fo.updateDistance();
                                fo.sortByDistance();
                            }
                            spinner_position = 0;
                            break;
                        case 1:
                            //offers ordered by price
                            //fo = (FragmentShowOffers) getSupportFragmentManager().findFragmentById(R.id.frame_container);
                            fo = (FragmentShowOffers) getSupportFragmentManager().findFragmentByTag("OFFERS");
                            if(fo != null) {
                                //fo.updateDistance();
                                fo.sortByPrice();
                            }
                            spinner_position = 1;
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    FragmentShowOffers fo;
                    fo = (FragmentShowOffers) getSupportFragmentManager().findFragmentByTag("OFFERS");
                    if(fo != null) {
                        //fo.updateDistance();
                        fo.sortByDistance();
                    }
                    spinner_position = 0;
                }

            });
        }

        /**
         * populate view according to current fragment
         */
        switch (current_fragment) {
            //activity has been just created
            case 0:
                getSupportActionBar().setTitle("Offers");
                FragmentShowOffers fragment = new FragmentShowOffers();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment, "OFFERS").commit();
                break;
            //offers fragment is the one in the view
            case 1:
                spinner.setVisibility(View.VISIBLE);
                getSupportActionBar().setTitle("Offers");
                break;
            //restaurants fragment is the one in the view
            case 2:
                spinner.setVisibility(View.GONE);
                getSupportActionBar().setTitle("Restaurants");
                break;
            //reservations fragment is the one in the view
            case 3:
                spinner.setVisibility(View.GONE);
                getSupportActionBar().setTitle("Reservations");
                break;
            //Profile fragment is the one in the view
            case 4:
                spinner.setVisibility(View.GONE);
                getSupportActionBar().setTitle("Profile");
                break;
        }
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
                //mDrawerLayout.closeDrawer(mDrawerList);
                spinner.setVisibility(View.VISIBLE);
                mDrawerList.setSelection(0);
                getSupportActionBar().setTitle("Offers");
                //getSupportActionBar().setDisplayShowTitleEnabled(true);
                fragment = new FragmentShowOffers();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment, "OFFERS").commit();
                current_fragment = 1;
                break;
            case 1:
                //mDrawerLayout.closeDrawer(mDrawerList);
                spinner.setVisibility(View.GONE);
                mDrawerList.setSelection(1);
                getSupportActionBar().setTitle("Restaurants");
                //getSupportActionBar().setDisplayShowTitleEnabled(true);
                fragment = new FragmentShowRestaurants();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment, "RESTAURANTS").commit();
                current_fragment = 2;
                break;

            case 2:
                spinner.setVisibility(View.GONE);
                mDrawerList.setSelection(2);
                getSupportActionBar().setTitle("Reservations");
                fragment = new FragmentShowReservations();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, fragment, "RESERVATIONS").commit();
                current_fragment = 3;
                break;
            case 3:
                spinner.setVisibility(View.GONE);
                mDrawerList.setSelection(3);
                getSupportActionBar().setTitle("Profile");
                startActivity(new Intent(ActivityMain.this, CustomerProfile.class));
                current_fragment = 4;
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
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false);
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
        FragmentShowOffers fo = (FragmentShowOffers)getSupportFragmentManager().findFragmentByTag("OFFERS");
        if(fo!=null){
            fo.updateDistance();
            switch (spinner_position) {
                case 0:
                    fo.sortByDistance();
                    break;
                case 1:
                    break;
            }
        }
        FragmentShowRestaurants fr = (FragmentShowRestaurants) getSupportFragmentManager().findFragmentByTag("RESTAURANTS");
        if(fr!=null){
            fr.updateDistance();
            fr.sortByDistance();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("current_fragment", current_fragment);
        outState.putInt("spinner_position", spinner_position);
        super.onSaveInstanceState(outState);
    }

    //this get method is used by fragments inside this activity
    public Location getLocation(){
        return this.mLastLocation;
    }
    public int getSpinnerPosition() {return this.spinner_position;}

}
