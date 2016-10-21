package zergtel.core;
/**
 * The static methods in this class allow text to be written to Or read from a file
 * @author Shyam Patel
 * @since January 2016
 *
 */
import java.io.*;

public class FileIO
{
	/*
	 * VARIABLE AND METHODS FOR WRITING TO A FILE:
	 */

    private static PrintWriter fileOut;

    /**
     * Creates a new file in the current folder and places a reference to it in fileOut
     * @param file represents name of the file
     */

    public static void createOutputFile (String file)
    {
        createOutputFile(file, false);
        try
        {
            fileOut = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        }
        catch(IOException e)
        {
            System.out.println("Cannot create file: " + file);
        }

    }

    /**
     * creates a new file in the current folder and places a reference to it in fileOut
     * @param file represents name of the file
     * @param append true if you want to add to the existing file
     */

    public static void createOutputFile(String file, boolean append)
    {

        try
        {
            fileOut = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
        }

        catch(IOException e)
        {
            System.out.println("Cannot create file:  " + file);
        }

    }

    /**
     * Text to be added to current file
     * @param text the characters to be added
     */

    public static void print(String text)
    {
        fileOut.print(text);
    }

    /**
     * Text to be added to current file
     * @param text the characters to be added
     */

    public static void println(String text)
    {
        fileOut.println(text);
    }

    /**
     * closes the file that is being written to
     * Note: this method must b called when you are finished
     * in order to save changes
     */

    public static void closeOutputFile()
    {
        fileOut.close();

    }

    //VARIABLES AND METHODS FOR READING A FILE
    private static BufferedReader fileIn;

    /**
     * Opens a file called file (that is in the current folder and places a reference to it in fileIn)
     * @param file the name of the file that already exists
     *
     */

    public static void openInputFile (String file)
    {
        try
        {
            fileIn = new BufferedReader(new FileReader(file));
        }

        catch(FileNotFoundException e)
        {
            System.out.println("Cannot open the file: " + file);
        }
    }

    /**
     * Read the next line from the file and return it

     * @return the text from the file read from the computer that was converted into a string
     */

    public static String readLine()
    {
        try
        {
            return fileIn.readLine();
        }
        catch(IOException e)
        {
            return null;
        }
    }

    /**
     * closes the file that iscurrently being read from
     */

    public static void closeInputFile()
    {
        try
        {
            fileIn.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}