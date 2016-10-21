package zergtel.core.io;

import java.io.*;

public class FileIn
{
    /**
     * The static methods in this class allow text to be written to Or read from a file
     * @author Shyam Patel
     * @since January 2016
     *
     */
    //VARIABLES AND METHODS FOR READING A FILE
    private BufferedReader fileIn;
    private static int threads = 0;

    /**
     * Opens a file called file (that is in the current folder and places a reference to it in fileIn)
     * @param file the name of the file that already exists
     *
     */

    public FileIn (String file)
    {
        try
        {
            fileIn = new BufferedReader(new FileReader(file));
            threads++;
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

    public String readLine()
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

    public void closeInputFile()
    {
        try
        {
            fileIn.close();
            threads--;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static int getThreads()
    {
        return threads;
    }
}