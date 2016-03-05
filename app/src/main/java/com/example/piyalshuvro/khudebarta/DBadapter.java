package com.example.piyalshuvro.khudebarta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Piyal Shuvro on 8/23/2015.
 */
public class DBadapter {
    private Database database;
    private Context context;
    private SQLiteDatabase db;

    public DBadapter(Context context) {
        this.context = context;
        database=new Database(context);
        db=database.getWritableDatabase();
    }



    public void insertMsg(String name,String txt,String type,int password,String date,String status) {
        ContentValues values = new ContentValues();
        values.put(database.Column_NUMBER, name);
        values.put(database.Column_TEXTBODY, txt);
        values.put(database.Column_TYPE, type);
        values.put(database.Column_PASSWORD,password);
        values.put(database.Column_Date, date);
        values.put(database.Column_Status,status);

        long insert= db.insert(database.Table_Name, null, values);

    }

    public String[] getDistinctName(){
        String[] DistinctName=null;
        String select_number_query="SELECT DISTINCT ("+database.Column_NUMBER+") FROM "+database.Table_Name+" ORDER BY "+database.Column_ID+" DESC ";
        Cursor cursor = db.rawQuery(select_number_query, null);
        if (cursor != null && cursor.getCount() > 0) {
            int size = cursor.getCount();
            DistinctName=new String[size];
            cursor.moveToFirst();
            for (int i = 0; i < size; i++) {
                DistinctName[i] = cursor.getString(cursor.getColumnIndex(database.Column_NUMBER));
                cursor.moveToNext();
            }
        }
        return DistinctName;

    }



    public String[] getRecentMessage(String[] names){
        String[] MessageBody=new String[names.length];
        for(int count=0;count<names.length;count++) {
            String select_date_query = "SELECT " +
                    database.Column_TEXTBODY +
                    " FROM " + database.Table_Name +
                    " WHERE " + database.Column_NUMBER + "="+"'"+
                    names[count]+"'"+" ORDER BY "+database.Column_ID+" DESC ";
            Cursor cursor = db.rawQuery(select_date_query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                MessageBody[count] = cursor.getString(cursor.getColumnIndex(database.Column_TEXTBODY));
            }

        }
        return MessageBody;
    }

    public String[] getMessageDate(String[] names){
        String[] MessageDate=new String[names.length];
        for(int count=0;count<names.length;count++) {
            String select_date_query = "SELECT " +
                    database.Column_Date +
                    " FROM " + database.Table_Name +
                    " WHERE " + database.Column_NUMBER + "="+"'"+
                    names[count]+"'"+" ORDER BY "+database.Column_ID+" DESC ";
            Cursor cursor = db.rawQuery(select_date_query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                MessageDate[count] = cursor.getString(cursor.getColumnIndex(database.Column_Date));
            }

        }
        return MessageDate;
    }

    public String[] getMessageStatus(String[] names){
        String[] MessageStatus=new String[names.length];
        for(int count=0;count<names.length;count++) {
            String select_date_query = "SELECT " +
                    database.Column_Status +
                    " FROM " + database.Table_Name +
                    " WHERE " + database.Column_NUMBER + "="+"'"+
                    names[count]+"'"+" ORDER BY "+database.Column_ID+" DESC ";
            Cursor cursor = db.rawQuery(select_date_query, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                MessageStatus[count] = cursor.getString(cursor.getColumnIndex(database.Column_Status));
            }

        }
        return MessageStatus;
    }

    public boolean HasDraft(String number){
        String size_query="SELECT DISTINCT ("+database.Column_NUMBER+") FROM "+database.Table_Name+" WHERE "+database.Column_NUMBER+"="+"'"+number+"' AND "+database.Column_TYPE+"="+"'DRAFT'";
        Cursor cursor = db.rawQuery(size_query, null);

        if(cursor.getCount()>0)
            return true;
        return false;
    }
    public boolean IsSmsPending(String number){
        String size_query="SELECT DISTINCT ("+database.Column_NUMBER+") FROM "+database.Table_Name+" WHERE "+database.Column_NUMBER+"="+"'"+number+"' AND "+database.Column_TYPE+"="+"'SENT' AND "+database.Column_Status+"="+"'SENDING'";
        Cursor cursor = db.rawQuery(size_query, null);

        if(cursor.getCount()>0)
            return true;
        return false;
    }

