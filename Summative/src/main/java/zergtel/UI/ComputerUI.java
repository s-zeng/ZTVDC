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
    String options[] = {"---Select an Option---", "File", "Settings", "Lisense", "Version"};
    Dimension minSize = new Dimension(640,480);
    JPanel logo = new JPanel();
    JPanel filler = new JPanel();
    JPanel commands = new JPanel();
    JComboBox logoBox = new JComboBox(options);

    public ComputerUI() {
        setTitle("ZergTel VDC");
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GroupLayout l = new GroupLayout(getContentPane());
        setBackground(new Color(44, 42, 43));
        setMinimumSize(minSize);
        setLayout(l);
        logo.add(logoBox);
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
        logo.setBorder(BorderFactory.createTitledBorder("ZergTel VDC"));
        JFXPanel browser = new JFXPanel();
        browser.setVisible(true);
            Platform.runLater(() -> {
            WebView youtube = new WebView();
            browser.setScene(new Scene(youtube));
            browser.setBounds(200, 150, 1000, 1000);
            youtube.getEngine().load("https://www.youtube.com/");
        });
        browser.setBorder(BorderFactory.createTitledBorder("Searcher"));
        commands.setBorder(BorderFactory.createTitledBorder("Menu"));
        l.setHorizontalGroup(l.createSequentialGroup()
        .addGroup(l.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(logo)
                .addComponent(commands))
        .addGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(filler)
                .addComponent(browser)));

        l.setVerticalGroup(l.createSequentialGroup()
        .addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(logo)
                .addComponent(filler))
        .addGroup(l.createParallelGroup()
                .addComponent(commands)
                .addComponent(browser)));
    }
}
