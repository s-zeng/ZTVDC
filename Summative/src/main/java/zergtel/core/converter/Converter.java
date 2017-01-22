package zergtel.core.converter;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Converter class
 * Conversion is done by using an application called FFMPEG, which converts audio and video formats into others
 * This class uses Process in order to input certain command lines which allows FFMPEG to convert videos within Java
 */
public class Converter {
    public Process app;
    public String cmd, directory, name;
    public File file;
    private int terminated; //stores the value of the app if it successfully terminates.
    public final static File FILE_FFMPEG = new File("./ffmpeg.exe");
    //Method called in order to convert videos using ffmpeg
    public void convert(File f, String d, String n) {
        try {
            file = f;
            directory = d + "\\";
            name = n;
            new File(directory).mkdirs();
            cmd = FILE_FFMPEG.getAbsolutePath() + " -loglevel fatal" + " -i \"" + file.getAbsolutePath() + "\" \"" + directory + name + "\"";
            System.out.println(cmd);
            Runtime convert = Runtime.getRuntime();

            File output = new File(directory + name);
            System.out.println(output.getAbsolutePath());
            System.out.println("Deleted: " + output.delete());

            app = convert.exec(cmd);
            BufferedReader appReader = new BufferedReader(new InputStreamReader(app.getErrorStream()));
            try {
                app.waitFor();
                terminated = app.waitFor();
                if(terminated == 0)
                    System.out.println("WOOOOOOOOOOOOOOO!");
                else {
                    Thread.sleep(500);
                    File file = new File(directory + name);
                    if(file.exists() == false)
                        JOptionPane.showMessageDialog(null, "Conversion failed!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //returns the value of terminated, used in ComputerUI class.
    public int getTerminated() { return terminated; }

    //Link used to figure out Process and methods to run FFMPEG through java: http://stackoverflow.com/questions/17123118/how-to-stop-ffmpeg-that-runs-through-java-process
}

