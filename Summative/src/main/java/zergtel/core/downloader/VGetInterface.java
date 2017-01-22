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
 *
 * Interface with axet's vget library, which can be found here: https://github.com/axet/vget
 * vget is our library for Youtube downloads. They normally support vimeo to, but that's broken at the moment.
 * See their github page for more
 *
 * Note that if you download a video with VGetInterface, and then download something else to a different directory,
 * the output from VGetInterface will go to the new directory, rather than the directory originally specified. This could
 * probably be easily fixed by storing the directory that the user wants in the constructor, or perhaps by accessing the
 * directory directly from EzHttp at the start of the download, rather than the end.
 */
public class VGetInterface {

    public static String get(String uri) throws Exception {
        return get(new URL(uri));
    }

    /**
     * Gets a youtube video at the given url
     *
     * @param url - Youtube url
     * @return - Title of the video
     * @throws Exception - We really need to generalize these exceptions
     */
    public static String get(URL url) throws Exception {
        //initates a VGet object with the temp directory, creating it if necessary
        File temp = new File(EzHttp.TEMP_LOCATION);
        temp.mkdir();
        VGet axetGetter = new VGet(url, temp);

        axetGetter.download(); //starts the download
        System.out.println("YT source: " + axetGetter.getVideo().getSource().toString()); //debug print

        //Determine whether one or two files were downloaded
        List<VideoFileInfo> info = axetGetter.getVideo().getInfo();
        int files = info.size();

        //more debug printouts
        System.out.println(files);
        System.out.println(info.get(0).getTarget().getAbsolutePath());

        String title = axetGetter.getVideo().getTitle(); //title of video

        /**
         * By default, vget downloads either one or two files - either a webm with both video and audio in it for less
         * secure videos, or seperate video and audio streams as an mp4 and webm file respectively.
         *
         * The following code block processes these files based on how many were downloaded, and packages them into a
         * nice single mp4 file in the actual download directory that the user specified.
         */
        if (files == 1) {
            Converter converter = new Converter();
            converter.convert(info.get(0).getTarget().getAbsoluteFile(), EzHttp.getDownloadLocation(), EzHttp.cleanseName(title) + ".mp4");
        } else if (files == 2) {
            Merge merger = new Merge();
            merger.merge(
                    info.get(0).getTarget().getAbsoluteFile(),
                    info.get(1).getTarget().getAbsoluteFile(),
                    new File(EzHttp.getDownloadLocation()).getAbsolutePath(),
                    EzHttp.cleanseName(title) + ".mp4"
            );
        }

        return title;
    }
}
