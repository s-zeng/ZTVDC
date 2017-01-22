package zergtel.UI;

import zergtel.core.converter.Converter;
import zergtel.core.converter.Merge;

import javax.swing.*;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * ConverterProgressBars/MergeProgressBars class
 * These classes were deprecated and are no longer used in the program
 *
 * The primary difficulty with implementing progress bars is extracting up-to-date progress information from Downloader,
 * Converter, and Merger. We haven't found a good way to get constantly up-to-date progress from these classes in an efficient
 * manner yet, and as a result, the GUI side implementation has been removed from use in the final code. It's still here
 * for reference though.
 *
 * This implementation attempts to do it's own math on completion based on expected file size, but ultimately that didn't
 * pan out very well.
 */
//Displays progress of a convert method being ran.
public class ConverterProgressBar extends JPanel implements Runnable {
    Converter c = new Converter();
    JTextField progress = new JTextField();
    File f;
    String d, n, fn;
    //constructor receiving a file, and directory and a name as inputs
    ConverterProgressBar(File fi, String di, String na) {
        f = fi;
        d = di;
        n = na;
        c.convert(f, d, n);

        Scanner read = new Scanner(c.app.getErrorStream()); //reads ffmpeg input from an error stream gotten from app
        Pattern lengthPatt = Pattern.compile("(?<=Duration: )[^,]*");
        String l = read.findWithinHorizon(lengthPatt, 0);//attempts to find the next occurrence of a pattern constructed from the specified string, ignoring delimiters
        String[] length = l.split(":"); //stores the length of conversion
        double dur = Integer.parseInt(length[0]) * 3600 + Integer.parseInt(length[1]) * 60 + Double.parseDouble(length[2]); //double that stores the duration
        progress.setBorder(BorderFactory.createTitledBorder("Total Duration in Seconds:" + dur)); //TextField containing the progress

        Pattern timePatt = Pattern.compile("(?<=time=)[\\d:.]*");
        String match;
        String[] matchComp;
        while (null != (match = read.findWithinHorizon(timePatt, 0))) {
            matchComp = match.split(":"); //splits the string match into an array receiving current duration for hours, minutes and seconds
            double completionRate = Integer.parseInt(matchComp[0]) * 3600 + Integer.parseInt(matchComp[1]) * 60 + Double.parseDouble(matchComp[2]) / dur; //stores duration left
            progress.setText("" + completionRate); //displays duration left
        }
    }
    //runs a new thread for a converter progress bar
    public void run() { ConverterProgressBar cPB = new ConverterProgressBar(f, d, n); }//this won't work, find work around

}
//exact same methodology as ConverterProgressBar, only difference is using the merge method instead of convert method. Same comments apply
class MergeProgressBar extends JPanel implements Runnable {
    File f1, f2;
    String d, n;
    JTextField progress = new JTextField();
    Merge m = new Merge();
    MergeProgressBar(File fi1, File fi2, String di, String na)
    {
        f1 = fi1;
        f2 = fi2;
        d = di;
        n = na;
        m.merge(f1, f2, d, n);

        Scanner read = new Scanner(m.app.getErrorStream());
        Pattern lengthPatt = Pattern.compile("(?<=Duration: )[^,]*");
        String l = read.findWithinHorizon(lengthPatt, 0);
        String[] length = l.split(":");
        double dur = Integer.parseInt(length[0]) * 3600 + Integer.parseInt(length[1]) * 60 + Double.parseDouble(length[2]);
        progress.setBorder(BorderFactory.createTitledBorder("Total Duration in Seconds:" + dur));

        Pattern timePatt = Pattern.compile("(?<=time=)[\\d:.]*");
        String match;
        String[] matchComp;
        while (null != (match = read.findWithinHorizon(timePatt, 0))) {
            matchComp = match.split(":");
            double completionRate = Integer.parseInt(matchComp[0]) * 3600 + Integer.parseInt(matchComp[1]) * 60 + Double.parseDouble(matchComp[2]) / dur;
            progress.setText("" + completionRate);
        }
    }
    public void run() {MergeProgressBar mPB = new MergeProgressBar(f1, f2, d, n);} //this won't work, find work around
}