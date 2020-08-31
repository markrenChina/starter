package com.sample.tmmi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * 开机自启
 * @author
 * @Date 2018/12/3
 */
public class AutoStartBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))){
            Intent autoStartIntent = new Intent(context, MainActivity.class);
            autoStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(autoStartIntent);
        }
    }
}
