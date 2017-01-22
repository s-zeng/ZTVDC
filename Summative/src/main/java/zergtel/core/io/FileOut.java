package zergtel.core.io;

/**
 * This class was originally meant for cleaning up of file outputs.
 * Throughout zergtel.core, many writes are made to files from many different sources.
 * We had intended this class to be the single interface with which all file writing is done, so that we can also include
 * name collision, name cleasing, and perhaps progress bar information in the package as well.
 *
 * In the end, we moved much of the functionality here to zergtel.core.EzHttp, but perhaps future mainteners can flesh this
 * class out for better versatility of code.
 */
public class FileOut
{
    private static int threads = 0;
    private static String downloadLocation = "download/";

}