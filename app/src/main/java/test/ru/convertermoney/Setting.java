package test.ru.convertermoney;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Dmitry on 01.03.2016.
 */
public class Setting {
    public static final String APP_PREFERENCES = "mysettings3";

    private static  SharedPreferences mSettings;
    private static Context c;

    public static SharedPreferences init(Context con){
        c = con;
        return mSettings = c.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static String getString( String key){
        return  mSettings.getString(key,"");
    }

    public static void setString(String key, String value){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(key, value);
        editor.apply();
    }



}
