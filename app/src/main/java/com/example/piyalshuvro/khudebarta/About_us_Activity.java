package com.example.piyalshuvro.khudebarta;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Pial-PC on 9/2/2015.
 */
public class About_us_Activity extends ActionBarActivity {
    LinearLayout animationViwer;
    ImageButton closeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        themeUtils.changeToTheme(About_us_Activity.this, pref.getInt("Theme", 0));*/
        setContentView(R.layout.about_us);
        getSupportActionBar().hide();
        closeButton=(ImageButton)findViewById(R.id.close_activity_button);
        animationViwer=(LinearLayout)findViewById(R.id.Animation);

        Animation translatebu= AnimationUtils.loadAnimation(this, R.anim.animation);
        animationViwer.startAnimation(translatebu);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
