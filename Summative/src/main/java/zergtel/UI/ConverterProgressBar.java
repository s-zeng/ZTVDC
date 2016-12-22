package zergtel.UI;

import zergtel.core.converter.Converter;

import javax.swing.*;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by Shyam on 2016-12-19.
 */

public class ConverterProgressBar extends JPanel implements Runnable {
    Converter c = new Converter();
    JTextField progress = new JTextField();
    ConverterProgressBar() {
        c.convert(c.file, c.directory, c.name, c.format);

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
    public void run() { ConverterProgressBar cPB = new ConverterProgressBar(); }

}
