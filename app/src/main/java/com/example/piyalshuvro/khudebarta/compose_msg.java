package com.example.piyalshuvro.khudebarta;


import android.app.Activity;
import android.app.AlertDialog;

import com.emoticons.EmoticonsGridAdapter;
import com.emoticons.EmoticonsPagerAdapter;
import com.krishna.ContactPickerAdapter;
import com.krishna.CustomMultiAutoCompleteTextView;
import com.krishna.SmsUtil;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Scroller;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;


public class compose_msg extends ActionBarActivity implements EmoticonsGridAdapter.KeyClickListener{

    //Variables For emoticons
    private static final int NO_OF_EMOTICONS = 54;
    private View popUpView;
    private LinearLayout emoticonsCover;
    private PopupWindow popupWindow;
    private int keyboardHeight;
    private boolean isKeyBoardVisible;
    private Bitmap[] emoticons;
    private LinearLayout parentLayout;
    EditText content;
    Boolean emoIsEntering=false,FromNotification=false;
    Button postButton;
    String[] emoText=new String[1000];
    Boolean[] hasEmo=new Boolean[1000];
    
    
    
    
    private CustomMultiAutoCompleteTextView phoneNum;
    TextView mTextView;
    int flag_edit = 0;
    String englishText = "";
    String phone_number, contact_name;
    int page_count;
    String PreferenceName = "BanglaParser";
    String PreferenceKey = "ParserStatus";
    boolean status;
    String result = "";
    BroadcastReceiver mMessageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_msg);



        for(int x=0;x<1000;x++)
        {
            emoText[x]="";
            hasEmo[x]=false;
        }

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                DBadapter db = new DBadapter(context);
                result = intent.getStringExtra("Status");
                if(result.equalsIgnoreCase("SMS successfully sent"))
                {
                    if(db.IsSmsPending(phone_number))
                    {
                        db.UpdatePendingSms(phone_number,"SUCCESS");
                    }
                }
                else
                {
                    db.UpdatePendingSms(phone_number,"FAILED");
                }
                Toast.makeText(getApplicationContext(), result,
                        Toast.LENGTH_SHORT).show();

            }
        };

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("SMSintent"));


        phoneNum = (CustomMultiAutoCompleteTextView)
                findViewById(R.id.editText);
        ContactPickerAdapter contactPickerAdapter = new ContactPickerAdapter(this,
                android.R.layout.simple_list_item_2, SmsUtil.getContacts(
                this, false));
        phoneNum.setAdapter(contactPickerAdapter);
        phoneNum.addTextChangedListener(new TextWatcher() {
            DBadapter dBadapter = new DBadapter(getApplicationContext());

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = phoneNum.getNumberCount();
                if (len != 0) {
                    String[] x = phoneNum.getAllName();
                    String[] y = phoneNum.getAllNum();
                    String temp = "";
                    if (len == 1) {
                        if (x[0].equals(y[0]))
                            setTitle(Html.fromHtml("<font color='#FFFFFF' size='5'><b>" + dBadapter.ProcessNumber(x[0]) + "</b></font>"));
                        else {
                            setTitle(Html.fromHtml("<font color='#FFFFFF' size='5'>" + x[0] + "</font>"));
                            getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#FFFFFF' size='5'>" + dBadapter.ProcessNumber(y[0]) + "</font>"));
                        }
                        String contactID = dBadapter.getContactIdFromNumber(getApplicationContext(), y[0]);
                        if (contactID.equalsIgnoreCase(y[0]) == false) {
                            Bitmap bitmap = dBadapter.getImageBitmap(contactID);
                            if (bitmap != null) {
                                Drawable d = new BitmapDrawable(getResources(), bitmap);
                                getSupportActionBar().setHomeAsUpIndicator(d);
                            } else
                                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.circle_contact_image_white);
                        }
                    } else if (len > 1) {
                        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.circle_contact_image_white);
                        getSupportActionBar().setSubtitle("");
                        if (len == 2) temp = x[0] + " and 1 other";
                        else if (len > 2) temp = x[0] + " and " + (len - 1) + " others";
                        setTitle(Html.fromHtml("<font color='#FFFFFF'>" + temp + "</font>"));
                    }
                    if (content.length() != 0)
                        postButton.setBackgroundResource(R.mipmap.send_blue);
                    else
                        postButton.setBackgroundResource(R.mipmap.send_black);
                }
                if (len == 0 || phoneNum.length() == 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setHomeAsUpIndicator(null);
                    getSupportActionBar().setSubtitle("");
                    setTitle("ক্ষুদে বার্তা");
                    postButton.setBackgroundResource(R.mipmap.send_black);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //initialization for emoticon
        parentLayout = (LinearLayout) findViewById(R.id.List_parent);
        emoticonsCover = (LinearLayout) findViewById(R.id.footer_for_emoticons);
        popUpView = getLayoutInflater().inflate(R.layout.emoticons_popup, null);

        postButton = (Button) findViewById(R.id.post_button);
        content=(EditText)findViewById(R.id.chat_content);

        // Defining default height of keyboard which is equal to 230 dip
        final float popUpheight = getResources().getDimension(
                R.dimen.keyboard_height);
        changeKeyboardHeight((int) popUpheight);
        // Showing and Dismissing pop up on clicking emoticons button
        ImageView emoticonsButton = (ImageView) findViewById(R.id.emoticons_button);

        emoticonsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!popupWindow.isShowing()) {

                    popupWindow.setHeight((int) (keyboardHeight));

                    if (isKeyBoardVisible) {
                        emoticonsCover.setVisibility(LinearLayout.GONE);
                    } else {
                        emoticonsCover.setVisibility(LinearLayout.VISIBLE);
                    }
                    popupWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0);

                } else {
                    popupWindow.dismiss();
                }

            }
        });

        readEmoticons();
        enablePopUpView();
        checkKeyboardHeight(parentLayout);
        enableFooterView();
        


        mTextView = (TextView) findViewById(R.id.character_count_textview);
        content.setScroller(new Scroller(getApplicationContext()));
        content.setMaxLines(5);
        content.setVerticalScrollBarEnabled(true);
        content.setMovementMethod(new ScrollingMovementMethod());
        String forwarded_sms = getIntent().getStringExtra("forwarded_sms");
        if (forwarded_sms != null) {
            content.setText(forwarded_sms);
            englishText = forwarded_sms;
        }

        content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    content.setBackgroundResource(R.drawable.apptheme_textfield_activated_holo_light);
                } else
                    content.setBackgroundResource(R.drawable.apptheme_textfield_disabled_holo_light);
            }
        });


        SharedPreferences prefer = getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        status = prefer.getBoolean(PreferenceKey, false);

        if (status) {
            content.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEvent.ACTION_UP == event.getAction()) {
                        content.setBackgroundResource(R.drawable.apptheme_textfield_activated_holo_light);
                        showInputDialog();
                    }
                    return true; // return is important...
                }
            });
        }


