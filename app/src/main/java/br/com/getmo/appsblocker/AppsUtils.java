package br.com.getmo.appsblocker;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabio.licks on 04/05/16.
 */
public class AppsUtils {

    public static final String TAG = "appsblocker";

    public static ArrayList<AppInfo> getApps( PackageManager manager ) {
        ArrayList<AppInfo> apps = new ArrayList<>();

        Intent it = new Intent( Intent.ACTION_MAIN, null );
        it.addCategory( Intent.CATEGORY_LAUNCHER );

        List<ResolveInfo> availableActivities = manager.queryIntentActivities( it, 0 );
        for( ResolveInfo ri : availableActivities ){

            // skip system apps if they shall not be included
            ApplicationInfo a = ri.activityInfo.applicationInfo;
            if( ( a.flags & ApplicationInfo.FLAG_SYSTEM ) == 1 ) {
                continue;
            }

            AppInfo app = new AppInfo();
            app.appName = ( String ) ri.loadLabel( manager );
            app.appPackage = ri.activityInfo.packageName;
            app.appIcon = ri.activityInfo.loadIcon( manager );
            app.appMainActivity = ri.activityInfo.name;
            apps.add( app );

            Log.d( TAG, app.toString() );
        }

        return apps;
    }

    public static String getDefaultHomeAppPackage( Context c ) {
        Intent intent = new Intent( Intent.ACTION_MAIN );
        intent.addCategory( Intent.CATEGORY_HOME );
        ResolveInfo defaultLauncher =
                c.getPackageManager().resolveActivity( intent, PackageManager.MATCH_DEFAULT_ONLY );
        return defaultLauncher.activityInfo.packageName;
    }

}
