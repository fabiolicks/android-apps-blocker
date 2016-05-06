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
import java.util.List;

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

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if ( findViewById(R.id.fragment_container ) != null ) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if ( savedInstanceState != null ) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            AppsListFragment firstFragment = new AppsListFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
//            firstFragment.setArguments( getIntent().getExtras() );

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager()
                    .beginTransaction()
                    .add( R.id.fragment_container, firstFragment )
                    .commit();
        }
    }
}
