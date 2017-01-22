package zergtel.core.io;

import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IMetaData;

import java.io.File;
import java.util.Map;


public class Metadata {

    /**
     * Sets the metadata of a file
     *
     * @param file the file that will have the information stored into
     * @param map Information of the Metadata to be added to the file (currently not implemented and not in use -
     *            you'll have to add in some kind of iteration over the map to actually add it into the file)
     *
     * This class is deprecated, and only partially implemented.
     * Originally intended to put metadata from a youtube video into the downloaded video (as normally downloads from
     * youtube don't come with metadata), but we didn't have enough time to write in extraction of video data from Youtube
     *
     * For future maintainers, the process shouldn't be too hard - Youtube's api has plenty of documentation surrounding
     * how to get such data; no need for http page crawling.
     *
     * In fact, if you prefetch the data on every search result before a download is done, you get the ability to display
     * video length on the search results preview too, as that information is not available from a general search and must
     * be obtained by querying the api on a specific video. You can query for multiple videos at the same time, so don't
     * worry too much about api restrictions.
     */
    public static void set(File file, Map<String, String> map)
    {
        IContainer iW = IContainer.make(); //creates an IContainer from xuggle libraries
        iW.open(file.getPath(), IContainer.Type.WRITE, null); //sets the IContainer to write information inside of the file received.
        IMetaData data = IMetaData.make(); //Makes the Metadata

        //data.setValue(); // setValue(String keys, String value) //Sets the metadata value received from the map
        //You'll have to iterate over the map to actually set the metadata flags properly

        iW.setMetaData(data); //sets the metadata into the file opened
        iW.close(); //closes the IContainer
    }

}