package zergtel.core.io;

import javax.swing.*;
import java.io.File;

/**
 * Created by Simon on 1/12/2017.
 * Simple wrapper around java's built in JFileChooser
 */
public class FileChooser {
    private JFileChooser chooser;
    private int chooserValue;

    public FileChooser() {
        try {
            LookAndFeel previousLF = UIManager.getLookAndFeel();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            chooser = new JFileChooser();
            UIManager.setLookAndFeel(previousLF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDirectory(File directory) {
        chooser.setCurrentDirectory(directory);
    }

    public File choose(String title, int mode) {
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(mode);
        chooserValue = chooser.showOpenDialog(null);
        File output = null;
        if (chooserValue == JFileChooser.APPROVE_OPTION) {
            output = chooser.getSelectedFile();
        }
        return output;
    }


}
