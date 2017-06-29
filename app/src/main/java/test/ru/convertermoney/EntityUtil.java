package test.ru.convertermoney;

import android.util.Log;

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;

import test.ru.convertermoney.model.ValCurs;

/**
 * Created by User on 28.06.2017.
 */

public class EntityUtil {


    public static ValCurs getValCurs(String xml) {
        ValCurs valCurs = null;
        Reader reader = new StringReader(xml);
        Persister serializer = new Persister();
        try
        {
            valCurs = serializer.read(ValCurs.class, reader, false);
            Log.v("SimpleTest", "vals size is : " + valCurs.valutes.size());
        }
        catch (Exception e)
        {
            Log.e("SimpleTest", e.getMessage());
        }

        return valCurs;
    }
}
