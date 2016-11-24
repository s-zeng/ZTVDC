package zergtel.core.downloader;

import com.github.axet.vget.VGet;

import java.io.File;
import java.net.URL;

/**
 * Created by Simon on 11/23/2016.
 */
public class VGetInterface {

    public static String get(String uri) throws Exception {
        return get(new URL(uri));
    }

    public static String get(URL url) throws Exception {
        VGet axetGetter = new VGet(url, new File(EzHttp.getDownloadLocation()));
        axetGetter.download();
        System.out.println("YT source: " + axetGetter.getVideo().getSource().toString());


        return EzHttp.getDownloadLocation() + "/" + axetGetter.getVideo().getTitle();
    }
}
