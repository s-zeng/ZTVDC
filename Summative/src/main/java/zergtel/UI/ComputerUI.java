package zergtel.UI;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by Shyam on 2016-10-25.
 */
public class ComputerUI extends JFrame {
    Dimension minSize = new Dimension(640,480);
    private JButton[] buttons = new JButton[6];
    private JPanel[] panels = new JPanel[3];
    public ComputerUI() {
        setTitle("ZergTel VDC");
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setBackground(new Color(44, 42, 43));
        setMinimumSize(minSize);
        setLayout(new GroupLayout(c));
        this.addComponentListener(new ComponentAdapter(){
            public void componentResized(ComponentEvent e){
                Dimension size = ComputerUI.this.getSize();
                if(size.width<minSize.width)
                    size.width = minSize.width;
                if(size.height<minSize.height)
                    size.height = minSize.height;
                ComputerUI.this.setSize(size);
                }
            });
        setVisible(true);
        JFXPanel browser = new JFXPanel();
        browser.setVisible(true);
        c.add(browser);

        Platform.runLater(() -> {
            WebView youtube = new WebView();
            browser.setScene(new Scene(youtube));
            youtube.getEngine().load("https://www.youtube.com/");
        });
        pack();
    }

}
