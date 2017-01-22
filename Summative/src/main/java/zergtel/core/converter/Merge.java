package zergtel.core.converter;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Merge class
 * Merging is done by using an application called FFMPEG, which merges audio and video formats together
 * This class uses Process in order to input certain command lines which allows FFMPEG to merge videos within Java
 */
public class Merge {
    public Process app;
    public String cmd, directory, name;
    public File file1, file2;
    private int terminated; //stores the value of the app if it successfully terminates.
    public final static File FILE_FFMPEG = new File("./ffmpeg.exe");
    //Method called to merge video and audio clips together
    public void merge(File f1, File f2, String d, String n) {
        try {
            file1 = f1;
            file2 = f2;
            directory = d + "\\";
            name = n;
            new File(directory).mkdirs();
            cmd = FILE_FFMPEG.getAbsolutePath() + " -loglevel fatal" + " -i \"" + file1.getAbsolutePath() + "\" -i \"" + file2.getAbsolutePath() + "\" -c:v copy -c:a aac \"" + directory + name + "\"";
            System.out.println(cmd);
            Runtime convert = Runtime.getRuntime();
            app = convert.exec(cmd);

            File output = new File(directory + name);
            System.out.println(output.getAbsolutePath());
            System.out.println("Deleted: " + output.delete());

            BufferedReader appReader = new BufferedReader(new InputStreamReader(app.getErrorStream()));
            try {
                app.waitFor();
                terminated = app.waitFor();
                if(terminated == 0)
                    System.out.println("WOOOOOOOOOOOOOOOOOOO!");
                else {
                    Thread.sleep(500);
                    File file = new File(directory + name);
                    if(file.exists() == false)
                        JOptionPane.showMessageDialog(null, "Merging failed!");
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
}
