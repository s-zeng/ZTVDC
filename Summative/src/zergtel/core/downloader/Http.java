package zergtel.core.downloader;

import java.io.*;
import java.net.*;


public class Http {
	public static String get(String uri) throws Exception {
		return get(new URL(uri));
	}

	public static String get(URL uri) throws Exception {
		URLConnection connection = uri.openConnection();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						connection.getInputStream()));

		FileWriter writer = new FileWriter(uri.getFile());

		String lineInput;
		while ((lineInput = in.readLine()) != null) {
			writer.write(lineInput);
		}


		in.close();
		writer.close();

		return "";
	}
}