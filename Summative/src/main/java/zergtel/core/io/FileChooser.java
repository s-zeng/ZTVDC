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
        try { //The lines within this block serve to make the file chooser popup look like a native windows popup rather than an ugly java one.
            LookAndFeel previousLF = UIManager.getLookAndFeel();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            chooser = new JFileChooser();
            UIManager.setLookAndFeel(previousLF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets directory which the file chooser will default to on opening
     *
     * @param directory - The directory which it will default to
     */
    public void setDirectory(File directory) {
        chooser.setCurrentDirectory(directory);
    }

    /**
     * Allows user to choose a file through a JFileChooser popup
     *
     * @param title
     * @param mode
     * @return
     */
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
