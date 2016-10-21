package zergtel.core.downloader;

import java.io.*;
import java.net.*;
import zergtel.core.io.*;


public class Http {
	public static String get(String uri) throws Exception {
		return get(new URL(uri));
	}

	public static String get(URL uri) throws Exception {
		String fileName = getFileOutputName(uri);

		URLConnection connection = uri.openConnection();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						connection.getInputStream()));

		FileOut writer = new FileOut(fileName);



		String lineInput;
		while ((lineInput = in.readLine()) != null) {
			writer.println(lineInput);
		}


		in.close();
		writer.closeOutputFile();

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