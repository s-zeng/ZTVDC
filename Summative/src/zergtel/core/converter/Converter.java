package zergtel.core.converter;

import java.io.*;


public class Converter 
{
    public File convert(File f)
    {
        try {
            int char1 = f.getName().indexOf(0);
            int char2 = f.getName().indexOf(".");
            String outFile = "", format = "";
            outFile = outFile.substring(char1, char2);
            format = format.substring(char2);
            Runtime convert = Runtime.getRuntime();
            Process app = convert.exec("ffmpeg -i " + f.getName() + " " + outFile + format);
            BufferedReader appReader = new BufferedReader(new InputStreamReader(app.getInputStream()));
            String test;

            while ((test = appReader.readLine()) != null)
            {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }
	//https://docs.oracle.com/javase/tutorial/sound/converters.html
	//JAVE Library http://www.sauronsoftware.it/projects/jave/index.php
	//Stuff to experiment with: Joba Time, Java Media Framework API (JMF)
	//https://github.com/Rebant/Video-Converter/blob/master/videoConverter.java
	//https://www.ffmpeg.org/
}
