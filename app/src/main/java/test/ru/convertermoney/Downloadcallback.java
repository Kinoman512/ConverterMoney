package test.ru.convertermoney;

/**
 * Created by User on 28.06.2017.
 */
public interface Downloadcallback {

    void onDownload(String xml_money);

    void onEndDownload();

    void onBeginLoad();

}
