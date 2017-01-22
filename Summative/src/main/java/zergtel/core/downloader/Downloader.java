package zergtel.core.downloader;


import java.net.URL;

/**
 * The primary interface through which the rest of ZTVDC downloads videos
 * This class simply parses a url, detects what site it's from, and passes it to the appropriate class.
 */
public class Downloader {
	//Some inspirational sources:
	//https://sourceforge.net/p/ytd2/code/HEAD/tree/trunk/ytd2/src/main/java/zsk/YTDownloadThread.java 
	//https://github.com/ytsdk/ytsdk
	//http://stackoverflow.com/questions/4032766/how-to-download-videos-from-youtube-on-java
    //http://superuser.com/questions/773719/how-do-all-of-these-save-video-from-youtube-services-work?noredirect=1&lq=1

    /**
     * Downloads the resource at the requested url
     *
     * @param uri - A url, in the form of a string
     * @return - See get(URL uri)
     * @throws Exception - See get(URL uri)
     */
    public static String get(String uri) throws Exception {
        return get(new URL(uri));
    }

    /**
     * Downloads the resource at the requested url
     *
     * @param uri - A url as a java.net URL
     * @return - Currently assorted returns, and not used for anything at the moment. Future maintainers may wish to
     *           make something more coherent out of all the returns, such as returning title or file location.
     * @throws Exception - this should be changed to a much more specific exception, because atm this is one of the main reasons
     *                     that cancelling in zergtel.ui.ComputerUI is as spaghetti as it is
     */
    private static String get(URL uri) throws Exception {
        String[] hosts = uri.getHost().split("\\.");
	    String output = null;

        String host = hosts[hosts.length - 2] + "." + hosts[hosts.length-1];
        String path = uri.getPath();

        System.out.println(host);
        System.out.println(path);

        if (path.contains(".")) {
            output = EzHttp.get(uri);
        }

        String id;
        switch (host) {
	        case "bandcamp.com":
	        	output = Bandcamp.get(uri);
		        break;
            case "youtu.be":
                id = path.substring(1);
                uri = new URL("https://youtube.com/watch?v=" + id); //Only if the youtu.be url is simple! Does not work with feature=youtu.be, etc right now
            case "youtube.com":
//	        case "vimeo.com":
                System.out.println("Vget: " + uri);
                if (path.split("/")[1].equals("embed")) {
                    id = path.split("/")[2];
                    uri = new URL("https://youtube.com/watch?v=" + id);
                }
		        output = VGetInterface.get(uri);
		        break;
        }
        return output;
    }
}
