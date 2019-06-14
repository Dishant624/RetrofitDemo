package com.example.retrofitdemo.callapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telecom.Call;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.util.Date;

public class CallReceiver extends PhonecallReceiver {

    public static final String TAG = "CallReceiver";

    @Override
    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        super.onIncomingCallStarted(ctx, number, start);
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        super.onOutgoingCallStarted(ctx, number, start);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        super.onIncomingCallEnded(ctx, number, start, end);


        CallNumbers callNumbers = CallNumbers.getInstance();

        int index = 1;
//        if(index == 1){
//            index = 2 ;
//        }else {
//            index =1;
//        }
//
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + callNumbers.getNumberlist().get(index)));
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        super.onOutgoingCallEnded(ctx, number, start, end);
        CallNumbers callNumbers = CallNumbers.getInstance();

//        int index = 1;
////        if(index == 1){
////            index = 2 ;
////        }else {
////            index =1;
////        }
////
//        Intent intent = new Intent(Intent.ACTION_CALL);
//        intent.setData(Uri.parse("tel:" + callNumbers.getNumberlist().get(index)));
//        if (intent.resolveActivity(ctx.getPackageManager()) != null) {
//            if (ctx.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    Activity#requestPermissions
//                ActivityCompat.requestPermissions((Activity) ctx,
//                        new String[]{Manifest.permission.CALL_PHONE},
//                        PhoneNumberRegister.MY_PERMISSIONS_REQUEST_Call);
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for Activity#requestPermissions for more details.
//                return;
//            }
//            ctx.startActivity(intent);
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        super.onMissedCall(ctx, number, start);

        Log.d(TAG, "onMissedCall: ");
        Log.d(TAG, "onMissedCall: number =" +number);

        CallNumbers callNumbers = CallNumbers.getInstance();

        int index = 1;
//        if(index == 1){
//            index = 2 ;
//        }else {
//            index =1;
//        }
//
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + callNumbers.getNumberlist().get(index)));
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
}
