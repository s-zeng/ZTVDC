package zergtel.UI;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import zergtel.core.Main;
import zergtel.core.converter.Converter;
import zergtel.core.converter.Merge;
import zergtel.core.downloader.Downloader;
import zergtel.core.downloader.EzHttp;
import zergtel.core.io.FileChooser;
import zergtel.core.searcher.Searcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Shyam on 2016-10-25.
 */
public class ComputerUI extends JFrame implements ActionListener{
    private int openingDisplay = 1;
    private int searchDisplay = 0;
    private int browserDisplay = 0;
    private int buttonNo = -1;
    private int numPressed = 0;
    private int swap = 0;
    public int isConverterCancelled = 0;
    public int isMergeCancelled = 0;
    private String[] imageUrl = new String[5];
    private String[] urlStorage = new String[5];
    private String userInput, directory, name, url;
    private File file1, file2;
    private Dimension minSize = new Dimension(1080, 635);
    private JPanel commands = new JPanel();
    private JPanel download = new JPanel();
    private JPanel convert = new JPanel();
    private JPanel search = new JPanel();
    private JPanel openingPanel = new JPanel();
    private JPanel searchPanel = new JPanel();
    private JPanel searchQuery[] = new JPanel[5];
    private JPanel searchFiller[] = new JPanel[5];
    private ArrayList<Map<String, String>> searchResults;
    private JFXPanel browserPanel = new JFXPanel();
    private WebView youtube;
    private WebEngine youtubeEngine;
    private GroupLayout layout;
    private GroupLayout[] searchList = new GroupLayout[5];
    private JButton downloadUrl = new JButton("Download Selected");
    private JButton downloadLink = new JButton("Download from URL");
    public JButton downloadUrlCancel = new JButton("Cancel");
    public JButton downloadLinkCancel = new JButton("Cancel");
    private JButton converter = new JButton("      Convert Files      ");
    public JButton converterCancel = new JButton("Cancel");
    private JButton merge = new JButton("Merge");
    public JButton mergeCancel = new JButton("Cancel");
    private JButton searchKW = new JButton("Search by Key Words");
    private JButton previewURL = new JButton("Preview Selected");
    private JTextArea openingText = new JTextArea();
    private JLabel image[] = new JLabel[5];
    private JLabel title[] = new JLabel[5];
    private JLabel channel[] = new JLabel[5];
    private JLabel description[] = new JLabel[5];
    private JLabel datePublished[] = new JLabel[5];
    private JLabel test[] = new JLabel[5];
    private JButton preview[] = new JButton[5];
    private FileChooser chooser = new FileChooser();
    private Converter c = new Converter();
    private Merge m = new Merge();
    private DownloadWorker downloadWorker;
    private ConvertWorker convertWorker;
    private MergeWorker mergeWorker;

