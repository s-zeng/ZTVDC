package zergtel.core.downloader;

import com.github.axet.vget.VGet;
import com.github.axet.vget.info.VideoFileInfo;
import zergtel.core.converter.Converter;
import zergtel.core.converter.Merge;

import java.io.File;
import java.net.URL;
import java.util.List;

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
        List<VideoFileInfo> info = axetGetter.getVideo().getInfo();
        int files = info.size();
        System.out.println(files);
        System.out.println(info.get(0).getTarget().getAbsolutePath());
        String title = axetGetter.getVideo().getTitle();

        if (files == 1) {
            Converter converter = new Converter();
            converter.convert(info.get(0).getTarget().getAbsoluteFile(), EzHttp.getDownloadLocation(), title + ".mp4");
        } else if (files == 2) {
            Merge merger = new Merge();
            merger.merge(
                    info.get(0).getTarget().getAbsoluteFile(),
                    info.get(1).getTarget().getAbsoluteFile(),
                    new File(EzHttp.getDownloadLocation()).getAbsolutePath(),
                    EzHttp.cleanseName(title) + ".mp4"
            );
        }

        return EzHttp.TEMP_LOCATION + "/" + axetGetter.getVideo().getTitle();
    }
}
