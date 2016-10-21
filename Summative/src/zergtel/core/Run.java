package zergtel.core;

		import zergtel.core.downloader.Http;
		import java.io.*;
public class Run {

	public static void main(String[] args) {
		try {
			Http.get("http://google.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
