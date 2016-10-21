package zergtel.core;

import zergtel.core.downloader.Http;
import java.io.*;
public class Run {

	public static void main(String[] args) {
		try {
			Http.get("test");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Runtime convert = Runtime.getRuntime();
			Process app = convert.exec("ffmpeg.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
