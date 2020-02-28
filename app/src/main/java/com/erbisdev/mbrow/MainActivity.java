package com.erbisdev.mbrow;
import android.Manifest;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static android.content.ContentValues.TAG;



public class MainActivity extends AppCompatActivity
implements  NavigationView.OnNavigationItemSelectedListener{
    String[] listItems={"History", "Mission And Objectives", "Principals Forewords", "School System",
            "Calender", "Rules And Regulations", "Our Traditional", "Our Alumini Council","Our Pholosophy","Our Branches"};
    boolean[] listImages= {true, true, true, true, true,true, true, true, true, true};
    WebView web1;
    EditText ed1;
    ImageButton  forward, back,  clear, stop;
    Button bt1,reload;
    String Address;
    ProgressBar pbar;
    private static boolean isTaskRunnig;
    private static ProgressDialog progressDialog;
    private static Thread thread;
   // private static int tabIndex = 0;
    //private TabHost tabHost;
   public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 0;

    private static int SPLASH_TIME_OUT = 1000;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private static ListView listView;
    private static ArrayList<BookmarkModel> arrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // init();

        if (savedInstanceState != null) {
           // web1.restoreState(savedInstanceState);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity

                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }




        setTitle(null);
        NavigationView navigationView = null;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
        else {

        }

        web1 = (WebView) findViewById(R.id.webView1);
        ed1 = (EditText) findViewById(R.id.editText1);
        bt1 = (Button) findViewById(R.id.button1);
        forward = (ImageButton) findViewById(R.id.button3);
        back = (ImageButton) findViewById(R.id.button2);
        reload = (Button) findViewById(R.id.button4);
        clear = (ImageButton) findViewById(R.id.button5);

        stop = (ImageButton) findViewById(R.id.button);
        pbar = (ProgressBar) findViewById(R.id.progressBar1);
        pbar.setVisibility(View.GONE);


        {
            web1.setBackgroundColor(0);
          // web1.loadUrl("file:///android_asset/htmls1.html");
          web1.setBackgroundResource(R.drawable.app_background);
       //    web1.setBackgroundColor(Color.TRANSPARENT);
            WebSettings webSetting = web1.getSettings();
            webSetting.setBuiltInZoomControls(true);
            web1.getSettings().setDisplayZoomControls(false);

            web1.getSettings().setSupportMultipleWindows(true);
            web1.getSettings().setPluginState(WebSettings.PluginState.ON);
            web1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webSetting.setJavaScriptEnabled(true);
            web1.getSettings().setLoadWithOverviewMode(true);
            web1.getSettings().setUseWideViewPort(true);
            web1.setWebViewClient(new WebViewClient());
            CookieManager.getInstance().setAcceptCookie(true);//Enable Cookies
            web1.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);//Set Cache (8mb)
            String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();//Set Cache (8mb)
            web1.getSettings().setAppCachePath(appCachePath);//Set Cache (8mb)
            web1.getSettings().setAllowFileAccess(true);//Set Cache (8mb)
            web1.getSettings().setAppCacheEnabled(true);//Set Cache (8mb)
            web1.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);//Set Cache (8mb)
            web1.setWebChromeClient(new GoogleClient());
        }
      //  MobileAds.initialize(this, "ca-app-pub-2533008544133019~5686371606");
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String url = ed1.getText().toString();
                if ((ed1.getText().toString().contains("http://")) || (ed1.getText().toString().contains("https://"))) {
                    url =  ed1.getText().toString();
                    web1.loadUrl(url);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(ed1.getWindowToken(), 0);
                    return;
                } else if (ed1.getText().toString().contains(".com")
                        || ed1.getText().toString().contains(".net")
                        || ed1.getText().toString().contains(".org")
                        || ed1.getText().toString().contains(".np")
                        || ed1.getText().toString().contains(".in")
                        || ed1.getText().toString().contains(".int")
                        || ed1.getText().toString().contains(".edu")
                        || ed1.getText().toString().contains(".mil")
                        || ed1.getText().toString().contains(".arpa")
                        || ed1.getText().toString().contains(".af")
                        || ed1.getText().toString().contains(".jp")
                        || ed1.getText().toString().contains(".twn")
                        || ed1.getText().toString().contains(".me")
                        || ed1.getText().toString().contains(".us")
                        || ed1.getText().toString().contains(".uk")
                        || ed1.getText().toString().contains(".th")
                        || ed1.getText().toString().contains(".sa")
                        || ed1.getText().toString().contains(".sb")
                        || ed1.getText().toString().contains(".sc")
                        || ed1.getText().toString().contains(".ru")
                        || ed1.getText().toString().contains(".qa")
                        || ed1.getText().toString().contains(".qa")
                        || ed1.getText().toString().contains(".np")
                        || ed1.getText().toString().contains(".ac.in")
                        || ed1.getText().toString().contains(".gov")) {
                    web1.loadUrl("https://"
                            + url);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(ed1.getWindowToken(), 0);
                    return;

                } else {
                    web1.loadUrl("https://www.google.com/search?q="
                            + url);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(ed1.getWindowToken(), 0);
                }


            }


        });

        ed1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (motionEvent.getX()>(view.getWidth()-view.getPaddingRight())){
                        ((EditText)view).setText("");
                    }
                }
                return false;
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (web1.canGoForward())
                    web1.goForward();


            }
        });
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (web1.canGoBack())
                    web1.goBack();


            }
        });
        reload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                web1.reload();


            }
        });
        clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                web1.clearHistory();

            }
        });
        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                web1.stopLoading();

            }
        });
        //   web1.setOnTouchListener(new OnSwipeTouchListener(this));
        web1.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        final DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        // This is where downloaded files will be written, using the package name isn't required
        // but it's a good way to communicate who owns the directory
        final File destinationDir = new File(Environment.getExternalStorageDirectory(), getPackageName());
        if (!destinationDir.exists()) {
            destinationDir.mkdir(); // Don't forget to make the directory if it's not there
        }

        web1.setDownloadListener(new DownloadListener() {

            @Override


            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimeType,
                                        long contentLength) {

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));


                request.setMimeType(mimeType);


                String cookies = CookieManager.getInstance().getCookie(url);


                request.addRequestHeader("cookie", cookies);


                request.addRequestHeader("User-Agent", userAgent);


                request.setDescription("Downloading file...");


                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));


                request.allowScanningByMediaScanner();


                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                DownloadManager.Request request1 = request.setDestinationInExternalPublicDir( "/Nidhi", URLUtil.guessFileName(url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Check At /Nidhi Folder", Toast.LENGTH_LONG).show();
            }
        });
    }

         final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");
        final String[] HISTORY_PROJECTION = new String[]{
                "_id", // 0
                "url", // 1
                "visits", // 2
                "date", // 3
                "bookmark", // 4
                "title", // 5
                "favicon", // 6
                "thumbnail", // 7
                "touch_icon", // 8
                "user_entered", // 9
        };
        final int HISTORY_PROJECTION_TITLE_INDEX = 5;
        final int HISTORY_PROJECTION_URL_INDEX = 1;




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(getApplicationContext(), "Portrait mode", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getApplicationContext(), "Landscape mode", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
       super.onRestoreInstanceState(savedInstanceState);
        web1.restoreState(savedInstanceState);

    }

   @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
