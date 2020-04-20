package fi.jessestenroth.koskaelokuvat.data.functions;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class save data to android device (Settings)
 * This class use android preferences
 * @author Jesse Stenroth
 */
public class SavingFeature {

    private static final String pref = "saving";
    private Context context;
    private boolean debuggi = true;

    /**
     * This constuctor set context data to class memory
     * @param context context of activity
     */
    public SavingFeature(Context context){
        this.context = context;
    }

    /**
     * This method save boolean value to device
     * @param key key of data
     * @param value value what want save
     */
    public void saveBoolean(String key, boolean value){
        SharedPreferences settings = context.getSharedPreferences(pref, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * This method return boolean value from device
     * @param key key of value
     * @return value and if value not found then return false
     */
    public boolean getBoolean(String key){
        SharedPreferences settings = context.getSharedPreferences(pref, 0);
        return settings.getBoolean(key, false);
    }

    /**
     * This method save String value to device
     * @param key key of value
     * @param value value what want save
     */
    public void saveString(String key, String value){
        SharedPreferences settings = context.getSharedPreferences(pref, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }
    /**
     * This method return String value from device
     * @param key key of value
     * @return value and if value not found then return 'null'
     */
    public String getStringValue(String key){
        SharedPreferences settings = context.getSharedPreferences(pref, 0);
        return settings.getString(key, "null");
    }

    /**
     * This method save area id code and area name to device
     * @param name area name
     * @param code area id code
     */
    public void saveArea(String name, String code){
        if(debuggi){
            System.out.println("namr: " + name + " code: " + code);
        }
        saveString("areaName", name);
        saveString("areaCode", code);
        saveBoolean("update", true);
        saveBoolean("asetettu", true);
    }

    /**
     * This method return area name from device
     * @return name of area what now use
     */
    public String getAreaName(){
        return getStringValue("areaName");
    }

    /**
     * This method return area id code from device
     * @return id code of area what now use
     */
    public String getAreaCode(){
        return getStringValue("areaCode");
    }
}
