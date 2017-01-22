package zergtel.core.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * The core behind grabbing resources from the web
 * Very simple and naive implementation of http resource grabbing
 *
 * Some nuances with this class:
 *   - Throughout ComputerUI, download location is set by using setDownloadLocation in this class. Unfortunately,
 *     this can cause some complication with where some files actually end up, depending on the sequencing of events.
 *     See VGetInterface for an example
 */
public class EzHttp {
	private static final int BUFFER_SIZE = 4096;
	public static final String DEFAULT_LOCATION = "download/";
	public static final String TEMP_LOCATION = System.getenv("temp") + "/ZTVDC/";
	private static String downloadLocation = "download/";

//	Todo - method to avoid naming conflicts (e.g. save as example(1).mp3 if example.mp3 already exists)
//		maybe add seperate class for file things like this and cleanseName

	public static String getDownloadLocation() {
		return downloadLocation;
	}

	public static void setDLToDefault() {
		downloadLocation = DEFAULT_LOCATION;
	}

	public static void setDLToTemp() {
		downloadLocation = TEMP_LOCATION;
	}

	public static void setDownloadLocation(String ndownloadLocation) {
		downloadLocation = ndownloadLocation;
		File dir = new File(downloadLocation);
		dir.mkdirs();
	}

	public static String get(String uri) throws Exception {
		return get(new URL(uri), "", "");
	}

	public static String get(URL uri) throws Exception {
		return get(uri, "", downloadLocation);
	}

	public static String get(URL uri, String fileName) throws Exception {
		return get(uri, fileName, "");
	}

	public static String get(String uri, String fileName) throws Exception {
		return get(new URL(uri), fileName, "");
	}

	public static String get(String uri, String fileName, String downLocation) throws Exception {
		return get(new URL(uri), fileName, downLocation);
	}

	/**
	 * Gets a resource from a given url, and saves it as filename at downlocation
	 *
	 * @param uri - URL of resource
	 * @param fileName - Name of saved file
	 * @param downLocation - Location of saving
	 * @return - Name of file
	 * @throws Exception - This should be replaced with a much more specific exception
	 */
	private static String get(URL uri, String fileName, String downLocation) throws Exception {
		//Merits some refactoring for efficiency this code does

		if (fileName.equals("")) {
			fileName = getFileOutputName(uri);
			System.out.println("File name: " + fileName);
		}

		System.out.println("DownLocation: " + downLocation);
		File dir = new File(
				cleanseDirectory(downLocation)
		);
		System.out.println("new downlocation: " + dir.getAbsolutePath());
		System.out.println(dir.mkdirs());

		System.out.println(fileName);
		System.out.println(dir.getAbsolutePath());
		fileName = dir
				.getAbsolutePath()
				.concat("\\")
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

	/**
	 * Cleanses a file from illegal characters with some regex
	 *
	 * @param fileName - Name to cleanse
	 * @return - Cleansed name
	 */
	public static String cleanseName(String fileName) {
		//flesh this function out to be able to fix all illegal windows file names
		return fileName.replaceAll("\"|\\/|\\?|\\||:|\\*|<|>", "").replaceAll("\\.+$", "");
	}

	/**
	 * Same as cleanseName, but allows slashes for nested directories
	 *
	 * @param dirName - Name to cleanse
	 * @return - Cleansed name
	 */
	public static String cleanseDirectory(String dirName) {
		return dirName.replaceAll("\"|\\?|\\||\\*|<|>", "").replaceAll("\\.+$", "");
	}
}