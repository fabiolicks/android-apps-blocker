package br.com.getmo.appsblocker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by fabio.licks on 21/06/16.
 */

public class LookupReceiver extends BroadcastReceiver {

    public static String ACTION_RERUN = "br.com.getmo.appsblocker.action.LOOKUP_RERUN";

    @Override
    public void onReceive( Context context, Intent intent ) {
        String action = intent.getAction();

        if ( Intent.ACTION_BOOT_COMPLETED.equals( action ) ) {
            LookupService.startActionLookup( context );
        }

        if ( ACTION_RERUN.equals( action ) ) {
            if ( intent.getBooleanExtra( "STOP", false ) ) {
                Log.d( "DEBUG", "STOPPING SERVICE!" );
                LookupService.stopActionLookup( );
                return;
            } else {
                if ( !AppsUtils.isServiceRunning( context ) ) {
                    Log.d( "DEBUG", "RERUN SERVICE!" );
                    LookupService.startActionLookup( context );
                } else {
                    Log.d( "DEBUG", "SERVICE IS RUNNING - ALL OK!" );
                }
            }
        }

        schedule( context );
    }

    private static void schedule( Context c ) {
        Calendar now = Calendar.getInstance();
        now.add( Calendar.MINUTE, 1 );

        ( ( AlarmManager ) c.getSystemService( Context.ALARM_SERVICE ) )
                .set( AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), configPendingIntent( c ) );
    }

    private static void stopSchedule( Context c ) {
        ( ( AlarmManager ) c.getSystemService( Context.ALARM_SERVICE ) )
                .cancel( configPendingIntent( c ) );
    }

    private static PendingIntent configPendingIntent( Context c ) {
        PendingIntent p =
                PendingIntent
                        .getBroadcast( c, 0,
                                new Intent( "br.com.getmo.appsblocker.action.LOOKUP_RERUN" ), 0 );
        return p;
    }

    public static void start( Context c ) {
        c.sendBroadcast( new Intent( ACTION_RERUN ) );
    }

    public static void stop( Context c ) {
        stopSchedule( c );
        c.sendBroadcast( new Intent( ACTION_RERUN ).putExtra( "STOP", true ) );
    }
}
