package br.com.getmo.appsblocker;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;
import com.jaredrummler.android.processes.models.Stat;
import com.orm.util.NamingHelper;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 * @see <a href="https://github.com/jaredrummler/AndroidProcesses">AndroidProcesses</a>
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

        String nameOfLauncherPkg = AppsUtils.getDefaultHomeAppPackage( this );

        AppInfo current = new AppInfo();
        String     temp = "";

        // Get the Activity Manager
        ActivityManager manager = ( ActivityManager ) getSystemService( ACTIVITY_SERVICE );

        while( !stop ) {
            // Get a list of running tasks

            List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
            for ( AndroidAppProcess pInfo : processes ) {
                if ( pInfo.foreground ) {
                    try {
                        PackageInfo packageInfo = pInfo.getPackageInfo( this, 0 );
                        ApplicationInfo a = packageInfo.applicationInfo;

                        // skip system apps if they shall not be included
                        if( ( a.flags & ApplicationInfo.FLAG_SYSTEM ) == 1 ) {
                            continue;
                        }

                        current.appPackage = pInfo.name;
                        current.appName    = packageInfo.applicationInfo.loadLabel( getPackageManager() ).toString();

                        if ( !temp.equals( current.appPackage ) ) {
                            temp = current.appPackage;
                            Log.d( "DEBUG", "--------------- : { foreground : "
                                    + pInfo.foreground + ", pid : "
                                    + pInfo.stat().getPid() + ", name : "
                                    + current.appPackage + ", package : " + current.appName + "}" );

                            if ( !allowed.contains( current ) ) {
                                if ( !( "br.com.getmo.appsblocker".equals( current.appPackage )
                                        || nameOfLauncherPkg.equals( current.appPackage ) ) ) {
                                    Intent it = new Intent( ToastReceiver.ACTION_LOOKUP_ALERT );
                                    it.putExtra( AppInfo.APP_NAME, current.appName );
                                    sendBroadcast( it );
                                }
                            }

                            break;
                        }
                    } catch ( Exception e ) {

                    }
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
