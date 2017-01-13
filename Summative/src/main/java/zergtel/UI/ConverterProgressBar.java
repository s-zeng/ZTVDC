package zergtel.UI;

import zergtel.core.converter.Converter;
import zergtel.core.converter.Merge;

import javax.swing.*;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by Shyam on 2016-12-19.
 */

public class ConverterProgressBar extends JPanel implements Runnable {
    Converter c = new Converter();
    JTextField progress = new JTextField();
    File f;
    String d;
    String n;
    String fn;
    ConverterProgressBar(File fi, String di, String na) {
        f = fi;
        d = di;
        n = na;
        c.convert(f, d, n);

        Scanner read = new Scanner(c.app.getErrorStream());
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
    public void run() { ConverterProgressBar cPB = new ConverterProgressBar(f, d, n); }//this won't work, find work around

}
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