    public String[] GetPendingSmsBody(String number)
    {
        String MessageBody[]=new String[3];
        String select_date_query = "SELECT " +
                database.Column_TEXTBODY +","+database.Column_Date+ " FROM " + database.Table_Name+
                " WHERE "+database.Column_NUMBER+"="+"'"+number+"' AND "+database.Column_TYPE+"="+"'SENT' AND "+database.Column_Status+"="+"'SENDING'";
        Cursor cursor = db.rawQuery(select_date_query, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            MessageBody[0] = cursor.getString(cursor.getColumnIndex(database.Column_TEXTBODY));
            MessageBody[1]=cursor.getString(cursor.getColumnIndex(database.Column_Date));

        }

        return MessageBody;
    }
    public void UpdatePendingSms(String number,String result) {

        String change_pass = "UPDATE " + database.Table_Name + " SET " + database.Column_Status + " ='"+result+"'" + " WHERE " +
                database.Column_NUMBER + "=" + "'" + number + "'" + " AND " + database.Column_Status + " ='SENDING'";
        Cursor cursor = db.rawQuery(change_pass, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
    }

    public String[] GetDraftBody(String number)
    {
        String MessageBody[]=new String[3];
        String select_date_query = "SELECT " +
                database.Column_TEXTBODY +","+database.Column_Date+ " FROM " + database.Table_Name+
                " WHERE "+database.Column_NUMBER+"="+"'"+number+"' AND "+database.Column_TYPE+"="+"'DRAFT'";
        Cursor cursor = db.rawQuery(select_date_query, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            MessageBody[0] = cursor.getString(cursor.getColumnIndex(database.Column_TEXTBODY));
            MessageBody[1]=cursor.getString(cursor.getColumnIndex(database.Column_Date));

        }

        return MessageBody;
    }
    public void DeleteDraft(String number)
    {
        db.execSQL("DELETE FROM "+database.Table_Name+" WHERE "+database.Column_NUMBER+"="+"'"+number+"' AND "+database.Column_TYPE+"="+"'DRAFT'");
    }


    public int Size(){
        String size_query="SELECT DISTINCT ("+database.Column_NUMBER+") FROM "+database.Table_Name;
        Cursor cursor = db.rawQuery(size_query, null);

        return cursor.getCount();
    }
    public int CoversationSize(String number){
        String size_query="SELECT "+database.Column_TEXTBODY +" FROM "+database.Table_Name+" WHERE "+database.Column_NUMBER+"="+"'"+number+"'";
        Cursor cursor = db.rawQuery(size_query, null);
        return cursor.getCount();
    }

    public String getContactNameFromNumber(Context context,
                                           String number) {

        String phNo = number; // phone number you already have
        String name = number.toString();
        try {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                    Uri.encode(phNo));
            Cursor cs = context.getContentResolver()
                    .query(uri, new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME },
                            null, null, null);
            if (cs.getCount() > 0) {
                cs.moveToFirst();
                name = cs.getString(cs.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                cs.close();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }

    public String getContactIdFromNumber(Context ctext,
                                         String number) {

        String phNo = number; // phone number you already have
        String id = number.toString();
        try {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                    Uri.encode(phNo));
            Cursor cs = ctext.getContentResolver()
                    .query(uri, new String[] { ContactsContract.PhoneLookup._ID },
                            null, null, null);
            if (cs.getCount() > 0) {
                cs.moveToFirst();
                id = cs.getString(cs.getColumnIndex(ContactsContract.PhoneLookup._ID));
                cs.close();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.WHITE;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    public Bitmap getImageBitmap(String Contact_Id)
    {
        Uri my_contact_Uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(Contact_Id));
        Log.d("URI", my_contact_Uri.toString());
        InputStream photo_stream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), my_contact_Uri);
        if (photo_stream == null) return null;
        Bitmap bitmap = BitmapFactory.decodeStream(photo_stream);
        return bitmap;
    }

    public Uri getImageURI(String Contact_Id)
    {
        Uri my_contact_Uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(Contact_Id));
        return my_contact_Uri;
    }

