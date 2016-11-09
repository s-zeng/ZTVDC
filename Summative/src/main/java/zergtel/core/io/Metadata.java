package zergtel.core.io;

import com.xuggle.xuggler.*;

import java.io.*;
import java.util.*;


public class Metadata {

    public static void get(File file, Map<String, String> map)
    {
        IContainer iW = IContainer.make();
        iW.open(file.getPath(), IContainer.Type.WRITE, null);
        IMetaData data = IMetaData.make();
        //data.setValue(); // setValue(String keys, String value)
        iW.setMetaData((IMetaData) data);
        iW.close();
    }

}

//http://stackoverflow.com/questions/28082995/how-to-get-id3tags-information-of-a-audio-file-in-a-specific-url
//https://github.com/sannies/mp4parser ... http://www.xuggle.com/downloads potential maven libraries to be added (don't add them until we meet up again Nov. 2 please