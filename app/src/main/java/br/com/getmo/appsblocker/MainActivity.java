package br.com.getmo.appsblocker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    /*
    Intent startMain = new Intent(Intent.ACTION_MAIN);
    startMain.addCategory(Intent.CATEGORY_HOME);
    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(startMain);
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                PackageManager packageManager = MainActivity.this.getPackageManager();
                ArrayList<ApplicationInfo> list = AppsUtils.getInstalledApps( MainActivity.this );
                if ( list != null ) {
                    Iterator<ApplicationInfo> it = list.iterator();
                    while( it.hasNext() ) {
                        ApplicationInfo app = it.next();
                        Log.d( "APPS", "--> " + (String) app.loadLabel( packageManager ));
                    }
                }

                return null;
            }
        }.execute();
    }
}
