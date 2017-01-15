package zergtel.UI;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import zergtel.core.converter.Converter;
import zergtel.core.converter.Merge;
import zergtel.core.downloader.Downloader;
import zergtel.core.io.FileChooser;
import zergtel.core.searcher.Searcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Shyam on 2016-10-25.
 */
public class ComputerUI extends JFrame implements ActionListener{
    private String url;
    private String userInput, directory, name;
    private File file1, file2;
    private Dimension minSize = new Dimension(1024, 576);
    private JPanel commands = new JPanel();
    private JPanel download = new JPanel();
    private JPanel convert = new JPanel();
    private JPanel search = new JPanel();
    private JPanel openingPanel = new JPanel();
    private JPanel searchPanel = new JPanel();
    private JPanel searchQuery[] = new JPanel[25];
    private ArrayList<Map<String, String>> searchResults;
    private JFXPanel browserPanel = new JFXPanel();
    private WebView youtube;
    private WebEngine youtubeEngine;
    private GroupLayout layout;
    private JButton downloadUrl = new JButton("Download with URL from Searcher");
    private JButton downloadLink = new JButton("Download with URL");
    private JButton converter = new JButton("Convert");
    private JButton converterCancel = new JButton("Cancel");
    private JButton merge = new JButton("Merge");
    private JButton mergeCancel = new JButton("Cancel");
    private JButton searchKW = new JButton("Search by Key Words");
    private JButton previewURL = new JButton("Preview URL");
    private JOptionPane info = new JOptionPane();
    JOptionPane failure = new JOptionPane();
    private JOptionPane userI = new JOptionPane();
    private JTextArea openingText = new JTextArea();
    private JLabel image[] = new JLabel[25];
    private JLabel title[] = new JLabel[25];
    private JLabel channel[] = new JLabel[25];
    private JLabel description[] = new JLabel[25];
    private JLabel datePublished[] = new JLabel[25];
    private JButton preview[] = new JButton[25];
    private FileChooser chooser = new FileChooser();
    private Converter c = new Converter();
    private Merge m = new Merge();
    private SwingWorker worker;

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
        GroupLayout[] searchList = new GroupLayout[25];
        GridLayout searcherLayout = new GridLayout(5, 1);

        setLayout(layout);
        commands.setLayout(commandLayout);
        download.setLayout(downloadLayout);
        convert.setLayout(convertLayout);
        search.setLayout(searchLayout); //I made seperate layouts in order to make them resizeable
        openingPanel.setLayout(openingLayout);
        searchPanel.setLayout(searcherLayout);

        openingText.setText("Welcome to ZTVDC!\nUse the Menu to the left for options.");
        openingText.setBackground(null);
        openingText.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        openingText.setEditable(false);

        download.add(downloadLink);
        download.add(downloadUrl);
        convert.add(converter);
        convert.add(converterCancel);
        convert.add(merge);
        convert.add(mergeCancel);
        search.add(searchKW);
        search.add(previewURL);
        openingPanel.add(openingText);

        openingPanel.setBorder(BorderFactory.createTitledBorder("Welcome to ZTVDC"));
        browserPanel.setBorder(BorderFactory.createTitledBorder("Browser"));
        commands.setBorder(BorderFactory.createTitledBorder("Menu"));
        download.setBorder(BorderFactory.createTitledBorder("Downloader"));
        convert.setBorder(BorderFactory.createTitledBorder("Converter"));
        search.setBorder(BorderFactory.createTitledBorder("Search"));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Results"));

