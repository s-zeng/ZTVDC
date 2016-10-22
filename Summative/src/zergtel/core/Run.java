package zergtel.core;

		import zergtel.core.downloader.Http;
		import java.io.*;
public class Run {

	public static void main(String[] args) {
		try {
			Http.get("http://popplers5.bandcamp.com/download/track?enc=mp3-128&fsig=8b732af24df036790dce29b8bf65fde4&id=214214452&stream=1&ts=1476937747.0", "Nascency.mp3");
			Http.get("http://simonzeng.tk", "index.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
