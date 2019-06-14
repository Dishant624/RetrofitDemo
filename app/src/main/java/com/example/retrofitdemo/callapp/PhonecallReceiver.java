package com.example.retrofitdemo.callapp;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.util.Date;

public class PhonecallReceiver extends BroadcastReceiver {

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;
    public final String TAG = "PhonecallReceiver";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        //We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            Log.d(TAG, "onReceive: 1");
            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
        } else {
            Log.d(TAG, "onReceive: 2");
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                Log.d(TAG, "onReceive: 2.1");
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                Log.d(TAG, "onReceive: 2.2");
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                Log.d(TAG, "onReceive: 2.3");
                state = TelephonyManager.CALL_STATE_RINGING;
            }


            onCallStateChanged(context, state, number);
        }
    }

    //Derived classes should override these to respond to specific events of interest
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
    }

    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {

    }

    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
//
        CallNumbers callNumbers = CallNumbers.getInstance();


        int index =callNumbers.getIndex();
        if(index ==10){
            return;
        }
        index++;
        callNumbers.setIndex(index);



//
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + callNumbers.getNumberlist().get(callNumbers.getIndex())));
        if(index == 5){
            index =1;
            callNumbers.setIndex(index);
        }
        if (intent.resolveActivity(ctx.getPackageManager()) != null) {
            if (ctx.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                ActivityCompat.requestPermissions((Activity) ctx,
                        new String[]{Manifest.permission.CALL_PHONE},
                        PhoneNumberRegister.MY_PERMISSIONS_REQUEST_Call);
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            ctx.startActivity(intent);
        }
    }

    protected void onMissedCall(Context ctx, String number, Date start) {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onCallStateChanged(Context context, int state, String number) {
        Log.d(TAG, "onCallStateChanged: 2.4");
        if (lastState == state) {
            Log.d(TAG, "onCallStateChanged: 2.44");
            //No change, debounce extras
            //return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG, "onCallStateChanged: ringing");
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                onIncomingCallStarted(context, number, callStartTime);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(TAG, "onCallStateChanged: offhook");
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false;
                    callStartTime = new Date();
                    onOutgoingCallStarted(context, savedNumber, callStartTime);
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(TAG, "onCallStateChanged: idle");
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    //Ring but no pickup-  a miss
                    onMissedCall(context, savedNumber, callStartTime);
                } else if (isIncoming) {
                    Log.d(TAG, "onReceive: 2.5");
                    onIncomingCallEnded(context, savedNumber, callStartTime, new Date());
                } else {
                    Log.d(TAG, "onReceive: 2.6");
                    onOutgoingCallEnded(context, savedNumber, callStartTime, new Date());
                }
                break;
        }
    }
}
