package fi.jessestenroth.koskaelokuvat;

import androidx.appcompat.app.AppCompatActivity;
import fi.jessestenroth.koskaelokuvat.data.functions.SavingFeature;
import fi.jessestenroth.koskaelokuvat.data.finnkinoapi.FinnkinoAPIGetterSettings;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.Locale;

/**
 * This activity is for app Settings. There user can change settings
 * @author Jesse Stenroth
 */
public class SettingsActivity extends AppCompatActivity implements FinnkinoAPIGetterSettings.getDataToSettings {
    //hold app language options
    private Spinner lang;
    //hold areas options
    private Spinner area;
    //save functions
    private SavingFeature save;
    //gps on/off
    private Switch select;
    //because area listener call functions also when spinner is created
    private boolean canSaveArea = false;
    //because listener call also when spinner create
    private boolean canRun = false;
    //debug feature
    private boolean debug = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.save = new SavingFeature(this);
        this.lang = (Spinner) findViewById(R.id.languageSelector);
        this.area = (Spinner) findViewById(R.id.areas_settings);
        this.select = (Switch) findViewById(R.id.switch1);

        //get data of area options
        FinnkinoAPIGetterSettings xml = new FinnkinoAPIGetterSettings(area, this, this);
        xml.getAreas();
        this.lang.setSelection(-1);
        this.lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(canRun){
                    String value = lang.getSelectedItem().toString();
                    if(debug){
                        Log.e("value", value);
                    }
                    if(value.equals("Finnish") || value.equals("Suomi")){
                        setLang("fi");
                    } else if(value.equals("System") || value.equals("Järjestelmä")){
                        //this set language same as device
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            if (debug) {
                                Log.e("locale", Resources.getSystem().getConfiguration().getLocales().get(0).getLanguage());

                            }
                            setLang(Resources.getSystem().getConfiguration().getLocales().get(0).getLanguage());
                        } else {
                            if (debug) {
                                Log.e("locale", Resources.getSystem().getConfiguration().locale.getLanguage());
                            }
                            setLang(Resources.getSystem().getConfiguration().locale.getLanguage());
                        }
                    } else{
                        setLang("en");
                    }
                } else{
                    canRun = true;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        //switch element
        this.select.setChecked(save.getBoolean("gps"));
        this.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    save.saveBoolean("gps", true);
                } else {
                    // The toggle is disabled
                    save.saveBoolean("gps", false);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //view can update
        save.saveBoolean("update", true);
    }

    /**
     * This method set language for app. ( example 'en' means app language is english)
     * @param l short version of language what app use
     */
    private void setLang(String l){
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(l.toLowerCase()));
        } else {
            config.locale = new Locale(l.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
        getApplicationContext().getResources().updateConfiguration(config, dm);
        startActivity(getIntent());
        finish();
    }

    @Override
    public void setArea(String name, String code) {
        if(canSaveArea) {
            if (debug) {
                Log.e("area", "name: " + name + " code: " + code);
            }
            save.saveArea(name, code);
        } else{
            canSaveArea = true;
        }
    }
}
