package zergtel.UI;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import zergtel.core.converter.Converter;
import zergtel.core.converter.Merge;
import zergtel.core.downloader.Downloader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

/**
 * Created by Shyam on 2016-10-25.
 */
public class ComputerUI extends JFrame implements ActionListener{
    private int start = 0;
    private String url = "https://youtube.com"; //this will be used for webview, DO NOT KEEP IN FINAL PRODUCT
    private String options[] = {"PlaceHolder", "File", "Settings", "Lisense", "Version"};
    private String u1, u2, name, format;
    private File f1, f2;
    private Dimension minSize = new Dimension(640,480);
    private JPanel logo = new JPanel();
    private JPanel filler = new JPanel();
    private JPanel commands = new JPanel();
    private JPanel download = new JPanel();
    private JPanel convert = new JPanel();
    private JPanel search = new JPanel();
    private JFXPanel browser = new JFXPanel();
    private JButton downloadUrl = new JButton("Download with URL");
    private JButton converter = new JButton("Convert");
    private JButton merge = new JButton("Merge");
    private JButton searchKW = new JButton("Coming Soon!");
    private JOptionPane info = new JOptionPane();
    private JOptionPane failure = new JOptionPane();
    private JOptionPane userI1 = new JOptionPane();
    private JOptionPane userI2 = new JOptionPane();
    private JOptionPane userI3 = new JOptionPane();
    private JOptionPane userI4 = new JOptionPane();
    private JComboBox logoBox = new JComboBox(options);

    public ComputerUI() {
        setTitle("ZergTel VDC");
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(44, 42, 43));
        setMinimumSize(minSize);
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

        GroupLayout l = new GroupLayout(getContentPane());
        GroupLayout cL = new GroupLayout(commands);
        GroupLayout dL = new GroupLayout(download);
        GroupLayout cL2 = new GroupLayout(convert);
        GroupLayout sL = new GroupLayout(search);
        setLayout(l);
        commands.setLayout(cL);
        download.setLayout(dL);
        convert.setLayout(cL2);
        search.setLayout(sL); //I made seperate layouts in order to make them resizeable

        logo.add(logoBox);
        download.add(downloadUrl);
        convert.add(converter);
        convert.add(merge);
        search.add(searchKW);

        browser.setBorder(BorderFactory.createTitledBorder("Searcher"));
        commands.setBorder(BorderFactory.createTitledBorder("Menu"));
        logo.setBorder(BorderFactory.createTitledBorder("ZergTel VDC"));
        download.setBorder(BorderFactory.createTitledBorder("Downloader"));
        convert.setBorder(BorderFactory.createTitledBorder("Converter"));
        search.setBorder(BorderFactory.createTitledBorder("Searcher"));

        l.setHorizontalGroup(l.createSequentialGroup()
        .addGroup(l.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(logo)
                .addComponent(commands))
        .addGroup(l.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(filler)
                .addComponent(browser,  0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        l.setVerticalGroup(l.createSequentialGroup()
        .addGroup(l.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(logo)
                .addComponent(filler))
        .addGroup(l.createParallelGroup()
                .addComponent(commands, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(browser,  0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        cL.setHorizontalGroup(cL.createSequentialGroup()
                .addGroup(cL.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(download, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(convert, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(search, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))); //Adding the parameter 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE allows it to force resize, or else it will stay at min size.
        cL.setVerticalGroup(cL.createSequentialGroup()
        .addGroup(cL.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(download, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(cL.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(convert, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addComponent(search, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        dL.setHorizontalGroup(dL.createSequentialGroup()
        .addComponent(downloadUrl, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        dL.setVerticalGroup(dL.createSequentialGroup()
        .addComponent(downloadUrl, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        cL2.setHorizontalGroup(cL2.createSequentialGroup()
        .addGroup(cL2.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(converter, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(merge, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        cL2.setVerticalGroup(cL2.createSequentialGroup()
        .addGroup(cL2.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(converter, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(cL2.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(merge, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        sL.setHorizontalGroup(sL.createSequentialGroup()
        .addComponent(searchKW, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        sL.setVerticalGroup(sL.createSequentialGroup()
        .addComponent(searchKW, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        downloadUrl.addActionListener(this);
        converter.addActionListener(this);
        merge.addActionListener(this);
        searchKW.addActionListener(this);

        info.showMessageDialog(null, "Click Download with URL to search a video to download, or else click convert/merge to use that function!");
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == downloadUrl)
        {
            if(start == 0) {
                info.showMessageDialog(null, "In order to download a video, click the download button when watching the video you would like to download", "How to download", info.PLAIN_MESSAGE);
                start++;
            }
            Platform.runLater(() -> {
                WebView youtube = new WebView();
                WebEngine youtubeEngine = youtube.getEngine();
                if(url.equals("https://youtube.com")) {
                    browser.setScene(new Scene(youtube));
                    youtubeEngine.load(url);
                    url += "a";
                } else {
                    url = youtubeEngine.getLocation();
                    try {
                        Downloader.get(url); //GET SIMON TO NOTIFY HOW TO PROPERLY DOWNLOAD VIA URL
                    } catch(Exception ex) {
                        failure.showMessageDialog(null, "Oh no! Something goofed!", "Error", failure.ERROR_MESSAGE);
                    }
                }
            });

        }
        if (e.getSource() == converter) {
            Converter c = new Converter();
            u1 = userI1.showInputDialog(null, "Insert the name of the file (INCLUDE .FORMAT!!!!!!!!) example: test.mp4");
            name = userI3.showInputDialog(null, "Insert name of the new file (EXCLUDE .FORMAT!!!!!!!) example: test");
            format = userI4.showInputDialog(null, "Insert format of the file (EXCLUDE NAME AND PERIOD!!!!!!!!) example mp4");
            File location = new File("C:\\");
            File[] listOfFiles = location.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                String filename = listOfFiles[i].getName();
                if (filename.endsWith(u1))
                    f1 = listOfFiles[i];
            }
                c.convert(f1.getAbsoluteFile(), name, format);
        }
        if(e.getSource() == merge) {
            Merge m = new Merge();
            u1 = userI1.showInputDialog(null, "Insert the name of the video file (INCLUDE .FORMAT!!!!!!!!) example: test.mp4");
            u2 = userI2.showInputDialog(null, "Insert the name of the audio file (INCLUDE FORMAT!!!!!!!!) example test.mp3");
            name = userI3.showInputDialog(null, "Insert name of the new file (EXCLUDE .FORMAT!!!!!!!) example: test");
            format = userI4.showInputDialog(null, "Insert format of the file (EXCLUDE NAME AND PERIOD!!!!!!!!) example mp4");
            File location1 = new File("C:\\");
            File[] listOfFiles1 = location1.listFiles();
            for (int i = 0; i < listOfFiles1.length; i++) {
                String filename = listOfFiles1[i].getName();
                if (filename.endsWith(u1))
                    f1 = listOfFiles1[i];
            }
            File location2 = new File("C:\\");
            File[] listOfFiles2 = location2.listFiles();
            for (int i = 0; i < listOfFiles2.length; i++) {
                String filename = listOfFiles2[i].getName();
                if (filename.endsWith(u1))
                    f2 = listOfFiles2[i];
            }
            m.merge(f1.getAbsoluteFile(), f2.getAbsoluteFile(), name, format);
        }
        if(e.getSource() == searchKW)
            info.showMessageDialog(null, "This will be available soon!");
    }
}


