package test.ru.convertermoney;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import test.ru.convertermoney.model.ValCurs;

/**
 * Created by User on 28.06.2017.
 */

public class DownloadMoneyTask  extends AsyncTask<String, Integer, String> {


    String xml_money = "";
    private ValCurs valCurs;

    Downloadcallback downloadcallback;

    public DownloadMoneyTask(Downloadcallback downloadcallback) {
        this.downloadcallback = downloadcallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        downloadcallback.onBeginLoad();
    }

    @Override
    protected void onPostExecute(String integer) {
        super.onPostExecute(integer);
        downloadcallback.onEndDownload();
        downloadcallback.onDownload(xml_money);
    }

    @Override
    protected String doInBackground(String... parameter) {
        try {

            URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");

            URLConnection con = url.openConnection();
            con.setConnectTimeout(3000);
            con.setReadTimeout(3000);
            InputStream in2 = con.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    in2,
                    Charset.forName("windows-1251")
            ));
            String inputLine = "";
            StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            xml_money = sb.toString();
            in.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        return xml_money;
    }
}
