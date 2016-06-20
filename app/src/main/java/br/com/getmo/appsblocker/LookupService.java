package br.com.getmo.appsblocker;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;

import com.orm.util.NamingHelper;

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
        stopActionLookup();

        Intent intent = new Intent( context, LookupService.class );
        intent.setAction( ACTION_LOOKUP );
        context.startService( intent );
    }

    public static void stopActionLookup( ) {
        stop = true;
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
        stop = false;

        // List of allowed apps
        List<AppInfo> allowed =
                AppInfo.find( AppInfo.class, NamingHelper.toSQLNameDefault( "isAllowed" ) + " = ?", "1" );
        Log.d( "DEBUG", "---------------> " + allowed.size() );

        //
        Intent intent = new Intent( Intent.ACTION_MAIN );
        intent.addCategory( Intent.CATEGORY_HOME );
        ResolveInfo defaultLauncher = getPackageManager().resolveActivity( intent, PackageManager.MATCH_DEFAULT_ONLY );
        String nameOfLauncherPkg = defaultLauncher.activityInfo.packageName;

        AppInfo current = new AppInfo();
        current.appPackage = "";
        String temp = "";

        // Get the Activity Manager
        ActivityManager manager = ( ActivityManager ) getSystemService( ACTIVITY_SERVICE );
        while( !stop ) {

            // Get a list of running tasks, we are only interested in the last one,
            // the top most so we give a 1 as parameter so we only get the topmost.
            if ( android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                List<ActivityManager.RunningAppProcessInfo> runningTasks = manager.getRunningAppProcesses();
                if ( runningTasks != null && runningTasks.size() > 0 )
                    current.appPackage = runningTasks.get( 0 ).processName;
            } else {
                List<ActivityManager.RunningTaskInfo> runningTasksOld = manager.getRunningTasks(1);
                if ( runningTasksOld != null && runningTasksOld.size() > 0 )
                    current.appPackage = runningTasksOld.get(0).topActivity.getPackageName();
            }

            if ( !temp.equals( current.appPackage ) ) {
                temp = current.appPackage;
                Log.d( "DEBUG", "---------------> " + current.appPackage );
            }

            if ( !allowed.contains( current ) ) {
                if ( !( "br.com.getmo.appsblocker".equals( current.appPackage ) || nameOfLauncherPkg.equals( current.appPackage ) ) ) {
                    Intent it = new Intent( ToastReceiver.ACTION_LOOKUP_ALERT );
//                    it.putExtra( AppInfo.APP_NAME, componentInfo. )
                    sendBroadcast( it );
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d( "DEBUG", "service destroyed" );
    }

    public static boolean stop;
}
