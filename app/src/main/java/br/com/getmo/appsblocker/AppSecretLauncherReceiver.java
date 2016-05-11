package br.com.getmo.appsblocker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by fabiol on 11/05/2016.
 */
public class AppSecretLauncherReceiver extends BroadcastReceiver {

    @Override
    public void onReceive( Context context, Intent intent ) {
        String action = intent.getAction();
        if ( Intent.ACTION_NEW_OUTGOING_CALL.equals( action ) ) {
            final String secret = intent.getStringExtra( Intent.EXTRA_PHONE_NUMBER );
            String pass = "#8743#";
            if ( secret.equals( pass ) ) {
                abortBroadcast();
                setResultData( null );
                context.startActivity(
                        new Intent( context, MainActivity.class ).setFlags( Intent.FLAG_ACTIVITY_NEW_TASK ) );
            }
        }
    }
}
