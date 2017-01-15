package zergtel.core.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Merge {
    public Process app;
    public String cmd, directory, name;
    public File tempFile, file1, file2;
    public final static File FILE_FFMPEG = new File("./ffmpeg.exe");
    public void merge(File f1, File f2, String d, String n) {
        try {
            file1 = f1;
            file2 = f2;
            directory = d + "/";
            name = n;
            cmd = FILE_FFMPEG.getAbsolutePath() + " -i " + file1.getAbsolutePath() + " -i " + file2.getAbsolutePath() + " -c:v copy -c:a copy " + directory + name;
            System.out.println(cmd);
            Runtime convert = Runtime.getRuntime();
            app = convert.exec(cmd);
            BufferedReader appReader = new BufferedReader(new InputStreamReader(app.getErrorStream()));
            try {
                app.waitFor();
                final int TERMINATED = app.waitFor();
                if(TERMINATED == 0)
                    System.out.println("Completed!");
                else {
                    String line;
                    if((line = appReader.readLine()) != null)
                        System.out.println(line);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Working!");
    }
    public void cancel()
    {
        app.destroy();
        tempFile = new File(directory + name);
        tempFile.delete();
    }
}
