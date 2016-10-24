package zergtel.core.downloader;

public class Downloader {
	//Some inspirational code:
	//https://sourceforge.net/p/ytd2/code/HEAD/tree/trunk/ytd2/src/main/java/zsk/YTDownloadThread.java 
	//https://github.com/ytsdk/ytsdk
	//http://stackoverflow.com/questions/4032766/how-to-download-videos-from-youtube-on-java
    //http://superuser.com/questions/773719/how-do-all-of-these-save-video-from-youtube-services-work?noredirect=1&lq=1

    /**To download from bandcamp:
     * In the script sections of the html of any album/track page, ther will be a JSON object in the form of var TralbumData -
     * TralbumData['trackinfo'] is a list of JSONs, where each element corresponds to one track on the page
     *  TralbumData['trackinfo'][n]['title'] contains the title of the track, and
     *  TralbumData['trackinfo'][n]['file'] contains a json with available download links
     *      Normally, the only available format to download is mp3-128. However, sometimes we are lucky and it's a free download by default -
     *      in that case, we have things like flac and mp3-320 to choose from.
     */
}
