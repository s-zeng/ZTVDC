package zergtel.core.downloader;


import java.net.URL;


public class Downloader {
	//Some inspirational code:
	//https://sourceforge.net/p/ytd2/code/HEAD/tree/trunk/ytd2/src/main/java/zsk/YTDownloadThread.java 
	//https://github.com/ytsdk/ytsdk
	//http://stackoverflow.com/questions/4032766/how-to-download-videos-from-youtube-on-java
    //http://superuser.com/questions/773719/how-do-all-of-these-save-video-from-youtube-services-work?noredirect=1&lq=1

    public static String get(String uri) throws Exception {
        return get(new URL(uri));
    }

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
