package zergtel.core;

		import zergtel.core.downloader.Http;

		import java.net.URL;

import com.github.axet.vget.*;
import java.io.*;
		import java.util.concurrent.atomic.AtomicBoolean;



public class Run {

	public static void main(String[] args) {
		try {
//			HttpOld.get("http://popplers5.bandcamp.com/download/track?enc=mp3-128&fsig=8b732af24df036790dce29b8bf65fde4&id=214214452&stream=1&ts=1476937747.0", "Nascency.mp3");
//			HttpOld.get("http://simonzeng.tk/example.mp3");
//			Http getter = new Http(new URL("http://simonzeng.tk/example.mp3"));
//			getter.download();
//
//			while (getter.getStatus() == 0)
//				System.out.println(getter.getProgress());
			VGet thread1 = new VGet(new URL("https://www.youtube.com/watch?v=dQw4w9WgXcQ"), new File("download/"));
			thread1.download(new AtomicBoolean(false), new Runnable() {
				public void run() {
					System.out.print(".");
				}
			});

			//Todo - integrate vget into the project (shift dependencies) to make changes
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
