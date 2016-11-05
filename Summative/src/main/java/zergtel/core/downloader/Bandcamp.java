package zergtel.core.downloader;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Simon on 11/1/2016.
 */
public class Bandcamp {
    public static String get(String url) {
        File tmp;
        try {
            tmp = new File(EzHttp.get(url, "bandcamp.tmp"));
        } catch (Exception e) {
            System.err.println("Invalid url");
            return "";
        }
        System.out.println("before");
        String[] temp = extractFiles(tmp);
        System.out.println("after");

        return "";
    }

    private static String[] extractFiles(File file) {
        //such a good debug tool for json shenanigans: http://jsonviewer.stack.hu/
        JsonParser parser = new JsonParser();
        JsonArray mediaList = ((JsonObject)parser
                .parse(extractString(file)))
                .getAsJsonArray("trackinfo");

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

            String mediaName = media.get("title").toString().concat(".mp3");

            try {
                System.out.println(mediaName + " - " + downloadLink);
                EzHttp.get(downloadLink, mediaName);
            } catch (Exception e) {
                System.err.println("wtf");
            }

        }


        return new String[5];
    }

    private static String extractString(File file) {
        System.out.println(file.getName());
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.contains("poppler")) {
                    System.out.println(line.length());

                    //this is for preprocessing of the json
//                    line = line.trim().substring(11);
                    line = "{".concat(line.trim());
                    line = line.substring(0, line.length() - 2).concat("]}");


                    System.out.println(line);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("doh");
            return "";
        }
        return line;
    }
}
