package zergtel.core.downloader;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;


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
//     * -automated testing- done
     * metadata getting from youtube!
     *
     * ...and maybe then we can start on the ui :)
     */

    /**
    Week of 11/21 topics to write about:
     Set up automated testing
        -TestNG
        -lack of ci :(
        -or maybe circle ci...?
     downloader get output standardizing
     comparison testing
     code coverage
     vget interface + direct urls
        -next step: integrating with EzHttp
            -will have to find audio stream url however
     future fork to android
     getting shyam to include comprehensive test suite
     include:
        https://github.com/kronicmage/summative/commits/master
        html attachment of light and full test output of downloader
     */

    @DataProvider(name = "links")
    public static Object[][] getLinks(ITestContext context) {
        Object[][] fullList = new Object[][] {
            {"https://insaneintherainmusic.bandcamp.com/album/live-at-grillbys"},
            {"http://souleyedigitalmusic.bandcamp.com/album/extreme-road-trip-ost"},
            {"http://simonzeng.tk/example.mp3"},
            {"https://www.youtube.com/watch?v=f4yvZF1cMz0"},
            {"https://vimeo.com/29950141"},
            {"https://www.youtube.com/watch?v=4zLfCnGVeL4"},
            {"https://www.youtube.com/watch?v=dQw4w9WgXcQ"}
        };
        Object[][] lightList = new Object[][] {
            {"http://souleyedigitalmusic.bandcamp.com/album/extreme-road-trip-ost"},
            {"http://simonzeng.tk/example.mp3"},
            {"https://www.youtube.com/watch?v=f4yvZF1cMz0"}
        };

        List<String> includedGroups = Arrays.asList(context.getIncludedGroups());

        if(includedGroups.contains("downloadLight")) {
            return lightList;
        }
        else if (includedGroups.contains("downloadFull")) {
            return fullList;
        }

        return null;
    }

    @BeforeGroups(groups = {"downloadLight", "downloadFull"})
    public static void clearDownloads() {
        File downloadDir = new File(EzHttp.getDownloadLocation());
        String[]entries = downloadDir.list();
        System.out.println("blah");
        for(String s: entries != null ? entries : new String[0]){
            File currentFile = new File(downloadDir.getPath(),s);
            currentFile.delete();
        }
    }

    @Test(dataProvider = "links", groups = {"downloadLight", "downloadFull"})
    public void testGet(String link) throws Exception {
        System.out.println("Link test: " + link);
        String downLocation = Downloader.get(link);
        String[] paths = downLocation.split("\\/|\\\\");
        System.out.println(Arrays.toString(paths));

        Assert.assertTrue(true);

        System.out.println("Test success");
    }

}