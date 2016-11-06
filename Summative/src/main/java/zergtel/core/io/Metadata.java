package zergtel.core.io;

import com.xuggle.xuggler.*;

import java.io.*;

public class Metadata {

    public static void transfer(File f, File f2) throws IOException {
        IContainer iR = IContainer.make();
        int data = iR.open(f.getPath(), IContainer.Type.READ, null);
        IMetaData metaData = iR.getMetaData();
        IContainer iW = IContainer.make();
        int data2 = iW.open(f2.getPath(), IContainer.Type.WRITE, null);
        iW.setMetaData(metaData);

    }

}

//http://stackoverflow.com/questions/28082995/how-to-get-id3tags-information-of-a-audio-file-in-a-specific-url
//https://github.com/sannies/mp4parser ... http://www.xuggle.com/downloads potential maven libraries to be added (don't add them until we meet up again Nov. 2 please