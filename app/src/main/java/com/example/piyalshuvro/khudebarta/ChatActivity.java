package com.example.piyalshuvro.khudebarta;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.emoticons.EmoticonsGridAdapter;
import com.emoticons.EmoticonsPagerAdapter;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;


public class ChatActivity extends ActionBarActivity implements EmoticonsGridAdapter.KeyClickListener {


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




    ListView lv;
    DBadapter dBadapter;
    CheckBox itemCheckBox;
    String phone_number;
    String englishText = "";
    int page_count;
    TextView mTextView;
    private ChatAdapter chatAdapter;
    private List<String[]> thread = new ArrayList<>();
    Database db;
    SQLiteDatabase sql;
    int flag_edit = 0, message_item_checked;
    View view;
    boolean show_checkBox = false;
    String PreferenceName = "BanglaParser";
    String PreferenceKey = "ParserStatus";
    boolean status;
    String finalString, result,checker;

    BroadcastReceiver mMessageReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_window);
        phone_number = getIntent().getStringExtra("Number");
        FromNotification=getIntent().getBooleanExtra("IsFromNotification", false);
        checker="";
        lv = (ListView) findViewById(R.id.thread_listView);

        for(int x=0;x<1000;x++)
        {
            emoText[x]="";
            hasEmo[x]=false;
        }

        dBadapter=new DBadapter(getApplicationContext());

        dBadapter.ChangeReadStatus(phone_number);


        //initialization for emoticon
        parentLayout = (LinearLayout) findViewById(R.id.List_parent);
        emoticonsCover = (LinearLayout) findViewById(R.id.footer_for_emoticons);
        popUpView = getLayoutInflater().inflate(R.layout.emoticons_popup, null);

        postButton = (Button) findViewById(R.id.post_button);

        // Defining default height of keyboard which is equal to 230 dip
        final float popUpheight = getResources().getDimension(
                R.dimen.keyboard_height);
        changeKeyboardHeight((int) popUpheight);
        // Showing and Dismissing pop up on clicking emoticons button
        ImageView emoticonsButton = (ImageView) findViewById(R.id.emoticons_button);

        readEmoticons();
        enablePopUpView();
        checkKeyboardHeight(parentLayout);
        enableFooterView();

        lv.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //hide customized emoticon keyboard
                if (popupWindow.isShowing())
                    popupWindow.dismiss();
                return false;
            }
        });

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

        mTextView = (TextView) findViewById(R.id.character_count_textview);
        itemCheckBox = (CheckBox) findViewById(R.id.CheckBox_conversation);
        view = (View) findViewById(R.id.innerLine);

        if (phone_number.equalsIgnoreCase("ক্ষুদে বার্তা") == true) {
            view.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            postButton.setVisibility(View.GONE);
            mTextView.setVisibility(View.GONE);
            emoticonsButton.setVisibility(View.GONE);

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
                loadListView();
                Toast.makeText(getApplicationContext(), result,
                        Toast.LENGTH_SHORT).show();

            }
        };

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("SMSintent"));
    /*    getSupportActionBar().setDisplayHomeAsUpEnabled(false);*/


        DBadapter dBadapter = new DBadapter(getApplicationContext());

        loadListView();
