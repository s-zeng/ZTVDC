package zergtel.core.downloader;

import java.io.*;
import java.net.*;


public class EzHttp {
	private static final int BUFFER_SIZE = 4096;
	private static String downloadLocation = "download/";

//	Todo - method to avoid naming conflicts (e.g. save as example(1).mp3 if example.mp3 already exists)
//		maybe add seperate class for file things like this and cleanseName


	public static String getDownloadLocation() {
		return downloadLocation;
	}

	public static void setDownloadLocation(String ndownloadLocation) {
		downloadLocation = ndownloadLocation;
	}

	public static String get(String uri) throws Exception {
		return get(new URL(uri), "", "");
	}

	public static String get(String uri, String fileName) throws Exception {
		return get(new URL(uri), fileName, "");
	}

	public static String get(String uri, String fileName, String downLocation) throws Exception {
		return get(new URL(uri), fileName, downLocation);
	}

	private static String get(URL uri, String fileName, String downLocation) throws Exception {
		//Merits some refactoring for efficiency this code does

		if (fileName.equals("")) {
			fileName = getFileOutputName(uri);
			System.out.println("File name: " + fileName);
		}

		File dir = new File(
				downloadLocation.concat(cleanseDirectory(downLocation)
				)
		);
		dir.mkdir();

		fileName = dir
				.getAbsolutePath()
				.concat("/")
				.concat(cleanseName(fileName));
		System.out.println(fileName);

		URLConnection connection = uri.openConnection();
		int size = connection.getContentLength();

		InputStream in = connection.getInputStream();
		byte[] buffer = new byte[BUFFER_SIZE];
		int n;
		int downloaded = 0;


		OutputStream out = new FileOutputStream(fileName);
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
			downloaded += n;
		}
		in.close();
		out.close();

		return fileName;
	}

	private static String getFileOutputName(URL uri) {
		String output;
		if (!uri.getFile().isEmpty()) {
			output = uri.getFile();
		} else {
			output = uri.getHost() + uri.getPath();
		}

		return output;
	}

	private static String cleanseName(String fileName) {
		//flesh this function out to be able to fix all illegal windows file names
		return fileName.replaceAll("\"|\\/|\\?|\\||:|\\*|<|>", "");
	}

	private static String cleanseDirectory(String dirName) {
		return dirName.replaceAll("\"|\\?|\\||:|\\*|<|>", "");
	}
}