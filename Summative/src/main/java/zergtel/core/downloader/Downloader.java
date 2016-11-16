package zergtel.core.downloader;


import java.net.URI;
import java.util.Arrays;

public class Downloader {
	//Some inspirational code:
	//https://sourceforge.net/p/ytd2/code/HEAD/tree/trunk/ytd2/src/main/java/zsk/YTDownloadThread.java 
	//https://github.com/ytsdk/ytsdk
	//http://stackoverflow.com/questions/4032766/how-to-download-videos-from-youtube-on-java
    //http://superuser.com/questions/773719/how-do-all-of-these-save-video-from-youtube-services-work?noredirect=1&lq=1

    public static boolean get(String uri) throws Exception {
        return get(new URI(uri));
    }

    private static boolean get(URI uri) throws Exception {
        String[] hosts = uri.getHost().split("\\.");

        String host = hosts[hosts.length - 2] + "." + hosts[hosts.length-1];
        String path = uri.getPath();

        System.out.println(host);
        System.out.println(path);

        if (path.contains(".")) {
            EzHttp.get(uri.toString());
        }


        return false;
    }
}