/*        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView error=(TextView)(view.findViewById(R.id.timeInfo));
                String errorMsg=((TextView)(view.findViewById(R.id.txtMessage))).getText().toString();
                if(error.length()<3)
                    content.setText(errorMsg);
                return false;
            }
        });*/
        if (show_checkBox == false) {
            String contactID = dBadapter.getContactIdFromNumber(getApplicationContext(), phone_number);
            if (contactID.equalsIgnoreCase(phone_number) == false) {
                Bitmap bitmap = dBadapter.getImageBitmap(contactID);
                if (bitmap != null) {
                    Drawable d = new BitmapDrawable(getResources(), bitmap);
                    getSupportActionBar().setHomeAsUpIndicator(d);
                } else
                    getSupportActionBar().setHomeAsUpIndicator(R.mipmap.circle_contact_image_white);
            }
            String name = dBadapter.getContactNameFromNumber(getApplicationContext(), phone_number);
            if (name.equalsIgnoreCase(phone_number) == false) {
                getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#FFFFFF'>" + phone_number + "</font>"));
            }
            setTitle(Html.fromHtml("<font color='#FFFFFF'>" + name + "</font>"));
        }

        /*itemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    message_item_checked++;
                    setTitle(message_item_checked + " Selected");
                } else if (b == false) {
                    message_item_checked--;
                    setTitle(message_item_checked + " Selected");
                }
            }
        });*/






        /*if(flag_edit==0)*/
        content.addTextChangedListener(new TextWatcher() {
            int counter;
            int p=1,q=2;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                p=content.getSelectionStart();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 0) {
                    postButton.setBackgroundResource(R.mipmap.send_black);
                } else {
                    postButton.setBackgroundResource(R.mipmap.send_blue);
                }
/*                    if(flag_edit==0)
                    {
                        if(s.length()==0)
                        {
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
                            {
                                //int cursorPosition = content.getSelectionStart();
                                englishText=englishText.substring(0,englishText.length()-1);
                            }


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
                q=content.getSelectionStart();
                //insert: p=inserted value, q=cursor poisition
                //delete: p=cursor position , q=deleted value

                if(p>q)
                {
                    //Toast.makeText(getApplicationContext(),q+content.getText().toString().substring(q,p)+p,Toast.LENGTH_SHORT).show();
                    int z;
                    int maxLen=content.length()-1;
                    if(hasEmo[q])
                    {
                        emoText[q]="";
                        hasEmo[q]=false;
                    }
                    for(z=p;z<maxLen;z++)
                    {
                        emoText[z]=emoText[z+1];
                        hasEmo[z]=hasEmo[z+1];
                    }
                }

                else if(p<q)
                {
                    //Toast.makeText(getApplicationContext(),p+content.getText().toString().substring(p,q)+q,Toast.LENGTH_SHORT).show();
                    /*int minLimit=q;*/
                    String t=content.getText().toString().charAt(p)+"";
/*                    if(t.matches(".*"))
                        minLimit=q-1;*/
                    for(int z=content.length();z>=q;z--)
                    {
                        emoText[z]=emoText[z-1];
                        hasEmo[z]=hasEmo[z-1];
                    }
                    if(hasEmo[p]/* &&minLimit!=q-1*/)
                    {
                        emoText[p]="";
                        hasEmo[p]=false;
                    }
                }
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
        content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    content.setBackgroundResource(R.drawable.apptheme_textfield_activated_holo_light);

                } else
                    content.setBackgroundResource(R.drawable.apptheme_textfield_disabled_holo_light);

            }
        });


        postButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!content.getText().toString().isEmpty()) {

                    final String phoneNo = phone_number;

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


                    SixBitEnDec objSixBitEnDec = new SixBitEnDec();
                    byte[] compressByte = objSixBitEnDec.encode(sms, 8);


                    CharBuffer cBuffer = ByteBuffer.wrap(compressByte).asCharBuffer();
                    finalString = cBuffer.toString();

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Calendar cal = Calendar.getInstance();
                    final String currentDateTimeString = dateFormat.format(cal.getTime());

                    try {
                        String SENT = "com.example.piyalshuvro.khudebarta.SMS_SENT";

                        Intent sentIntent = new Intent(SENT);
                        PendingIntent sentPI = PendingIntent.getBroadcast(
                                getApplicationContext(), 0, sentIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);

                       /* String DELIVERED="com.example.piyalshuvro.khudebarta.SMS_DELIVERED";

                        Intent deliveryIntent = new Intent(DELIVERED);

                        PendingIntent deliverPI = PendingIntent.getBroadcast(
                                getApplicationContext(), 0, deliveryIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);*/

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo,null, finalString, sentPI, null);


                        char[] buffer = finalString.toCharArray();
                        byte[] b = new byte[buffer.length << 1];

                        CharBuffer dBuffer = ByteBuffer.wrap(b).asCharBuffer();

                        for (int i = 0; i < buffer.length; i++)
                            dBuffer.put(buffer[i]);
                        SixBitEnDec objEnDec = new SixBitEnDec();
                        String finaldecompresString = objEnDec.decode(b, 8);
                        finaldecompresString = finaldecompresString.trim();
                        final String finalMsg = finaldecompresString.substring(0, (finaldecompresString.length() - 2));


                        DBadapter db = new DBadapter(getApplicationContext());
                        db.DeleteDraft(phoneNo);
/*
                        if (result == "SMS successfully sent") {
                            db.insertMsg(phoneNo, finalMsg, "SENT", 1, currentDateTimeString, "success");
                        } else*/
                        db.insertMsg(phoneNo, finalMsg, "SENT", 0, currentDateTimeString, "SENDING");

                        content.setText("");
                        finish();
                        startActivity(getIntent());

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "SMS sending failed, please try again later!",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            }
        });


    }

    private void loadListView() {

        db = new Database(this);
        sql = db.getWritableDatabase();
        dBadapter = new DBadapter(this);

        thread.clear();
        thread = dBadapter.getMessageBody(phone_number);

        if (dBadapter.HasDraft(phone_number) == true) {
            String[] x = dBadapter.GetDraftBody(phone_number);
            content.setText(x[0]);
            englishText = x[0];
            content.setSelection(x[0].length());
        }

        if (thread.size() == 0) {
            lv.setVisibility(View.INVISIBLE);
                /*Intent intent=new Intent(ChatActivity.this,compose_msg.class);
                intent.putExtra("forwarded_sms",compose_editText.getText().toString());
                intent.putExtra("forwarded_number",phone_number);
                compose_editText.setText("");
                englishText="";

                finish();
                startActivity(intent);*/
        } else {
            lv.setVisibility(View.VISIBLE);
            String[] checker = thread.get(0);
            chatAdapter = new ChatAdapter(this, thread, phone_number, show_checkBox, finalString);
            lv.setAdapter(chatAdapter);
            lv.setSelection(lv.getCount() - 1);

        }
    }

    public void onBackPressed() {
        if (show_checkBox == true) {
            finish();
            startActivity(getIntent());
        } else {
            if (content.length() != 0)
                saveAsDraft();
            else if (content.length() == 0) {
                deleteDraft();
            }
            finish();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (show_checkBox == false) {
            inflater.inflate(R.menu.menu_conversation, menu);
        } else if (show_checkBox == true) {
            inflater.inflate(R.menu.menu_delete_multiple_messages, menu);
        }
        if(phone_number.equalsIgnoreCase("ক্ষুদে বার্তা"))
        {
            menu.removeItem(R.id.call);
        }

        return true;
    }

    @Override
    protected void onResume() {
        loadListView();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.call:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone_number));
                startActivity(callIntent);
                return true;

            case android.R.id.home:
                /*Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(dBadapter.getContactIdFromNumber(getApplicationContext(),phone_number)));
                intent.setData(uri);
                startActivity(intent);*/
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                return false;

            case R.id.delete_messages:
                show_checkBox = true;
                supportInvalidateOptionsMenu();
                message_item_checked = 0;
                setTitle(message_item_checked + " Selected");
                getSupportActionBar().setSubtitle(null);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeAsUpIndicator(null);
                loadListView();
                return true;

            case R.id.close_multiple_msg_delete:
                show_checkBox = false;
                finish();
                startActivity(getIntent());
                return true;

            default:
                return true;
        }
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(ChatActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChatActivity.this, AlertDialog.THEME_HOLO_LIGHT);
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


    private void deleteDraft() {
        String recipentNum = phone_number;
        DBadapter db = new DBadapter(getApplicationContext());
        db.DeleteDraft(recipentNum);
    }

    private void saveAsDraft() {
        String stringToSave = content.getText().toString();
        String recipentNum = phone_number;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String currentDateTimeString = dateFormat.format(cal.getTime());
        DBadapter db = new DBadapter(getApplicationContext());

        if (db.HasDraft(recipentNum) == true)
            db.DeleteDraft(recipentNum);

        db.insertMsg(recipentNum, stringToSave, "DRAFT", 0, currentDateTimeString, "SUCCESS");

        Toast.makeText(getApplicationContext(), "Saved as draft", Toast.LENGTH_SHORT).show();
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

        EmoticonsPagerAdapter adapter = new EmoticonsPagerAdapter(ChatActivity.this, paths, this);
        pager.setAdapter(adapter);

        // Creating a pop window for emoticons keyboard
        popupWindow = new PopupWindow(popUpView, ViewGroup.LayoutParams.MATCH_PARENT,
                (int) keyboardHeight, false);

        TextView backSpace = (TextView) popUpView.findViewById(R.id.back);
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
    }

}

