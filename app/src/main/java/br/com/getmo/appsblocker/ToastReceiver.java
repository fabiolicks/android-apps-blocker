package br.com.getmo.appsblocker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by fabiol on 09/05/2016.
 */
public class ToastReceiver extends BroadcastReceiver {

    public static final String ACTION_LOOKUP_ALERT = "br.com.getmo.appsblocker.action.LOOKUP_ALERT";

    @Override
    public void onReceive( Context context, Intent intent ) {
        String action = intent.getAction();
        if ( ACTION_LOOKUP_ALERT.equals( action ) ) {
            String appName = intent.getStringExtra( AppInfo.APP_NAME );
            Toast.makeText(
                    context,
                    String.format( context.getString( R.string.alert_denied ), appName ), Toast.LENGTH_SHORT ).show();

            // Start Home Screen
            context.startActivity(
                    new Intent( Intent.ACTION_MAIN )
                            .addCategory( Intent.CATEGORY_HOME )
                            .setFlags( Intent.FLAG_ACTIVITY_NEW_TASK ) );
        }
    }
}
