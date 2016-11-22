package zergtel.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Shyam on 2016-10-25.
 */
public class ComputerUI extends JFrame {
    private JButton[] buttons = new JButton[6];
    public ComputerUI() {
        JPanel panel = new JPanel(new BorderLayout());
        setExtendedState(MAXIMIZED_BOTH);
        setTitle("ZergTel VDC");
        setLayout(null); //CHANGE THIS BEFORE SPAGHETTI HAPPENS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        setResizable(true);
        Container c = getContentPane();
        c.setBackground(new Color(44, 42, 43));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }
}
