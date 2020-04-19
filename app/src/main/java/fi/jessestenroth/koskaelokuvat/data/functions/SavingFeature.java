package fi.jessestenroth.koskaelokuvat.data.functions;

import android.content.Context;
import android.content.SharedPreferences;

public class SavingFeature {

    private static final String pref = "saving";
    private Context context;
    private boolean debuggi = true;

    public SavingFeature(Context context){
        this.context = context;
    }

    public void saveBoolean(String key, boolean value){
        SharedPreferences settings = context.getSharedPreferences(pref, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public boolean getBoolean(String key){
        SharedPreferences settings = context.getSharedPreferences(pref, 0);
        return settings.getBoolean(key, false);
    }
    public void saveString(String key, String value){
        SharedPreferences settings = context.getSharedPreferences(pref, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String getStringValue(String key){
        SharedPreferences settings = context.getSharedPreferences(pref, 0);
        return settings.getString(key, "null");
    }
    public void saveArea(String name, String code){
        if(debuggi){
            System.out.println("namr: " + name + " code: " + code);
        }
        saveString("areaName", name);
        saveString("areaCode", code);
        saveBoolean("update", true);
        saveBoolean("asetettu", true);
    }
    public String getAreaName(){
        return getStringValue("areaName");
    }
    public String getAreaCode(){
        return getStringValue("areaCode");
    }
}
