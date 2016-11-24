package zergtel.core.converter;

import sun.security.util.SecurityConstants;

import java.io.*;


public class Converter 
{
    Process app;
    public void convert(File f, String n, String fn)
    {
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

    public void cancel() { app.destroy(); }
    public void merge(File f1, File f2, String n, String fn)
    {
        try {
            String cmd = "C:/Users/User/IdeaProjects/summative/Summative/bin/FFMPEG/bin/ffmpeg -i " + f1.getAbsolutePath() + " -i " + f2.getAbsolutePath() + " -c:v copy -c:a copy " + n + "." + fn;
            Runtime convert = Runtime.getRuntime();
            app = convert.exec(cmd);
            BufferedReader appReader = new BufferedReader(new InputStreamReader(app.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Completed");

    }

    //http://stackoverflow.com/questions/17123118/how-to-stop-ffmpeg-that-runs-through-java-process
    //http://stackoverflow.com/questions/10927718/how-to-read-ffmpeg-response-from-java-and-use-it-to-create-a-progress-bar
	//https://docs.oracle.com/javase/tutorial/sound/converters.html
	//JAVE Library http://www.sauronsoftware.it/projects/jave/index.php
	//Stuff to experiment with: Joba Time, Java Media Framework API (JMF)
	//https://github.com/Rebant/Video-Converter/blob/master/videoConverter.java
	//https://www.ffmpeg.org/
    //https://github.com/wseemann/FFmpegMediaMetadataRetriever
}