web1.saveState(outState);
    }


    private boolean checkAndRequestPermissions() {
        int permissionRead = 0;

            permissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        int permissionInternet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionRecordAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionRead != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        }
        if (permissionInternet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();

                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "Permission granted");
                        // process the normal flow
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                            showDialogOK("Service Permissions are required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            //     explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?");
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void explain(String msg) {
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        //  permissionsclass.requestPermission(type,code);
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:com.erbisdev.mbrow")));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are You Sure You Want To Exit?");
        builder.setCancelable(true);
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
    //    final Switch simpleSwitch = (Switch)  findViewById(R.id.ss);

        if (id == R.id.nav_forward) {
            // Handle the camera action
            if (web1.canGoForward())
                web1.goForward();
        }  if (id == R.id.nav_backward) {
            // Handle the camera action
            if (web1.canGoBack())
                web1.goBack();
        }
        if (id == R.id.nav_send) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = currenturl;
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Choose Link Send Method:"));
        }
        if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "This is my fav browsing app #Nidhi - THe Browser , try it, it's COOL";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Choose App Sharing Method:"));
        }
        if (id == R.id.nav_about) {
            Intent intent = new Intent(getApplicationContext(),Activity2.class);
startActivity(intent);


            }
        if (id == R.id.nav_fb) {
            Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
            startActivity(intent);


        }
        else {
         //   if (id == R.id.DJS) {



        //   }
       }

   //    else if (id == R.id.nav_default) {


  //             Intent intent = new Intent(Intent.ACTION_MAIN);
   //            intent.addCategory(Intent.CATEGORY_APP_BROWSER);

   //            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

    //           startActivity(intent);



      // }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }


    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }


    private boolean isMyAppLauncherDefault()
    {
        final IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_APP_BROWSER );
        filter.addCategory(Intent.CATEGORY_BROWSABLE );
        List<IntentFilter> filters = new ArrayList<IntentFilter>();
        filters.add(filter);

        final String myPackageName = getPackageName();
        List<ComponentName> activities = new ArrayList<>();
        final PackageManager packageManager = getPackageManager();

        packageManager.getPreferredActivities(filters, activities, null);

        for (ComponentName activity : activities) {
            if (myPackageName.equals(activity.getPackageName())) {
                Toast.makeText(getApplicationContext(), "Already set as Default",
                        Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }


    private String currenturl;

    public class WebViewClient extends android.webkit.WebViewClient {


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            pbar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Log.d("WEB_VIEW_TEST", "error code:" + errorCode + " - " + description);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {


            // TODO Auto-generated method stub

            if (url.startsWith("intent://")) {
                try {
                    Context context = view.getContext();
                    Intent intent = new Intent().parseUri(url, Intent.URI_INTENT_SCHEME);

                    if (intent != null) {
                        view.stopLoading();

                        PackageManager packageManager = context.getPackageManager();
                        ResolveInfo info = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
                        if (info != null) {
                            context.startActivity(intent);
                        } else {
                            String fallbackUrl = intent.getStringExtra("browser_fallback_url");
                            view.loadUrl(fallbackUrl);

                            // or call external broswer
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fallbackUrl));
//                    context.startActivity(browserIntent);
                        }

                        return true;
                    }
                } catch (URISyntaxException e) {

                    Log.e(TAG, "Can't resolve intent://", e);
                }

            }

            return false;

        }

        @Override
        public void onPageFinished(WebView view, String url) {

            // TODO Auto-generated method stub

            super.onPageFinished(view, url);
            ed1.setText(url, TextView.BufferType.NORMAL);
            pbar.setVisibility(View.GONE);
            currenturl = url;
           MainActivity.this.setTitle(view.getTitle());
        }

    }

    public class GoogleClient extends WebChromeClient {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title)) {
                    MainActivity.this.setTitle(title);
                }
            }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web1.canGoBack()) {
            web1.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





}