package zergtel.core.io;

import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.io.*;
import java.io.*;
/**
 * Created by User on 2016-11-01.
 */
public class Metadata {

    public static void get(File f) throws IOException
    {
        IContainer iC = IContainer.make();
        int data = iC.open(f.getPath(), IContainer.Type.READ, null);
        if (data < 0)
            throw new RuntimeException("ripperoni with a side of spaghetti");
        int numStreams = iC.getNumStreams();
        long duration = iC.getDuration();
        long fileSize = iC.getFileSize();
        long bitRate = iC.getBitRate();
    }

}

//http://stackoverflow.com/questions/28082995/how-to-get-id3tags-information-of-a-audio-file-in-a-specific-url
//https://github.com/sannies/mp4parser ... http://www.xuggle.com/downloads potential maven libraries to be added (don't add them until we meet up again Nov. 2 please