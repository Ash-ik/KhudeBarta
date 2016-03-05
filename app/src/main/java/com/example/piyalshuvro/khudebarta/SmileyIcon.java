package com.example.piyalshuvro.khudebarta;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;

import java.io.InputStream;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by Pial-PC on 11/15/2015.
 */
public class SmileyIcon {

    private Context context;
    private static final int NO_OF_EMOTICONS = 54;
    private Bitmap[] emoticons=new Bitmap[NO_OF_EMOTICONS];

    public SmileyIcon(Context context) {
        this.context=context;
        readEmoticons();
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
        AssetManager mngr = context.getAssets();
        InputStream in = null;
        try {
            in = mngr.open("emoticons/" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap temp = BitmapFactory.decodeStream(in, null, null);
        return temp;
    }

    public SpannableStringBuilder convertToEmoticon(String text)
    {
        SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder(text);
        int i,len=text.length()-4;
        for(i=0;i<len;i++)
        {
            if(text.charAt(i)=='<' &&text.charAt(i+1)=='&' &&text.charAt(i+4)=='>')
            {
                final Integer index=(text.charAt(i+2)-'0')*10+(text.charAt(i+3)-'0');
                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                    public Drawable getDrawable(String source) {
                        Drawable d = new BitmapDrawable(context.getResources(),emoticons[index - 1]);
                        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                        return d;
                    }
                };

                Drawable x=imageGetter.getDrawable(text+".png");
                Spanned cs = Html.fromHtml("<img src ='" + index + "'/>", imageGetter, null);
                spannableStringBuilder.setSpan(new ImageSpan(x),i,i+5,Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
        return spannableStringBuilder;
    }
}
