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
        for( ResolveInfo ri:availableActivities ){
            AppInfo app = new AppInfo();
            app.appName = (String) ri.loadLabel( manager );
            app.appId   = ri.activityInfo.  packageName;
            app.appIcon = ri.activityInfo.loadIcon( manager );
            apps.add( app );

            Log.d( TAG, app.toString() );
        }

        return apps;
    }

//    public static ArrayList<ApplicationInfo> getInstalledApps( Context _c ) {
//        PackageManager packageManager = _c.getPackageManager();
//        List<ApplicationInfo> list =
//                checkForLaunchIntent(
//                        packageManager, packageManager.getInstalledApplications(
//                                PackageManager.GET_META_DATA ) );
//
//        ArrayList<ApplicationInfo> out = new ArrayList<>();
//        for ( ApplicationInfo info : list ) {
////            if ( ( info.flags & ApplicationInfo.FLAG_SYSTEM ) == 1 ) {
////                // System application
////                continue;
////            } else {
////                // Installed by user
//////			info.loadLabel(packageManager)
//////			info.packageName;
//////			info.loadIcon(packageManager)
//                out.add( info );
////            }
//        }
//
//        // return ( new JSONArray( out ) ).toString();
//        return out;
//    }
//
//    private static List<ApplicationInfo> checkForLaunchIntent(PackageManager packageManager, List<ApplicationInfo> list) {
//        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
//        for (ApplicationInfo info : list) {
//            try {
//                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
//                    applist.add(info);
//                }
//            } catch (Exception e) {
//                Log.e(TAG, e.getMessage(), e);
//            }
//        }
//
//        return applist;
//    }
}
