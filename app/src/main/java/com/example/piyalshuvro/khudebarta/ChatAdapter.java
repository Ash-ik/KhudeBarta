package com.example.piyalshuvro.khudebarta;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.telephony.SmsManager;
import android.text.ClipboardManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<String[]> {
    private List<String[]> msg;
    private Activity context;
    LinearLayout main_layout, small_layout;
    LinearLayout chat_bubble;
    TimeFormatting time;
    TextView txtBody, txtTime;
    CheckBox itemISchecked;
    String phone_number;
    boolean checkbox_status;
    SmileyIcon smileyIcon;
    String result;


    public ChatAdapter(Activity context, List<String[]> msg, String number, boolean checkbox_status, String result) {
        super(context, R.layout.list_item_chat_message, msg);
        this.msg = msg;
        this.context = context;
        this.phone_number = number;
        this.checkbox_status = checkbox_status;
        this.result = result;
        smileyIcon=new SmileyIcon(this.context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        /*View view = convertView;
        if (view == null) {*/
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.list_item_chat_message, null, false);
        main_layout = (LinearLayout) view.findViewById(R.id.content);
        small_layout = (LinearLayout) view.findViewById(R.id.contentWithBackground);
        txtBody = (TextView) view.findViewById(R.id.txtMessage);
        txtTime = (TextView) view.findViewById(R.id.timeInfo);
        chat_bubble = (LinearLayout) view.findViewById(R.id.contentWithBackground);
        itemISchecked = (CheckBox) view.findViewById(R.id.CheckBox_conversation);

        if (checkbox_status == true) {
            itemISchecked.setVisibility(View.VISIBLE);
        }

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Siyamrupali.ttf");
        txtBody.setTypeface(tf);

        final String info[] = msg.get(position);
        time = new TimeFormatting();
        txtBody.setText(smileyIcon.convertToEmoticon(info[0]));
        txtTime.setText(time.showTimediff(info[1]));

        if (info[4].equalsIgnoreCase("SUCCESS")) {
            txtTime.setText(time.showTimediff(info[1]));
        } else if (info[4].equalsIgnoreCase("FAILED")) {
/*            SpannableStringBuilder builder = new SpannableStringBuilder(" ");
            builder.setSpan(new ImageSpan(context, R.drawable.sms_error),
                    0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);*/

            txtTime.setText("Sms sending failed,Tap to try again");
            txtTime.setTextColor(Color.RED);
        }else if(info[4].equalsIgnoreCase("SENDING")){
            txtTime.setText("Sending....");
        }

        final ChatActivity chatActivity = new ChatActivity();


        chat_bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (info[4].equalsIgnoreCase("SUCCESS"))
                {
                    String[] data = new String[5];
                    data[0] = phone_number;
                    data[1] = info[0];
                    data[2] = time.coversation_date(info[1]);
                    data[3] = info[2];
                    data[4] = info[3];
                    show_menu(data);
                }
                else if (info[4].equalsIgnoreCase("FAILED")) {
                    DBadapter dBadapter = new DBadapter(context);
                    dBadapter.DeleteSingleMessage(info[3]);

                    String textBody = info[0]+"$৳";

                    SixBitEnDec objSixBitEnDec = new SixBitEnDec();
                    byte[] compressByte = objSixBitEnDec.encode(textBody, 8);


                    CharBuffer cBuffer = ByteBuffer.wrap(compressByte).asCharBuffer();
                    textBody = cBuffer.toString();

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Calendar cal = Calendar.getInstance();
                    final String currentDateTimeString = dateFormat.format(cal.getTime());

                    try {
                        String SENT = "com.example.piyalshuvro.khudebarta.SMS_SENT";

                        Intent sentIntent = new Intent(SENT);
                        PendingIntent sentPI = PendingIntent.getBroadcast(
                                context, 0, sentIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone_number, null, textBody, sentPI, null);


                        char[] buffer = textBody.toCharArray();
                        byte[] b = new byte[buffer.length << 1];

                        CharBuffer dBuffer = ByteBuffer.wrap(b).asCharBuffer();

                        for (int i = 0; i < buffer.length; i++)
                            dBuffer.put(buffer[i]);
                        SixBitEnDec objEnDec = new SixBitEnDec();
                        String finaldecompresString = objEnDec.decode(b, 8);
                        finaldecompresString = finaldecompresString.trim();
                        final String finalMsg = finaldecompresString.substring(0, (finaldecompresString.length() - 2));


                        if(dBadapter.HasDraft(phone_number))
                        dBadapter.DeleteDraft(phone_number);
                        dBadapter.insertMsg(phone_number, finalMsg, "SENT", 0, currentDateTimeString, "SENDING");


                        context.finish();
                        context.startActivity(context.getIntent());

                    } catch (Exception e) {
                        Toast.makeText(context,
                                "SMS sending failed, please try again later!",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }

            }
        });


        if (info[2].equals("SENT")) {

            small_layout.setBackgroundResource(R.drawable.out);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) small_layout.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            small_layout.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) main_layout.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            main_layout.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) txtBody.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            txtBody.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) txtTime.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            txtTime.setLayoutParams(layoutParams);
        } else if (info[2].equals("INBOX")) {
            small_layout.setBackgroundResource(R.drawable.in);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) small_layout.getLayoutParams();
            if (checkbox_status == true) {
                layoutParams.setMargins(60, 0, 0, 0);
            }
            layoutParams.gravity = Gravity.LEFT;
            small_layout.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) main_layout.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            main_layout.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) txtBody.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            txtBody.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) txtTime.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            txtTime.setLayoutParams(layoutParams);
        }


        /*} else {

        }*/

        return view;
    }

    public void show_menu(final String[] data) {
        final CharSequence[] items = {
                "Delete Message", "Forward", "Copy Text", "View Details"
        };
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("Message Options");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                switch (item) {
                    case 0:
                        if (data[4].equals("1") == true) {
                            Toast.makeText(context, "System Message can't be deleted.", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            show_delete_confirmation(data[4]);
                            break;
                        }

                    case 1:
                        Intent intent = new Intent(context, compose_msg.class);
                        intent.putExtra("forwarded_sms", data[1]);
                        context.finish();
                        context.startActivity(intent);
                        break;
                    case 2:
                        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboard.setText(data[1]);
                        } else {
                            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("Copied Text", data[1]);
                            clipboard.setPrimaryClip(clip);
                        }
                        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();

                        break;
                    case 3:

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setTitle("Message details");
                        if (data[3].equals("SENT"))
                            alertBuilder.setMessage("Type: Text message\nTo: " + phone_number + "\nSent: " + data[2] + "\n");
                        else if (data[3].equals("INBOX"))
                            alertBuilder.setMessage("Type: Text message\nFrom: " + phone_number + "\nReceived: " + data[2] + "\n");

                        alertBuilder.setCancelable(true);
                        AlertDialog alertViewDetails = alertBuilder.create();

                        alertViewDetails.show();


                        break;
                    default:
                        break;
                }

            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void show_delete_confirmation(String id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        final String contactID = id;
        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("This message will be deleted");
        // set positive button: Delete thread
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(context, "Message Deleted", Toast.LENGTH_SHORT).show();
                DBadapter dB = new DBadapter(context);
                dB.DeleteSingleMessage(contactID);
                context.finish();
                if (dB.Size() != 0) {
                    if (dB.CoversationSize(phone_number) != 0) {
                        context.startActivity(context.getIntent());

                    } else {
                        Intent i = new Intent(context, Home.class);
                        context.startActivity(i);
                    }


                } else {
                    Intent i = new Intent(context, Home.class);
                    context.startActivity(i);
                }
            }
        });
        // set negative button: Cancel
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // cancel the alert box and put a Toast to the user
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }


}