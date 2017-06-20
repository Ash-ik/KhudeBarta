package com.example.piyalshuvro.khudebarta;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Transformation;

import java.util.List;

public class CustomListView extends ArrayAdapter<String[]> {
    TimeFormatting time;
    Transformation transformation = new RoundedTransformationBuilder()
            .borderColor(Color.GREEN)
            .borderWidthDp(3)
            .cornerRadiusDp(30)
            .oval(false)
            .build();
    private List<String[]> msg;
    private Activity context;


    public CustomListView(Activity context, List<String[]> msg) {
        super(context, R.layout.thread_item, msg);
        this.msg = msg;
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*View view = convertView;
        if (view == null) {*/
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.thread_item, null, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageSender);

        TextView hiddenTextView = (TextView) view.findViewById(R.id.hiddentextView);
        TextView ContactName = (TextView) view.findViewById(R.id.textContactName);
        TextView txtBody = (TextView) view.findViewById(R.id.textContent);
        TextView txtTime = (TextView) view.findViewById(R.id.textTime);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Siyamrupali.ttf");
        txtBody.setTypeface(tf);

        DBadapter dBadapter = new DBadapter(context);


        String info[] = msg.get(position);
        ContactName.setText(info[0]);
        SmileyIcon smileyIcon=new SmileyIcon(context);
        txtBody.setText(smileyIcon.convertToEmoticon(info[1]));
        if (info[4].equalsIgnoreCase("SUCCESS")) {
            time = new TimeFormatting();
            txtTime.setText(time.showTimediff(info[2]));
        }else if (info[4].equalsIgnoreCase("FAILED")) {
            SpannableStringBuilder builder = new SpannableStringBuilder(" ");
            builder.setSpan(new ImageSpan(context, R.drawable.sms_error),
                    0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);


            txtTime.setText(builder);
        }else if(info[4].equalsIgnoreCase("SENDING")){
            txtTime.setText("Sending.....");
        }


        if (dBadapter.GetReadStatus(info[3]) == 1) {
            ContactName.setTypeface(null, Typeface.BOLD);
            txtBody.setTypeface(null, Typeface.BOLD);
            txtBody.setTextSize(15);
            txtTime.setTypeface(null, Typeface.BOLD);
        } else {
            ContactName.setTypeface(null, Typeface.NORMAL);
            txtBody.setTypeface(null, Typeface.NORMAL);
            txtBody.setTextSize(15);
            txtTime.setTypeface(null, Typeface.NORMAL);
        }

        TextView textViewDraft = (TextView) view.findViewById(R.id.textDraft);

        hiddenTextView.setText(info[3]);
        if (dBadapter.HasDraft(info[3]) == true) {
            textViewDraft.setText("(Draft)");
        } else
            textViewDraft.setText("(" + dBadapter.CoversationSize(info[3]) + ")");


        String contactID = dBadapter.getContactIdFromNumber(context, info[3]);

        if(contactID.equalsIgnoreCase(info[3]) == false)
        {
            Uri uri=dBadapter.getImageURI(contactID);
            if(uri!=null)
            {
                Glide
                        .with(context)
                        .load(uri)
                        .centerCrop()
                        .into(imageView);
            }
            else
            {
                Glide
                        .with(context)
                        .load(R.mipmap.contact_image)
                        .transform(new CircleTransform(context))
                        .thumbnail(.1f)
                        .centerCrop()
                        .into(imageView);
            }

        }
        else
        {
            Glide
                    .with(context)
                    .load(R.mipmap.contact_image)
                    .transform(new CircleTransform(context))
                    .thumbnail(.1f)
                    .centerCrop()
                    .into(imageView);
        }

 /*       if (contactID.equalsIgnoreCase(info[3]) == false) {
            Bitmap myBitmap = dBadapter.getImageBitmap(contactID);
            if (myBitmap != null) {
                myBitmap = dBadapter.getCircleBitmap(myBitmap);
                imageView.setImageBitmap(myBitmap);
            } else {
                Bitmap myBitmaps = BitmapFactory.decodeResource(context.getResources(), R.mipmap.circle_contact_image);
                Bitmap newBitmap = Bitmap.createBitmap(myBitmaps.getWidth(), myBitmaps.getHeight(), myBitmaps.getConfig());
                Canvas canvas = new Canvas(newBitmap);
                //*canvas.drawColor(0x39816);*//*
                canvas.drawBitmap(myBitmaps, 0, 0, null);
                myBitmaps = dBadapter.getCircleBitmap(newBitmap);
                imageView.setImageBitmap(myBitmaps);

            }

        } else {
            Bitmap myBitmaps = BitmapFactory.decodeResource(context.getResources(), R.mipmap.circle_contact_image);
            Bitmap newBitmap = Bitmap.createBitmap(myBitmaps.getWidth(), myBitmaps.getHeight(), myBitmaps.getConfig());
            Canvas canvas = new Canvas(newBitmap);
            //*canvas.drawColor(0x39816);*//*
            canvas.drawBitmap(myBitmaps, 0, 0, null);
            myBitmaps = dBadapter.getCircleBitmap(newBitmap);
            imageView.setImageBitmap(myBitmaps);
        }



		/*} else {


		}*/
        return view;
    }


    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        @Override public String getId() {
            return getClass().getName();
        }
    }

}
