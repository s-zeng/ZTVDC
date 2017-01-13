package zergtel.core.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Merge {
    public Process app;
    public String cmd, directory, name, format;
    public File tempFile, file1, file2;
    public final static File FILE_FFMPEG = new File("./ffmpeg.exe");
    public void merge(File f1, File f2, String d, String n, String fn) {
        try {
            file1 = f1;
            file2 = f2;
            directory = d + "/";
            name = n;
            format = fn;
            cmd = FILE_FFMPEG.getAbsolutePath() + " -i " + f1.getAbsolutePath() + " -i " + f2.getAbsolutePath() + " -c:v copy -c:a copy " + d + n + "." + fn;
            Runtime convert = Runtime.getRuntime();
            app = convert.exec(cmd);
            BufferedReader appReader = new BufferedReader(new InputStreamReader(app.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Working!");
    }
    public void cancel()
    {
        app.destroy();
        tempFile = new File(directory + name + "." + format);
        tempFile.delete();
    }
}
