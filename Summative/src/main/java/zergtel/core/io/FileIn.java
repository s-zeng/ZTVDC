package zergtel.core.io;

import java.io.File;
import java.io.IOException;


public class FileIn
{
    private static final int BUFFER_SIZE = 4096;
    private static int threads = 0;
    private String fileName;
    private String format;
    private int char1;
    public String getName(File f) throws IOException
    {
        fileName = f.getName();
        return fileName;
    }

    public static int getThreads()
    {
        return threads;
    }
}