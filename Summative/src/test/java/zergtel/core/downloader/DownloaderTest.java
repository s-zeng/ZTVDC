package zergtel.core.downloader;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import java.util.Arrays;

/**
 * Created by Simon on 11/22/2016.
 */
public class DownloaderTest {
    /**
     * things left to implement (caps means emphasis):
     * design of extension system
     *  -this extension system must:
     *      -be versatile enough for most sites
     *      -be secure (NOT ALLOW ARBITRARY CODE EXECUTION)
     *      -allow drag and drop functionality of adding extensions
     * THREADING
     * proper file location/name handling
     * vimeo fix, integration of vget library for greater changes
     * 	-notably, replacing axet's wget library with my own http client
     * error handling paradigm design
     * imports cleanup
     * filetype checking for raw http downloads (overall just more comprehensive url parsing)
     * temp file handling and cleanup
     * bandcamp additional quality/format detection
     * automated testing
     * metadata getting from youtube!
     *
     * ...and maybe then we can start on the ui :)
     */

    @DataProvider(name = "links")
    public static Object[][] getLinks() {
        return new Object[][] {
            {"https://insaneintherainmusic.bandcamp.com/album/live-at-grillbys"},
            {"http://simonzeng.tk/example.mp3"},
            {"https://www.youtube.com/watch?v=f4yvZF1cMz0"},
            {"https://vimeo.com/29950141"},
            {"https://www.youtube.com/watch?v=4zLfCnGVeL4"}
        };
    }


    @Test(dataProvider = "links")
    public void testGet(String link) throws Exception {
        System.out.println("Link test: " + link);
        String downLocation = Downloader.get(link);
        String[] paths = downLocation.split("\\/");
        Arrays.toString(paths);

        Assert.assertTrue(true);

        System.out.println("Test success");
    }

}