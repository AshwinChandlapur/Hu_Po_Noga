package com.stickercamera.app.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.github.skykai.stickercamera.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.stickercamera.app.ui.mainfeed.AndroidVersion;
import com.stickercamera.app.ui.mainfeed.DataAdapter;
import com.stickercamera.app.ui.mainfeed.JSONResponse;
import com.stickercamera.app.ui.mainfeed.RequestInterface;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private ArrayList<AndroidVersion> data;
    private DataAdapter adapter;
    boolean connection;


    private String TAG = "ParentActivity";
    Fragment fragment;
    FragmentTransaction ft;
    CallbackManager cbManager;
    AccessTokenTracker accessTokenTracker;
    private InterstitialAd interstitial;
    View parentLayout;
    boolean isConnection;

    int i =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        parentLayout = findViewById(android.R.id.content);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MobileAds.initialize(this, getString(R.string.app_id));
         isConnection=haveNetworkConnection();

        if(isConnection == false)
        {
            Snackbar.make(parentLayout,"Connect To Internet for Seamless Experience.",Snackbar.LENGTH_LONG).show();
        }

        //Interstitial Ad Space
        AdRequest adRequests = new AdRequest.Builder()
                .addTestDevice("E1C583B224120C3BEF4A3DB0177A7A37").build();
        interstitial = new InterstitialAd(ParentActivity.this);
        interstitial.setAdUnitId(getString(R.string.interstitial_ad_unit));
        interstitial.loadAd(adRequests);

        //Interstitial ad space





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ParentActivity.this,MainActivity.class);
                startActivity(i);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        ImageView bgimage=(ImageView)findViewById(R.id.bgimage);
        Glide.with(this).load("https://raw.githubusercontent.com/AshwinChandlapur/Trial/master/bgbg50.jpg")
                .thumbnail(0.5f)
                .crossFade()
                .error(R.drawable.bgbg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bgimage);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        cbManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.e(TAG, "onCurrentAccessTokenChanged: ");
            }
        };
        accessTokenTracker.startTracking();
        LoginManager.getInstance().registerCallback(cbManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e(TAG, "onSuccess: " + AccessToken.getCurrentAccessToken());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "onError: " + error.getMessage());
            }
        });

        initViews();


    }
    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();


        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                i++;
                Log.d("Fliging","Flingin");
                if(i>7)
                {displayInterstitial();}
                return false;
            }
        });

    }


    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if ((interstitial.isLoaded()) && (interstitial!=null)) {
            interstitial.show();
        }
    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
            {
                if (ni.isConnected())
                {
                    haveConnectedWifi = true;
                }

            }
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                {
                    if (ni.isConnected())
                    {
                        haveConnectedMobile = true;
                    }

                }

        }
        return haveConnectedWifi || haveConnectedMobile;
    }



    private void loadJSON(){

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(getCacheDir(), cacheSize);



        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(chain -> {
                    try {
                        return chain.proceed(chain.request());
                    } catch (Exception e) {
                        Request offlineRequest = chain.request().newBuilder()
                                .header("Cache-Control", "public, only-if-cached," +
                                        "max-stale=" + 60 * 60 * 24)
                                .build();
                        return chain.proceed(offlineRequest);
                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                //    https://raw.githubusercontent.com/AshwinChandlapur/ImgLoader/gh-pages/example.json
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
                adapter = new DataAdapter(data);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }


    public void switchFragment (Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.app_bar, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cbManager.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent goHome = new Intent(Intent.ACTION_MAIN);
            goHome.addCategory(Intent.CATEGORY_HOME);
            goHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(goHome);
        }

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sticker) {
            Intent i = new Intent(ParentActivity.this,MainActivity.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_voter) {
            fragment = new VoterID();
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.app_bar, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }
        else if (id == R.id.nav_fbfeed) {
            fragment = new PostsFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.app_bar, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }
        else if(id ==R.id.nav_tickets){
            String str = "https://in.bookmyshow.com/movies/humble-politician-nograj/ET00056918/";
            Intent sendIntent = new Intent();
            sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           /// sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setData(Uri.parse(str));
            startActivity(sendIntent);



        }
        else if (id ==R.id.nav_home){
            Intent i = new Intent(getApplicationContext(),ParentActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_share) {
                    String str = "https://play.google.com/store/apps/details?id=" + getPackageName();
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "♥ Vote for Nograj! Becaz He's Humble! :D ♥\n\nDownload it Now:\n" + str);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);





        } else if (id == R.id.nav_aboutus) {
            fragment = new About();
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.app_bar, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







}


