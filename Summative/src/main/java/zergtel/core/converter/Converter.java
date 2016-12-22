package zergtel.core.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class Converter {
    public Process app;
    public String cmd, directory, name, format;
    public File file;

    public void convert(File f, String d, String n, String fn) {
        try {
            file = f;
            directory = d;
            name = n;
            format = fn;
            cmd = "C:/Users/User/IdeaProjects/summative/Summative/bin/FFMPEG/bin/ffmpeg -i " + f.getAbsolutePath() + " " + d + n + "." + fn;
            Runtime convert = Runtime.getRuntime();
            app = convert.exec(cmd);
            BufferedReader appReader = new BufferedReader(new InputStreamReader(app.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Working!");
    }

    //http://stackoverflow.com/questions/17123118/how-to-stop-ffmpeg-that-runs-through-java-process
    //http://stackoverflow.com/questions/10927718/how-to-read-ffmpeg-response-from-java-and-use-it-to-create-a-progress-bar
}

