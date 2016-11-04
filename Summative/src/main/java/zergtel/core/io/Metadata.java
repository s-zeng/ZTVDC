package zergtel.core.io;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import java.io.*;

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
        System.out.println(numStreams + "\n" + duration + "\n" + fileSize + "\n" + bitRate + "\n");
        for(int i = 0; i < numStreams; i++)
        {
            IStream s = iC.getStream(i);
            IStreamCoder iCS = s.getStreamCoder();
            System.out.printf(" %d", i);
            System.out.printf("%s ", iCS.getCodecType());
            System.out.printf("%s ", iCS.getCodecID());
            System.out.printf("%s ", s.getDuration());
            System.out.printf("%s", iC.getStartTime());
            System.out.printf("%d%d", iCS.getTimeBase().getNumerator(), iCS.getTimeBase().getDenominator());
            System.out.println("Audio/Video codec below");
            if(iCS.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO)
            {
                System.out.printf("%d", iCS.getSampleRate());
                System.out.printf("%d", iCS.getChannels());
                System.out.printf("s", iCS.getSampleFormat());
            }
            else if (iCS.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO)
            {
                System.out.printf("%d", iCS.getWidth());
                System.out.printf("%d", iCS.getHeight());
                System.out.printf("%s", iCS.getPixelType());
                System.out.printf("%5.2f", iCS.getFrameRate().getDouble());
            }
        }
    }

}

//http://stackoverflow.com/questions/28082995/how-to-get-id3tags-information-of-a-audio-file-in-a-specific-url
//https://github.com/sannies/mp4parser ... http://www.xuggle.com/downloads potential maven libraries to be added (don't add them until we meet up again Nov. 2 please