package zergtel.core.downloader;


import com.google.gson.Gson;

import java.io.File;
import java.io.*;

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
        String json = extractString(file);
        Gson gson = new Gson();
        gson.fromJson(json);
        return new String[5];
    }

    private static String extractString(File file) {
        System.out.println(file.getName());
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (line.contains("poppler")) {
                    line = "{".concat(line.trim()).concat("}");
                    System.out.println(line);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("doh");
            return "";
        }
        return line;
    }
}
