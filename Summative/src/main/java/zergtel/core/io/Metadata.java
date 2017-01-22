package zergtel.core.io;

import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IMetaData;

import java.io.File;
import java.util.Map;


public class Metadata {

    /**
     * Sets the metadata of a file
     * @param file the file that will have the information stored into
     * @param map Information of the Metadata to be added to the file
     * This class is deprecated
     *            (add more content about uses and how to revive)
     */
    public static void set(File file, Map<String, String> map)
    {
        IContainer iW = IContainer.make(); //creates an IContainer from xuggle libraries
        iW.open(file.getPath(), IContainer.Type.WRITE, null); //sets the IContainer to write information inside of the file received.
        IMetaData data = IMetaData.make(); //Makes the Metadata
        //data.setValue(); // setValue(String keys, String value) //Sets the metadata value received from the map
        iW.setMetaData(data); //sets the metadata into the file opened
        iW.close(); //closes the IContainer
    }

}