package zergtel.core.downloader;


import java.io.File;
import java.util.Scanner;

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

        extractLinks(tmp);

        return "";
    }

    private static String[] extractLinks(File file) {
        try {
            Scanner in = new Scanner(file);

            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.contains("poppler")) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {

        }
    }
}
