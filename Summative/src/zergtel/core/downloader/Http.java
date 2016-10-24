package zergtel.core.downloader;

import java.io.*;
import java.net.*;


public class Http {
	private static final int BUFFER_SIZE = 4096;
	private static final String DOWNLOAD_LOCATION = "download/";

//	Todo - method to avoid naming conflicts (e.g. save as example(1).mp3 if example.mp3 already exists)
//		maybe add seperate class for file things like this and cleanseName


	public static String get(String uri) throws Exception {
		return get(new URL(uri), "");
	}

	public static String get(String uri, String fileName) throws Exception {
		return get(new URL(uri), fileName);
	}

	private static String get(URL uri, String fileName) throws Exception {
		//Merits some refactoring for efficiency this code does

		if (fileName.equals("")) {
			fileName = getFileOutputName(uri);
			System.out.println("File name: " + fileName);
		}

		fileName = DOWNLOAD_LOCATION.concat(cleanseName(fileName));

		URLConnection connection = uri.openConnection();

		InputStream in = connection.getInputStream();
		byte[] buffer = new byte[BUFFER_SIZE];
		int n;

		OutputStream out = new FileOutputStream(fileName);
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
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
		return fileName.replaceAll("/", "");
	}
}