package zergtel.core.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Merge extends Thread{
    Process app;
    File file1, file2;
    String name, format;
    public void merge(File f1, File f2, String n, String fn)
    {
        file1 = f1;
        file2 = f2;
        name = n;
        format = fn;
        try {
            String cmd = "C:/Users/User/IdeaProjects/summative/Summative/bin/FFMPEG/bin/ffmpeg -i " + f1.getAbsolutePath() + " -i " + f2.getAbsolutePath() + " -c:v copy -c:a copy " + n + "." + fn;
            Runtime convert = Runtime.getRuntime();
            app = convert.exec(cmd);
            BufferedReader appReader = new BufferedReader(new InputStreamReader(app.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Working!");

    }
    Thread th = new Thread(new Runnable() {
        @Override
        public void run() {
            merge(file1, file2, name, format);
        }
    });
    public void cancel() { th.destroy(); }
    public void pause() { th.stop(); }
    public void restart() { th.resume(); }
}
