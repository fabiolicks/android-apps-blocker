package br.com.getmo.appsblocker;

import android.graphics.drawable.Drawable;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.Objects;

/**
 * Created by fabio.licks on 04/05/16.
 */
public class AppInfo extends SugarRecord implements Comparable<AppInfo> {

    public String appPackage;

    public boolean isAllowed;

    public String appMainActivity;

    public String appName;

    @Ignore
    public Drawable appIcon;

    @Ignore
    public static final String APP_NAME = "br.com.getmo.appsblocker.field.APP_NAME";

    public AppInfo( ) {
        this.appPackage = "";
    }

    @Override
    public String toString() {
        return "{appPackage='" + appPackage + '\'' +
                ", appMainActivity='" + appMainActivity + '\'' +
                ", appName='" + appName + '\'' +
                '}';
    }

    @Override
    public int compareTo( AppInfo another ) {
        // ascending order (descending order would be: name2.compareTo(name1))
        return appName.compareTo( another.appName );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppInfo appInfo = (AppInfo) o;
        return Objects.equals(appPackage, appInfo.appPackage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appPackage);
    }
}
