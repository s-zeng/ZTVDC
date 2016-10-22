package zergtel.core.downloader;

import java.io.*;
import java.net.*;


public class Http {
	private static final int bufferSize = 4096;

	public static String get(String uri, String fileName) throws Exception {
		return get(new URL(uri), fileName);
	}

	private static String get(URL uri, String fileName) throws Exception {
		if (fileName.equals(""))
			fileName = getFileOutputName(uri);

		URLConnection connection = uri.openConnection();

		InputStream in = connection.getInputStream();
		byte[] buffer = new byte[bufferSize];
		int n = - 1;

		OutputStream output = new FileOutputStream(fileName);
		while ((n = in.read(buffer)) != -1)
		{
			output.write(buffer, 0, n);
		}
		in.close();
		output.close();

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
}