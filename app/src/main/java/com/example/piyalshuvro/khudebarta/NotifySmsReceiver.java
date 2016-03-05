package com.example.piyalshuvro.khudebarta;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.sql.StatementEvent;

public class NotifySmsReceiver extends BroadcastReceiver {




    public static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(ACTION))
        {
            Bundle bundle = intent.getExtras();
            if (bundle != null)
            {
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];


                for (int i = 0; i < pdus.length; i++){
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                }
                for (SmsMessage message : messages)
                {

                    String strMessageNumber = message.getDisplayOriginatingAddress();
                    String strMessageBody = message.getDisplayMessageBody();

                  /* decompressing sms try-catch block*/

                    try {
                        char[] buffer = strMessageBody.toCharArray();
                        byte[] b = new byte[buffer.length << 1];
                        byte[] byteutf8 = strMessageBody.getBytes("UTF-8");

                        CharBuffer dBuffer = ByteBuffer.wrap(b).asCharBuffer();

                        for(int i = 0; i < buffer.length; i++)

                            dBuffer.put(buffer[i]);
                        SixBitEnDec objEnDec = new SixBitEnDec();


                        String finaldecompresString = objEnDec.decode(b, 8);
                        finaldecompresString=finaldecompresString.trim();




                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Calendar cal = Calendar.getInstance();
                        String currentDateTimeString = dateFormat.format(cal.getTime());


                        if (finaldecompresString.charAt(finaldecompresString.length() - 2) == '$' && finaldecompresString.charAt(finaldecompresString.length() - 1) == '৳') {
                            String finalMsg = finaldecompresString.substring(0, finaldecompresString.length() - 2);
                            DBadapter db = new DBadapter(context);
                            db.insertMsg(db.ProcessNumber(strMessageNumber), finalMsg, "INBOX", 1, currentDateTimeString,"SUCCESS");
                            showNotification(context, strMessageNumber, finalMsg);
                            abortBroadcast();
                        }


                    }
                    catch (Exception ex){
                        ex.printStackTrace();

                    }
                }
            }
        }
    }

    public void showNotification(Context c,String phoneNo,String body){
        DBadapter db = new DBadapter(c);
        Random random = new Random();
        int Unique_ID = random.nextInt(9999 - 1000) + 1000;
        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(c,ChatActivity.class);
        intent.putExtra("Number", db.ProcessNumber(phoneNo));
        intent.putExtra("IsFromNotification",true);
        PendingIntent pIntent = PendingIntent.getActivity(c, 0, intent, 0);

        String contactName=db.getContactNameFromNumber(c, phoneNo);

        Notification mNotification;

        if(phoneNo.equalsIgnoreCase(contactName)==false)
        {
            phoneNo=db.ProcessNumber(phoneNo);
            Bitmap bitmap= db.getImageBitmap(db.getContactIdFromNumber(c, phoneNo));
            mNotification=new Notification.Builder(c)
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(contactName)
                    .setContentText(body)
                    .setContentIntent(pIntent)
                    .setLights(0x00000001,500,500)
                    .setSound(soundUri)
                    .addAction(0, "View", pIntent)
                    .build();
        }
        else {
            mNotification=new Notification.Builder(c)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(db.ProcessNumber(contactName))
                    .setContentText(body)
                    .setContentIntent(pIntent)
                    .setLights(1,500,500)
                    .setSound(soundUri)
                    .addAction(0, "View", pIntent)
                    .build();
        }
        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0


        NotificationManager notificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotification.flags=Notification.FLAG_AUTO_CANCEL;

        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(Unique_ID, mNotification);
//        cancelNotification(Unique_ID,c);
    }
    public void cancelNotification(int notificationId,Context context){

        if (Context.NOTIFICATION_SERVICE!=null) {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) context.getSystemService(ns);
            nMgr.cancel(notificationId);
        }
    }

}