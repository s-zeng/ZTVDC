package zergtel.core.downloader;



/**
 * Created by Simon on 11/1/2016.
 */
public class Bandcamp {
    public static String get(String url) {
        try {
            EzHttp.get(url, "bandcamp.tmp");
        } catch (Exception e) {
            System.err.println("Invalid url");
            return;
        }
        return "";
    }
}
