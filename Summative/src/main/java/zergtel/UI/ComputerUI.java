package zergtel.UI;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import zergtel.core.converter.Converter;
import zergtel.core.converter.Merge;
import zergtel.core.downloader.Downloader;
import zergtel.core.io.FileChooser;

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
    private int isPressed = 0;
    private String url; //this will be used for webview, DO NOT KEEP IN FINAL PRODUCT
    private String userInput1, userInput2, directory, name, format;
    private File file1, file2;
    private Dimension minSize = new Dimension(1024, 576);
    private JPanel logo = new JPanel();
    private JPanel filler = new JPanel();
    private JPanel commands = new JPanel();
    private JPanel download = new JPanel();
    private JPanel convert = new JPanel();
    private JPanel search = new JPanel();
    private JPanel openingPanel = new JPanel();
    private JFXPanel searchPanel = new JFXPanel();
    private WebView youtube;
    private WebEngine youtubeEngine;
    private GroupLayout layout;
    private JButton downloadUrl = new JButton("Download with URL in Browser");
    private JButton downloadLink = new JButton("Download with URL");
    private JButton converter = new JButton("Convert");
    private JButton merge = new JButton("Merge");
    private JButton searchKW = new JButton("Reload");
    private JOptionPane info = new JOptionPane();
    private JOptionPane failure = new JOptionPane();
    private JOptionPane userI = new JOptionPane();
    private JTextArea openingText = new JTextArea();
    private FileChooser chooser = new FileChooser();

    public ComputerUI() {
        setTitle("ZergTel VDC");
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(44, 42, 43));
        setMinimumSize(minSize);
        chooser.setDirectory(new File("."));
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

        layout = new GroupLayout(getContentPane());
        GroupLayout commandLayout = new GroupLayout(commands);
        GroupLayout downloadLayout = new GroupLayout(download);
        GroupLayout convertLayout = new GroupLayout(convert);
        GroupLayout searchLayout = new GroupLayout(search);
        GroupLayout openingLayout = new GroupLayout(openingPanel);

        setLayout(layout);
        commands.setLayout(commandLayout);
        download.setLayout(downloadLayout);
        convert.setLayout(convertLayout);
        search.setLayout(searchLayout); //I made seperate layouts in order to make them resizeable
        openingPanel.setLayout(openingLayout);

        openingText.setText("Welcome to ZTVDC!\nUse the Menu to the left for options.");
        openingText.setBackground(null);
        openingText.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        openingText.setEditable(false);

        download.add(downloadLink);
        download.add(downloadUrl);
        convert.add(converter);
        convert.add(merge);
        search.add(searchKW);
        openingPanel.add(openingText);

        openingPanel.setBorder(BorderFactory.createTitledBorder("Welcome to ZTVDC"));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Browser"));
        commands.setBorder(BorderFactory.createTitledBorder("Menu"));
        logo.setBorder(BorderFactory.createTitledBorder("ZergTel VDC"));
        download.setBorder(BorderFactory.createTitledBorder("Downloader"));
        convert.setBorder(BorderFactory.createTitledBorder("Converter"));
        search.setBorder(BorderFactory.createTitledBorder("Search"));

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(logo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(commands, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(filler, 0 ,GroupLayout.DEFAULT_SIZE, 724)
                        .addComponent(openingPanel, 0, 700, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(logo, 0, GroupLayout.DEFAULT_SIZE, 100)
                        .addComponent(filler, 0, GroupLayout.DEFAULT_SIZE, 100))
                .addGroup(layout.createParallelGroup()
                        .addComponent(commands, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(openingPanel, 0, 500, Short.MAX_VALUE)));

        commandLayout.setHorizontalGroup(commandLayout.createSequentialGroup()
                .addGroup(commandLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(download, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(convert, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(search))); //Adding the parameter 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE allows it to force resize, or else it will stay at min size.
        commandLayout.setVerticalGroup(commandLayout.createSequentialGroup()
        .addGroup(commandLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(download, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(commandLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(convert, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addComponent(search));

        downloadLayout.setHorizontalGroup(downloadLayout.createSequentialGroup()
        .addGroup(downloadLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(downloadUrl, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(downloadLink, 0 ,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        downloadLayout.setVerticalGroup(downloadLayout.createSequentialGroup()
        .addGroup(downloadLayout.createParallelGroup(GroupLayout.Alignment.CENTER))
        .addComponent(downloadUrl, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(downloadLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(downloadLink, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        convertLayout.setHorizontalGroup(convertLayout.createSequentialGroup()
        .addGroup(convertLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(converter, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(merge, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        convertLayout.setVerticalGroup(convertLayout.createSequentialGroup()
        .addGroup(convertLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(converter, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(convertLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(merge, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        searchLayout.setHorizontalGroup(searchLayout.createSequentialGroup()
        .addComponent(searchKW, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        searchLayout.setVerticalGroup(searchLayout.createSequentialGroup()
        .addComponent(searchKW, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        openingLayout.setHorizontalGroup(openingLayout.createSequentialGroup()
        .addComponent(openingText, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        openingLayout.setVerticalGroup(openingLayout.createSequentialGroup()
        .addComponent(openingText, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        downloadUrl.addActionListener(this);
        downloadLink.addActionListener(this);
        converter.addActionListener(this);
        merge.addActionListener(this);
        searchKW.addActionListener(this);

        info.showMessageDialog(null, "Click Download with URL in SearcherExample once to get instructions, then the rest of the time click it to download, or else click convert/merge to use that function!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == downloadUrl) {
            if(isPressed == 0) {
                layout.replace(openingPanel, searchPanel);
                Platform.runLater(() -> {
                    youtube = new WebView();
                    youtubeEngine = youtube.getEngine();
                    youtubeEngine.load("https://youtube.com");
                    searchPanel.setScene(new Scene(youtube));
                    youtubeEngine.getLoadWorker().stateProperty().addListener(
                            new ChangeListener<State>() {
                                @Override
                                public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                                    if (newValue == State.SUCCEEDED){
                                        url = youtubeEngine.getLocation();
                                        System.out.println(url);
                                    }
                                }
                            }
                    );
                });
                info.showMessageDialog(null, "Reload the page with the desired download video. Click download while playing the youtube video.");
                isPressed++;
            } else {
                try {
                    Downloader.get(url);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    failure.showMessageDialog(null, "Oh no! Something goofed!", "Error", failure.ERROR_MESSAGE);

                }
            }
        }
        if (e.getSource() == downloadLink) {
            userInput1 = userI.showInputDialog(null, "Insert the url for the video");

            try {
                Downloader.get(userInput1);
            } catch (Exception ex){
                ex.printStackTrace();
                failure.showMessageDialog(null, "Oh no! Something goofed!", "Error", failure.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == converter) {
            try {
                file1 = chooser.choose("Select file to convert", JFileChooser.FILES_ONLY);
//            directory = userI.showInputDialog(null, "Insert the directory of the output file");
                if (!file1.equals(null)) {
                    directory = chooser.choose("Choose where to save the output file", JFileChooser.DIRECTORIES_ONLY).getAbsolutePath();
                    if (!directory.equals(null)) {
                        name = userI.showInputDialog(null, "Insert name of the new file (EXCLUDE .FORMAT!!!!!!!) example: test");
                        format = userI.showInputDialog(null, "Insert format of the file (EXCLUDE NAME AND PERIOD!!!!!!!!) example mp4");
                        Converter c = new Converter();
                        c.convert(file1, directory, name, format);
                    }
                }
            } catch (HeadlessException e1) {
                e1.printStackTrace();
                failure.showMessageDialog(null, e1.getMessage(), "Error", failure.ERROR_MESSAGE);
            }
        }
        if(e.getSource() == merge) {
            Merge m = new Merge();
            userInput1 = userI.showInputDialog(null, "Insert the directory of the video file");
            userInput2 = userI.showInputDialog(null, "Insert the directory of the audio file");
            directory = userI.showInputDialog(null, "Insert the directory of the output file");
            name = userI.showInputDialog(null, "Insert name of the new file (EXCLUDE .FORMAT!!!!!!!) example: test");
            format = userI.showInputDialog(null, "Insert format of the file (EXCLUDE NAME AND PERIOD!!!!!!!!) example mp4");
            file1 = new File(userInput1);
            file2 = new File(userInput2);
            m.merge(file1, file2, directory, name, format);
        }
        if(e.getSource() == searchKW)
            Platform.runLater(() ->
                    youtubeEngine.reload());
    }
}