/*        if(flag_edit==0)*/
        content.addTextChangedListener(new TextWatcher() {
            int counter;
            int p=1,q=2;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                p=content.getSelectionStart();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    if (phoneNum.getNumberCount() != 0)
                        postButton.setBackgroundResource(R.mipmap.send_blue);
                    else
                        postButton.setBackgroundResource(R.mipmap.send_black);
                }

                    /*if (flag_edit == 0) {
                        if (s.length() == 0) {
                            postButton.setBackgroundResource(R.drawable.send_black);
                            englishText="";
                        }
                        else
                        {
                            postButton.setBackgroundResource(R.drawable.send_blue);
                            int len=englishText.length();
                            if(count!=0)
                                englishText=englishText+s.toString().substring(start,start+count);
                            else if(count==0)
                                englishText=englishText.substring(0,englishText.length()-1);

                        }

                        flag_edit=1;
                        content.setText(BanglaPhoneticParser.parse(englishText));
                        content.setSelection(content.length());*/

                if (s.length() >= 0 && s.length() <= 158) {
                    page_count = 1;
                    counter = 158;
                } else if (s.length() >= 159 && s.length() <= 304) {
                    page_count = 2;
                    counter = 304;
                } else if (s.length() >= 305 && s.length() <= 457) {
                    page_count = 3;
                    counter = 457;
                } else if (s.length() >= 458 && s.length() <= 608) {
                    page_count = 4;
                    counter = 608;
                }
                String character_count = "(" + page_count + ") " + s.length() + "/" + counter;
                mTextView.setText(character_count);
                flag_edit = 0;

            }


            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!content.getText().toString().isEmpty() && phoneNum.getNumberCount() != 0) {


                    //1,6,11,14
                    //1=<&01>
                    //6=<&02>
                    //11=<&03>
                    //14=<&04>
                    //i.have.done.it.
                    //i<&01>have<&02>done<&03>it<&04>


                    String sms = "";

                    for(int i=0;i<content.length();i++)
                    {
                        if(hasEmo[i] &&emoText[i].length()!=0)
                        {
                            sms=sms+emoText[i];
                        }
                        else
                            sms=sms+content.getText().charAt(i);
                    }

                    sms = sms + "$৳";
                   /* while (sms.length() % 16 < 9) {
                        sms = " " + sms;
                    }*/
                    SixBitEnDec objSixBitEnDec = new SixBitEnDec();
                    byte[] compressByte = objSixBitEnDec.encode(sms, 8);
                    CharBuffer cBuffer = ByteBuffer.wrap(compressByte).asCharBuffer();
                    String finalString = cBuffer.toString();

                    char[] buffer = finalString.toCharArray();
                    byte[] b = new byte[buffer.length << 1];
                    CharBuffer dBuffer = ByteBuffer.wrap(b).asCharBuffer();
                    for (int i = 0; i < buffer.length; i++)
                        dBuffer.put(buffer[i]);
                    SixBitEnDec objEnDec = new SixBitEnDec();
                    String finaldecompresString = objEnDec.decode(b, 8);
                    finaldecompresString = finaldecompresString.trim();
                    String finalMsg = finaldecompresString.substring(0, (finaldecompresString.length() - 2));

                    DBadapter db = new DBadapter(getApplicationContext());
                    String[] num = phoneNum.getAllNum();
                    for (int k = 0; k < num.length; k++) {
                        num[k] = db.ProcessNumber(num[k]);

                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Calendar cal = Calendar.getInstance();
                        String currentDateTimeString = dateFormat.format(cal.getTime());
                        try {
                            String SENT = "com.example.piyalshuvro.khudebarta.SMS_SENT";

                            Intent sentIntent = new Intent(SENT);
                            PendingIntent sentPI = PendingIntent.getBroadcast(
                                    getApplicationContext(), 0, sentIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                            /*String DELIVERED="com.example.piyalshuvro.khudebarta.SMS_DELIVERED";

                            Intent deliveryIntent = new Intent(DELIVERED);

                            PendingIntent deliverPI = PendingIntent.getBroadcast(
                                    getApplicationContext(), 0, deliveryIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);*/



                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(num[k], null, finalString, sentPI, null);

                            db.insertMsg(num[k], finalMsg, "SENT", 0, currentDateTimeString, "SENDING");
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS failed, please try again later!",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                    String sendNumDetails = num[0];
                    for (int p = 1; p < phoneNum.getNumberCount(); p++)
                        sendNumDetails = sendNumDetails + "," + num[p];


                    content.setText("");
                    finish();

                    if (phoneNum.getNumberCount() == 1) {
                        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                        intent.putExtra("Number", num[0]);
                        startActivity(intent);
                    } else if (phoneNum.getNumberCount() > 1) {
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    }


                }
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return false;
            default:
                return true;
        }
    }

    public void onBackPressed() {
        if (content.length() != 0 && phoneNum.getNumberCount() == 0)
            showDiscardMessageWarning();
        else {
            if (content.length() != 0 && phoneNum.getNumberCount() != 0)
                saveAsDraft();

            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }

    public String getPhoneNumber(String name) {
        Context context = getApplicationContext();
        String ret = null;
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ='" + name + "'";
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, null, null);
        if (c.moveToFirst()) {
            ret = c.getString(0);
        }
        c.close();
        if (ret == null)
            ret = "";
        return ret;
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(compose_msg.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(compose_msg.this, AlertDialog.THEME_HOLO_LIGHT);
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setTitle("Write text to convert it in bangla");

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        final TextView Btext = (TextView) promptView.findViewById(R.id.textViewBANGLA);
        String past_text = content.getText().toString();
        Btext.setText("");
        if (past_text.length() == 0)
            Btext.setText("");
        else
            Btext.setText(past_text);
        editText.setText(englishText);
        editText.setSelection(editText.length());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("")) {
                    String result = BanglaPhoneticParser.parse(s.toString());
                    Btext.setText(result);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        content.setText(Btext.getText().toString());
                        content.setSelection(content.length());
                        phoneNum.setSelection(phoneNum.length());
                        content.setBackgroundResource(R.drawable.apptheme_textfield_disabled_focused_holo_light);
                        englishText = editText.getText().toString();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void saveAsDraft() {
        String stringToSave = content.getText().toString();
        String[] recipentName = phoneNum.getAllName();
        String[] recipentNum = phoneNum.getAllNum();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDateTimeString = dateFormat.format(cal.getTime());

        TimeFormatting timeFormatting = new TimeFormatting();
        currentDateTimeString = timeFormatting.showTimediff(currentDateTimeString);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        DBadapter db = new DBadapter(getApplicationContext());
        for (int i = 0; i < phoneNum.getNumberCount(); i++) {
            recipentNum[i] = db.ProcessNumber(recipentNum[i]);

            if (db.HasDraft(recipentNum[i]) == true)
                db.DeleteDraft(recipentNum[i]);
            db.insertMsg(recipentNum[i], stringToSave, "DRAFT", 0, currentDateTimeString, "SUCCESS");


/*            sharedPreferences.edit().putBoolean(recipentNum[i]+"HAS_DRAFT",true).commit();
            sharedPreferences.edit().putString(recipentNum[i] + "DRAFT_BODY", stringToSave).commit();
            sharedPreferences.edit().putString(recipentNum[i]+"DRAFT_TIME",currentDateTimeString).commit();*/
        }
        Toast.makeText(getApplicationContext(), "Saved as draft", Toast.LENGTH_SHORT).show();
    }

    private void showDiscardMessageWarning() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(compose_msg.this);
        alertDialogBuilder.setTitle("Warning");
        alertDialogBuilder.setIcon(R.drawable.abc_spinner_mtrl_am_alpha);
        alertDialogBuilder.setMessage("Your Message will be discarded because it has no valid recipents.");
        // set positive button: Delete thread
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                content.setText("");
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
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






    //Emoticons functions





    /**
     * Overriding onKeyDown for dismissing keyboard on key down
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * Checking keyboard height and keyboard visibility
     */
    int previousHeightDiffrence = 0;
    private void checkKeyboardHeight(final View parentLayout) {

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        parentLayout.getWindowVisibleDisplayFrame(r);

                        int screenHeight = parentLayout.getRootView()
                                .getHeight();
                        int heightDifference = screenHeight - (r.bottom);

                        if (previousHeightDiffrence - heightDifference > 50) {
                            popupWindow.dismiss();
                        }

                        previousHeightDiffrence = heightDifference;
                        if (heightDifference > 100) {

                            isKeyBoardVisible = true;
                            changeKeyboardHeight(heightDifference);

                        } else {

                            isKeyBoardVisible = false;

                        }

                    }
                });

    }


    /**
     * change height of emoticons keyboard according to height of actual
     * keyboard
     *
     * @param height minimum height by which we can make sure actual keyboard is
     *               open or not
     */
    private void changeKeyboardHeight(int height) {

        if (height > 100) {
            keyboardHeight = height;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, keyboardHeight);
            emoticonsCover.setLayoutParams(params);
        }

    }

    /**
     * Defining all components of emoticons keyboard
     */
    private void enablePopUpView() {

        ViewPager pager = (ViewPager) popUpView.findViewById(R.id.emoticons_pager);
        pager.setOffscreenPageLimit(3);

        ArrayList<String> paths = new ArrayList<String>();

        for (short i = 1; i <= NO_OF_EMOTICONS; i++) {
            paths.add(i + ".png");
        }

        EmoticonsPagerAdapter adapter = new EmoticonsPagerAdapter(compose_msg.this, paths, this);
        pager.setAdapter(adapter);

        // Creating a pop window for emoticons keyboard
        popupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT,
                (int) keyboardHeight, false);

        Button backSpace = (Button) popUpView.findViewById(R.id.back);
        backSpace.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                content.dispatchKeyEvent(event);
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                emoticonsCover.setVisibility(LinearLayout.GONE);
            }
        });
    }


    /**
     * Reading all emoticons in local cache
     */
    private void readEmoticons() {

        emoticons = new Bitmap[NO_OF_EMOTICONS];
        for (short i = 0; i < NO_OF_EMOTICONS; i++) {
            emoticons[i] = getImage((i + 1) + ".png");
        }

    }

    /**
     * For loading smileys from assets
     */
    private Bitmap getImage(String path) {
        AssetManager mngr = getAssets();
        InputStream in = null;
        try {
            in = mngr.open("emoticons/" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap temp = BitmapFactory.decodeStream(in, null, null);
        return temp;
    }


    /**
     * Enabling all content in footer i.e. post window
     */
    private void enableFooterView() {

        content = (EditText) findViewById(R.id.chat_content);
        content.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (popupWindow.isShowing()) {

                    popupWindow.dismiss();

                }

            }
        });


    }

    @Override
    public void keyClickedIndex(final String index) {

        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                StringTokenizer st = new StringTokenizer(index, ".");
                Drawable d = new BitmapDrawable(getResources(),emoticons[Integer.parseInt(st.nextToken()) - 1]);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                return d;
            }
        };

        Spanned cs = Html.fromHtml("<img src ='"+ index +"'/>", imageGetter, null);
        int cursorPosition = content.getSelectionStart();
        content.getText().insert(cursorPosition, cs);
        int pos=Integer.parseInt(index.substring(0,index.length()-4));
        if(pos<10)
            emoText[cursorPosition]="<&0"+pos+">";
        else emoText[cursorPosition]="<&"+pos+">";
        hasEmo[cursorPosition]=true;
        hasEmo[cursorPosition]=true;
    }

}