        for(int i = 0; i < 25; i++) {
            searchQuery[i] = new JPanel();
            searchQuery[i].setBorder(BorderFactory.createTitledBorder("Results" + i+1));
            searchList[i] = new GroupLayout(searchQuery[i]);
            searchQuery[i].setLayout(searchList[i]);
            preview[i] = new JButton("Preview");
            searchQuery[i].add(preview[i]);
            title[i] = new JLabel();
            searchQuery[i].add(title[i]);
            channel[i] = new JLabel();
            searchQuery[i].add(channel[i]);
            description[i] = new JLabel();
            searchQuery[i].add(description[i]);
            datePublished[i] = new JLabel();
            searchQuery[i].add(datePublished[i]);
        }

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(commands, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(openingPanel, 0, 700, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createSequentialGroup()
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
        .addComponent(merge, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(convertLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(converterCancel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(mergeCancel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        convertLayout.setVerticalGroup(convertLayout.createSequentialGroup()
        .addGroup(convertLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(converter, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(converterCancel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(convertLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(merge, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(mergeCancel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        searchLayout.setHorizontalGroup(searchLayout.createSequentialGroup()
        .addGroup(searchLayout.createParallelGroup()
        .addComponent(searchKW, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(previewURL, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        searchLayout.setVerticalGroup(searchLayout.createSequentialGroup()
        .addGroup(searchLayout.createParallelGroup()
        .addComponent(searchKW, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(searchLayout.createParallelGroup()
        .addComponent(previewURL, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        openingLayout.setHorizontalGroup(openingLayout.createSequentialGroup()
        .addComponent(openingText, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        openingLayout.setVerticalGroup(openingLayout.createSequentialGroup()
        .addComponent(openingText, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        downloadUrl.addActionListener(this);
        downloadLink.addActionListener(this);
        converter.addActionListener(this);
        converterCancel.addActionListener(this);
        merge.addActionListener(this);
        mergeCancel.addActionListener(this);
        searchKW.addActionListener(this);
        previewURL.addActionListener(this);
        for(int i = 0; i < 25; i++)
            preview[i].addActionListener(this);


        this.setLocationRelativeTo(null);
        downloadUrl.setEnabled(false);
        converterCancel.setEnabled(false);
        mergeCancel.setEnabled(false);
        info.showMessageDialog(null, "Click Download with URL in SearcherExample once to get instructions, then the rest of the time click it to download, or else click convert/merge to use that function!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == downloadUrl) {
            //lol replace this with downloading url from searcher
        }
        if (e.getSource() == downloadLink) {
            url = JOptionPane.showInputDialog(null, "Insert media URL to download from");
            System.out.println("Url: " + url);

            if (!url.equals(null)) {
                new DownloadWorker(url).execute();
            }
        }
        if (e.getSource() == converter) {
            try {
                file1 = chooser.choose("Select file to convert", JFileChooser.FILES_ONLY);
                if (!file1.equals(null)) {
                    directory = chooser.choose("Choose where to save the output file", JFileChooser.DIRECTORIES_ONLY).getAbsolutePath();
                    if (!directory.equals(null)) {
                        name = userI.showInputDialog(null, "Insert name of the new file (Include format) example: test.mp4");
                        new ConvertWorker(file1, directory, name).execute();
                    }
                }
            } catch (HeadlessException e1) {
                e1.printStackTrace();
                failure.showMessageDialog(null, e1.getMessage(), "Error", failure.ERROR_MESSAGE);
            }
            converterCancel.setEnabled(true);
        }
        if(e.getSource() == converterCancel)
            c.cancel(); //i may have broken your cancer because i use a seperate converter instance in ConverterWorker - try to make it work
        //A potential solution - assign the new ConvertWorker to a variable beforehand, and then do variable.execute() to run, and variable.cancel() to cancel lol
        if(e.getSource() == merge) {
            file1 = chooser.choose("Select video source file", JFileChooser.FILES_ONLY);
            if (!file1.equals(null)) {
                file2 = chooser.choose("Select audio source file", JFileChooser.FILES_ONLY);
                if (!file2.equals(null)) {
                    directory = chooser.choose("Choose where to save the output file", JFileChooser.DIRECTORIES_ONLY).getAbsolutePath();
                    if (!directory.equals(null)) {
                        name = userI.showInputDialog(null, "Insert name of the new file (Include format) example: test.mp4");
//                        m.merge(file1, file2, directory, name);
                        new MergeWorker(file1, file2, directory, name).execute();
                    }
                }
            }
            mergeCancel.setEnabled(true);
        }
        if(e.getSource() == mergeCancel)
            m.cancel();
        if(e.getSource() == searchKW) { //TODO: put searcher into a worker to, because this is actually kinda slow
            userInput = userI.showInputDialog(null, "Please enter the key words you desire to search for");
            searchResults = Searcher.search(userInput);
            for (int i = 0; i <25; i++) { //magic number, beware
                Map<String, String> result = searchResults.get(i);
                System.out.println(result.toString());
                title[i].setText(result.get("title"));
                channel[i].setText(result.get("channel"));
                description[i].setText(result.get("description"));
                datePublished[i].setText(result.get("datePublished"));
            }
        }
        if(e.getSource() == previewURL) {
            userInput = userI.showInputDialog(null, "Please enter the URL you would like to search");
            Platform.runLater (() -> {
                youtube = new WebView();
                youtubeEngine = youtube.getEngine();
                youtubeEngine.load(userInput);
                browserPanel.setScene(new Scene(youtube));
            });
            layout.replace(openingPanel, browserPanel);
        }
    }
}

class DownloadWorker extends SwingWorker<String, Void> {
    String url;

    DownloadWorker(String downUrl) {
        url = downUrl;
    }

    @Override
    public String doInBackground() {
        try {
            return Downloader.get(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Oh no! Something goofed!", "Error", JOptionPane.ERROR_MESSAGE);
            return ex.getMessage();
        }
    }
}

class ConvertWorker extends SwingWorker<String, Void> {
    String directory, name;
    File inFile;
    private Converter converter = new Converter();

    ConvertWorker(File input, String dir, String nom) {
        inFile = input;
        directory = dir;
        name = nom;
    }

    @Override
    public String doInBackground() {
        try {
            converter.convert(inFile, directory, name);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Oh no! Something goofed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}

class MergeWorker extends SwingWorker<String, Void> {
    String directory, name;
    File file1, file2;
    private Merge merger = new Merge();

    MergeWorker(File f1, File f2, String dir, String nom) {
        file1 = f1;
        file2 = f2;
        directory = dir;
        name = nom;
    }

    @Override
    public String doInBackground() {
        try {
            merger.merge(file1, file2, directory, name);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Oh no! Something goofed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}


