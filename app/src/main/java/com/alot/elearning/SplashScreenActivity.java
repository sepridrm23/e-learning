package com.alot.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by afi on 04/05/18.
 */

public class SplashScreenActivity extends AppCompatActivity{
    String newVersion;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SplashScreenActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        if (Utilities.isLogin(SplashScreenActivity.this)) {
                            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }, 1500);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        new GetVersionCode().execute();
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == 4){
//            Snackbar.make(findViewById(android.R.id.content), "Loading...",
//                    Snackbar.LENGTH_SHORT).show();
//        }
//        return true;
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    private class GetVersionCode extends AsyncTask<Void, String, String> {
//        @Override
//        protected String doInBackground(Void... voids) {
//
//            try {
////                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id="+SplashScreenActivity.this.getPackageName()+"&hl=en")
////                        .timeout(5000)
////                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
////                        .referrer("http://www.google.com")
////                        .get()
////                        .select("div[itemprop=softwareVersion]")
////                        .first()
////                        .ownText();
//
//                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + SplashScreenActivity.this.getPackageName()+ "&hl=en")
//                        .timeout(30000)
//                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                        .referrer("http://www.google.com")
//                        .get()
//                        .select("div.hAyfc:nth-child(3) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
//                        .first()
//                        .ownText();
//
////                Log.e("latestversion","---"+newVersion);
//
//                return newVersion;
//            } catch (Exception e) {
//                Log.e("error version", ""+e.toString());
//                return newVersion;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String onlineVersion) {
//            super.onPostExecute(onlineVersion);
//            PackageManager packageManager = SplashScreenActivity.this.getPackageManager();
//            String packageName = SplashScreenActivity.this.getPackageName();
//            String myVersionCode = "null";
//            try {
//                myVersionCode = String.valueOf(packageManager.getPackageInfo(packageName, 0).versionName);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            Log.e("version", myVersionCode+" "+onlineVersion);
//
//            if (!myVersionCode.equalsIgnoreCase(onlineVersion)) {
//                //show dialog
//                new AlertDialog.Builder(SplashScreenActivity.this)
//                        .setTitle("Update aplikasi tersedia")
//                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // continue with delete
//                                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                                try {
////                                    Toast.makeText(getApplicationContext(), "App is in BETA version cannot update", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                                } catch (ActivityNotFoundException anfe) {
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+appPackageName)));
//                                }
//                            }
//                        })
//                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // do nothing
//                                dialog.dismiss();
//                                Intent myIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                                startActivity(myIntent);
//                                finish();
////                                onBackPressed();
//                            }
//                        })
//                        .setCancelable(false)
//                        .setIcon(R.drawable.ic_action_info)
//                        .show();
//
//            }else {
//                Intent myIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                startActivity(myIntent);
//                finish();
//            }
//        }
//    }

}