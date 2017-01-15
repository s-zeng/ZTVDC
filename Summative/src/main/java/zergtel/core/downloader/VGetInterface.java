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
        File temp = new File(EzHttp.TEMP_LOCATION);
        temp.mkdir();
        VGet axetGetter = new VGet(url, temp);
        axetGetter.download();
        System.out.println("YT source: " + axetGetter.getVideo().getSource().toString());
        System.out.println(axetGetter.getVideo().getInfo().size());

        return EzHttp.TEMP_LOCATION + "/" + axetGetter.getVideo().getTitle();
    }
}
