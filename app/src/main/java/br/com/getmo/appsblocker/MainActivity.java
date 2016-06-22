package br.com.getmo.appsblocker;


import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
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

    @Override
    protected void onStart() {
        super.onStart();
        LookupReceiver.stop( this );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LookupReceiver.start( this );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.unlock_settings:
                // http://stackoverflow.com/questions/15802352/how-to-show-clear-defaults-programmatically
                // http://stackoverflow.com/questions/11171721/how-to-call-the-usb-tethering-intent-in-android-4-0-and-3-0
                getPackageManager().clearPackagePreferredActivities( getPackageName() );
                Toast.makeText( this, "Configurações desbloqueadas!", Toast.LENGTH_SHORT ).show();
                return true;
            case R.id.lock_settings:
                // http://stackoverflow.com/questions/15802352/how-to-show-clear-defaults-programmatically
                // http://stackoverflow.com/questions/11171721/how-to-call-the-usb-tethering-intent-in-android-4-0-and-3-0
//                getPackageManager().clearPackagePreferredActivities( getPackageName() );
                Toast.makeText( this, "Configurações desbloqueadas!", Toast.LENGTH_SHORT ).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void hideApp( ) {
//        // http://stackoverflow.com/questions/8134884/android-how-to-programmatically-hide-launcher-icon
//        PackageManager p = getPackageManager();
//        ComponentName componentName = new ComponentName( this, MainActivity.class ); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
//        p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
//    }
}