    public ComputerUI() {
        URL iconURL = getClass().getResource("/zergtel.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());

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

        download.add(downloadUrl);
        download.add(downloadUrlCancel);
        download.add(downloadLink);
        download.add(downloadLinkCancel);
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

        for(int i = 0; i < 5; i++) {
            searchQuery[i] = new JPanel();
            searchQuery[i].setBorder(BorderFactory.createTitledBorder("Result #" + (i+1)));
            searchList[i] = new GroupLayout(searchQuery[i]);
            searchQuery[i].setLayout(searchList[i]);
            preview[i] = new JButton("Select");
            searchQuery[i].add(preview[i]);
            title[i] = new JLabel();
            searchQuery[i].add(title[i]);
            channel[i] = new JLabel();
            searchQuery[i].add(channel[i]);
            description[i] = new JLabel();
            searchQuery[i].add(description[i]);
            datePublished[i] = new JLabel();
            searchQuery[i].add(datePublished[i]);
            test[i] = new JLabel();
            image[i] = new JLabel();
            searchQuery[i].add(test[i]);
            searchFiller[i] = new JPanel();
            searchQuery[i].add(searchFiller[i]);


            searchList[i].setHorizontalGroup(searchList[i].createSequentialGroup()
            .addGroup(searchList[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(test[i]))
            .addGroup(searchList[i].createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(title[i], 450, 450, 750))
            .addGroup(searchList[i].createParallelGroup(GroupLayout.Alignment.LEADING))
                    .addComponent(datePublished[i], 75, GroupLayout.DEFAULT_SIZE, 75)
            .addComponent(preview[i], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
            searchList[i].setVerticalGroup(searchList[i].createSequentialGroup()
            .addGroup(searchList[i].createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(test[i])
                .addComponent(title[i], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(datePublished[i], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(preview[i], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
            searchList[i].setAutoCreateGaps(true);
            searchPanel.add(searchQuery[i]);
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
        .addComponent(downloadLink, 0 ,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(downloadLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(downloadUrlCancel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(downloadLinkCancel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        downloadLayout.setVerticalGroup(downloadLayout.createSequentialGroup()
        .addGroup(downloadLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(downloadUrl, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(downloadUrlCancel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(downloadLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
        .addComponent(downloadLink, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(downloadLinkCancel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

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
        downloadUrlCancel.addActionListener(this);
        downloadLink.addActionListener(this);
        downloadLinkCancel.addActionListener(this);
        converter.addActionListener(this);
        converterCancel.addActionListener(this);
        merge.addActionListener(this);
        mergeCancel.addActionListener(this);
        searchKW.addActionListener(this);
        previewURL.addActionListener(this);
        for(int i = 0; i < 5; i++)
            preview[i].addActionListener(this);


        this.setLocationRelativeTo(null);
        downloadUrl.setEnabled(false);
        downloadUrlCancel.setEnabled(false);
        downloadLinkCancel.setEnabled(false);
        converterCancel.setEnabled(false);
        mergeCancel.setEnabled(false);
        previewURL.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == downloadUrl) {
            directory = chooser.choose("Choose where to save the downloaded file", JFileChooser.DIRECTORIES_ONLY).getAbsolutePath() + "\\";
            if (!directory.equals(null)) {
                EzHttp.setDownloadLocation(directory);
                url = urlStorage[buttonNo];
                downloadWorker = new DownloadWorker(url);
                JOptionPane.showMessageDialog(null, "Downloading has begun - we'll alert you when it's done.");
                downloadWorker.execute();
                downloadUrlCancel.setEnabled(true);
            }
        }
        if (e.getSource() == downloadUrlCancel) {
            downloadWorker.cancel(true);
            downloadUrlCancel.setEnabled(false);
            new File(directory + name).delete();
        }
        if (e.getSource() == downloadLink) {
            url = JOptionPane.showInputDialog(null, "Insert BandCamp, YouTube, or raw file link to download from");
            System.out.println("Url: " + url);
            if (!url.equals(null)) {
                directory = chooser.choose("Choose where to save the downloaded file", JFileChooser.DIRECTORIES_ONLY).getAbsolutePath() + "\\";

                if (!directory.equals(null)) {
                    EzHttp.setDownloadLocation(directory);
                    downloadWorker = new DownloadWorker(url);
                    JOptionPane.showMessageDialog(null, "Downloading has begun - we'll alert you when it's done.");
                    downloadWorker.execute();
                    downloadLinkCancel.setEnabled(true);
                }
            }
        }
        if (e.getSource() == downloadLinkCancel) {
            downloadWorker.cancel(true);
            downloadLinkCancel.setEnabled(false);
            new File(directory + name).delete();
        }
        if (e.getSource() == converter) {
            try {
                file1 = chooser.choose("Select file to convert", JFileChooser.FILES_ONLY);
                if (!file1.equals(null)) {
                    directory = chooser.choose("Choose where to save the output file", JFileChooser.DIRECTORIES_ONLY).getAbsolutePath();
                    if (!directory.equals(null)) {
                        name = JOptionPane.showInputDialog(null, "Insert name of the new file (Include format) example: test.mp4");
                        convertWorker = new ConvertWorker(file1, directory, name);
                        JOptionPane.showMessageDialog(null, "Conversion has begun - we'll alert you when it's done");
                        convertWorker.execute();
                    }
                }
            } catch (HeadlessException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            converterCancel.setEnabled(true);
        }
        if (e.getSource() == converterCancel) {
            isConverterCancelled = 1;
            convertWorker.cancel(true);
            converterCancel.setEnabled(false);
            File temp = new File("./" + name);
            System.out.println(temp.getAbsolutePath());
            temp.delete();
        }
        //A potential solution - assign the new ConvertWorker to a variable beforehand, and then do variable.execute() to run, and variable.cancel() to cancel lol
        if (e.getSource() == merge) {
            file1 = chooser.choose("Select video source file", JFileChooser.FILES_ONLY);
            if (!file1.equals(null)) {
                file2 = chooser.choose("Select audio source file", JFileChooser.FILES_ONLY);
                if (!file2.equals(null)) {
                    directory = chooser.choose("Choose where to save the output file", JFileChooser.DIRECTORIES_ONLY).getAbsolutePath();
                    if (!directory.equals(null)) {
                        name = JOptionPane.showInputDialog(null, "Insert name of the new file (Include format) example: test.mp4");
//                        m.merge(file1, file2, directory, name);
                        mergeWorker = new MergeWorker(file1, file2, directory, name);
                        JOptionPane.showMessageDialog(null, "Merging has begun - we'll alert you when it's done.");
                        mergeWorker.execute();
                    }
                }
            }
            mergeCancel.setEnabled(true);
        }
        if (e.getSource() == mergeCancel) {
            isMergeCancelled = 1;
            mergeWorker.cancel(true);
            mergeCancel.setEnabled(false);
            new File(directory + "\\" +  name).delete();
        }
        if (e.getSource() == searchKW) {
            userInput = JOptionPane.showInputDialog(null, "Please enter your search query");
            if (!userInput.equals("")) {
                searchResults = Searcher.search(userInput);
                URL[] url = new URL[5];
                ImageIcon[] imageStored = new ImageIcon[5];
                BufferedImage[] images = new BufferedImage[5];
                for (int i = 0; i < searchResults.size(); i++) { //magic number, beware
                    Map<String, String> result = searchResults.get(i);
                    title[i].setText("<html>" + "Title: " + result.get("title") + "<br>" + "Channel: " + result.get("channel") + "<br>" + "<font color = 'gray'>" + "Description: " + result.get("description") + "</font>" + "</html>");
                    datePublished[i].setText(result.get("datePublished"));
                    urlStorage[i] = result.get("url");
                    imageUrl[i] = result.get("thumbnail");
                    try {
                        url[i] = new URL(imageUrl[i]);
                        images[i] = ImageIO.read(url[i]);
                        imageStored[i] = new ImageIcon(images[i]);
                        image[i].setIcon(imageStored[i]);
                    } catch(MalformedURLException e3) {
                        e3.printStackTrace();
                    } catch(IOException e4) {
                        e4.printStackTrace();
                    }
                    if (swap == 0) {
                        searchList[i].replace(test[i], image[i]);
                        searchList[i].linkSize(SwingConstants.VERTICAL, image[i], preview[i]);
                    }
                    searchList[i].replace(image[i], image[i]);
                }
                if (searchResults.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No search results found.", "Woops!", 0);
                } else {
                    swap++;
                    if (openingDisplay == 1 && searchDisplay == 0) {
                        layout.replace(openingPanel, searchPanel);
                        openingDisplay = 0;
                        searchDisplay = 1;
                    } else if (browserDisplay == 1 && searchDisplay == 0) {
                        layout.replace(browserPanel, searchPanel);
                        browserDisplay = 0;
                        searchDisplay = 1;
                    }
                }
            }
        }
        if(e.getSource() == previewURL) {
            switch (buttonNo) {
                case 0: url = urlStorage[0];
                    browser();
                    break;
                case 1: url = urlStorage[1];
                    browser();
                    break;
                case 2: url = urlStorage[2];
                    browser();
                    break;
                case 3: url = urlStorage[3];
                    browser();
                    break;
                case 4: url = urlStorage[4];
                    browser();
                    break;
            }
        }
        for(int i = 0; i < 5; i++)
        {
            if(e.getSource() == preview[i])
            {
                buttonNo = i;
                JOptionPane.showMessageDialog(null, "You may now use the appropriate button on the left to preview or download.", "Video selected!", 1);
                downloadUrl.setEnabled(true);
                previewURL.setEnabled(true);
            }
        }

    }
    public void browser() {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            if (numPressed != 0)
                youtubeEngine.getLoadWorker().cancel();
            if(numPressed == 0) {
                youtube = new WebView();
                youtubeEngine = youtube.getEngine();
                browserPanel.setScene(new Scene(youtube));
            }
            youtubeEngine.load(url);
        });
        if (browserDisplay == 0 && openingDisplay == 1) {
            layout.replace(openingPanel, browserPanel);
            browserDisplay = 1;
            openingDisplay = 0;
            youtubeEngine.getLoadWorker().cancel();
        } else if (browserDisplay == 0 && searchDisplay == 1) {
            layout.replace(searchPanel, browserPanel);
            browserDisplay = 1;
            searchDisplay = 0;
        }
        numPressed++;
    }
}

class DownloadWorker extends SwingWorker<String, Void> {
    String url;

    DownloadWorker(String downUrl) {
        url = downUrl;
    }

    @Override
    public String doInBackground() {
        String output;

        try {
            output = Downloader.get(url);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Oh no! Something goofed!", "Error", JOptionPane.ERROR_MESSAGE);
            return ex.getMessage();
        }
        Main.ui.downloadLinkCancel.setEnabled(false);
        Main.ui.downloadUrlCancel.setEnabled(false);
        JOptionPane.showMessageDialog(null, "Downloading has finished for " + output);
        return output;
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
            if (converter.getTerminated() == 0 && Main.ui.isConverterCancelled == 0)
                JOptionPane.showMessageDialog(null, "Conversion has finished for " + name);
            else if(converter.getTerminated() == 0 && Main.ui.isConverterCancelled == 1) {
                JOptionPane.showMessageDialog(null, "Conversion was cancelled for" + name);
                converter.app.destroy();
                Main.ui.isConverterCancelled = 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Oh no! Something goofed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        Main.ui.converterCancel.setEnabled(false);
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
            if(merger.getTerminated() == 0)
                JOptionPane.showMessageDialog(null, "Merging has finished for" + name);
            else if(merger.getTerminated() == 0 && Main.ui.isMergeCancelled == 1) {
                JOptionPane.showMessageDialog(null, "Merging was cancelled for" + name);
                merger.app.destroy();
                Main.ui.isMergeCancelled = 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Oh no! Something goofed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        Main.ui.mergeCancel.setEnabled(false);
        return null;
    }
}


