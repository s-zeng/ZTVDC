package com.github.axet.vget.info;

import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.axet.vget.info.VideoInfo.States;
import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.ex.DownloadInterruptedError;

public abstract class VGetParser {

    public abstract VideoInfo info(URL web);

    public void info(VideoInfo info, AtomicBoolean stop, Runnable notify) {
        try {
            List<VideoFileInfo> dinfo = extract(info, stop, notify);

            info.setInfo(dinfo);

            for (DownloadInfo i : dinfo) {
                i.setReferer(info.getWeb());
                i.extract(stop, notify);
            }
        } catch (DownloadInterruptedError e) {
            info.setState(States.STOP, e);
            notify.run();
            throw e;
        } catch (RuntimeException e) {
            info.setState(States.ERROR, e);
            notify.run();
            throw e;
        }
    }

    public abstract List<VideoFileInfo> extract(final VideoInfo vinfo, final AtomicBoolean stop, final Runnable notify);

}
