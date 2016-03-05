package com.example.piyalshuvro.khudebarta;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;

/**
 * Created by Pial-PC on 9/12/2015.
 */
public class themeUtils {
    private static int cTheme;



    public static void changeToTheme(Activity activity, int theme)

    {

        cTheme = theme;

        switch (cTheme)

        {

            default:

            case 0:
                activity.setTheme(R.style.AppTheme);

                break;

            case 1:
                activity.setTheme(R.style.Theme_AppCompat);

                break;

        }


    }

    public static void onActivityCreateSetTheme(Activity activity)

    {

        switch (cTheme)

        {

            default:

            case 0:
                activity.setTheme(R.style.AppTheme);
                break;

            case 1:
                activity.setTheme(R.style.Theme_AppCompat);
                break;

        }

    }
}
