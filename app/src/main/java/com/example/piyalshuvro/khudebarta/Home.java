package com.example.piyalshuvro.khudebarta;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class Home extends ActionBarActivity {
    ListView lvMsg;
    ImageButton compose;
    DBadapter dBadapter;

    private String[] ThreadMsgName;
    private String[] ThreadMsgNumber;
    private String[] ThreadMsgBody;
    private String[] ThreadMsgDate;
    private String[] ThreadMsgStatus;
    private CustomListView customList;
    Database db;
    SQLiteDatabase sql;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        lvMsg = (ListView) findViewById(R.id.listThread);
        lvMsg.setScrollContainer(true);
        loadListView();


        compose = (ImageButton) findViewById(R.id.floating_imageButton);


        lvMsg.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(mLastFirstVisibleItem<firstVisibleItem)
                {
//                    Log.i("SCROLLING DOWN", "TRUE");
                    onDownScrolling();
                }
                if(mLastFirstVisibleItem>firstVisibleItem)
                {
//                    Log.i("SCROLLING UP","TRUE");
                    onUpScrolling();
                }
                mLastFirstVisibleItem=firstVisibleItem;

            }
        });

        lvMsg.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String number = ((TextView) view.findViewById(R.id.hiddentextView)).getText().toString();
                Intent HomeIntent = new Intent(getApplicationContext(), ChatActivity.class);
                HomeIntent.putExtra("Number",number);
                startActivity(HomeIntent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
               /* TextView contactName = (TextView) view.findViewById(R.id.textContactName);
                TextView body = (TextView) view.findViewById(R.id.textContent);
                TextView time = (TextView) view.findViewById(R.id.textTime);*//*
                if (((TextView) view.findViewById(R.id.textContactName)).getTypeface().getStyle() == Typeface.BOLD) {
                    dBadapter.ChangeReadStatus(number);
                }*/


            }
        });

        compose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent compose_intent = new Intent(Home.this, compose_msg.class);
                startActivity(compose_intent);
            }
        });

        registerForContextMenu(lvMsg);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        View view = info.targetView;
        String contactNumber = ((TextView) view.findViewById(R.id.hiddentextView)).getText().toString();
        String contactName = ((TextView) view.findViewById(R.id.textContactName)).getText().toString();
        switch (item.getItemId()) {
            case R.id.delete:
                if (contactNumber.equalsIgnoreCase("ক্ষুদে বার্তা") == true) {
                    Toast.makeText(getApplicationContext(), "System Message can't be deleted.", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    ImageView imageView=((ImageView) view.findViewById(R.id.imageSender));
                    show_dialog_box(contactNumber, contactName,imageView.getDrawable());
                    return true;
                }

            case R.id.add_to_contact:
                if (contactName.equals(contactNumber)) {
                    add_to_phone_contact(contactNumber);
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry, number already in contacts.", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    public void show_dialog_box(String Number, String Name,Drawable userPic) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this);
        final String contactNumber = Number;
        final String contactName = Name;
        alertDialogBuilder.setTitle(contactName);
        alertDialogBuilder.setMessage("Are you sure?");
        alertDialogBuilder.setIcon(userPic);
        // set positive button: Delete thread
        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "All Conversation of " + contactNumber.toString() + " Deleted", Toast.LENGTH_LONG).show();
                dBadapter.DeleteThread(contactNumber);
                loadListView();

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



    /*this methods add number to phone internal contact*/

    public void add_to_phone_contact(String number) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, number);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        loadListView();
        super.onResume();
    }


    private void loadListView() {

        db = new Database(this);
        sql = db.getWritableDatabase();
        dBadapter = new DBadapter(this);

        if (dBadapter.Size() == 0) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String currentDateTimeString = dateFormat.format(cal.getTime());
            dBadapter.insertMsg("ক্ষুদে বার্তা", "ক্ষুদে বার্তার দুনিয়ায় আপনাকে স্বাগতম।", "INBOX", 0, currentDateTimeString,"SUCCESS");
        } else if (dBadapter.Size() > 1)
            dBadapter.DeleteThread("ক্ষুদে বার্তা");
        List<String[]> thread = new ArrayList<>();
        thread.clear();
        ThreadMsgName = new String[dBadapter.Size()];
        ThreadMsgNumber = new String[dBadapter.Size()];
        ThreadMsgBody = new String[dBadapter.Size()];
        ThreadMsgDate = new String[dBadapter.Size()];
        ThreadMsgStatus = new String[dBadapter.Size()];
        ThreadMsgNumber = dBadapter.getDistinctName();
        ThreadMsgBody = dBadapter.getRecentMessage(ThreadMsgNumber);
        ThreadMsgDate = dBadapter.getMessageDate(ThreadMsgNumber);
        ThreadMsgStatus = dBadapter.getMessageStatus(ThreadMsgNumber);


        for (int i = 0; i < dBadapter.Size(); i++) {
            ThreadMsgName[i] = dBadapter.getContactNameFromNumber(this, ThreadMsgNumber[i]);
        }

        for (int i = 0; i < dBadapter.Size(); i++) {
            String[] temp = new String[5];
            temp[0] = ThreadMsgName[i];
            temp[1] = ThreadMsgBody[i];
            temp[2] = ThreadMsgDate[i];
            temp[3] = ThreadMsgNumber[i];
            temp[4] = ThreadMsgStatus[i];

            thread.add(temp);
        }
        customList = new CustomListView(this, thread);
        lvMsg.setAdapter(customList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent Settingsintent = new Intent(getApplicationContext(), Settings.class);
                startActivity(Settingsintent);
                break;
            case R.id.help:
                Intent newintent = new Intent(getApplicationContext(), HelpActivity.class);
                startActivity(newintent);
                break;
            case R.id.about_us:
                Intent intent = new Intent(getApplicationContext(), About_us_Activity.class);
                startActivity(intent);
                break;
            case R.id.contact_with_us:
                showEmailDialogBox();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showEmailDialogBox() {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.contact_with_us, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Home.this, AlertDialog.THEME_HOLO_LIGHT);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userEmailSubject = (EditText) promptsView
                .findViewById(R.id.subjectEmail);
        final EditText userFeedback = (EditText) promptsView
                .findViewById(R.id.Body);
        final TextView email_validator = (TextView) promptsView.
                findViewById(R.id.Email_validator);

        // set dialog message
        alertDialogBuilder
                .setTitle("Send your Email")
                .setCancelable(false)
                .setPositiveButton("Send",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                String emailSubject = userEmailSubject.getText().toString();
                                String body = userFeedback.getText().toString();
                                Intent email_intent = new Intent(Intent.ACTION_SEND);
                                email_intent.setData(Uri.parse("mailto:"));
                                email_intent.setType("message/rfc822");
                                email_intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"khudebartaa@gmail.com"});//Here string array is necessary or TO will not be add
                                email_intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                                email_intent.putExtra(Intent.EXTRA_TEXT, body);

                                try {
                                    startActivity(Intent.createChooser(email_intent, "Choose an Email client"));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public void onUpScrolling() {
        compose.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }


    public void onDownScrolling()
    {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) compose.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        compose.animate().translationY(compose.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }
}
