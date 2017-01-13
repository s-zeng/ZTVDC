package zergtel.core.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class Converter {
    public Process app;
    public String cmd, directory, name;
    public File file, tempFile;
    public final static File FILE_FFMPEG = new File("./ffmpeg.exe");
    public void convert(File f, String d, String n) {
        try {
            file = f;
            directory = d + "\\";
            name = n;
            cmd = FILE_FFMPEG.getAbsolutePath() + " -i " + file.getAbsolutePath() + " " + directory + name;
            System.out.println(cmd);
            Runtime convert = Runtime.getRuntime();
            app = convert.exec(cmd);
            BufferedReader appReader = new BufferedReader(new InputStreamReader(app.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Working!");
    }

    public static void main(String args[])
    {
        Converter c = new Converter();
        File file1 = new File("C:/Users/User/IdeaProjects/summative/Summative/bin/file.mp4");
        c.convert(file1, "C:/Users/User/IdeaProjects/summative/Summative/", "test.mp4");
    }

    public void cancel()
    {
        app.destroy();
        tempFile = new File(directory + name);
        System.out.println(tempFile.getAbsolutePath());
    }

    //http://stackoverflow.com/questions/17123118/how-to-stop-ffmpeg-that-runs-through-java-process
    //http://stackoverflow.com/questions/10927718/how-to-read-ffmpeg-response-from-java-and-use-it-to-create-a-progress-bar
}

