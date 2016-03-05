package com.example.piyalshuvro.khudebarta;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Target;


public class Settings extends ActionBarActivity {

    ImageButton change_theme;
    final CharSequence[] themes = {"Light", "Dark"};
    boolean status;
    String PreferenceName = "BanglaParser";
    String PreferenceKey = "ParserStatus";
    TextView ParserStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        themeUtils.changeToTheme(Settings.this, pref.getInt("Theme", 0));*/
        setContentView(R.layout.activity_settings);
        ParserStatus = (TextView) findViewById(R.id.ParserStatus);
//        change_theme = (ImageButton) findViewById(R.id.change_theme);
        Switch BanglaParserSwitch = (Switch) findViewById(R.id.SwitchforParser);

        SharedPreferences prefer = getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        status = prefer.getBoolean(PreferenceKey, false);

        BanglaParserSwitch.setChecked(status);

        if (status) {
            ParserStatus.setText("Turn off Bangla Parser");
        } else {
            ParserStatus.setText("Turn on Bangla Parser");
        }

        BanglaParserSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences preferences = getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(PreferenceKey, b);
                editor.commit();
                if (b == true) {
                    ParserStatus.setText("Turn off Bangla Parser");
                } else if (b == false) {
                    ParserStatus.setText("Turn on Bangla Parser");
                }
            }
        });
        /*change_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogForTheme();
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.close) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /*public void showDialogForTheme(){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();


        AlertDialog.Builder alerBuilder=new AlertDialog.Builder(Settings.this)
                .setTitle("Choose theme :")
                .setSingleChoiceItems(themes, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){
                            editor.putInt("Theme", 0).commit();
                            themeUtils.onActivityCreateSetTheme(Settings.this);
                            finish();
                        }
                        else if (which == 1) {
                            editor.putInt("Theme", 1).commit();
                            themeUtils.onActivityCreateSetTheme(Settings.this);
                            finish();
                        }
                        dialog.dismiss();
                    }
                });
        AlertDialog alertdialog=alerBuilder.create();
        alertdialog.show();

    }*/

}
