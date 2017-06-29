package test.ru.convertermoney;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import test.ru.convertermoney.model.ValCurs;

/**
 * Created by User on 28.06.2017.
 */

public class Cache {


    static String PATH_CACHE = "cache";

    public static void write(Context context, ValCurs valCurs ){
        File outputFile = new File(context.getFilesDir(), PATH_CACHE);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(outputFile));
            oos.writeObject(valCurs);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  ValCurs read(Context context){
        File outputFile = new File(context.getFilesDir(), PATH_CACHE);
        ValCurs var = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(outputFile));
            var = (ValCurs) ois.readObject();
            ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return var;
    }
}
