package br.com.getmo.appsblocker;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class LookupService extends IntentService {

    private static final String ACTION_LOOKUP = "br.com.getmo.appsblocker.action.LOOKUP";

    public LookupService() {
        super("LookupService");
    }

    /**
     * Starts this service to perform action RUNNING APPS LOOKUP. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionLookup( Context context ) {
        Intent intent = new Intent( context, LookupService.class );
        intent.setAction( ACTION_LOOKUP );
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent( Intent intent ) {
        if ( intent != null ) {
            final String action = intent.getAction();
            if ( ACTION_LOOKUP.equals( action ) ) {
                handleActionLookup( );
            }
        }
    }

    /**
     * Handle action Lookup in the provided background thread with no
     * parameters.
     */
    private void handleActionLookup(  ) {
        // Get the Activity Manager
        ActivityManager manager = ( ActivityManager ) getSystemService( ACTIVITY_SERVICE );

//        // List of allowed apps
//        List<AppInfo> allowed = AppInfo.find( AppInfo.class, "IS_ALLOWED = ?", "1" );

        again = true;

        while( again ) {
            // Get a list of running tasks, we are only interested in the last one,
            // the top most so we give a 1 as parameter so we only get the topmost.
            List< ActivityManager.RunningTaskInfo > task = manager.getRunningTasks( 1 );

            // Get the info we need for comparison.
            ComponentName componentInfo = task.get( 0 ).topActivity;

            Log.d( "DEBUG", "-------------------------------------> " + componentInfo.getPackageName() );

//            // Check if it matches our package name.
//            if ( componentInfo.getPackageName().equals( PackageName ) ) {
//
//            } else {
//                // If not then our app is not on the foreground.
//            }

            sendBroadcast( new Intent( ToastReceiver.ACTION_LOOKUP_ALERT ) );
            again = false;
        }
    }

    public static boolean again;
}
