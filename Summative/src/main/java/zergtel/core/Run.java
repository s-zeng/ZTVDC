package zergtel.core;

		import zergtel.core.downloader.Http;

		import java.net.URL;

public class Run {

	public static void main(String[] args) {
		try {
//			HttpOld.get("http://popplers5.bandcamp.com/download/track?enc=mp3-128&fsig=8b732af24df036790dce29b8bf65fde4&id=214214452&stream=1&ts=1476937747.0", "Nascency.mp3");
//			HttpOld.get("http://simonzeng.tk/example.mp3");
			Http getter = new Http(new URL("http://simonzeng.tk/example.mp3"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
