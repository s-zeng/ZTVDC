package zergtel.core.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class Converter extends Thread
{
    Process app;
    File file;
    String name, format;
    public void convert(File f, String n, String fn)
    {
        file = f;
        name = n;
        format = fn;
        try {
            String cmd = "C:/Users/User/IdeaProjects/summative/Summative/bin/FFMPEG/bin/ffmpeg -i " + f.getAbsolutePath() + " " + n + "." + fn;
            Runtime convert = Runtime.getRuntime();
            app = convert.exec(cmd);
            BufferedReader appReader = new BufferedReader(new InputStreamReader(app.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Completed!");
    }
    Thread th = new Thread(new Runnable() {
        @Override
        public void run() {
        convert(file, name, format);
        }
    });


    public void cancel() { th.destroy(); }
    public void pause() { th.stop(); }
    public void restart() { th.resume(); }

    //http://stackoverflow.com/questions/17123118/how-to-stop-ffmpeg-that-runs-through-java-process
    //http://stackoverflow.com/questions/10927718/how-to-read-ffmpeg-response-from-java-and-use-it-to-create-a-progress-bar
	//https://docs.oracle.com/javase/tutorial/sound/converters.html
	//JAVE Library http://www.sauronsoftware.it/projects/jave/index.php
	//https://github.com/Rebant/Video-Converter/blob/master/videoConverter.java
    //https://github.com/wseemann/FFmpegMediaMetadataRetriever
}

