package zergtel.core.downloader;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;


/**
 * Created by Simon on 11/1/2016.
 *
 * This class handles downloads from BandCamp
 */

public class Bandcamp {

    public static String get(URL url) throws Exception {
        File tmp;
        tmp = new File(EzHttp.get(url.toString(), "bandcamp.tmp", EzHttp.TEMP_LOCATION));
        String[] downloadedFiles = extractFiles(tmp);
        return getTitle(tmp);
    }

    public static String get(String url) throws Exception{
        return get(new URL(url));
    }

    /**
     * Extracts and downloads all songs from a given bandcamp page
     *
     * To download from bandcamp:
     * In the script sections of the html of any album/track page, there will be a JSON object in the form of var TralbumData -
     * TralbumData['trackinfo'] is a list of JSONs, where each element corresponds to one track on the page
     * TralbumData['trackinfo'][n]['title'] contains the title of the nth track, and
     * TralbumData['trackinfo'][n]['file'] contains a json with available download links for that track
     * Normally, the only available format to download is mp3-128. However, sometimes we are lucky and it's a free download by default -
     * in that case, we have things like flac and mp3-320 to choose from.
     * As of right now though, this class downloads mp3-128 only - we will have to add additional format detection later
     *
     * @param file - Html file of bandcamp page
     * @return - Array of names of songs found on the bandcamp page
     */
    private static String[] extractFiles(File file) {
        //such a good debug tool for json shenanigans: http://jsonviewer.stack.hu/
        String rawJson = extractLine(file, "poppler");
//        System.out.println(rawJson);

        //preprocessing json
        rawJson = "{".concat(rawJson.trim());
        rawJson = rawJson.substring(0, rawJson.length() - 2).concat("]}");


        JsonParser parser = new JsonParser();
        JsonArray mediaList = ((JsonObject) parser
                .parse(rawJson))
                .getAsJsonArray("trackinfo");

        String folder = getTitle(file);
        System.out.println("Folder: " + folder);

        String[] output = new String[mediaList.size()];
        for (int i = 0; i < mediaList.size(); i++) {
            JsonObject media = mediaList
                    .get(i)
                    .getAsJsonObject();

            String downloadLink = "http:".concat(
                    media
                            .get("file")
                            .getAsJsonObject()
                            .get("mp3-128")
                            .toString()
                            .replaceAll("\"", "")
            );

            String mediaName = media
                    .get("title")
                    .toString()
                    .concat(".mp3");

            try {
                System.out.println(mediaName + " - " + downloadLink);
                System.out.println(folder);
                System.out.println(EzHttp.getDownloadLocation());
                EzHttp.get(downloadLink, mediaName, EzHttp.getDownloadLocation() + folder);
                output[i] = folder + "\\" + mediaName;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return output;
    }

    /**
     * Gets the first line within file that contains str
     *
     * @param file - File to scan
     * @param str - String to scan for
     * @return - Line of file where str is found (the actual line, not the line number)
     */
    private static String extractLine(File file, String str) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.contains(str)) {
                    System.out.println(line);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("doh");
            e.printStackTrace();
            return "";
        }
        return line;
    }

    /**
     * Gets title of html page
     *
     * @param http - html file
     * @return - Title of html file
     */
    private static String getTitle(File http) {
        return extractLine(http, "<title>").trim().replaceAll("<(.*?)>|\\\\|\\/", "");
    }
}
