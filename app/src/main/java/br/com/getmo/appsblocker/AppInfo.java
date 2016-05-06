package br.com.getmo.appsblocker;

import android.graphics.drawable.Drawable;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by fabio.licks on 04/05/16.
 */
public class AppInfo extends SugarRecord implements Comparable<AppInfo> {

    public String appId;

    public boolean isAllowed;

    @Ignore
    public String appName;

    @Ignore
    public Drawable appIcon;

    @Override
    public String toString() {
        return "{ appId='" + appId + "\', appName='" + appName + "\'}";
    }

    @Override
    public int compareTo( AppInfo another ) {
        // ascending order (descending order would be: name2.compareTo(name1))
        return appName.compareTo( another.appName );
    }
}