    public List<String[]> getMessageBody(String names){
        List<String[]> MessageDetails= new ArrayList<>();
        String MessageBody;
        String MessageDate;
        String MessageType;
        String MessageID;
        String MessageStatus;
        String select_date_query = "SELECT " +
                database.Column_TEXTBODY +","+database.Column_Date+
                ","+database.Column_TYPE+","+database.Column_ID +","+database.Column_Status +" FROM " + database.Table_Name+
                " WHERE " + database.Column_NUMBER + "="+"'"+
                names+"'"+" AND "+database.Column_TYPE+" NOT IN ("+"'DRAFT')";
        Cursor cursor = db.rawQuery(select_date_query, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i=0;i<cursor.getCount();i++) {
                String[] temp= new String[5];
                MessageBody = cursor.getString(cursor.getColumnIndex(database.Column_TEXTBODY));
                MessageDate=cursor.getString(cursor.getColumnIndex(database.Column_Date));
                MessageType=cursor.getString(cursor.getColumnIndex(database.Column_TYPE));
                MessageID=cursor.getString(cursor.getColumnIndex(database.Column_ID));
                MessageStatus=cursor.getString(cursor.getColumnIndex(database.Column_Status));

                temp[0]=MessageBody;
                temp[1]=MessageDate;
                temp[2]=MessageType;
                temp[3]=MessageID;
                temp[4]=MessageStatus;
                MessageDetails.add(temp);
                cursor.moveToNext();
            }
        }

        return MessageDetails;
    }

    /*Deleting a list thread */

    public void DeleteThread(String Number){
        db.execSQL("DELETE FROM "+database.Table_Name+" WHERE "+database.Column_NUMBER+"="+"'"+Number+"'");
    }

    public void DeleteSingleMessage(String id){
        int msg_id=Integer.parseInt(id);
        db.execSQL("DELETE FROM "+database.Table_Name+" WHERE "+database.Column_ID+"="+msg_id);
        db.execSQL("DELETE FROM "+database.Table_Name+" WHERE "+database.Column_ID+"="+msg_id);
    }


    public int GetReadStatus(String contactnumber){
        int checkInboxorSent=0;

        String select_pass = "SELECT " +
                database.Column_PASSWORD +
                " FROM " + database.Table_Name +
                " WHERE " + database.Column_NUMBER + "="+"'"+contactnumber +"'"
                +" AND "+database.Column_TYPE+"='INBOX'"+" ORDER BY "+database.Column_ID+" DESC ";
        Cursor cursor = db.rawQuery(select_pass, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            checkInboxorSent= cursor.getInt(cursor.getColumnIndex(database.Column_PASSWORD));
        }

        return checkInboxorSent;
    }

    public void ChangeReadStatus(String number) {

        String change_pass = "UPDATE " + database.Table_Name + " SET " + database.Column_PASSWORD + " =0" + " WHERE " +
                database.Column_NUMBER + "=" + "'" + number + "'" +" AND " + database.Column_TYPE + " ='INBOX' " + " AND " + database.Column_PASSWORD + " =1";
        Cursor cursor = db.rawQuery(change_pass, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
    }
    public String ProcessNumber(String rawNumber)
    {
        int n = 0;
        for (; n < rawNumber.length(); n++) if (rawNumber.charAt(n) == '0') break;
        rawNumber = "+88" + rawNumber.substring(n, rawNumber.length());
        rawNumber=rawNumber.replace("-","");
        rawNumber=rawNumber.replace(" ","");
        rawNumber = rawNumber.substring(0, 8) + "-" + rawNumber.substring(8, rawNumber.length());
        return rawNumber;
    }
    /*public List<String[]> getAllMsgDetails() {
        List<String[]> allMsgDetails = new ArrayList<>();

        String select_number_query="SELECT "+database.Column_NUMBER+" FROM "+database.Table_Name;
        Cursor cursor = db.rawQuery(select_number_query, null);
        // select * from books;
        if (cursor != null && cursor.getCount() > 0) {
            int size = cursor.getCount();
            String[] md=new String[size];
            cursor.moveToFirst();
            for (int i = 0; i < size; i++) {
                String number = cursor.getString(cursor.getColumnIndex(database.Column_NUMBER));
                *//*String texts = cursor.getString(cursor.getColumnIndex(database.Column_TEXTBODY));
                String type = cursor.getString(cursor.getColumnIndex(database.Column_TYPE));
                Integer password = cursor.getColumnIndex(database.Column_PASSWORD);
                String date = cursor.getString(cursor.getColumnIndex(database.Column_Date));*//*
                md[i]=number;
                *//*md[1]=texts;
                md[2]=type;
                md[3]=password.toString();
                md[4]=date;*//*
                cursor.moveToNext();
            }
            allMsgDetails.add(md);

        }

        cursor.close();
        return allMsgDetails;

    }*/
}
