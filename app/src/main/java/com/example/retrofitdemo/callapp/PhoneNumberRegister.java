package com.example.retrofitdemo.callapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.internal.telephony.ITelephony;
import com.example.retrofitdemo.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class PhoneNumberRegister extends AppCompatActivity {


    EditText mPhone1;
    EditText mPhone2;
    EditText mPhone3;
    EditText mPhone4;
    EditText mPhone5;
    EditText mPhone6;

    Button mCallbtn;
    Button mEndCall;


    public static final int MY_PERMISSIONS_REQUEST_Call = 200;

    CallNumbers callNumbers;

    ArrayList<String> numbers =new ArrayList<>(2);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_register);

        callNumbers = CallNumbers.getInstance();

        mPhone1 = findViewById(R.id.phone1);

        mPhone2 = findViewById(R.id.phone2);

        mPhone3 = findViewById(R.id.phone3);

        mPhone4 = findViewById(R.id.phone4);

        mPhone5 = findViewById(R.id.phone5);

        mPhone6 = findViewById(R.id.phone6);

        mCallbtn = findViewById(R.id.call);

        mEndCall = findViewById(R.id.end_call);

        mEndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callNumbers.setIndex(10);

                try {
                    TelephonyManager telephonyManager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                    Class clazz = Class.forName(telephonyManager.getClass().getName());
                    Method method = clazz.getDeclaredMethod("getITelephony");
                    method.setAccessible(true);
                    ITelephony telephonyService = (ITelephony) method.invoke(telephonyManager);
                    telephonyService.endCall();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

        mCallbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(PhoneNumberRegister.this,
                        new String[]{Manifest.permission.CALL_PHONE,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_CALL_LOG
                        },
                        MY_PERMISSIONS_REQUEST_Call);


                if (mPhone1.getText().length() == 10 && mPhone2.getText().length() == 10 &&
                        mPhone3.getText().length() == 10 && mPhone4.getText().length() == 10 &&
                        mPhone5.getText().length() == 10 && mPhone6.getText().length() == 10) {

                    numbers.add(mPhone1.getText().toString());
                    numbers.add(mPhone2.getText().toString());
                    numbers.add(mPhone3.getText().toString());
                    numbers.add(mPhone4.getText().toString());
                    numbers.add(mPhone5.getText().toString());
                    numbers.add(mPhone6.getText().toString());

                    callNumbers.setNumberlist(numbers);


                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + mPhone1.getText()));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            ActivityCompat.requestPermissions(PhoneNumberRegister.this,
                                    new String[]{Manifest.permission.CALL_PHONE,
                                            Manifest.permission.READ_PHONE_STATE,
                                            Manifest.permission.READ_CALL_LOG
                                    },
                                    MY_PERMISSIONS_REQUEST_Call);
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(PhoneNumberRegister.this, "Enter Valid Numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_Call: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        callNumbers.setIndex(0);
    }
}
