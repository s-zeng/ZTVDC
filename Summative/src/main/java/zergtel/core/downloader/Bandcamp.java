package zergtel.core.downloader;


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
        String[] temp = extractLinks(tmp);
        System.out.println("after");

        return "";
    }

    private static String[] extractLinks(File file) {
        System.out.println(file.getName());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("poppler")) {
                    System.out.println(line);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("doh");
        }
        return new String[1];
    }
}
