package com.example.piyalshuvro.khudebarta;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by Pial-PC on 11/24/2015.
 */
public class SentSMSreceiver extends BroadcastReceiver {

    public static final String ACTION_SMS_SENT = "com.example.piyalshuvro.khudebarta.SMS_SENT";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        String result="";

        if (action.equals(ACTION_SMS_SENT))
        {
            switch (getResultCode()) {

                case Activity.RESULT_OK:
                    result = "SMS successfully sent";
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    result = "SMS sending failed";
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    result = "Airplane mode is on";
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    result = "No PDU defined";
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    result = "No cellular service";
                    break;
                default:
                    break;
            }

            Intent in = new Intent("SMSintent");
            in.putExtra("Status",result);
            LocalBroadcastManager.getInstance(context).sendBroadcast(in);
        }
    }
}
